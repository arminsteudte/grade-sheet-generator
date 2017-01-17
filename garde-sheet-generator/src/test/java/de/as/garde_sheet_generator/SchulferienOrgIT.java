package de.as.garde_sheet_generator;

import static org.junit.Assert.*;

import java.net.URI;
import java.time.Year;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SchulferienOrgIT {

	private static Client rsClient;
	private static final String PROVIDER_URI_TEMPLATE = "http://www.schulferien.org/media/ical/deutschland/ferien_niedersachsen_{year}.ics";

	@BeforeClass
	public static void setUpJaxRSClient() {
		rsClient = ClientBuilder.newClient();
	}

	@Test
	public void getICalForYear_ShouldReturnICalAsString() {

		// given
		final int recentYear = Year.now().getValue();
		URI targetUri = UriBuilder.fromPath(PROVIDER_URI_TEMPLATE).build(recentYear);

		Builder requBuilder = rsClient.target(targetUri).request();

		// when
		String iCal = requBuilder.get(String.class);

		// then
		assertNotNull(iCal);
		assertFalse(StringUtils.isEmpty(iCal));

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
		URI targetUri = UriBuilder.fromPath(PROVIDER_URI_TEMPLATE).build(recentYear);
		
		Builder requBuilder = rsClient.target(targetUri).request();

		// when
		requBuilder.get(String.class);

	}

	@AfterClass
	public static void closeConnection() {
		rsClient.close();
	}

}
