package com.restaurant.business.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.restaurant.business.bean.UserInfo;
import com.restaurant.business.service.interfaces.IUserInfoService;
import com.restaurant.system.service.interfaces.ISystemService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/spring-test.xml")
//@ContextConfiguration(locations = { "classpath*:spring.xml","classpath*:spring-redis.xml","classpath*:spring-jdbc.xml" })
//@ContextConfiguration(locations = { "classpath*:spring-context.xml","classpath*:spring-mvc.xml" })
public class UserServiceTest {

	@Autowired
	private IUserInfoService userInfoService;
	
	@Autowired
	private ISystemService systemService;
	
	@Test
	public void testLoginCheck(){
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(systemService.getNextValue("USER_INFO"));
		userInfo.setUserName("yuhb");
		if (userInfoService.loginCheck(userInfo) != null) {
			System.out.println("OK");
		} else {
			System.out.println("Sorry");
		}
	}
	
	@Test
	public void testRegister(){
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(systemService.getNextValue("USER_INFO"));
		userInfo.setUserName("yuhb1");
		System.out.println(userInfoService.register(userInfo));
	}
}
