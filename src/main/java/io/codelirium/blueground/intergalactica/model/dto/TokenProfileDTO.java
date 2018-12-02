package io.codelirium.blueground.intergalactica.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenProfileDTO implements UserDetails, Serializable {

	private static final long serialVersionUID = -6031979811450129600L;


	private String profileName;

	private String uniqueId;

	private String password;

	private String token;

	private List<SimpleGrantedAuthority> authorities;


	public TokenProfileDTO(final String token) {

		this.token = token;

	}


	@Override
	@JsonIgnore
	public String getUsername() {

		return uniqueId;

	}


	@Override
	@JsonIgnore
	public String getPassword() {

		return password;

	}


	@Override
	public List<SimpleGrantedAuthority> getAuthorities() {

		return authorities;

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

		public String profileName;

		public String uniqueId;

		public String passwordHash;

		public String token;

		public List<SimpleGrantedAuthority> authorities;


		public Builder with(final Consumer<Builder> builderFunction) {

			builderFunction.accept(this);

			return this;
		}


		public TokenProfileDTO build() {

			return new TokenProfileDTO(profileName, uniqueId, passwordHash, token, authorities);

		}
	}
}
