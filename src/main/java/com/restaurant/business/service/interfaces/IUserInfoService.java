package com.restaurant.business.service.interfaces;

import com.restaurant.business.bean.UserInfo;

public interface IUserInfoService {
	public boolean register(UserInfo user);
	public UserInfo loginCheck(UserInfo user);
}
