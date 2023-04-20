package com.electronicshop.entityManger.customInterface;

import java.util.List;

import com.electronicshop.entities.Brand;
import com.electronicshop.entities.Product;
import com.electronicshop.pojos.ProductFilterPojo;
import com.electronicshop.pojos.ProductPojo;

public interface ProductRepoCustom {

	List<Product> customFindMethod(String filter);
	
}

//String title,Brand brand,Boolean etat,
//Integer discount,String color,String size, Float price,Integer quantity