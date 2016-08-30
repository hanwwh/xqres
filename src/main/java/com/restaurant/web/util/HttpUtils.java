package com.restaurant.web.util;

import java.io.*;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.apache.log4j.Logger;

import com.restaurant.util.SystemUtil;

public class HttpUtils {
	
	private static Logger logger = Logger.getLogger(HttpUtils.class);

	/***************************************************************************
	 * 将信息发回页面
	 * @param response
	 * @param message
	 */
	public static void send(HttpServletResponse response, String message){
		PrintWriter writer = null;
		try {

			//设置字符集编码
			response.setCharacterEncoding(SystemUtil.getDefaultCharset());
			
			writer = response.getWriter();
			writer.write(message);
			writer.flush();
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("#send. error.", e);
		} finally {
			if (writer != null) {
				writer.close();
				writer = null;
			}
		}
	}

	/***************************************************************************
	 * 将信息发回页面，可设置字符集编码
	 * @param response
	 * @param message
	 * @param charset
	 */
	public static void send(HttpServletResponse response, String message, String charset){
		PrintWriter writer = null;
		try {

			//设置字符集编码
			if (SystemUtil.isEmpty(charset))
				charset = SystemUtil.getDefaultCharset();

			response.setCharacterEncoding(charset);

			writer = response.getWriter();
			writer.write(message);
			writer.flush();

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("#send. error.", e);
		} finally {
			if (writer != null) {
				writer.close();
				writer = null;
			}
		}
	}
	
	/***************************************************************************
	 * 根据参数名称从请求中取输入参数值
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String name){
		try {

			request.setCharacterEncoding(SystemUtil.getDefaultCharset());
			String value = request.getParameter(name);
			if (SystemUtil.isEmpty(value)) {
				return null;
			}

			return value;

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("#getParameter. error.", e);
		}

		return null;
	}
	
	/***************************************************************************
	 * 将输入流中数据转换成字符串
	 * @param inputStream
	 * @return
	 */
	public static String transferInputStream(InputStream inputStream) {
		ByteArrayOutputStream byteOut = null;
		try {
			
			byteOut = new ByteArrayOutputStream();
			int len = 0;
			byte[] bs = new byte[1024];
			while((len = inputStream.read(bs)) >= 0){
				byteOut.write(bs, 0, len);
			}

			return byteOut.toString(SystemUtil.getDefaultCharset());
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("#transferInputStream. 转换失败！！！", e);
		} finally {
			try {

				if (inputStream != null) {
					inputStream.close();
					inputStream = null;
				}
				
				if (byteOut != null) {
					byteOut.close();
					byteOut = null;
				}
				
			} catch (IOException e2) {
				// TODO: handle exception
				logger.error("#transferInputStream. 关闭流时异常！！！", e2);
			}
			
		}
		
		return null;
	}

	/***************************************************************************
	 * 将接收到的数据转换成json
	 * @param request
	 * @return
	 */
	public static Map<String, Object> parseJSON(HttpServletRequest request) {
		try {

			logger.info("#parseJSON. 取POST信息");
			String postInfo = transferInputStream(request.getInputStream());
			logger.info("#parseJSON. form表单信息: "+postInfo);
			Gson gson = new Gson();
			Map<String, Object> map = gson.fromJson(postInfo, Map.class);
			Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<String, Object> e = it.next();
				logger.info("#parseJSON. key: "+e.getKey()+", value: "+e.getValue());
			}

			return map;

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("#parseJSON. 转换失败！！！", e);
		}

		return null;
	}
}
