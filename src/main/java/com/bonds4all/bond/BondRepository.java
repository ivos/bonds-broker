package com.bonds4all.bond;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BondRepository extends JpaRepository<Bond, Long> {

	@Query("select b from Bond b"
			+ " join fetch b.terms"
			+ " where b.reference = :reference")
	Optional<Bond> findByReference(@Param("reference") String reference);
}
