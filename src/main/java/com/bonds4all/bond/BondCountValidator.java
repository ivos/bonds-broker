package com.bonds4all.bond;

import com.bonds4all.ip_address_request.IpAddressRequestService;
import com.bonds4all.validation.Validation;
import com.bonds4all.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BondCountValidator {

	private final Validation validation;
	private final IpAddressRequestService ipAddressRequestService;

	@Value("${bond.ipDailyLimit}")
	private int ipDailyLimit;

	@Autowired
	public BondCountValidator(Validation validation, IpAddressRequestService ipAddressRequestService) {
		this.validation = validation;
		this.ipAddressRequestService = ipAddressRequestService;
	}

	public void verify(String ip) {
		int previous = ipAddressRequestService.countPrevious(ip);
		if (previous >= ipDailyLimit) {
			validation.reject(new ValidationError(null, null,
					"legal requirement violated: too many bonds from IP address"));
		}
	}
}
