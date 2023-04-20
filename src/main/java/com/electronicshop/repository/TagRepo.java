package com.electronicshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronicshop.entities.Tag;

public interface TagRepo extends JpaRepository<Tag, Integer> {

	Optional<Tag> findByNom(String nom);
	
	
}
