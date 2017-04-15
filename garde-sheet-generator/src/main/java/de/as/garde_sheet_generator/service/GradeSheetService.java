package de.as.garde_sheet_generator.service;

import java.security.spec.EncodedKeySpec;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.annotations.VisibleForTesting;

import de.as.garde_sheet_generator.dao.HolidayDao;
import de.as.garde_sheet_generator.dao.PupilDao;

public class GradeSheetService {

	private final HolidayDao holidays;
	private final PupilDao pupils;

	public GradeSheetService(HolidayDao holidaySupplier, PupilDao pupilSupplier) {
		this.pupils = pupilSupplier;
		this.holidays = holidaySupplier;
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

}
