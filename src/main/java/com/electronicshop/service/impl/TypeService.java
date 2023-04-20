package com.electronicshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.electronicshop.entities.Type;
import com.electronicshop.repository.TypeRepo;
import com.electronicshop.service.IType;

@Service
public class TypeService implements IType {
	
	@Autowired
	private TypeRepo typeRepo;

	@Override
	public ResponseEntity<List<Type>> getAllTYpes() {
		
		return ResponseEntity.ok(typeRepo.findAll());
	}

	@Override
	public ResponseEntity<Type> addType(Type type) {
		
		return ResponseEntity.ok(typeRepo.save(type));
	}

	@Override
	public ResponseEntity<Object> deleteType(int id) {
		Optional<Type> type = typeRepo.findById(id);
		if(!type.isPresent())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		typeRepo.delete(type.get());
		return ResponseEntity.ok(null);
	}

	@Override
	public ResponseEntity<Object> deleteTypeByName(String name) {

		Optional<Type> type = typeRepo.findByName(name);
		if(!type.isPresent())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		
		typeRepo.deleteByName(name);
		return ResponseEntity.ok(null);
	}

}
