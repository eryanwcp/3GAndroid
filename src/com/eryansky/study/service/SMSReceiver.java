package com.eryansky.study.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * 短信拦截器
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2012-4-18 下午04:07:24
 */
public class SMSReceiver extends BroadcastReceiver {
	
	private static final String TAG = "SMSReceiver";
	//private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public void onReceive(Context context, Intent intent) {
		//if (intent.getAction().equals(SMS_RECEIVED)) {
			SmsManager sms = SmsManager.getDefault();
		    String content = "";
		    String inPhoneNo = "";
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				Object[] pdus = (Object[]) bundle.get("pdus");
				SmsMessage[] messages = new SmsMessage[pdus.length];
				for (int i = 0; i < pdus.length; i++)
					messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				for (SmsMessage message : messages) {
					content = message.getMessageBody();
					inPhoneNo = message.getOriginatingAddress();
					//sms.sendTextMessage(inPhoneNo, null, content, null, null);回复接收到的信息
					Toast.makeText(context, "手机号:" + inPhoneNo + " 短信内容:\n" + content, Toast.LENGTH_LONG).show();
					//abortBroadcast();//拦截短信 不广播（这样短信就不会显示在短信管理器里面）
				}
			}
		}
	//}
}