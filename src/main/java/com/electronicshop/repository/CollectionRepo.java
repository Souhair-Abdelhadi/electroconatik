package com.electronicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronicshop.entities.Collection;

public interface CollectionRepo extends JpaRepository<Collection, Integer> {

}
