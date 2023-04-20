package com.electronicshop.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.electronicshop.compositeKey.OrderProductKey;
import com.electronicshop.entities.Order;
import com.electronicshop.entities.OrderProduct;
import com.electronicshop.entities.User;
import com.electronicshop.entities.Variant;
import com.electronicshop.pojos.OrderPojo;
import com.electronicshop.pojos.UpdatePaiement;
import com.electronicshop.pojos.UpdateShippingadress;
import com.electronicshop.repository.OrderProductRepo;
import com.electronicshop.repository.OrderRepo;
import com.electronicshop.repository.UserRepo;
import com.electronicshop.repository.VariantRepo;
import com.electronicshop.service.IOrder;

@Service
public class OrderService implements IOrder {

	 @Autowired
	 private OrderRepo orderRepo;
	 
	 @Autowired
	 private VariantRepo variantRepo;
	 
	 @Autowired
	 private UserRepo userRepo;
	 
	 @Autowired
	 private OrderProductRepo orderProductRepo;
	 
	
	@Override
	public ResponseEntity<Object> getAllOrders() {

		return ResponseEntity.ok(orderRepo.findAll());
	}

	@Override
	public ResponseEntity<Object> addOrder(OrderPojo orderPojo) {
		
		Optional<Variant> variant = variantRepo.findById(orderPojo.getVariant_id());
		HashMap<String, String> res = new HashMap<>();
		if(!variant.isPresent()) {
			res.put("message", "No variant found with the given id");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
		Variant _variant = variant.get();
		if(_variant.getQuantity() < orderPojo.getOrder_quantity()) {
			res.put("message", "The current quantity is less than the commanded quantity");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
		
		Optional<User> user = userRepo.findById(orderPojo.getUser_id());
		if(!user.isPresent()) {
			res.put("message", "No user found with the given id");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
		
		_variant.setQuantity(_variant.getQuantity() - orderPojo.getOrder_quantity());
		variantRepo.save(_variant);
		float totalPrice = orderPojo.getOrder_quantity() * variant.get().getPrice();
		Order order =new Order(orderPojo.getShippingAdress(), 0, 0, totalPrice, orderPojo.getPaymentMethodType(), false, null, Date.valueOf(LocalDate.now()));
		order.setUser(user.get());
		order = orderRepo.save(order);
		OrderProduct orderProduct = new OrderProduct(new OrderProductKey(order.getId(), _variant.getId()), orderPojo.getOrder_quantity(), order, _variant);
		
		orderProduct = orderProductRepo.save(orderProduct);
		Set<OrderProduct> list = order.getOrderProducts();
		if(list == null) {
			list = new HashSet<>();
		}
		list.add(orderProduct);
		order.setOrderProducts(list);
		
		return ResponseEntity.ok(orderRepo.save(order));
	}

	@Override
	public ResponseEntity<Object> updateOrder(Order order) {

		Optional<Order> _order = orderRepo.findById(order.getId());
		if(!_order.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		return ResponseEntity.ok(orderRepo.save(order));
	}

	@Override
	public ResponseEntity<Object> updatePaiement(UpdatePaiement updatePaiement) {

		Optional<Order> _order = orderRepo.findById(updatePaiement.getOrderId());
		if(!_order.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		Order order = _order.get();
		order.setPaid(updatePaiement.getIsPaid());
		
		return ResponseEntity.ok(orderRepo.save(order));
	}

	@Override
	public ResponseEntity<Object> UpdateShippingAdress(UpdateShippingadress shippingadress) {
		Optional<Order> _order = orderRepo.findById(shippingadress.getOrderId());
		if(!_order.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		Order order = _order.get();
		order.setShippingAdress(shippingadress.getAddress());;
		
		return ResponseEntity.ok(orderRepo.save(order));
	
	}

	@Override
	public ResponseEntity<Object> cancelOrder(int id) {

		Optional<Order> _order = orderRepo.findById(id);
		if(!_order.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		Order order = _order.get();
		if(order.isCancelded()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		if(order.isDelivred()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		order.getOrderProducts()
		.forEach(item -> {
			Optional<Variant> variant = variantRepo.findById(item.getVariant().getId());
			if(variant.isPresent()) {
				Variant _variant = variant.get();
				_variant.setQuantity(_variant.getQuantity() + item.getQuantity());
				variantRepo.save(_variant);
			}
		});
		
		order.setCancelded(true);
		order.setIpdatedAt(Date.valueOf(LocalDate.now()));
		
		
		return ResponseEntity.ok(orderRepo.save(order));
	}

}
