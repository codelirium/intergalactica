package io.codelirium.blueground.intergalactica.repository;

import io.codelirium.blueground.intergalactica.model.entity.UnitEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;


@Repository
@Transactional
public interface UnitRepository extends PagingAndSortingRepository<UnitEntity, Long> {

	String SQL_QUERY_GET_ALL_UNITS_BY_TITLE_OR_REGION = "SELECT "                                                   +
															"*  "                                                   +
														"FROM "                                                     +
															"UNITS U "                                              +
														"WHERE "                                                    +
																"LOWER(U.TITLE)  LIKE CONCAT('%', LOWER(?1), '%') " +
															" OR LOWER(U.REGION) LIKE CONCAT('%', LOWER(?1), '%')";

	@Cacheable(value = "unit-by-search-term", keyGenerator = "keyGenerator")
	@Query(nativeQuery = true, value = SQL_QUERY_GET_ALL_UNITS_BY_TITLE_OR_REGION)
	Page<UnitEntity> findByTitleOrRegion(final String searchTerm, final Pageable pageable);

}
