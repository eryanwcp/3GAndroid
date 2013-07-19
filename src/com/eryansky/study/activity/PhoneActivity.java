package com.eryansky.study.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.eryansky.study.R;
/**
 * 拨打电话 
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2011-11-15 下午02:23:51 
 */
public class PhoneActivity extends Activity{
	
	private EditText et_phoneNumber;
	private Button bt_call;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone);
		
		et_phoneNumber = (EditText) this.findViewById(R.id.phone_et_phoneNumber);
		
		bt_call = (Button) this.findViewById(R.id.phone_bt_call);
		bt_call.setOnClickListener(callListener);
		
	}
	
	/*
	 * 拨打按钮监听事件处理
	 */
	OnClickListener callListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String phoneNumber = et_phoneNumber.getText().toString();//输入的手机号
			
			//校验手机号 省略...
			
			//Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + phoneNumber));
			Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("tel:" + phoneNumber));
			startActivity(intent);
			
		}
	};
}
