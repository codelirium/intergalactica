package io.codelirium.blueground.intergalactica.configuration.security.filter;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import javax.servlet.http.HttpServletRequest;

import static io.codelirium.blueground.intergalactica.controller.mapping.UrlMappings.API_PATH_WEBSOCKET;
import static java.util.Objects.nonNull;


public class TokenAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

	private static final String HEADER_TOKEN = "X-Token";

	private static final String PARAM_TOKEN = "token";


	@Override
	protected Object getPreAuthenticatedPrincipal(final HttpServletRequest request) {

		return getToken(request);

	}


	@Override
	protected Object getPreAuthenticatedCredentials(final HttpServletRequest request) {

		return getToken(request);

	}


	private String getToken(final HttpServletRequest request) {

		return nonNull(request.getParameter(PARAM_TOKEN)) && request.getRequestURI().contains(API_PATH_WEBSOCKET) ?
													request.getParameter(PARAM_TOKEN) : request.getHeader(HEADER_TOKEN);

	}
}
