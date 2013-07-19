package com.eryansky.study.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import com.eryansky.study.R;
import com.eryansky.study.entity.Contact;
import com.eryansky.study.service.HttpManager;
import com.eryansky.study.service.MainService;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.webkit.WebView;
/**
 * android中使用html页面布局  并且Java与Javascript交换数据（方法互调）
 * （注：2.3.3模拟器存在bug）
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2011-12-28 下午05:28:23
 */
public class UiHtmlActivity extends Activity{
	
	private static final String TAG = "UiHtmlActivity";
    private WebView wv_html;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_html);
		
		wv_html = (WebView) this.findViewById(R.id.ui_webview);
		wv_html.getSettings().setJavaScriptEnabled(true);
		wv_html.addJavascriptInterface(new ContactPlugin(), "contact");//contact为javascript与java访问接口名称
		wv_html.loadUrl("file:///android_asset/index.html");//加载文件url地址 可为本地或则网络
		//wv_html.loadUrl("http://192.168.2.147/essh/index.html");//网络
		//wv_html.loadUrl("http://www.baidu.com");
		
		
		
		/*try {
			File file = new File("file:///android_asset/index.html");
			InputStream inStream = new FileInputStream(file);
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(inStream, "UTF-8");
			int eventType = parser.getEventType();//产生第一个事件
			while(eventType!=XmlPullParser.END_DOCUMENT){//只要不是文档结束事件
				switch (eventType) {	
				case XmlPullParser.START_TAG:
					String name = parser.getName();//获取解析器当前指向的元素的名称
					if("body".equals(name)){
						Log.d(TAG, parser.nextText());
					}
					break;
				}
				eventType = parser.next();
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
	}

	private final class ContactPlugin {
		public void getContacts(){
    		List<Contact> contacts = HttpManager.getContacts();
    		try {
				JSONArray array = new JSONArray();
				for(Contact contact : contacts){
					JSONObject item = new JSONObject();
					item.put("id", contact.getId());
					item.put("name", contact.getName());
					item.put("mobile", contact.getMobile());
					array.put(item);
				}
				String json = array.toString();
				Log.i(TAG, json);
				wv_html.loadUrl("javascript:show('"+ json +"')");//调用javascript方法show()
			} catch (JSONException e) {
				e.printStackTrace();
			}
    	}
    	
    	public void call(String mobile){
    		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:"+ mobile));
    		startActivity(intent);
    	}
	}
}
