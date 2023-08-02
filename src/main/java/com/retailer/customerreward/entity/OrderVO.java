package com.retailer.customerreward.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name ="order")
public class OrderVO {
	@Id
	@Column(name="ORDER_ID")
	private Integer orderId;
	
	@Column(name="CUSTOMER_ID")
	private Integer customerId;
	
	@Column(name="ORDER_AMOUNT")
	private Double orderAmount;
	
	@Column(name="ORDER_DATE")
	private LocalDate orderDate;
}
