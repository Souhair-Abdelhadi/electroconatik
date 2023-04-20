package com.electronicshop.service;

import java.io.IOException;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.electronicshop.entities.Variant;
import com.electronicshop.pojos.VariantPojo;

public interface IVariant {

	Variant addVariant(String product_id,String variant,MultipartFile[] images);
	
	ResponseEntity<Object> updateVariant(VariantPojo variantPojo);
	
	ResponseEntity<Object> deleteVariant(int id);
	
	ResponseEntity<Object> getVariant(int id) throws IOException;
	
	ResponseEntity<Object> getVariantsOfProduct(int idProduct) throws IOException;
	
	ResponseEntity<Object> getVariantImages(int id);
	
	ResponseEntity<Object> deleteProductVariants(int idProduct);
	
	
}
