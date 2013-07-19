package com.eryansky.test;

import java.util.List;

import com.eryansky.study.db.DBOpenHelper;
import com.eryansky.study.entity.Person;
import com.eryansky.study.service.PersonManager;

import android.test.AndroidTestCase;
import android.util.Log;

public class PersonManagerTest extends AndroidTestCase {
	private static final String TAG = "PersonManagerTest";

	public void testCreateDB() throws Throwable{
		DBOpenHelper dbOpenHelper = new DBOpenHelper(this.getContext());
		dbOpenHelper.getWritableDatabase();//第一次调用该方法就会创建数据库
	}
	
	public void testSave() throws Throwable{
		PersonManager personManager = new PersonManager(this.getContext());
		Person person = new Person();
		person.setName("eryan");
		person.setAmount(100);
		personManager.save(person);
		
		person = new Person();
		person.setAmount(50);
		person.setName("zhangliming");
		personManager.save(person);
		
		person = new Person();
		person.setAmount(45);
		person.setName("libaobao");
		personManager.save(person);
		
		person = new Person();
		person.setAmount(190);
		person.setName("taobao");
		personManager.save(person);
	}
	
	public void testUpate() throws Throwable{
		PersonManager personManager = new PersonManager(this.getContext());
		Person person = personManager.find(1);	
		person.setName("lili");
		personManager.update(person);
	}
	
	public void testDelete() throws Throwable{
		PersonManager personManager = new PersonManager(this.getContext());
		personManager.delete(1);
	}
	
	public void testFind() throws Throwable{
		PersonManager personManager = new PersonManager(this.getContext());
		Person person = personManager.find(1);
		Log.i(TAG, person.toString());
	}
	
	public void testGetScrollData() throws Throwable{
		PersonManager personManager = new PersonManager(this.getContext());
		List<Person> persons = personManager.getScrollData(0, 30);
		for(Person person:persons){
			Log.i(TAG, person.toString());
		}
	}
	
	public void testGetCount() throws Throwable{
		PersonManager personManager = new PersonManager(this.getContext());
		Log.i(TAG, personManager.getCount()+"");
	}
	
	public void testPayment() throws Throwable{
		PersonManager personManager = new PersonManager(this.getContext());
		personManager.payment();
	}
}
