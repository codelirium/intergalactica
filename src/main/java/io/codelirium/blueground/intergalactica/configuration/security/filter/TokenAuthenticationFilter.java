package io.codelirium.blueground.intergalactica.configuration.security.filter;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import javax.servlet.http.HttpServletRequest;


public class TokenAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

	private static final String HEADER_TOKEN = "X-Token";


	@Override
	protected Object getPreAuthenticatedPrincipal(final HttpServletRequest request) {

		return request.getHeader(HEADER_TOKEN);

	}


	@Override
	protected Object getPreAuthenticatedCredentials(final HttpServletRequest request) {

		return request.getHeader(HEADER_TOKEN);

	}
}
