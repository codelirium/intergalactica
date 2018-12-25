package io.codelirium.blueground.intergalactica.configuration.resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Configuration
public class StaticResourceConfiguration extends WebMvcConfigurationSupport {

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/websocket/**")
				.addResourceLocations("classpath:/BOOT-INF/classes/static/websocket/");

	}
}
