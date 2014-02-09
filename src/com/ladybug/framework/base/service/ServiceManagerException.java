package com.ladybug.framework.base.service;

import com.ladybug.framework.system.exception.SystemException;

/**
 * 服务管理器异常
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class ServiceManagerException extends SystemException {

	private static final long serialVersionUID = 1L;

	public ServiceManagerException() {
		super();
	}

	public ServiceManagerException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ServiceManagerException(String msg) {
		super(msg);
	}

	public ServiceManagerException(Throwable throwable) {
		super(throwable);
	}

	
}
