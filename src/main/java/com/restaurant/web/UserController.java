package com.restaurant.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.restaurant.business.bean.UserInfo;
import com.restaurant.business.service.interfaces.IUserInfoService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@RequestMapping("/loginView")
	public String loginView(){
		return "login";
	}
	
	@RequestMapping("/registerView")
	public String registerView(){
		return "/test/register";
	}
	
	@RequestMapping("/login")
	public ModelAndView login(UserInfo user){
		ModelAndView mav = new ModelAndView();
		UserInfo userInfo = userInfoService.loginCheck(user);
		if (userInfo == null) {
			mav.setViewName("login");
			mav.addObject("errorMsg","用户名或密码有误！");
			return mav;
		} else {
			mav.setViewName("success");
			mav.addObject("user", userInfo);
			return mav;
		}
	}
	
	@RequestMapping("register")
	public ModelAndView register(UserInfo user){
		ModelAndView mav = new ModelAndView();
		if (userInfoService.register(user)) {
			mav.setViewName("register_success");
			return mav;
		} else {
			mav.setViewName("register");
			mav.addObject("errorMsg","用户名已被占用，请更换！！");
			return mav;
		}
	}
}
