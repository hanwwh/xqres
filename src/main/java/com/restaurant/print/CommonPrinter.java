package com.restaurant.print;

import java.awt.print.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

import com.restaurant.util.SystemConstant;
import com.restaurant.util.SystemUtil;
import org.apache.log4j.Logger;

/************************************************************************
 * 连接打印机，并打印数据
 * 这些打印机必须是本地电脑已经连接上的打印机
 * @author YUHB
 *
 */
public class CommonPrinter {
	
	private static Logger logger = Logger.getLogger(CommonPrinter.class);
	
	private int copyNum = 1;

	private PrintRequestAttributeSet attributeSet;
	
	private Doc doc;
	
	//设置打印数据的格式，此处默认为HTML的字符串信息 
//	private DocFlavor printFormat = DocFlavor.STRING.TEXT_HTML;
//	private DocFlavor printFormat = DocFlavor.STRING.TEXT_PLAIN;
	private DocFlavor printFormat = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
	
	//打印HTML格式的字符串
	private String html;
	
	//默认打印机名称
//	public static final String PRINTER_NAME = "HP LaserJet";
//	public static final String PRINTER_NAME = "GP-5850";
	public static final String PRINTER_NAME = SystemUtil.getProperty(SystemConstant.DEFAULT_PRINTER_NAME);
	
	//打印文件
	private File file;

	//打印信息接口
	private AbstractPrinter printerInfo;
	
	/************************************************************************
	 * 按照默认配置设置打印机
	 * 打印1份，按照默认的HTML格式打印
	 */
	public CommonPrinter(){
		attributeSet = new HashPrintRequestAttributeSet();
		attributeSet.add(new Copies(this.copyNum));
	}
	
	/************************************************************************
	 * 设置打印份数
	 * 按照默认的HTML格式打印
	 * @param copyNum
	 */
	public CommonPrinter(int copyNum){
		this.copyNum = copyNum;
		attributeSet = new HashPrintRequestAttributeSet();
		attributeSet.add(new Copies(this.copyNum));
	}
	
	/************************************************************************
	 * 设置打印份数
	 * 设置打印格式
	 * @param copyNum
	 * @param printFormat
	 */
	public CommonPrinter(int copyNum, DocFlavor printFormat){
		this(copyNum);
		this.printFormat = printFormat;
	}
	
	/************************************************************************
	 * 打印文件
	 * @param copyNum
	 * @param file
	 */
	public CommonPrinter(int copyNum, File file){
		this(copyNum, DocFlavor.INPUT_STREAM.AUTOSENSE);
		this.file = file;
	}
	
	/************************************************************************
	 * 打印文件
	 * @param printerInfo
	 */
	public CommonPrinter(AbstractPrinter printerInfo){
		this(1, DocFlavor.SERVICE_FORMATTED.PRINTABLE);
		this.printerInfo = printerInfo;
	}

	/************************************************************************
	 * 打印文件
	 * @param copyNum
	 * @param html
	 */
	public CommonPrinter(int copyNum, String html){
		this(copyNum);
		this.html = html;
	}
	
	/************************************************************************
	 * 搜索打印机
	 * 根据设置的默认打印机【PRINTER_NAME】来返回打印机服务
	 * @return PrintService
	 */
	protected PrintService lookupPrinter() throws Exception {
		try {
			
			//查找所有打印服务 (这些打印机必须已经被本机电脑添加) 
	        PrintService[] services = PrintServiceLookup.lookupPrintServices(printFormat, this.attributeSet);
	        logger.info("#lookupPrinters. 搜索到的打印机总数: "+services.length);
	        
			for (PrintService printService : services) {
				String printerName = printService.getName();
				logger.info("#lookupPrinters. 搜索到的打印机: "+printerName);
				if (printerName.contains(PRINTER_NAME)) {
					logger.info("#lookupPrinters. 已搜索到默认打印机: "+printerName);
					return printService;
				}
				
			}
	        
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("#lookupPrinters. 没有找到打印机或出现异常，请检查！！！.");
			throw new Exception("没有找到打印机或检索过程中出现异常，请检查！！！", e);
		}
		
		logger.error("#lookupPrinter. 没有找到打印机，请检查！！！");
		return null;
	}
	
	/************************************************************************
	 * 打印
	 */
	public void print() throws Exception {
		try {
			
			//搜寻打印机
			PrintService printer = this.lookupPrinter();
			if (printer == null) {
				throw new NullPointerException("printer == null. 没有找到默认打印机！！！");
			}
			
			//日志输出打印机的各项属性  
	        AttributeSet attrs = printer.getAttributes();
	        logger.info("****************************************************");
	        for (Attribute attr : attrs.toArray()) {
	            String attributeName = attr.getName();
	            String attributeValue = attrs.get(attr.getClass()).toString();
	            logger.info("*"+attributeName + " : " + attributeValue);
	        }
	        logger.info("****************************************************");
			
			//创建打印数据  
//	     	DocAttributeSet docAttr = new HashDocAttributeSet();//设置文档属性  
//	      	Doc myDoc = new SimpleDoc(psStream, psInFormat, docAttr);
	        DocAttributeSet das = new HashDocAttributeSet();
			Object printData = this.prepareData();
			logger.info("#print. 开始打印, 数据资源-printData: "+printData);
			if (printData == null) {
				throw new NullPointerException("printData == null. 准备数据失败！！！");
			}
			
			doc = new SimpleDoc(printData, printFormat, das);
			
			//创建文档打印作业
			long start = System.currentTimeMillis();
			logger.info("#print. 开始打印, 请稍候...");
			DocPrintJob job = printer.createPrintJob();
			job.print(doc, attributeSet);
			logger.info("#print. 完成打印, 共耗时: "+(System.currentTimeMillis() - start)+" 毫秒.");
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("#print. print error.", e);
			throw new Exception("打印过程中出现异常情况，打印没有完成，请检查！！！", e);
		}
	}

	/************************************************************************
	 * 采用默认打印机打印
	 */
	public void printDefaultInfo() throws Exception{
		int height = this.printerInfo.getHeight();

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
		p.setImageableArea(5, -20, 163, height + 20);
		pf.setPaper(p);

		//把 PageFormat 和 Printable 添加到书中，组成一个页面
		book.append(this.printerInfo, pf);

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
	
	/************************************************************************
	 * 准备打印数据
	 * @return
	 */
	protected Object prepareData() {
		
		try {
			
			logger.info("#prepareData. printFormat: "+printFormat);
			if (this.printFormat == DocFlavor.SERVICE_FORMATTED.PRINTABLE) {
//				PrinterData printerData = new PrinterData(1, this.html);
				logger.info("#prepareData. 准备数据-printerInfo: "+this.printerInfo.toString());
				return this.printerInfo;
			} else if (this.printFormat == DocFlavor.STRING.TEXT_HTML) {
				return this.html;
			} else if (this.printFormat == DocFlavor.STRING.TEXT_PLAIN) {
				return this.html;
			}  else if (this.printFormat == DocFlavor.INPUT_STREAM.AUTOSENSE) {
				InputStream inputStream = new FileInputStream(this.file);
				return inputStream;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("#prepareData. prepareData error.", e);
		}
		
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		String html = "<html><body>zzz</body></html>";
		CommonPrinter printer = new CommonPrinter(1, html);
		printer.print();
	}
}
