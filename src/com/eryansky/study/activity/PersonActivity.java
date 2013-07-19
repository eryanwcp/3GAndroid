package com.eryansky.study.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.eryansky.study.R;
import com.eryansky.study.entity.Person;
import com.eryansky.study.interfaces.IActivity;
import com.eryansky.study.service.MainService;
import com.eryansky.study.service.PersonManager;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
/**
 * Person Activity 
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2012-5-1 下午08:37:23
 */
public class PersonActivity extends Activity implements IActivity{

	private static final String TAG = "PersonActivity";
	private PersonManager personManager;
	private ListView lv_list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainService.allActivity.add(this);//将Activity交给MainService管理
		setContentView(R.layout.person);
		this.personManager = new PersonManager(this);
	       
        lv_list = (ListView) this.findViewById(R.id.person_lv);
        load();
        lv_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ListView lView = (ListView)parent;
				Cursor data = (Cursor)lView.getItemAtPosition(position);
				int personid = data.getInt(data.getColumnIndex("_id"));
				Toast.makeText(PersonActivity.this, personid+"", Toast.LENGTH_SHORT).show();
			}
		});
        
//        另外一种方式
//        List<Person> persons = personManager.getScrollData(0, 5);
//        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
//        for(Person person : persons){
//        	HashMap<String, Object> item = new HashMap<String, Object>();
//        	item.put("id", person.getId());
//        	item.put("name", person.getName());
//        	item.put("amount", person.getAmount());
//        	data.add(item);
//        }
//        SimpleAdapter adapter2 = new SimpleAdapter(this, data, R.layout.person_item,
//        		new String[]{"id", "name", "amount"}, new int[]{R.id.person_item_id, R.id.person_item_name, R.id.person_item_amount});
//        lv_list.setAdapter(adapter);
//        
//        lv_list.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				ListView lView = (ListView)parent;
//				HashMap<String, Object> item = (HashMap<String, Object>)lView.getItemAtPosition(position);
//				Toast.makeText(PersonActivity.this, item.get("id").toString(), Toast.LENGTH_SHORT).show();
//			}
//		});
//		
        
        Button button = (Button) this.findViewById(R.id.person_add);
        button.setOnClickListener(new AddButtonListener());
    }

	//添加按钮事件 处理 匿名内部类
	private final class AddButtonListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			Log.i(TAG, v.getId()+"");
			
			ContentResolver contentResolver = getContentResolver();
			Uri insertUri = Uri.parse("content://com.eryansky.providers.personprovider/person");
			ContentValues values = new ContentValues();
			values.put("name", "eryan");
			values.put("amount", 100);
			Uri uri = contentResolver.insert(insertUri, values);
			Toast.makeText(PersonActivity.this, "添加完成", Toast.LENGTH_SHORT).show();
			load();
		}
		
	}
	
	//加载数据
	private void load(){
		Cursor cursor = personManager.getCursorScrollData(0, 10);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.person_item, cursor,
        		new String[]{"_id", "name", "amount"}, new int[]{R.id.person_item_id, R.id.person_item_name, R.id.person_item_amount});
        lv_list.setAdapter(adapter);
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		
	}
}
