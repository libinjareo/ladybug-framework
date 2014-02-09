package com.ladybug.framework.base.event.container.factory;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.ladybug.framework.base.event.container.EventContainer;
import com.ladybug.framework.base.event.container.EventContainerImpl;
import com.ladybug.framework.system.container.Container;
import com.ladybug.framework.system.container.factory.Provider;
import com.ladybug.framework.system.exception.ContainerException;

/**
 * 默认的Event提供者，主要方法为create()
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 * 
 */
public class DefaultEventProvider implements Provider {

	/**
	 * 默认事件容器实例
	 */
	private Class<?> defaultContainer = EventContainerImpl.class;

	/**
	 * 默认事件容器实现类
	 */
	private Class<?> containerClass = defaultContainer;

	/**
	 * 默认构造函数，解析htContainer配置文件，根据eventContainer键值读取事件容器实现类
	 */
	public DefaultEventProvider() {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("Container");
			setContainerClass(Class.forName(bundle.getString("eventContainer")));
		} catch (Exception ignor) {

		}

	}

	@Override
	public Container create() throws ContainerException {
		String message = null;
		try {
			// 创建事件容器实例
			EventContainer container = (EventContainer) getContainerClass()
					.newInstance();
			// 初始化容器
			container.init();
			return container;
		} catch (Exception e) {
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.event.i18n").getString(
						"EventContainer.FailedToCreateEventContainer");
			} catch (MissingResourceException ex) {

			}

			throw new ContainerException(message, e);
		}

	}

	/**
	 * 取得容器实现类
	 * 
	 * @return
	 */
	public Class<?> getContainerClass() {
		return containerClass;
	}

	/**
	 * 设置容器实现类
	 * 
	 * @param containerClass
	 */
	public void setContainerClass(Class<?> containerClass) {
		this.containerClass = containerClass;
	}

}
