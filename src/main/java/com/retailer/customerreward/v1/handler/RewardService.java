package com.retailer.customerreward.v1.handler;

import com.retailer.customerreward.exception.CustomerRewardException;
import com.retailer.customerreward.model.v1.MonthlyReward;
import com.retailer.customerreward.model.v1.RewardResponse;

public interface RewardService {

	public RewardResponse retrieveCustomerReward(Integer customerIdentifier) throws CustomerRewardException;

	public MonthlyReward retrieveCustomerRewardByMonth(Integer customerIdentifier, String yearValue, String monthValue)
			throws CustomerRewardException;

}
