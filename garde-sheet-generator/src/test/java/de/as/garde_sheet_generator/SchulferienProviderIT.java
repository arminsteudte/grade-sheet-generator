package de.as.garde_sheet_generator;

import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;

import java.net.URI;
import java.time.Year;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.UriBuilder;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SchulferienProviderIT {

	private static Client rsClient;
	private static final String PROVIDER_URI_TEMPLATE = "http://www.schulferien-deutschland.net/ical/ferien-{state}-{year}.ics";
	private static final String DEFAULT_STATE = "niedersachsen";

	@BeforeClass
	public static void setUpJaxRSClient() {
		rsClient = ClientBuilder.newClient();
	}

	@Test
	public void getICalForYear_ShouldReturnICalAsString() {

		// given
		final int recentYear = Year.now().getValue();
		
		URI targetUri = UriBuilder.fromPath(PROVIDER_URI_TEMPLATE).build(DEFAULT_STATE, recentYear);

		Builder requBuilder = rsClient.target(targetUri).request();

		// when
		String iCal = requBuilder.get(String.class);

		// then
		assertThat(iCal, is(not(isEmptyOrNullString())));

		/**
		 * Create Dump for iCal
		 */
		// ICalendar cal = Biweekly.parse(iCal).first();
		// try {
		// cal.write(new FileOutputStream("iCal_Dump_2017.ics"));
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	@Test(expected = NotFoundException.class)
	public void getICalForYear_WithUnknownYear_ShouldThrowNotFoundException() {

		// given
		final int recentYear = 1888;
		URI targetUri = UriBuilder.fromPath(PROVIDER_URI_TEMPLATE).build(DEFAULT_STATE, recentYear);
		
		Builder requBuilder = rsClient.target(targetUri).request();

		// when
		requBuilder.get(String.class);

	}

	@AfterClass
	public static void closeConnection() {
		rsClient.close();
	}

}
