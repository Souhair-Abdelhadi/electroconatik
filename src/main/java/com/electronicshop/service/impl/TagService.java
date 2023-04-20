package com.electronicshop.service.impl;

import java.util.HashMap;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.electronicshop.entities.Tag;
import com.electronicshop.repository.TagRepo;
import com.electronicshop.service.ITag;


@Service
public class TagService implements ITag {

	@Autowired
	private TagRepo tagRepo;
	
	@Override
	public ResponseEntity<Object> addTag(Tag tag) {
		HashMap<String, Object> res = new HashMap<>();
		Optional<Tag> _tag = tagRepo.findByNom(tag.getNom());
		if(_tag.isPresent()) {
			res.put("error", "Tag already exist");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
		return ResponseEntity.ok(tagRepo.save(tag));
	}

	@Override
	public ResponseEntity<Object> getTag(int id) {
		Optional<Tag> _tag = tagRepo.findById(id);
		if(!_tag.isPresent()) {
			HashMap<String, Object> res = new HashMap<>();
			res.put("error", "Tag not found");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
		return ResponseEntity.ok(_tag.get());
	}

	@Override
	public ResponseEntity<Object> getTags() {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(tagRepo.findAll());
	}

	
	
	
}
