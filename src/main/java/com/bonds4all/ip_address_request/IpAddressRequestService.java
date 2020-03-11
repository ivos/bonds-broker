package com.bonds4all.ip_address_request;

import com.bonds4all.time.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class IpAddressRequestService {

	private final IpAddressRequestRepository ipAddressRequestRepository;

	@Autowired
	public IpAddressRequestService(IpAddressRequestRepository ipAddressRequestRepository) {
		this.ipAddressRequestRepository = ipAddressRequestRepository;
	}

	@Transactional
	public void create(String clientIp) {
		IpAddressRequest ipAddressRequest = new IpAddressRequest();
		ipAddressRequest.setEffectiveDate(TimeService.now().toLocalDate());
		ipAddressRequest.setIp(clientIp);

		ipAddressRequestRepository.save(ipAddressRequest);
	}

	@Transactional(readOnly = true)
	public int countPrevious(String ip) {
		LocalDate today = TimeService.now().toLocalDate();
		return ipAddressRequestRepository.countByEffectiveDateAndIp(today, ip);
	}
}
