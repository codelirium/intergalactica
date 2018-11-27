package io.codelirium.blueground.intergalactica.configuration.security.component;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter
@Component
public class TokenProperties {

	@Value("${security.token.secret}")
	private String secret;

	@Value("${security.token.maxAgeSeconds}")
	private Long maxAgeSeconds;

}
