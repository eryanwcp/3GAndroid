package com.eryansky.test;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.test.AndroidTestCase;
import android.util.Log;
/**
 * 使用Spring android Rest
 * @author 尔演&wencp eryanwcp@gmail.com
 * @date   2012-7-9 下午2:06:43
 *
 */
public class RESTTest extends AndroidTestCase{
	
	private static final String TAG = "RESTTest";

	public void testClient(){
		String url = "https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q={query}";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		String result = restTemplate.getForObject(url, String.class, "SpringSource");
		Log.i(TAG, result);
	}
	
}
