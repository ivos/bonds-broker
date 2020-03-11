package com.bonds4all.bond;

import com.bonds4all.time.TimeService;
import com.bonds4all.validation.Validation;
import com.bonds4all.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalTime;

@Component
public class BondNightValidator {

	private final Validation validation;

	@Value("${bond.nightStart}")
	private String nightStart;

	@Value("${bond.nightEnd}")
	private String nightEnd;

	@Value("${bond.nightAmountLimit}")
	private String nightAmountLimit;

	@Autowired
	public BondNightValidator(Validation validation) {
		this.validation = validation;
	}

	public void verify(Bond bond) {
		LocalTime now = TimeService.now().toLocalTime();
		BigDecimal amount = bond.getAmount();
		boolean amountGtLimit = amount != null && amount.compareTo(new BigDecimal(nightAmountLimit)) >= 1;
		boolean night = now.isBefore(LocalTime.parse(nightEnd)) || now.isAfter(LocalTime.parse(nightStart));
		if (amountGtLimit && night) {
			validation.reject(new ValidationError("amount", amount,
					"legal requirement violated: time and amount"));
		}
	}
}
