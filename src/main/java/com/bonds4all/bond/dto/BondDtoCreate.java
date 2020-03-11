package com.bonds4all.bond.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BondDtoCreate {

	@NotNull
	@Size(max = 100)
	private String name;

	@NotNull
	private LocalDate startDate;

	@NotNull
	private LocalDate endDate;

	@NotNull
	@DecimalMin(value = "0", inclusive = false)
	private BigDecimal amount;
}
