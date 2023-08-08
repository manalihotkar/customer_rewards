package com.retailer.customerreward.v1.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.retailer.customerreward.exception.CustomerRewardException;
import com.retailer.customerreward.model.v1.MonthlyReward;
import com.retailer.customerreward.model.v1.Order;
import com.retailer.customerreward.v1.handler.OrderService;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

	private OrderController controller;

	@Mock
	private OrderService orderService;

	private Order order;
	
	private MonthlyReward monthlyReward;

	@BeforeEach
	public void setup() {
		controller = new OrderController(orderService);
		order = new Order();
		order.setOrderPrice(BigDecimal.valueOf(120));
		order.setCustomerIdentifier(Integer.valueOf(1));
		order.setOrderDate("20230405");
		order.setOrderIdentifier(1001);
		
		monthlyReward = new MonthlyReward();
		monthlyReward.setMonthlyOrderTotal(BigDecimal.valueOf(120));
		monthlyReward.setRewardPoints(BigDecimal.valueOf(90));
	}

	@Test
	public void testSaveOrder() throws Exception {
		when(orderService.saveOrder(any(Order.class), anyInt())).thenReturn(order);
		Order response = controller.saveOrder(order, 1);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(response.getCustomerIdentifier(), 1);
		Assertions.assertEquals(response.getOrderDate(), "20230405");
		Assertions.assertEquals(response.getOrderIdentifier(), 1001);
		Assertions.assertEquals(response.getOrderPrice(), BigDecimal.valueOf(120));
	}

	@Test
	public void testSaveOrderException() throws CustomerRewardException {
		CustomerRewardException exception = Assertions.assertThrows(CustomerRewardException.class, () -> {
			controller.saveOrder(order, null);
		});
		Assertions.assertEquals("Invalid order Details", exception.getMessage());
	}
	
	@Test
	public void testInvalidOrderException() throws CustomerRewardException {
		CustomerRewardException exception = Assertions.assertThrows(CustomerRewardException.class, () -> {
			controller.saveOrder(null, 1);
		});
		Assertions.assertEquals("Invalid order Details", exception.getMessage());
	}
	
	@Test
	public void testZerOrderPriceException() throws CustomerRewardException {
		order.setOrderPrice(BigDecimal.ZERO);
		CustomerRewardException exception = Assertions.assertThrows(CustomerRewardException.class, () -> {
			controller.saveOrder(order, 1);
		});
		Assertions.assertEquals("Invalid order price Details", exception.getMessage());
	}
	


}
