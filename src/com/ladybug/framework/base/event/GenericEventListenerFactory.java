package com.ladybug.framework.base.event;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 通用事件监听工厂
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class GenericEventListenerFactory implements EventListenerFactory {

	/**
	 * 监听工厂参数名称,默认为listener
	 */
	@SuppressWarnings("unused")
	private static final String PARAM_LISTENER = "listener";
	
	/**
	 * 监听器类名称
	 */
	private String listenerName;

	/**
	 * 默认构造函数
	 */
	public GenericEventListenerFactory() {
		listenerName = null;
	}

	@Override
	public EventListener create(Event event) throws EventListenerException {
		GenericEventListener result = null;
		Object listenerObject = null;
		StandardEventListener listener = null;
		
		//初始化通用事件监听
		result = new GenericEventListener();

		if (this.listenerName == null) {
			String message = null;
			try {
				message = ResourceBundle
						.getBundle("com.ladybug.framework.base.event.i18n")
						.getString(
								"GenericEventListenerFactory.IncludedListenerNotDeclared");
			} catch (MissingResourceException ex) {

			}
			if (event == null) {
				throw new EventListenerException(message + ", event is null");
			}
			throw new EventListenerException(message + ", application = "
					+ event.getApplication() + ", key = " + event.getKey());
		}

		try {
			listenerObject = Class.forName(this.listenerName).newInstance();
		} catch (Exception e) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.event.i18n").getString(
						"Common.FailedToCreateEventListener");
			} catch (MissingResourceException ex) {

			}
			if (event == null) {
				throw new EventListenerException(message + " : listener = "
						+ this.listenerName + ", event is null", e);
			}
			throw new EventListenerException(message + " : listener = "
					+ this.listenerName + ", application = "
					+ event.getApplication() + ", key = " + event.getKey(), e);

		}

		if (listenerObject instanceof StandardEventListener) {
			listener = (StandardEventListener) listenerObject;
		} else {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.event.i18n").getString(
						"Common.EventListenerExtended");
			} catch (MissingResourceException ex) {

			}
			if (event == null) {
				throw new EventListenerException(message + " : listener = "
						+ this.listenerName + ", event is null");
			}
			throw new EventListenerException(message + " : listener = "
					+ this.listenerName + ", application = "
					+ event.getApplication() + ", key = " + event.getKey());

		}

		//设置标准事件监听对象
		result.setListener(listener);

		//返回GenericEventListener
		return result;
	}

	@Override
	public void initParam(String name, String value)
			throws EventListenerException {
		//设置listener参数值
		if ((name != null) && ("listener").equals(name)) {
			listenerName = value;
		}
	}

}
