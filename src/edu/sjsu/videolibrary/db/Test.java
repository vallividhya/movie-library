package edu.sjsu.videolibrary.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Test {
	private static int MAX_RETRIES = 3;
	public static void main(String[] args) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		try {
			Date today = dateFormat.parse(dateFormat.format(new Date()));
			System.out.println(today);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int retryCount = 0;
		while (retryCount < MAX_RETRIES) {
			try {
				String id = UUID.randomUUID().toString();
				// Insert into the table
				break;
			} catch (Exception e) {
				retryCount++;
				continue;
			}
		}

		if (retryCount == MAX_RETRIES) {
			//throw new InternalException();
		}
		
	}
}
