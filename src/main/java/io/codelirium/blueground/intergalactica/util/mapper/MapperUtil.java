package io.codelirium.blueground.intergalactica.util.mapper;

import io.codelirium.blueground.intergalactica.model.dto.ColonistDTO;
import io.codelirium.blueground.intergalactica.model.dto.TokenDTO;
import io.codelirium.blueground.intergalactica.model.dto.UnitDTO;
import io.codelirium.blueground.intergalactica.model.entity.ColonistEntity;
import io.codelirium.blueground.intergalactica.model.entity.RoleEntity;
import io.codelirium.blueground.intergalactica.model.entity.UnitEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.Assert.notNull;


public class MapperUtil {


	private MapperUtil() { }


	public static TokenDTO toPlainToken(final String token) {

		notNull(token, "The token cannot be null.");


		return new TokenDTO(token);

	}


	public static TokenDTO toTokenDTO(final ColonistDTO colonistDTO, final String token) {

		notNull(colonistDTO, "The colonist DTO cannot be null.");
		notNull(token, "The token cannot be null.");


		return new TokenDTO.Builder()
				.with($ -> {
					$.username        = colonistDTO.getUsername();
					$.intergalacticId = colonistDTO.getIntergalacticId();
					$.passwordHash    = colonistDTO.getPasswordHash();
					$.token           = token;
					$.authorities     = colonistDTO.getAuthorities();
				})
				.build();
	}


	public static ColonistDTO toColonistDTO(final ColonistEntity colonistEntity) {

		notNull(colonistEntity, "The colonist entity cannot be null.");


		return new ColonistDTO.Builder()
				.with($ -> {
					$.username        = colonistEntity.getUsername();
					$.intergalacticId = colonistEntity.getIntergalacticId();
					$.passwordHash    = colonistEntity.getPasswordHash();
					$.authorities     = toAuthorities(colonistEntity.getRoles());
				})
				.build();
	}


	public static ColonistEntity toColonistEntity(final ColonistDTO colonistDTO) {

		notNull(colonistDTO, "The colonist DTO cannot be null.");


		return new ColonistEntity.Builder()
				.with($ -> {
					$.username        = colonistDTO.getUsername();
					$.intergalacticId = colonistDTO.getIntergalacticId();
					$.passwordHash    = colonistDTO.getPasswordHash();
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
}
