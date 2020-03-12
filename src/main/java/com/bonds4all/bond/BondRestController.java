package com.bonds4all.bond;

import com.bonds4all.bond.dto.BondDtoCreate;
import com.bonds4all.bond.dto.BondDtoEndDate;
import com.bonds4all.bond_term.BondTermService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/api/bonds")
@Slf4j
public class BondRestController {

	private final BondService bondService;
	private final BondTermService bondTermService;

	@Autowired
	public BondRestController(BondService bondService, BondTermService bondTermService) {
		this.bondService = bondService;
		this.bondTermService = bondTermService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> buyBond(
			@RequestBody BondDtoCreate dto,
			ServletRequest servletRequest,
			UriComponentsBuilder uriBuilder) {
		log.info("Buy bond, {}", dto);
		String clientIp = servletRequest.getRemoteAddr();

		Bond bond = bondService.create(clientIp, dto);

		URI locationUri = uriBuilder.path("/api/bonds/{reference}")
				.buildAndExpand(bond.getReference())
				.toUri();
		return ResponseEntity
				.created(locationUri)
				.build();
	}

	@RequestMapping(path = "/{reference}", method = RequestMethod.PATCH)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void adjustBond(
			@PathVariable String reference,
			@RequestBody BondDtoEndDate dto) {
		log.info("Adjust bond, {}, {}", reference, dto);
		bondTermService.updateEndDate(reference, dto);
	}
}
