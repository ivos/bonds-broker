package it.bond.buy;

import it.support.RestClient;
import it.support.TimeMachine;
import net.sf.lightair.LightAir;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.matchesRegex;

@RunWith(LightAir.class)
@Setup
@Verify
public class BuyBondIT {

	private static final String PATH = "/api/bonds";

	@Before
	public void setUp() {
		TimeMachine.setFixed("2020-03-04T10:11:12");
	}

	@After
	public void tearDown() {
		TimeMachine.setSystem();
	}

	private void ok(String request) {
		RestClient.from(this)
				.requestName(request)
				.post(PATH)
				.emptyResponse()
				.statusCode(HttpStatus.SC_CREATED)
				.location(matchesRegex("^http://localhost:8080/api/bonds/[0-9a-f\\-]{36}$"));
	}

	@Test
	public void full() {
		ok("full");
	}

	@Test
	public void future() {
		ok("future");
	}

	private void validation(String request) {
		validation(request, request);
	}

	private void validation(String request, String response) {
		RestClient.from(this)
				.requestName(request)
				.post(PATH)
				.responseName(response, ctx -> ctx
						.set("$.timestamp", "REPLACED"))
				.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
	}

	@Test
	@Verify("BuyBondIT.xml")
	public void empty() {
		validation("empty");
	}

	@Test
	@Verify("BuyBondIT.xml")
	public void longValues() {
		validation("longValues");
	}

	@Test
	@Verify("BuyBondIT.xml")
	public void amountZero() {
		validation("amountZero");
	}

	@Test
	@Verify("BuyBondIT.xml")
	public void shortTerm() {
		validation("shortTerm");
	}

	@Test
	@Verify("BuyBondIT.xml")
	public void startYesterday() {
		validation("startYesterday");
	}

	@Test
	@Verify("BuyBondIT.xml")
	public void nightAboveLimit() {
		TimeMachine.setFixed("2020-03-04T02:11:12");
		validation("nightAboveLimit");
	}

	@Test
	public void nightBelowLimit() {
		ok("nightBelowLimit");
	}

	@Test
	@Verify("tooManyBonds.xml")
	public void tooManyBonds() {
		validation("minimal", "tooManyBonds");
	}

	@Test
	@Setup("bondCountBelowLimit.xml")
	public void bondCountBelowLimit() {
		ok("minimal");
	}

	private void badRequest(String request) {
		RestClient.from(this)
				.requestName(request)
				.post(PATH)
				.statusCode(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	@Verify("BuyBondIT.xml")
	public void invalidDate() {
		badRequest("invalidDate");
	}

	@Test
	@Verify("BuyBondIT.xml")
	public void invalidAmount() {
		badRequest("invalidAmount");
	}
}
