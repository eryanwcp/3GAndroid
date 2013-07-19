package com.eryansky.study.exception;

/**
 * Service层公用的Exception.
 * 继承自RuntimeException, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 * @author 尔演&Eryan eryanwcp@gmail.com
 * @date   2012-3-22 上午9:49:32
 */
@SuppressWarnings("serial")
public class SystemException extends RuntimeException {


	public SystemException() {
		super();
	}

	public SystemException(String message) {
		super(message);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}
}
