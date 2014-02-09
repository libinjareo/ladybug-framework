package com.ladybug.framework.base.event;

/**
 * 事件工厂模型
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class EventFactoryModel {
	public static final String ID = "event-factory";
	public static final String P_ID_FACTORY_CLASS = "factory-class";
	public static final String P_ID_FACTORY_PARAM = "init-param";
	public static final String P_ID_PARAM_NAME = "param-name";
	public static final String P_ID_PARAM_VALUE = "param-value";

	/**
	 *  工厂实现类名称(包含路径)
	 */
	private String factoryName;
	
	/**
	 * 事件监听工厂参数数组
	 */
	private EventListenerFactoryParam[] factoryParams;

	/**
	 * 取得监听工厂名称
	 * @return
	 */
	public String getFactoryName() {
		return factoryName;
	}

	/**
	 * 设置监听工厂名称
	 * @param factoryName
	 */
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	/**
	 * 取得监听工厂参数数组
	 * @return
	 */
	public EventListenerFactoryParam[] getFactoryParams() {
		return factoryParams;
	}

	/**
	 * 设置监听工厂参数数组
	 * @param factoryParams
	 */
	public void setFactoryParams(EventListenerFactoryParam[] factoryParams) {
		this.factoryParams = factoryParams;
	}

}
