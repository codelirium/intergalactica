package io.codelirium.blueground.intergalactica.controller.handler.business;

import io.codelirium.blueground.intergalactica.controller.exception.CannotSignUpColonistException;
import io.codelirium.blueground.intergalactica.model.dto.ColonistDTO;
import io.codelirium.blueground.intergalactica.model.dto.response.RESTSuccessResponseBody;
import io.codelirium.blueground.intergalactica.service.business.ColonistService;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.inject.Inject;

import static io.codelirium.blueground.intergalactica.controller.mapping.UrlMappings.API_ENDPOINT_COLONISTS;
import static io.codelirium.blueground.intergalactica.controller.mapping.UrlMappings.API_PATH_ROOT;
import static io.codelirium.blueground.intergalactica.model.dto.response.builder.RESTResponseBodyBuilder.success;
import static java.util.Collections.singletonList;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(API_PATH_ROOT)
public class ColonistController {

	private static final Logger LOGGER = getLogger(ColonistController.class);


	private ColonistService colonistService;


	@Inject
	public ColonistController(final ColonistService colonistService) {

		this.colonistService = colonistService;

	}


	@ResponseStatus(CREATED)
	@PostMapping(value = API_ENDPOINT_COLONISTS, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<RESTSuccessResponseBody<ColonistDTO>> signUpColonist(@RequestBody final ColonistDTO colonistDTO) {

		final ColonistDTO signedUpColonistDTO;

		try {

			signedUpColonistDTO = colonistService.signUp(colonistDTO);

		} catch (final Exception e) {

			throw new CannotSignUpColonistException(e.getMessage(), e.getCause());

		}


		LOGGER.debug("Building response for colonist sign-up ...");

		final RESTSuccessResponseBody<ColonistDTO> body = success(ColonistDTO.class.getSimpleName(), singletonList(signedUpColonistDTO));

		LOGGER.debug("Response body for colonist sign-up was built successfully.");


		return new ResponseEntity<>(body, CREATED);
	}
}
