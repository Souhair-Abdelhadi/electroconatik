package com.electronicshop.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterProductsPojo {

	private String title;
	private String brand;
	private String type;
	private Boolean nouveau;
	
	@Override
	public String toString() {
		return "FilterProductsPojo [title=" + title + ", brand=" + brand + ", type=" + type + "]";
	}
	
	
}
