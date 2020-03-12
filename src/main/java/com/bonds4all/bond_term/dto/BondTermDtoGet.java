package com.bonds4all.bond_term.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BondTermDtoGet {
	private LocalDateTime effectiveTime;
	private LocalDate endDate;
	private BigDecimal interestRate;
}
