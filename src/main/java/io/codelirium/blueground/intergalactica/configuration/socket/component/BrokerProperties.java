package io.codelirium.blueground.intergalactica.configuration.socket.component;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter
@Component
public class BrokerProperties {

	@Value("${rabbitmq-stomp.port}")
	private Integer port;

	@Value("${rabbitmq-stomp.hostname}")
	private String hostname;

	@Value("${rabbitmq-stomp.username}")
	private String username;

	@Value("${rabbitmq-stomp.password}")
	private String password;

}
