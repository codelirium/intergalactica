package io.codelirium.blueground.intergalactica.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Data
@ToString
@EqualsAndHashCode
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RESTSearchResponseBody<S> extends RESTResponseBody {

	private static final String JACKSON_FIELD_NAME_SEARCH = "search";


	@JsonProperty(JACKSON_FIELD_NAME_SEARCH)
	private S pagedSearchDTO;


	public S getPagedSearchDTO() {

		return pagedSearchDTO;

	}


	public void setPagedSearchDTO(final S pagedSearchDTO) {

		this.pagedSearchDTO = pagedSearchDTO;

	}
}
