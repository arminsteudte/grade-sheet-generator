package de.as.garde_sheet_generator.dao;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.as.garde_sheet_generator.model.Pupil;

public class SheetDaoIT {

	private SheetDao sheetWriter;
	
	@Before
	public void setUp() {
		this.sheetWriter = new SheetDao();
	}
	
	@Test
	public void writePupilsAndLessonDatesToExcel_ShouldReadDataFromExcelWithResultTemplate() throws IOException {
		
		// given
		final Pupil p1 = new Pupil("Peter", "Griffin");
		final Pupil p2 = new Pupil("Paul", "Allan");
		final Pupil p3 = new Pupil("Mary", "Preusse");
		final List<Pupil> pupils = ImmutableList.of(p1, p2, p3);
		
		final LocalDate d1 = LocalDate.of(2017, 2, 1);
		final LocalDate d2 = d1.plusDays(1);
		final LocalDate d3 = d2.plusDays(1);
		final List<LocalDate> lessonDates = ImmutableList.of(d1, d2, d3);
		
		final File output = File.createTempFile("gradeSheet", ".xlsx");
		// todo File descriptor for reader template file
		
		// when
		
		
		// then
		
	}
	
	@After
	public void tearDown() {
		this.sheetWriter = null;
	}
}