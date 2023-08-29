package com.electronicshop.service;

import java.io.IOException;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.electronicshop.pojos.FilterProductsPojo;
import com.electronicshop.pojos.ProductPojo;
import com.electronicshop.pojos.ProductVariantsPojo;

public interface IProduct {

	ResponseEntity<Object> addProduct(ProductPojo productPojo );
	
	ResponseEntity<Object> addProductV2(ProductVariantsPojo productVariantsPojo );
	
	ResponseEntity<Object> updateProduct(ProductPojo productPojo);
	
	ResponseEntity<Object> updateProductAndVariants(ProductVariantsPojo productVariantsPojo);
	
	ResponseEntity<Object> deleteProduct(int id);
	
	ResponseEntity<Object> deleteByTitle(String title);
	
	ResponseEntity<Object> getProduct(int id);
	
	ResponseEntity<Object> getAllProducts();
	
	ResponseEntity<Object> getRandomProducts();
	
	ResponseEntity<Object> getFilteredProducts(String title,String brand,Boolean etat,
			Integer discount,String color,String size, Float price,Integer quantity);
	
	ResponseEntity<Object> getFilteredProducts(FilterProductsPojo filterProductsPojo);
	
	ResponseEntity<Object> getSingleProductImage(int id);
	
}
