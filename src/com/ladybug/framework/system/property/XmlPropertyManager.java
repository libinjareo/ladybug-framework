package com.ladybug.framework.system.property;

import java.io.IOException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * XML配置属性管理器<br>
 * 关联PropertyConfigModel
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class XmlPropertyManager extends PropertyManager {

	public static final String KEY = "com.ladybug.framework.system.property.XmlPropertyManager";
	
	/**
	 * 默认的句柄名称
	 */
	public static final String DEFAULT_BUNDLE_NAME = "property-config.xml";
	private PropertyConfigModel config;

	/**
	 * 默认构造函数,同时初始化PropertyConfigModel
	 * 
	 * @throws PropertyManagerException
	 */
	public XmlPropertyManager() throws PropertyManagerException {
		
		//此处为相对于web项目的class路径下
		String bundleName = System.getProperty(
				"com.ladybug.framework.system.property.XmlPropertyManager",
				"property-config.xml");
		if (bundleName == null) {
			bundleName = "property-config.xml";
		}
		
//		//如果是非web环境，则取类路径下的文件绝对路径
//		if(DEFAULT_BUNDLE_NAME.equals(bundleName)){
//			//读单域的web服务器中的配置文件路径
//			bundleName = XmlPropertyManager.class.getResource("/").getPath() + "property-config.xml";
//		}
		
		
		//解析配置文件取得属性配置模型
		this.config = getPropertyConfigModel(bundleName);
	}

	@Override
	protected String getPropertyHandlerName(String key)
			throws PropertyHandlerException {
		String result;
		try {
			result = this.config.getPropertyHandlerName(key);
			if (result == null)
				throw new PropertyHandlerException();

		} catch (Exception e) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.system.property.i18n")
						.getString("DefaultPropertyManager.NoSuchKey");
			} catch (MissingResourceException ex) {

			}
			throw new PropertyHandlerException(message + " : key = " + key);
		}
		return result;
	}

	@Override
	protected PropertyHandlerParam[] getPropertyHandlerParams(String key)
			throws PropertyHandlerException {
		PropertyHandlerParam[] result = this.config
				.getPropertyParams(key);
		return result;
	}

	/**
	 * 解析配置文件，取得PropertyConfigModel
	 * @param bundleName
	 * @return
	 * @throws PropertyManagerException
	 */
	private PropertyConfigModel getPropertyConfigModel(String bundleName)
			throws PropertyManagerException {

		try {
			PropertyConfigModelProducer producer = new PropertyConfigModelProducer();
			PropertyConfigModel model = producer.createPropertyConfigModel(bundleName);
			return model;
		} catch (IllegalArgumentException e) {
			throw new PropertyManagerException(e.getMessage(), e);
		} catch (ParserConfigurationException e) {
			throw new PropertyManagerException(e.getMessage(), e);
		} catch (SAXException e) {
			throw new PropertyManagerException(e.getMessage(), e);
		} catch (IOException e) {
			throw new PropertyManagerException(e.getMessage(), e);
		} catch (Exception e) {
			throw new PropertyManagerException(e.getMessage(), e);
		}

	}
	
	
	
	
	public static void main(String[] args) throws Exception{
		XmlPropertyManager xpm = new XmlPropertyManager();
		String handlerName = xpm.getPropertyHandlerName("service");
		System.out.println("handler-class: "+handlerName);
		
		PropertyHandlerParam[] params = xpm.getPropertyHandlerParams("service");
		for(int i = 0; i < params.length; i++){
			System.out.println("param-name: " + params[i].getName());
			System.out.println("param-value: " + params[i].getValue());
			
		}
	}
}
