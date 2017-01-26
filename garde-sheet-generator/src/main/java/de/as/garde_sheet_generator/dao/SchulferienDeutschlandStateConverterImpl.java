package de.as.garde_sheet_generator.dao;

import de.as.garde_sheet_generator.model.State;

public class SchulferienDeutschlandStateConverterImpl implements StateConverter {

	@Override
	public String convert(State state) {
		return state.toString().toLowerCase().replace('_', '-');
	}

}
