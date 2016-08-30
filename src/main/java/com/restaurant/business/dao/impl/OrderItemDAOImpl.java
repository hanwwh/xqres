package com.restaurant.business.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.restaurant.business.bean.FoodInfo;
import com.restaurant.business.bean.OrderItemFoodRelat;
import com.restaurant.system.service.interfaces.ISystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.restaurant.business.bean.OrderItem;
import com.restaurant.business.dao.interfaces.IOrderItemDAO;

@Repository
public class OrderItemDAOImpl implements IOrderItemDAO {

	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(OrderItemDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ISystemService systemService;

	@Override
	public List<OrderItem> queryOrderItems() {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM ORDER_ITEM";
		final List<OrderItem> list = new ArrayList<OrderItem>();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				OrderItem item = new OrderItem();
				item.setItemId(rs.getLong("ITEM_ID"));
				item.setGroupId(rs.getLong("GROUP_ID"));
				item.setStatus(rs.getInt("STATUS"));
				item.setItemCode(rs.getString("ITEM_CODE"));
				item.setItemName(rs.getString("ITEM_NAME"));
				item.setItemPrice(rs.getFloat("ITEM_PRICE"));
				item.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				item.setItemPicurl(rs.getString("ITEM_PICURL"));
				item.setItemDetails(rs.getString("ITEM_DETAILS"));
				list.add(item);
			}
		});
		
		return list;
	}

	@Override
	public int insertOrderItem(OrderItem item) {
		// TODO Auto-generated method stub
		String sql = "insert into ORDER_ITEM(item_id, group_id, status, item_code, item_name, item_price, create_date, "
				+ "item_picurl, item_details, remark) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int count = jdbcTemplate.update(sql, new Object[]{item.getItemId(), item.getGroupId(), item.getStatus(), item.getItemCode(), 
				item.getItemName(), item.getItemPrice(), new Timestamp(System.currentTimeMillis()), item.getItemPicurl(),
				item.getItemDetails(), item.getRemark()});
		
		return count;
	}

	@Override
	public List<FoodInfo> selectFoods() {
		String sql = "select food_id, food_name, food_price from food_info";
		final List<FoodInfo> list = new ArrayList<FoodInfo>();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				FoodInfo info = new FoodInfo();
				info.setFoodId(rs.getLong("food_id"));
				info.setFoodName(rs.getString("food_name"));
				info.setFoodPrice(rs.getFloat("food_price"));
				list.add(info);
			}
		});

		return list;
	}

	private SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	public String nextOrderCode() {
		try {

			String current_date_str = format.format(new Date());
			String sql = "SELECT ITEM_CODE FROM ORDER_ITEM WHERE INSTR(ITEM_CODE, '"+current_date_str+"') = 1";
			logger.info("#nextOrderCode, sql: "+sql);
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			if (list.size() == 0) {
				sql = "UPDATE sequence SET current_value = 1 WHERE NAME = 'ORDER_CODE'";
				logger.info("#nextOrderCode, sql: "+sql);
				jdbcTemplate.update(sql);
				return current_date_str+"0001";
			} else {
				long next_code_num = systemService.getNextValue("ORDER_CODE");
				logger.info("#nextOrderCode, next_code_num: "+next_code_num);
				if (next_code_num < 10)
					return current_date_str+"000"+next_code_num;
				else if (next_code_num < 100)
					return current_date_str+"00"+next_code_num;
				else if (next_code_num < 1000)
					return current_date_str+"0"+next_code_num;
				else
					return current_date_str+next_code_num;
			}

		} catch (Exception e) {
			logger.error("#nextOrderCode, error. ", e);

		}

		return null;
	}

	@Override
	public int saveOrderItemFoodRelat(List<OrderItemFoodRelat> list) {
		String sql = "insert into order_item_food_relat(item_id, food_id, food_count, order_type, discount, create_date, remark) "+
				"values(?, ?, ?, ?, ?, ?, ?)";
		List<Object[]> obj = new ArrayList<Object[]>();
		for (OrderItemFoodRelat relat : list) {
			obj.add(new Object[]{relat.getItemId(), relat.getFoodId(), relat.getFoodCount(), relat.getOrderType(),
				relat.getDiscount(), new Timestamp(System.currentTimeMillis()), relat.getRemark()});
		}
		int[] count = jdbcTemplate.batchUpdate(sql, obj);
		return count.length;
	}

	@Override
	public FoodInfo selectFoodsById(long food_id) {
		logger.info("#selectFoodsById. food_ids: "+food_id);
		String sql = "select food_id, food_name, food_code, food_type, food_price, status from food_info where food_id = ? ";
		final List<FoodInfo> list = new ArrayList<FoodInfo>();
		jdbcTemplate.query(sql, new Object[]{food_id}, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				FoodInfo info = new FoodInfo();
				info.setFoodId(rs.getLong("food_id"));
				info.setFoodName(rs.getString("food_name"));
				info.setFoodPrice(rs.getFloat("food_price"));
				list.add(info);
			}
		});

		if (list.size() == 0)
			return null;

		return list.get(0);
	}

}
