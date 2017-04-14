package de.as.garde_sheet_generator.model;

public class Pupil {

	private final String firstName;
	private final String lastName;

	public Pupil(final String firstName, final String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

}
