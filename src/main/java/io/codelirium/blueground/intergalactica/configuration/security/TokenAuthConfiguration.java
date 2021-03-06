package io.codelirium.blueground.intergalactica.configuration.security;

import io.codelirium.blueground.intergalactica.configuration.security.component.AccessAuthenticationEntryPoint;
import io.codelirium.blueground.intergalactica.configuration.security.filter.TokenAuthenticationFilter;
import io.codelirium.blueground.intergalactica.service.security.TokenAuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import javax.inject.Inject;

import static io.codelirium.blueground.intergalactica.configuration.socket.WebSocketConfiguration.WEBSOCKET_ENDPOINT;
import static io.codelirium.blueground.intergalactica.controller.mapping.UrlMappings.API_ENDPOINT_COLONISTS;
import static io.codelirium.blueground.intergalactica.controller.mapping.UrlMappings.API_PATH_ROOT;
import static java.lang.String.format;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Order(3)
@Configuration
public class TokenAuthConfiguration extends WebSecurityConfigurerAdapter {


	private TokenAuthenticationService tokenAuthenticationService;

	private AccessAuthenticationEntryPoint accessAuthenticationEntryPoint;


	@Inject
	public TokenAuthConfiguration(final TokenAuthenticationService tokenAuthenticationService,
								  final AccessAuthenticationEntryPoint accessAuthenticationEntryPoint) {

		this.tokenAuthenticationService = tokenAuthenticationService;

		this.accessAuthenticationEntryPoint = accessAuthenticationEntryPoint;

	}


	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		final String colonistsEndpoint = format("%s%s", API_PATH_ROOT, API_ENDPOINT_COLONISTS);

		http
			.antMatcher(colonistsEndpoint)
				.authorizeRequests()
					.antMatchers(POST, colonistsEndpoint)
						.anonymous()
							.anyRequest()
								.authenticated()
			.and()
				.antMatcher(WEBSOCKET_ENDPOINT)
					.authorizeRequests()
						.anyRequest()
							.authenticated()
			.and()
				.exceptionHandling()
					.defaultAuthenticationEntryPointFor(accessAuthenticationEntryPoint, new AntPathRequestMatcher(colonistsEndpoint))
			.and()
				.addFilterBefore(authFilter(), RequestHeaderAuthenticationFilter.class)
				.authenticationProvider(preAuthProvider())
				.sessionManagement()
					.sessionCreationPolicy(STATELESS)
			.and()
				.csrf()
					.disable();
	}


	@Inject
	public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {

		auth.authenticationProvider(preAuthProvider());

	}


	@Bean
	public TokenAuthenticationFilter authFilter() throws Exception {

		final TokenAuthenticationFilter filter = new TokenAuthenticationFilter();

		filter.setAuthenticationManager(authenticationManager());


		return filter;
	}


	@Bean
	public PreAuthenticatedAuthenticationProvider preAuthProvider() throws Exception {

		final PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();

		provider.setPreAuthenticatedUserDetailsService(tokenAuthenticationService);


		return provider;
	}
}
