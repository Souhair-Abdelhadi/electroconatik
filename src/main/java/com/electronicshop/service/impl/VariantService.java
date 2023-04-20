package com.electronicshop.service.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.aspectj.weaver.ast.Var;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.electronicshop.entities.Brand;
import com.electronicshop.entities.Product;
import com.electronicshop.entities.ProductImage;
import com.electronicshop.entities.Variant;
import com.electronicshop.pojos.ProductImagePojo;
import com.electronicshop.pojos.VariantPojo;
import com.electronicshop.repository.ProductImageRepo;
import com.electronicshop.repository.ProductRepo;
import com.electronicshop.repository.VariantRepo;
import com.electronicshop.service.IVariant;


@Service
public class VariantService implements IVariant {

	@Autowired
	private VariantRepo variantRepo;
	
	@Autowired
	private ProductImageRepo productImageRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private StorageService storageService;
	
	@Override
	public Variant addVariant(String product_id,String strVariant, MultipartFile[] images) {
		try {
			
			JSONObject productId = new JSONObject(product_id);
			System.out.println("product id : " +productId.getInt("product_id"));
			Variant variant = getVariant(productId.getInt("product_id"),strVariant);
			System.out.println("-------------------error here----------------------");
			Variant _variant = variantRepo.save(variant);
			System.out.println(" images : "+images.length);
			for(int i=0; i < images.length;i++) {
				ProductImage productImage = new ProductImage();
				productImage.setAlt(_variant.getProduit().getTitle() + " "+_variant.getColor());
				productImage.setSrc(storageService.uploadProductImage(images[i]));
				productImage.setVariant(_variant);
				
				ProductImage _productImage = productImageRepo.save(productImage);
				Set<ProductImage> list = _variant.getImages();
				if(list == null) {
					list = new HashSet<>();
				}
				list.add(_productImage);
				_variant.setImages(list);
				
			}
			System.out.println("------------------End of variant upload--------------------------");
			System.out.println(images.length);
			return _variant ;

		} catch (Exception e) {
			// TODO: handle exception
			HashMap<String, Object> error = new HashMap<>();
			error.put("error", "can't read data");
			System.out.println(e);
			return null;
			
		}
	}

	@Override
	public ResponseEntity<Object> updateVariant(VariantPojo variantPojo) {
		
		Optional<Variant> variant = variantRepo.findById(variantPojo.getId());
		if(!variant.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Variant with the given id not found");
		}
		Variant _variant = variant.get();
		_variant.setColor(variantPojo.getColor());
		_variant.setDiscount(variantPojo.getDiscount());
		_variant.setPrice(variantPojo.getPrice());
		_variant.setQuantity(variantPojo.getQuantity());
		_variant.setSale(variantPojo.getSale());
		_variant.setSize(variantPojo.getSize());
		_variant.setSku(variantPojo.getSku());
		_variant.setStock(variantPojo.getStock());
		_variant.setVar1(variantPojo.getVar1());
		_variant.setVar2(variantPojo.getVar2());
		_variant.setVar3(variantPojo.getVar3());
		_variant.setVar4(variantPojo.getVar4());
		_variant.setVar5(variantPojo.getVar5());
		variantRepo.deleteProductVariants(_variant.getId());
		System.out.println("---------product check--------");
		Product product = productRepo.findById(_variant.getProduit().getId()).get();
		System.out.println("---------check END--------");
		Set<ProductImage> productImages = new HashSet<>();
		System.out.println("variants images size : "+variantPojo.getImages().size());
		variantPojo.getImages().forEach(image -> {
			String filePath = storageService.uploadBase64ProductImage(image.getSrc());
			System.out.println("image uploaded : "+(filePath != null ? "true" : "false"));
			if(filePath != null) {
				productImages.add(productImageRepo.save(new ProductImage(0, product.getTitle()+" "+ _variant.getColor(), filePath, _variant)));
			}
		});
		_variant.setImages(productImages);
		return ResponseEntity.ok(_variant);
	}

	@Override
	public ResponseEntity<Object> deleteVariant(int id) {
		HashMap<String, Object> res = new HashMap<>();
		Optional<Variant> variant = variantRepo.findById(id);
		if(!variant.isPresent()) {
			res.put("message", "Could find a variant with the given id");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res) ;
		}
		variantRepo.deleteById(id);		
		res.put("message", "Variant with id "+ id + " deleted!");
		return ResponseEntity.status(HttpStatus.OK).body(res) ;
	}

	@Override
	public ResponseEntity<Object> getVariant(int id) throws IOException {
		Optional<Variant> variant = variantRepo.findById(id);
		HashMap<String, Object> res = new HashMap<>();
		if(!variant.isPresent()) {
			res.put("message", "Could not find variant with the given id");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
		
		Variant _variant = variant.get();
		Set<ProductImage> images = new HashSet<>();
		for(ProductImage image: _variant.getImages()) {
			image.setSrc(storageService.downloadProductImage(image.getId()));
			images.add(image);
		}
		_variant.setImages(images);
		return ResponseEntity.ok(_variant);
	}
	
	
	@Override
	public ResponseEntity<Object> getVariantImages(int id) {

		Optional<Variant> _variant = variantRepo.findById(id);
		if(!_variant.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		Variant variant = _variant.get();
		Set<ProductImage> pImgs = variant.getImages();
		Set<ProductImage> list = new HashSet<>();
		pImgs.forEach(item ->{
			try {
				item.setSrc(storageService.downloadProductImage(item.getId()));
				list.add(item);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		variant.setImages(list);
		return ResponseEntity.ok(variant);
	}
	
	@Override
	public ResponseEntity<Object> getVariantsOfProduct(int idProduct) throws IOException {

		Optional<Product> product = productRepo.findById(idProduct);
		
		if(!product.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		Product _product = product.get();
		Set<Variant>  list = new HashSet<>();
		
		Set<Variant> variants =  _product.getVariants();
		variants.forEach(variant -> {
			Variant _variant = variant;
			Set<ProductImage> proImgs = _variant.getImages();
			proImgs.forEach(image -> {
				try {
					image.setSrc(storageService.downloadProductImage(image.getId()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			_variant.setImages(proImgs);
			list.add(_variant);
			
		});
				
		return ResponseEntity.ok(list);
	}
	
	public Variant getVariant(int product_id,String strVariant) throws ParseException {
		
		Variant variant = new Variant();
		JSONParser js = new JSONParser(strVariant);

		HashMap<String, Object> _variant = (HashMap<String, Object>)js.parse();
		System.out.println("variant : "+ _variant.toString());
		variant.setSku("sku");
		variant.setSize((String)_variant.get("size"));
		System.out.println("size");
		variant.setColor((String)_variant.get("color"));
		System.out.println("color");
		variant.setPrice(Float.parseFloat((String)_variant.get("price")));
		System.out.println(Float.parseFloat((String)_variant.get("price")));
		variant.setSale((boolean)_variant.get("sale"));
		System.out.println("sale");
		variant.setDiscount(Integer.parseInt((String)_variant.get("discount")));
		System.out.println("discount");
		variant.setStock(Integer.parseInt((String)_variant.get("stock")));
		System.out.println("quantity");
		variant.setQuantity(Integer.parseInt((String)_variant.get("quantity")));
		System.out.println("------- end of variant check ------");
		Product product = productRepo.findById(product_id).get();
		
		variant.setProduit(product);
		
		return variant;
		
	}
	
	public ProductImage getProductImage(String strImageInfo) throws ParseException {
			
		ProductImage image = new ProductImage();
		JSONParser js = new JSONParser(strImageInfo);

		HashMap<String, Object> _image = (HashMap<String, Object>)js.parse();
		image.setSrc((String)_image.get("src"));
		image.setAlt((String)_image.get("alt"));
				
		return image;
		
	}

	@Override
	public ResponseEntity<Object> deleteProductVariants(int idProduct) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
