package com.ladybug.framework.base.event.container;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.ladybug.framework.base.event.EmptyEvent;
import com.ladybug.framework.base.event.Event;
import com.ladybug.framework.base.event.EventException;
import com.ladybug.framework.base.event.EventListener;
import com.ladybug.framework.base.event.EventListenerException;
import com.ladybug.framework.base.event.EventListenerFactory;
import com.ladybug.framework.base.event.EventListenerFactoryParam;
import com.ladybug.framework.base.event.EventPropertyException;
import com.ladybug.framework.base.event.EventPropertyHandler;
import com.ladybug.framework.base.event.EventResult;
import com.ladybug.framework.system.exception.ApplicationException;
import com.ladybug.framework.system.exception.ContainerException;
import com.ladybug.framework.system.exception.SystemException;
import com.ladybug.framework.system.property.PropertyHandlerException;
import com.ladybug.framework.system.property.PropertyManager;
import com.ladybug.framework.system.property.PropertyManagerException;

/**
 * 事件容器实现，关联EventPropertyHandler
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 */
public class EventContainerImpl implements EventContainer {

	/**
	 * 事件属性处理器
	 */
	private EventPropertyHandler eventPropertyHandler;
	
	/**
	 * 事件监听工厂缓存
	 */
	private Map<String,EventListenerFactory> factories;

	@Override
	public void init() throws ContainerException {
		PropertyManager propertyManager;

		try {
			propertyManager = PropertyManager.getPropertyManager();
		} catch (PropertyManagerException e) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.event.i18n").getString(
						"EventManager.FailedToGetPropertyManager");
			} catch (MissingResourceException ex) {

			}

			throw new ContainerException(message, e);
		}

		try {
			this.eventPropertyHandler = (EventPropertyHandler) propertyManager
					.getPropertyHandler("event");
		} catch (PropertyHandlerException e) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.event.i18n").getString(
						"EventManager.FailedToGetEventPropertyHandler");
			} catch (MissingResourceException ex) {

			}

			throw new ContainerException(message + " : " + "event", e);
		}

		//初始化缓存
		this.factories = new HashMap<String,EventListenerFactory>();
	}

	@Override
	public EventPropertyHandler getEventPropertyHandler() {
		return this.eventPropertyHandler;
	}

	@Override
	public Event createEvent(String application, String key)
			throws EventPropertyException, EventException {
		String name = null;
		Object eventObject = null;
		Event event = null;
		
		//创建Event实现类名称
		name = this.eventPropertyHandler.getEventName(application, key);
		if (name == null) {
			//如果类名称为空则为EmptyEvent()
			event = new EmptyEvent();
		} else {
			try {
				//创建event对象实例
				eventObject = Class.forName(name).newInstance();
			} catch (Exception e) {
				String message = null;
				try {
					message = ResourceBundle.getBundle(
							"com.ladybug.framework.base.event.i18n")
							.getString("EventManager.FailedToCreateEvent");
				} catch (MissingResourceException ex) {

				}

				throw new EventException(message + " : event class = " + name
						+ ", application = " + application + ", key = " + key,
						e);
			}

			//进行强制转换
			if (eventObject instanceof Event) {
				event = (Event) eventObject;
			} else {
				String message = null;
				try {
					message = ResourceBundle.getBundle(
							"com.ladybug.framework.base.event.i18n")
							.getString("EventManager.EventExtended");
				} catch (MissingResourceException ex) {

				}

				throw new EventException(message + " : event class = " + name
						+ ", application = " + application + ", key = " + key);

			}
		}
		return event;
	}

	@Override
	public EventResult dispatch(Event event) throws EventException,
			SystemException, ApplicationException {
		
		//创建Event监听工厂
		EventListenerFactory factory = getEventListenerFactory(event);
		//创建事件监听
		EventListener listener = factory.create(event);
		//执行相应事件业务逻辑
		return listener.execute(event);
	}

	/**
	 * 根据Event创建EventListenerFactory，需要Event提供application和event-key值
	 * 
	 * @param event Event
	 * @return
	 * @throws EventException
	 */
	private EventListenerFactory getEventListenerFactory(Event event)
			throws EventException {
		EventListenerFactory factory = null;

		//应用
		String eventApp = event.getApplication();
		//event-key
		String eventKey = event.getKey();

		//应用和Event-key只要有一个不存在则打印错误信息
		if ((eventApp == null) || ("".equals(eventApp)) || (eventKey == null)
				|| ("".equals(eventKey))) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.event.i18n").getString(
						"Common.IllegalEvent");
			} catch (MissingResourceException ex) {

			}

			throw new EventException(message + " : event class = "
					+ event.getClass().getName() + ", application = "
					+ eventApp + ", key = " + eventKey);

		}

		//构建factory-key字符串，作为缓存Key值
		String factoryKey = event.getApplication() + "." + event.getKey();
		
		boolean dynamic = false;
		//取得是否动态创建
		try {
			dynamic = this.getEventPropertyHandler().isDynamic();
		} catch (EventPropertyException e) {
			throw new EventException(e.getMessage(), e);
		}

		if (dynamic) {
			//如果是动态创建则直接创建
			factory = createEventListenerFactory(event);
		} else {
			//如果是非动态创建则直接从缓存中取，如果缓存中不存在，再重新创建
			synchronized (this.factories) {
				factory = (EventListenerFactory) this.factories.get(factoryKey);
				if (factory == null) {
					factory = createEventListenerFactory(event);
					//放入缓存
					this.factories.put(factoryKey, factory);
				}
			}
		}
		return factory;
	}

	/**
	 * 根据Event创建EventListenerFactory,需要Event提供application和event-key
	 * 
	 * @param event Event
	 * @return EventListenerFactory
	 * @throws EventException
	 */
	private EventListenerFactory createEventListenerFactory(Event event)
			throws EventException {
		String factoryName = null;
		Object factoryObject = null;
		EventListenerFactory factory = null;
		EventListenerFactoryParam[] params = null;

		//取得工厂类名称
		try {
			factoryName = this.getEventPropertyHandler()
					.getEventListenerFactoryName(event.getApplication(),
							event.getKey());
		} catch (EventPropertyException e) {
			throw new EventException(e.getMessage(), e);
		}
		
		//创建工程实例
		try {
			factoryObject = Class.forName(factoryName).newInstance();
		} catch (Exception e) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.event.i18n").getString(
						"EventManager.FailedToCreateFactory");
			} catch (MissingResourceException ex) {

			}

			throw new EventException(message + " : factory class = "
					+ factoryName + ", application = " + event.getApplication()
					+ ", key = " + event.getKey(), e);

		}
		
		//进行强制转换
		if (factoryObject instanceof EventListenerFactory) {
			factory = (EventListenerFactory) factoryObject;
		} else {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.event.i18n").getString(
						"EventManager.FactoryImplemented");
			} catch (MissingResourceException ex) {

			}

			throw new EventException(message + " : factory class = "
					+ factoryName + ", application = " + event.getApplication()
					+ ", key = " + event.getKey());

		}

		//取得工厂参数数据
		try {
			params = this.getEventPropertyHandler()
					.getEventListenerFactoryParams(event.getApplication(),
							event.getKey());
		} catch (EventPropertyException e) {
			throw new EventException(e.getMessage(), e);
		}
		
		//遍历工厂参数数组，设置工厂参数值
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				try {
					//对监听工厂进行初始化操作
					factory.initParam(params[i].getName(), params[i].getValue());
				} catch (EventListenerException e) {
					throw new EventException(e.getMessage(), e);
				}
			}
		}
		
		//返回
		return factory;
	}

}
