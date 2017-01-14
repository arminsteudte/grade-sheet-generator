package de.as.garde_sheet_generator.model;

import java.time.LocalDate;

public class Holiday {

	private final HolidayType type;
	private final LocalDate start;
	private final LocalDate end;

	public Holiday(HolidayType type, LocalDate start, LocalDate end) {
		this.type = type;
		this.start = start;
		this.end = end;
	}

	public HolidayType getType() {
		return type;
	}

	public LocalDate getStart() {
		return start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public enum HolidayType {
		
		// TODO Is UNKNOWN a good idea? Compare unit test.
		WINTER("Winterferien"), EASTER("Osterferien"), PENTECOST("Pfingstferien"), SUMMER("Sommerferien"), AUTUMN(
				"Herbstferien"), CHRISTMAS("Weihnachtsferien"), UNKNOWN("Unbekannt");

		private final String fullDescription;

		private HolidayType(String fullDesc) {
			this.fullDescription = fullDesc;
		}

		public String getFullDescription() {
			return fullDescription;
		}

	}

}
