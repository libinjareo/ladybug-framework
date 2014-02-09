package com.ladybug.framework.base.event;

import java.util.Collection;

/**
 * 事件组模型
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class EventGroupModel {
	public static final String ID = "event-group";
	public static final String P_ID_EVENT_KEY = "event-key";
	public static final String P_ID_EVENT_NAME = "event-class";
	public static final String P_ID_PRE_TRIGGER = "pre-trigger";
	public static final String P_ID_POST_TRIGGER = "post-trigger";
	public static final String P_ID_TRIGGER_CLASS = "trigger-class";

	/**
	 * 事件Key
	 */
	private String eventKey;
	
	/**
	 * 事件类名称
	 */
	private String eventName;

	/**
	 * 前置触发集合
	 */
	private Collection<EventTriggerInfo> preTriggerInfos;
	
	/**
	 * 响应触发集合
	 */
	private Collection<EventTriggerInfo> postTriggerInfos;
	
	/**
	 * 事件工厂模型
	 */
	private EventFactoryModel eventFactory;

	/**
	 * 取得事件Key
	 * @return
	 */
	public String getEventKey() {
		return eventKey;
	}

	/**
	 * 设置事件Key
	 * @param eventKey
	 */
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	/**
	 * 取得事件类名称
	 * @return
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * 设置事件类名称
	 * @param eventName
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * 取得前置触发集合
	 * @return
	 */
	public Collection<EventTriggerInfo> getPreTriggerInfos() {
		return preTriggerInfos;
	}

	/**
	 * 设置前置触发集合
	 * @param preTriggerInfos
	 */
	public void setPreTriggerInfos(Collection<EventTriggerInfo> preTriggerInfos) {
		this.preTriggerInfos = preTriggerInfos;
	}

	/**
	 * 取得响应触发集合
	 * @return
	 */
	public Collection<EventTriggerInfo> getPostTriggerInfos() {
		return postTriggerInfos;
	}

	/**
	 * 设置响应触发集合
	 * @param postTriggerInfos
	 */
	public void setPostTriggerInfos(Collection<EventTriggerInfo> postTriggerInfos) {
		this.postTriggerInfos = postTriggerInfos;
	}

	/**
	 * 取得事件工厂模型
	 * @return
	 */
	public EventFactoryModel getEventFactory() {
		return eventFactory;
	}

	/**
	 * 设置事件工厂模型
	 * @param eventFactory
	 */
	public void setEventFactory(EventFactoryModel eventFactory) {
		this.eventFactory = eventFactory;
	}

}
