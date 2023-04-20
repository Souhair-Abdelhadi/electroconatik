package com.electronicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronicshop.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
