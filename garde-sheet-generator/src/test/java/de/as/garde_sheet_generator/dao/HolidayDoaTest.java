package de.as.garde_sheet_generator.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import biweekly.Biweekly;
import biweekly.ICalendar;
import de.as.garde_sheet_generator.model.Holiday;

public class HolidayDoaTest {

	private static final String I_CAL_DUMP_FILE_NAME = "iCal_Dump_2017.ics";
	private static final int KNOWN_YEAR = 2017;
	private static final int UNKNOWN_YEAR = 1888;
	private HolidayDao dao;

	@Before
	public void setUp() throws IOException {

		SchulferienOrgProvider holidayProviderMock = createHolidayProviderMock();
		this.dao = new HolidayDao(holidayProviderMock);

	}

	@After
	public void tearDown() {

		this.dao = null;

	}

	@Test
	public void getHolidays_WithTooOldYear_ShouldReturnEmptyList() {

		// given
		final Year year = Year.of(UNKNOWN_YEAR);

		// when
		List<Holiday> holidays = this.dao.getHolidays(year);

		// then
		assertTrue(holidays.isEmpty());

	}

	@Test
	public void getHolidays_WithValidYearAndFullYear_ShouldReturnAllHolidayTypesAtLeastOnce() {

		// given
		final Year year = Year.of(KNOWN_YEAR);

		// when
		List<Holiday> holidays = this.dao.getHolidays(year);

		// then
		assertFalse(holidays.isEmpty());

		String[] expectedHolidayDesc = {"Winterferien", "Osterferien", "Pfingstferien", "Sommerferien", "Herbstferien"};

		for (String desc : expectedHolidayDesc) {

			assertTrue(holidays.stream().anyMatch(h -> h.getDescription().equals(desc)));

		}

	}

	private SchulferienOrgProvider createHolidayProviderMock() throws IOException {
		SchulferienOrgProvider holidayProviderMock = mock(SchulferienOrgProvider.class);
		File dumpFile = new File(this.getClass().getResource("/" + I_CAL_DUMP_FILE_NAME).getFile());
		ICalendar iCalDump = Biweekly.parse(dumpFile).first();
		when(holidayProviderMock.getHolidays(Year.of(KNOWN_YEAR))).thenReturn(Optional.of(iCalDump.write()));
		when(holidayProviderMock.getHolidays(Year.of(UNKNOWN_YEAR))).thenReturn(Optional.empty());
		return holidayProviderMock;
	}
}
