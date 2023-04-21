package com.electronicshop.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.electronicshop.entities.Tag;
import com.electronicshop.service.impl.TagService;

@RestController
@RequestMapping(path = "tag")
@CrossOrigin("*")
public class TagController {

	
	@Autowired
	private TagService tagService;
	
	
	@PostMapping("/add")
	ResponseEntity<Object> addTag(@RequestBody Tag tag){	
		return tagService.addTag(tag);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<Object> getTag(@PathVariable int id){
		return tagService.getTag(id);
	}
	
	@GetMapping("/all")
	ResponseEntity<Object> getAllTags(){
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		String encodedPassword = bc.encode("aqwzsxedc");
		System.out.println("encode password "+encodedPassword);
		return tagService.getTags();
	}
	
	
}
