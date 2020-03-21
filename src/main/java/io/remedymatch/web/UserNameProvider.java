package io.remedymatch.web;

import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Optional;

@Component
public class UserNameProvider {

    public String getUserName(){
        Jwt jwt  = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwt.getClaims().get("sub").toString();
    }
}
