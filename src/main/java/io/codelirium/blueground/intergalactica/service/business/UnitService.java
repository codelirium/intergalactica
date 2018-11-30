package io.codelirium.blueground.intergalactica.service.business;

import io.codelirium.blueground.intergalactica.model.dto.UnitDTO;
import io.codelirium.blueground.intergalactica.model.dto.pagination.PagedSearchDTO;
import io.codelirium.blueground.intergalactica.model.entity.UnitEntity;
import io.codelirium.blueground.intergalactica.repository.UnitRepository;
import io.codelirium.blueground.intergalactica.service.annotation.BusinessService;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import javax.inject.Inject;
import java.util.Collection;

import static io.codelirium.blueground.intergalactica.util.mapper.MapperUtil.toUnitDTOs;
import static io.codelirium.blueground.intergalactica.util.pagination.PaginationUtil.feedPageInfo;
import static io.codelirium.blueground.intergalactica.util.pagination.PaginationUtil.makePageRequest;
import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.util.Assert.notNull;


@BusinessService
public class UnitService {

	private static final Logger LOGGER = getLogger(UnitService.class);


	private UnitRepository unitRepository;


	@Inject
	public UnitService(final UnitRepository unitRepository) {

		this.unitRepository = unitRepository;

	}


	public Collection<UnitDTO> getAllPaged(final PagedSearchDTO searchDTO) {

		notNull(searchDTO, "The search DTO cannot be null.");


		final Page<UnitEntity> unitEntities = unitRepository.findAll(makePageRequest(searchDTO));

		feedPageInfo(searchDTO, unitEntities);


		LOGGER.debug(format("Retrieved %d intergalactic units - page: %d/%d - page size: %d - total units: %d",
																									searchDTO.getPage(),
																									searchDTO.getTotalPages(),
																									searchDTO.getPageSize(),
																									searchDTO.getTotalElements()));


		return toUnitDTOs(unitEntities.getContent());
	}


	public Collection<UnitDTO> searchByTermPaged(final String searchTerm, final PagedSearchDTO searchDTO) {

		notNull(searchTerm, "The search term cannot be null.");
		notNull(searchDTO, "The search DTO cannot be null.");


		final Page<UnitEntity> unitEntities = unitRepository.findByTitleOrRegion(searchTerm, makePageRequest(searchDTO));

		feedPageInfo(searchDTO, unitEntities);


		LOGGER.debug(format("Retrieved %d searched intergalactic units - page: %d/%d - page size: %d - total units: %d",
																									searchDTO.getPage(),
																									searchDTO.getTotalPages(),
																									searchDTO.getPageSize(),
																									searchDTO.getTotalElements()));


		return toUnitDTOs(unitEntities.getContent());
	}
}
