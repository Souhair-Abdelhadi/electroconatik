package com.electronicshop.pojos;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.electronicshop.entities.Category;
import com.electronicshop.entities.Collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ProductPojo {

	@NotNull
	private String title ;
	@NotNull
	private String description;
	@NotNull
	private String type ;

	private List<Integer> collections;

	private List<Integer> category ;
	@NotNull
	private boolean etat;
	@NotNull
	private String brand;
	
	public ProductPojo(@NotNull String title, @NotNull String description, @NotNull String type,
			List<Integer> collections, List<Integer> category, @NotNull boolean etat, @NotNull String brand) {
		super();
		this.title = title;
		this.description = description;
		this.type = type;
		this.collections = collections;
		this.category = category;
		this.etat = etat;
		this.brand= brand;
	}
	
	public ProductPojo(@NotNull String title, @NotNull String description, @NotNull String type, @NotNull boolean etat, @NotNull String brand) {
		super();
		this.title = title;
		this.description = description;
		this.type = type;
		this.etat = etat;
		this.brand= brand;
	}
	
	
	
	
}
