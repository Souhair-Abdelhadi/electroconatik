package com.electronicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronicshop.compositeKey.OrderProductKey;
import com.electronicshop.entities.OrderProduct;

public interface OrderProductRepo extends JpaRepository<OrderProduct, OrderProductKey> {

}
