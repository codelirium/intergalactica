package io.codelirium.blueground.intergalactica.service.security;

import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.codelirium.blueground.intergalactica.model.dto.TokenDTO;
import io.codelirium.blueground.intergalactica.service.annotations.SecurityService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import javax.inject.Inject;

import static io.codelirium.blueground.intergalactica.service.security.TokenService.FIELD_ROLE;
import static io.codelirium.blueground.intergalactica.service.security.TokenService.FIELD_USER;
import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.Assert.notNull;


@SecurityService
public class TokenAuthenticationService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

	private TokenService tokenService;


	@Inject
	public TokenAuthenticationService(final TokenService tokenService) {

		this.tokenService = tokenService;

	}


	@Override
	public UserDetails loadUserDetails(final PreAuthenticatedAuthenticationToken authentication) throws UsernameNotFoundException {

		notNull(authentication, "The authentication cannot be null.");


		if (nonNull(authentication.getPrincipal()) && authentication.getPrincipal() instanceof String && authentication.getCredentials() instanceof String) {

			DecodedJWT token;

			try {

				token = tokenService.decode((String) authentication.getPrincipal());

			} catch (final InvalidClaimException e) {

				throw new UsernameNotFoundException("The token is not valid.");

			}


			return new TokenDTO.Builder()
					.with($ -> {
						$.username        = token.getSubject();
						$.intergalacticId = token.getClaim(FIELD_USER).asString();
						$.passwordHash    = (String) authentication.getCredentials();
						$.token           = token.getToken();
						$.authorities     = token.getClaim(FIELD_ROLE)
														.asList(String.class)
														.stream()
															.map(SimpleGrantedAuthority::new)
															.collect(toList());
					})
					.build();

		} else {

			throw new UsernameNotFoundException(format("Could not retrieve user details for: [%s]", authentication.getPrincipal()));

		}
	}
}