package com.ladybug.framework.base.service.container.factory;

import com.ladybug.framework.system.container.Container;
import com.ladybug.framework.system.container.factory.ContainerFactory;
import com.ladybug.framework.system.container.factory.Provider;
import com.ladybug.framework.system.exception.ContainerException;

/**
 * 服务容器创建工厂
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class ServiceContainerFactory implements ContainerFactory {

	/**
	 * 容器供应者
	 */
	protected Provider provider = new DefaultServiceProvider();

	@Override
	public Container create() throws ContainerException {
		return getProvider().create();
	}

	@Override
	public Provider getProvider() {
		return this.provider;
	}

	@Override
	public void setProvider(Provider paramProvider) {
		this.provider = paramProvider;
	}

}
