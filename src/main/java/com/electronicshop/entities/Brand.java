package com.electronicshop.entities;

import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
		name = "brands"
		)
@Builder
public class Brand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false,unique = true)
	private String name;
	
	private String slug;
	
	private String image;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	@Column(columnDefinition = "boolean default 0")
	private boolean deleted;
	
//	 @OneToMany(mappedBy = "brand")
//	 @JsonIgnore
//	 private Set<Product> produits =  new HashSet<>();

	public Brand(String name, String slug, String image, Date createdAt) {
		super();
		this.name = name;
		this.slug = slug;
		this.image = image;
		this.createdAt = createdAt;
	}
	 
	 
	
}
