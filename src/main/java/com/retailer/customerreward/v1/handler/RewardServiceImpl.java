package com.retailer.customerreward.v1.handler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailer.customerreward.entity.OrderVO;
import com.retailer.customerreward.exception.CustomerRewardException;
import com.retailer.customerreward.model.v1.MonthlyReward;
import com.retailer.customerreward.model.v1.Order;
import com.retailer.customerreward.model.v1.Reward;
import com.retailer.customerreward.model.v1.RewardResponse;
import com.retailer.customerreward.repository.OrderRepository;
import com.retailer.customerreward.util.RewardCalculatorUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RewardServiceImpl implements RewardService {

	private OrderRepository orderRepository;

	@Autowired
	public RewardServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public RewardResponse retrieveCustomerReward(Integer customerIdentifier) throws CustomerRewardException {
		log.info("RewardServiceHandlerImpl: Request received and been processed in handler for customerId",
				customerIdentifier);
		try {
			List<OrderVO> orderVO = orderRepository.findAllByCustomerId(customerIdentifier);
			if(orderVO.isEmpty())
				throw new CustomerRewardException("No Customer Found with Id "+ customerIdentifier);
			Map<LocalDate, DoubleSummaryStatistics> ord = orderVO.stream().collect(Collectors.groupingBy(
					e -> e.getOrderDate().withDayOfMonth(1), Collectors.summarizingDouble(OrderVO::getOrderAmount)));
			ord.entrySet().stream().sorted(Comparator.comparingDouble(p -> p.getValue().getSum()));
			RewardResponse rewardResponse = new RewardResponse();
			List<Reward> rewardList = new ArrayList<Reward>();
			BigDecimal orderTotal = BigDecimal.ZERO;
			BigDecimal rewardTotal = BigDecimal.ZERO;
			for (LocalDate dt : ord.keySet()) {
				Reward reward = new Reward();
				reward.setRewardMonth(dt.getYear() + "-" + dt.getMonthValue());
				reward.setMonthlyOrderTotal(BigDecimal.valueOf(ord.get(dt).getSum()));
				orderTotal = orderTotal.add(reward.getMonthlyOrderTotal());
				reward.setRewardPoints(RewardCalculatorUtil.calculateReward(reward.getMonthlyOrderTotal()));
				rewardTotal = rewardTotal.add(reward.getRewardPoints());
				rewardList.add(reward);
			}
			rewardResponse.setOrderTotal(orderTotal);
			rewardResponse.setRewardTotal(rewardTotal);
			rewardResponse.setRewards(rewardList);
			return rewardResponse;

		} catch (Exception ex) {
			log.error("No customer exists {}", ex.getMessage());
			throw new CustomerRewardException("No customer exists with id - " + customerIdentifier);
		}
	}

	@Override
	public MonthlyReward retrieveCustomerRewardByMonth(Integer customerIdentifier, String yearValue, String monthValue)
			throws CustomerRewardException {

		log.info("RewardServiceHandlerImpl: Request received and been processed in handler for customerId",
				customerIdentifier);
		try {
			List<OrderVO> orderVO = orderRepository.findAllByCustomerId(customerIdentifier);
			if(orderVO.isEmpty())
				throw new CustomerRewardException("No Customer Found with Id "+ customerIdentifier + " and date "+yearValue+"-"+monthValue);
			List<OrderVO> list = orderVO.stream()
					.filter(e -> e.getOrderDate().getMonthValue() == Integer.parseInt(monthValue)
							&& e.getOrderDate().getYear() == Integer.parseInt(yearValue))
					.collect(Collectors.toList());
			MonthlyReward reward = new MonthlyReward();
			List<Order> orders = new ArrayList<Order>();
			BigDecimal orderTotal = BigDecimal.ZERO;
			for (OrderVO dt : list) {
				Order order = new Order();
				order.setCustomerIdentifier(customerIdentifier);
				order.setOrderDate(dt.getOrderDate().toString());
				order.setOrderIdentifier(dt.getOrderId());
				order.setOrderPrice(BigDecimal.valueOf(dt.getOrderAmount()));
				orders.add(order);
				orderTotal = orderTotal.add(order.getOrderPrice());
				reward.setRewardMonth(dt.getOrderDate().getYear() + "-"+ dt.getOrderDate().getMonthValue());
			}
			reward.setOrder(orders);
			
			reward.setMonthlyOrderTotal(orderTotal);
			reward.setRewardPoints(RewardCalculatorUtil.calculateReward(orderTotal));
			return reward;

		} catch (Exception ex) {
			log.error("No customer exists {}", ex.getMessage());
			throw new CustomerRewardException("No customer exists with id - " + customerIdentifier);
		}
	}

}
