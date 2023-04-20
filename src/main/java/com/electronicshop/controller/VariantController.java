package com.electronicshop.controller;

import java.io.IOException;

import org.apache.tomcat.util.json.ParseException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.electronicshop.entities.Variant;
import com.electronicshop.pojos.VariantPojo;
import com.electronicshop.service.impl.VariantService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin("*")
@RequestMapping("variant")
public class VariantController {

	@Autowired
	private VariantService variantService;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<Object> addVariant(@RequestPart("product_id") String product_id, @RequestPart("strVariant") String strVariant,@RequestPart("images") MultipartFile[] files){		
		Variant variant = variantService.addVariant(product_id,strVariant, files);
		if(variant == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
		
		return ResponseEntity.ok(variant) ;
	}
	
	@GetMapping("/{id}")
	ResponseEntity<Object> getVariant(@PathVariable int id) throws IOException{
		return variantService.getVariant(id);
	}

	
	@DeleteMapping("/delete/{id}")
	ResponseEntity<Object> deleteVariant(@PathVariable int id){
		return variantService.deleteVariant(id);
	}
	
	@GetMapping("/getImages/{id}")
	ResponseEntity<Object> getImages(@PathVariable int id){
		return variantService.getVariantImages(id);
	}
	
	@PutMapping("/update")
	ResponseEntity<Object> updateVariant(@RequestParam("variant") String variant){
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			VariantPojo variantPojo = objectMapper.readValue(variant, VariantPojo.class);
			return variantService.updateVariant(variantPojo);
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Give all associated data of variant");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Give all associated data of variant");
		}
		
	}
}
