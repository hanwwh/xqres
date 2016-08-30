package com.restaurant.print;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("printTaskService")
public class PrintTaskService {
	
	private static Logger logger = Logger.getLogger(PrintTaskService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/***********************************************************
	 * 系统启动之后开始循环扫描订单表
	 * 打印订单信息
	 */
	@PostConstruct
	public void print(){
		logger.info("#print. --------------------->@PostConstruct");
		/*while(true){
			//查询未打印的订单
			String sql = "select * from order_item where status = 1";
		}*/
		
		//起自动打印线程
		CommonPrinter printer = new CommonPrinter(1, "");
	}
	
	/***********************************************************
	 * 实例销毁之前
	 */
	@PreDestroy
	public void destory(){
		logger.info("#destory. --------------------->@PreDestroy");
	}
}
