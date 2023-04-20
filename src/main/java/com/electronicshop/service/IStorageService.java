package com.electronicshop.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.electronicshop.entities.Brand;

public interface IStorageService {
	
	
	public String uploadBrandImage(MultipartFile file) throws IOException;
	
	public String downloadBrandImage(String fileName) throws IOException;
	
	public String uploadProductImage(MultipartFile file) throws IOException;
	 
	public String downloadProductImage(int id) throws IOException;
	
	public String uploadBase64ProductImage(String base64);

}
