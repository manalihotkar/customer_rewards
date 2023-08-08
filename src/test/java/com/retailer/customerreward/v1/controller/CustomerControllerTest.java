package com.retailer.customerreward.v1.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.retailer.customerreward.exception.CustomerRewardException;
import com.retailer.customerreward.model.v1.Customer;
import com.retailer.customerreward.v1.handler.CustomerService;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

	private CustomerController controller;

	@Mock
	private CustomerService CustomerService;

	private Customer customer;
	

	@BeforeEach
	public void setup() {
		controller = new CustomerController(CustomerService);
		customer = new Customer();
		customer.setEmail("a@xyz.com");
		customer.setCustomerIdentifier(Integer.valueOf(1));
		customer.setName("abc");
	}

	@Test
	public void testSaveCustomer() throws Exception {
		when(CustomerService.saveCustomer(any(Customer.class))).thenReturn(customer);
		Customer response = controller.saveCustomer(customer);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(response.getCustomerIdentifier(), 1);
		Assertions.assertEquals(response.getEmail(), "a@xyz.com");
		Assertions.assertEquals(response.getName(), "abc");
	}

	@Test
	public void testSaveCustomerException() throws CustomerRewardException {
		CustomerRewardException exception = Assertions.assertThrows(CustomerRewardException.class, () -> {
			controller.saveCustomer(null);
		});
		Assertions.assertEquals("Invalid customer Details", exception.getMessage());
	}
	
	@Test
	public void testInvalidCustomerException() throws CustomerRewardException {
		CustomerRewardException exception = Assertions.assertThrows(CustomerRewardException.class, () -> {
			controller.updateCustomer(null, 1);
		});
		Assertions.assertEquals("Invalid customer Details", exception.getMessage());
	}
	
	@Test
	public void testUpdateCustomer() throws Exception {
		when(CustomerService.updateCustomer(any(Customer.class), anyInt())).thenReturn(customer);
		Customer response = controller.updateCustomer(customer, 1);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(response.getCustomerIdentifier(), 1);
		Assertions.assertEquals(response.getEmail(), "a@xyz.com");
		Assertions.assertEquals(response.getName(), "abc");
	}
	
	@Test
	public void testGetCustomer() throws CustomerRewardException {
		when(CustomerService.fetchCustomer(anyInt())).thenReturn(customer);
		Customer response = controller.customer( 1);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(response.getCustomerIdentifier(), 1);
		Assertions.assertEquals(response.getEmail(), "a@xyz.com");
		Assertions.assertEquals(response.getName(), "abc");
	}
	


}
