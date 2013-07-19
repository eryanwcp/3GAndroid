package com.eryansky.study.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * SQLite工具类 （单例）
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2012-5-1 下午08:10:21
 */
public class DBOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "study.db";  
    private static final int DATABASE_VERSION = 1;  
    public static final String T_PERSON ="t_person";  
    
    private static DBOpenHelper instance;  
    
	public DBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static DBOpenHelper Instance(Context context) {  
        if (instance == null) {  
            instance = new DBOpenHelper(context);  
        }   
        return instance;  
    } 
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + T_PERSON +" (personid integer primary key autoincrement, name varchar(20), amount integer)");//执行有更改的sql语句
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + T_PERSON);
		onCreate(db);
	}

}
