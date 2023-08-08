package com.retailer.customerreward.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailer.customerreward.exception.CustomerRewardException;
import com.retailer.customerreward.model.v1.Order;
import com.retailer.customerreward.v1.handler.OrderService;

import lombok.extern.slf4j.Slf4j;
/**
 * Controller class to create order.
 * @author manali
 *
 */
@Slf4j
@RestController
@RequestMapping("/retailer/v1/customer")
public class OrderController extends BaseController{
	
	private OrderService orderService;
	
	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}


	@PostMapping("/{customerIdentifier}/order")
	public Order saveOrder(@Valid @RequestBody Order order, @PathVariable Integer customerIdentifier) throws Exception{
		
		if(customerIdentifier!= null && validateOrderDetails(order)) {
			log.info("OrderController validation success for customer {}", customerIdentifier);
			return orderService.saveOrder(order, customerIdentifier);
		}else {
			throw new CustomerRewardException("Invalid order Details");
		}
	}

}
