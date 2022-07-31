package com.skywalker.expensetracker.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

import com.skywalker.expensetracker.exceptions.DateFormatException;

@Component
public class Helper {
	
	public static int findAge(String dob) throws Exception{
		// find age from dob
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate theDOB;
		try {
			theDOB = LocalDate.parse(dob, formatter);
		}catch(Exception ex) {
			throw new DateFormatException("Required date format 'dd-MM-yyyy'.");
		}
		
		LocalDate currentDate = LocalDate.now();
		Period period = Period.between(theDOB, currentDate);
		if(period.getYears() <= 0) {
			throw new DateFormatException("Invalid Date of Birth");
		}
		return period.getYears();
	}
	
	public static boolean validateUsername(String name) {
		for(int i=0;i<name.length();i++) {
			if(Character.isDigit(name.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
