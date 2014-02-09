package com.ladybug.framework.system.property;

import com.ladybug.framework.system.exception.FrameworkException;

/**
 * 属性处理器异常
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class PropertyHandlerException extends FrameworkException {

	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 * @param throwable Throwable
	 */
	public PropertyHandlerException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	/**
	 * 构造函数
	 * @param msg 异常信息
	 */
	public PropertyHandlerException(String msg) {
		super(msg);
	}

	/**
	 * 默认的构造函数
	 */
	public PropertyHandlerException() {
		
	}

}
