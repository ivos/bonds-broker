package com.bonds4all.bond;

import com.bonds4all.bond_term.BondTerm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bonds")
@Data
@EqualsAndHashCode(of = "id")
public class Bond {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 100)
	private String name;

	@NotNull
	private LocalDate startDate;

	@NotNull
	private BigDecimal amount;

	@NotNull
	@Size(max = 36)
	private String reference;

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "bond")
	@OrderBy("id")
	@Valid
	@NotNull
	@Size(min = 1)
	private List<BondTerm> terms = new ArrayList<>();
}
