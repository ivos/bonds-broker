package com.bonds4all.bond.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class BondDtoEndDate {

	@NotNull
	private LocalDate endDate;
}
