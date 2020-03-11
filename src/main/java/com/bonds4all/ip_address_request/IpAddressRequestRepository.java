package com.bonds4all.ip_address_request;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface IpAddressRequestRepository extends JpaRepository<IpAddressRequest, Long> {

	int countByEffectiveDateAndIp(LocalDate effectiveDate, String ip);
}
