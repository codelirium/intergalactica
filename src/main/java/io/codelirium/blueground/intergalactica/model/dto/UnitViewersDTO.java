package io.codelirium.blueground.intergalactica.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.codelirium.blueground.intergalactica.model.dto.base.IdentifiableDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.io.Serializable;
import java.util.function.Consumer;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnitViewersDTO extends IdentifiableDTO<Long> implements Serializable {

	private static final long serialVersionUID = -5759892580913496796L;


	private Long unitId;

	private Long activeViewers;


	public static class Builder {

		public Long unitId;

		public Long activeViewers;


		public Builder with(final Consumer<Builder> builderFunction) {

			builderFunction.accept(this);

			return this;
		}


		public UnitViewersDTO build() {

			return new UnitViewersDTO(unitId, activeViewers);

		}
	}
}
