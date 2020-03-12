package com.bonds4all.bond.dto;

import com.bonds4all.bond_term.dto.BondTermDtoGet;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class BondDtoGet {
	private String name;
	private LocalDate startDate;
	private BigDecimal amount;
	private List<BondTermDtoGet> terms;
}
