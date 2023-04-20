package com.electronicshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.electronicshop.entities.Brand;

public interface IBrand {
	
	ResponseEntity<Object> addBrand(String brand,MultipartFile file);

	ResponseEntity<Object> getBrand(int id);
	
	List<Brand> getAllBrand();
	
	ResponseEntity<Object> updateBrand(int id,String brand,MultipartFile file);
	
	ResponseEntity<Object> deleteBrand(int id);

	ResponseEntity<Object> findNamesAll();
}
