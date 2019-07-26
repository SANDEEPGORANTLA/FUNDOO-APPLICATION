package com.bridgelabz.fundoo.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class Utility {
	public static String todayDate() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String simpledateformatDateTime = now.format(format);
		return simpledateformatDateTime;
	}
}