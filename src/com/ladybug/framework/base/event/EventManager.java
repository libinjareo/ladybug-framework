package com.ladybug.framework.base.event;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.ladybug.framework.base.event.container.EventContainer;
import com.ladybug.framework.base.event.container.factory.EventContainerFactory;
import com.ladybug.framework.system.exception.ApplicationException;
import com.ladybug.framework.system.exception.ContainerException;
import com.ladybug.framework.system.exception.SystemException;
import com.ladybug.framework.system.log.LogManager;

/**
 * 事件管理器，单例模式实现，关联EventContainer,主要提供如下功能:<br>
 * 1)getEventPropertyHandler<br>
 * 2)createEvent(String application, String key)<br>
 * 3)dispatch(Event event) 
 * 
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class EventManager {
	public static String LOG_HEAD = "[JEE][Event]";
	public static final String EVENT_PROPERTY_HANDLER_KEY = "event";

	private static Boolean managerFlag = new Boolean(false);
	private static EventManager manager;
	private EventContainer eventContainer;

	/**
	 * 默认私有构造函数，初始化EventContainer
	 * 
	 * @throws EventManagerException
	 */
	private EventManager() throws EventManagerException {
		try {
			this.eventContainer = (EventContainer) new EventContainerFactory()
					.create();
			
			//打印创建成功信息
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.event.i18n").getString(
						"EventManager.SuccessedToCreateContainer");
			} catch (MissingResourceException ex) {

			}

			LogManager.getLogManager().getLogAgent().sendMessage(
					EventManager.class.getName(),
					"INFO",
					LOG_HEAD + message + " - "
							+ this.eventContainer.getClass().getName());
		} catch (ContainerException e) {
			throw new EventManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 入口方法，取得事件管理器
	 * 
	 * @return EventManager
	 * @throws EventManagerException
	 */
	public static EventManager getEventManager() throws EventManagerException {
		//如果没有创建实例
		if (!managerFlag.booleanValue()) {
			//保证线程安全
			synchronized (managerFlag) {
				//如果没有创建过实例
				if (!managerFlag.booleanValue()) {
					try {
						//创建实例
						manager = new EventManager();
					} catch (EventManagerException e) {
						String message = null;
						try {
							message = ResourceBundle
									.getBundle(
											"com.ladybug.framework.base.event.i18n")
									.getString(
											"EventManager.FailedToCreateManager");
						} catch (MissingResourceException ex) {

						}
						LogManager.getLogManager().getLogAgent().sendMessage(
								EventManager.class.getName(), "ERROR",
								LOG_HEAD + message, e);

						throw e;
					}
					//更改创建标志
					managerFlag = new Boolean(true);
					
					//打印成功信息
					String message = null;
					try {
						message = ResourceBundle
								.getBundle(
										"com.ladybug.framework.base.event.i18n")
								.getString(
										"EventManager.SuccessedToCreateManager");
					} catch (MissingResourceException ex) {

					}

					LogManager.getLogManager().getLogAgent().sendMessage(
							EventManager.class.getName(), "INFO",
							LOG_HEAD + message);
				}

			}
		}
		return manager;
	}

	/**
	 * 取得事件属性处理器
	 * @return EventPropertyHandler
	 */
	public EventPropertyHandler getEventPropertyHandler() {
		return this.eventContainer.getEventPropertyHandler();
	}

	/**
	 * 创建事件Event
	 * 
	 * @param application 应用
	 * @param key event-key值
	 * @return Event
	 * @throws EventPropertyException
	 * @throws EventException
	 */
	public Event createEvent(String application, String key)
			throws EventPropertyException, EventException {
		//由EventContainer创建
		Event event = this.eventContainer.createEvent(application, key);

		event.setApplication(application);
		event.setKey(key);

		return event;

	}

	/**
	 * 事件派发
	 * 
	 * @param event Event
	 * @return EventResult
	 * @throws EventException
	 * @throws SystemException
	 * @throws ApplicationException
	 */
	public EventResult dispatch(Event event) throws EventException,
			SystemException, ApplicationException {
		return this.eventContainer.dispatch(event);
	}
}
