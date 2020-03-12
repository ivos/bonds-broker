package com.bonds4all.bond_term;

import com.bonds4all.bond.Bond;
import com.bonds4all.bond.BondTermLengthValidator;
import com.bonds4all.bond.dto.BondDtoEndDate;
import com.bonds4all.time.TimeService;
import com.bonds4all.validation.Validation;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class BondTermService {

	private final BondTermRepository bondTermRepository;
	private final MapperFacade mapper;
	private final Validation validation;
	private final BondTermLengthValidator bondTermLengthValidator;

	@Value("${bond.extensionDiscountRate}")
	private String extensionDiscountRate;

	@Autowired
	public BondTermService(BondTermRepository bondTermRepository, MapperFacade mapper,
			Validation validation, BondTermLengthValidator bondTermLengthValidator) {
		this.bondTermRepository = bondTermRepository;
		this.mapper = mapper;
		this.validation = validation;
		this.bondTermLengthValidator = bondTermLengthValidator;
	}

	@Transactional
	public void updateEndDate(String reference, BondDtoEndDate dto) {
		validation.verifyBean(dto);

		BondTerm last = getLastTermForBond(reference);
		Bond bond = last.getBond();

		BondTerm bondTerm = mapper.map(dto, BondTerm.class);
		bondTerm.setBond(bond);
		bondTerm.setEffectiveTime(TimeService.now());
		bondTerm.setInterestRate(last.getInterestRate());

		discountBondOnExtension(bondTerm, last.getEndDate());

		bondTermLengthValidator.verify(bond, bondTerm);

		bondTermRepository.saveAndFlush(bondTerm);
	}

	private BondTerm getLastTermForBond(String reference) {
		return bondTermRepository.findFirstByBond_ReferenceOrderByIdDesc(reference)
				.orElseThrow(EntityNotFoundException::new);
	}

	private void discountBondOnExtension(BondTerm bondTerm, LocalDate lastEndDate) {
		LocalDate newEndDate = bondTerm.getEndDate();
		if (newEndDate.isAfter(lastEndDate)) {
			bondTerm.setInterestRate(
					bondTerm.getInterestRate()
							.multiply(new BigDecimal(extensionDiscountRate)));
		}
	}
}
