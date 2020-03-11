package it.support;

import org.apache.http.HttpStatus;
import org.joda.time.DateTimeUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeMachine {

	public static void setFixed(String time) {
		RestClient.from(null)
				.requestContent("{\"time\":\"" + time + "\"}")
				.put("/test-api/time")
				.emptyResponse()
				.statusCode(HttpStatus.SC_NO_CONTENT);
		DateTimeUtils.setCurrentMillisFixed(
				LocalDateTime.parse(time)
						.atZone(ZoneId.systemDefault())
						.toInstant()
						.toEpochMilli());
	}

	public static void setSystem() {
		RestClient.from(null)
				.delete("/test-api/time")
				.emptyResponse()
				.statusCode(HttpStatus.SC_NO_CONTENT);
		DateTimeUtils.setCurrentMillisSystem();
	}
}
