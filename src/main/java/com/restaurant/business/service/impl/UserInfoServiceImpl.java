package com.restaurant.business.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.business.bean.UserInfo;
import com.restaurant.business.dao.interfaces.IUserInfoDAO;
import com.restaurant.business.service.interfaces.IUserInfoService;

@Transactional
@Service
public class UserInfoServiceImpl implements IUserInfoService {
	
	@Autowired
	private IUserInfoDAO userDAO;

	@Override
	public boolean register(UserInfo user) {
		// TODO Auto-generated method stub
		UserInfo userInfo = userDAO.findUserByUserName(user.getUserName());
		if (userInfo == null) {
			userDAO.register(user);
			return true;
		} else {
			System.out.println("该用户已存在!!!!!user_name: "+user.getUserName());
			return false;
		}
	}

	@Override
	public UserInfo loginCheck(UserInfo user) {
		// TODO Auto-generated method stub
		UserInfo userInfo = userDAO.findUserByUserName(user.getUserName());
		
		return userInfo;
	}

}
