package it.bank.FabrickTest.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utility {
	public static boolean checkDate(String dateToCheck) {
		SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
		//To make strict date format validation
		formatter.setLenient(false);
		Date parsedDate = null;
		try {
			parsedDate = formatter.parse(dateToCheck);
			System.out.println("++validated DATE TIME ++"+formatter.format(parsedDate));

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public static String asJsonString(Object input) {
		try {
			return new ObjectMapper().writeValueAsString(input);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
