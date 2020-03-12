package it.bond.adjust;

import it.support.RestClient;
import net.sf.lightair.LightAir;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(LightAir.class)
@Setup
@Verify
public class AdjustBondIT {

	private String getPath(String reference) {
		return "/api/bonds/" + reference;
	}

	private void ok(String request) {
		RestClient.from(this)
				.requestName(request)
				.patch(getPath("reference-1"))
				.emptyResponse()
				.statusCode(HttpStatus.SC_NO_CONTENT);
	}

	@Test
	public void shortenTerm() {
		ok("shortenTerm");
	}

	@Test
	public void extendTerm() {
		ok("extendTerm");
	}

	private void validation(String request) {
		RestClient.from(this)
				.requestName(request)
				.patch(getPath("reference-1"))
				.responseName(request, ctx -> ctx
						.set("$.timestamp", "REPLACED"))
				.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
	}

	@Test
	@Verify("AdjustBondIT.xml")
	public void empty() {
		validation("empty");
	}

	@Test
	@Verify("AdjustBondIT.xml")
	public void shortTerm() {
		validation("shortTerm");
	}

	@Test
	@Verify("AdjustBondIT.xml")
	public void invalidDate() {
		RestClient.from(this)
				.requestName("invalidDate")
				.patch(getPath("reference-1"))
				.statusCode(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	@Verify("AdjustBondIT.xml")
	public void notFound() {
		RestClient.from(this)
				.requestName("shortenTerm")
				.patch(getPath("reference-666"))
				.responseName("notFound", ctx -> ctx
						.set("$.timestamp", "REPLACED"))
				.statusCode(HttpStatus.SC_NOT_FOUND);
	}
}
