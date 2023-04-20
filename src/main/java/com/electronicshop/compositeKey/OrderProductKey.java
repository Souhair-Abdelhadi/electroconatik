package com.electronicshop.compositeKey;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class OrderProductKey implements Serializable {

	private static final long serialVersionUID = -8537153430351064455L;

	private int order_id;
	
	private int variant_id;
	
	
	
	
}
