package com.ladybug.framework.base.event;

import com.ladybug.framework.base.data.DataAccessController;
import com.ladybug.framework.system.exception.ApplicationException;
import com.ladybug.framework.system.exception.SystemException;

/**
 * 事件触发器接口
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public interface EventTrigger {
	
	/**
	 * 执行业务逻辑
	 * 
	 * @param paramEvent Event
	 * @param paramDataAccessController DataAccessController
	 * @throws SystemException
	 * @throws ApplicationException
	 */
	public void fire(Event paramEvent,
			DataAccessController paramDataAccessController)
			throws SystemException, ApplicationException;
}
