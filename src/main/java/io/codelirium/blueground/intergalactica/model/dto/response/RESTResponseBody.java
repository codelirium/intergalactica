package io.codelirium.blueground.intergalactica.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Data
@ToString
@EqualsAndHashCode
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RESTResponseBody implements Serializable {

	private static final long serialVersionUID = -5640756286027710938L;


	private String status;

	private String type;

}
