package de.as.garde_sheet_generator.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReadStatus;
import org.jxls.reader.XLSReader;
import org.xml.sax.SAXException;

import com.google.common.base.Preconditions;

import de.as.garde_sheet_generator.model.Pupil;

public class PupilDao {

	private File xmlConfig;
	private XLSReader reader;
	
	public PupilDao(File xmlConfig) throws IOException, SAXException {
		
		this.xmlConfig = xmlConfig;
		this.reader = ReaderBuilder.buildFromXML(xmlConfig);
		
	}
	
	public List<Pupil> extractFromXlsx(File excelFile) throws InvalidFormatException, IOException, FileNotFoundException {
		
		Preconditions.checkNotNull(excelFile);
		
		FileInputStream xlsStream = new FileInputStream(excelFile);
		Map<String, Object> beans = new HashMap<>();
		beans.put("pupil", new Pupil("", ""));
		List<Pupil> pupils = new ArrayList<>();
		beans.put("pupils", pupils);
		XLSReadStatus readStatus = reader.read(xlsStream, beans);
		
		
		return pupils;
	}
	
	
}
