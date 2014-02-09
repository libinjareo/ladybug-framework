package com.ladybug.framework.base.event.container;

import com.ladybug.framework.base.event.Event;
import com.ladybug.framework.base.event.EventException;
import com.ladybug.framework.base.event.EventPropertyException;
import com.ladybug.framework.base.event.EventPropertyHandler;
import com.ladybug.framework.base.event.EventResult;
import com.ladybug.framework.system.container.Container;
import com.ladybug.framework.system.exception.ApplicationException;
import com.ladybug.framework.system.exception.SystemException;

/**
 * 事件容器，主要提供以下功能：<br>
 * 1)getEventPropertyHandler<br>
 * 2)createEvent(String application, String key)<br>
 * 3)dispatch(Event paramEvent)
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public interface EventContainer extends Container {
	public static final String EVENT_PROPERTY_HANDLER_KEY = "event";

	/**
	 * 取得事件属性处理器
	 * @return EventPropertyHandler
	 */
	public EventPropertyHandler getEventPropertyHandler();

	/**
	 * 创建Event
	 * @param application 应用，对应于event-config-app.xml
	 * @param key event-key值
	 * @return Event
	 * @throws EventPropertyException
	 * @throws EventException
	 */
	public Event createEvent(String application, String key)
			throws EventPropertyException, EventException;

	/**
	 * 事件派发,需要Event提供application和event-key值
	 * 
	 * @param paramEvent Event
	 * @return
	 * @throws EventException
	 * @throws SystemException
	 * @throws ApplicationException
	 */
	public EventResult dispatch(Event paramEvent) throws EventException,
			SystemException, ApplicationException;
}
