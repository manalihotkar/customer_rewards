package com.retailer.customerreward.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.retailer.customerreward.entity.CustomerVO;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerVO, Integer>{

}
