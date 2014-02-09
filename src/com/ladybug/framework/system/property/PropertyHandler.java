package com.ladybug.framework.system.property;

/**
 * 属性处理器接口,只提供初始化方法
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public interface PropertyHandler {

	/**
	 * 通过属性参数数组进行初始化
	 * 
	 * @param paramArrayOfPropertyParam 属性参数数组
	 * @throws PropertyHandlerException 属性处理器异常
	 */
	public void init(PropertyParam[] paramArrayOfPropertyParam)
			throws PropertyHandlerException;
}
