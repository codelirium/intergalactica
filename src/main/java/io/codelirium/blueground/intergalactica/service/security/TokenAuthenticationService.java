package io.codelirium.blueground.intergalactica.service.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.codelirium.blueground.intergalactica.service.annotation.SecurityService;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import javax.inject.Inject;

import static io.codelirium.blueground.intergalactica.util.mapper.MapperUtil.toTokenDTO;
import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static org.springframework.util.Assert.notNull;


@SecurityService
public class TokenAuthenticationService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

	private TokenService tokenService;


	@Inject
	public TokenAuthenticationService(final TokenService tokenService) {

		this.tokenService = tokenService;

	}


	@Override
	public UserDetails loadUserDetails(final PreAuthenticatedAuthenticationToken authentication) throws AuthenticationException {

		notNull(authentication, "The authentication cannot be null.");


		if (nonNull(authentication.getPrincipal()) && authentication.getPrincipal() instanceof String && authentication.getCredentials() instanceof String) {

			DecodedJWT token;

			try {

				token = tokenService.decode((String) authentication.getPrincipal());

			} catch (final JWTVerificationException e) {

				throw new UsernameNotFoundException("The token is not valid.");

			}


			return toTokenDTO(token, (String) authentication.getCredentials());

		} else {

			throw new UsernameNotFoundException(format("Could not retrieve user details for: [%s]", authentication.getPrincipal()));

		}
	}
}
