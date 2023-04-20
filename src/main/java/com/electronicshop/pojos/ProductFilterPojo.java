package com.electronicshop.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterPojo {

	private String title;
	private String brand;
	private Boolean etat;
	private Integer discount;
	private String color;
	private String size;
	private Integer price;
	private Integer quantity;
	
	
}
