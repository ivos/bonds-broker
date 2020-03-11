package com.bonds4all.time;

import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TimeService {

	private static Clock clock;

	static {
		setSystemTime();
	}

	static void setFixedTime(LocalDateTime time) {
		clock = Clock.fixed(time.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
	}

	static void setSystemTime() {
		clock = Clock.systemDefaultZone();
	}

	public static LocalDateTime now() {
		return LocalDateTime.now(clock);
	}
}
