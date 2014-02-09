package com.ladybug.framework.base.data;

import com.ladybug.framework.system.exception.SystemException;

/**
 * 数据连接异常
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class DataConnectException extends SystemException {

	private static final long serialVersionUID = 1L;

	/**
	 * 默认的构造函数
	 */
	public DataConnectException() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 * @param throwable Throwable
	 */
	public DataConnectException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 */
	public DataConnectException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数
	 * 
	 * @param throwable Throwable
	 */
	public DataConnectException(Throwable throwable) {
		super(throwable);
	}

}
