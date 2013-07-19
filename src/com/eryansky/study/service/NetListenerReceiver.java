package com.eryansky.study.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetListenerReceiver extends BroadcastReceiver {
	// 侦听网络状态的变化
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			// 判断当前网络是否已经连接
			// 判断当前网络是否已经连接
			//Toast.makeText(context, info.getType()+":"+info.getTypeName(), Toast.LENGTH_SHORT).show();
			if (info.getState() == NetworkInfo.State.CONNECTED) {
				Toast.makeText(context, "Network is connected！", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, "Network is disconnected！", Toast.LENGTH_LONG).show();
			}

		} else {
			Toast.makeText(context, "Network is disconnected！", Toast.LENGTH_LONG).show();
		}
	}

	public static boolean checkNet(Context context) {// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

	

}
