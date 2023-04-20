package com.electronicshop.service.impl;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.electronicshop.entities.Brand;
import com.electronicshop.repository.BrandRepo;
import com.electronicshop.service.IBrand;

@Service
public class BrandService implements IBrand  {

	@Autowired
	private BrandRepo brandRepo;
	
	@Autowired
	private StorageService storageService;
	
	
	@Override
	public ResponseEntity<Object> addBrand(String strBrand, MultipartFile file) {
		Brand brand = new Brand();
		JSONParser js = new JSONParser(strBrand);
		try {
			HashMap<String, Object> _brand = (HashMap<String, Object>)js.parse();
			brand.setName((String)_brand.get("name"));
			brand.setSlug((String)_brand.get("slug"));
			brand.setCreatedAt(Date.valueOf(LocalDate.now()));

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		Optional<Brand> _brand = brandRepo.findByName(brand.getName());
		HashMap<String, Object> res = new HashMap<>();
		if(_brand.isPresent()) {
			res.put("error", "Brand already exist");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
		
		String filePath = null;
		try {
			filePath = storageService.uploadBrandImage(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(filePath == null) {
			res.put("error", "Could not upload the image to server");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
		
		brand.setImage(filePath);
		return ResponseEntity.ok(brandRepo.save(brand));
		
	
	}

	@Override
	public ResponseEntity<Object> getBrand(int id) {
		HashMap<String, Object> res = new HashMap<>();
		Optional<Brand> _brand = brandRepo.findById(id);
		
		if(!_brand.isPresent()) {
			res.put("error", "Could not find brand with the given id");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
		Brand brand = _brand.get();
		try {
			brand.setImage(storageService.downloadBrandImage(brand.getName()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(brand);
		
		
	}

	@Override
	public List<Brand> getAllBrand() {
		// TODO Auto-generated method stub
		List<Brand> allBrands = brandRepo.findAll();
		for(int i=0;i<allBrands.size();i++) {
			try {
				allBrands.get(i).setImage(storageService.downloadBrandImage(allBrands.get(i).getName()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return allBrands ;
	}

	@Override
	public ResponseEntity<Object> deleteBrand(int id) {
		// TODO Auto-generated method stub
		Optional<Brand> brand = brandRepo.findById(id);
		HashMap<String, Object> res = new HashMap<>();
		if(!brand.isPresent()) {
			res.put("message", "no brand found with the given id");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
		Brand _brand = brand.get();
		_brand.setDeleted(true);
		brandRepo.save(_brand);
		res.put("message", "brand with id "+id + " deleted");
		return ResponseEntity.ok(res);
	}

	@Override
	public ResponseEntity<Object> updateBrand(int id, String strBrand, MultipartFile file) {
		
		Optional<Brand> _brand = brandRepo.findById(id);
		HashMap<String, Object> res = new HashMap<>();
		if(!_brand.isPresent()) {
			res.put("error", "Brand already exist");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
		
		String filePath = null;
		try {
			filePath = storageService.uploadBrandImage(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(filePath == null) {
			res.put("error", "Could not upload the image to server");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
		Brand brand = _brand.get();
		brand.setName(strBrand);
		brand.setImage(filePath);
		return ResponseEntity.ok(brandRepo.save(brand));
	}

	@Override
	public ResponseEntity<Object> findNamesAll() {
		List<HashMap<String, Object>> res = new ArrayList<>();
		List<Brand> brands = brandRepo.findAll();
		for(Brand brand:brands) {
			HashMap<String, Object> item = new HashMap<>();
			item.put("name", brand.getName());
			res.add(item);
		}
		return ResponseEntity.ok(res);

		
	}

}
