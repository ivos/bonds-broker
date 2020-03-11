package com.bonds4all.bond_term;

import com.bonds4all.bond.Bond;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bond_terms")
@Data
@EqualsAndHashCode(of = "id")
public class BondTerm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "bond_id")
	@ManyToOne
	@NotNull
	private Bond bond;

	@NotNull
	private LocalDateTime effectiveTime;

	@NotNull
	private LocalDate endDate;

	@NotNull
	private BigDecimal interestRate;
}
