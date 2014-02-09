package com.ladybug.framework.system.container.factory;

import com.ladybug.framework.system.container.Container;
import com.ladybug.framework.system.exception.ContainerException;

/**
 * 容器供应者接口，只提供创建方法create()
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 */
public interface Provider {

	/**
	 * 创建容器
	 * @return HTContainer实现
	 * @throws HTContainerException
	 */
	public Container create() throws ContainerException;
}
