package com.restaurant.web;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web")
public class WebController {

	private static Logger logger = Logger.getLogger(WebController.class);
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String forward(@RequestParam String id) {
		logger.info("#forward. 页面跳转, id: "+id);
		String result = "/index/summary";
		switch (id) {
			case "summary_id" ://首页
				result = "/index/summary";
				break;
			case "item_id" ://订单管理
				result = "/order/printInputOrder";
				break;
			case "food_id" ://菜品管理/index/summary
				result = "/index/summary";
				break;
			case "user_id" ://用户管理
				result = "/index/summary";
				break;
			case "manage_id" ://管理中心
				result = "/index/summary";
				break;
			case "login_id" ://登陆
				result = "/index/summary";
				break;
			case "register_id" ://注册
				result = "/index/summary";
				break;
			case "logout_id" ://退出
				result = "/index/summary";
				break;
			default:
				result = "/index/summary";
				break;
		}

		logger.info("#forward. 跳转结果result: "+result);
		return result;
	}

}
