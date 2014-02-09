package com.ladybug.framework.system.exception;

/**
 * 系统非法异常
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class IllegalSystemException extends SystemException {
	private static final long serialVersionUID = 1L;

	/**
	 * 默认的构造函数
	 */
	public IllegalSystemException() {
		super();
	}

	/**
	 * 构造函数
	 * @param msg 异常信息
	 * @param throwable Throwable
	 */
	public IllegalSystemException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	/**
	 * 构造函数
	 * @param msg 异常信息
	 */
	public IllegalSystemException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数
	 * 
	 * @param throwable Throwable
	 */
	public IllegalSystemException(Throwable throwable) {
		super(throwable);
	}

	
}
