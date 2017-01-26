package de.as.garde_sheet_generator.dao;

import de.as.garde_sheet_generator.model.State;

/**
 * Interface describing converters to translate between enum and string
 * representation for each provider request.
 * 
 * @author Armin
 *
 */
public interface StateConverter {

	/**
	 * Converts enum to string for building the provider request.
	 * @param state 
	 * @return Provider's string representation.
	 */
	public String convert(State state);

}
