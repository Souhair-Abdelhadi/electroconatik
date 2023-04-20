package com.electronicshop.service;

import org.springframework.http.ResponseEntity;

import com.electronicshop.domain.LoginRequest;
import com.electronicshop.domain.LoginResponse;
import com.electronicshop.entities.User;

public interface IUser {
	
	ResponseEntity<LoginResponse> login(LoginRequest loginRequest);

}
