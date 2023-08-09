package com.retailer.customerreward.util;

import java.math.BigDecimal;

public class RewardCalculatorUtil {
	public static BigDecimal calculateReward(BigDecimal totalTransactionAmount) {
		BigDecimal rewardPoints = BigDecimal.ZERO;
		if (totalTransactionAmount.compareTo(BigDecimal.valueOf(100)) >= 0) {
			rewardPoints = totalTransactionAmount.subtract(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(2));
			rewardPoints = rewardPoints.add(BigDecimal.valueOf(50)); 
		} else if (totalTransactionAmount.compareTo(BigDecimal.valueOf(50)) >= 0) {
			rewardPoints = totalTransactionAmount.subtract(BigDecimal.valueOf(50)).multiply(BigDecimal.valueOf(1));
		}
		return rewardPoints;

	}
}
