package consulting.crafters.visibility.checker.aspect;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Inherited
@Retention(RUNTIME)
@Target({METHOD, TYPE})
public @interface HasVisibilityOn {
    String value();
}
