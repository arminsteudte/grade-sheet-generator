package de.as.garde_sheet_generator.dao;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.as.garde_sheet_generator.model.State;

public class SchulferienStateConverterImplTest {

	@Test
	public void convertState_WithEachEnumValue_ShouldReturnNotEmptyString() {

		// given
		SchulferienDeutschlandStateConverterImpl converter = new SchulferienDeutschlandStateConverterImpl();

		// when
		for (State state : State.values()) {
			String result = converter.convert(state);
			assertThat(result, is(not(isEmptyOrNullString())));
		}

	}

	@Test
	public void convertState_WithSachsenAnhalt_ShouldReturnExpectedString() {
		// given
		SchulferienDeutschlandStateConverterImpl converter = new SchulferienDeutschlandStateConverterImpl();
		
		// when
		final String actual = converter.convert(State.SACHSEN_ANHALT);
		
		// then
		assertThat(actual, is(equalTo("sachsen-anhalt")));
	}

}
