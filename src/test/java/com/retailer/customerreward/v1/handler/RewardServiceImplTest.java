package com.retailer.customerreward.v1.handler;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.retailer.customerreward.entity.OrderVO;
import com.retailer.customerreward.exception.CustomerRewardException;
import com.retailer.customerreward.model.v1.MonthlyReward;
import com.retailer.customerreward.model.v1.RewardResponse;
import com.retailer.customerreward.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class RewardServiceImplTest {

	@Mock
	private OrderRepository orderRepository;

	List<OrderVO> orderVOList  = new ArrayList();

	RewardService rewardService;

	@BeforeEach
	public void setup() {
		rewardService = new RewardServiceImpl(orderRepository);
	}

	@Test
	public void testRetrieveCustomerReward() {
		when(orderRepository.findAllByCustomerId(1)).thenReturn(orderVOList);
		CustomerRewardException exception = Assertions.assertThrows(CustomerRewardException.class, () -> {
			rewardService.retrieveCustomerReward(1);
		});
		Assertions.assertEquals("No customer exists with id - 1", exception.getMessage());
	}

	@Test
	public void testRetrieveCustomerReward1() throws CustomerRewardException {
		//orderVOList = new ArrayList();
		OrderVO orderVO = new OrderVO();
		orderVO.setCustomerId(1);
		orderVO.setOrderAmount(120.0);
		orderVO.setOrderDate(LocalDate.now());
		orderVO.setOrderId(11);
		
		OrderVO orderVO1 = new OrderVO();
		orderVO1.setCustomerId(1);
		orderVO1.setOrderAmount(100.0);
		orderVO1.setOrderDate(LocalDate.now());
		orderVO1.setOrderId(12);

		orderVOList.add(orderVO);
		orderVOList.add(orderVO1);
		when(orderRepository.findAllByCustomerId(1)).thenReturn(orderVOList);
		RewardResponse res = rewardService.retrieveCustomerReward(1);
		Assertions.assertNotNull(res);

	}
	
	@Test
	public void testRetrieveCustomerRewardByMonthException() {
		when(orderRepository.findAllByCustomerId(1)).thenReturn(orderVOList);
		CustomerRewardException exception = Assertions.assertThrows(CustomerRewardException.class, () -> {
			rewardService.retrieveCustomerRewardByMonth(1, "2023", "04");
		});
		Assertions.assertEquals("No customer exists with id - 1", exception.getMessage());
	}

	@Test
	public void testRetrieveCustomerRewardByMonth() throws CustomerRewardException {
		//orderVOList = new ArrayList();
		OrderVO orderVO = new OrderVO();
		orderVO.setCustomerId(1);
		orderVO.setOrderAmount(120.0);
		orderVO.setOrderDate(LocalDate.of(2023,04,04));
		orderVO.setOrderId(11);
		
		OrderVO orderVO1 = new OrderVO();
		orderVO1.setCustomerId(1);
		orderVO1.setOrderAmount(100.0);
		orderVO1.setOrderDate(LocalDate.of(2023, 04, 06));
		orderVO1.setOrderId(12);

		orderVOList.add(orderVO);
		orderVOList.add(orderVO1);
		when(orderRepository.findAllByCustomerId(1)).thenReturn(orderVOList);
		MonthlyReward res = rewardService.retrieveCustomerRewardByMonth(1, "2023", "04");
		Assertions.assertNotNull(res);

	}

}
