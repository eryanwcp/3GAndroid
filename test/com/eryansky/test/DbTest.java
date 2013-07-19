package com.eryansky.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.test.AndroidTestCase;
import android.util.Log;
/**
 * 手机程序直接访问PC数据库 MySQL （不安全）
 * @author 尔演&wencp eryanwcp@gmail.com
 * @date   2012-7-9 上午10:25:52
 *
 */
public class DbTest extends AndroidTestCase {
	
	private static final String TAG = "DbTest";

	public void linkMySQL() {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://10.0.2.2:3306/eryansky?useUnicode=true&amp;characterEncoding=UTF-8";
		String user = "eryansky";//远程访问MySQL需要开启相关授权
		String password = "password";
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, password);

			if (!conn.isClosed())
			Log.i(TAG, "数据库连接成功！");

			//数据库查询 测试
			String query = "select * from t_user";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				
				int id = rs.getInt(1);
				String loginname = rs.getString(2);
				Log.i(TAG, id + "-" + loginname);
			}
			conn.close();
			
		} catch (ClassNotFoundException e) {
			Log.e(TAG,"找不到驱动程序，" + e.getMessage());
		} catch (SQLException e) {
			Log.e(TAG,"数据库连接异常，" + e.getMessage());
		}
	}
}
