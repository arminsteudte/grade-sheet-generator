package de.as.garde_sheet_generator.dao;

import java.net.URI;
import java.time.Year;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.UriBuilder;

class SchulferienOrgProvider {

	private static final String YEAR_PLACEHOLDER = "year";
	private Client rsClient;
	private final String urlTemplate;

	public SchulferienOrgProvider(String providerUrlTemplate) {
		rsClient = ClientBuilder.newClient();
		this.urlTemplate = providerUrlTemplate;
	}

	public String getHolidays(Year year) {
		
		URI uri = UriBuilder.fromUri(urlTemplate).resolveTemplate(YEAR_PLACEHOLDER, year).build();
		
		Builder requBuilder = rsClient.target(uri).request();

		String iCal = requBuilder.get(String.class);
		
		return iCal;
	}

}
