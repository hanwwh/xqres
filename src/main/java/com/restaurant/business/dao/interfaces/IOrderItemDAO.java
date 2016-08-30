package com.restaurant.business.dao.interfaces;

import java.util.List;

import com.restaurant.business.bean.FoodInfo;
import com.restaurant.business.bean.OrderItem;
import com.restaurant.business.bean.OrderItemFoodRelat;

public interface IOrderItemDAO {

	public List<OrderItem> queryOrderItems();
	
	public int insertOrderItem(OrderItem item);

	public List<FoodInfo> selectFoods();

	public String nextOrderCode();

	public int saveOrderItemFoodRelat(List<OrderItemFoodRelat> list);

	public FoodInfo selectFoodsById(long food_id);
}
