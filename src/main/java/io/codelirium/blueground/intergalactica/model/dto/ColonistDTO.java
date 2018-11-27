package io.codelirium.blueground.intergalactica.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.function.Consumer;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ColonistDTO {

	private String username;

	private String intergalacticId;

	private String passwordHash;

	private List<SimpleGrantedAuthority> authorities;


	public static class Builder {

		public String username;

		public String intergalacticId;

		public String passwordHash;

		public List<SimpleGrantedAuthority> authorities;


		public Builder with(final Consumer<Builder> builderFunction) {

			builderFunction.accept(this);

			return this;
		}


		public ColonistDTO build() {

			return new ColonistDTO(username, intergalacticId, passwordHash, authorities);

		}
	}
}
