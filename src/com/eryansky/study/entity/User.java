package com.eryansky.study.entity;
/**
 * 用户User entity
 * @ClassName: User 
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2011-12-28 上午11:13:37
 */
public class User {
	
	private String username;//用户名
	private String password;//密码
	
	public User(){
		
	}
	
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
