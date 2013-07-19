package com.eryansky.study.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * <action android:name="android.intent.action.BOOT_COMPLETED"/>
 * 开机即启动后台服务
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2012-4-19 下午02:11:28
 */
public class BootBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent service = new Intent(context, PhoneListenerService.class);//显式/隐式
		context.startService(service);//Intent激活组件(Service)
	}

}
