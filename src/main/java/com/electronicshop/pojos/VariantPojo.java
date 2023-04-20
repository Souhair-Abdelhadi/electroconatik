package com.electronicshop.pojos;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VariantPojo {

	private Integer id;
	private String color;
	private Integer discount;
	private Float price;
	private Integer quantity;
	private Boolean sale;
	private String size;
	private String sku;
	private Integer stock;
	private List<ProductImagePojo> images = new ArrayList<>();
	private String var1;
	private String var2;
	private String var3;
	private String var4;
	private String var5;
	private List<OrderProductPojo> orderProducts = new ArrayList<>();
	
	
	
}
