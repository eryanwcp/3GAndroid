package com.eryansky.study.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eryansky.study.R;
/**
 * 短信发送
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2011-11-15 上午11:18:47
 */
public class SmsActivity extends Activity {
	
	private static final String TAG = "SmsActivity";
	
	private EditText et_phoneNumber,et_message;
	private Button bt_send;
	
	private String phoneNumber,message;//手机号，短信内容
	
	private AlertDialog dialog;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms);
		Log.d(TAG, "yyyyyyyy");
		System.out.println(11);
		et_phoneNumber = (EditText) this.findViewById(R.id.et_phoneNumber);
		et_message = (EditText) this.findViewById(R.id.et_message);
		
		bt_send = (Button) this.findViewById(R.id.bt_send);
		bt_send.setOnClickListener(sendListener);
	}

	/*
	 * 短信发送按钮监听
	 */
	OnClickListener sendListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			phoneNumber = et_phoneNumber.getText().toString().trim(); //手机号
			message = et_message.getText().toString();//短信内容
			
			//校验输入
			if(vaildate()){
				SmsManager smsManager = SmsManager.getDefault();
				ArrayList<String> texts = smsManager.divideMessage(message);//拆分短信
				for(String text : texts){
					smsManager.sendTextMessage(phoneNumber, null, text, null, null);
				}
				Toast.makeText(SmsActivity.this, R.string.sms_send_success, Toast.LENGTH_LONG).show();
			}
		}
		
	};
	
	/*
	 * 校验输入内容
	 */
	private boolean vaildate(){
		if("".equals(phoneNumber)){
			//以Toast方式提醒用户
			Toast.makeText(SmsActivity.this, R.string.sms_phoneNumber_isempty, Toast.LENGTH_LONG).show();
			
			//以弹出对话框的方式提示信息
//			showDialog(getResources().getString(R.string.sms_phoneNumber_isempty));
			
			return false;
		}
		
		return true;
	}
	
	/*
	 * 弹出对话框
	 */
	private void showDialog(String msg){
		Builder builder = new Builder(this);
		builder.setTitle(R.string.sms_dialog_title)
		       .setIcon(R.drawable.ic_launcher)
		       .setMessage(msg)
		       .setNegativeButton(R.string.ok, null);
	 	dialog = builder.create();
	 	dialog.show();
		
	}
}
