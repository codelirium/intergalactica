package io.codelirium.blueground.intergalactica.component.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.codelirium.blueground.intergalactica.model.dto.event.UnitViewersEvent;
import io.codelirium.blueground.intergalactica.model.entity.UnitViewersEntity;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.FluxSink;
import javax.inject.Inject;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import static io.codelirium.blueground.intergalactica.configuration.socket.WebSocketConfiguration.BROKER_BASE_TOPIC;
import static io.codelirium.blueground.intergalactica.util.mapper.MapperUtil.toUnitViewersDTO;
import static java.lang.String.format;
import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static org.springframework.util.Assert.notNull;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;


@Component
public class UnitViewCounterEventPublisher implements ApplicationListener<UnitViewersEvent>, Consumer<FluxSink<UnitViewersEvent>> {

	private static final String BROKER_TOPIC_UNIT_VIEWERS = "/unit-viewers-counter";


	private final Executor eventSubmitter = newSingleThreadExecutor();

	private final BlockingQueue<UnitViewersEvent> queue = new LinkedBlockingQueue<>();

	private ObjectMapper objectMapper;

	private SimpMessagingTemplate simpMessagingTemplate;


	@Inject
	public UnitViewCounterEventPublisher(final ObjectMapper objectMapper, final SimpMessagingTemplate simpMessagingTemplate) {

		this.objectMapper = objectMapper;

		this.simpMessagingTemplate = simpMessagingTemplate;

	}


	@Override
	public void onApplicationEvent(final UnitViewersEvent event) {

		notNull(event, "The event cannot be null.");


		this.queue.offer(event);


		try {

			final String message = objectMapper.writeValueAsString(toUnitViewersDTO((UnitViewersEntity) event.getSource()));

			simpMessagingTemplate.convertAndSend(format("%s%s", BROKER_BASE_TOPIC, BROKER_TOPIC_UNIT_VIEWERS), message);

		} catch (final Exception e) {

			rethrowRuntimeException(e);

		}
	}


	@Override
	public void accept(final FluxSink<UnitViewersEvent> sink) {

		notNull(sink, "The sink cannot be null.");


		this.eventSubmitter.execute(() -> {

			while (true)

				try {

					sink.next(queue.take());

				} catch (final InterruptedException e) {

					rethrowRuntimeException(e);

				}
		});
	}
}
