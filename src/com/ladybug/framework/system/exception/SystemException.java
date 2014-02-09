package com.ladybug.framework.system.exception;

/**
 * 应用系统异常
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class SystemException extends FrameworkException {

	private static final long serialVersionUID = 1L;

	/**
	 * 构造 函数
	 */
	public SystemException() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 * @param throwable Throwable
	 */
	public SystemException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 */
	public SystemException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数
	 * 
	 * @param throwable Throwable
	 */
	public SystemException(Throwable throwable) {
		super(throwable);
	}
	
	

}
