package com.electronicshop.service.impl;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.electronicshop.entities.Brand;
import com.electronicshop.entities.ProductImage;
import com.electronicshop.repository.BrandRepo;
import com.electronicshop.repository.ProductImageRepo;
import com.electronicshop.service.IStorageService;

@Service
public class StorageService implements IStorageService {
	
	private final String BRAND_FOLDER_PATH = "/electroshop/brands/";
	private final String PRODUCT_FOLDER_PATH = "/electroshop/procducts/";
	
	@Autowired
	private BrandRepo brandRepo;
	
	@Autowired
	private ProductImageRepo productImageRepo;
	
	@Override
	public String uploadProductImage(MultipartFile file) throws IOException {
		try {		     
	        File dir = new File(PRODUCT_FOLDER_PATH);
	        if(!dir.exists()) {
	        	System.out.println("------------------------------------------");
	        	System.out.println("folder not found in the path");
	        	System.out.println("------------------------------------------");
	        	if(!dir.mkdirs()) {
	        		System.out.println("------------------------------------------");
	            	System.out.println("folder did not created");
	            	System.out.println("------------------------------------------");
	        	}
	        	else {
	        		System.out.println(" path : "+ dir.getAbsolutePath());
	        	}
	        }
	        
	        //Files.copy(file.getInputStream(), Path.of("/electroshop/brands/"+file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
	        System.out.println("filename : "+file.getOriginalFilename());
	        String name = file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf("."));
	        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
	        LocalDateTime localDate = LocalDateTime.now();
	        Timestamp timestamp = Timestamp.valueOf(localDate);
	        String timestmp = String.valueOf(timestamp.getTime());
	        file.transferTo(Path.of(PRODUCT_FOLDER_PATH+name+timestmp+ext));
	        return PRODUCT_FOLDER_PATH+name+timestmp+ext;
		} catch (Exception e) {

			return null;
		}
	}

	@Override
	public String downloadProductImage(int id) throws IOException {
		Optional<ProductImage> productImage = productImageRepo.findById(id);
		if(!productImage.isPresent()) {
			return null;
		}
        String filePath=productImage.get().getSrc();
        File file = new File(filePath);
        byte[] image = Files.readAllBytes(file.toPath());
        String type = file.getName().substring(file.getName().indexOf(".")+1);
        String base64Header = "data:image/"+type+";base64,";
        String imageBase64 = Base64.getEncoder().encodeToString(image);
        System.out.println("image base64 "+base64Header.concat(imageBase64));
        return base64Header.concat(imageBase64);
	}

	@Override
	public String uploadBrandImage(MultipartFile file) throws IOException {
		
		try {		     
	        File dir = new File(BRAND_FOLDER_PATH);
	        if(!dir.exists()) {
	        	System.out.println("------------------------------------------");
	        	System.out.println("folder not found in the path");
	        	System.out.println("------------------------------------------");
	        	if(!dir.mkdirs()) {
	        		System.out.println("------------------------------------------");
	            	System.out.println("folder did not created");
	            	System.out.println("------------------------------------------");
	        	}
	        	else {
	        		System.out.println(" path : "+ dir.getAbsolutePath());
	        	}
	        }
	        
	        //Files.copy(file.getInputStream(), Path.of("/electroshop/brands/"+file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
	        file.transferTo(Path.of(BRAND_FOLDER_PATH+file.getOriginalFilename()));
	        return BRAND_FOLDER_PATH+file.getOriginalFilename();
		} catch (Exception e) {

			return null;
		}
	}

	@Override
	public String downloadBrandImage(String brandName) throws IOException {

		Optional<Brand> brand = brandRepo.findByName(brandName);
		if(!brand.isPresent()) {
			return null;
		}
        String filePath=brand.get().getImage();
        File file = new File(filePath);
        byte[] image = Files.readAllBytes(file.toPath());
        String type = file.getName().substring(file.getName().indexOf(".")+1);
        String base64Header = "data:image/"+type+";base64,";
        String imageBase64 = Base64.getEncoder().encodeToString(image);
        System.out.println("image base64 "+base64Header.concat(imageBase64));
        return base64Header.concat(imageBase64);
	}

	@Override
	public String uploadBase64ProductImage(String base64) {
		// TODO Auto-generated method stub
		
		LocalDateTime localDate = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDate);
        String timestmp = String.valueOf(timestamp.getTime());
        
        String head = base64.substring(0, 10);
        System.out.println(head);
        String[] list = base64.split(";");
        String[] ext = list[0].split("/");
        if(!head.equals("data:image")) {
        	return null;
        }
        System.out.println(base64);
        String[] base = base64.split(",");
        byte[] imageBytes = Base64.getDecoder().decode(base[1]);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        
        try {
        	File dir = new File(PRODUCT_FOLDER_PATH);
	        if(!dir.exists()) {
	        	System.out.println("------------------------------------------");
	        	System.out.println("folder not found in the path");
	        	System.out.println("------------------------------------------");
	        	if(!dir.mkdirs()) {
	        		System.out.println("------------------------------------------");
	            	System.out.println("folder did not created");
	            	System.out.println("------------------------------------------");
	        	}
	        	else {
	        		System.out.println(" path : "+ dir.getAbsolutePath());
	        	}
	        }
        	BufferedImage bImage = ImageIO.read(bis);
			ImageIO.write(bImage, ext[1], new File(PRODUCT_FOLDER_PATH+timestmp+"."+ext[1]));
			return PRODUCT_FOLDER_PATH+timestmp+"."+ext[1];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e+ " error in upload");
			return null;
		}
		
		
	}

	
	
}
