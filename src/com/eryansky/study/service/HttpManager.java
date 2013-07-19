package com.eryansky.study.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;

import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.util.Xml;

import com.eryansky.study.entity.Contact;
import com.eryansky.study.util.StreamTool;
/**
 * http请求的一些业务方法
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2011-12-28 下午05:29:45
 */
public class HttpManager {
	
	
	private static final String TAG = "HttpManager";

	/*------ 一般方式HttpGet、HttpPost -------*/
	/**
	 * GET请求  HttpGet
	 * @param url 请求地址
	 * @param params 
	 * @return
	 * @throws Exception
	 */
	public static String sendGetRequest(String url,Map<String, String> params) throws Exception{
		//url: http://192.168.2.147:8080/eryansky/servlet/LoginServlet
		//params: ?username=admin&password=admin
		String paramsString = parmsToString(params, "UTF-8");
		HttpGet request = new HttpGet(url+paramsString); //get
        HttpResponse response = new DefaultHttpClient().execute(request);
		String result = null;
		if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
			result = EntityUtils.toString(response.getEntity());
		}
		return result;

	}
	
	/**
	 * POST请求 HttpPost
	 * @param url 请求url地址
	 * @param params 请求参数 post方式body部分
	 * @return 响应结果字符串
	 * @throws java.io.IOException
	 */
	public static String sendPostRequest(String url,List<NameValuePair> params) throws IOException{
		HttpPost request = new HttpPost(url); //post
        request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		HttpResponse response = new DefaultHttpClient().execute(request);
		String result = null;
		if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
			result = EntityUtils.toString(response.getEntity());
		}
		return result;
		/*// 创建一个本地Cookie存储的实例  
        CookieStore cookieStore = new BasicCookieStore();  
        //创建一个本地上下文信息  
        HttpContext localContext = new BasicHttpContext();  
        //在本地上下问中绑定一个本地存储  
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);  
        
        //获取cookie中的各种信息  
        List<Cookie> cookies = cookieStore.getCookies();  
        for (int i = 0; i < cookies.size(); i++) {  
            System.out.println("Local cookie: " + cookies.get(i));  
        }  
        //获取消息头的信息  
        Header[] headers = response.getAllHeaders();  
        for (int i = 0; i<headers.length; i++) {  
            System.out.println(headers[i]);  
        } 
        */
		

	}
	
	
	
	/*------ HttpURLConnection方式 ------*/
	

	/**
	 * 查看某个网页html代码  
	 * @param path 请求地址
	 * @return 网页html代码
	 * @throws Exception
	 */
	public static String getHtml(String path) throws Exception {
		URL url = new URL(path.toString());
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);//连接超时
		InputStream inStream = conn.getInputStream();//通过输入流获取html数据
		byte[] data = StreamTool.readInputStream(inStream);//得到html的二进制数据
		String html = new String(data);
		return html;
	}
	
	/**
	 * 获取web服务器上的图片
	 * @param path 图片地址
	 * @return 图片自字节流
	 * @throws Exception
	 */
	public static byte[] getImage(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);
		InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
		return StreamTool.readInputStream(inStream);//得到图片的二进制数据
	}

	/**
	 * 获取web服务器上的图片
	 * @param url 图片地址
	 * @return
	 */
	public static BitmapDrawable getImageFromURL(URL url) {
		BitmapDrawable bd = null;
		try {
			// 创建连接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 获取数据
			bd = new BitmapDrawable(conn.getInputStream());
			// 关闭连接
			conn.disconnect();
		} catch (Exception e) {
		}
		return bd;
	}
	
	
	/**
	 * 向服务器发送get请求 HttpURLConnection
	 * @param path url地址
	 * @param params 传递参数
	 * @param enc 请求编码（UTF-8）
	 * @return
	 * @throws Exception
	 */
	public static boolean sendGetRequest(String path, Map<String, String> params, String enc) throws Exception{
		StringBuilder sb = new StringBuilder(path);
		sb.append('?');
		// ?method=save&title=435435435&timelength=89&
		for(Map.Entry<String, String> entry : params.entrySet()){
			sb.append(entry.getKey()).append('=')
				.append(URLEncoder.encode(entry.getValue(), enc)).append('&');
		}
		sb.deleteCharAt(sb.length()-1);
		
		URL url = new URL(sb.toString());
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);
		if(conn.getResponseCode()==200){
			return true;
		}
		return false;
	}
	
	/**
	 * 向服务器发送post请求 HttpURLConnection
	 * @param path
	 * @param params
	 * @param enc
	 * @return
	 * @throws Exception
	 */
	public static boolean sendPostRequest(String path, Map<String, String> params, String enc) throws Exception{
		// username=admin&password=admin
		byte[] data = parmsToString(params, enc).getBytes();//得到实体的二进制数据
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5 * 1000);
		conn.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据
		//Content-Type: application/x-www-form-urlencoded
		//Content-Length: 38
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(data);
		outStream.flush();
		outStream.close();
		if(conn.getResponseCode()==200){
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * POST发送xml请求 
	 * @param path url地址
	 * @param inStream 传递实体数据流
	 * @param params 传递参数
	 * @return 请求
	 * @throws Exception
	 */
	public static String sendXML(String path,InputStream inStream,Map<String, String> params)throws Exception{
		String requestXml = XmlUtil.readXmlFile(inStream,params);//组合成xml实体内容  类似socket里面的报文
		
		Log.d(TAG, "request xml:\n" + requestXml);
		byte[] data = requestXml.getBytes();
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5 * 1000);
		conn.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据
		conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		//conn.setRequestProperty("User-Agent","android");//客户端类型（例如：xxx浏览器）
		//conn.setRequestProperty("Cookie","xxx");//客户端Cookie JSESSIONID
		//conn.setRequestProperty("Connection","Keep-Alive");//保持长连接Keep-Alive 不使用长连接close http1.1版本协议默认为长连接
		//conn.setRequestProperty("From","eryanwcp@gmail.com");//请求发送者邮件地址
		OutputStream outStream = conn.getOutputStream();
		outStream.write(data);
		outStream.flush();
		outStream.close();
		String responseXml;
		if(conn.getResponseCode()==200){
			//遍历服务器响应头信息
			/*Map<String, List<String>> header = conn.getHeaderFields();
			Iterator it = header.entrySet().iterator();
			while(it.hasNext()){
				Entry entry = (Entry) it.next();
				Log.i(entry.getKey().toString(), entry.getValue().toString());
			}
			for(int i=0;i<header.size();i++){
				Log.i("Header:", header.keySet().toString());
			}*/
			
			/*//获取服务器返回的cookie文本 如果服务器未返回则为null
			String cookies = conn.getHeaderField("Set-Cookie");
			String sessionid;
			if(cookies!=null){
				sessionid = cookies.substring(0, cookies.indexOf(";"));
				Log.i("cookie:", sessionid);
			}*/
			
			responseXml = new String(StreamTool.readInputStream(conn.getInputStream()),"UTF-8");//响应xml数据
			/*Log.d(TAG, "response xml:\n" + responseXml);
			Map<String, String> map = parseXml(responseXml);
			result = map.get("result");
			if("OK".equals(result)){
				User user = new User(map.get("username"), map.get("password"));//登录成功后即可保存登录状态在客户端
			}*/
			return responseXml;
		}
		return null;
	}
	
	
	/**
	 * POST发送xml/json请求 
	 * @param path 请求路径
	 * @param requestStr 请求实体内容（字符串）
	 * @param enc 请求编码（UTF-8）
	 * @return 响应实体内容（字符串）
	 * @throws Exception
	 */
	public static String sendStrRequest(String path,String requestStr,String enc)throws Exception{
		byte[] data = requestStr.getBytes();
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5 * 1000);
		conn.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据
		conn.setRequestProperty("Content-Type", "text/xml; charset="+enc);
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(data);
		outStream.flush();
		outStream.close();
		String responseStr;
		if(conn.getResponseCode()==200){
			responseStr = new String(StreamTool.readInputStream(conn.getInputStream()),"UTF-8");//响应xml/json数据
			return responseStr;
		}
		return null;
	}
	
	/*------- apache HttpCilent方式 android jar集成了apache HttpCilent项目 -------*/
	//SSL HTTPS Cookie
	public static boolean sendRequestFromHttpClient(String path, Map<String, String> params, String enc) throws Exception{
		List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();
		if(params!=null && !params.isEmpty()){
			for(Map.Entry<String, String> entry : params.entrySet()){
				paramPairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		UrlEncodedFormEntity entitydata = new UrlEncodedFormEntity(paramPairs, enc);//得到经过编码过后的实体数据
		HttpPost post = new HttpPost(path); //form
		post.setEntity(entitydata);
		DefaultHttpClient client = new DefaultHttpClient(); //浏览器
		HttpResponse response = client.execute(post);//执行请求
		if(response.getStatusLine().getStatusCode()==200){
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * GET请求参数组装 
	 * @param params 请求参数
	 * @param enc 编码
	 * @return
	 * @throws Exception
	 */
	private static String parmsToString(Map<String, String> params,String enc) throws Exception{
		StringBuilder sb = new StringBuilder();
		if(params!=null && !params.isEmpty()){
			sb.append("?");// + ?
			for(Map.Entry<String, String> entry : params.entrySet()){
				sb.append(entry.getKey()).append('=')
					.append(URLEncoder.encode(entry.getValue(), enc)).append('&');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	
	
	/**
	 * 服务器端公共返回xml数据解析 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	private static Map<String, String> parseXml(String xml) throws Exception{
		InputStream inStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(inStream, "UTF-8");
		Map<String, String> map = new HashMap<String, String>();
		int eventType = parser.getEventType();//产生第一个事件
		while(eventType!=XmlPullParser.END_DOCUMENT){//只要不是文档结束事件
			switch (eventType) {	
			case XmlPullParser.START_TAG:
				String name = parser.getName();//获取解析器当前指向的元素的名称
				if("username".equals(name)){
					map.put("username", parser.nextText());
				}
				if("password".equals(name)){
					map.put("password", parser.nextText());
				}
				if("result".equals(name)){
					map.put("result", parser.nextText());
				}
				break;
			}
			eventType = parser.next();
			
		}
		return map;
	}
	
	
	/**
	 * @Description: 查询联系人  （数据源来自webserver ）此处采用模拟数据
	 * @return
	 */
	public static List<Contact> getContacts(){
		//从服务器获取数据（此处模拟）
		List<Contact> contacts = new ArrayList<Contact>();
		contacts.add(new Contact(01, "温春平", "15879034026"));
		contacts.add(new Contact(02, "中国移动", "10086"));
		contacts.add(new Contact(03, "查话费", "1008613"));
		return contacts;
	}

}
