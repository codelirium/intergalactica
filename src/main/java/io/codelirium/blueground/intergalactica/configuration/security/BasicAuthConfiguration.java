package io.codelirium.blueground.intergalactica.configuration.security;

import io.codelirium.blueground.intergalactica.configuration.security.component.AccessAuthenticationEntryPoint;
import io.codelirium.blueground.intergalactica.service.facade.ColonistDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import javax.inject.Inject;

import static io.codelirium.blueground.intergalactica.controller.mapping.UrlMappings.API_ENDPOINT_TOKENS;
import static io.codelirium.blueground.intergalactica.controller.mapping.UrlMappings.API_PATH_ROOT;
import static java.lang.String.format;
import static org.springframework.security.config.BeanIds.AUTHENTICATION_MANAGER;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Order(2)
@Configuration
public class BasicAuthConfiguration extends WebSecurityConfigurerAdapter {


	private PasswordEncoder passwordEncoder;

	private ColonistDetailsService colonistDetailsService;

	private AccessAuthenticationEntryPoint accessAuthenticationEntryPoint;


	@Inject
	public BasicAuthConfiguration(final PasswordEncoder passwordEncoder,
								  final ColonistDetailsService colonistDetailsService,
								  final AccessAuthenticationEntryPoint accessAuthenticationEntryPoint) {

		this.passwordEncoder = passwordEncoder;

		this.colonistDetailsService = colonistDetailsService;

		this.accessAuthenticationEntryPoint = accessAuthenticationEntryPoint;

	}


	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		final String tokensEndpoint = format("%s%s", API_PATH_ROOT, API_ENDPOINT_TOKENS);

		http
			.antMatcher(tokensEndpoint)
				.authorizeRequests()
					.anyRequest()
						.authenticated()
			.and()
				.httpBasic()
					.authenticationEntryPoint(accessAuthenticationEntryPoint)
			.and()
				.exceptionHandling()
					.defaultAuthenticationEntryPointFor(accessAuthenticationEntryPoint, new AntPathRequestMatcher(tokensEndpoint))
			.and()
				.sessionManagement()
					.sessionCreationPolicy(STATELESS);
	}


	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(colonistDetailsService).passwordEncoder(passwordEncoder);

	}


	@Override
	@Bean(name = AUTHENTICATION_MANAGER)
	public AuthenticationManager authenticationManagerBean() throws Exception {

		return super.authenticationManagerBean();

	}
}
