package com.eryansky.study.activity;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.eryansky.study.R;
/**
 * 获取当前位置所坐标 纬度、经度 并且根据坐标获取所在城市
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2011-12-28 上午09:29:42
 */
public class CurrentLocationActivity extends Activity { 
	
	
	private static final String TAG = "CurrentLocationActivity";
	private double lat,lon;//纬度 经度
	
	private Button bt_viewmap;//查看地图按钮
	
    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.currentlocation);  
        
        bt_viewmap = (Button) this.findViewById(R.id.currentlocation_viewmap);
        bt_viewmap.setOnClickListener(searchListener);
        
        LocationManager locationManager;  
        String serviceName = Context.LOCATION_SERVICE;  
        locationManager = (LocationManager)getSystemService(serviceName);  
//        String provider = LocationManager.GPS_PROVIDER;  
        String provider = LocationManager.NETWORK_PROVIDER;  
          
        Criteria criteria = new Criteria();  
        criteria.setAccuracy(Criteria.ACCURACY_FINE);  
        criteria.setAltitudeRequired(false);  
        criteria.setBearingRequired(false);  
        criteria.setCostAllowed(true);  
        criteria.setPowerRequirement(Criteria.POWER_LOW);  
//        String provider = locationManager.getBestProvider(criteria, true);  
          
        Location location = locationManager.getLastKnownLocation(provider);  
        updateWithNewLocation(location);  
        locationManager.requestLocationUpdates(provider, 2000, 10,locationListener);  
    }  
    
    /**
     * 位置变化监听
     */
	private final LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			updateWithNewLocation(location);//坐标位置发生变化时更新坐标信息
		}
		public void onProviderDisabled(String provider) {
			updateWithNewLocation(null);
		}
		public void onProviderEnabled(String provider) {}

		public void onStatusChanged(String provider, int status, Bundle extras) {}
	}; 
    
    /**
     * 更新显示地理坐标信息
     * @param location
     */
    private void updateWithNewLocation(Location location) {  
        String latLongString;  
        TextView myLocationText;  
        myLocationText = (TextView)findViewById(R.id.currentlocation_location);  
        if (location != null) {  
        lat = location.getLatitude();  
        lon = location.getLongitude();  
        latLongString = "纬度:" + lat + "\n经度:" + lon;  
        } else {  
        latLongString = "无法获取地理坐标信息！";  
        }  
        List<Address> addList = null;  
        Geocoder ge = new Geocoder(this);  
        try {  
            addList = ge.getFromLocation(lat, lon, 1);  //该方法有时候会获取不到城市信息 可以切换先手机网络（例如关/开GPRS）
        } catch (IOException e) {  
            e.printStackTrace();
        }  
        if(addList!=null && addList.size()>0){  
            for(int i=0; i<addList.size(); i++){  
                Address ad = addList.get(i);  
                Log.i(TAG, ad.toString());
                latLongString += "\n";  
                latLongString += ad.getAddressLine(0) + " " + ad.getAddressLine(1) + " " +  ad.getAddressLine(2) + " " + ad.getAddressLine(3);  
            }  
        } else{
        	latLongString +="\n无法获取所地理位置信息！";
        }
        myLocationText.setText("您当前的位置是:\n" + latLongString);  
    }  
    
    /**
     * 查看地图按钮事件响应
     */
    OnClickListener searchListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//根据坐标查看地图 geo:latitude,longitude
			Uri uri = Uri.parse("geo:" + lat + ","+ lon);
			Intent intent = new Intent(Intent.ACTION_VIEW,uri);
			startActivity(intent);
		}
	};
}  