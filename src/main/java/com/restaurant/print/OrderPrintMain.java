package com.restaurant.print;

import org.apache.log4j.Logger;

/************************************************************************
 * 在电脑本地运行
 * 每隔30秒扫描远程数据库，将未打印的订单信息全部打印出来
 * @author YUHB
 *
 */
public class OrderPrintMain implements Runnable {
	
	private static Logger logger = Logger.getLogger(OrderPrintMain.class);
	
	private static OrderPrintMain main = new OrderPrintMain();
	
	private OrderPrintMain(){
		this.start();
	}
	
	public static OrderPrintMain getInstance() {
		return main;
	}
	
	private void start(){
		Thread thread = new Thread(main);
		thread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			//查询未打印的订单
			String sql = "select * from order_item where status = 1";
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
