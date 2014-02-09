package com.ladybug.framework.base.event;

import java.util.Collection;

import com.ladybug.framework.system.property.PropertyHandler;

/**
 * 事件属性处理器
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public interface EventPropertyHandler extends PropertyHandler {

	/**
	 * 是否为动态创建
	 * @return
	 * @throws EventPropertyException
	 */
	public boolean isDynamic() throws EventPropertyException;

	/**
	 * 取得事件名称
	 * @param application 
	 * @param key
	 * @return
	 * @throws EventPropertyException
	 */
	public String getEventName(String application, String key)
			throws EventPropertyException;

	/**
	 * 取得事件监听工厂名称
	 * @param application
	 * @param key
	 * @return
	 * @throws EventPropertyException
	 */
	public String getEventListenerFactoryName(String application,
			String key) throws EventPropertyException;

	/**
	 * 取得事件监听工厂参数数组
	 * @param application
	 * @param key
	 * @return
	 * @throws EventPropertyException
	 */
	public EventListenerFactoryParam[] getEventListenerFactoryParams(
			String application, String key)
			throws EventPropertyException;

	/**
	 * 取得事件前置触发集合,集合中元素保持配置顺序
	 * @param application
	 * @param key
	 * @return
	 * @throws EventPropertyException
	 */
	public Collection<EventTriggerInfo> getEventTriggerInfos(String application,
			String key) throws EventPropertyException;

	
	/**
	 * 取得Post事件触发集合,集合中元素保持配置顺序
	 * @param application
	 * @param key
	 * @return
	 * @throws EventPropertyException
	 */
	public Collection<EventTriggerInfo> getPostEventTriggerInfos(String application,
			String key) throws EventPropertyException;
}
