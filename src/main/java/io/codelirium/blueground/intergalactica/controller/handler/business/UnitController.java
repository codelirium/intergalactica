package io.codelirium.blueground.intergalactica.controller.handler.business;

import io.codelirium.blueground.intergalactica.controller.exception.CannotGetUnitsException;
import io.codelirium.blueground.intergalactica.model.dto.UnitDTO;
import io.codelirium.blueground.intergalactica.model.dto.pagination.PagedSearchDTO;
import io.codelirium.blueground.intergalactica.model.dto.response.RESTSuccessResponseBody;
import io.codelirium.blueground.intergalactica.service.business.UnitService;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.inject.Inject;
import java.util.Collection;

import static io.codelirium.blueground.intergalactica.controller.mapping.UrlMappings.*;
import static io.codelirium.blueground.intergalactica.model.dto.response.builder.RESTResponseBodyBuilder.success;
import static io.codelirium.blueground.intergalactica.model.entity.UnitEntity.COLUMN_NAME_ID;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(API_PATH_ROOT)
public class UnitController {

	private static final Logger LOGGER = getLogger(UnitController.class);


	private UnitService unitService;


	@Inject
	public UnitController(final UnitService unitService) {

		this.unitService = unitService;

	}


	@ResponseStatus(OK)
	@GetMapping(value = API_ENDPOINT_UNITS_PAGED, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<RESTSuccessResponseBody<UnitDTO>> getAllPaged(@PathVariable(PATH_PARAM_PAGE) final int page) {

		final PagedSearchDTO searchDTO = new PagedSearchDTO(page, COLUMN_NAME_ID);


		Collection<UnitDTO> unitDTOS;

		try {

			unitDTOS = unitService.getAllPaged(searchDTO);

		} catch (final Exception e) {

			throw new CannotGetUnitsException(e.getMessage(), e.getCause());

		}


		LOGGER.debug("Building paged response for all units ...");

		final RESTSuccessResponseBody<UnitDTO> body = success(UnitDTO.class.getSimpleName(), unitDTOS, searchDTO);

		LOGGER.debug("Paged response body for all units was built successfully.");


		return new ResponseEntity<>(body, OK);
	}


	@ResponseStatus(OK)
	@GetMapping(value = API_ENDPOINT_UNITS_PAGED, params = REQ_PARAM_SEARCH_TERM, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<RESTSuccessResponseBody<UnitDTO>> searchByTermPaged( @PathVariable(PATH_PARAM_PAGE) final int page,
																							 @RequestParam(REQ_PARAM_SEARCH_TERM) final String searchTerm) {

		final PagedSearchDTO searchDTO = new PagedSearchDTO(page, COLUMN_NAME_ID);


		Collection<UnitDTO> unitDTOS;

		try {

			unitDTOS = unitService.searchByTermPaged(searchTerm, searchDTO);

		} catch (final Exception e) {

			throw new CannotGetUnitsException(e.getMessage(), e.getCause());

		}


		LOGGER.debug("Building paged response for searched units ...");

		final RESTSuccessResponseBody<UnitDTO> body = success(UnitDTO.class.getSimpleName(), unitDTOS, searchDTO);

		LOGGER.debug("Paged response body searched for units was built successfully.");


		return new ResponseEntity<>(body, OK);
	}
}
