package consulting.crafters.visibility.checker.expression;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
public class VisibilityMethodSecurityExpressionRoot
        extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {
    private Object filterObject;
    private Object returnObject;

    public VisibilityMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean hasVisibilityOn(Long OrganizationId) {
        val username = ((UserDetails) this.getPrincipal()).getUsername();
        return false;
    }

    @Override
    public Object getThis() {
        return this;
    }
}
