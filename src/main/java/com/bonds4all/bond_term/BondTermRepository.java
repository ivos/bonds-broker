package com.bonds4all.bond_term;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BondTermRepository extends JpaRepository<BondTerm, Long> {

	Optional<BondTerm> findFirstByBond_ReferenceOrderByIdDesc(String reference);
}
