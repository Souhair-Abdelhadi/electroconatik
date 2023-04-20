package com.electronicshop.service;

import org.springframework.http.ResponseEntity;

import com.electronicshop.entities.Tag;

public interface ITag {

	
	ResponseEntity<Object> addTag(Tag tag);
	
	ResponseEntity<Object> getTag(int id);
	
	ResponseEntity<Object> getTags();
	
	
}
