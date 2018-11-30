package io.codelirium.blueground.intergalactica.service.facade;

import io.codelirium.blueground.intergalactica.model.dto.ColonistDTO;
import io.codelirium.blueground.intergalactica.service.annotation.FacadeService;
import io.codelirium.blueground.intergalactica.service.business.ColonistService;
import io.codelirium.blueground.intergalactica.service.security.TokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import javax.inject.Inject;
import java.util.Optional;

import static io.codelirium.blueground.intergalactica.util.mapper.MapperUtil.toTokenDTO;
import static java.lang.String.format;
import static org.springframework.util.Assert.notNull;


@FacadeService
public class ColonistDetailsService implements UserDetailsService {

	private TokenService tokenService;

	private ColonistService colonistService;


	@Inject
	public ColonistDetailsService(final TokenService tokenService, final ColonistService colonistService) {

		this.tokenService = tokenService;

		this.colonistService = colonistService;

	}


	@Override
	public UserDetails loadUserByUsername(final String intergalacticId) throws UsernameNotFoundException {

		notNull(intergalacticId, "The intergalacticId id cannot be null.");


		final Optional<ColonistDTO> optionalColonistDTO = colonistService.findByIntergalacticId(intergalacticId);

		if (!optionalColonistDTO.isPresent()) {

			throw new UsernameNotFoundException(format("Colonist with intergalactic id: #%s cannot be found.", intergalacticId));

		}


		return toTokenDTO(optionalColonistDTO.get(), tokenService.encode(optionalColonistDTO.get()));
	}
}
