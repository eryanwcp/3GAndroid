package com.eryansky.study.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eryansky.study.db.DBOpenHelper;
import com.eryansky.study.entity.Person;
/**
 * Person管理类 执行相关crud操作 SQL方式
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2012-5-1 下午08:22:21
 */
public class PersonManager {

	private DBOpenHelper dbOpenHelper;

	public PersonManager(Context context) {
		this.dbOpenHelper = DBOpenHelper.Instance(context);
	}
	
	/**
	 * 事务示例
	 */
	public void payment(){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.beginTransaction();//事启事务
		try{
			db.execSQL("update t_person set amount=amount-10 where personid=?", new Object[]{1});
			db.execSQL("update t_person set amount=amount+10 where personid=?", new Object[]{2});
			db.setTransactionSuccessful();//设置事务标志为成功，当结束事务时就会提交事务
		}finally{
			db.endTransaction();
		}
	}
	
	/**
	 * 保存
	 * @param person
	 */
	public void save(Person person){
		//如果要对数据进行更改，就调用此方法得到用于操作数据库的实例,该方法以读和写方式打开数据库
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("insert into t_person (name,amount) values(?,?)",
				new Object[]{person.getName(),person.getAmount()});
	}
	/**
	 * 修改
	 * @param person
	 */
	public void update(Person person){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("update t_person set name=? where personid=?", 
				new Object[]{person.getName(),person.getId()});
	}
	/**
	 * 删除
	 * @param id
	 */
	public void delete(Integer id){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("delete from t_person where personid=?", new Object[]{id.toString()});
	}
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
	public Person find(Integer id){
		//如果只对数据进行读取，建议使用此方法
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from t_person where personid=?", new String[]{id.toString()});
		if(cursor.moveToFirst()){
			int personid = cursor.getInt(cursor.getColumnIndex("personid"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			int amount = cursor.getInt(cursor.getColumnIndex("amount"));
			Person person = new Person(personid, name);
			person.setAmount(amount);
			return person;
		}
		return null;
	}
	/**
	 * 分页查询
	 * @param offset
	 * @param maxResult
	 * @return
	 */
	public List<Person> getScrollData(Integer offset, Integer maxResult){
		List<Person> persons = new ArrayList<Person>();
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from t_person limit ?,?",
				new String[]{offset.toString(), maxResult.toString()});
		while(cursor.moveToNext()){
			int personid = cursor.getInt(cursor.getColumnIndex("personid"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			int amount = cursor.getInt(cursor.getColumnIndex("amount"));
			Person person = new Person(personid, name);
			person.setAmount(amount);
			persons.add(person);
		}
		cursor.close();
		return persons;
	}
	/**
	 * 
	 * @param offset
	 * @param maxResult
	 * @return
	 */
	public Cursor getCursorScrollData(Integer offset, Integer maxResult){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		return db.rawQuery("select personid as _id, name, amount from t_person limit ?,?",
				new String[]{offset.toString(), maxResult.toString()});
	}
	/**
	 * 查找总记录数
	 * @return
	 */
	public long getCount() {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select count(*) from t_person", null);
		cursor.moveToFirst();
		return cursor.getLong(0);
	}
}
