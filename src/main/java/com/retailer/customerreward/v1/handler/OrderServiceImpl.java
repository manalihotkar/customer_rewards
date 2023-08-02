package com.retailer.customerreward.v1.handler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailer.customerreward.entity.CustomerVO;
import com.retailer.customerreward.entity.OrderVO;
import com.retailer.customerreward.exception.CustomerRewardException;
import com.retailer.customerreward.model.v1.Order;
import com.retailer.customerreward.repository.CustomerRepository;
import com.retailer.customerreward.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

	OrderRepository orderRepository;
	
	CustomerRepository customerRepository;
	
	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository) {
		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
	}


	@Override
	public Order saveOrder(Order order, Integer customerIdentifier) throws CustomerRewardException {
		try {
			CustomerVO vo = customerRepository.findById(customerIdentifier).get();
			if(vo == null) {
				throw new CustomerRewardException("Customer not found for Id "+ customerIdentifier);
			}
			OrderVO orderVO = new OrderVO();
			Random ran = new Random();
			Integer x = ran.nextInt(6) + 5;
			orderVO.setOrderId(x);
			orderVO.setCustomerId(customerIdentifier);
			orderVO.setOrderAmount(order.getOrderPrice().doubleValue());
			orderVO.setOrderDate(LocalDate.now());
			OrderVO savedOrderVO =  orderRepository.save(orderVO);
			log.info("Order saved for customer id {}", customerIdentifier);
			return setOrderDetails(savedOrderVO);
		} catch(Exception ex) {
			log.error("Exception while saving customer details"+ ex.getMessage());
			throw new CustomerRewardException("Exception while saving order details "+ ex.getMessage());
		}
	}


	private Order setOrderDetails(OrderVO savedOrderVO) {
		Order order = new Order();
		order.setCustomerIdentifier(savedOrderVO.getCustomerId());
		order.setOrderIdentifier(savedOrderVO.getOrderId());
		order.setOrderPrice(BigDecimal.valueOf(savedOrderVO.getOrderAmount()));
		order.setOrderDate(savedOrderVO.getOrderDate().toString());
		return order;
	}

}
