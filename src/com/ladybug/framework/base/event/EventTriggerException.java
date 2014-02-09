package com.ladybug.framework.base.event;

import com.ladybug.framework.system.exception.SystemException;

/**
 * Event触发器异常
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class EventTriggerException extends SystemException {

	private static final long serialVersionUID = 1L;

	public EventTriggerException() {
		super();
	}

	public EventTriggerException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public EventTriggerException(String msg) {
		super(msg);
	}

	public EventTriggerException(Throwable throwable) {
		super(throwable);
	}
	
	

}
