package com.eryansky.study.activity;

import com.eryansky.study.BuildConfig;
import com.eryansky.study.R;
import com.eryansky.study.interfaces.IActivity;
import com.eryansky.study.service.MainService;
import com.eryansky.study.service.NetListenerReceiver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

/**
 * 程序主入口Activity 
 * 根据需要可以启用不同的注释 查看相关示例
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2011-12-22 上午11:23:35
 */
public class MainActivity extends Activity implements IActivity{

	private static final String TAG = "MainActivity";

	@Override
	public void init() {
		//检查网络
		if(NetListenerReceiver.checkNet(this)){//联网正常
			if(!MainService.isrun){  
				Intent it=new Intent(this,MainService.class);
			    this.startService(it);
			    MainService.isrun=true; 
			}
			if(BuildConfig.DEBUG){
				Log.d(TAG, "Network.............ok");
			}
		}else{ //提示用户网络异常 
			Log.d(TAG, "Network.............error");
			MainService.alertNetError(this);
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		init();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainService.allActivity.add(this);//将Activity交给MainService管理
		setContentView(R.layout.main);

		// 针对不同示例 可以启动相应Activity查看示例

		// 启动另外的一个Activity

		// 1.电话拨号器
//		 Intent intent = new Intent(MainActivity.this, PhoneActivity.class);

		// 2.短信发送器
//		 Intent intent = new Intent(MainActivity.this, SmsActivity.class);

		// 3.登录演示（网络通信方式相关演示）
//		 Intent intent = new Intent(MainActivity.this, LoginActivity.class);

		// 4.html布局ui （使用html javascript等开发客服端界面）
//		 Intent intent = new Intent(MainActivity.this, UiHtmlActivity.class);

		// 5.手机号码归属地查询
		 Intent intent = new Intent(MainActivity.this,MobileCodeActivity.class);

		// 6.将联系人数量显示在icon图标上
		// Intent intent = new
		// Intent(MainActivity.this,NotificationIconActivity.class);

		// 7.发送邮件
		// Intent意图 立即执行的意图
//		 Intent intent = new Intent(MainActivity.this,SendMailActivity.class);

		//
		// 8.从网络获取JSON数据
//		 Intent intent = new Intent(MainActivity.this, JsonActivity.class);

		// 9.当前地理位置信息 根据地理位置查看地图
//		Intent intent = new Intent(MainActivity.this,CurrentLocationActivity.class);
		 
		 
		//10.SQLite数据库操作  ContentProvider相关
//		Intent intent = new Intent(MainActivity.this,PersonActivity.class);
		 
		 //11.摄像头使用
//		 Intent intent = new Intent(MainActivity.this,TakePictureActivity.class);
		

		this.startActivity(intent);// 启动意图intent
		//finish();// 销毁this Activity
		

	}

	//再主界面按下回退键时提示是否退出程序
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			MainService.promptExitApp(this);
		}
		return super.onKeyDown(keyCode, event);
	}

	

	@Override
	public void refresh(Object... param) {
		
	}
}