package com.ladybug.framework.system.exception;

/**
 * 应用异常
 * 
 * @author james.li
 * @version 1.0
 *
 */
public class ApplicationException extends FrameworkException {

	private static final long serialVersionUID = 1L;

	/**
	 *  默认的构造函数
	 */
	public ApplicationException() {
		super();
	}

	/**
	 * 构造函数
	 * @param msg 异常信息
	 * @param throwable Throwable
	 */
	public ApplicationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	/**
	 * 构造函数
	 * @param msg 异常信息
	 */
	public ApplicationException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数
	 * @param throwable Throwable
	 */
	public ApplicationException(Throwable throwable) {
		super(throwable);
	}
	
}
