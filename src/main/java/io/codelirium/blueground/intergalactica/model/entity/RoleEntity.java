package io.codelirium.blueground.intergalactica.model.entity;

import io.codelirium.blueground.intergalactica.model.entity.base.PersistableBaseEntity;
import lombok.*;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

import static io.codelirium.blueground.intergalactica.model.entity.base.PersistableBaseEntity.COLUMN_NAME_ID;
import static io.codelirium.blueground.intergalactica.model.entity.base.PersistableBaseEntity.FIELD_NAME_ID;


@Data
@Entity
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = RoleEntity.TABLE_NAME)
@AttributeOverride(name = FIELD_NAME_ID, column = @Column(name = COLUMN_NAME_ID))
public class RoleEntity extends PersistableBaseEntity<Long> implements Serializable {

	private static final long serialVersionUID = -6416235917914445528L;


	static final String TABLE_NAME                   = "ROLES";
	static final String COLUMN_NAME_INTERGALACTIC_ID = "INTERGALACTIC_ID";


	@Column(name = COLUMN_NAME_INTERGALACTIC_ID, nullable = false)
	private String intergalacticId;

	@Column(name = "ROLE", nullable = false)
	private String role;

}
