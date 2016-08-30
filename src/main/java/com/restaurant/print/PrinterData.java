package com.restaurant.print;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class PrinterData implements Printable {
	
	private int PAGES = 0;
    private String printStr;
    
	public PrinterData(int pAGES, String printStr) {
		super();
		PAGES = pAGES;
		this.printStr = printStr;
	}

	@Override
	public int print(Graphics gp, PageFormat pf, int page) throws PrinterException {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D) gp;
        g2.setPaint(Color.black); // 设置打印颜色为黑色
        if (page >= PAGES) // 当打印页号大于需要打印的总页数时，打印工作结束
        {
            return Printable.NO_SUCH_PAGE;
        }
        g2.translate(pf.getImageableX(), pf.getImageableY());// 转换坐标，确定打印边界
        Font font = new Font("宋体", Font.PLAIN, 8);// 创建字体
        g2.setFont(font);
        // 打印当前页文本
        int printFontCount = printStr.length();// 打印字数
        int printFontSize = font.getSize();// Font 的磅值大小
        float printX = 100 / 2; // 给定字符点阵，X页面正中,164.4
        float printY = 50 / 2; // 给定字符点阵，Y页面正中
        float printMX = printX - (printFontCount * printFontSize / 2);// 打印到正中间
        float printMY = printY - printFontSize / 2;// 打印到正中间
        g2.drawString(printStr, printMX, printMY); // 具体打印每一行文本，在指定位置
        /*g2.drawString(printStr, printMX - printFontSize * printFontCount,
                printMY + printFontSize); // 具体打印每一行文本 
        g2.drawString(printStr, printMX + printFontSize * printFontCount,
                printMY + printFontSize); // 具体打印每一行文本 
        g2.drawString(printStr, printMX, printMY + printFontSize * 2); // 具体打印每一行文本 
        g2.drawString(printStr, printMX, printMY + printFontSize * 2);
         g2.drawString(printStr, printMX, printMY + printFontSize * 2);
          g2.drawString(printStr, printMX, printMY + printFontSize * 2);*/
        return Printable.PAGE_EXISTS;
	}

}
