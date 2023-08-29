package com.electronicshop.entityManger.entity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.electronicshop.entities.Brand;
import com.electronicshop.entities.Product;
import com.electronicshop.entityManger.customInterface.ProductRepoCustom;
import com.electronicshop.pojos.FilterProductsPojo;
import com.electronicshop.pojos.ProductFilterPojo;
import com.electronicshop.pojos.ProductPojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class ProductRepoImpl implements ProductRepoCustom {

	@PersistenceContext
    private EntityManager entityManager;
	
	
	@Override
	public List<Product> customFindMethod(String filter) {
		System.out.println(filter);
		ObjectMapper objectMapper = new ObjectMapper();

		ProductFilterPojo productFilterPojo = null;
		try {
			productFilterPojo = objectMapper.readValue(filter, ProductFilterPojo.class);
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query = "select p from Product p,Variant v where p.id = v.produit.id AND 1=1   ";
		
		String conditions = "";
		
		System.out.println("title : "+ productFilterPojo.getTitle());
		
		if(productFilterPojo.getTitle() != null && productFilterPojo.getTitle().trim().length() > 0 ) {
			System.out.println("inside title condition");
			conditions += " AND p.title='%"+productFilterPojo.getTitle()+"%' ";
		}
		if(productFilterPojo.getBrand() != null ) {
			conditions+=" AND b.name='%"+productFilterPojo.getBrand()+"%' ";
		}
		if(productFilterPojo.getEtat() != null   ) {
			conditions+=" AND v.sale="+productFilterPojo.getEtat();
		}
		if(productFilterPojo.getDiscount()  != null && productFilterPojo.getDiscount()  > 0) {
			conditions+=" AND v.discount="+productFilterPojo.getDiscount() ;
		}
		if( productFilterPojo.getPrice()  != null && productFilterPojo.getPrice() > 0) {     
			conditions+=" AND v.price="+productFilterPojo.getPrice();
		}
		if( productFilterPojo.getQuantity() != null && productFilterPojo.getQuantity() > 0) {
			conditions+=" AND v.quantity="+productFilterPojo.getQuantity();
		}
		if(productFilterPojo.getColor() != null && productFilterPojo.getColor().trim().length() > 0) {
			conditions+=" AND v.color='"+productFilterPojo.getColor()+"' ";
		}
		if(productFilterPojo.getSize() != null && productFilterPojo.getSize().trim().length() > 0 ) {
			conditions+=" AND v.size='"+productFilterPojo.getSize()+"' ";
		}	
		
		query = query + conditions;
		
		System.out.println("conditions : "+conditions);
		System.out.println("query : "+query);
		
		return (List<Product>) entityManager.createQuery(query,Product.class).getResultList();
		
		
	}


	@Override
	public List<Product> customFindMethod(FilterProductsPojo filterProductsPojo) {
		System.out.println(filterProductsPojo.toString());

		
		String query = "select DISTINCT(p) from Product p,Variant v where p.id = v.produit.id AND 1=1   ";
		
		String conditions = "";
		
		System.out.println("title : "+ filterProductsPojo.getTitle());
		
		if(filterProductsPojo.getTitle() != null && filterProductsPojo.getTitle().trim().length() > 0 ) {
			System.out.println("inside title condition");
			conditions += " AND p.title like'%"+filterProductsPojo.getTitle().trim()+"%' ";
		}
		if(filterProductsPojo.getBrand() != null && filterProductsPojo.getBrand().trim().length() > 0 ) {
			conditions+=" AND p.brand like '%"+filterProductsPojo.getBrand().trim()+"%' ";
		}
		if(filterProductsPojo.getType() != null && filterProductsPojo.getType().trim().length() > 0 ) {
			conditions+=" AND p.type like'%"+filterProductsPojo.getType().trim()+"%' ";
		}
		if(filterProductsPojo.getNouveau() != null && filterProductsPojo.getNouveau() ) {
			conditions+=" AND v.sale="+filterProductsPojo.getNouveau();
		}
		
		query = query + conditions;
		
		System.out.println("conditions : "+conditions);
		System.out.println("query : "+query);
		
		List<Product> products =  (List<Product>) entityManager.createQuery(query,Product.class).getResultList();
		System.out.println("products searched size : "+products.size());
		return products;
	}

	

}
