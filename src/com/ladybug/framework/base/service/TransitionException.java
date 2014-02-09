package com.ladybug.framework.base.service;

import com.ladybug.framework.system.exception.SystemException;

/**
 * 转换器异常
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class TransitionException extends SystemException {

	private static final long serialVersionUID = 1L;

	public TransitionException() {
		super();
	}

	public TransitionException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public TransitionException(String msg) {
		super(msg);
	}

	public TransitionException(Throwable throwable) {
		super(throwable);
	}
	
	

}
