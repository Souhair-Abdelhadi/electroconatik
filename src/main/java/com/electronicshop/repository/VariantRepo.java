package com.electronicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.electronicshop.entities.Variant;

public interface VariantRepo extends JpaRepository<Variant, Integer> {

	@Transactional
	@Modifying
	@Query(value="delete from variants where produit_id = :id",nativeQuery = true)
	void deleteProductVariants(@Param("id") int id);

}
