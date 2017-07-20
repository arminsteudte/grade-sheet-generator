package de.as.garde_sheet_generator.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.google.common.annotations.VisibleForTesting;

import de.as.garde_sheet_generator.dao.HolidayDao;
import de.as.garde_sheet_generator.dao.PupilDao;
import de.as.garde_sheet_generator.dao.SheetDao;
import de.as.garde_sheet_generator.model.Holiday;
import de.as.garde_sheet_generator.model.Pupil;
import de.as.garde_sheet_generator.model.State;

public class GradeSheetService {

	private final HolidayDao holidaySupplier;
	private final PupilDao pupilSupplier;
	private final SheetDao sheetWriter;

	public GradeSheetService(HolidayDao holidaySupplier, PupilDao pupilSupplier, SheetDao sheetWriter) {
		this.pupilSupplier = pupilSupplier;
		this.holidaySupplier = holidaySupplier;
		this.sheetWriter = sheetWriter;
	}

	@VisibleForTesting
	protected List<LocalDate> calculateLessonDates(Set<DayOfWeek> lessonWeekdays, LocalDate schoolYearStart,
			LocalDate schoolYearEnd) {

		List<LocalDate> lessonDates = new ArrayList<>();

		final LocalTime placeholderTime = LocalTime.of(0, 0);
		final LocalDateTime startDateTime = LocalDateTime.of(schoolYearStart, placeholderTime);
		final LocalDateTime endDate = LocalDateTime.of(schoolYearEnd, placeholderTime);

		long differenceInDays = Duration.between(startDateTime, endDate).toDays();

		LocalDate currentDate = schoolYearStart;
		List<LocalDate> allDates = new ArrayList<>();

		for (int i = 0; i < differenceInDays; i++) {

			allDates.add(currentDate);

			currentDate = currentDate.plusDays(1);
		}

		lessonDates = allDates.stream().filter(d -> lessonWeekdays.contains(d.getDayOfWeek()))
				.collect(Collectors.toList());

		return lessonDates;

	}
	
	public void generateGradeSheet(URI savingPath, Set<DayOfWeek> lessonWeekdays, LocalDate schoolYearStart,
			LocalDate schoolYearEnd, State state, Year schoolYear, File excelPupils) throws InvalidFormatException, FileNotFoundException, IOException {
		
		List<LocalDate> lessonDates = this.calculateLessonDates(lessonWeekdays, schoolYearStart, schoolYearEnd);
		
		List<Holiday> holidayDates = this.holidaySupplier.getHolidays(state, schoolYear);
		
		List<Pupil> pupilList = this.pupilSupplier.extractFromXlsx(excelPupils);
		
		
	}

}
