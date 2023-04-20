package com.electronicshop.pojos;

import java.sql.Date;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderPojo {

	
	@NotNull
	private Integer variant_id;
	
	@NotNull
	private Integer order_quantity;
	
	@NotNull
	private Long user_id;
	
	@NotNull
	private String shippingAdress;

	private float shippingPrice;
	
	@NotNull
	private String paymentMethodType;
	
	
	
	
	
}
