package com.retailer.customerreward.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailer.customerreward.exception.CustomerRewardException;
import com.retailer.customerreward.model.v1.Customer;
import com.retailer.customerreward.v1.handler.CustomerService;

import lombok.extern.slf4j.Slf4j;

/***
 * Endpoint to create, update and get customer details for Retailer.
 * 
 * @author manali
 *
 */
@Slf4j
@RestController
@RequestMapping("/retailer/v1/customer")
public class CustomerController extends BaseController {

	CustomerService customerService;

	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	/***
	 * Gets details of the customer.
	 * 
	 * @param customerIdentifier
	 * @return
	 * @throws CustomerRewardException
	 */
	@GetMapping("/{customerIdentifier}")
	public Customer customer(@PathVariable Integer customerIdentifier) throws CustomerRewardException {
		log.info("CustomerController: Inside customer details {}", customerIdentifier);
		return customerService.fetchCustomer(customerIdentifier);
	}

	/**
	 * Creates a new Customer.
	 * 
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@PostMapping
	public Customer saveCustomer(@Valid @RequestBody Customer customer) throws Exception {

		if (validateCustomerDetails(customer)) {
			log.info("Validation succes for customer creation");
			return customerService.saveCustomer(customer);
		} else {
			throw new CustomerRewardException("Invalid customer Details");
		}
	}

	/***
	 * Update details of existing customer.
	 * 
	 * @param customer
	 * @param customerIdentifier
	 * @return
	 * @throws Exception
	 */
	@PatchMapping("/{customerIdentifier}")
	public Customer updateCustomer(@Valid @RequestBody Customer customer, @PathVariable Integer customerIdentifier)
			throws Exception {

		if (validateCustomerDetails(customer)) {
			return customerService.updateCustomer(customer, customerIdentifier);
		} else {
			throw new CustomerRewardException("Invalid customer Details");
		}

	}

}
