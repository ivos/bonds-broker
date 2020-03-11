package com.bonds4all.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.bonds4all.validation.ValidationError;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class ErrorResponse {

	@NotNull
	private Date timestamp;

	@NotNull
	private int status;

	@NotNull
	private String error;

	private String path;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<ValidationError> errors;
}
