package com.electronicshop.service.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.electronicshop.entities.Brand;
import com.electronicshop.entities.Category;
import com.electronicshop.entities.Collection;
import com.electronicshop.entities.Product;
import com.electronicshop.entities.ProductImage;
import com.electronicshop.entities.Variant;
import com.electronicshop.entityManger.entity.ProductRepoImpl;
import com.electronicshop.pojos.ProductFilterPojo;
import com.electronicshop.pojos.ProductPojo;
import com.electronicshop.pojos.ProductVariantsPojo;
import com.electronicshop.pojos.VariantPojo;
import com.electronicshop.repository.BrandRepo;
import com.electronicshop.repository.CategoryRepo;
import com.electronicshop.repository.CollectionRepo;
import com.electronicshop.repository.ProductImageRepo;
import com.electronicshop.repository.ProductRepo;
import com.electronicshop.repository.TypeRepo;
import com.electronicshop.repository.VariantRepo;
import com.electronicshop.service.IProduct;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class ProductService implements IProduct {
	
	@Autowired
	private BrandRepo brandRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CollectionRepo collectionRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private TypeRepo typeRepo;
	
	@Autowired
	private VariantRepo variantRepo;
	
	@Autowired
	private ProductImageRepo productImageRepo;
	
	@Override
	public ResponseEntity<Object> addProduct(ProductPojo productPojo )  {
		Product product = pojoToProduct(productPojo,true);
		if(product == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
		}
		
		return ResponseEntity.ok(productRepo.save(product));
		
	}
	
	@Override
	public ResponseEntity<Object> updateProduct(ProductPojo productPojo) {

		Product product = pojoToProduct(productPojo);
		if(product == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
		}

		System.out.println("id : "+product.getId());
		
		return ResponseEntity.ok(productRepo.save(product));
	}
	
	
	@Override
	public ResponseEntity<Object> deleteProduct(int id) {
		
		Optional<Product> product  = productRepo.findById(id);
		HashMap<String, Object> res = new HashMap<>();
		if(!product.isPresent()) {
			res.put("message", "Could not find product with the given id");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
		productRepo.deleteById(id);
		res.put("message", "Product with id "+id+" deleted!");
		return ResponseEntity.ok(res);
		
	}
	
	@Override
	public ResponseEntity<Object> getProduct(int id) {
		Optional<Product> product  = productRepo.findById(id);
		HashMap<String, Object> res = new HashMap<>();
		if(!product.isPresent()) {
			res.put("message", "Could not find product with the given id");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
		
		Product _product = product.get();
		 Set<Variant> list = new HashSet<>();
		 Set<Variant> variants = _product.getVariants();
		 variants.forEach( variant -> {
			 Set<ProductImage> _prodcutImages = new HashSet<>();
			 variant.getImages().forEach(imageList ->{
				 ProductImage productImage = imageList;
				 try {
					productImage.setSrc(storageService.downloadProductImage(imageList.getId()));
					_prodcutImages.add(productImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 });
			 variant.setImages(_prodcutImages);
			 list.add(variant);
		 });
		_product.setVariants(variants);
		return ResponseEntity.ok(_product);
	}

	@Override
	public ResponseEntity<Object> getAllProducts() {

		return ResponseEntity.ok(productRepo.findAll());
	}

	@Override
	public ResponseEntity<Object> getRandomProducts() {

		return ResponseEntity.ok(productRepo.findRandomProducts());
	}
	
	@Override
	public ResponseEntity<Object> getFilteredProducts(String title,String brand,Boolean sale,
			Integer discount,String color,String size, Float price,Integer quantity  ) {
					
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return ResponseEntity.ok(productRepo.customFindMethod(objectMapper.writeValueAsString(new ProductFilterPojo(title, brand, sale, discount, color, size, discount, quantity))));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}		
		
	}
	
	private Product pojoToProduct(ProductPojo productPojo,boolean check) {
		Product product = new Product();
		product.setTitle(productPojo.getTitle());
		product.setDescription(productPojo.getDescription());
		product.setType(productPojo.getType());
		
		if(productPojo.getCollections() != null && productPojo.getCollections().size() > 0) {
			List<Collection> collections = new ArrayList<>();
			for(int i=0;i<productPojo.getCollections().size();i++)
				collections.add(collectionRepo.findById(productPojo.getCollections().get(i)).get());
			product.setCollections(collections);
		}
		if(productPojo.getCategory() != null && productPojo.getCategory().size() > 0) {
			List<Category> categories = new ArrayList<>();
			for(int i=0;i<productPojo.getCategory().size();i++)
				categories.add(categoryRepo.findById(productPojo.getCategory().get(i)).get());
			product.setCategory(categories);
			product.setCategory(categories);
		}	
		

		product.setEtat(productPojo.isEtat());
		Optional<Brand> _brand = brandRepo.findByName(productPojo.getBrand());
		if( check && !_brand.isPresent()) {
			return null;
		}
		product.setBrand(_brand.get().getName());
		
		return product;

	}
	
	private Product pojoToProduct(ProductPojo productPojo) {
		if(!productRepo.findProductByTitle(productPojo.getTitle()).isPresent()){
			return null;
		}
		Product product = productRepo.findProductByTitle(productPojo.getTitle()).get();
		product.setTitle(productPojo.getTitle());
		product.setDescription(productPojo.getDescription());
		if(!typeExist(productPojo.getType())) {
			return null;
		}
		product.setType(productPojo.getType());
		
		if(productPojo.getCollections() != null && productPojo.getCollections().size() > 0) {
			List<Collection> collections = new ArrayList<>();
			for(int i=0;i<productPojo.getCollections().size();i++)
				collections.add(collectionRepo.findById(productPojo.getCollections().get(i)).get());
			product.setCollections(collections);
		}
		if(productPojo.getCategory() != null && productPojo.getCategory().size() > 0) {
			List<Category> categories = new ArrayList<>();
			for(int i=0;i<productPojo.getCategory().size();i++)
				categories.add(categoryRepo.findById(productPojo.getCategory().get(i)).get());
			product.setCategory(categories);
		}	
		

		product.setEtat(productPojo.isEtat());
		Optional<Brand> _brand = brandRepo.findByName(productPojo.getBrand());
		if(!_brand.isPresent()) {
			return null;
		}
		product.setBrand(_brand.get().getName());
		
		return product;

	}
	
	private Product toProduct(String strProduct) throws ParseException {
		Product product = new Product();
		JSONParser js = new JSONParser(strProduct);
		HashMap<String, Object> _product = (HashMap<String, Object>)js.parse();
		int brand_id = ((BigInteger)_product.get("brand_id")).intValue();
		Optional<Brand> _brand = brandRepo.findById(brand_id);
		if(!_brand.isPresent()) {
			return null;
		}
		product.setTitle((String)_product.get("title"));
		product.setDescription((String)_product.get("description"));
		product.setType((String)_product.get("type"));
		product.setCollections((List<Collection>)_product.get("collection"));
		product.setCategory((List<Category>)_product.get("category"));
		product.setBrand(_brand.get().getName());
		
		return product;
		
	}

	@Override
	public ResponseEntity<Object> getSingleProductImage(int id) {

		Optional<Product> product = productRepo.findById(id);
		if(!product.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		Product _product = product.get();
		Set<Variant> variant =   _product.getVariants();
		if(variant.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		Variant vr = (Variant)(variant.toArray()[0]);
		Set<ProductImage> productImages = vr.getImages();
		ProductImage prImage = (ProductImage) (productImages.toArray()[0]);
		System.out.println("product image : "+prImage.getSrc());
		HashMap<String, String> res = new HashMap<>();
		
		try {
			res.put("src", storageService.downloadProductImage(prImage.getId()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
		
		return ResponseEntity.ok(res);
	}

	

	private boolean typeExist(String type) {
		return typeRepo.findByName(type).isPresent();
	}

	@Override
	public ResponseEntity<Object> deleteByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Object> updateProductAndVariants(ProductVariantsPojo productVariantsPojo) {
		int id = 0;
		
		try {
			Optional<Product> product = productRepo.findProductByTitle(productVariantsPojo.getTitle());
			if(!product.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			if(!typeExist(productVariantsPojo.getType() )) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			Optional<Brand> _brand = brandRepo.findByName(productVariantsPojo.getBrand());
			if(!_brand.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			
			 Product _product = product.get();
			_product.setTitle(productVariantsPojo.getTitle());
			_product.setType(productVariantsPojo.getType());
			_product.setEtat(productVariantsPojo.isEtat());
			_product.setTitle(productVariantsPojo.getTitle());
			_product.setBrand(productVariantsPojo.getBrand());
			_product.setDescription(productVariantsPojo.getDescription());
			id = productRepo.save(_product).getId();
			variantRepo.deleteProductVariants(_product.getId());
			List<VariantPojo> variantPojos = productVariantsPojo.getVariants();
			System.out.println("length of variants : "+variantPojos.size());
			variantPojos.forEach(variant -> {
				
//				Variant _variant = variantRepo.save(new Variant(variant.getSku(), variant.getSize(), variant.getColor(), variant.getPrice(),
//						variant.getSale(), variant.getDiscount(), variant.getStock(), variant.getQuantity(), null, _product));
				Variant _variant = variantRepo.save(new Variant(variant.getSku(), variant.getSize(), variant.getColor(), variant.getPrice(),
						variant.getSale(), variant.getDiscount(), variant.getStock(), variant.getQuantity(),variant.getVar1(),
						variant.getVar2(),variant.getVar3(),variant.getVar4(),variant.getVar5(),null, _product));
				Variant _variant1 =  variantRepo.save(_variant);
				System.out.println("length of images : "+variant.getImages().size());
				Set<ProductImage> productImages = new HashSet<>();
				variant.getImages().forEach(image -> {
					String filePath = storageService.uploadBase64ProductImage(image.getSrc());
					if(filePath != null) {
						productImages.add(productImageRepo.save(new ProductImage(0,filePath, _product.getTitle()+" "+ variant.getColor(), _variant)));
					}
				});
				_variant1.setImages(productImages);
				_variant1 = variantRepo.save(_variant1);
			});
		} catch (Exception e) {
			// TODO: handle exception
			productRepo.deleteById(id);
			System.out.println(e);
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
		return ResponseEntity.ok(productRepo.findProductByTitle(productVariantsPojo.getTitle()));
	}

	@Override
	public ResponseEntity<Object> addProductV2(ProductVariantsPojo productVariantsPojo) {
		int id = 0;
		try {
			Optional<Product> product = productRepo.findProductByTitle(productVariantsPojo.getTitle());
			if(product.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A product with this same name already exist");
			}
			if(!typeExist(productVariantsPojo.getType() )) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Given type does not exist");
			}
			Optional<Brand> _brand = brandRepo.findByName(productVariantsPojo.getBrand());
			if(!_brand.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Given brand name does not exist");
			}
			
			System.out.println("--------------- Start adding product----------------------");
			 Product _product = new Product();
			_product.setTitle(productVariantsPojo.getTitle());
			_product.setType(productVariantsPojo.getType());
			_product.setEtat(productVariantsPojo.isEtat());
			_product.setTitle(productVariantsPojo.getTitle());
			_product.setBrand(productVariantsPojo.getBrand());
			_product.setDescription(productVariantsPojo.getDescription());
			id = productRepo.save(_product).getId();
			System.out.println("--------------- Product added----------------------");
			List<VariantPojo> variantPojos = productVariantsPojo.getVariants();
			System.out.println("length of variants : "+variantPojos.size());
			variantPojos.forEach(variant -> {
				Variant _variant = variantRepo.save(new Variant(variant.getSku(), variant.getSize(), variant.getColor(), variant.getPrice(),
						variant.getSale(), variant.getDiscount(), variant.getStock(), variant.getQuantity(),
						variant.getVar1(),variant.getVar2(),variant.getVar3(),variant.getVar4(),variant.getVar5(),null, _product));
				Variant _variant1 =  variantRepo.save(_variant);
				System.out.println("length of images : "+variant.getImages().size());
				Set<ProductImage> productImages = new HashSet<>();
				variant.getImages().forEach(image -> {
					if(image.getSrc() == null || image.getSrc().isEmpty()) 
						return;
					String filePath = storageService.uploadBase64ProductImage(image.getSrc());
					if(filePath != null) {
						productImages.add(productImageRepo.save(new ProductImage(0, filePath,_product.getTitle()+" "+ variant.getColor(), _variant)));
					}
				});
				_variant1.setImages(productImages);
				_variant1 = variantRepo.save(_variant1);
			});
		} catch (Exception e) {
			// TODO: handle exception
			productRepo.deleteById(id);
			System.out.println(e);
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
		return ResponseEntity.ok(productRepo.findProductByTitle(productVariantsPojo.getTitle()));
	}
	
}
