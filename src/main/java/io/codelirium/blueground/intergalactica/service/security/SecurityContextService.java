package io.codelirium.blueground.intergalactica.service.security;

import io.codelirium.blueground.intergalactica.model.dto.TokenDTO;
import io.codelirium.blueground.intergalactica.service.annotations.SecurityService;
import org.springframework.security.core.context.SecurityContextHolder;


@SecurityService
public class SecurityContextService {

	public TokenDTO getPrincipal() {

		return (TokenDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	}
}
