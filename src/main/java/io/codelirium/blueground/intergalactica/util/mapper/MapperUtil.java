package io.codelirium.blueground.intergalactica.util.mapper;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.codelirium.blueground.intergalactica.model.dto.ColonistDTO;
import io.codelirium.blueground.intergalactica.model.dto.TokenProfileDTO;
import io.codelirium.blueground.intergalactica.model.dto.UnitDTO;
import io.codelirium.blueground.intergalactica.model.dto.UnitViewersDTO;
import io.codelirium.blueground.intergalactica.model.entity.ColonistEntity;
import io.codelirium.blueground.intergalactica.model.entity.RoleEntity;
import io.codelirium.blueground.intergalactica.model.entity.UnitEntity;
import io.codelirium.blueground.intergalactica.model.entity.UnitViewersEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static io.codelirium.blueground.intergalactica.service.security.TokenService.FIELD_ROLE;
import static io.codelirium.blueground.intergalactica.service.security.TokenService.FIELD_USER;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.Assert.notNull;


public class MapperUtil {


	private MapperUtil() { }


	public static TokenProfileDTO toPlainToken(final String token) {

		notNull(token, "The token cannot be null.");


		return new TokenProfileDTO(token);

	}


	public static TokenProfileDTO toTokenDTO(final DecodedJWT token, final String credentials) {

		notNull(token, "The decoded JWT cannot be null.");
		notNull(credentials, "The credentials cannot be null.");


		return new TokenProfileDTO.Builder()
				.with($ -> {
					$.profileName  = token.getSubject();
					$.uniqueId     = token.getClaim(FIELD_USER).asString();
					$.passwordHash = credentials;
					$.token        = token.getToken();
					$.authorities  = token.getClaim(FIELD_ROLE)
												.asList(String.class)
																.stream()
																	.map(SimpleGrantedAuthority::new)
																	.collect(toList());
				})
				.build();
	}


	public static TokenProfileDTO toTokenDTO(final ColonistDTO colonistDTO, final String token) {

		notNull(colonistDTO, "The colonist DTO cannot be null.");
		notNull(token, "The token cannot be null.");


		return new TokenProfileDTO.Builder()
				.with($ -> {
					$.profileName  = colonistDTO.getProfileName();
					$.uniqueId     = colonistDTO.getIntergalacticId();
					$.passwordHash = colonistDTO.getPassword();
					$.token        = token;
					$.authorities  = colonistDTO.getAuthorities();
				})
				.build();
	}


	public static ColonistDTO toColonistDTO(final ColonistEntity colonistEntity) {

		notNull(colonistEntity, "The colonist entity cannot be null.");


		return new ColonistDTO.Builder()
				.with($ -> {
					$.profileName     = colonistEntity.getProfileName();
					$.intergalacticId = colonistEntity.getIntergalacticId();
					$.password        = colonistEntity.getPasswordHash();
					$.authorities     = toAuthorities(colonistEntity.getRoles());
				})
				.build();
	}


	public static ColonistEntity toColonistEntity(final ColonistDTO colonistDTO) {

		notNull(colonistDTO, "The colonist DTO cannot be null.");


		return new ColonistEntity.Builder()
				.with($ -> {
					$.profileName     = colonistDTO.getProfileName();
					$.intergalacticId = colonistDTO.getIntergalacticId();
					$.passwordHash    = colonistDTO.getPassword();
					$.roles           = toRoles($.intergalacticId, colonistDTO.getAuthorities());
				})
				.build();
	}


	public static List<SimpleGrantedAuthority> toAuthorities(final List<RoleEntity> roles) {

		notNull(roles, "The roles cannot be null.");


		return roles
				.stream()
					.map(role -> new SimpleGrantedAuthority(role.getRole()))
					.collect(toList());
	}


	public static List<RoleEntity> toRoles(final String intergalacticId, final List<SimpleGrantedAuthority> authorities) {

		notNull(intergalacticId, "The intergalactic id cannot be null.");
		notNull(authorities, "The authorities cannot be null.");


		return authorities
					.stream()
						.map(authority -> new RoleEntity(intergalacticId, authority.getAuthority()))
						.collect(toList());
	}


	public static Collection<UnitDTO> toUnitDTOs(final List<UnitEntity> unitEntities) {

		notNull(unitEntities, "The unit entities cannot be null.");


		return unitEntities
						.stream()
							.map(unitEntity -> new UnitDTO.Builder()
									.with($ -> {
										$.id                 = unitEntity.getId();
										$.image              = unitEntity.getImage();
										$.title              = unitEntity.getTitle();
										$.region             = unitEntity.getRegion();
										$.description        = unitEntity.getDescription();
										$.cancellationPolicy = unitEntity.getCancellationPolicy();
										$.priceAmount        = unitEntity.getPriceAmount();
										$.priceCurrency      = unitEntity.getPriceCurrency();
										$.score              = unitEntity.getScore();
									})
									.build())
							.collect(toCollection(LinkedList::new));
	}


	public static UnitViewersDTO toUnitViewersDTO(final UnitViewersEntity unitViewersEntity) {

		notNull(unitViewersEntity, "The unit viewers entity cannot be null.");


		return new UnitViewersDTO.Builder()
				.with($ -> {
					$.unitId        = unitViewersEntity.getUnitId();
					$.activeViewers = unitViewersEntity.getActiveViewers();
				})
				.build();
	}
}
