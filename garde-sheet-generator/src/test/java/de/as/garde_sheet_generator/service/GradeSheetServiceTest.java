package de.as.garde_sheet_generator.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import de.as.garde_sheet_generator.dao.HolidayDao;

public class GradeSheetServiceTest {

	private HolidayDao dao;
	
	@Before
	public void setUp() {
		this.dao = mock(HolidayDao.class);
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
