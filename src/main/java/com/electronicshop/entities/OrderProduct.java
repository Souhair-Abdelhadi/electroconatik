package com.electronicshop.entities;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.electronicshop.compositeKey.OrderProductKey;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(
		name = "order_product"
		)
public class OrderProduct {
	
	@EmbeddedId
	private OrderProductKey id;
	
	@NotNull
	@Column(nullable = false)
	private int quantity;
	
	@ManyToOne
	@MapsId("order_id")
	@JoinColumn(name = "order_id")
	@JsonBackReference
	private Order order;
	
	@ManyToOne
	@MapsId("variant_id")
	@JoinColumn(name = "variant_id")
	@JsonBackReference
	private Variant variant;
	
	

}
