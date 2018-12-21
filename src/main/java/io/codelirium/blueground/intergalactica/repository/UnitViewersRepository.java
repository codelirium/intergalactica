package io.codelirium.blueground.intergalactica.repository;

import io.codelirium.blueground.intergalactica.model.entity.UnitViewersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;


@Repository
@Transactional
public interface UnitViewersRepository extends JpaRepository<UnitViewersEntity, Long> {

	String SQL_QUERY_INCREASE_UNIT_VIEWERS_BY_UNIT_ID = "UPDATE "                                  +
															"UNIT_VIEWERS "                        +
														"SET "                                     +
															"ACTIVE_VIEWERS = ACTIVE_VIEWERS + 1 " +
														"WHERE "                                   +
															"UNIT_ID = ?1 "                        +
														"RETURNING "                               +
															"ID, "                                 +
															"UNIT_ID, "                            +
															"ACTIVE_VIEWERS";

	@Query(nativeQuery = true, value = SQL_QUERY_INCREASE_UNIT_VIEWERS_BY_UNIT_ID)
	UnitViewersEntity increaseUnitViewers(final Long unitId);


	String SQL_QUERY_DECREASE_UNIT_VIEWERS_BY_UNIT_ID = "UPDATE "                                  +
															"UNIT_VIEWERS "                        +
														"SET "                                     +
															"ACTIVE_VIEWERS = ACTIVE_VIEWERS - 1 " +
														"WHERE "                                   +
																"UNIT_ID = ?1 "                    +
															"AND ACTIVE_VIEWERS > 0 "              +
														"RETURNING "                               +
															"ID, "                                 +
															"UNIT_ID, "                            +
															"ACTIVE_VIEWERS";

	@Query(nativeQuery = true, value = SQL_QUERY_DECREASE_UNIT_VIEWERS_BY_UNIT_ID)
	UnitViewersEntity decreaseUnitViewers(final Long unitId);

}
