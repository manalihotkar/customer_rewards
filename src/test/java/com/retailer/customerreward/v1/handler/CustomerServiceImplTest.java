package com.retailer.customerreward.v1.handler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.retailer.customerreward.entity.CustomerVO;
import com.retailer.customerreward.exception.CustomerRewardException;
import com.retailer.customerreward.model.v1.Customer;
import com.retailer.customerreward.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {
	
	CustomerService customerService;
	
	@Mock
	private CustomerRepository customerRepository;
	
	private CustomerVO customerVO;
	
	private Customer customer;
	
	@BeforeEach
	public void setUp() {
		customerService = new CustomerServiceImpl(customerRepository);		
		customerVO = new CustomerVO();
		customerVO.setCustomerName("name");
		customerVO.setCustomerEmail("email");
		
		customer = new Customer();
		customer.setName("name");
		customer.setEmail("email");
	}
	
	@Test
	public void testSaveCustomer() throws CustomerRewardException {
		when(customerRepository.save(any(CustomerVO.class))).thenReturn(customerVO);
		Customer savedCustomer = customerService.saveCustomer(customer);
		Assertions.assertNotNull(savedCustomer);
		Assertions.assertEquals(savedCustomer.getEmail(), "email");
		Assertions.assertEquals(savedCustomer.getName(), "name");
	}
	
	
	@Test
	public void testUpdateCustomer() throws CustomerRewardException {
		when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customerVO));
		when(customerRepository.save(any(CustomerVO.class))).thenReturn(customerVO);
		Customer customer = new Customer();
		customer.setName("name");
		customer.setEmail("email");
		Customer savedCustomer = customerService.updateCustomer(customer, 1);
		Assertions.assertNotNull(savedCustomer);
		Assertions.assertEquals(savedCustomer.getEmail(), "email");
		Assertions.assertEquals(savedCustomer.getName(), "name");
	}
	
	@Test
	public void testFetchCustomer() throws CustomerRewardException {
		when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customerVO));
		//when(customerRepository.save(any(CustomerVO.class))).thenReturn(customerVO);
		
		Customer savedCustomer = customerService.fetchCustomer( 1);
		Assertions.assertNotNull(savedCustomer);
		Assertions.assertEquals(savedCustomer.getEmail(), "email");
		Assertions.assertEquals(savedCustomer.getName(), "name");
	}
	
	@Test
	public void testFetchCustomerException() throws CustomerRewardException {	
		CustomerRewardException exception = Assertions.assertThrows(CustomerRewardException.class, () -> {
			customerService.fetchCustomer( 1);
		});
		Assertions.assertEquals("CustomerServiceImpl :  Customer Details cannot be read for customerId 1", exception.getMessage());
	}
	
	@Test
	public void testUpdateCustomerException() throws CustomerRewardException {	
		CustomerRewardException exception = Assertions.assertThrows(CustomerRewardException.class, () -> {
			customerService.updateCustomer(customer, 1);
		});
		Assertions.assertEquals("CustomerServiceImpl :  Customer Details cannot be updated for customerId 1", exception.getMessage());
	}
	
	@Test
	public void testSacveCustomerException() throws CustomerRewardException {	
		CustomerRewardException exception = Assertions.assertThrows(CustomerRewardException.class, () -> {
			customerService.saveCustomer(customer);
		});
		Assertions.assertEquals("Customer cannot be added", exception.getMessage());
	}

}
