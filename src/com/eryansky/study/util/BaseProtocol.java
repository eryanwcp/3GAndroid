package com.eryansky.study.util;
import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.util.ArrayList;  
import java.util.List;  
  
import org.apache.http.HttpResponse;  
import org.apache.http.NameValuePair;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.message.BasicNameValuePair;  
import org.json.JSONException;  
import org.json.JSONObject;  
/**
 * HTTP POST请求返回json数据
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2012-3-16 下午01:43:45
 */
public class BaseProtocol {  
    private StringBuilder sb = new StringBuilder();  
  
    private HttpClient httpClient;  
    private HttpPost httpRequest;  
    private HttpResponse response;  
  
    private List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();  
  
    BaseProtocol() {  
        httpClient = new DefaultHttpClient();  
    }  
  
    /** 
     * 向服务器端发送请求 
     *  
     * @param url 
     * @throws Exception 
     */  
    protected void pack(String url) throws Exception {  
        httpClient = new DefaultHttpClient();  
        httpRequest = new HttpPost(url);  
  
        httpRequest.setEntity(new UrlEncodedFormEntity(nameValuePair));  
        response = httpClient.execute(httpRequest);  
    }  
  
    /** 
     * 得到返回数据 
     *  
     * @param url 
     * @return 
     * @throws Exception 
     */  
    protected void parse() throws Exception {  
        // TODO 状态处理 500 200  
        if (response.getStatusLine().getStatusCode() == 200) {  
  
            BufferedReader bufferedReader2 = new BufferedReader(  
                    new InputStreamReader(response.getEntity().getContent()));  
            for (String s = bufferedReader2.readLine(); s != null; s = bufferedReader2  
                    .readLine()) {  
                sb.append(s);  
            }  
        }  
    }  
  
    /** 
     * 向服务器发送信息 
     *  
     * @param key 
     * @param value 
     */  
    public void addNameValuePair(String key, String value) {  
        nameValuePair.add(new BasicNameValuePair(key, value));  
    }  
  
    /** 
     * 返回JSONObject对象数据模型 
     *  
     * @return 
     * @throws org.json.JSONException
     */  
    public JSONObject getJSON() throws JSONException {  
        return new JSONObject(sb.toString());  
    }  
  
} 