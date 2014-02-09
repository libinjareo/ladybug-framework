package com.ladybug.framework.base.event;

import com.ladybug.framework.base.data.DAOException;
import com.ladybug.framework.base.data.DataAccessController;
import com.ladybug.framework.base.data.DataConnectException;
import com.ladybug.framework.base.data.DataConnectorException;
import com.ladybug.framework.base.data.DataPropertyException;
import com.ladybug.framework.system.exception.ApplicationException;
import com.ladybug.framework.system.exception.SystemException;

/**
 * 事件触发器适配器抽象,关联DataAccessController,增加方法：<br>
 * 1)getDAO<br>
 * 2)fire(Event event):执行业务逻辑，留由子类实现
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public abstract class EventTriggerAdapter implements EventTrigger {

	/**
	 * DAO控制器
	 */
	private DataAccessController controller;

	/**
	 * 默认的构造函数
	 */
	public EventTriggerAdapter() {
		controller = null;
	}

	/**
	 * 取得DAO对象
	 * @param application data配置业务名称，如data-config-crm.xml中的crm
	 * @param key (dao-key)配置名称
	 * @param connect data-config-相应业务.xml中的(connector-name)配置名称
	 * @return DAO
	 * @throws DataPropertyException
	 * @throws DataConnectorException
	 * @throws DataConnectException
	 * @throws DAOException
	 */
	protected Object getDAO(String application, String key, String connect)
			throws DataPropertyException, DataConnectorException,
			DataConnectException, DAOException {
		return this.controller.getDAO(application, key, connect);
	}

	@Override
	public void fire(Event event, DataAccessController controller)
			throws SystemException, ApplicationException {
		this.controller = controller;
		fire(event);

	}

	/**
	 * 执行相应业务逻辑
	 * 
	 * @param event Event
	 * @throws SystemException
	 * @throws ApplicationException
	 */
	protected abstract void fire(Event event) throws SystemException,
			ApplicationException;
}
