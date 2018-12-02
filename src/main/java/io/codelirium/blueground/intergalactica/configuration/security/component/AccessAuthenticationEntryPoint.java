package io.codelirium.blueground.intergalactica.configuration.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.codelirium.blueground.intergalactica.model.dto.response.RESTFailureResponseBody;
import org.slf4j.Logger;
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
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Component
public class AccessAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	private static final Logger LOGGER = getLogger(AccessAuthenticationEntryPoint.class);


	private static final String ACCESS_BEGIN_PREFIX_MESSAGE = "ACCESS::HANDLER::BEGIN";
	private static final String ACCESS_END_PREFIX_MESSAGE   = "ACCESS::HANDLER::END";


	private ObjectMapper objectMapper;


	@Inject
	public AccessAuthenticationEntryPoint(final ObjectMapper objectMapper, @Value("${security.basic.realm:intergalactica}") final String realm) {

		this.objectMapper = objectMapper;

		super.setRealmName(realm);

	}


	@Override
	public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException e) throws IOException, ServletException {

		LOGGER.info("{} - unauthorized", ACCESS_BEGIN_PREFIX_MESSAGE);


		try {

			final String httpStatusCode = valueOf(SC_UNAUTHORIZED);


			LOGGER.debug("Building custom {} response body ...", httpStatusCode);

			final RESTFailureResponseBody<String> body = failure(httpStatusCode, e.getMessage());

			LOGGER.debug("Custom {} response body was built successfully.", httpStatusCode);


			response.setStatus(SC_UNAUTHORIZED);
			response.setContentType(APPLICATION_JSON_VALUE);
			response.setCharacterEncoding(UTF_8.toString());

			response.getOutputStream().write(objectMapper.writeValueAsBytes(body));

		} finally {

			LOGGER.info("{} - unauthorized", ACCESS_END_PREFIX_MESSAGE);

		}
	}
}
