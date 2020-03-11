package com.bonds4all.time;

import com.bonds4all.validation.Validation;
import com.bonds4all.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FutureOrPresentValidator {

	private final Validation validation;

	@Autowired
	public FutureOrPresentValidator(Validation validation) {
		this.validation = validation;
	}

	public void verify(LocalDate date, String path) {
		if (date != null && date.isBefore(TimeService.now().toLocalDate())) {
			validation.reject(new ValidationError(path, date, "must be a date in the present or in the future"));
		}
	}
}
