package consulting.crafters.visibility.checker.aspect;

import consulting.crafters.visibility.checker.ExternalVisibilityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class HasVisibilityOnAspect {
    private final ExternalVisibilityService visibilityService;

    @Before("@annotation(consulting.crafters.visibility.checker.aspect.HasVisibilityOn)")
    public void checkVisibility(JoinPoint jp) {
        val signature = (MethodSignature) jp.getSignature();
        val method = signature.getMethod();
        val visibilityExpression = method.getAnnotation(HasVisibilityOn.class).value();
        val parameterNames = signature.getParameterNames();
        val args = jp.getArgs();
        val parser = new SpelExpressionParser();
        val context = new StandardEvaluationContext();
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        val visibilityExpressionValue = parser.parseExpression(visibilityExpression).getValue(context, String.class);
        val username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!visibilityService.hasVisibilityOn(username, visibilityExpressionValue)) {
            throw new AccessDeniedException("User " + username + " does not have visibility on " + visibilityExpressionValue);
        }
    }
}
