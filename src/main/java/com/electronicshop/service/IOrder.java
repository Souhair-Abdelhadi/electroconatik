package com.electronicshop.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import com.electronicshop.entities.Order;
import com.electronicshop.pojos.OrderPojo;
import com.electronicshop.pojos.UpdatePaiement;
import com.electronicshop.pojos.UpdateShippingadress;

public interface IOrder {
	
	ResponseEntity<Object> getAllOrders();
	
	ResponseEntity<Object> addOrder(OrderPojo orderPojo);
	
	ResponseEntity<Object> updateOrder(Order order);
	
	ResponseEntity<Object> updatePaiement( UpdatePaiement updatePaiement );
	
	ResponseEntity<Object> UpdateShippingAdress(UpdateShippingadress shippingadress);
	
	ResponseEntity<Object> cancelOrder(int id);
	

}
