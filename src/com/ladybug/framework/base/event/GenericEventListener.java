package com.ladybug.framework.base.event;

import com.ladybug.framework.system.exception.ApplicationException;
import com.ladybug.framework.system.exception.SystemException;

/**
 * 通用事件监听,关联StandardEventListener
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class GenericEventListener implements EventListener {

	private StandardEventListener listener;

	@Override
	public EventResult execute(Event event) throws SystemException,
			ApplicationException {
		return this.listener.execute(event);
	}

	public void setListener(StandardEventListener listener) {
		this.listener = listener;
	}
}
