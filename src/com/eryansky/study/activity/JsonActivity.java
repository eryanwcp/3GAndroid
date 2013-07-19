package com.eryansky.study.activity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.eryansky.study.R;
import com.eryansky.study.entity.City;
import com.eryansky.study.service.HttpManager;
/**
 * 从网络获取JSON数据并解析 
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2011-12-28 上午11:11:22
 */
public class JsonActivity extends Activity {
	
	private static final String TAG = "JsonActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		String path = "http://www.google.com/ig/cities?country=cn";
		try {
			//{code: "cn",cities: [{name: "Baoding", lat: 38849998, lon: 115569999},{name: "Beijing", lat: 39930000, lon: 116279998, selected: true},{name: "Changchun", lat: 43900001, lon: 125220001},{name: "Changsha", lat: 28229999, lon: 112870002},{name: "Chengdu", lat: 30670000, lon: 104019996},{name: "Chongqing", lat: 29520000, lon: 106480003},{name: "Dalian", lat: 38900001, lon: 121629997},{name: "Fuyang", lat: 32930000, lon: 115830001},{name: "Fuzhou", lat: 26079999, lon: 119279998},{name: "Ganzhou", lat: 25850000, lon: 114949997},{name: "Guangzhou", lat: 23129999, lon: 113319999},{name: "Guiyang", lat: 26579999, lon: 106720001},{name: "Haikou", lat: 20030000, lon: 110349998},{name: "Handan", lat: 38029998, lon: 114419998},{name: "Hangzhou", lat: 30229999, lon: 120169998},{name: "Harbin", lat: 45750000, lon: 126769996},{name: "Hefei", lat: 31870000, lon: 117230003},{name: "Hengyang", lat: 36119998, lon: 114370002},{name: "Heze", lat: 36119998, lon: 114370002},{name: "Hohhot", lat: 40819999, lon: 111680000},{name: "Huanggang", lat: 30620000, lon: 114129997},{name: "Jinan", lat: 36680000, lon: 116980003},{name: "Jining", lat: 36680000, lon: 116980003},{name: "Kunming", lat: 25020000, lon: 102680000},{name: "Lanzhou", lat: 36040000, lon: 103879997},{name: "Lhasa", lat: 29657589, lon: 91132050},{name: "Nanchang", lat: 28600000, lon: 115919998},{name: "Nanchong", lat: 30799999, lon: 106080001},{name: "Nanjing", lat: 32000000, lon: 118800003},{name: "Nanning", lat: 22819999, lon: 108349998},{name: "Nantong", lat: 32000000, lon: 118800003},{name: "Nanyang", lat: 33029998, lon: 112580001},{name: "Ningbo", lat: 30229999, lon: 120169998},{name: "Qingdao", lat: 36069999, lon: 120330001},{name: "Shanghai", lat: 31399999, lon: 121470001},{name: "Shangqiu", lat: 36119998, lon: 114370002},{name: "Shantou", lat: 23399999, lon: 116680000},{name: "Shaoyang", lat: 27229999, lon: 111470001},{name: "Shenyang", lat: 41770000, lon: 123430000},{name: "Shenzhen", lat: 22549999, lon: 114099998},{name: "Shijiazhuang", lat: 38029998, lon: 114419998},{name: "Taiyuan", lat: 37779998, lon: 112550003},{name: "Tangshan", lat: 39669998, lon: 118150001},{name: "Tianjin", lat: 39099998, lon: 117169998},{name: "Urumqi", lat: 43779998, lon: 87620002},{name: "Weifang", lat: 36700000, lon: 119080001},{name: "Wenzhou", lat: 30229999, lon: 120169998},{name: "Wuhan", lat: 30620000, lon: 114129997},{name: "Xi An", lat: 34299999, lon: 108930000},{name: "Xiamen", lat: 24479999, lon: 118080001},{name: "Xining", lat: 36619998, lon: 101769996},{name: "Xinyang", lat: 32130001, lon: 114050003},{name: "Xuzhou", lat: 34279998, lon: 117150001},{name: "Yancheng", lat: 34279998, lon: 117150001},{name: "Yibin", lat: 28799999, lon: 104599998},{name: "Yinchuan", lat: 38479999, lon: 106220001},{name: "Zhengzhou", lat: 34720001, lon: 113650001},{name: "Zhoukou", lat: 33000000, lon: 114019996},{name: "Zhumadian", lat: 33000000, lon: 114019996}]}
			String json = HttpManager.getHtml(path);
			Log.i(TAG, json);
			JSONTokener jsonParser = new JSONTokener(json);
			// 读取JSONObject对象。
			JSONObject jo = (JSONObject) jsonParser.nextValue();
			String code = jo.getString("code");
			Log.i(TAG, "code:" + code);

			JSONArray cities = jo.getJSONArray("cities");
			for (int i = 0; i < cities.length(); i++) {
				JSONObject item = cities.getJSONObject(i);
				City c = new City(item.getString("name"), item.getInt("lat"),
						item.getInt("lon"));
				if(i==0){
					Toast.makeText(JsonActivity.this, c.toString(), Toast.LENGTH_SHORT).show();
				}
				
				Log.i(TAG, c.toString());
			}
			Log.i(TAG, "cities length:" + cities.length());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//方式二
		/*JSONRPCClient jsonCilent = JSONRPCClient.create(path);
		jsonCilent.setConnectionTimeout(5*1000);
		jsonCilent.setSoTimeout(2*1000);
		
		try {
			String json = jsonCilent.callString("", "");
			Log.d(TAG, json);
		} catch (JSONRPCException e) {
			e.printStackTrace();
		}*/

		
		
	}
	
	
}
