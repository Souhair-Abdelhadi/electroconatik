package com.electronicshop.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "roles")
public class Role {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true,nullable = false,length = 40)
	@NonNull
	private String name;
	
	
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Role(@NonNull String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
