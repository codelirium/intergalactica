package io.codelirium.blueground.intergalactica.configuration.socket;

import io.codelirium.blueground.intergalactica.configuration.socket.component.BrokerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import javax.inject.Inject;

import static io.codelirium.blueground.intergalactica.controller.mapping.UrlMappings.API_PATH_ROOT;
import static io.codelirium.blueground.intergalactica.controller.mapping.UrlMappings.API_PATH_WEBSOCKET;
import static java.lang.String.format;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

	public static final String WEBSOCKET_ENDPOINT = format("%s%s", API_PATH_ROOT, API_PATH_WEBSOCKET);

	public static final String BROKER_BASE_TOPIC = "/topic";


	@Inject
	private BrokerProperties properties;


	@Override
	public void configureMessageBroker(final MessageBrokerRegistry registry) {

		registry
			.enableStompBrokerRelay(BROKER_BASE_TOPIC)
			.setRelayHost(properties.getHostname())
			.setRelayPort(properties.getPort())
			.setSystemLogin(properties.getUsername())
			.setSystemPasscode(properties.getPassword())
			.setClientLogin(properties.getUsername())
			.setClientPasscode(properties.getPassword());

	}


	@Override
	public void registerStompEndpoints(final StompEndpointRegistry registry) {

		registry
			.addEndpoint(WEBSOCKET_ENDPOINT)
			.setAllowedOrigins("*");

	}
}
