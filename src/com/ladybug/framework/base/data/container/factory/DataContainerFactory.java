package com.ladybug.framework.base.data.container.factory;

import com.ladybug.framework.system.container.Container;
import com.ladybug.framework.system.container.factory.ContainerFactory;
import com.ladybug.framework.system.container.factory.Provider;
import com.ladybug.framework.system.exception.ContainerException;

/**
 * 数据容器创建工厂，内聚Provider(DefaultDataProvider),主要功能如下：<br>
 * 1）创建容器HTContainer<br>
 * 2）设置Provider<br>
 * 3）取得Provider<br>
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 */
public class DataContainerFactory implements ContainerFactory {

	protected  Provider provider = new DefaultDataProvider();

	@Override
	public Container create() throws ContainerException {
		return getProvider().create();
	}

	@Override
	public void setProvider(Provider paramProvider) {
		this.provider = paramProvider;
	}

	@Override
	public Provider getProvider() {
		return this.provider;
	}

}
