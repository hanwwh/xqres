package com.restaurant.system.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.restaurant.system.dao.interfaces.ISystemDAO;

@Repository
public class SystemDAOImpl implements ISystemDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public long getNextValue(String tableName) {
		// TODO Auto-generated method stub
		String sql = "select nextval( ? )";
		
		List<Long> vList = jdbcTemplate.query(sql, new Object[]{tableName.toUpperCase()}, new RowMapper<Long>() {

			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				return rs.getLong(1);
			}
		});
		
		return vList.get(0);
	}

}
