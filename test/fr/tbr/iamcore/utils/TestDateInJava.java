package fr.tbr.iamcore.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDateInJava {
	
	
	public static void main(String[] args) throws ParseException{
		Date date = new Date();
		
		System.out.println(date);
		
		String dateInString = "04/01/2016 20:20:01";
		
		String pattern = "dd/MM/yyyy HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		System.out.println(sdf.parse(dateInString));
		
		
		
		Date parsedDate = new SimpleDateFormat(pattern).parse("12/12/2012 03:04:05");
		System.out.println(date.getTime() - parsedDate.getTime());
		
		
	}

}
