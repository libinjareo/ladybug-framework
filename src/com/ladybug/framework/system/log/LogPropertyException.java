package com.ladybug.framework.system.log;

import com.ladybug.framework.system.exception.SystemException;

/**
 * 日志属性异常
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class LogPropertyException extends SystemException {

	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 * @param throwable Throwable
	 */
	public LogPropertyException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	/**
	 * 构造函数
	 * @param msg 异常信息
	 */
	public LogPropertyException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数
	 * @param throwable Throwable
	 */
	public LogPropertyException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * 默认的构造函数
	 */
	public LogPropertyException() {
		super();
	}

}
