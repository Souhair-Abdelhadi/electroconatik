package com.electronicshop.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandPojo {

	
	private int id;
	
	private String name;
	
	private String image;

	public BrandPojo(String name) {
		super();
		this.name = name;
	}
	
	
	
	
}
