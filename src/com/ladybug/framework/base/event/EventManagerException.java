package com.ladybug.framework.base.event;

import com.ladybug.framework.system.exception.SystemException;

/**
 * 事件管理异常
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class EventManagerException extends SystemException {

	private static final long serialVersionUID = 1L;

	public EventManagerException() {
		super();
	}

	public EventManagerException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public EventManagerException(String msg) {
		super(msg);
	}

	public EventManagerException(Throwable throwable) {
		super(throwable);
	}
	
	

}
