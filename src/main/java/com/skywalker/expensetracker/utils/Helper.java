package com.skywalker.expensetracker.utils;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Component;

@Component
public class Helper {
	
	public static int findAge(String dob) {
		// find age from dob
		LocalDate theDOB = LocalDate.parse(dob);
		LocalDate currentDate = LocalDate.now();
		Period period = Period.between(theDOB, currentDate);
		return period.getYears();
	}
}
