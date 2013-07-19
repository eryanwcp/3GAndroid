package com.eryansky.study.entity;
/**
 * Person实体类
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2012-5-1 下午08:07:43
 */
public class Person {
	private Integer id;
	private String name;
	private Integer amount;
	
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Person(){}
	
	public Person(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
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

	@Override
	public String toString() {
		return "Person [amount=" + amount + ", id=" + id + ", name=" + name
				+ "]";
	}


}
