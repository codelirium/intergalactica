package io.codelirium.blueground.intergalactica.repository;

import io.codelirium.blueground.intergalactica.model.entity.ColonistEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.Optional;


@Repository
@Transactional
public interface ColonistRepository extends JpaRepository<ColonistEntity, Long> {

	@Cacheable(value = "colonist-by-intergalactic-id", keyGenerator = "keyGenerator")
	Optional<ColonistEntity> findByIntergalacticId(final String intergalacticId);

}
