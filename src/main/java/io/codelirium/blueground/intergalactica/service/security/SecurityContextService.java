package io.codelirium.blueground.intergalactica.service.security;

import io.codelirium.blueground.intergalactica.model.dto.TokenProfileDTO;
import io.codelirium.blueground.intergalactica.service.annotation.SecurityService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;


@SecurityService
public class SecurityContextService {

	public Optional<Authentication> getAuthentication() {

		final SecurityContext context = SecurityContextHolder.getContext();


		return nonNull(context) ? ofNullable(context.getAuthentication()) : empty();
	}


	public Optional<TokenProfileDTO> getPrincipal() {

		final Optional<Authentication> optionalAuthentication = getAuthentication();


		return optionalAuthentication.isPresent() ? ofNullable((TokenProfileDTO) optionalAuthentication.get().getPrincipal()) : empty();
	}
}
