package com.electronicshop.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;

import javax.servlet.ServletRequest;
import javax.websocket.server.PathParam;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.electronicshop.entities.Brand;
import com.electronicshop.pojos.BrandPojo;
import com.electronicshop.repository.BrandRepo;
import com.electronicshop.service.impl.BrandService;
import com.electronicshop.service.impl.StorageService;

@RestController
@CrossOrigin("*")
public class BrandController {

	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private StorageService storageService;
	
	@RequestMapping(value = "/brand/add",method = RequestMethod.POST,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	ResponseEntity<Object> addBrand(@RequestPart("brand") String brand ,@RequestPart("image") MultipartFile file) throws IOException {	
		return brandService.addBrand(brand,file);
	}
	
	@GetMapping("/brand/{id}")
	ResponseEntity<Object> getBrand( @PathVariable int id){
		return brandService.getBrand(id);
	}
	
	@GetMapping("/brand/all")
	ResponseEntity<Object> getAllBrands(){
		return ResponseEntity.ok(brandService.getAllBrand());
	}
	
	@GetMapping("/brand/names/all")
	ResponseEntity<Object> getAllBrandsNames(){
		return brandService.findNamesAll();
	}
	
	@PutMapping("/brand/update")
	ResponseEntity<Object> updateBrand(@RequestPart("data") String data,@RequestPart("image") MultipartFile image ){
		JSONObject jso = new JSONObject(data);
		return brandService.updateBrand((Integer)jso.get("id"),(String)jso.get("name"),image);
	}
	
	@DeleteMapping("/brand/delete/{id}")
	ResponseEntity<Object> deleteBrand(@PathVariable int id){
		return brandService.deleteBrand(id);
	}
	
	@GetMapping("/download/brand/{name}")
    public ResponseEntity<Object> downloadFile(@PathVariable String name) {
       
		String image = null;
		try {
			image = storageService.downloadBrandImage(name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String, Object> res = new HashMap<>();
		if(image == null) {
			res.put("error", "could not find brand with the given name");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
		res.put("url",image);
		return ResponseEntity.ok(res);
    }
	
	
	
}
