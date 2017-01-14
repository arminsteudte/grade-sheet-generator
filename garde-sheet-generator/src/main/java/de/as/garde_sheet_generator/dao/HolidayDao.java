package de.as.garde_sheet_generator.dao;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import de.as.garde_sheet_generator.model.Holiday;
import de.as.garde_sheet_generator.model.Holiday.HolidayType;

public class HolidayDao {

	private static final int INDEX_HOLIDAY_DESCRIPTION = 0;
	private final SchulferienOrgProvider holidayDataProvider;

	public HolidayDao(SchulferienOrgProvider provider) {
		this.holidayDataProvider = provider;
	}

	public List<Holiday> getHolidays(Year year, boolean halfSchoolYear) {

		List<Holiday> result = new ArrayList<>();

		Optional<String> optionalHolidays = this.holidayDataProvider.getHolidays(year);

		ICalendar cal = Biweekly.parse(optionalHolidays.orElse("")).first();

		if (cal != null) {
			result = cal.getEvents().stream().map(e -> convertEventToHoliday(e)).collect(Collectors.toList());
		}
		
		return result;
	}

	static Holiday convertEventToHoliday(VEvent ev) {

		String holidayDescr = ev.getSummary().getValue().split(" ")[INDEX_HOLIDAY_DESCRIPTION];
		HolidayType type = HolidayType.UNKNOWN;

		switch (holidayDescr) {
		case "Winterferien":
			type = HolidayType.WINTER;
			break;
		case "Osterferien":
			type = HolidayType.EASTER;
			break;
		case "Pfingstferien":
			type = HolidayType.PENTECOST;
			break;
		case "Sommerferien":
			type = HolidayType.SUMMER;
			break;
		case "Herbstferien":
			type = HolidayType.AUTUMN;
			break;
		case "Weihnachtsferien":
			type = HolidayType.CHRISTMAS;
			break;
		default:
			System.err.println("Unknown holiday: " + holidayDescr);
			break;
		}

		TimeZone localTimeZone = TimeZone.getDefault();
		LocalDate startDate = LocalDate.from(ev.getDateStart().getValue().toInstant().atZone(localTimeZone.toZoneId()));
		LocalDate endDate = LocalDate.from(ev.getDateEnd().getValue().toInstant().atZone(localTimeZone.toZoneId()));

		return new Holiday(type, startDate, endDate);

	}
}
