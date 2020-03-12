package it.bond.get;

import it.support.RestClient;
import net.sf.lightair.LightAir;
import net.sf.lightair.annotation.Setup;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(LightAir.class)
@Setup
public class GetBondIT {

	private String getPath(String reference) {
		return "/api/bonds/" + reference;
	}

	private void ok(String response) {
		RestClient.from(this)
				.get(getPath("reference-1"))
				.responseName(response)
				.statusCode(HttpStatus.SC_OK);
	}

	@Test
	public void full() {
		ok("full");
	}

	@Test
	public void notFound() {
		RestClient.from(this)
				.get(getPath("reference-666"))
				.responseName("notFound", ctx -> ctx
						.set("$.timestamp", "REPLACED"))
				.statusCode(HttpStatus.SC_NOT_FOUND);
	}
}
