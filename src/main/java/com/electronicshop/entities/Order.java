package com.electronicshop.entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
		name = "orders"
		)
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	@Column(nullable = false)
	private String shippingAdress;
	@NotNull
	@Column(nullable = false,columnDefinition = "float default 0")
	private float taxPrice;
	@Column(nullable = false,columnDefinition = "float default 0")
	private float shippingPrice;
	@NotNull
	@Column(nullable = false)
	private float totalOrderPrice;
	@NotNull
	@Column(nullable = false)
	private String paymentMethodType;
	@Column(nullable = false,columnDefinition = "boolean default 0")
	private boolean isPaid;
	
	private Date paidAt;
	
	@Column(nullable = false,columnDefinition = "boolean default 0")
	private boolean isDelivred;
	
	private Date delivredAt;
	
	private Date createdAt;
	
	private Date ipdatedAt;
	
	@Column(nullable = false,columnDefinition = "boolean default 0")
	private boolean cancelded;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "order")
	@JsonManagedReference
	private Set<OrderProduct> orderProducts = new HashSet<OrderProduct>();

	public Order(@NotNull String shippingAdress, @NotNull float taxPrice, float shippingPrice,
			@NotNull float totalOrderPrice, @NotNull String paymentMethodType, boolean isPaid, Date paidAt,
			Date createdAt) {
		super();
		this.shippingAdress = shippingAdress;
		this.taxPrice = taxPrice;
		this.shippingPrice = shippingPrice;
		this.totalOrderPrice = totalOrderPrice;
		this.paymentMethodType = paymentMethodType;
		this.isPaid = isPaid;
		this.paidAt = paidAt;
		this.createdAt = createdAt;
	}

	

}
