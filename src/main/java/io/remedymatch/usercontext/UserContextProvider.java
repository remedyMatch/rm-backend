package io.remedymatch.usercontext;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
class UserContextProvider {

    private static final String LOGISTIK_CLIENT = "rm-logistic-client";

    String getUserName() {
        return getJwt().getClaim("username");
    }

    boolean isTechnicalUser() {
        return LOGISTIK_CLIENT.equals(getJwt().getClaim("clientId"));
    }

    private Jwt getJwt() {
        return (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
