package com.retailer.customerreward.v1.controller;

import java.math.BigDecimal;

import com.retailer.customerreward.exception.CustomerRewardException;
import com.retailer.customerreward.model.v1.Customer;
import com.retailer.customerreward.model.v1.Order;

import lombok.extern.slf4j.Slf4j;

/***
 * Parent class added for validations.
 * @author manali
 *
 */
@Slf4j
public class BaseController {
	
	public boolean validateCustomerDetails(Customer customer) throws CustomerRewardException {
		if(customer == null || customer.getEmail() == null || customer.getName() == null){
			log.error("Customer email, name and orderPrice is missing");
			return false;
		}
		return true;
	
	}
	
	public boolean validateOrderDetails(Order order) throws CustomerRewardException {
		if(order == null){
			log.error("Order details is missing");
			return false;
		}else if(order != null && order.getOrderPrice().compareTo(BigDecimal.ZERO) <= 0) {
			log.error("Customer orderPrice must be above 0");
			throw new CustomerRewardException("Invalid order price Details");
		}
		return true;
	
	}

}
