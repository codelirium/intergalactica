package io.codelirium.blueground.intergalactica.configuration.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static io.codelirium.blueground.intergalactica.model.dto.response.builder.RESTResponseBodyBuilder.failure;
import static java.lang.String.valueOf;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.nonNull;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Component
public class AccessAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	private ObjectMapper objectMapper;


	@Inject
	public AccessAuthenticationEntryPoint(final ObjectMapper objectMapper, @Value("${security.basic.realm:default}") final String realm) {

		this.objectMapper = objectMapper;

		super.setRealmName(realm);

	}


	@Override
	public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException e) throws IOException, ServletException {

		response.setStatus(SC_UNAUTHORIZED);
		response.setContentType(APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(UTF_8.toString());


		String message;

		if (nonNull(e.getCause())) {

			message = e.getCause().getMessage();

		} else {

			message = e.getMessage();

		}


		response.getOutputStream().write(objectMapper.writeValueAsBytes(failure(valueOf(SC_UNAUTHORIZED), message)));
	}
}
