package com.ladybug.framework.base.service;

import com.ladybug.framework.system.exception.SystemException;

/**
 * 服务控制器异常
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class ServiceControllerException extends SystemException {

	private static final long serialVersionUID = 1L;

	public ServiceControllerException() {
		super();
	}

	public ServiceControllerException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ServiceControllerException(String msg) {
		super(msg);
	}
	

}
