package de.as.garde_sheet_generator.dao;

import static org.junit.Assert.*;
import static org.hamcrest.collection.IsEmptyCollection.emptyCollectionOf;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.xml.sax.SAXException;

import de.as.garde_sheet_generator.model.Pupil;

public class PupilDaoTest {

	@Test(expected = NullPointerException.class)
	public void extractFromXlsx_WithNullAsFile_ShouldThrowNullpointerException() throws URISyntaxException, IOException, SAXException, InvalidFormatException {

		File xmlConfig = new File(getClass().getResource("/" + "pupilJxlsTemplate.xml").toURI());
		// given
		PupilDao pupilDao = new PupilDao(xmlConfig );
		
		// when
		pupilDao.extractFromXlsx(null);
		// then
		throw new IllegalStateException("NullPointerException has not occured.");

	}
	
	@Test
	public void extractFromXlsx_WithFile_ShouldReturnNonEmptyListOfPupils() throws URISyntaxException, IOException, SAXException, InvalidFormatException {
		
		// given
		File xlsxFile = new File(getClass().getResource("/" + "Klasse_11e.xlsx").toURI());
		File jxlsPupilConfig = new File(getClass().getResource("/" + "pupilJxlsTemplate.xml").toURI());
		PupilDao pupilDao = new PupilDao(jxlsPupilConfig);		
		
		// when
		List<Pupil> pupils = pupilDao.extractFromXlsx(xlsxFile);
		
		// then
		assertThat(pupils, is(not(emptyCollectionOf(Pupil.class))));
		assertThat(pupils.size(), is(30));
		assertThat(pupils.get(3).getFirstName(), is("Corvin"));
		
	}

}
