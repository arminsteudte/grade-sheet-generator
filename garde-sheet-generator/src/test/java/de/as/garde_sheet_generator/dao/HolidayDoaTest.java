package de.as.garde_sheet_generator.dao;

import static org.junit.Assert.*;
import de.as.garde_sheet_generator.model.Holiday.HolidayType;

import java.time.Year;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.as.garde_sheet_generator.model.Holiday;

public class HolidayDoaTest {

	private HolidayDao dao;

	@Before
	public void setUp() {

		this.dao = new HolidayDao();

	}

	@After
	public void tearDown() {

		this.dao = null;

	}

	@Test
	public void getHolidays_WithTooOldYear_ShouldReturnEmptyList() {

		// given
		final Year year = Year.of(1888);
		final boolean halfSchoolYear = false;

		// when
		List<Holiday> holidays = this.dao.getHolidays(year, halfSchoolYear);

		// then
		assertTrue(holidays.isEmpty());

	}

	@Test
	public void getHolidays_WithValidYearAndFullYear_ShouldReturnAllHolidayTypesAtLeastOnce() {

		// given
		final Year year = Year.of(2017);
		final boolean halfSchoolYear = false;

		// when
		List<Holiday> holidays = this.dao.getHolidays(year, halfSchoolYear);
		
		// then
		assertFalse(holidays.isEmpty());
		for( HolidayType type : HolidayType.values()) {
			
			assertTrue(holidays.stream().anyMatch(h -> type == h.getType()));
			
		}

	}

}
