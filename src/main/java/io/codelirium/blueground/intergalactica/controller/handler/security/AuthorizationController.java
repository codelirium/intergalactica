package io.codelirium.blueground.intergalactica.controller.handler.security;

import io.codelirium.blueground.intergalactica.controller.exception.CannotGenerateTokenException;
import io.codelirium.blueground.intergalactica.controller.handler.business.ColonistController;
import io.codelirium.blueground.intergalactica.model.dto.TokenDTO;
import io.codelirium.blueground.intergalactica.model.dto.response.RESTSuccessResponseBody;
import io.codelirium.blueground.intergalactica.service.security.SecurityContextService;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.inject.Inject;

import static io.codelirium.blueground.intergalactica.controller.exception.CannotGenerateTokenException.MESSAGE_CANNOT_GENERATE_TOKEN;
import static io.codelirium.blueground.intergalactica.controller.mapping.UrlMappings.API_ENDPOINT_TOKENS;
import static io.codelirium.blueground.intergalactica.controller.mapping.UrlMappings.API_PATH_ROOT;
import static io.codelirium.blueground.intergalactica.model.dto.response.builder.RESTResponseBodyBuilder.success;
import static io.codelirium.blueground.intergalactica.util.mapper.MapperUtil.toPlainToken;
import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(API_PATH_ROOT)
public class AuthorizationController {

	private static final Logger LOGGER = getLogger(ColonistController.class);


	private SecurityContextService securityContextService;


	@Inject
	public AuthorizationController(final SecurityContextService securityContextService) {

		this.securityContextService = securityContextService;

	}


	@ResponseStatus(OK)
	@GetMapping(value = API_ENDPOINT_TOKENS, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<RESTSuccessResponseBody<TokenDTO>> getToken() {

		final String token = securityContextService.getPrincipal().getToken();

		if (isNull(token)) {

			throw new CannotGenerateTokenException(MESSAGE_CANNOT_GENERATE_TOKEN);

		}


		LOGGER.debug("Building response for token generation ...");

		final RESTSuccessResponseBody<TokenDTO> body = success(TokenDTO.class.getSimpleName(), singletonList(toPlainToken(token)));

		LOGGER.debug("Response body for token generation was built successfully.");


		return new ResponseEntity<>(body, OK);
	}
}
