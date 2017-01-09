package de.as.garde_sheet_generator.model;

public class Holiday {
	
	private HolidayType type;
	
	public Holiday(HolidayType type) {
		this.type = type;
	}
	
		
	public HolidayType getType() {
		return type;
	}




	public enum HolidayType {
		WINTER,
		EASTER,
		PENTECOST,
		SUMMER,
		AUTUMN,
		CHRISTMAS
	}

}
