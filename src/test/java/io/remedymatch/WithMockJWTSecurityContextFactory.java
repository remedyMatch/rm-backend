package io.remedymatch;

import net.minidev.json.JSONArray;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

public class WithMockJWTSecurityContextFactory implements WithSecurityContextFactory<WithMockJWT> {
    @Override
    public SecurityContext createSecurityContext(WithMockJWT annotation) {
        var claims = new HashMap<String, Object>();
        if (annotation.subClaim() != null) {
            claims.put("sub", annotation.subClaim());
        }
        if (annotation.groupsClaim() != null && annotation.groupsClaim().length > 0) {
            var groups = new JSONArray();
            for (String groupName : annotation.groupsClaim()) {
                groups.appendElement(groupName);
            }
            claims.put("groups",groups);
        }
        var fakedHeader = new HashMap<String,Object>();
        fakedHeader.put("typ","JWT");
        var jwt = new Jwt(
                UUID.randomUUID().toString(),
                Instant.now(),
                Instant.MAX,
                fakedHeader,
                claims
        );

        var authentication = new TestingAuthenticationToken(jwt, annotation.credentials());
        authentication.setAuthenticated(true);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }
}
