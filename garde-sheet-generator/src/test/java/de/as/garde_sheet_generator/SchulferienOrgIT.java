package de.as.garde_sheet_generator;

import static org.junit.Assert.*;

import java.time.Year;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;

import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SchulferienOrgIT {

	private static Client rsClient;
	
	@BeforeClass
	public static void setUpJaxRSClient() {
		 rsClient = ClientBuilder.newClient();
	}
	
	@Test
	public void getICalForYear_ShouldReturnICalAsString() {
		
		// given
		final int recentYear = Year.now().getValue(); 
		
		Builder requBuilder = rsClient.target("http://www.schulferien.org/media/ical").path("deutschland/ferien_niedersachsen_"+recentYear+".ics").request();
		
		// when
		String iCal = requBuilder.get(String.class);
		
		// then
		assertNotNull(iCal);
		assertFalse(StringUtils.isEmpty(iCal));
		
		/**
		 * Create Dump for iCal
		 */
//		ICalendar cal = Biweekly.parse(iCal).first();
//		try {
//			cal.write(new FileOutputStream("iCal_Dump_2017.ics"));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	@Test(expected=NotFoundException.class)
	public void getICalForYear_WithUnknownYear_ShouldThrowNotFoundException() {
		
		// given
		final int recentYear = 1888; 
		
		Builder requBuilder = rsClient.target("http://www.schulferien.org/media/ical").path("deutschland/ferien_niedersachsen_"+recentYear+".ics").request();
		
		// when
		requBuilder.get(String.class);
		
	}
	
	@AfterClass
	public static void closeConnection() {
		rsClient.close();
	}

}
