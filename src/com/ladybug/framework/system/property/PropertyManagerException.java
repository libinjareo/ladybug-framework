package com.ladybug.framework.system.property;

import com.ladybug.framework.system.exception.FrameworkException;

/**
 * 属性管理异常
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class PropertyManagerException extends FrameworkException {

	private static final long serialVersionUID = 1L;

	/**
	 * 默认的构造函数
	 */
	public PropertyManagerException() {
		super();
	}

	/**
	 * 默认的构造函数
	 * 
	 * @param msg 异常信息
	 */
	public PropertyManagerException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数
	 * 
	 * @param throwable 异常信息
	 */
	public PropertyManagerException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * 构造函数
	 * @param msg 异常信息
	 * @param throwable Throwable
	 */
	public PropertyManagerException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	
}
