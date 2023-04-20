package com.electronicshop.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.electronicshop.entities.Type;

public interface IType {

	ResponseEntity<List<Type>> getAllTYpes();
	
	ResponseEntity<Type> addType(Type type);
	
	ResponseEntity<Object> deleteType(int id);
	
	ResponseEntity<Object> deleteTypeByName(String name);
	
}
