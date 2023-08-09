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
	
	@Test
	public void testCalculateRewardLessThen100() {
		BigDecimal rewardPoints =RewardCalculatorUtil.calculateReward(BigDecimal.valueOf(70));
		Assertions.assertEquals(rewardPoints, BigDecimal.valueOf(20));
	}
	
	@Test
	public void testCalculateRewardLessThen50() {
		BigDecimal rewardPoints =RewardCalculatorUtil.calculateReward(BigDecimal.valueOf(40));
		Assertions.assertEquals(rewardPoints, BigDecimal.valueOf(0));
	}

}
