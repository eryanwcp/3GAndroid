package com.eryansky.test;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.eryansky.study.entity.Contact;
import com.eryansky.study.service.HttpManager;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * 单元测试（联系人）
 * @ClassName: ContactTest 
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2011-12-15 下午03:50:52
 */
public class ContactTest extends AndroidTestCase {

    private static final String TAG = "ContactTest";
	
	public void testgetContacts() throws Throwable{
		List<Contact> contacts = HttpManager.getContacts();
		JSONArray array = new JSONArray();
		for(Contact contact : contacts){
			JSONObject item = new JSONObject();
			item.put("id", contact.getId());
			item.put("name", contact.getName());
			item.put("mobile", contact.getMobile());
			array.put(item);
		}
		String json = array.toString();
		Log.i(TAG, json);
	}
}
