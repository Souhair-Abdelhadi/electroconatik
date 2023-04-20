package com.electronicshop.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.electronicshop.domain.LoginRequest;
import com.electronicshop.domain.LoginResponse;
import com.electronicshop.entities.User;
import com.electronicshop.jwt.JwtTokenFilter;
import com.electronicshop.jwt.JwtTokenUtil;
import com.electronicshop.repository.UserRepo;
import com.electronicshop.service.IUser;

@Service
public class UserService implements IUser {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Override
	public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
		
		Optional<User> user = userRepo.findByEmail(loginRequest.getEmail());
		if(!user.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//		String encodedPassword = bCryptPasswordEncoder.
//		System.out.println("password : "+encodedPassword);
		if(!bCryptPasswordEncoder.matches(loginRequest.getPassword(),user.get().getPassword())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		LoginResponse loginResponse = new LoginResponse();
		String token = jwtTokenUtil.generateAccessToken(user.get());
		loginResponse.setToken(token);
		
		return ResponseEntity.ok(loginResponse);
	}

}
