package com.restaurant.business.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import com.restaurant.business.bean.FoodInfo;
import com.restaurant.business.bean.OrderItemFoodRelat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.business.bean.OrderItem;
import com.restaurant.business.dao.interfaces.IOrderItemDAO;
import com.restaurant.business.service.interfaces.IOrderItemService;

@Transactional
@Service
public class OrderItemServiceImpl implements IOrderItemService {

	@Autowired
	private IOrderItemDAO itemDAO;

	@Override
	public List<OrderItem> queryOrderItems() {
		// TODO Auto-generated method stub
		return itemDAO.queryOrderItems();
	}

	@Override
	public int insertOrderItem(OrderItem item) {
		// TODO Auto-generated method stub
		return itemDAO.insertOrderItem(item);
	}

	@Override
	public List<FoodInfo> selectFoods() {
		return itemDAO.selectFoods();
	}

	@Override
	public String nextOrderCode() {
		return itemDAO.nextOrderCode();
	}

	@Override
	public int saveOrderItemFoodRelat(List<OrderItemFoodRelat> list) {
		return itemDAO.saveOrderItemFoodRelat(list);
	}

	@Override
	public FoodInfo selectFoodsById(long food_id) {
		return itemDAO.selectFoodsById(food_id);
	}

}
