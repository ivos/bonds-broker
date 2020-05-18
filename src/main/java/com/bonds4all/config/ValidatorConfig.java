package com.bonds4all.config;

import com.bonds4all.time.TimeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Configuration
public class ValidatorConfig {

	@Bean
	public Validator validator(TimeService timeService) {
		ValidatorFactory factory = Validation.byDefaultProvider()
				.configure()
				.clockProvider(timeService)
				.buildValidatorFactory();
		return factory.getValidator();
	}
}
