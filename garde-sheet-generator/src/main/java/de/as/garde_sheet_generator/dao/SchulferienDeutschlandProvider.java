package de.as.garde_sheet_generator.dao;

import java.net.URI;
import java.time.Year;
import java.util.Optional;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.UriBuilder;

public class SchulferienDeutschlandProvider {

	private static final String YEAR_PLACEHOLDER = "year";
	private static final String STATE_PLACEHOLDER = "state";
	private Client rsClient;
	private final String urlTemplate;

	public SchulferienDeutschlandProvider(String providerUrlTemplate) {
		rsClient = ClientBuilder.newClient();
		this.urlTemplate = providerUrlTemplate;
	}

	public Optional<String> getHolidays(String state,Year year) {

		Optional<String> result = Optional.empty();

		URI uri = UriBuilder.fromUri(urlTemplate).resolveTemplate(YEAR_PLACEHOLDER, year).resolveTemplate(STATE_PLACEHOLDER, state).build();

		Builder requBuilder = rsClient.target(uri).request();

		try {
			String iCal = requBuilder.get(String.class);
			result = Optional.of(iCal);
		} catch (NotFoundException e) {
			// TODO Logging	
		}

		return result;
	}

}
