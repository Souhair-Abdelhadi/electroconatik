package com.electronicshop.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.electronicshop.entities.Brand;
import com.electronicshop.pojos.BrandPojo;

public interface BrandRepo extends JpaRepository<Brand, Integer>  {

	@Query(value = "SELECT * FROM brands b WHERE b.name = :name",nativeQuery = true)
	Optional<Brand> findByName(String name);
	
	Optional<Brand> findByImage(String filePath);
		
	@Query(value = "SELECT * FROM brands b WHERE b.deleted = false",nativeQuery = true)
	List<Brand> findAll();
	

}
