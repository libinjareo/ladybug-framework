package com.ladybug.framework.base.event;

import com.ladybug.framework.system.exception.ApplicationException;
import com.ladybug.framework.system.exception.SystemException;

/**
 * 事件监听接口，只有一个方法：execute(Event)
 * 
 * @author james.li
 * @version 1.0
 *
 */
public interface EventListener {

	/**
	 * 执行业务逻辑
	 * @param paramEvent Event事件
	 * @return EventResult
	 * @throws SystemException
	 * @throws ApplicationException
	 */
	public EventResult execute(Event paramEvent) throws SystemException,
			ApplicationException;

}
