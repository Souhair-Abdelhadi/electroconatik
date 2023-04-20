package com.electronicshop.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductEmbeddedKeyPojo {

	private Integer product_id;
	private Integer order_id;
	
}
