package de.as.garde_sheet_generator.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import de.as.garde_sheet_generator.dao.HolidayDao;
import de.as.garde_sheet_generator.dao.PupilDao;

public class GradeSheetServiceTest {

	private HolidayDao holidaySupplier;
	private PupilDao pupilSupplier;
	
	private GradeSheetService service;
	
	@Before
	public void setUp() {
		this.holidaySupplier = mock(HolidayDao.class);
		this.pupilSupplier = mock(PupilDao.class);
		
		this.service = new GradeSheetService(holidaySupplier, pupilSupplier);
	}
	
	@Test
	public void calculateLessonDates_WithOneWeek_ShouldReturnSpecifiedWeekdaysAsConcreteDates() {

		// given
		Set<DayOfWeek> lessonDays = Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
		LocalDate schoolYearStart = LocalDate.of(2017, 4, 10); // Monday
		LocalDate schoolYearEnd	= LocalDate.of(2017, 4, 16); // Sunday
		
		// when
		List<LocalDate> actualLessonDates = this.service.calculateLessonDates(lessonDays, schoolYearStart, schoolYearEnd);
		
		// then
		assertThat(actualLessonDates.size(), is(2));
		List<DayOfWeek> actualDaysOfWeek = actualLessonDates.stream().map((date) -> DayOfWeek.from(date)).collect(Collectors.toList());
		assertThat(actualDaysOfWeek.get(0), is(DayOfWeek.MONDAY));
		assertThat(actualDaysOfWeek.get(1), is(DayOfWeek.WEDNESDAY));
	}

}
