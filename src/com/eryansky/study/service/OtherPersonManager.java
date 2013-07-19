package com.eryansky.study.service;

import java.util.ArrayList;
import java.util.List;

import com.eryansky.study.db.DBOpenHelper;
import com.eryansky.study.entity.Person;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * Person管理类 执行相关crud操作
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2012-5-1 下午09:32:17
 */
public class OtherPersonManager {
	private DBOpenHelper dbOpenHelper;
	
	public OtherPersonManager(Context context) {
		this.dbOpenHelper = DBOpenHelper.Instance(context);
	}

	public void save(Person person){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", person.getName());
		db.insert(DBOpenHelper.T_PERSON, null, values);
	}
	
	public void update(Person person){
		// update person set name =? where personid =?
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", person.getName());
		db.update(DBOpenHelper.T_PERSON, values, "personid=?", new String[]{person.getId().toString()});
	}
	
	public void delete(Integer id){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.delete(DBOpenHelper.T_PERSON, "personid=?", new String[]{id.toString()});
	}
	
	public Person find(Integer id){
		//如果只对数据进行读取，建议使用此方法
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query(DBOpenHelper.T_PERSON, new String[]{"personid", "name"},
				"personid=?", new String[]{id.toString()}, null, null, null);
		//select personid,name from person where personid=? order by ... limit 3,5
		if(cursor.moveToFirst()){
			int personid = cursor.getInt(cursor.getColumnIndex("personid"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			return new Person(personid, name);
		}
		return null;
	}

	public List<Person> getScrollData(Integer offset, Integer maxResult){
		List<Person> persons = new ArrayList<Person>();
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query(DBOpenHelper.T_PERSON, null, null, null, null, null, null, offset+","+ maxResult);
		while(cursor.moveToNext()){
			int personid = cursor.getInt(cursor.getColumnIndex("personid"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			Person person = new Person(personid, name);
			persons.add(person);
		}
		cursor.close();
		return persons;
	}

	public long getCount() {// select count(*) from person
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query(DBOpenHelper.T_PERSON, new String[]{"count(*)"}, null, null, null, null, null);
		cursor.moveToFirst();
		return cursor.getLong(0);
	}
}
