package com.electronicshop.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
		name = "tags"
		)
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true,length = 30)
	@NotNull
	private String nom;
	
	@ManyToMany(mappedBy = "tags",fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Product> produits = new HashSet<>();

	public Tag(@NotNull String nom) {
		super();
		this.nom = nom;
	}
	
	
	
	
}
