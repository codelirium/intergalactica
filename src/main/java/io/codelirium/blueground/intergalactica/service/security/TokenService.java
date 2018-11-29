package io.codelirium.blueground.intergalactica.service.security;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.codelirium.blueground.intergalactica.configuration.security.component.TokenProperties;
import io.codelirium.blueground.intergalactica.model.dto.ColonistDTO;
import io.codelirium.blueground.intergalactica.service.annotations.SecurityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import static com.auth0.jwt.JWT.create;
import static com.auth0.jwt.JWT.require;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.systemDefault;
import static java.util.Date.from;
import static org.springframework.util.Assert.notNull;


@SecurityService
public class TokenService {

	public static final String FIELD_USER  = "user";
	public static final String FIELD_ROLE  = "role";


	private TokenProperties tokenProperties;

	private String issuer;

	private Algorithm algorithm;

	private JWTVerifier verifier;


	@Inject
	public TokenService(final TokenProperties tokenProperties, @Value("${spring.application.name}") final String issuer) throws UnsupportedEncodingException {

		this.tokenProperties = tokenProperties;

		this.issuer = issuer;

		this.algorithm = Algorithm.HMAC512(tokenProperties.getSecret());

		this.verifier = require(algorithm).acceptExpiresAt(0).build();

	}


	public String encode(final ColonistDTO colonistDTO) {

		notNull(colonistDTO, "The colonist DTO cannot be null.");


		final LocalDateTime now = now();


		return create()
				.withIssuer(issuer)
				.withSubject(colonistDTO.getUsername())
				.withIssuedAt(from(now.atZone(systemDefault()).toInstant()))
				.withExpiresAt(from(now.plusSeconds(tokenProperties.getMaxAgeSeconds()).atZone(systemDefault()).toInstant()))
				.withArrayClaim(FIELD_ROLE, colonistDTO.getAuthorities()
																	.stream()
																		.map(SimpleGrantedAuthority::getAuthority)
																		.toArray(String[]::new))
				.withClaim(FIELD_USER, colonistDTO.getIntergalacticId())
				.sign(algorithm);
	}


	public DecodedJWT decode(final String token) throws JWTVerificationException {

		notNull(token, "The token cannot be null.");


		return this.verifier.verify(token);
	}
}
