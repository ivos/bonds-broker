package com.bonds4all.bond;

import com.bonds4all.bond.dto.BondDtoCreate;
import com.bonds4all.bond_term.BondTerm;
import com.bonds4all.ip_address_request.IpAddressRequestService;
import com.bonds4all.time.FutureOrPresentValidator;
import com.bonds4all.time.TimeService;
import com.bonds4all.validation.Validation;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class BondService {

	private final BondRepository bondRepository;
	private final MapperFacade mapper;
	private final Validation validation;
	private final IpAddressRequestService ipAddressRequestService;
	private final BondTermLengthValidator bondTermLengthValidator;
	private final FutureOrPresentValidator futureOrPresentValidator;
	private final BondNightValidator bondNightValidator;
	private final BondCountValidator bondCountValidator;

	@Value("${bond.interestRate}")
	private String interestRate;

	@Autowired
	public BondService(BondRepository bondRepository, MapperFacade mapper, Validation validation,
			IpAddressRequestService ipAddressRequestService, BondTermLengthValidator bondTermLengthValidator,
			FutureOrPresentValidator futureOrPresentValidator, BondNightValidator bondNightValidator,
			BondCountValidator bondCountValidator) {
		this.bondRepository = bondRepository;
		this.mapper = mapper;
		this.validation = validation;
		this.ipAddressRequestService = ipAddressRequestService;
		this.bondTermLengthValidator = bondTermLengthValidator;
		this.futureOrPresentValidator = futureOrPresentValidator;
		this.bondNightValidator = bondNightValidator;
		this.bondCountValidator = bondCountValidator;
	}

	@Transactional
	public Bond create(String clientIp, BondDtoCreate dto) {
		validation.verifyBean(dto);

		Bond bond = mapper.map(dto, Bond.class);
		BondTerm bondTerm = mapper.map(dto, BondTerm.class);

		futureOrPresentValidator.verify(bond.getStartDate(), "startDate");
		bondTermLengthValidator.verify(bond, bondTerm);
		bondNightValidator.verify(bond);
		bondCountValidator.verify(clientIp);

		bond.setReference(UUID.randomUUID().toString());
		bond.getTerms().add(bondTerm);
		bondTerm.setBond(bond);
		bondTerm.setEffectiveTime(TimeService.now());
		bondTerm.setInterestRate(new BigDecimal(interestRate));

		bond = bondRepository.save(bond);
		ipAddressRequestService.create(clientIp);
		bondRepository.flush();

		return bond;
	}
}
