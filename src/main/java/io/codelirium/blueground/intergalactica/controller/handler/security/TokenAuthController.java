package io.codelirium.blueground.intergalactica.controller.handler.security;

import io.codelirium.blueground.intergalactica.controller.annotation.SecureRestController;
import io.codelirium.blueground.intergalactica.controller.exception.CannotValidateTokenException;
import io.codelirium.blueground.intergalactica.controller.handler.business.ColonistController;
import io.codelirium.blueground.intergalactica.model.dto.TokenProfileDTO;
import io.codelirium.blueground.intergalactica.model.dto.response.RESTSuccessResponseBody;
import io.codelirium.blueground.intergalactica.service.security.SecurityContextService;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.inject.Inject;
import java.util.Optional;

import static io.codelirium.blueground.intergalactica.controller.exception.CannotGenerateTokenException.MESSAGE_CANNOT_GENERATE_TOKEN;
import static io.codelirium.blueground.intergalactica.controller.exception.CannotValidateTokenException.MESSAGE_INVALID_OR_MISSING_TOKEN;
import static io.codelirium.blueground.intergalactica.controller.mapping.UrlMappings.*;
import static io.codelirium.blueground.intergalactica.model.dto.response.builder.RESTResponseBodyBuilder.success;
import static io.codelirium.blueground.intergalactica.util.mapper.MapperUtil.toPlainToken;
import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@SecureRestController
@RequestMapping(API_PATH_ROOT)
public class TokenAuthController {

	private static final Logger LOGGER = getLogger(ColonistController.class);


	private SecurityContextService securityContextService;


	@Inject
	public TokenAuthController(final SecurityContextService securityContextService) {

		this.securityContextService = securityContextService;

	}


	@ResponseStatus(OK)
	@GetMapping(value = API_ENDPOINT_TOKENS, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<RESTSuccessResponseBody<TokenProfileDTO>> getToken() {

		final Optional<TokenProfileDTO> optionalTokenDTO = securityContextService.getPrincipal();

		if (!optionalTokenDTO.isPresent()) {

			throw new CannotValidateTokenException(MESSAGE_CANNOT_GENERATE_TOKEN);

		}


		LOGGER.debug("Building response for token generation ...");

		final RESTSuccessResponseBody<TokenProfileDTO> body = success(TokenProfileDTO.class.getSimpleName(), singletonList(toPlainToken(optionalTokenDTO.get().getToken())));

		LOGGER.debug("Response body for token generation was built successfully.");


		return new ResponseEntity<>(body, OK);
	}


	@ResponseStatus(OK)
	@GetMapping(value = API_ENDPOINT_TOKEN_PROFILE, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<RESTSuccessResponseBody<TokenProfileDTO>> getTokenProfile() {

		final Optional<TokenProfileDTO> optionalTokenDTO = securityContextService.getPrincipal();

		if (!optionalTokenDTO.isPresent() || isNull(optionalTokenDTO.get().getToken())) {

			throw new CannotValidateTokenException(MESSAGE_INVALID_OR_MISSING_TOKEN);

		}


		LOGGER.debug("Building response for token profile retrieval ...");

		final RESTSuccessResponseBody<TokenProfileDTO> body = success(TokenProfileDTO.class.getSimpleName(), singletonList(optionalTokenDTO.get()));

		LOGGER.debug("Response body for token profile retrieval was built successfully.");


		return new ResponseEntity<>(body, OK);
	}
}
