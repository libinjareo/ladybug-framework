package com.ladybug.framework.base.event;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 标准事件监听工厂
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class StandardEventListenerFactory implements EventListenerFactory {

	/**
	 * 默认的工厂参数名称，默认为listener
	 */
	@SuppressWarnings("unused")
	private static final String PARAM_LISTENER = "listener";
	
	/**
	 * 监听类名称
	 */
	private String listenerName;

	@Override
	public EventListener create(Event event) throws EventListenerException {
		//监听对象
		Object listenerObject = null;
		
		//标准事件监听对象
		StandardEventListener listener = null;

		try {
			//创建标准事件监听对象
			listenerObject = Class.forName(listenerName).newInstance();
		} catch (Exception e) {
			//打印错误信息
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.event.i18n").getString(
						"Common.FailedToCreateEventListener");
			} catch (MissingResourceException ex) {

			}
			//打印事件为空异常信息
			if (event == null) {
				throw new EventListenerException(message + " : listener = "
						+ this.listenerName + ", event is null", e);
			}
			
			//抛出EventListenerException
			throw new EventListenerException(message + " : listener = "
					+ this.listenerName + ", application = "
					+ event.getApplication() + ", key = " + event.getKey(), e);

		}
		//进行强制转换
		if (listenerObject instanceof StandardEventListener) {
			listener = (StandardEventListener) listenerObject;
		} else {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.event.i18n").getString(
						"Common.EventListenerExtended");
			} catch (MissingResourceException e) {

			}
			if (event == null) {
				throw new EventListenerException(message + " : listener = "
						+ this.listenerName + ", event is null");
			}
			throw new EventListenerException(message + " : listener = "
					+ this.listenerName + ", application = "
					+ event.getApplication() + ", key = " + event.getKey());

		}
		return listener;
	}

	@Override
	public void initParam(String name, String value)
			throws EventListenerException {
		//赋值listener参数值
		if ((name != null) && ("listener").equals(name)) {
			listenerName = value;
		}
	}

}
