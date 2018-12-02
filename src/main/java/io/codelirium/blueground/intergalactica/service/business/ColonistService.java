package io.codelirium.blueground.intergalactica.service.business;

import io.codelirium.blueground.intergalactica.model.dto.ColonistDTO;
import io.codelirium.blueground.intergalactica.repository.ColonistRepository;
import io.codelirium.blueground.intergalactica.service.annotation.BusinessService;
import io.codelirium.blueground.intergalactica.util.mapper.MapperUtil;
import org.slf4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.inject.Inject;
import java.util.Optional;

import static io.codelirium.blueground.intergalactica.model.entity.RoleEntity.Roles.USER;
import static io.codelirium.blueground.intergalactica.util.mapper.MapperUtil.toColonistEntity;
import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static java.util.Optional.of;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.util.Assert.notNull;


@BusinessService
public class ColonistService {

	private static final Logger LOGGER = getLogger(ColonistService.class);


	private PasswordEncoder passwordEncoder;

	private ColonistRepository colonistRepository;


	@Inject
	public ColonistService(final PasswordEncoder passwordEncoder, final ColonistRepository colonistRepository) {

		this.passwordEncoder = passwordEncoder;

		this.colonistRepository = colonistRepository;

	}


	public ColonistDTO signUp(final ColonistDTO colonistDTO) {

		notNull(colonistDTO, "The colonist DTO cannot be null.");


		LOGGER.debug(format("Registering new colonist with intergalactic id: [%s]", colonistDTO.getIntergalacticId()));


		colonistDTO.setPassword(passwordEncoder.encode(colonistDTO.getPassword()));
		colonistDTO.setAuthorities(singletonList(new SimpleGrantedAuthority(USER.name())));


		return of(colonistRepository.saveAndFlush(toColonistEntity(colonistDTO)))
														.map(MapperUtil::toColonistDTO)
														.orElseThrow(() -> new RuntimeException("Cannot sign-up new colonist."));

	}


	public Optional<ColonistDTO> findByIntergalacticId(final String intergalacticId) {

		notNull(intergalacticId, "The intergalactic id cannot be null.");


		LOGGER.debug(format("Retrieving existing colonist with intergalactic id: [%s]", intergalacticId));


		return colonistRepository.findByIntergalacticId(intergalacticId).map(MapperUtil::toColonistDTO);

	}
}
