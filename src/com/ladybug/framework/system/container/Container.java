package com.ladybug.framework.system.container;

import com.ladybug.framework.system.exception.ContainerException;


/**
 * 容器接口,只提供初始化方法init()
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 */
public interface Container {

	/**
	 * 初始化容器
	 * @throws HTContainerException 容器异常
	 */
	public void init() throws ContainerException;
}
