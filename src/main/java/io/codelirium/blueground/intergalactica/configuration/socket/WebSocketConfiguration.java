package io.codelirium.blueground.intergalactica.configuration.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.codelirium.blueground.intergalactica.component.event.UnitViewCounterEventPublisher;
import io.codelirium.blueground.intergalactica.model.dto.event.UnitViewersEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;
import java.util.concurrent.Executor;

import static io.codelirium.blueground.intergalactica.controller.mapping.UrlMappings.API_PATH_ROOT;
import static io.codelirium.blueground.intergalactica.controller.mapping.UrlMappings.WEBSOCKET_ENDPOINT_UNIT_VIEWERS;
import static java.lang.String.format;
import static java.util.Collections.singletonMap;
import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static reactor.core.publisher.Flux.create;


@Configuration
public class WebSocketConfiguration {

	@Bean
	public Executor eventSubmitter() {

		return newSingleThreadExecutor();

	}


	@Bean
	public HandlerMapping handlerMapping(final WebSocketHandler webSocketHandler) {

		return new SimpleUrlHandlerMapping() {
			{
				setUrlMap(singletonMap(format("%s%s", API_PATH_ROOT, WEBSOCKET_ENDPOINT_UNIT_VIEWERS), webSocketHandler));
				setCorsConfigurations(singletonMap("*", new CorsConfiguration().applyPermitDefaultValues()));
				setOrder(10);
			}
		};
	}


	@Bean
	public WebSocketHandlerAdapter webSocketHandlerAdapter() {

		return new WebSocketHandlerAdapter();

	}


	@Bean
	public WebSocketHandler webSocketHandler(final ObjectMapper objectMapper, final UnitViewCounterEventPublisher eventPublisher) {

		final Flux<UnitViewersEvent> publish = create(eventPublisher).share();


		return session -> {

			final Flux<WebSocketMessage> messageFlux = publish.map(event -> {

				try {

					return objectMapper.writeValueAsString(event.getSource());

				} catch (final JsonProcessingException e) {

					throw new RuntimeException(e);

				}

			}).map(session::textMessage);


			return session.send(messageFlux);
		};
	}
}
