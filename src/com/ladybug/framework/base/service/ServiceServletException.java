package com.ladybug.framework.base.service;

import com.ladybug.framework.system.exception.SystemException;

/**
 * 服务servlet异常
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class ServiceServletException extends SystemException {

	private static final long serialVersionUID = 1L;

	public ServiceServletException() {
		super();
	}

	public ServiceServletException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ServiceServletException(String msg) {
		super(msg);
	}

	public ServiceServletException(Throwable throwable) {
		super(throwable);
	}

}
