package io.codelirium.blueground.intergalactica;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import static io.codelirium.blueground.intergalactica.configuration.db.DatabaseConfiguration.CORE_PACKAGE;
import static java.lang.Boolean.FALSE;


@SpringBootApplication
@ComponentScan({CORE_PACKAGE})
public class IntergalacticApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {

		return application.sources(IntergalacticApplication.class);

	}


	public static void main(final String[] args) {

		new SpringApplicationBuilder(IntergalacticApplication.class)
				.logStartupInfo(FALSE)
				.run(args);

	}
}
