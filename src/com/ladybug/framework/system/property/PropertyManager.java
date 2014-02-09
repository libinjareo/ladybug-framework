package com.ladybug.framework.system.property;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 属性管理抽象类，单例模式实现，主要有以下功能：<br>
 * 1)getPropertyHandler(key)
 * 2)getPropertyHandlerName
 * 3)PropertyHandlerParam[] getPropertyHandlerParams
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public abstract class PropertyManager {

	public static final String KEY = "com.ladybug.framework.system.property.PropertyManager";
	
	/**
	 * 默认的系统属性管理者
	 */
	public static final String DEFAULT_SYSTEM_MANAGER = "com.ladybug.framework.system.property.XmlPropertyManager";

	/**
	 * 静态私有实例
	 */
	private static PropertyManager manager;
	
	/**
	 * PropertyHandler缓存
	 */
	private Map<String,PropertyHandler> propertyHandlers;

	/**
	 * 默认的构造函数
	 */
	protected PropertyManager() {
		propertyHandlers = new HashMap<String,PropertyHandler>();
	}

	/**
	 * 取得属性管理器实例，默认为XmlPropertyManager实例
	 * 
	 * @return PropertyManager
	 * @throws PropertyManagerException
	 */
	public static synchronized PropertyManager getPropertyManager()
			throws PropertyManagerException {

		String className = null;

		if (manager == null) {
			try {
				className = System
						.getProperty("com.ladybug.framework.system.property.PropertyManager");
			} catch (Exception e) {
				String message = null;
				try {
					message = ResourceBundle.getBundle(
							"com.ladybug.framework.system.property.i18n")
							.getString("PropertyManager.FailedToGetClass");
				} catch (MissingResourceException ex) {
				}
				throw new PropertyManagerException(
						message
								+ " : System property = "
								+ "ccom.ladybug.framework.system.property.PropertyManager",
						e);
			}
			
			if (className == null) {
				className = "com.ladybug.framework.system.property.XmlPropertyManager";
			}

			try {
				//创建实例
				manager = (PropertyManager) Class.forName(className)
						.newInstance();

			} catch (Exception e) {
				String message = null;
				try {
					message = ResourceBundle.getBundle(
							"com.ladybug.framework.system.property.i18n")
							.getString("PropertyManager.FailedToCreateManager");
				} catch (MissingResourceException ex) {
				}
				e.printStackTrace();
				throw new PropertyManagerException(message + " : class name = "
						+ className, e);
			}

		}
		return manager;
	}

	/**
	 * 根据参数名称取得属性处理器类名称(包含路径)
	 * @param paramString
	 * @return
	 * @throws PropertyHandlerException
	 */
	protected abstract String getPropertyHandlerName(String paramString)
			throws PropertyHandlerException;

	/**
	 * 根据参数名称取得属性处理参数数组
	 * @param paramString
	 * @return PropertyHandlerParam[]
	 * @throws PropertyHandlerException
	 */
	protected abstract PropertyHandlerParam[] getPropertyHandlerParams(
			String paramString) throws PropertyHandlerException;

	/**
	 * 根据参数键值取得PropertyHandler,首先从缓存中取，如果缓存中不存在，则通过子类方法getPropertyHandlerName()
	 * 取得属性处理器类名称，然后创建实例并放入缓存
	 * 
	 * @param key
	 * @return PropertyHandler
	 * @throws PropertyHandlerException
	 */
	public PropertyHandler getPropertyHandler(String key)
			throws PropertyHandlerException {
		
		//首先从缓存中取得
		PropertyHandler result = (PropertyHandler) propertyHandlers.get(key);
		if (result == null) {
			//通过子类方法取得属性处理器类名称
			String className = getPropertyHandlerName(key);
			try {
				//创建实例
				result = (PropertyHandler) Class.forName(className)
						.newInstance();
			} catch (Exception e) {
				String message = null;
				try {
					message = ResourceBundle
							.getBundle(
									"com.ladybug.framework.system.property.i18n")
							.getString(
									"PropertyManager.FailedToGetPropertyHandler");
				} catch (MissingResourceException ex) {
				}
				throw new PropertyHandlerException(message + " : " + className,
						e);
			}
			
			//通过子类方法取得属性处理器参数
			PropertyHandlerParam[] params = getPropertyHandlerParams(key);
			
			//属性处理器初始化参数
			result.init(params);
			
			//根据key值放入缓存
			this.propertyHandlers.put(key, result);
		}
		
		return result;
	}

}
