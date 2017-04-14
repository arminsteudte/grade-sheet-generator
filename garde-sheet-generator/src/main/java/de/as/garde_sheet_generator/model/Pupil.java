package de.as.garde_sheet_generator.model;

public class Pupil {

	private String firstName;
	private String lastName;

	public Pupil(final String firstName, final String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Pupil() {
		this.firstName = "";
		this.lastName = "";
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
