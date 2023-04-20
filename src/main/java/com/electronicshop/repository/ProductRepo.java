package com.electronicshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.electronicshop.entities.Product;
import com.electronicshop.entityManger.customInterface.ProductRepoCustom;

@Transactional
@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>,ProductRepoCustom  {
	
	@Query(value = "select * from products ORDER by FLOOR(rand() * 16) LIMIT 15;",nativeQuery = true)
	List<Product> findRandomProducts();
	
	@Query(value = "CALL getFiltredList(:title,:etat);",nativeQuery = true)
	List<Product> filterProductsByOptions(@Param("title") String title,@Param("etat") Boolean etat);
	
	@Query(value = "SELECT * FROM products p WHERE p.title = :title ;",nativeQuery = true)
	Optional<Product> findProductByTitle(@Param("title") String title);
	
	@Modifying
	@Query(value = "delete products p WHERE p.title = :title ;",nativeQuery = true)
	void deleteByTitle(@Param("title") String title);
	
	
	

	
}
