package com.restaurant.business.dao.interfaces;

import com.restaurant.business.bean.UserInfo;

public interface IUserInfoDAO {

	public void register(UserInfo user);
	public UserInfo findUserByUserName(final String userName);
}
