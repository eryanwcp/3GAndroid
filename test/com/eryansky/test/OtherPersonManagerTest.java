package com.eryansky.test;

import java.util.List;

import com.eryansky.study.entity.Person;
import com.eryansky.study.service.OtherPersonManager;

import android.test.AndroidTestCase;
import android.util.Log;

public class OtherPersonManagerTest extends AndroidTestCase {
	private static final String TAG = "OtherPersonManagerTest";
	
	public void testSave() throws Throwable{
		OtherPersonManager personService = new OtherPersonManager(this.getContext());
		Person person = new Person();
		person.setName("xiaoxiao");
		personService.save(person);
		
		person = new Person();
		person.setName("zhangliming");
		personService.save(person);
		
		person = new Person();
		person.setName("libaobao");
		personService.save(person);
		
		person = new Person();
		person.setName("taobao");
		personService.save(person);
	}
	
	public void testUpate() throws Throwable{
		OtherPersonManager personService = new OtherPersonManager(this.getContext());
		Person person = personService.find(1);	
		person.setName("lili");
		personService.update(person);
	}
	
	public void testDelete() throws Throwable{
		OtherPersonManager personService = new OtherPersonManager(this.getContext());
		personService.delete(1);
	}
	
	public void testFind() throws Throwable{
		OtherPersonManager personService = new OtherPersonManager(this.getContext());
		Person person = personService.find(1);
		Log.i(TAG, person.toString());
	}
	
	public void testGetScrollData() throws Throwable{
		OtherPersonManager personService = new OtherPersonManager(this.getContext());
		List<Person> persons = personService.getScrollData(0, 10);
		for(Person person : persons){
			Log.i(TAG, person.toString());
		}
	}
	
	public void testGetCount() throws Throwable{
		OtherPersonManager personService = new OtherPersonManager(this.getContext());
		Log.i(TAG, personService.getCount()+"");
	}
}
