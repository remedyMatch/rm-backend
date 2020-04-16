package io.remedymatch.usercontext;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
class UserContextProvider {
	
	String getUserName() {
		return getJwt().getClaim("username");
	}

	private Jwt getJwt() {
		return (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
