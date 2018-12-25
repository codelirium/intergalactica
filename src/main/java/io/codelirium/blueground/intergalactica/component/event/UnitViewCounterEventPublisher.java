package io.codelirium.blueground.intergalactica.component.event;

import io.codelirium.blueground.intergalactica.model.dto.event.UnitViewersEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.FluxSink;
import javax.inject.Inject;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import static org.springframework.util.Assert.notNull;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;


@Component
public class UnitViewCounterEventPublisher implements ApplicationListener<UnitViewersEvent>, Consumer<FluxSink<UnitViewersEvent>> {

	private final Executor eventSubmitter;

	private final BlockingQueue<UnitViewersEvent> queue = new LinkedBlockingQueue<>();


	@Inject
	public UnitViewCounterEventPublisher(final Executor eventSubmitter) {

		this.eventSubmitter = eventSubmitter;

	}


	@Override
	public void onApplicationEvent(final UnitViewersEvent event) {

		notNull(event, "The event cannot be null.");


		this.queue.offer(event);

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
