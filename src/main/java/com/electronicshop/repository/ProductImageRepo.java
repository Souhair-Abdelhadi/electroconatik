package com.electronicshop.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.electronicshop.entities.ProductImage;

public interface ProductImageRepo extends JpaRepository<ProductImage, Integer> {

	@Transactional
	@Modifying
	@Query(value="delete from variant_images where variant_id = :variant_id",nativeQuery = true)
	void deleteProductImagesOfVariant(@Param("variant_id") int variant_id);
}
