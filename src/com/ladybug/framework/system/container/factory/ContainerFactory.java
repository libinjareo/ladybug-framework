package com.ladybug.framework.system.container.factory;

import com.ladybug.framework.system.container.Container;
import com.ladybug.framework.system.exception.ContainerException;

/**
 * 容器工厂接口
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public interface ContainerFactory {
	
	/**
	 * 创建容器，由Provider实现
	 * 
	 * @return HTContainer的实现 
	 * @throws HTContainerException 容器工厂异常
	 */
	public Container create() throws ContainerException;

	/**
	 * 提供供应者对象
	 * @param paramProvider Provider实例
	 */
	public void setProvider(Provider paramProvider);

	/**
	 * 返回供应者对象
	 * 
	 * @return Provider实例
	 */
	public Provider getProvider();

}
