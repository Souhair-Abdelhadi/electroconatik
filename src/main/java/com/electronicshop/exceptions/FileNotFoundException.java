package com.electronicshop.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3524643952506480745L;
	private String message;
	
}
