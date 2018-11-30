package io.codelirium.blueground.intergalactica.controller.handler.security;

import io.codelirium.blueground.intergalactica.controller.exception.CannotGenerateTokenException;
import io.codelirium.blueground.intergalactica.controller.exception.CannotGetTokenDetailsException;
import io.codelirium.blueground.intergalactica.controller.handler.business.ColonistController;
import io.codelirium.blueground.intergalactica.model.dto.TokenDTO;
import io.codelirium.blueground.intergalactica.model.dto.response.RESTSuccessResponseBody;
import io.codelirium.blueground.intergalactica.service.security.SecurityContextService;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.inject.Inject;
import java.util.Optional;

import static io.codelirium.blueground.intergalactica.controller.exception.CannotGenerateTokenException.MESSAGE_CANNOT_GENERATE_TOKEN;
import static io.codelirium.blueground.intergalactica.controller.exception.CannotGetTokenDetailsException.MESSAGE_CANNOT_GET_TOKEN_DETAILS;
import static io.codelirium.blueground.intergalactica.controller.mapping.UrlMappings.*;
import static io.codelirium.blueground.intergalactica.model.dto.response.builder.RESTResponseBodyBuilder.success;
import static io.codelirium.blueground.intergalactica.util.mapper.MapperUtil.toPlainToken;
import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
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
	public @ResponseBody ResponseEntity<RESTSuccessResponseBody<TokenDTO>> getToken() {

		final Optional<TokenDTO> optionalTokenDTO = securityContextService.getPrincipal();

		if (!optionalTokenDTO.isPresent()) {

			throw new CannotGenerateTokenException(MESSAGE_CANNOT_GENERATE_TOKEN);

		}


		LOGGER.debug("Building response for token generation ...");

		final RESTSuccessResponseBody<TokenDTO> body = success(TokenDTO.class.getSimpleName(), singletonList(toPlainToken(optionalTokenDTO.get().getToken())));

		LOGGER.debug("Response body for token generation was built successfully.");


		return new ResponseEntity<>(body, OK);
	}


	@ResponseStatus(OK)
	@GetMapping(value = API_ENDPOINT_TOKEN_DETAILS, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<RESTSuccessResponseBody<TokenDTO>> getTokenDetails() {

		final Optional<TokenDTO> optionalTokenDTO = securityContextService.getPrincipal();

		if (!optionalTokenDTO.isPresent() || isNull(optionalTokenDTO.get().getToken())) {

			throw new CannotGetTokenDetailsException(MESSAGE_CANNOT_GET_TOKEN_DETAILS);

		}


		LOGGER.debug("Building response for token details retrieval ...");

		final RESTSuccessResponseBody<TokenDTO> body = success(TokenDTO.class.getSimpleName(), singletonList(optionalTokenDTO.get()));

		LOGGER.debug("Response body for token details retrieval was built successfully.");


		return new ResponseEntity<>(body, OK);

	}
}
