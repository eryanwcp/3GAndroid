package com.eryansky.study.entity;
/**
 * 城市实体Entity 网络获取城市信息 JSON数据时使用的城市实体entity
 * @ClassName: City 
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2011-12-28 上午11:12:04
 */
public class City {
	
	private String name;
	private int lat;
	private int lon;
	public City() {
		// TODO Auto-generated constructor stub
	}
	
	public City(String name, int lat, int lon) {
		super();
		this.name = name;
		this.lat = lat;
		this.lon = lon;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLat() {
		return lat;
	}
	public void setLat(int lat) {
		this.lat = lat;
	}
	public int getLon() {
		return lon;
	}
	public void setLon(int lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return "City [name=" + name + ", lat=" + lat + ", lon=" + lon + "]";
	}
	
	
}
