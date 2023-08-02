package com.retailer.customerreward.util;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RewardCalculatorUtilTest {
	
	@Test
	public void testCalculateReward() {
		BigDecimal rewardPoints =RewardCalculatorUtil.calculateReward(BigDecimal.valueOf(120));
		Assertions.assertEquals(rewardPoints, BigDecimal.valueOf(90));
	}

}
