package com.ladybug.framework.base.event;

import com.ladybug.framework.system.exception.SystemException;

/**
 * 事件异常
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class EventException extends SystemException {

	private static final long serialVersionUID = 1L;

	public EventException() {
		super();
	}

	public EventException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public EventException(String msg) {
		super(msg);
	}

	public EventException(Throwable throwable) {
		super(throwable);
	}

}
