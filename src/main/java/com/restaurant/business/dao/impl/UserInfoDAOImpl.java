package com.restaurant.business.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.restaurant.business.bean.UserInfo;
import com.restaurant.business.dao.interfaces.IUserInfoDAO;

@Repository
public class UserInfoDAOImpl implements IUserInfoDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void register(UserInfo user) {
		// TODO Auto-generated method stub
		String sql = "insert into user_info(user_id, user_name) values(?, ?)";
		Object[] params = new Object[]{user.getUserId(), user.getUserName()};
		jdbcTemplate.update(sql, params);
	}

	@Override
	public UserInfo findUserByUserName(String userName) {
		// TODO Auto-generated method stub
		String sql = "select * from user_info where user_name = ?";
		final UserInfo user = new UserInfo();
		jdbcTemplate.query(sql, new Object[]{userName}, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				if (rs.next()) {
					user.setUserId(rs.getLong("user_id"));
					user.setUserName(rs.getString("user_name"));
				}
			}
		});
		
		if (user.getUserId() == 0) {
			return null;
		}
		
		return user;
	}

}
