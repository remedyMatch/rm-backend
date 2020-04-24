package io.remedymatch.usercontext;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.remedymatch.person.domain.service.PersonSucheService;
import lombok.AllArgsConstructor;

@Order(1)
@AllArgsConstructor
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class UserContextFilter implements Filter {

	private final UserContextProvider userContextProvider;
	private final PersonSucheService personSucheService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			UserContext.setContextUser(personSucheService.getByUsername(userContextProvider.getUserName()));

			chain.doFilter(request, response);
		} finally {
			UserContext.clear();
		}
	}
}