package com.electronicshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electronicshop.entities.Type;
import com.electronicshop.service.impl.TypeService;

@RestController
@RequestMapping("type")
@CrossOrigin("*")
public class TypeController {

	@Autowired
	private TypeService typeService;
	
	
	@GetMapping("/all")
	ResponseEntity<List<Type>> getAllTypes(){
		return typeService.getAllTYpes();
	}
	
	@PostMapping("/add")
	ResponseEntity<Type> addType(@RequestBody Type type){
		return typeService.addType(type);
	}
	
//	@DeleteMapping("/delete/{id}")
//	ResponseEntity<Object> deleteType(@PathVariable int id){
//		return typeService.deleteType(id);
//	}
	
	@DeleteMapping("/delete/{name}")
	ResponseEntity<Object> deleteType(@PathVariable String name){
		return typeService.deleteTypeByName(name);
	}
	
}
