package io.remedymatch;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = WithMockJWTSecurityContextFactory.class)
public @interface WithMockJWT {
    String usernameClaim() default "";
    String [] groupsClaim() default {};
    String credentials() default "";
}
