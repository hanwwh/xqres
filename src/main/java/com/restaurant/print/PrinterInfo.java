package com.restaurant.print;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;

import com.restaurant.business.bean.FoodInfo;
import com.restaurant.business.bean.OrderItem;
import com.restaurant.util.SystemConstant;
import com.restaurant.util.SystemUtil;
import com.restaurant.web.bean.PrintOrderBean;
import org.apache.log4j.Logger;

public class PrinterInfo extends AbstractPrinter {
	
	private static Logger logger = Logger.getLogger(PrinterInfo.class);

	private OrderItem item;
	private List<PrintOrderBean.Foods> foodInfos;

	private int line_cnt = 11;
	private int remark_cnt = 3;
	private int start_x = 4;

	private static final String DEFAULT_PRINTER_NAME = SystemUtil.getProperty(SystemConstant.DEFAULT_PRINTER_NAME);

	public PrinterInfo() {

	}

	public PrinterInfo(OrderItem item, List<PrintOrderBean.Foods> foodInfos) {
		this.item = item;
		this.foodInfos = foodInfos;
		this.remark_cnt = this.getRemarkLen("其他信息："+item.getRemark());
	}

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		// TODO Auto-generated method stub
		logger.info("#print. pageIndex: "+pageIndex);

		Graphics2D g2d = (Graphics2D) graphics;
		g2d.setColor(Color.BLACK);

		switch(pageIndex){
			case 0:
				//设置打印字体（字体名称、样式和点大小）（字体名称可以是物理或者逻辑名称）
				//Java平台所定义的五种字体系列：Serif、SansSerif、Monospaced、Dialog 和 DialogInput
				g2d.setFont(new Font("新宋体", Font.PLAIN, 9));

				g2d.drawString("**********************************", start_x, 15);

				g2d.setFont(new Font("新宋体", Font.PLAIN, 12));
				g2d.drawString("米欧MIO简餐", 45, 30);
				g2d.setFont(new Font("新宋体", Font.PLAIN, 9));
				g2d.drawString("下单时间："+dateFormat.format(item.getCreateDate()), start_x, 45);
				g2d.drawString("订单编号："+item.getItemCode(), start_x, 55);
				g2d.drawString("------------订单信息------------", start_x, 70);
				int front_len = 17;
				int behind_len = 6;
				/*int space_behind_len = 6;
				//找最小值，使得每个菜品的总价左对齐
				for(PrintOrderBean.Foods foodInfo : foodInfos){
					int len = behind_len-
							(""+foodInfo.getFoodNum()).length()-(""+foodInfo.getFoodPrice()).length();
					if (space_behind_len > len)
						space_behind_len = len;
				}*/
				for(int i=0; i<foodInfos.size(); i++){
					PrintOrderBean.Foods foodInfo = foodInfos.get(i);
					int space_front_len = front_len-foodInfo.getFoodName().length()*2;
					logger.info("#print. space_front_len: "+space_front_len);
					int space_behind_len = behind_len-(""+foodInfo.getFoodNum()).length();
					logger.info("#print. space_behind_len: "+space_behind_len);
					StringBuffer buffer = new StringBuffer();
					for(int j=0; j<space_front_len; j++){
						buffer.append(" ");
					}
					String line = foodInfo.getFoodName()+buffer.toString()+"X"+foodInfo.getFoodNum();
					buffer.delete(0, space_front_len);
//					buffer = new StringBuffer();
					for(int j=0; j<space_behind_len; j++){
						buffer.append(" ");
					}
					line = line+buffer.toString()+"￥"+foodInfo.getFoodPrice();
					logger.info("#print. line: "+line);
					g2d.drawString(line, start_x, 70+15*(i+1));
					buffer = null;
				}

				int height = 70+15*foodInfos.size()+15;
				g2d.drawString("---------------------------------", start_x, height);

				g2d.setFont(new Font("新宋体", Font.PLAIN, 12));
				int white_space_len = 18-(""+item.getItemPrice()).length();
				StringBuffer buffer = new StringBuffer();
				for(int i=0; i<white_space_len; i++){
					buffer.append(" ");
				}
				g2d.drawString("应付"+buffer.toString()+"￥"+item.getItemPrice(), start_x, height+15);
				buffer = null;
				g2d.setFont(new Font("新宋体", Font.PLAIN, 9));
				g2d.drawString("---------------------------------", start_x, height+30);
				g2d.setFont(new Font("新宋体", Font.PLAIN, 12));
				g2d.drawString("备注：", start_x, height+40);
				g2d.drawString("手机："+item.getItemDetails(), start_x, height+55);
				int height_1 = height+55;
				String remark = "其他："+item.getRemark();
				/*int remark_len = remark.length();
				System.out.println("----->remark.length(): "+remark_len);//41
				int remark_num = remark_len%line_cnt;//11
				int remark_cnt = remark_len/line_cnt;//2
				System.out.println("----->remark_cnt: "+remark_cnt+", remark_num: "+remark_num);
				if (remark_num > 0)
					remark_cnt++;*/
				System.out.println("----->remark_cnt: "+remark_cnt);
				for(int i=0; i<remark_cnt; i++){
					if (i<remark_cnt-1)
						g2d.drawString(remark.substring(line_cnt*i, line_cnt*(i+1)), start_x, height_1+15*(i+1));
					else
						g2d.drawString(remark.substring(line_cnt*(remark_cnt-1)), start_x, height_1+15*(i+1));
				}

				int height_2 = height_1+15*remark_cnt+15;

				g2d.setFont(new Font("新宋体", Font.PLAIN, 9));
				g2d.drawString("****************END***************", start_x, height_2);
				System.out.println("------------>height_2: "+height_2);

				return PAGE_EXISTS;
			case 1:
				System.out.println("----->000000");
				return PAGE_EXISTS;
			default:
				System.out.println("----->111111");
				return NO_SUCH_PAGE;

		}

	}
	
	public void printInfo() throws Exception{
//		int height = 225+15*foodInfos.size();
		int height = getHeight();
		
		Book book = new Book();
		//设置成竖打 
	    PageFormat pf = new PageFormat();
	    pf.setOrientation(PageFormat.PORTRAIT);
	    
	    //通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。  
	    
	    Paper p = new Paper();
//	    p.setSize(590, 840);
	    p.setSize(163,height);//纸张大小
	    //A4(595 X 842)设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72
//	    p.setImageableArea(10,10, 590,840);
	    p.setImageableArea(start_x, -20, 163, height + 20);
	    pf.setPaper(p);
	    
	    //把 PageFormat 和 Printable 添加到书中，组成一个页面 
	    book.append(this, pf);
	    
	    //获取打印机服务对象， 默认打印机
	    PrinterJob job = PrinterJob.getPrinterJob();
	    logger.info("#printInfo. jobName: "+job.getJobName()+", userName； "+job.getUserName());
	    logger.info("#printInfo. 打印机名称: "+job.getPrintService().getName());
	    
	    //日志输出打印机的各项属性  
        AttributeSet attrs = job.getPrintService().getAttributes();
        logger.info("****************************************************");
        for (Attribute attr : attrs.toArray()) {
            String attributeName = attr.getName();
            String attributeValue = attrs.get(attr.getClass()).toString();
            logger.info("*"+attributeName + " : " + attributeValue);
        }
        logger.info("****************************************************");
        
	    //设置打印类
	    job.setPageable(book);
	    
        //可以用printDialog显示打印对话框，在用户确认后打印；也可以直接打印 
        /*boolean a=job.printDialog();
        logger.info("#print. a="+a);*/
	    
	    job.print();
	    
	}

	private int getRemarkLen(String remark){
		int remark_len = remark.length();
		System.out.println("----->remark.length(): "+remark_len);//41
		int remark_num = remark_len%line_cnt;//11
		int remark_cnt = remark_len/line_cnt;//2
		System.out.println("----->remark_cnt: "+remark_cnt+", remark_num: "+remark_num);
		if (remark_num > 0)
			remark_cnt++;
		return remark_cnt;
	}

	@Override
	public int getHeight() {
		return 165 + 15*foodInfos.size() + 15*this.remark_cnt;
	}

	@Override
	public int getStartX() {
		return this.start_x;
	}
	
	public static void main(String[] args) {
		OrderItem item = new OrderItem();
		item.setItemId(10000010);
		item.setItemName("米欧外卖订单");
		item.setItemPrice(21.00f);
		item.setStatus(1);
		item.setItemCode("201608270010");
		item.setCreateDate(new Timestamp(System.currentTimeMillis()));
		item.setRemark("手机号码：15057158629；地址：广业街长阳路口都市水乡水起苑3-3-301");

		List<PrintOrderBean.Foods> foodList = new ArrayList<PrintOrderBean.Foods>();
		PrintOrderBean.Foods foodInfo = new PrintOrderBean.Foods();
		foodInfo.setFoodId(10000005);
		foodInfo.setFoodName("米欧泡菜拌饭");
		foodInfo.setFoodPrice(38.00f);
		foodList.add(foodInfo);

		PrintOrderBean.Foods foodInfo1 = new PrintOrderBean.Foods();
		foodInfo1.setFoodId(10000006);
		foodInfo1.setFoodName("海鲜膳酱拌饭");
		foodInfo1.setFoodPrice(11.00f);
		foodList.add(foodInfo1);

		PrintOrderBean.Foods foodInfo2 = new PrintOrderBean.Foods();
		foodInfo2.setFoodId(10000007);
		foodInfo2.setFoodName("波隆那酱拌饭");
		foodInfo2.setFoodPrice(13.00f);
		foodList.add(foodInfo2);

		PrintOrderBean.Foods foodInfo3 = new PrintOrderBean.Foods();
		foodInfo3.setFoodId(10000008);
		foodInfo3.setFoodName("日式咖喱拌饭");
		foodInfo3.setFoodPrice(19.00f);
		foodList.add(foodInfo3);

		PrinterInfo info = new PrinterInfo(item, foodList);
		try {

//			info.printInfo();
			CommonPrinter printer = new CommonPrinter(info);
//			printer.print();
//			printer.printDefaultInfo();
			System.out.println("中国记录".length()+"中国记录");
			System.out.println("ABCD".length()+"1234");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
