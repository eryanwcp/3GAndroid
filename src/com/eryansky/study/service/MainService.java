package com.eryansky.study.service;

import java.io.InputStream;
import java.util.ArrayList;

import com.eryansky.study.activity.LoginActivity;
import com.eryansky.study.BuildConfig;
import com.eryansky.study.R;
import com.eryansky.study.interfaces.IActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
/**
 * 主服务 采用线程的方式处理Activity任务请求 采用Handler刷新Activity窗体界面展示
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2012-4-23 下午09:39:58
 */
public class MainService extends Service implements Runnable {

	private static final String TAG = "MainService";

	public static boolean isrun = false;// 线程
	public static MainService mainService;// 全局静态引用
	public NetListenerReceiver netReceiver = new NetListenerReceiver();
	// 保存所有任务对象
	private static ArrayList<Task> allTask = new ArrayList<Task>();
	// 保存所有 Activity
	public static ArrayList<IActivity> allActivity = new ArrayList<IActivity>();

	public static final String URL_LOGIN = "http://10.0.2.2/eryansky/login1!login.action";

	public MainService() {
		mainService = this;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mainService = this;
		Thread t = new Thread(this);
		t.start();
		// 添加网络状态变化的广播接收器
		this.registerReceiver(netReceiver, new IntentFilter(
				"android.net.conn.CONNECTIVITY_CHANGE"));
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void run() {
		while (isrun) {
			Task lasttask = null;
			if(BuildConfig.DEBUG){
				Log.d(TAG, "Thread is running ......");
			}
			synchronized (allTask) {// 接任务
				if (allTask.size() > 0) {
					lasttask = allTask.get(0);
					doTask(lasttask);// 执行任务
				}
			}
			
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
		}

	}

	/**
	 * 添加任务
	 * @param ts
	 */
	public static void addNewTask(Task ts) {
		allTask.add(ts);
	}
	
	/**
	 * 执行任务
	 * @param task 任务
	 */
	public void doTask(Task task) {
		Message message = hand.obtainMessage();
		try {
			switch (task.getTaskID()) {
			case Task.TASK_USER_LOGIN:// 用户登录
				InputStream inStream = this.getResources().getAssets()
						.open("xml/login.xml");
				String result = "";
				result = HttpManager.sendXML(URL_LOGIN, inStream,
						task.getTaskParam());
				message.obj = result;
				break;
			}
			message.what = task.getTaskID();
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		allTask.remove(task);// 完成任务		
		hand.sendMessage(message);// 发送刷新 UI的消息到主线程
	}

	/**
	 * 刷新UI
	 */
	public Handler hand = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Task.TASK_USER_LOGIN:
				IActivity ia = MainService.getActivityByName("LoginActivity");
				Log.d(TAG, (String) msg.obj);
				ia.refresh(LoginActivity.REF_LOGIN_RESULT, msg.obj);
				break;
			}
		}
	};

	/**
	 * 根据Activity名称获取实现了IActivity接口的Activity
	 * @param name
	 * @return
	 */
	public static IActivity getActivityByName(String name) {
		IActivity ia = null;
		for (IActivity ac : allActivity) {
			if (ac.getClass().getName().indexOf(name) >= 0) {
				ia = ac;
			}
		}
		return ia;
	}
	
	/**
	 * 提示用户网络异常
	 * @param context
	 */
	public static void alertNetError(final Activity context) {
		AlertDialog.Builder ab = new AlertDialog.Builder(context);
		// 设定标题
		ab.setTitle(context.getResources().getString(R.string.net_err_title));
		// 设定提示信息
		ab.setMessage(context.getResources().getString(R.string.net_err_info));
		// 设定退出按钮
		ab.setNegativeButton(context.getResources()
				.getString(R.string.exit_app), new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				exitApp(context);
			}
		});
		// 网络设置按钮
		ab.setPositiveButton(
				context.getResources().getString(R.string.net_setting),
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						context.startActivityForResult(
								new Intent(
										android.provider.Settings.ACTION_WIRELESS_SETTINGS),
								0);
					}
				});
		ab.create().show();
	}

	/**
	 * 退出应用程序(退出之前执行相关操作：销毁Activity、Service等)
	 * @param context
	 */
	public static void exitApp(Activity context) {// 退出所有Activity
		for (int i = 0; i < allActivity.size(); i++) {
			((Activity) allActivity.get(i)).finish();
		}
		allActivity.clear();
		// 退出Service
		context.stopService(new Intent("com.eryansky.study.mainservice"));
		// 关闭子线程
		MainService.isrun = false;
		// 关闭广播接口
		if(MainService.mainService !=null){
			MainService.mainService
			.unregisterReceiver(MainService.mainService.netReceiver);
		}
		

	}

	

	/**
	 * 提示是否退出应用程序
	 * @param context
	 */
	public static void promptExitApp(final Activity context) {
		// 创建对话框
		AlertDialog.Builder ab = new AlertDialog.Builder(context);
		LayoutInflater li = LayoutInflater.from(context);
		View msgview = li.inflate(R.layout.exitdialog, null);
		ab.setView(msgview);// 设定对话框显示的内容
		ab.setPositiveButton(R.string.app_exit_ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
				exitApp(context);// 退出整个应用
			}
		});
		ab.setNegativeButton(R.string.app_exit_cancle, null);
		ab.show();
	}
}
