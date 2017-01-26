package de.as.garde_sheet_generator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.as.garde_sheet_generator.dao.SchulferienDeutschlandProvider;

@Configuration
public class AppConfig {
	
	@Bean
	public SchulferienDeutschlandProvider holidayProvider() {
		return new SchulferienDeutschlandProvider("");
	}
	
}
