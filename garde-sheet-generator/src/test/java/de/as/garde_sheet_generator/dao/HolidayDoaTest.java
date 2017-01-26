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

import org.junit.Before;
import org.junit.Test;

import biweekly.Biweekly;
import biweekly.ICalendar;
import de.as.garde_sheet_generator.model.Holiday;
import de.as.garde_sheet_generator.model.State;

public class HolidayDoaTest {

	private static final String DEFAULT_STATE = "sachsen-anhalt";
	private static final String I_CAL_DUMP_FILE_NAME = "ferien_niedersachsen_2016.ics";
	private static final int KNOWN_YEAR = 2017;
	private static final int UNKNOWN_YEAR = 1888;
	private HolidayDao dao;

	@Before
	public void setUp() throws IOException {

		SchulferienDeutschlandProvider holidayProviderMock = createHolidayProviderMock();
		StateConverter converter = createStateConverterMock();
		this.dao = new HolidayDao(holidayProviderMock, converter);

	}

	@Test
	public void getHolidays_WithTooOldYear_ShouldReturnEmptyList() {

		// given
		final Year year = Year.of(UNKNOWN_YEAR);

		// when
		List<Holiday> holidays = this.dao.getHolidays(State.SACHSEN_ANHALT, year);

		// then
		assertTrue(holidays.isEmpty());

	}

	@Test
	public void getHolidays_WithValidYearAndFullYear_ShouldReturnAllHolidayTypesAtLeastOnce() {

		// given
		final Year year = Year.of(KNOWN_YEAR);

		// when
		List<Holiday> holidays = this.dao.getHolidays(State.SACHSEN_ANHALT, year);

		// then
		assertFalse(holidays.isEmpty());

		String[] expectedHolidayDesc = {"Winterferien", "Osterferien", "Sommerferien", "Herbstferien"};

		for (String desc : expectedHolidayDesc) {

			assertTrue(holidays.stream().anyMatch(h -> h.getDescription().equals(desc)));

		}

	}

	private SchulferienDeutschlandProvider createHolidayProviderMock() throws IOException {
		SchulferienDeutschlandProvider holidayProviderMock = mock(SchulferienDeutschlandProvider.class);
		File dumpFile = new File(this.getClass().getResource("/" + I_CAL_DUMP_FILE_NAME).getFile());
		ICalendar iCalDump = Biweekly.parse(dumpFile).first();
		when(holidayProviderMock.getHolidays(DEFAULT_STATE, Year.of(KNOWN_YEAR))).thenReturn(Optional.of(iCalDump.write()));
		when(holidayProviderMock.getHolidays(DEFAULT_STATE, Year.of(UNKNOWN_YEAR))).thenReturn(Optional.empty());
		return holidayProviderMock;
	}

	private StateConverter createStateConverterMock() {
		StateConverter converter = mock(SchulferienDeutschlandStateConverterImpl.class);
		when(converter.convert(State.SACHSEN_ANHALT)).thenReturn(DEFAULT_STATE);
		return converter;
	}
}
