package com.electronicshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ElectronicShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicShopApplication.class, args);
		
//		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
//		System.out.println("for mediani "+ bc.encode("****"));
//		System.out.println("for atik "+ bc.encode("******"));
	}

}
