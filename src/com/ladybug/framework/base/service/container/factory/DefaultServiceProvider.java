package com.ladybug.framework.base.service.container.factory;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.ladybug.framework.base.service.container.ServiceContainer;
import com.ladybug.framework.base.service.container.ServiceContainerImpl;
import com.ladybug.framework.system.container.Container;
import com.ladybug.framework.system.container.factory.Provider;
import com.ladybug.framework.system.exception.ContainerException;

/**
 * 默认服务供应者
 * 
 * @author libinjareo@163.com
 * @version 1.0
 *
 */
public class DefaultServiceProvider implements Provider {

	/**
	 * 默认服务容器实现
	 */
	
	private Class<?> defaultContainer = ServiceContainerImpl.class;
	
	/**
	 * 默认服务容器实现类
	 */
	private Class<?> containerClass = this.defaultContainer;

	/**
	 * 构造方法
	 */
	public DefaultServiceProvider() {
		try {
			//取得htContainer.properties的资源句柄
			ResourceBundle bundle = ResourceBundle.getBundle("Container");
			//设置容器实现类,由serviceContainer属性设定
			setContainerClass(Class.forName(bundle
					.getString("serviceContainer")));
		} catch (Exception ignore) {

		}

	}

	@Override
	public Container create() throws ContainerException {
		try {
			//创建容器实现类实例
			ServiceContainer container = (ServiceContainer) getContainerClass()
					.newInstance();
			//初始化容器
			container.init();
			
			return container;
		} catch (Exception e) {
			String message = null;
			try {
				message = ResourceBundle
						.getBundle(
								"com.huateng.web.framework.base.service.i18n")
						.getString(
								"ServiceContainer.FailedToCreateServiceContainer");
			} catch (MissingResourceException ex) {

			}
			throw new ContainerException(message, e);
		}

	}

	/**
	 * 取得容器实现类
	 * @return
	 */
	public Class<?> getContainerClass() {
		return containerClass;
	}

	/**
	 * 设置容器实现类
	 * @param containerClass
	 */
	public void setContainerClass(Class<?> containerClass) {
		this.containerClass = containerClass;
	}

}
