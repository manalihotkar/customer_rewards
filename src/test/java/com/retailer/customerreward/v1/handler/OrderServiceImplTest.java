package com.retailer.customerreward.v1.handler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.retailer.customerreward.entity.CustomerVO;
import com.retailer.customerreward.entity.OrderVO;
import com.retailer.customerreward.exception.CustomerRewardException;
import com.retailer.customerreward.model.v1.Order;
import com.retailer.customerreward.repository.CustomerRepository;
import com.retailer.customerreward.repository.OrderRepository;



@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
	
	OrderService orderService;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@Mock
	private OrderRepository orderRepository;
	
	private OrderVO orderVO;
	
	private CustomerVO vo;
	
	private Order order;
	
	@BeforeEach
	public void setUp() {
		orderService = new OrderServiceImpl(orderRepository, customerRepository);		
		orderVO = new OrderVO();
		orderVO.setCustomerId(1);
		orderVO.setOrderAmount(120.0);
		orderVO.setOrderDate(LocalDate.now());
		orderVO.setOrderId(11);
		
		order = new Order();
		order.setCustomerIdentifier(1);
		order.setOrderDate("20230404");
		order.setOrderIdentifier(11);
		order.setOrderPrice(BigDecimal.valueOf(120));
		
		vo = new CustomerVO();
	}
	
	@Test
	public void testSaveOrder() throws CustomerRewardException {
		when(customerRepository.findById(anyInt())).thenReturn(Optional.of(vo));
		when(orderRepository.save(any(OrderVO.class))).thenReturn(orderVO);
		Order savedOrder = orderService.saveOrder(order, 1);
		Assertions.assertNotNull(savedOrder);
		Assertions.assertEquals(savedOrder.getCustomerIdentifier(), 1);
		Assertions.assertEquals(savedOrder.getOrderIdentifier(), 11);
	}
	
	@Test
	public void testSaveOrderException() throws CustomerRewardException {
		when(customerRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
		CustomerRewardException exception = Assertions.assertThrows(CustomerRewardException.class, () -> {
			orderService.saveOrder(order, 1);
		});
		Assertions.assertEquals("Exception while saving order details No value present", exception.getMessage());
	
	}
	
	
	@Test
	public void testSaveOrderException1() throws CustomerRewardException {	
		CustomerRewardException exception = Assertions.assertThrows(CustomerRewardException.class, () -> {
			orderService.saveOrder(order, null);
		});
		Assertions.assertEquals("Exception while saving order details No value present", exception.getMessage());
	}
	
	


}
