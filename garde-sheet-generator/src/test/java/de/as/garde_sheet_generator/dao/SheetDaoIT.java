package de.as.garde_sheet_generator.dao;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReadStatus;
import org.jxls.reader.XLSReader;
import org.xml.sax.SAXException;

import com.google.common.collect.ImmutableList;

import de.as.garde_sheet_generator.model.Pupil;

public class SheetDaoIT {

	private SheetDao sheetWriter;

	@Before
	public void setUp() {
		this.sheetWriter = new SheetDao();
	}

	@Test
	public void writePupilsAndLessonDatesToExcel_ShouldReadDataFromExcelWithResultTemplate()
			throws IOException, URISyntaxException, SAXException, InvalidFormatException {

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
		final File readerConfig = new File(getClass().getResource("/" + "pupilHolidaysITJxlsTemplate.xml").toURI());
		final XLSReader xlsxReader = ReaderBuilder.buildFromXML(readerConfig);

		// when
		this.sheetWriter.writeGradeXls(pupils, lessonDates, output);

		// then
		FileInputStream xlsStream = new FileInputStream(output);
		Map<String, Object> beans = new HashMap<>();
		beans.put("pupil", new Pupil("", ""));
		final Dates actualDates = new Dates();
		beans.put("dates", actualDates);
		List<Pupil> actualPupils = new ArrayList<>();
		beans.put("pupils", actualPupils);
		XLSReadStatus readStatus = xlsxReader.read(xlsStream, beans);

		assertThat(readStatus.isStatusOK(), is(true));

		assertThat(actualDates.getDate1(), is(d1));
		assertThat(actualDates.getDate2(), is(d2));
		assertThat(actualDates.getDate3(), is(d3));
		
		assertThat(actualPupils.size(), is(3));
		assertThat(actualPupils.get(0), is(equalTo(p1)));
		assertThat(actualPupils.get(1), is(equalTo(p2)));
		assertThat(actualPupils.get(2), is(equalTo(p3)));
	}

	@After
	public void tearDown() {
		this.sheetWriter = null;
	}

	private class Dates {

		private LocalDate date1;
		private LocalDate date2;
		private LocalDate date3;

		
		public LocalDate getDate1() {
			return date1;
		}

		public void setDate1(LocalDate date1) {
			this.date1 = date1;
		}

		public LocalDate getDate2() {
			return date2;
		}

		public void setDate2(LocalDate date2) {
			this.date2 = date2;
		}

		public LocalDate getDate3() {
			return date3;
		}

		public void setDate3(LocalDate date3) {
			this.date3 = date3;
		}

	}

}