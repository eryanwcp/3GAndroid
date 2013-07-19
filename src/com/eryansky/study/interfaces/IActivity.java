package com.eryansky.study.interfaces;
/**
 * Activity公共接口
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2012-4-23 下午02:47:01
 */
public interface IActivity {

	/**
	 * 可初始化相关操作
	 */
	public void init();

	/**
	 * 刷新Activity窗体
	 * @param param
	 */
	public void refresh(Object... param);
}
