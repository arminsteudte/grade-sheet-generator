package de.as.garde_sheet_generator.dao;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import de.as.garde_sheet_generator.model.Holiday;


public class HolidayDao {

	private final SchulferienOrgProvider holidayDataProvider;
	
	public HolidayDao(SchulferienOrgProvider provider) {
		this.holidayDataProvider = provider;
	}

	public List<Holiday> getHolidays(Year year, boolean halfSchoolYear) {

		return new ArrayList<>();
	}

}
