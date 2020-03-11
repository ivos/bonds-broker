package com.bonds4all.time;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-api/time")
@Profile("test")
public class TimeRestController {

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void setFixedTime(@RequestBody FixedTimeDto dto) {
		TimeService.setFixedTime(dto.getTime());
	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void setSystemTime() {
		TimeService.setSystemTime();
	}
}
