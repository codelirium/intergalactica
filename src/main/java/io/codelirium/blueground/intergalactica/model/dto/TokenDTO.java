package io.codelirium.blueground.intergalactica.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Data
@ToString
@EqualsAndHashCode
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenDTO extends ColonistDTO implements UserDetails, Serializable {

	private static final long serialVersionUID = -6031979811450129600L;


	private String token;


	public TokenDTO(final String token) {

		this.token = token;

	}


	public TokenDTO(final String username,
					final String intergalacticId,
					final String passwordHash,
					final String token,
					final List<SimpleGrantedAuthority> authorities) {

		super(username, intergalacticId, passwordHash, authorities);

		this.token = token;

	}


	@Override
	public String getUsername() {

		return super.getUsername();

	}


	public String getIntergalacticId() {

		return super.getIntergalacticId();

	}


	@Override
	@JsonIgnore
	public String getPassword() {

		return super.getPasswordHash();

	}


	@JsonIgnore
	public String getPasswordHash() {

		return super.getPasswordHash();

	}


	@Override
	public List<SimpleGrantedAuthority> getAuthorities() {

		return super.getAuthorities();

	}


	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {

		return true;

	}


	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {

		return true;

	}


	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {

		return true;

	}


	@Override
	@JsonIgnore
	public boolean isEnabled() {

		return true;

	}


	public static class Builder {

		public String username;

		public String intergalacticId;

		public String passwordHash;

		public String token;

		public List<SimpleGrantedAuthority> authorities;


		public Builder with(final Consumer<Builder> builderFunction) {

			builderFunction.accept(this);

			return this;
		}


		public TokenDTO build() {

			return new TokenDTO(username, intergalacticId, passwordHash, token, authorities);

		}
	}
}
