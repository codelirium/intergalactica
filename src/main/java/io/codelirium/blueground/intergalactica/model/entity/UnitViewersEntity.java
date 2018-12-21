package io.codelirium.blueground.intergalactica.model.entity;

import io.codelirium.blueground.intergalactica.model.entity.base.PersistableBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = UnitViewersEntity.TABLE_NAME)
@AttributeOverride(name = FIELD_NAME_ID, column = @Column(name = COLUMN_NAME_ID))
public class UnitViewersEntity extends PersistableBaseEntity<Long> implements Serializable {

	private static final long serialVersionUID = 2786135964919479386L;


	static final String TABLE_NAME = "UNIT_VIEWERS";


	@Column(name = "UNIT_ID", nullable = false)
	private Long unitId;

	@Column(name = "ACTIVE_VIEWERS", nullable = false)
	private Long activeViewers;

}
