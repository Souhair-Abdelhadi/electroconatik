package com.electronicshop.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
		name = "variants"
		)
public class Variant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String sku;
	
	private String size;
	
	@NotNull
	private String color;
	
	@NotNull
	private float price;
	
	@Column(columnDefinition = "Boolean default 0")
	private boolean sale;
	
	@Column(columnDefinition = "Integer default 0")
	private int discount;
	
	@Column(length = 100,columnDefinition = "Integer default 0")
	private int stock;
	
	@NotNull
	@Column(columnDefinition = "Integer default 0")
	private int quantity;
	
	@Column(nullable = true)
	private String var1;
	@Column(nullable = true)
	private String var2;
	@Column(nullable = true)
	private String var3;
	@Column(nullable = true)
	private String var4;
	@Column(nullable = true)
	private String var5;
	
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "variant")
	@JsonManagedReference
	private Set<ProductImage> images;
	
	@ManyToOne
	@JoinColumn(name = "produit_id",nullable = false)
	@JsonBackReference
	private Product produit;
	
	@OneToMany(mappedBy = "variant")
	@JsonManagedReference
	private Set<OrderProduct> orderProducts = new HashSet<OrderProduct>();


	public Variant( String sku,  String size, @NotNull String color, @NotNull float price,
			 boolean sale,  int discount,  int stock, @NotNull int quantity,
			Set<ProductImage> images, Product produit) {
		super();
		this.sku = sku;
		this.size = size;
		this.color = color;
		this.price = price;
		this.sale = sale;
		this.discount = discount;
		this.stock = stock;
		this.quantity = quantity;
		this.images = images;
		this.produit = produit;
	}


	public Variant( String sku,  String size, @NotNull String color, @NotNull float price,
			 boolean sale,  int discount,  int stock, @NotNull int quantity, String var1,
			String var2, String var3, String var4, String var5, Set<ProductImage> images, Product produit) {
		super();
		this.sku = sku;
		this.size = size;
		this.color = color;
		this.price = price;
		this.sale = sale;
		this.discount = discount;
		this.stock = stock;
		this.quantity = quantity;
		this.var1 = var1;
		this.var2 = var2;
		this.var3 = var3;
		this.var4 = var4;
		this.var5 = var5;
		this.images = images;
		this.produit = produit;
	}
	
	
	
	
	
	
	
}
