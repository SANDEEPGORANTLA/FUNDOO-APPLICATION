package com.bridgelabz.fundoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FundooNoteApplication {

	public static void main(String[] args) {
		System.out.println("                                            "+"WelCome to Spring");		
		System.err.println("                                      "+"WelCome to FundooApplication");
		SpringApplication.run(FundooNoteApplication.class, args);
	}

}
