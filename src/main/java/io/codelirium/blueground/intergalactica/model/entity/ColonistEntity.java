package io.codelirium.blueground.intergalactica.model.entity;

import io.codelirium.blueground.intergalactica.model.entity.base.PersistableBaseEntity;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

import static io.codelirium.blueground.intergalactica.model.entity.base.PersistableBaseEntity.FIELD_NAME_ID;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;


@Data
@Entity
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = ColonistEntity.TABLE_NAME)
@AttributeOverride(name = FIELD_NAME_ID, column = @Column(name = ColonistEntity.COLUMN_NAME_ID))
public class ColonistEntity extends PersistableBaseEntity<Long> implements Serializable {

	private static final long serialVersionUID = -2589170530443760064L;


			static final String TABLE_NAME                   = "COLONISTS";
			static final String COLUMN_NAME_ID               = "ID";
	private static final String COLUMN_NAME_INTERGALACTIC_ID = "INTERGALACTIC_ID";


	@Column(name = "USERNAME", nullable = false)
	private String username;

	@Column(name = COLUMN_NAME_INTERGALACTIC_ID, nullable = false)
	private String intergalacticId;

	@Column(name = "PASSWORD_HASH", nullable = false)
	private String passwordHash;

	@OneToMany(fetch = EAGER, orphanRemoval = true, cascade = ALL)
	@JoinColumn(name = COLUMN_NAME_INTERGALACTIC_ID, referencedColumnName = RoleEntity.COLUMN_NAME_INTERGALACTIC_ID)
	private List<RoleEntity> roles;


	public static class Builder {

		public String username;

		public String intergalacticId;

		public String passwordHash;

		public List<RoleEntity> roles;


		public Builder with(final Consumer<Builder> builderFunction) {

			builderFunction.accept(this);

			return this;
		}


		public ColonistEntity build() {

			return new ColonistEntity(username, intergalacticId, passwordHash, roles);

		}
	}
}
