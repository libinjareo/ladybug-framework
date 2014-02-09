package com.ladybug.framework.base.event;

import com.ladybug.framework.system.exception.SystemException;

/**
 * 事件监听异常
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class EventListenerException extends SystemException {

	private static final long serialVersionUID = 1L;

	public EventListenerException() {
		super();
	}

	public EventListenerException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public EventListenerException(String msg) {
		super(msg);
	}

	public EventListenerException(Throwable throwable) {
		super(throwable);
	}

}
