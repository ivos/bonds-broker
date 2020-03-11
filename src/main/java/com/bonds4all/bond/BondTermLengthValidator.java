package com.bonds4all.bond;

import com.bonds4all.bond_term.BondTerm;
import com.bonds4all.validation.Validation;
import com.bonds4all.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class BondTermLengthValidator {

	private final Validation validation;

	@Value("${bond.minimumTermLengthYears}")
	private int minimumTermLengthYears;

	@Autowired
	public BondTermLengthValidator(Validation validation) {
		this.validation = validation;
	}

	public void verify(Bond bond, BondTerm bondTerm) {
		LocalDate startDate = bond.getStartDate();
		LocalDate endDate = bondTerm.getEndDate();
		if (startDate != null && endDate != null) {
			if (Period.between(startDate, endDate).getYears() < minimumTermLengthYears) {
				validation.reject(new ValidationError("endDate", endDate, "bond term length is too short"));
			}
		}
	}
}
