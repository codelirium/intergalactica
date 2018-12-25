package io.codelirium.blueground.intergalactica.configuration.socket;

import io.codelirium.blueground.intergalactica.configuration.socket.component.WebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import javax.inject.Inject;

import static io.codelirium.blueground.intergalactica.controller.mapping.UrlMappings.API_PATH_ROOT;
import static io.codelirium.blueground.intergalactica.controller.mapping.UrlMappings.WEBSOCKET_ENDPOINT_UNIT_VIEWERS;
import static java.lang.String.format;
import static org.springframework.util.Assert.notNull;


@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

	public static final String WEBSOCKET_ENDPOINT = format("%s%s", API_PATH_ROOT, WEBSOCKET_ENDPOINT_UNIT_VIEWERS);


	@Inject
	private WebSocketHandler webSocketHandler;


	@Override
	public void registerWebSocketHandlers(final WebSocketHandlerRegistry registry) {

		notNull(registry, "The web socket registry cannot be null.");


		registry.addHandler(webSocketHandler, WEBSOCKET_ENDPOINT).setAllowedOrigins("*");

	}
}
