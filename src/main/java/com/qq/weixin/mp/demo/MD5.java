package com.qq.weixin.mp.demo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new MD5().encode("yuhb_411524"));
	}
	
	public String encode(String source) {
		try {
			
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] bbs = source.getBytes();
			digest.update(bbs);
			byte[] result = digest.digest();
			System.out.println(result.length);
			StringBuffer sBuffer = new StringBuffer();
			for (int i = 0; i < result.length; i++) {
				sBuffer.append(","+result[i]);
			}
			System.out.println(sBuffer.toString().substring(1));
			return byteArrayToHex(result);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String byteArrayToHex(byte[] byteArray) {  
        
        // 首先初始化一个字符数组，用来存放每个16进制字符  
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };  
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））  
        char[] resultCharArray =new char[byteArray.length * 2];  
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去  
        int index = 0; 
        for (byte b : byteArray) {  
           resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];  
           resultCharArray[index++] = hexDigits[b& 0xf];  
        }
        // 字符数组组合成字符串返回  
        return new String(resultCharArray);  
    }

}
