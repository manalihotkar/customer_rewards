package com.retailer.customerreward.v1.handler;

import com.retailer.customerreward.exception.CustomerRewardException;
import com.retailer.customerreward.model.v1.Order;

public interface OrderService {

	public Order saveOrder(Order order, Integer customerIdentifier) throws CustomerRewardException;

}
