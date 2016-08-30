package com.restaurant.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.system.dao.interfaces.ISystemDAO;
import com.restaurant.system.service.interfaces.ISystemService;

@Service
public class SystemServiceImpl implements ISystemService {
	
	@Autowired
	private ISystemDAO systemDAO;

	@Override
	public long getNextValue(String tableName) {
		// TODO Auto-generated method stub
		return systemDAO.getNextValue(tableName);
	}

}
