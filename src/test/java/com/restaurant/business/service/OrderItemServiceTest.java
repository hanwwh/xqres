package com.restaurant.business.service;

import java.util.List;

import com.restaurant.business.bean.FoodInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.restaurant.business.bean.OrderItem;
import com.restaurant.business.service.interfaces.IOrderItemService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/spring-test.xml")
public class OrderItemServiceTest {

	@Autowired
	private IOrderItemService itemService;
	
	@Test
	public void queryOrderItems(){
		List<OrderItem> list = itemService.queryOrderItems();
		for(int i=0; i<list.size(); i++){
			System.out.println(list.get(i).getItemName());
		}
	}

	@Test
	public void nextOrderCode(){
		System.out.println(itemService.nextOrderCode());
	}

	@Test
	public void selectFoodsById(){
		FoodInfo foodInfo = itemService.selectFoodsById(100000001);
		System.out.println("----->food_name: "+foodInfo.getFoodName());
	}
}
