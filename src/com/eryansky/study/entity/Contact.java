package com.eryansky.study.entity;
/**
 * 联系人 Entity （用于在android使用html布局页面使用） 
 * @ClassName: Contact 
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2011-12-28 上午11:13:22
 */
public class Contact {
	private Integer id;//联系人编号
	private String name;//联系人姓名
	private String mobile;//两膝人手机号
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Contact(Integer id, String name, String mobile) {
		super();
		this.id = id;
		this.name = name;
		this.mobile = mobile;
	}
	
}
