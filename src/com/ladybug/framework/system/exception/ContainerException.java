package com.ladybug.framework.system.exception;

/**
 * 容器异常
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class ContainerException extends SystemException {

	private static final long serialVersionUID = 1L;

	/**
	 * 默认的构造函数
	 */
	public ContainerException() {
		super();
	}

	/**
	 *  构造函数
	 *  
	 * @param msg 异常信息
	 * @param throwable Throwable
	 */
	public ContainerException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	/**
	 * 构造函数
	 * @param msg 异常信息
	 */
	public ContainerException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数
	 * @param throwable Throwable
	 */
	public ContainerException(Throwable throwable) {
		super(throwable);
	}
	
	

}
