package com.ladybug.framework.base.service;

import java.util.Locale;

import com.ladybug.framework.system.property.PropertyHandler;

/**
 * 服务属性处理器接口
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public interface ServicePropertyHandler extends PropertyHandler {

	/**
	 * 默认的异常属性字符串
	 */
	public static final String DEFAULT_EXCPETION_ATTRIBUTE = "exception";
	
	/**
	 *  默认的编码属性字符串
	 */
	public static final String DEFAULT_ENCODING_ATTRIBUTE = "com.ladybug.framework.base.service.encoding";
	
	/**
	 * 默认的Locale属性字符串
	 */
	public static final String DEFAULT_LOCALE_ATTRIBUTE = "com.ladybug.framework.base.service.locale";

	/**
	 * 是否为动态创建
	 * 
	 * @return
	 * @throws ServicePropertyException
	 */
	public boolean isDynamic() throws ServicePropertyException;

	/**
	 * 取得客户端编码
	 * 
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getClientEncoding() throws ServicePropertyException;

	/**
	 * 取得编码属性名称
	 * 
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getEncodingAttributeName() throws ServicePropertyException;

	/**
	 * 取得客户端Locale
	 * 
	 * @return
	 * @throws ServicePropertyException
	 */
	public Locale getClientLocale() throws ServicePropertyException;

	/**
	 * 取得Locale属性名称
	 * 
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getLocaleAttributeName() throws ServicePropertyException;

	/**
	 * 取得转出页面路径
	 * 
	 * @param application 应用
	 * @param service 服务
	 * @param locale Locale
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getNextPagePath(String application, String service,
			Locale locale) throws ServicePropertyException;

	/**
	 * 取得转出页面路径
	 * 
	 * @param application 应用
	 * @param service 服务
	 * @param key 缓存Key
	 * @param locale Locale
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getNextPagePath(String application, String service,
			String key, Locale locale)
			throws ServicePropertyException;

	/**
	 * 取得输入错误页面路径
	 * 
	 * @param application 应用
	 * @param service 服务
	 * @param locale Locale
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getInputErrorPagePath(String application,
			String service, Locale locale)
			throws ServicePropertyException;

	/**
	 * 取得输入错误页面路径
	 * 
	 * @param application 应用
	 * @param service 服务
	 * @param key 缓存key
	 * @param locale Locale
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getInputErrorPagePath(String application,
			String service, String key, Locale locale)
			throws ServicePropertyException;

	/**
	 * 取得输入错误页面路径
	 * 
	 * @param application 应用
	 * @param locale Locale
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getInputErrorPagePath(String application, Locale locale)
			throws ServicePropertyException;

	/**
	 * 取得输入错误页面路径
	 * 
	 * @param locale Locale
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getInputErrorPagePath(Locale locale)
			throws ServicePropertyException;

	/**
	 * 取得服务错误页面路径
	 * 
	 * @param application 应用
	 * @param service 服务
	 * @param locale Locale
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getServiceErrorPagePath(String application,
			String service, Locale locale)
			throws ServicePropertyException;

	/**
	 * 取得服务错误页面路径
	 * 
	 * @param application 应用
	 * @param service 服务
	 * @param key 缓存key值
	 * @param locale Locale
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getServiceErrorPagePath(String application,
			String service, String key, Locale locale)
			throws ServicePropertyException;

	/**
	 * 取得服务错误页面路径
	 * 
	 * @param application 应用
	 * @param locale Locale
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getServiceErrorPagePath(String application, Locale locale)
			throws ServicePropertyException;

	/**
	 * 取得服务错误页面路径
	 * @param locale Locale
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getServiceErrorPagePath(Locale locale)
			throws ServicePropertyException;

	/**
	 * 取得页面错误页面路径
	 * 
	 * @param application 应用
	 * @param service 服务
	 * @param locale Locale
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getSystemErrorPagePath(String application,
			String service, Locale locale)
			throws ServicePropertyException;

	/**
	 * 取得系统错误页面路径
	 * 
	 * @param application 应用
	 * @param service 服务
	 * @param key 缓存Key值
	 * @param locale Locale
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getSystemErrorPagePath(String application,
			String service, String key, Locale locale)
			throws ServicePropertyException;

	/**
	 * 取得系统错误页面路径
	 * 
	 * @param application 应用
	 * @param locale Locale
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getSystemErrorPagePath(String application, Locale locale)
			throws ServicePropertyException;

	/**
	 * 取得系统错误页面路径
	 * 
	 * @param locale Locale
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getSystemErrorPagePath(Locale locale)
			throws ServicePropertyException;

	/**
	 * 取得服务控制器名称
	 * 
	 * @param application 应用
	 * @param service 服务
	 * @param locale Locale
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getServiceControllerName(String application,
			String service, Locale locale)
			throws ServicePropertyException;

	/**
	 * 取得转换器名称
	 * 
	 * @param application 应用
	 * @param service 服务
	 * @param locale Locale
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getTransitionName(String application, String service,
			Locale locale) throws ServicePropertyException;

	/**
	 * 取得异常属性名称
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getExceptionAttributeName() throws ServicePropertyException;

}
