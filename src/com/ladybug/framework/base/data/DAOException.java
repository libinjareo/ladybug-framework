package com.ladybug.framework.base.data;

import com.ladybug.framework.system.exception.SystemException;

/**
 * DAO异常
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class DAOException extends SystemException {

	private static final long serialVersionUID = 1L;

	/**
	 * 默认的构造函数
	 */
	public DAOException() {
		super();
	}

	/**
	 * 构造 函数
	 * 
	 * @param msg 异常信息
	 * @param throwable Throwable
	 */
	public DAOException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 */
	public DAOException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数
	 * 
	 * @param throwable Throwable
	 */
	public DAOException(Throwable throwable) {
		super(throwable);
	}

}
