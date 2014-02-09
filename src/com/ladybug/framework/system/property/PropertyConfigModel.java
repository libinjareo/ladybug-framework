package com.ladybug.framework.system.property;

import java.util.Map;

/**
 * 属性配置模型，内置缓存，对应于配置文件property-config.xml或者是PropertyConfig.properties,共有以下配置属性：<br>
 * 1)service<br>
 * 2)event<br>
 * 3)data<br>
 * 4)log
 * <br>
 * 主要有以下功能：
 * 1)getPropertyHandlerName
 * 2)PropertyHandlerParam[] getPropertyParams
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class PropertyConfigModel {
	
	/**
	 * 配置主标签名称
	 */
	static final String ID = "property-config";
	static final String SERVICE_ID = "service";
	static final String EVENT_ID = "event";
	static final String DATA_ID = "data";
	static final String SESSION_ID = "session";
	static final String MESSAGE_ID = "message";
	static final String I18NMESSAGE_ID = "i18n_message";
	static final String LOG_ID = "log";
	
	/**
	 * 处理器类名称
	 */
	static final String HANDLER_ID = "handler-class";
	
	/**
	 * 属性模型缓存
	 */
	private Map<String,PropertyModel> map;

	/**
	 * 设置属性配置缓存
	 * 
	 * @param map
	 */
	protected void setProperties(Map<String,PropertyModel> map) {
		this.map = map;
	}

	/**
	 * 取得相应业务的属性处理器类名称
	 * @param key
	 * @return
	 */
	protected String getPropertyHandlerName(String key) {
		if (key == null) {
			return null;
		}
		//根据Key值，从缓存中取得PropertyModel
		PropertyModel prop = (PropertyModel) this.map.get(key);
		if (prop == null) {
			return null;
		}
		String name = prop.getPropertyHandlerName();
		return name;
	}

	/**
	 * 取得相应属性配置参数数组
	 * @param key
	 * @return
	 */
	protected PropertyHandlerParam[] getPropertyParams(String key) {
		if (key == null) {
			return null;
		}
		PropertyModel prop = (PropertyModel) this.map.get(key);
		if (prop == null) {
			return null;
		}
		//取得配置参数数组
		PropertyHandlerParam[] params = prop.getParams();
		return params;
	}
}
