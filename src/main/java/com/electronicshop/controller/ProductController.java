package com.electronicshop.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.electronicshop.pojos.FilterProductsPojo;
import com.electronicshop.pojos.ProductImagePojo;
import com.electronicshop.pojos.ProductPojo;
import com.electronicshop.pojos.ProductVariantsPojo;
import com.electronicshop.pojos.VariantPojo;
import com.electronicshop.service.impl.ProductService;
import com.electronicshop.service.impl.VariantService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("product")
@CrossOrigin("*")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private VariantService variantService;
	
	
	@PostMapping(value = "/add")
	ResponseEntity<Object> addProduct(@RequestBody @Valid ProductPojo productPojo){
		return productService.addProduct(productPojo);
	}
	
	@PostMapping(value = "/addv2")
	ResponseEntity<Object> addProductV2(@RequestParam("product") String product ){
		System.out.println("product : "+product);
		ObjectMapper objectMapper = new ObjectMapper();
		ProductVariantsPojo productVariantsPojo;
		try {
			productVariantsPojo = objectMapper.readValue(product, ProductVariantsPojo.class);
			return productService.addProductV2(productVariantsPojo);
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@GetMapping("/{id}")
	ResponseEntity<Object> getProduct(@PathVariable int id){
		return productService.getProduct(id);
	}
	
	@GetMapping("/variants/{id}")
	ResponseEntity<Object> getProductVariants(@PathVariable int id) throws IOException{
		return variantService.getVariantsOfProduct(id);
	}
	
	@GetMapping("/all")
	ResponseEntity<Object> getAllProduct(){
		return productService.getAllProducts();
	}

	@GetMapping("/random")
	ResponseEntity<Object> getRandomProduct(){
		return productService.getRandomProducts();
	}
	
	@PutMapping("/update")
	ResponseEntity<Object> updateProduct( @RequestBody ProductPojo productPojo){
		return productService.updateProduct(productPojo);
	}
	
	@PutMapping(value="/update/product_variants")
	ResponseEntity<Object> updateProductAndVariants( @RequestParam("product") String product ){
		System.out.println(product);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ProductVariantsPojo productVariantsPojo = objectMapper.readValue(product, ProductVariantsPojo.class);
			
//			List<VariantPojo> list = productVariantsPojo.getVariants();
//			List<ProductImagePojo> lp = list.get(0).getImages();
			
			productService.updateProductAndVariants(productVariantsPojo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/filterProducts")
	ResponseEntity<Object> getFiltredProduct( String title, String brand,
			 Boolean etat, Integer discount, String color, String size,
			 Float price, Integer quantity
			){
		return productService.getFilteredProducts(title, brand, etat, discount, color, size, price, quantity);
	}
	
	@GetMapping("/filterProducts2")
	ResponseEntity<Object> getFiltredProduct2(@RequestParam String filter){
		ObjectMapper mapper = new ObjectMapper();
		try {
			FilterProductsPojo filterProductsPojo = mapper.readValue(filter, FilterProductsPojo.class);
			return productService.getFilteredProducts(filterProductsPojo);

		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No object with params was provided");
		}
		
	}
	
	
	@GetMapping("/image/{id}")
	ResponseEntity<Object> getSingleProductImage(@PathVariable int id){
		return productService.getSingleProductImage(id);
	}
}

