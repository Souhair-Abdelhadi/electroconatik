package com.electronicshop.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileStorageException extends RuntimeException {

	private static final long serialVersionUID = -891472799822881908L;
	private String message;
	
}
