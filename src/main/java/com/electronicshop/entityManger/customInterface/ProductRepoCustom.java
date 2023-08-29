package com.electronicshop.entityManger.customInterface;

import java.util.List;

import com.electronicshop.entities.Brand;
import com.electronicshop.entities.Product;
import com.electronicshop.pojos.FilterProductsPojo;
import com.electronicshop.pojos.ProductFilterPojo;
import com.electronicshop.pojos.ProductPojo;

public interface ProductRepoCustom {

	List<Product> customFindMethod(String filter);
	
	List<Product> customFindMethod(FilterProductsPojo filterProductsPojo);
	
}

//String title,Brand brand,Boolean etat,
//Integer discount,String color,String size, Float price,Integer quantity