package com.electronicshop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electronicshop.pojos.OrderPojo;
import com.electronicshop.service.impl.OrderService;

@RestController
@RequestMapping("order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/all")
	ResponseEntity<Object> getAllOrders(){
		return orderService.getAllOrders();
	}
	
	@PostMapping("/add")
	ResponseEntity<Object> addOrder(@RequestBody @Valid OrderPojo orderPojo){
		return orderService.addOrder(orderPojo);
	}
	
	@PutMapping("/cancel/{id}")
	ResponseEntity<Object> cancelOrder(@PathVariable int id){
		return orderService.cancelOrder(id);
	}

}
