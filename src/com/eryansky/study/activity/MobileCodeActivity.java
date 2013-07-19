package com.eryansky.study.activity;

import java.io.IOException;
import java.io.InputStream;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eryansky.study.R;
import com.eryansky.study.service.MobileInfoMangager;

/**
 * 根据公网提供的weservice查询手机号归属地 注意：测试该服务在免费条件下：grps环境下测试 cmnet下无法查询
 * cmwap正常，wifi下测试正常
 * 
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2011-12-6 下午05:03:40
 */
public class MobileCodeActivity extends Activity {

	private static final String TAG = "MobileCodeActivity";

	private static final String NS = "http://WebXml.com.cn/"; // 命名空间
	private static final String METHOD_GETMOBILECODE = "getMobileCodeInfo"; // 请求
																			// 方法
	private static final String WEBSERVICE_URL = "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx";// 请求URL

	private ProgressDialog pd;
	private EditText phoneNumber_et;
	private TextView address_tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mobilecode);

		phoneNumber_et = (EditText) this.findViewById(R.id.mobile_phoneNumber);
		address_tv = (TextView) this.findViewById(R.id.mobile_address);
		Button query_bt = (Button) this.findViewById(R.id.mobile_query);
		query_bt.setOnClickListener(queryListener);
	}

	// 查询按钮事件响应处理
	OnClickListener queryListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			pd = ProgressDialog.show(MobileCodeActivity.this, "提示信息！",
					"正在查询，请稍候...");
			String mobileNumber = phoneNumber_et.getText().toString();// 输入的手机号
			// InputStream inStream =
			// this.getClass().getClassLoader().getResourceAsStream("assets/xml/mobilecode.xml");

			try {
				// 方式一 http方式
				 InputStream inStream = getResources().getAssets().open("xml/mobilecode.xml");
				 address_tv.setText(MobileInfoMangager.getMobileAddress(inStream,mobileNumber));//自定义方式
				// 方式二 采用ksoap2-android获取
//				address_tv.setText(getMobileCode(mobileNumber));
			} catch (Exception e) {
				Log.e(TAG, e.toString());
				Toast.makeText(MobileCodeActivity.this, "查询失败！",
						Toast.LENGTH_SHORT).show();
			} finally {
				pd.dismiss();
			}
		}
	};

	/**
	 * 采用ksoap2-android作为webservice客户端
	 * 
	 * @return
	 * @throws java.io.IOException
	 * @throws org.xmlpull.v1.XmlPullParserException
	 */
	private String getMobileCode(String mobileNumber) throws IOException,
			XmlPullParserException {
		// 实例化SoapObject对象,指定webService的命名空间以及调用方法的名称
		SoapObject req = new SoapObject(NS, METHOD_GETMOBILECODE);
		// getMobile方法中有一个String的参数，这里将mobileNumber传递到getMobile中
		req.addProperty("mobileCode", mobileNumber);
		// req.addProperty("userID","");//测试用户可不设置
		// 获得序列化的Envelope
		SoapSerializationEnvelope envelope;
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = req;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(req);
		// Android传输对象
		HttpTransportSE ht = new HttpTransportSE(WEBSERVICE_URL);
		ht.debug = true;
		// 调用WebService
		ht.call(NS + METHOD_GETMOBILECODE, envelope);
		if (envelope.getResponse() != null) {
			SoapObject res = (SoapObject) envelope.bodyIn;
			return res.getProperty("getMobileCodeInfoResult").toString();
			// return envelope.bodyIn.toString();
		}
		return null;
	}

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			finish();
//			Toast.makeText(MobileCodeActivity.this, "系统退出...",
//					Toast.LENGTH_SHORT).show();
//		}
//		return super.onKeyDown(keyCode, event);
//	}

}