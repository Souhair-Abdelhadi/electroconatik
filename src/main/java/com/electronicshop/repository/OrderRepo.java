package com.electronicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronicshop.entities.Order;

public interface OrderRepo extends JpaRepository<Order, Integer> {

}
