package com.ladybug.framework.base.service;

import com.ladybug.framework.system.exception.SystemException;

/**
 * 服务属性处理异常
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class ServicePropertyException extends SystemException {

	private static final long serialVersionUID = 1L;

	public ServicePropertyException() {
		super();
	}

	public ServicePropertyException(String msg) {
		super(msg);
	}

	public ServicePropertyException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

}
