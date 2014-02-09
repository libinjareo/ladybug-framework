package com.ladybug.framework.base.event;

/**
 * 事件监听工厂接口，主要提供以下功能:<br>
 * 1)创建事件监听器
 * 2)初始化参数
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public interface EventListenerFactory {

	/**
	 * 创建事件监听对象
	 * @param paramEvent Event
	 * @return
	 * @throws EventListenerException
	 */
	public EventListener create(Event paramEvent) throws EventListenerException;

	/**
	 * 初始化参数
	 * @param name 参数名称
	 * @param value 参数值
	 * @throws EventListenerException
	 */
	public void initParam(String name, String value)
			throws EventListenerException;

}
