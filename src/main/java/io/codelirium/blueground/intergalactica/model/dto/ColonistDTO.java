package io.codelirium.blueground.intergalactica.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.io.Serializable;
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
public class ColonistDTO implements Serializable {

	private static final long serialVersionUID = 8673864055237191066L;


	private String profileName;

	private String intergalacticId;

	private String password;

	private List<SimpleGrantedAuthority> authorities;


	@JsonIgnore
	public String getPassword() {

		return password;

	}


	@JsonProperty
	public void setPassword(final String password) {

		this.password = password;

	}


	@JsonProperty
	public List<SimpleGrantedAuthority> getAuthorities() {

		return authorities;

	}


	@JsonIgnore
	public void setAuthorities(List<SimpleGrantedAuthority> authorities) {

		this.authorities = authorities;

	}


	public static class Builder {

		public String profileName;

		public String intergalacticId;

		public String password;

		public List<SimpleGrantedAuthority> authorities;


		public Builder with(final Consumer<Builder> builderFunction) {

			builderFunction.accept(this);

			return this;
		}


		public ColonistDTO build() {

			return new ColonistDTO(profileName, intergalacticId, password, authorities);

		}
	}
}
