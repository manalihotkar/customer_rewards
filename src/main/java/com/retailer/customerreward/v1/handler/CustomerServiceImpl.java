package com.retailer.customerreward.v1.handler;

import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailer.customerreward.entity.CustomerVO;
import com.retailer.customerreward.exception.CustomerRewardException;
import com.retailer.customerreward.model.v1.Customer;
import com.retailer.customerreward.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepository;

	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public Customer saveCustomer(Customer customer) throws CustomerRewardException {
		try {
			log.info("CustomerServiceImpl : Saving Customer Details");
			CustomerVO customerVO = new CustomerVO();
			Random ran = new Random();
			Integer x = ran.nextInt(6) + 5;
			customerVO.setCustomerId(x);
			customerVO.setCustomerName(customer.getName());
			customerVO.setCustomerEmail(customer.getEmail());
			CustomerVO savedCustomer = customerRepository.save(customerVO);
			return setCustomer(savedCustomer);
		} catch (Exception ex) {
			log.error("Exception occured while saving customer Details {}" + ex.getMessage());
			throw new CustomerRewardException("Customer cannot be added");
		}

	}

	private Customer setCustomer(CustomerVO savedCustomer) {
		Customer customer = new Customer();
		customer.setCustomerIdentifier(savedCustomer.getCustomerId());
		customer.setEmail(savedCustomer.getCustomerEmail());
		customer.setName(savedCustomer.getCustomerName());
		return customer;
	}

	@Override
	public Customer updateCustomer(@Valid Customer customer, Integer customerIdentifier)
			throws CustomerRewardException {
		try {
			CustomerVO customerVO = customerRepository.findById(customerIdentifier).get();
			if (customerVO != null) {
				log.info("CustomerServiceImpl : Updating Customer Details or Order Details for customerId {}",
						customerIdentifier);
				customerVO.setCustomerName(
						customer.getName() != null ? customer.getName() : customerVO.getCustomerName());
				customerVO.setCustomerEmail(
						customer.getEmail() != null ? customer.getEmail() : customerVO.getCustomerEmail());

				return setCustomer(customerRepository.save(customerVO));
			}
		} catch (Exception ex) {
			log.error("CustomerServiceImpl : Exception occured while updating customer Details " + ex.getMessage());
			throw new CustomerRewardException(
					"CustomerServiceImpl :  Customer Details cannot be updated for customerId " + customerIdentifier);
		}
		return null;
	}

	@Override
	public Customer fetchCustomer(Integer customerIdentifier) throws CustomerRewardException {
		try {
			CustomerVO customerVO = customerRepository.findById(customerIdentifier).get();
			if (customerVO != null) {
				Customer customer = new Customer();
				customer.setEmail(customerVO.getCustomerEmail());
				customer.setName(customerVO.getCustomerName());
				customer.setCustomerIdentifier(customerVO.getCustomerId());
				return customer;
			}
			return null;
		} catch (Exception ex) {
			log.error("CustomerServiceImpl : Exception occured while reading customer Details " + ex.getMessage());
			throw new CustomerRewardException(
					"CustomerServiceImpl :  Customer Details cannot be read for customerId " + customerIdentifier);
		}

	}

}
