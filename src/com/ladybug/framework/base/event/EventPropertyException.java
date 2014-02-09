package com.ladybug.framework.base.event;

import com.ladybug.framework.system.exception.SystemException;

/**
 * Event属性异常
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class EventPropertyException extends SystemException {

	private static final long serialVersionUID = 1L;

	public EventPropertyException() {
		super();
	}

	public EventPropertyException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public EventPropertyException(String msg) {
		super(msg);
	}

	public EventPropertyException(Throwable throwable) {
		super(throwable);
	}

}
