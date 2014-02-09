package com.ladybug.framework.base.service;

import com.ladybug.framework.system.exception.ApplicationException;

/**
 * 请求异常
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class RequestException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public RequestException() {
		super();
	}

	public RequestException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public RequestException(String msg) {
		super(msg);
	}

	public RequestException(Throwable throwable) {
		super(throwable);
	}
	
	

}
