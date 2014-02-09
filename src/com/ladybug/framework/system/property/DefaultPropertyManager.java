package com.ladybug.framework.system.property;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * 默认的属性管理器，对应解析文件PropertyConfig.properties
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 */
public class DefaultPropertyManager extends PropertyManager {

	public static final String KEY = "com.ladybug.framework.system.property.DefaultPropertyManager";
	public static final String DEFAULT_BUNDLE_NAME = "/PropertyConfig.properties";
	
	/**
	 * 资源句柄
	 */
	private ResourceBundle bundle;

	/**
	 * 默认构造函数，同时初始化ResourceBundle
	 * @throws PropertyManagerException
	 */
	public DefaultPropertyManager() throws PropertyManagerException {
		//其中/表示相对于web项目classes目录下  
		String bundleName = System
				.getProperty(
						"com.ladybug.framework.system.property.DefaultPropertyManager",
						"/PropertyConfig.properties");

		try {
			bundle = new PropertyResourceBundle(this.getClass()
					.getResourceAsStream(bundleName));
		} catch (Exception e) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.huateng.web.framework.system.property.i18n")
						.getString(
								"DefaultPropertyManager.FailedToCreateManager");
			} catch (MissingResourceException ex) {
			}
			throw new PropertyManagerException(message
					+ " : resource bundle = " + bundleName, e);
		}
	}

	@Override
	protected String getPropertyHandlerName(String key)
			throws PropertyHandlerException {
		String keyName = key + ".class";
		String result;

		try {
			result = this.bundle.getString(keyName);
		} catch (Exception e) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.system.property.i18n")
						.getString("DefaultPropertyManager.NoSuchKey");
			} catch (MissingResourceException ex) {
			}
			throw new PropertyHandlerException(message + " : key = " + keyName,
					e);
		}
		return result;
	}

	@Override
	protected PropertyHandlerParam[] getPropertyHandlerParams(String key)
			throws PropertyHandlerException {

		Enumeration<String> keys = this.bundle.getKeys();
		String prefix = key + ".param.";

		Vector<PropertyHandlerParam> params = new Vector<PropertyHandlerParam>();

		if (keys != null) {
			while (keys.hasMoreElements()) {
				String currentKey = (String) keys.nextElement();
				if (currentKey.startsWith(prefix)) {
					PropertyHandlerParam param = new PropertyHandlerParam();
					param.setName(currentKey.substring(prefix.length()));

					try {
						param.setValue(this.bundle.getString(currentKey));
					} catch (MissingResourceException e) {
						param.setValue("");
					}
					params.add(param);

				}
			}
		}

		PropertyHandlerParam[] result = new PropertyHandlerParam[params.size()];
		for (int i = 0; i < params.size(); i++) {
			result[i] = (PropertyHandlerParam) params.elementAt(i);
		}
		return result;
	}
	
	
	
	public static void main(String[] args) throws PropertyManagerException{
		DefaultPropertyManager dpm = new DefaultPropertyManager();
		try {
			String propertyHandlerName = dpm.getPropertyHandlerName("aaaaa");
			System.out.println("propertyHandlerName:" + propertyHandlerName);
			PropertyHandlerParam[] params = dpm.getPropertyHandlerParams("aaaaa");
			for(int i = 0; i < params.length; i++){
				System.out.println("params name:"+params[i].getName());
				System.out.println("params value:"+params[i].getValue());
			}
		} catch (PropertyHandlerException e) {
			
			System.out.println(e.getMessage());
		}
		
	}

}
