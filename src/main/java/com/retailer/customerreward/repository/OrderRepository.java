package com.retailer.customerreward.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.retailer.customerreward.entity.OrderVO;

@Repository
public interface OrderRepository extends CrudRepository<OrderVO, Integer> {


	public List<OrderVO> findAllByCustomerId(Integer customerIdentifier);
	

}
