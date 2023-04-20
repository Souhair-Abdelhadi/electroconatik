package com.electronicshop.domain;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginRequest {

	@NotNull
	private String email;
	
	@NotNull
	private String password;
}
