package com.restaurant.web;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.restaurant.business.bean.FoodInfo;
import com.restaurant.business.bean.OrderItemFoodRelat;
import com.restaurant.system.service.interfaces.ISystemService;
import com.restaurant.util.SystemUtil;
import com.restaurant.web.bean.PrintOrderBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.restaurant.business.bean.OrderItem;
import com.restaurant.business.service.interfaces.IOrderItemService;
import com.restaurant.print.CommonPrinter;
import com.restaurant.print.PrinterInfo;
import com.restaurant.web.util.HttpUtils;

@Controller
@RequestMapping("/order")
public class OrderItemController {
	
	private static Logger logger = Logger.getLogger(OrderItemController.class);
	
	@Autowired
	private IOrderItemService itemService;

	@Autowired
	private ISystemService systemService;

	@RequestMapping("/query")
	public ModelAndView getOrderItems(HttpServletResponse response){
		PrintWriter writer = null;
		
		try {
			
			ModelAndView mav = new ModelAndView();
			List<OrderItem> list = itemService.queryOrderItems();
			JsonObject object = new JsonObject();
	        JsonArray array = new JsonArray();
			for(int i=0; i<list.size(); i++){
				OrderItem item = list.get(i);
				JsonObject ob = new JsonObject(); 
				ob.addProperty("id", item.getItemId());  
	            ob.addProperty("item_name", item.getItemName());  
	            ob.addProperty("item_price", item.getItemPrice());  
	            ob.addProperty("item_details", item.getItemDetails());  
	            ob.addProperty("item_picurl", item.getItemPicurl());  
	            ob.addProperty("item_code", item.getItemCode());  

	            array.add(ob);
			}
			
			object.add("items", array);
			writer = response.getWriter();
			writer.write(object.toString());
			writer.flush();
			
			return mav;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
				writer = null;
			}
		}
		
		return null;
	}
	
	@RequestMapping("/print")
	public String printOrderItem(HttpServletRequest request, HttpServletResponse response){
		try {

//			String itemId = request.getParameter("ITEM_ID");
			request.setCharacterEncoding(SystemUtil.getDefaultCharset());
			response.setCharacterEncoding(SystemUtil.getDefaultCharset());



			//跳转到打印页面
			return "/order/printOrder";

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("#printOrderItem. printOrderItem error", e);
		}

		return "/error";
	}

	@RequestMapping(value="/printOrder", method=RequestMethod.POST)
	public String printOrder(HttpServletRequest request, HttpServletResponse response){
		try {

			System.out.println("RequestURL: "+request.getRequestURL());
			System.out.println("ContextPath: "+request.getContextPath());
			System.out.println("PathInfo: "+request.getPathInfo());
			System.out.println("PathTranslated: "+request.getPathTranslated());
			System.out.println("ServletPath: "+request.getServletPath());

//			String itemId = request.getParameter("ITEM_ID");
			logger.info("#printOrder. 取POST信息");
			String postInfo = HttpUtils.transferInputStream(request.getInputStream());
			logger.info("#printOrder. form表单信息: "+postInfo);

			//保存订单
//			OrderItem item = new OrderItem();

			logger.info("#printOrder. 初始化打印机，请稍候...");
//			CommonPrinter printer = new CommonPrinter(1, postInfo);
//			CommonPrinter printer = new CommonPrinter(1, "订单信息123");
			PrinterInfo printerInfo = new PrinterInfo();

			logger.info("#printOrder. 完成打印机数据初始化，开始打印，请稍候...");
//			printer.print();
			printerInfo.printInfo();
			logger.info("#printOrder. 打印完成！！！");

			//跳转到打印成功页面
			return "/order/print_success";

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("#printOrder. printOrder error", e);
		}
		
		//跳转到打印失败页面
		return "/order/print_error";
	}

	@RequestMapping(value = "/selectFoods", method = RequestMethod.GET)
	public void selectFoods(HttpServletResponse response) {
		List<FoodInfo> list = itemService.selectFoods();
		JsonArray jsonArray = new JsonArray();

		JsonArray array = new JsonArray();

		for(FoodInfo info : list){
			JsonObject object = new JsonObject();
			object.addProperty("food_id", info.getFoodId());
			object.addProperty("food_name", info.getFoodName());
			object.addProperty("food_price", info.getFoodPrice());
			jsonArray.add(object);
		}

		String result = jsonArray.toString();
		logger.info("#selectFoods. result: "+result);
		HttpUtils.send(response, result, SystemUtil.getDefaultCharset());
	}

	@RequestMapping(value="/printInput", method=RequestMethod.POST)
	public void printInput(HttpServletRequest request, HttpServletResponse response) {

		int result = 1;

		try {

			request.setCharacterEncoding(SystemUtil.getDefaultCharset());
			response.setCharacterEncoding(SystemUtil.getDefaultCharset());
			logger.info("#printInput. Charset: "+SystemUtil.getDefaultCharset());
			logger.info("#printInput. 取POST信息");
			String postInfo = HttpUtils.transferInputStream(request.getInputStream());
			logger.info("#printInput. form表单信息: "+postInfo);

			Gson gson = new Gson();
			PrintOrderBean bean = gson.fromJson(postInfo, new TypeToken<PrintOrderBean>(){}.getType());
			logger.info("#printInput. PrintOrderBean: "+bean.toString());

			String item_name = bean.getItemName();
			float item_price = bean.getItemPrice();
			String item_remark = bean.getItemRemark();
			List<PrintOrderBean.Foods> foods = bean.getFoods();

			OrderItem item = new OrderItem();
			item.setItemId(systemService.getNextValue("ORDER_ITEM"));
			item.setItemName(item_name);
			item.setItemPrice(item_price);
			item.setStatus(1);
			item.setItemCode(itemService.nextOrderCode());
			item.setCreateDate(new Timestamp(System.currentTimeMillis()));
			item.setItemDetails(bean.getProductNo());//用details暂存手机号码
			item.setRemark(item_remark);

			List<OrderItemFoodRelat> list = new ArrayList<OrderItemFoodRelat>();
			List<PrintOrderBean.Foods> printFoods = new ArrayList<PrintOrderBean.Foods>();
			for(PrintOrderBean.Foods food : foods) {
				//被点的菜品才会被打印
				if (food.getFoodNum() > 0) {
					OrderItemFoodRelat relat = new OrderItemFoodRelat();
					relat.setItemId(item.getItemId());
					relat.setFoodId(food.getFoodId());
					relat.setDiscount(1);
					relat.setFoodCount(food.getFoodNum());
					list.add(relat);

					printFoods.add(food);
				}
			}

			logger.info("#printInput. 开始打印订单信息.");
			PrinterInfo info = new PrinterInfo(item, printFoods);
			CommonPrinter printer = new CommonPrinter(info);
			printer.printDefaultInfo();
			logger.info("#printInput. 完成打印订单信息.");

			logger.info("#printInput. 开始打印订单信息-----第二张.");
			printer.printDefaultInfo();
			logger.info("#printInput. 开始保存订单信息-----第二张.");

			int count = itemService.insertOrderItem(item);
			logger.info("#printInput. insertOrderItem success. count: "+count);

			count = itemService.saveOrderItemFoodRelat(list);
			logger.info("#printInput. saveOrderItemFoodRelat success. count: "+count);
			logger.info("#printInput. 完成保存订单信息.");

		} catch (Exception e) {
			logger.error("#printInput. printInput error.", e);
			result = 0;
		} finally {
//			String json = "{\"result\" : \""+result+"\"}";
			logger.info("#printInput. 操作结果标志位-----result: "+result);
			HttpUtils.send(response, ""+result);
		}
	}
	
}
