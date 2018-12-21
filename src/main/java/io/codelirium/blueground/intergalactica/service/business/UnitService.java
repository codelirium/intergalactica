package io.codelirium.blueground.intergalactica.service.business;

import io.codelirium.blueground.intergalactica.model.dto.UnitDTO;
import io.codelirium.blueground.intergalactica.model.dto.UnitViewersDTO;
import io.codelirium.blueground.intergalactica.model.dto.event.UnitViewersEvent;
import io.codelirium.blueground.intergalactica.model.dto.pagination.PagedSearchDTO;
import io.codelirium.blueground.intergalactica.model.entity.UnitEntity;
import io.codelirium.blueground.intergalactica.repository.UnitRepository;
import io.codelirium.blueground.intergalactica.repository.UnitViewersRepository;
import io.codelirium.blueground.intergalactica.service.annotation.BusinessService;
import io.codelirium.blueground.intergalactica.util.mapper.MapperUtil;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import reactor.core.scheduler.Scheduler;
import javax.inject.Inject;
import java.util.Collection;

import static io.codelirium.blueground.intergalactica.util.mapper.MapperUtil.toUnitDTOs;
import static io.codelirium.blueground.intergalactica.util.pagination.PaginationUtil.feedPageInfo;
import static io.codelirium.blueground.intergalactica.util.pagination.PaginationUtil.makePageRequest;
import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.util.Assert.notNull;
import static reactor.core.publisher.Mono.defer;
import static reactor.core.publisher.Mono.just;


@BusinessService
public class UnitService {

	private static final Logger LOGGER = getLogger(UnitService.class);


	private Scheduler jdbcScheduler;

	private UnitRepository unitRepository;

	private UnitViewersRepository unitViewersRepository;

	private ApplicationEventPublisher eventPublisher;


	@Inject
	public UnitService( final Scheduler                 jdbcScheduler,
						final UnitRepository            unitRepository,
						final UnitViewersRepository     unitViewersRepository,
						final ApplicationEventPublisher eventPublisher) {

		this.jdbcScheduler = jdbcScheduler;

		this.unitRepository = unitRepository;

		this.unitViewersRepository = unitViewersRepository;

		this.eventPublisher = eventPublisher;

	}


	public Collection<UnitDTO> getAllPaged(final PagedSearchDTO searchDTO) {

		notNull(searchDTO, "The search DTO cannot be null.");


		final Page<UnitEntity> unitEntities = unitRepository.findAll(makePageRequest(searchDTO));

		feedPageInfo(searchDTO, unitEntities);


		LOGGER.debug(format("Retrieved %d intergalactic units - page: %d/%d - page size: %d - total units: %d",
																									unitEntities.getContent().size(),
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
																									unitEntities.getContent().size(),
																									searchDTO.getPage(),
																									searchDTO.getTotalPages(),
																									searchDTO.getPageSize(),
																									searchDTO.getTotalElements()));


		return toUnitDTOs(unitEntities.getContent());
	}


	public UnitViewersDTO increaseUnitViewers(final Long unitId) {

		notNull(unitId, "The unit id cannot be null.");


		return defer(() -> just(unitViewersRepository.increaseUnitViewers(unitId)))
					.subscribeOn(jdbcScheduler)
					.doOnSuccess(unitViewersEntity -> {

						this.eventPublisher.publishEvent(new UnitViewersEvent(unitViewersEntity));

						LOGGER.debug(format("Added viewer to unit id: #%d", unitId));

					})
					.doOnError(e -> LOGGER.error(format("Failed to add viewer to unit id: #%d - error: %s", unitId, e.getMessage())))
					.blockOptional()
						.map(MapperUtil::toUnitViewersDTO)
						.orElseThrow(RuntimeException::new);
	}


	public UnitViewersDTO decreaseUnitViewers(final Long unitId) {

		notNull(unitId, "The unit id cannot be null.");


		return defer(() -> just(unitViewersRepository.decreaseUnitViewers(unitId)))
					.subscribeOn(jdbcScheduler)
					.doOnSuccess(unitViewersEntity -> {

						this.eventPublisher.publishEvent(new UnitViewersEvent(unitViewersEntity));

						LOGGER.debug(format("Removed viewer from unit id: #%d", unitId));

					})
					.doOnError(e -> LOGGER.error(format("Failed to remove viewer from unit id: #%d - error: %s", unitId, e.getMessage())))
					.blockOptional()
						.map(MapperUtil::toUnitViewersDTO)
						.orElseThrow(RuntimeException::new);
	}
}
