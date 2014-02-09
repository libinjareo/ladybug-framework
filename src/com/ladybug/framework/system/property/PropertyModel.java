package com.ladybug.framework.system.property;

/**
 * 属性配置模型
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class PropertyModel {
	
	/**
	 * 属性处理器类名称
	 */
	private String propertyHandlerName = null;
	
	/**
	 *  属性处理配置参数数组
	 */
	private PropertyHandlerParam[] params = null;

	/**
	 *  属性参数配置标签
	 */
	static final String INIT_ID = "init-param";
	
	/**
	 * 参数名称标签名称
	 */
	static final String PARAM_NAME_ID = "param-name";
	
	/**
	 * 参数名称标签值
	 */
	static final String PARAM_VALUE_ID = "param-value";

	/**
	 * 取得属性处理器类名称
	 * @return
	 */
	public String getPropertyHandlerName() {
		return propertyHandlerName;
	}

	/**
	 * 设置属性处理器类名称
	 * @param propertyHandlerName
	 */
	public void setPropertyHandlerName(String propertyHandlerName) {
		this.propertyHandlerName = propertyHandlerName;
	}

	/**
	 * 取得属性处理配置参数数组
	 * @return
	 */
	public PropertyHandlerParam[] getParams() {
		return params;
	}

	/**
	 * 设置属性处理配置参数数组
	 * @param params
	 */
	public void setParams(PropertyHandlerParam[] params) {
		this.params = params;
	}

}
