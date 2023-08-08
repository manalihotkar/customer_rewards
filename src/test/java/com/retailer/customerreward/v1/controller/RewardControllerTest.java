package com.retailer.customerreward.v1.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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
import com.retailer.customerreward.model.v1.RewardResponse;
import com.retailer.customerreward.v1.handler.RewardService;

@ExtendWith(MockitoExtension.class)
public class RewardControllerTest {

	private RewardController controller;

	@Mock
	private RewardService rewardService;

	private RewardResponse response;
	
	private MonthlyReward monthlyReward;

	@BeforeEach
	public void setup() {
		controller = new RewardController(rewardService);
		response = new RewardResponse();
		response.setOrderTotal(BigDecimal.valueOf(120));
		response.setRewardTotal(BigDecimal.valueOf(90));
		
		monthlyReward = new MonthlyReward();
		monthlyReward.setMonthlyOrderTotal(BigDecimal.valueOf(120));
		monthlyReward.setRewardPoints(BigDecimal.valueOf(90));
	}

	@Test
	public void testRetrieveReward() throws CustomerRewardException {
		when(rewardService.retrieveCustomerReward(anyInt())).thenReturn(response);
		RewardResponse response = controller.retrieveReward(12);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(response.getRewardTotal(), BigDecimal.valueOf(90));
	}

	@Test
	public void testRetrieveRewardException() throws CustomerRewardException {
		CustomerRewardException exception = Assertions.assertThrows(CustomerRewardException.class, () -> {
			controller.retrieveReward(null);
		});
		Assertions.assertEquals("Customer ID cannot be null400 BAD_REQUEST", exception.getMessage());
	}
	
	@Test
	public void testRetrieveRewardByMonth() throws CustomerRewardException {
		when(rewardService.retrieveCustomerRewardByMonth(anyInt(), anyString(), anyString())).thenReturn(monthlyReward);
		MonthlyReward reward = controller.retrieveRewardByMonth(12, "202304");
		Assertions.assertNotNull(reward);
		Assertions.assertEquals(reward.getRewardPoints(), BigDecimal.valueOf(90));
	}

	@Test
	public void testRetrieveRewardByMonthException() throws CustomerRewardException {
		CustomerRewardException exception = Assertions.assertThrows(CustomerRewardException.class, () -> {
			controller.retrieveRewardByMonth(null, null);
		});
		Assertions.assertEquals("Customer ID cannot be null400 BAD_REQUEST", exception.getMessage());
	}

}
