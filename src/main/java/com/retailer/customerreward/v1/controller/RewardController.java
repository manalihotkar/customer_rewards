package com.retailer.customerreward.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailer.customerreward.exception.CustomerRewardException;
import com.retailer.customerreward.model.v1.MonthlyReward;
import com.retailer.customerreward.model.v1.RewardResponse;
import com.retailer.customerreward.v1.handler.RewardService;

import lombok.extern.slf4j.Slf4j;

/***
 * Controller class to get rewards-points.
 * 
 * @author manali
 *
 */
@Slf4j
@RestController
@RequestMapping("/retailer/v1/customer")
public class RewardController {

	private RewardService rewardService;

	@Autowired
	public RewardController(RewardService rewardHandler) {
		this.rewardService = rewardHandler;
	}

	/***
	 * Returns reward-points for each month order of the customer.
	 * 
	 * @param customerIdentifier
	 * @return
	 * @throws CustomerRewardException
	 */
	@GetMapping("/reward/{customerIdentifier}")
	public RewardResponse retrieveReward(@PathVariable Integer customerIdentifier) throws CustomerRewardException {
		log.info("RewardController: getreward called for customerIdentifier {}", customerIdentifier);
		if (customerIdentifier != null) {
			return rewardService.retrieveCustomerReward(customerIdentifier);
		} else
			throw new CustomerRewardException("Customer ID cannot be null" + HttpStatus.BAD_REQUEST);

	}

	/***
	 * Returns rewards of the customer for particular month.
	 * 
	 * @param customerIdentifier
	 * @param yearMonthVal
	 * @return
	 * @throws CustomerRewardException
	 */
	@GetMapping("/reward/{customerIdentifier}/yearmonth/{yearMonthVal}")
	public MonthlyReward retrieveRewardByMonth(@PathVariable Integer customerIdentifier,
			@PathVariable String yearMonthVal) throws CustomerRewardException {

		if (customerIdentifier != null && yearMonthVal != null && yearMonthVal.length() == 6) {
			String yearValue = yearMonthVal.substring(0, 4);
			String monthValue = yearMonthVal.substring(4, 6);

			return rewardService.retrieveCustomerRewardByMonth(customerIdentifier, yearValue, monthValue);
		} else
			throw new CustomerRewardException("Customer ID cannot be null" + HttpStatus.BAD_REQUEST);

	}

}
