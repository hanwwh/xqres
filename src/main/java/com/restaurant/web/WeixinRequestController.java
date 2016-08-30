package com.restaurant.web;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.restaurant.util.SystemUtil;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qq.weixin.mp.demo.SHA1;
import com.restaurant.system.bean.WeixinMessageBean;
import com.restaurant.web.util.HttpUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

@Controller
@RequestMapping("/weixin")
public class WeixinRequestController {
	
	private static Logger logger = Logger.getLogger(WeixinRequestController.class);
	
	// 自定义 token
	private String TOKEN = "CA1972BA718818B816122A5B3E040F66";
	
	/************************************************************************
	 * 根据加密信息进行请求来源验证
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	private boolean check(String signature, String timestamp, String nonce) {
		try {
			
			String[] str = { TOKEN, timestamp, nonce };
			Arrays.sort(str); // 字典序排序
			String bigStr = str[0] + str[1] + str[2];
			
			// SHA1加密
			String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();
			logger.info("#check. digest: "+digest);
			
			return digest.equals(signature);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("#check. check error. ", e);
		}
		
		return false;
	}
	
	/************************************************************************
	 * 验证来自微信服务器的请求消息，验证通过之后返回信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/token", method = RequestMethod.GET)
	public void checkToken(HttpServletRequest request, HttpServletResponse response){
		
		try {
			
			// 微信加密签名
			String signature = request.getParameter("signature");
			// 随机字符串
			String echostr = request.getParameter("echostr");
			// 时间戳
			String timestamp = request.getParameter("timestamp");
			// 随机数
			String nonce = request.getParameter("nonce");
			
			logger.info("#checkToken. signature: "+signature);
			logger.info("#checkToken. echostr: "+echostr);
			logger.info("#checkToken. timestamp: "+timestamp);
			logger.info("#checkToken. nonce: "+nonce);
			
			// 确认请求来至微信
			if (this.check(signature, timestamp, nonce)) {
				logger.info("#checkToken. 验证成功!!!");
				//如果echostr不为空，则表示正在做服务器的配置验证，将echostr重新返回给微信服务器，完成验证
				logger.info("#checkToken. 向微信服务器返回echostr: "+echostr+", 以确保服务器的配置验证成功!");
				HttpUtils.send(response, echostr);
			} else {
				logger.info("#checkToken. 验证失败，请检查!!!signature： "+signature+", TOKEN: "+TOKEN);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("checkToken error.", e);
			HttpUtils.send(response, "好像出错啦，不好意思哈^0^");
		}
	}
	
	/************************************************************************
	 * 接收微信服务器发来的各种消息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public void process(HttpServletRequest request, HttpServletResponse response){
		try {
			
			long start = System.currentTimeMillis();
			
			// 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
	        request.setCharacterEncoding(SystemUtil.getDefaultCharset());
	        response.setCharacterEncoding(SystemUtil.getDefaultCharset()); 
			
			// 微信加密签名
			String signature = request.getParameter("signature");
			// 随机字符串
			String echostr = request.getParameter("echostr");
			// 时间戳
			String timestamp = request.getParameter("timestamp");
			// 随机数
			String nonce = request.getParameter("nonce");
			
			logger.info("#process. signature: "+signature);
			logger.info("#process. echostr: "+echostr);
			logger.info("#process. timestamp: "+timestamp);
			logger.info("#process. nonce: "+nonce);
			
			// 确认请求来至微信
			if (this.check(signature, timestamp, nonce)) {
				logger.info("#process. 接收并处理用户发来的消息.");
				
				Map<String, String> map = this.parseXml(request);
				WeixinMessageBean messageBean = new WeixinMessageBean();
//				messageBean.setToUserName(map.get("ToUserName"));
//				messageBean.setFromUserName(map.get("FromUserName"));
				messageBean.setToUserName(map.get("FromUserName"));
				messageBean.setFromUserName(map.get("ToUserName"));
				messageBean.setCreateTime(map.get("CreateTime"));
				messageBean.setMsgType(map.get("MsgType"));
				messageBean.setContent(map.get("Content"));
				messageBean.setMsgId(map.get("MsgId"));
				logger.info("#process. messageBean: "+messageBean.toString());
				
				String result = processMessage(messageBean);
				logger.info("#process. result: "+result);
				messageBean.setContent(result);
//				messageBean.setContent("Welcome to my domain...");
				
				//转换成XML
				XStream xStream = this.createXStream();
				xStream.alias("xml", messageBean.getClass());
				String respMsg = xStream.toXML(messageBean);
				logger.info("#process. respMsg: "+respMsg);
				
				HttpUtils.send(response, respMsg);
				
				logger.info("#process. 处理该POST请求共耗时: "+(System.currentTimeMillis() - start)+" 毫秒！");
				
			} else {
				logger.info("#process. 验证失败，该消息不是来自微信服务器，请检查!!!signature： "+signature+", TOKEN: "+TOKEN);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("#process. process error.", e);
		}
	}
	
	/************************************************************************
	 * 处理微信服务器发来的XML格式的消息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		
		InputStream inputStream = null;
		
		try {
			
			// 从request中取得输入流
			inputStream = request.getInputStream();
			if (inputStream == null || inputStream.available() == 0) {
				logger.error("#parseXml. inputStream为空。 接收的消息为空，请检查！开始抛出异常！");
				throw new NullPointerException("#parseXml. inputStream为空。 接收的消息为空，请检查！");
			}
			
			logger.info("parseXml. 消息长度："+inputStream.available());
			
	        // 将解析结果存储在HashMap中  
	        Map<String, String> map = new HashMap<String, String>();
	        // 读取输入流  
	        SAXReader reader = new SAXReader();
	        Document document = reader.read(inputStream);
	        // 得到xml根元素  
	        Element root = document.getRootElement();
	        // 得到根元素的所有子节点  
	        List<Element> elementList = root.elements();
	        
	        // 遍历所有子节点  
	        for (Element e : elementList)
	            map.put(e.getName(), e.getText());
	        
	        return map;
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("#parseXml. parseXml error. ", e);
			throw e;
		} finally {
			// 释放资源 
			if (inputStream != null) {
		        inputStream.close();
		        inputStream = null;
			}
		}

    }
	
	/************************************************************************
	 * 创建xml转换类
	 * @return XStream
	 */
	private XStream createXStream() {
		XStream xStream = new XStream(new XppDriver(){
			@Override
			public HierarchicalStreamWriter createWriter(Writer out) {
				// TODO Auto-generated method stub
				return new PrettyPrintWriter(out) {  
	                // 对所有xml节点的转换都增加CDATA标记  
	                boolean cdata = true;  
	  
	                @SuppressWarnings("unchecked")  
	                public void startNode(String name, Class clazz) {  
	                    super.startNode(name, clazz);  
	                }  
	  
	                protected void writeText(QuickWriter writer, String text) {  
	                    if (cdata) {  
	                        writer.write("<![CDATA[");  
	                        writer.write(text);  
	                        writer.write("]]>");  
	                    } else {  
	                        writer.write(text);  
	                    }  
	                }  
	            };  
			}
		});
		
		return xStream;
	}
	
	/************************************************************************
	 * 处理用户发来的各种不同类型的消息
	 * @param messageBean
	 * @return
	 */
	private String processMessage(WeixinMessageBean messageBean){
		StringBuffer result = new StringBuffer();
		String content = messageBean.getContent();
		logger.info("#processMessage. content: "+content);
		switch (content) {
		case "0":
			result.append("欢迎【").append(messageBean.getToUserName()).append("】，新朋友！");
			break;
		case "1":
			result.append("谢谢惠顾【").append(messageBean.getToUserName()).append("】，欢迎下次光临！");
			break;
		case "2":
			result.append("您好，").append(messageBean.getToUserName()).append("，有什么可以为你服务？");
			break;
		case "3":
			result.append("欢迎【").append(messageBean.getToUserName()).append("】来到我的公众号！");
			break;
		case "yuhb":
			result.append("大傻帽儿……哦……");
			break;
		case "xuzz":
			result.append("小美妞儿^o^");
			break;

		default:
			result.append("请求处理异常，请稍候尝试！");
			break;
		}
		
		return result.toString();
	}
	
	@RequestMapping("/tokenbak")
	public String checkTokenBak(HttpServletRequest request, HttpServletResponse response, Model model){
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		
		logger.info("----->user.dir: "+System.getProperty("user.dir"));
		logger.info("----->xqres.root: "+System.getProperty("xqres.root"));
		logger.info("----->xqres.root: "+System.getProperty("xqres.root"));
		logger.info("----->signature: "+signature);
		logger.info("----->echostr: "+echostr);
		logger.info("----->timestamp: "+timestamp);
		logger.info("----->nonce: "+nonce);
		
		model.addAttribute("token", TOKEN);
		model.addAttribute("signature", signature);
		model.addAttribute("echostr", echostr);
		model.addAttribute("timestamp", timestamp);
		model.addAttribute("nonce", nonce);
		
		PrintWriter writer = null;
		
		try {
			
//			writer = response.getWriter();
			if (TOKEN.equals(signature)) {
				logger.info("验证成功!!!");
//				writer.println(echostr);
//				writer.flush();
				model.addAttribute("result", "success");
			} else {
				logger.info("验证失败，请检查!!!signature： "+signature+", TOKEN: "+TOKEN);
				model.addAttribute("result", "fail");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
				writer = null;
			}
		}
		
		return "/test/tokenCheck";
	}
}
