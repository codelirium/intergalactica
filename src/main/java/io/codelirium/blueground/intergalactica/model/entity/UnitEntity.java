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
@Table(name = UnitEntity.TABLE_NAME)
@AttributeOverride(name = FIELD_NAME_ID, column = @Column(name = COLUMN_NAME_ID))
public class UnitEntity extends PersistableBaseEntity<Long> implements Serializable {

	private static final long serialVersionUID = -2589170530443760063L;


	static final String TABLE_NAME = "UNITS";


	@Column(name = "IMAGE", nullable = false)
	private String image;

	@Column(name = "TITLE", nullable = false)
	private String title;

	@Column(name = "REGION", nullable = false)
	private String region;

	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	@Column(name = "CANCELLATION_POLICY", nullable = false)
	private String cancellationPolicy;

	@Column(name = "PRICE_AMOUNT", nullable = false)
	private Double priceAmount;

	@Column(name = "PRICE_CCY", nullable = false)
	private String priceCurrency;

	@Column(name = "SCORE", nullable = false)
	private Integer score;

}
