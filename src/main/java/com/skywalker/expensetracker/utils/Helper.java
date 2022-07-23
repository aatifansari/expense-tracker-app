package com.skywalker.expensetracker.utils;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Component;

@Component
public class Helper {
	
	public static int findAge(String dob) {
		
		// convert dob string to LocalDate
		LocalDate theDOB = LocalDate.parse(dob);
		
		LocalDate currentDate = LocalDate.now();
		
		//find duration between dates
		Period period = Period.between(theDOB, currentDate);
		
		return period.getYears();
	}

}
