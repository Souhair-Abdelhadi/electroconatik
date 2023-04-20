package com.electronicshop.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.electronicshop.entities.Type;

public interface TypeRepo extends JpaRepository<Type, Integer> {

	Optional<Type> findByName(String name);
	
	
	@Transactional
	@Modifying
	void deleteByName(String name);
}
