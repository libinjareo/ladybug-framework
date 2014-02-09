package com.ladybug.framework.base.data.container.factory;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.ladybug.framework.base.data.container.DataContainer;
import com.ladybug.framework.base.data.container.DataContainerImpl;
import com.ladybug.framework.system.container.Container;
import com.ladybug.framework.system.container.factory.Provider;
import com.ladybug.framework.system.exception.ContainerException;

/**
 * 默认的数据容器供应者
 * 
 * @author james.li(libinjareo@163.com)
 *
 */
public class DefaultDataProvider implements Provider {

	/**
	 * 默认的数据容器实现类
	 */
	private Class<?> defaultContainer = DataContainerImpl.class;
	/**
	 * 数据容器实现类，默认为DataContainerImpl.class
	 */
	private Class<?> containerClass = this.defaultContainer;

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

	/**
	 * 默认的构造函数，主要功能：<br>
	 * 1）读取Container配置文件<br>
	 * 2) 设置容器实现类
	 */
	public DefaultDataProvider() {
		try {
			//读取htContainer配置
			ResourceBundle bundle = ResourceBundle.getBundle("Container");
			//设置容器实现类
			setContainerClass(Class.forName(bundle.getString("dataContainer")));
		} catch (Exception ignor) {

		}
	}

	@Override
	public Container create() throws ContainerException {
		String message = null;
		try {
			//创建数据容器实例
			DataContainer container = (DataContainer) getContainerClass()
					.newInstance();
			
			//初始化容器，主要是取得DataPropertyHandler
			container.init();
			
			return container;
		} catch (Exception e) {
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.data.i18n").getString(
						"DataContainer.FailedToCreateDataContainer");
			} catch (MissingResourceException ex) {

			}

			throw new ContainerException(message, e);
		}

	}

}
