package com.ladybug.framework.base.event;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.ladybug.framework.base.data.DAOException;
import com.ladybug.framework.base.data.DataAccessController;
import com.ladybug.framework.base.data.DataConnectException;
import com.ladybug.framework.base.data.DataConnectorException;
import com.ladybug.framework.base.data.DataManager;
import com.ladybug.framework.base.data.DataPropertyException;
import com.ladybug.framework.system.exception.ApplicationException;
import com.ladybug.framework.system.exception.IllegalSystemException;
import com.ladybug.framework.system.exception.SystemException;
import com.ladybug.framework.system.log.LogManager;

/**
 * 标准事件监听抽象,关联DataAccessController,主要增加以下几个功能：<br>
 * 1)getDAO<br>取得Dao对象<br>
 * 2)EventResult fire(Event event)，执行业务逻辑,留由子类实现<br>
 * 3)EventResult diapatchEvent(Event event),事件派发,留由子类实现
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public abstract class StandardEventListener implements EventListener {

	/**
	 * DAO控制器
	 */
	private DataAccessController dataAccessController;

	/**
	 * 取得Dao控制器
	 * @return DataAccessController
	 */
	protected DataAccessController getDataAccessController() {
		return this.dataAccessController;
	}

	/**
	 * 取得Dao对象
	 * @param application data配置业务名称，如data-config-crm.xml中的crm
	 * @param key (dao-key)配置名称
	 * @param connect data-config-相应业务.xml中的(connector-name)配置名称
	 * @return Dao
	 * @throws DataPropertyException
	 * @throws DataConnectorException
	 * @throws DataConnectException
	 * @throws DAOException
	 */
	protected Object getDAO(String application, String key, String connect)
			throws DataPropertyException, DataConnectorException,
			DataConnectException, DAOException {
		return getDataAccessController().getDAO(application, key, connect);
	}

	@Override
	public EventResult execute(Event event) throws SystemException,
			ApplicationException {
		//数据管理器
		DataManager manager = DataManager.getDataManager();
		//前置触发器列表
		EventTriggerList triggerList = null;
		//Post触发器列表
		EventTriggerList postTriggerList = null;
		EventResult result = null;

		//DAO控制器
		this.dataAccessController = manager.getDataAccessController();
		
		try {
			//取得前置触发器列表
			triggerList = new EventTriggerList(event.getApplication(), event
					.getKey());
		} catch (Throwable e) {
			LogManager.getLogManager().getLogAgent().sendMessage(
					EventManager.class.getName(), "ERROR",
					EventManager.LOG_HEAD + e.getMessage(), e);
			triggerList = null;
		}

		try {
			//取得Post触发器列表
			postTriggerList = new EventTriggerList(event.getApplication(),
					event.getKey(), false);
		} catch (Throwable e) {
			LogManager.getLogManager().getLogAgent().sendMessage(
					EventManager.class.getName(), "ERROR",
					EventManager.LOG_HEAD + e.getMessage(), e);
			postTriggerList = null;
		}

		try {
			//如果存在前置触发器，则执行所有前置触发器业务
			if (triggerList != null) {
				triggerList.fireAll(event, this.dataAccessController);
			}

			try {
				//执行基本事件业务逻辑
				result = fire(event);
				
				//如果存在Post触发器，则执行所有Post触发器业务
				if (postTriggerList != null) {
					postTriggerList.fireAll(event, this.dataAccessController);
				}
				
				//进行统一提交
				this.dataAccessController.commit();
				
			} catch (Throwable e) {
				try {
					this.dataAccessController.rollback();
				} catch (Throwable ex) {
					//打印回滚错误信息
					LogManager.getLogManager().getLogAgent().sendMessage(
							EventManager.class.getName(), "ERROR",
							EventManager.LOG_HEAD + ex.getMessage(), ex);
				}
				//打印异常信息
				LogManager.getLogManager().getLogAgent().sendMessage(
						EventManager.class.getName(), "ERROR",
						EventManager.LOG_HEAD + e.getMessage(), e);
				
			}
		} catch (Throwable e) {
			if ((e instanceof SystemException))
				throw ((SystemException) e);
			if ((e instanceof ApplicationException)) {
				throw ((ApplicationException) e);
			}
			//打印执行过程中异常信息
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.event.i18n").getString(
						"Common.UnsupposedExceptionExecuteEvent");
			} catch (MissingResourceException ex) {

			}
			throw new IllegalSystemException(message, e);
		} finally {
			try {
				//释放资源
				this.dataAccessController.release();
			} catch (Throwable e) {
				LogManager.getLogManager().getLogAgent().sendMessage(
						EventManager.class.getName(), "ERROR",
						EventManager.LOG_HEAD + e.getMessage(), e);
			}
		}
		return result;
	}

	/**
	 *  执行具体事件业务逻辑
	 *  
	 * @param event Event
	 * @return EventResult
	 * @throws SystemException
	 * @throws ApplicationException
	 */
	protected abstract EventResult fire(Event event) throws SystemException,
			ApplicationException;

	/**
	 * 进行Event事件分发
	 * 
	 * @param event Event
	 * @return EventResult
	 * @throws EventManagerException
	 * @throws EventException
	 * @throws SystemException
	 * @throws ApplicationException
	 */
	protected EventResult diapatchEvent(Event event)
			throws EventManagerException, EventException, SystemException,
			ApplicationException {
		return EventManager.getEventManager().dispatch(event);
	}
}
