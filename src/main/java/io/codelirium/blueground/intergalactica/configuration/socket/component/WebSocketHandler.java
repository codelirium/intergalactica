package io.codelirium.blueground.intergalactica.configuration.socket.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.codelirium.blueground.intergalactica.component.event.UnitViewCounterEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import javax.inject.Inject;

import static org.springframework.util.Assert.notNull;
import static reactor.core.publisher.Flux.create;


@Component
public class WebSocketHandler extends AbstractWebSocketHandler {

	private ObjectMapper objectMapper;

	private UnitViewCounterEventPublisher eventPublisher;


	@Inject
	public WebSocketHandler(final ObjectMapper objectMapper, final UnitViewCounterEventPublisher eventPublisher) {

		this.objectMapper = objectMapper;

		this.eventPublisher = eventPublisher;

	}


	@Override
	public void afterConnectionEstablished(final WebSocketSession session) {

		notNull(session, "The web socket session cannot be null.");


		create(eventPublisher).toStream().forEach(event -> {

			try {

				session.sendMessage(new TextMessage(objectMapper.writeValueAsString(event.getSource())));

			} catch (final Exception e) {

				throw new RuntimeException(e);

			}
		});
	}
}
