package com.electronicshop.service;

import org.springframework.http.ResponseEntity;

import com.electronicshop.entities.ProductImage;

public interface IProductImage {
	
	ResponseEntity<Object> addProductImage(ProductImage productImage);
	
	ResponseEntity<Object> updateProductImage(ProductImage productImage);
	
	ResponseEntity<Object> deleteProductImage(int id);
	
	ResponseEntity<Object> getProductImage(int id);

}
