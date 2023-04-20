package com.electronicshop.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
		name = "products"
		)
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private int id;
	
	@NotNull
	@Column(length = 50,nullable = false,unique = true)
	private String title;
	
	@NotNull
	@Column(length = 1000)
	private String description;
	
	@NotNull
	@Column(length = 50,nullable = false)
	private String type;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "product_collections",
			joinColumns = @JoinColumn(name = "product_id"),
			inverseJoinColumns = @JoinColumn(name = "collection_id")
			)
	private List<Collection> collections = new ArrayList<>() ;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "product_categories",
			joinColumns = @JoinColumn(name = "product_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id")
			)
	private List<Category> category = new ArrayList<>() ;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "product_tag",
			joinColumns = @JoinColumn(name = "product_id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id")
			)
	private Set<Tag> tags = new HashSet<>();
	
	@OneToMany(mappedBy = "produit")
	@JsonManagedReference
	private Set<Variant> variants = new HashSet<>();
			
	@Column(nullable = false,columnDefinition = "boolean default 1")
	private boolean etat;
			
//	@ManyToOne
//	@JoinColumn(name = "brand_id")
//	private Brand brand;
	
	private String brand;
	

	public Product(@NotNull String title, @NotNull String description, @NotNull String type, boolean etat, String brand) {
		super();
		this.title = title;
		this.description = description;
		this.type = type;
		this.etat = etat;
		this.brand = brand;
	}
	
	
	
	
	
	
	
	
}
