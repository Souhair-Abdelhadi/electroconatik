package com.electronicshop.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.electronicshop.entities.Variant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantsPojo {

	private Integer id;
	private String title ;
	private String description;
	private String type ;

	private List<Integer> collections= new ArrayList<>();
	private List<Integer> tags = new ArrayList<>();
	private List<Integer> category = new ArrayList<>() ;
	private boolean etat;
	private String brand;
	
	List<VariantPojo> variants = new ArrayList<>();



	@Override
	public String toString() {
		return "ProductVariantsPojo [title=" + title + ", description=" + description + ", type=" + type
				+ ", collections=" + collections + ", category=" + category + ", etat=" + etat + ", brand=" + brand
				+ ", variants=" + variants + "]";
	}
	
	
	
}
