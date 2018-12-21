package io.codelirium.blueground.intergalactica.configuration.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;

import static java.lang.Runtime.getRuntime;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static reactor.core.scheduler.Schedulers.fromExecutor;


@Configuration
public class JDBCAsyncConfiguration {

	@Bean
	public Scheduler jdbcScheduler() {

		return fromExecutor(newFixedThreadPool(getRuntime().availableProcessors()));

	}
}
