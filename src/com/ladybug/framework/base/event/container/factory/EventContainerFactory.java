package com.ladybug.framework.base.event.container.factory;

import com.ladybug.framework.system.container.Container;
import com.ladybug.framework.system.container.factory.ContainerFactory;
import com.ladybug.framework.system.container.factory.Provider;
import com.ladybug.framework.system.exception.ContainerException;

/**
 * 事件容器工厂,主要提供如下功能：<br>
 * 1)HTContainer create()<br>
 * 2)getProvider()<br>
 * 3)setProvider
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class EventContainerFactory implements ContainerFactory {

	protected static Provider provider = new DefaultEventProvider();
	
	@Override
	public Container create() throws ContainerException {
		return getProvider().create();
	}

	@Override
	public Provider getProvider() {
		return provider;
	}

	@Override
	public void setProvider(Provider paramProvider) {
		provider = paramProvider;
	}

}
