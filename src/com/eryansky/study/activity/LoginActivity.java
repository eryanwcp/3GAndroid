package com.eryansky.study.activity;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


import com.eryansky.study.R;
import com.eryansky.study.interfaces.IActivity;
import com.eryansky.study.service.HttpManager;
import com.eryansky.study.service.MainService;
import com.eryansky.study.service.MinaCilentFactory;
import com.eryansky.study.service.NetListenerReceiver;
import com.eryansky.study.service.Task;
import com.eryansky.study.service.XmlUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 用户登录演示（网络访问的几种方式）
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2011-12-22 上午11:25:28
 */
public class LoginActivity extends Activity implements IActivity{

	private static final String TAG = "LoginActivity";
	public static final int REF_LOGIN_RESULT = 1;
	private EditText et_username,et_password;//输入框 用户名 密码
	private Button bt_ok;//登录按钮
	
	private TextView tv_html;
	private ImageView icon_img;//图片
	
	private String username,password;//用户名，密码
	
	public ProgressDialog pd;
	
	
	//private MinaCilentFactory minaCilentFactory = new MinaCilentFactory();
	
	@Override
	public void init() {		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainService.allActivity.add(this);
		setContentView(R.layout.login);
		
		et_username = (EditText) this.findViewById(R.id.login_username);
		et_password = (EditText) this.findViewById(R.id.login_password);
		
		tv_html = (TextView) this.findViewById(R.id.login_text);
		icon_img = (ImageView) this.findViewById(R.id.login_img);
		
		
		bt_ok = (Button) this.findViewById(R.id.login_ok);
		bt_ok.setOnClickListener(new ButtonListener());//内部类实现事件监听
		
		//et_username.setOnFocusChangeListener(onFocusListener);//匿名内部类
		//et_username.addTextChangedListener(watcher);
		
		//1.查看网页html代码模拟器访问电脑本机ip为10.0.2.2
//        String path = "http://10.0.2.2/essh/";
//        String html;
//        try {
//			html = HttpManager.getHtml(path);
//			//tv_html.setText(Html.fromHtml(html));
//			tv_html.setText(html);
//			Log.d(TAG, "Html:\n" + html);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		
			
		//2.查看图片资源 
//        String path = "http://10.0.2.2/essh/mt.jpg";
//        byte[] data;
//		try {
//			data = HttpManager.getImage(path);
//			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);//生成位图
//			icon_img.setImageBitmap(bitmap);//显示图片
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
	}
	/**
	 * 该对象（View v）获取焦点时触发
	 */
	OnFocusChangeListener onFocusListener = new OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			int id = v.getId();
			switch (id) {
			case R.id.login_username:
				if(!hasFocus){
					username = et_username.getText().toString();//用户名
					Toast.makeText(LoginActivity.this, "username:" + username, Toast.LENGTH_SHORT).show();
				}
				break;

			default:
				break;
			}
			
			/*if(!hasFocus){
				username = et_username.getText().toString();//用户名
				Toast.makeText(LoginActivity.this, "username:" + username, Toast.LENGTH_SHORT).show();
			}*/
			
			
		}
	};
	/**
	 * EditText输入值时触发
	 */
	TextWatcher watcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			username = et_username.getText().toString();//用户名
			Toast.makeText(LoginActivity.this, "after:" + username, Toast.LENGTH_SHORT).show();
		}
	};
	OnFocusChangeListener listener = new OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			username = et_username.getText().toString();//用户名
			Toast.makeText(LoginActivity.this, username, Toast.LENGTH_SHORT).show();
			
		}
	};
	

	/**
	 * 登录校验 （客户端输入校验）
	 * @return boolean 校验是否通过：true通过 false不通过
	 */
	private boolean validate(){
		username = et_username.getText().toString();//用户名
		password = et_password.getText().toString();//密码
		if("".equals(username)){
			Toast.makeText(LoginActivity.this, R.string.login_username_isempty, Toast.LENGTH_SHORT).show();
			return false;
		}
		if("".equals(password)){
			Toast.makeText(LoginActivity.this, R.string.login_password_isempty, Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
		
	}

	@Override
	public void refresh(Object... param) {
		pd.dismiss();
		Toast.makeText(this, (String)param[1], Toast.LENGTH_LONG).show();
	}
	/**
	 * 内部类实现事件监听
	 * @author 尔演&Eryan eryanwcp@163.com
	 * @date 2012-4-3 下午05:09:10
	 */
	private final class ButtonListener implements View.OnClickListener{
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.login_ok:
				if(validate()){
					//网络访问的几种方式  用户登录
					
			        //1.HTTP GET http://10.0.2.2/essh/servlet/LoginServlet?username=admin&password=admin
//			        String result;
//			        String url = "http://10.0.2.2/essh/servlet/LoginServlet";
//					try {
//						Map<String, String> params = new HashMap<String, String>();
//						params.put("loginName", username);
//						params.put("password", password);
//						result = HttpManager.sendGetRequest(url,params);
//						Log.d(TAG, result);
//						if("1".equals(result)){
//							Log.i(TAG, "Login success");
//							Toast.makeText(LoginActivity.this, result, Toast.LENGTH_LONG).show();
//						}else{
//							Toast.makeText(LoginActivity.this, result, Toast.LENGTH_LONG).show();
//							Log.i(TAG, "Login fail");
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
			        
			        //2.HTTP POST
			       /* String result;
			        String url = "http://10.0.2.2/essh/servlet/LoginServlet";
					try {
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("username", username));
						params.add(new BasicNameValuePair("password", password));
						result = HttpService.sendPostRequest(url,params);
						Log.d(TAG, result);
						if("1".equals(result)){
							Log.i(TAG, "Login success");
							Toast.makeText(LoginActivity.this, result, Toast.LENGTH_LONG).show();
						}else{
							Toast.makeText(LoginActivity.this, result, Toast.LENGTH_LONG).show();
							Log.i(TAG, "Login fail");
						}
					} catch (IOException e) {
						e.printStackTrace();
					}*/
			        
			        //3.HTTP POST XML
					/*Map<String, String> params = new HashMap<String, String>();
					params.put("username", username);
					params.put("password",password);					
					String path = "http://10.0.2.2/essh/login1!login.action";
					String result;
					try {
						InputStream inStream = getResources().getAssets().open("xml/login.xml");
						//定义登录验证任务 
				        Task loginTask=new Task(Task.TASK_USER_LOGIN,params);
				        MainService.addNewTask(loginTask);
				    	//显示进度条
				        pd=new ProgressDialog(LoginActivity.this);
				        pd.setMessage(LoginActivity.this.getResources().getString(R.string.app_name));
				        pd.setTitle(LoginActivity.this.getResources().getString(R.string.app_name));
						pd.show();
//						result = HttpService.sendXML(path, inStream, params);
//						Log.d(TAG, "HTTP responseXml:" + result);
//						Toast.makeText(LoginActivity.this, result, Toast.LENGTH_LONG).show();
					} catch (Exception e) {
						e.printStackTrace();
					}*/
					
					
					//4.Socket Mina
					
					Map<String, String> params = new HashMap<String, String>();
					params.put("username", username);
					params.put("password",password);
					try {
						InputStream inStream = getResources().getAssets().open("xml/login.xml");
						String requestxml = XmlUtil.readXmlFile(inStream, params);//将请求转换成xml字符串（String类型）
						String responseXml = MinaCilentFactory.getSoctetRequest(requestxml);
						Log.d(TAG, "Socket responseXml:" + responseXml);
						Toast.makeText(LoginActivity.this, responseXml, Toast.LENGTH_LONG).show();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				break;

			default:
				break;
			}
		}
    }

	

}
