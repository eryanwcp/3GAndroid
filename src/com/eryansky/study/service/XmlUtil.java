package com.eryansky.study.service;

import java.io.InputStream;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.eryansky.study.util.StreamTool;

/**
 * XML工具类 
 * @author 尔演&Eryan eryanwcp@163.com 
 * @date 2011-9-28 下午10:50:41 
 */
public class XmlUtil {

	/**
	 * 读取xml文件
	 * @param inStream
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String readXmlFile(InputStream inStream, Map<String, String> params) throws Exception{
		byte[] data = StreamTool.readInputStream(inStream);
		String xml = new String(data);
		return replace(xml, params).trim().replaceAll("\\s2|\t|\r|\n", "");//去除字符串中的回车换行符\r\n 减少网络流量
	}
	
	/**
	 * xml文件占位符$替换，通用方法
	 * @param xml
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String replace(String xml, Map<String, String> params)throws Exception{
		String result = xml;
		if(params!=null && !params.isEmpty()){
			for(Map.Entry<String, String> entry : params.entrySet()){
				String name = "\\$"+ entry.getKey();
				Pattern pattern = Pattern.compile(name);
				Matcher matcher = pattern.matcher(result);
				if(matcher.find()){
					result = matcher.replaceAll(entry.getValue());
				}
			}
		}
		return result;
	}
	
}
