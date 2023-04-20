package com.electronicshop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electronicshop.domain.LoginRequest;
import com.electronicshop.domain.LoginResponse;
import com.electronicshop.service.impl.UserService;

@RestController
@RequestMapping("auth")
@CrossOrigin("*")
public class Authentication {

	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest ){
		return userService.login(loginRequest);
	}
	
	@GetMapping("/whoami")
	String whoami() {
		return "electroconatik rest api's";
	}
	
}
