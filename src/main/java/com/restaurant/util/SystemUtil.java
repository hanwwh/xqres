package com.restaurant.util;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.util.Properties;

public class SystemUtil {

	private static Logger logger = Logger.getLogger(SystemUtil.class);

	private static Properties SYSTEM_PROPERTIES = new Properties();

	private static final String SYSTEM_PROPERTIES_FILE = "/system.properties";

	static {
		try {

			logger.info("#static block. SYSTEM_PROPERTIES_FILE: "+SYSTEM_PROPERTIES_FILE);
			SYSTEM_PROPERTIES.load(SystemUtil.class.getResourceAsStream(SYSTEM_PROPERTIES_FILE));

		} catch (Exception e) {
			logger.error("#static block. error. ", e);
		}
	}

	/************************************
	 * 返回系统配置变量值
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
//		logger.info("#getProperty. key: "+key+", value: "+SYSTEM_PROPERTIES.getProperty(key));
		return SYSTEM_PROPERTIES.getProperty(key);
	}

	public static String getDefaultCharset() {
		return getProperty(SystemConstant.SYSTEM_CHARSET);
	}

	/************************************
	 * 判断字符串是否为空
	 * @param source
	 * @return boolean
	 * @author YUHB
	 */
	public static boolean isEmpty(String source) {
		return source == null || 
			   source.length() == 0 || 
			   "".equals(source) || 
			   "null".equals(source);
	}
	
	/************************************
	 * 合并字符数组
	 * @param strs
	 * @return String
	 * @author YUHB
	 */
	public static String join(String[] strs){
		return join(strs, ",");
	}
	
	/************************************
	 * 合并字符数组
	 * @param strs
	 * @return String
	 * @author YUHB
	 */
	public static String join(String[] strs, String splitChar){
		if (strs.length == 1) {
			return strs[0];
		}
		String str = "";
		for (int i = 0; i < strs.length; i++) {
			str = str + splitChar + strs[i];
		}
		
		return str.substring(1);
	}
	
	/************************************
	 * 返回MD5加密
	 * @param source
	 * @return
	 */
	public static String MD5(String source){
		return new com.qq.weixin.mp.demo.MD5().encode(source);
	}
	
	/************************************
	 * 返回SHA1加密
	 * @param source
	 * @return
	 */
	public static String SHA1(String source){
		return new com.qq.weixin.mp.demo.SHA1().getDigestOfString(source.getBytes());
	}
}
