package com.ladybug.framework.base.service.container;

import com.ladybug.framework.system.exception.SystemException;

public class ServiceContainerException extends SystemException {

	private static final long serialVersionUID = 1L;

	public ServiceContainerException() {
		super();
	}

	public ServiceContainerException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ServiceContainerException(String msg) {
		super(msg);
	}

	public ServiceContainerException(Throwable throwable) {
		super(throwable);
	}

}
