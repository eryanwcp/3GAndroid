package com.eryansky.study.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;

import com.eryansky.study.util.StreamTool;

import android.util.Xml;
/**
 * 根据webservice查询天气预报 Service 
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2011-10-14 上午08:37:00 
 *
 */
public class MobileInfoMangager {

	/**
	 * 生成请求xml实体数据
	 * @param inStream
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	private static String readSoapFile(InputStream inStream, String mobile) throws Exception{
		byte[] data = StreamTool.readInputStream(inStream);
		String soapxml = new String(data);
		Map<String, String> params = new HashMap<String, String>();
		params.put("mobile", mobile);
		return XmlUtil.replace(soapxml, params);
	}

	/**
	 * 请求并获取响应结果 
	 * @param inStream
	 * @param mobile 手机号
	 * @return 手机号归属地信息
	 * @throws Exception
	 */
	public static String getMobileAddress(InputStream inStream, String mobile)throws Exception{
		String soap = readSoapFile(inStream, mobile);
		byte[] data = soap.getBytes();
		URL url = new URL("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx");
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(6 * 1000);
		conn.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据
		conn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(data);
		outStream.flush();
		outStream.close();
		if(conn.getResponseCode()==200){
			return parseResponseXML(conn.getInputStream());
		}
		return null;
	}

	/**
	 * 解析webservice返回的xml数据
	 * @param inStream
	 * @return 
	 * @throws Exception
	 */
	private static String parseResponseXML(InputStream inStream) throws Exception{
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(inStream, "UTF-8");
		int eventType = parser.getEventType();//产生第一个事件
		while(eventType!=XmlPullParser.END_DOCUMENT){//只要不是文档结束事件
			switch (eventType) {	
			case XmlPullParser.START_TAG:
				String name = parser.getName();//获取解析器当前指向的元素的名称
				if("getMobileCodeInfoResult".equals(name)){
					return parser.nextText();
				}
				break;
			}
			eventType = parser.next();
		}
		return null;
	}
}
