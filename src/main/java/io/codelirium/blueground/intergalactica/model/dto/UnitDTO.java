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
public class UnitDTO extends IdentifiableDTO<Long> implements Serializable {

	private static final long serialVersionUID = 301098680944551306L;


	private String image;

	private String title;

	private String region;

	private String description;

	private String cancellationPolicy;

	private Double priceAmount;

	private String priceCurrency;

	private Integer score;


	public static class Builder {

		public Long id;

		public String image;

		public String title;

		public String region;

		public String description;

		public String cancellationPolicy;

		public Double priceAmount;

		public String priceCurrency;

		public Integer score;


		public Builder with(final Consumer<Builder> builderFunction) {

			builderFunction.accept(this);

			return this;
		}


		public UnitDTO build() {

			final UnitDTO unitDTO = new UnitDTO(image, title, region, description, cancellationPolicy, priceAmount, priceCurrency, score);

			unitDTO.setId(id);


			return unitDTO;
		}
	}
}
