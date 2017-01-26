package de.as.garde_sheet_generator.model;

import java.time.LocalDate;

public class Holiday {

	private final String description;
	private final LocalDate start;
	private final LocalDate end;

	public Holiday(String desc, LocalDate start, LocalDate end) {
		this.description = desc;
		this.start = start;
		this.end = end;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getStart() {
		return start;
	}

	public LocalDate getEnd() {
		return end;
	}

}
