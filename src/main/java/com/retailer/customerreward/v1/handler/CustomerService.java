package com.retailer.customerreward.v1.handler;

import javax.validation.Valid;

import com.retailer.customerreward.exception.CustomerRewardException;
import com.retailer.customerreward.model.v1.Customer;

public interface CustomerService {

	public Customer saveCustomer(@Valid Customer customer) throws CustomerRewardException;

	public Customer updateCustomer(@Valid Customer customer, Integer customerIdentifier) throws CustomerRewardException;

	Customer fetchCustomer(Integer customerIdentifier) throws CustomerRewardException;

}
