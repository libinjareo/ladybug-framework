package com.ladybug.framework.base.service;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.ladybug.framework.system.property.PropertyHandlerException;
import com.ladybug.framework.system.property.PropertyParam;

/**
 * 默认的服务属性处理器，用于解析ServiceConfig.properties文件,内置缓存
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class DefaultServicePropertyHandler implements ServicePropertyHandler {

	/**
	 * 默认的资源句柄名称
	 */
	public static final String DEFAULT_BUNDLE_NAME = "ServiceConfig";
	
	/**
	 * 默认的资源句柄名称参数
	 */
	public static final String DEFAULT_BUNDLE_NAME_PARAM = "bundle";

	/**
	 * 资源句柄前缀
	 */
	private String bundlePrefix;
	
	/**
	 * 应用资源句柄缓存,其结构为Map(application,appMap(locale,ResourceBundle))
	 */
	private Map<String,Map<Locale,ResourceBundle>> bundles = new HashMap<String,Map<Locale,ResourceBundle>>();
	
	/**
	 * 公共资源句柄缓存
	 */
	private Map<Locale,ResourceBundle> commonBundle = new HashMap<Locale,ResourceBundle>();

	/**
	 * 默认的构造函数
	 */
	public DefaultServicePropertyHandler() {
		setBundlePrefix(null);
	}

	/**
	 * 取得资源句柄前缀
	 * @return
	 */
	public String getBundlePrefix() {
		return bundlePrefix;
	}

	/**
	 * 设置资源句柄前缀
	 * 
	 * @param bundlePrefix
	 */
	public void setBundlePrefix(String bundlePrefix) {
		this.bundlePrefix = bundlePrefix;
	}

	/**
	 * 设置应用资源句柄缓存
	 * 
	 * @param applicationBundles
	 */
	@SuppressWarnings("unused")
	private void setApplicationBundles(Map<String,Map<Locale,ResourceBundle>> applicationBundles) {
		this.bundles = applicationBundles;
	}

	/**
	 * 取得应用资源句柄缓存
	 * @return
	 */
	@SuppressWarnings("unused")
	private Map<String,Map<Locale,ResourceBundle>> getApplicationBundles() {
		return this.bundles;
	}

	/**
	 * 设置公用资源句柄,以Locale为key值
	 * 
	 * @param commonBundle ResourceBundle
	 */
	@SuppressWarnings("unused")
	private void setCommonBundle(ResourceBundle commonBundle) {
		setCommonBundle(commonBundle, (Locale) null);
	}

	/**
	 * 设置公用资源句柄,以Locale为key值
	 * 
	 * @param commonBundle ResourceBundle
	 * @param locale Locale
	 */
	private void setCommonBundle(ResourceBundle commonBundle, Locale locale) {
		this.commonBundle.put(locale, commonBundle);
	}

	/**
	 * 设置应用资源句柄
	 * 
	 * @param application 应用，作为缓存Key值
	 * @param bundle 资源句柄
	 * @param locale Locale 
	 */
	private void setApplicationBundle(String application,
			ResourceBundle bundle, Locale locale) {
		//根据应用取得缓存Map
		Map<Locale,ResourceBundle> appMap = (Map<Locale,ResourceBundle>) this.bundles.get(application);
		//如果一级缓存中不存在,则首先放入到一级缓存 
		if (appMap == null) {
			appMap = new HashMap<Locale,ResourceBundle>();
			this.bundles.put(application, appMap);
		}
		//以Locale为Key存储资源句柄
		appMap.put(locale, bundle);
	}

	/**
	 * 根据application和Locale取得应用资源句柄
	 * @param application  应用字符串
	 * @param locale Locale
	 * @return
	 */
	private ResourceBundle getApplicationBundle(String application,
			Locale locale) {
		Map<Locale,ResourceBundle> appMap = (Map<Locale,ResourceBundle>) this.bundles.get(application);
		if (appMap == null) {
			return null;
		}
		return (ResourceBundle) appMap.get(locale);

	}

	/**
	 * 根据应用取得资源句柄
	 * 
	 * @param application 应用
	 * @return
	 * @throws ServicePropertyException
	 */
	@SuppressWarnings("unused")
	private ResourceBundle getResourceBundle(String application)
			throws ServicePropertyException {
		return getResourceBundle(application, (Locale) null);
	}

	/**
	 * 根据application和Locale取得资源句柄，首先从缓存中取，如果缓存中不存在，则创建句柄并放入缓存
	 * 
	 * @param application 应用
	 * @param locale Locale
	 * @return
	 * @throws ServicePropertyException
	 */
	private ResourceBundle getResourceBundle(String application, Locale locale)
			throws ServicePropertyException {
		ResourceBundle result;
		synchronized (this.bundles) {
			result = getApplicationBundle(application, locale);
			//如果缓存中不存在
			if (result == null) {
				//创建新的资源句柄
				result = createResourceBundle(application, locale);
				//放入缓存
				setApplicationBundle(application, result, locale);
			}
		}
		return result;
	}

	@SuppressWarnings("unused")
	private ResourceBundle getApplication(String application) {
		return getApplicationBundle(application, (Locale) null);
	}

	private ResourceBundle getCommonBundle(Locale locale)
			throws ServicePropertyException {
		Locale realLocale = locale;
		if (realLocale == null) {
			realLocale = Locale.getDefault();
		}
		ResourceBundle result;
		synchronized (this.commonBundle) {
			result = (ResourceBundle) this.commonBundle.get(realLocale);
			if (result == null) {
				result = createCommonBundle(realLocale);
				setCommonBundle(result, realLocale);
			}
		}
		return result;
	}

	private ResourceBundle createCommonBundle(Locale realLocale)
			throws ServicePropertyException {
		try {
			return ResourceBundle.getBundle(getBundlePrefix(), realLocale);
		} catch (MissingResourceException e) {
			throw new ServicePropertyException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unused")
	private ResourceBundle createResourceBundle(String application)
			throws ServicePropertyException {
		try {
			return ResourceBundle.getBundle(getPropertyPackage(application)
					+ getBundlePrefix() + "_" + getApplicationID(application));
		} catch (MissingResourceException e) {
			throw new ServicePropertyException(e.getMessage(), e);
		}

	}

	/**
	 * 根据application和Locale创建资源句柄
	 * 
	 * @param application 应用
	 * @param locale Locale
	 * @return
	 * @throws ServicePropertyException
	 */
	private ResourceBundle createResourceBundle(String application,
			Locale locale) throws ServicePropertyException {
		ResourceBundle bundle = null;
		try {
			if (locale == null) {
				bundle = ResourceBundle
						.getBundle(getPropertyPackage(application)
								+ getBundlePrefix() + "_"
								+ getApplicationID(application));
			} else {
				bundle = ResourceBundle.getBundle(
						getPropertyPackage(application) + getBundlePrefix()
								+ "_" + getApplicationID(application), locale);
			}
		} catch (MissingResourceException e) {
			throw new ServicePropertyException(e.getMessage(), e);
		}

		return bundle;
	}

	/**
	 * 取得应用包字符串
	 * 
	 * @param application
	 * @return
	 */
	private String getPropertyPackage(String application) {
		String[] paramAry = application.split("[.]");
		StringBuffer buf = new StringBuffer();
		if (paramAry.length > 1) {
			for (int i = 0; i < paramAry.length - 1; i++) {
				buf.append(paramAry[i]);
				buf.append(File.separator);
			}
		}

		return buf.toString();
	}

	/**
	 * 取得应用ID
	 * @param application
	 * @return
	 */
	private String getApplicationID(String application) {
		String[] paramAry = application.split("[.]");
		String id = paramAry[(paramAry.length - 1)];
		return id;
	}

	@Override
	public String getClientEncoding() throws ServicePropertyException {
		return ResourceBundleServicePropertyHandlerUtil
				.getClientEncoding(getCommonBundle(new Locale("", "")));
	}

	@Override
	public Locale getClientLocale() throws ServicePropertyException {
		return ResourceBundleServicePropertyHandlerUtil
				.getClientLocale(getCommonBundle(new Locale("", "")));
	}

	@Override
	public String getEncodingAttributeName() throws ServicePropertyException {
		return ResourceBundleServicePropertyHandlerUtil
				.getEncodingAttributeName(getCommonBundle(new Locale("", "")));
	}

	@Override
	public String getExceptionAttributeName() throws ServicePropertyException {
		return ResourceBundleServicePropertyHandlerUtil
				.getExceptionAttributeName(getCommonBundle(new Locale("", "")));
	}

	@Override
	public String getInputErrorPagePath(String application, String service,
			Locale locale) throws ServicePropertyException {
		return ResourceBundleServicePropertyHandlerUtil.getInputErrorPagePath(
				getCommonBundle(locale),
				getResourceBundle(application, locale), application, service);
	}

	@Override
	public String getInputErrorPagePath(String application, String service,
			String key, Locale locale) throws ServicePropertyException {
		return ResourceBundleServicePropertyHandlerUtil.getInputErrorPagePath(
				getCommonBundle(locale),
				getResourceBundle(application, locale), application, service,
				key);
	}

	@Override
	public String getInputErrorPagePath(String application, Locale locale)
			throws ServicePropertyException {
		return ResourceBundleServicePropertyHandlerUtil.getInputErrorPagePath(
				getCommonBundle(locale),
				getResourceBundle(application, locale), application);
	}

	@Override
	public String getInputErrorPagePath(Locale locale)
			throws ServicePropertyException {
		return ResourceBundleServicePropertyHandlerUtil
				.getInputErrorPagePath(getCommonBundle(locale));
	}

	@Override
	public String getLocaleAttributeName() throws ServicePropertyException {
		return ResourceBundleServicePropertyHandlerUtil
				.getLocaleAttributeName(getCommonBundle(new Locale("", "")));
	}

	@Override
	public String getNextPagePath(String application, String service,
			Locale locale) throws ServicePropertyException {
		return ResourceBundleServicePropertyHandlerUtil.getNextPagePath(
				getResourceBundle(application, locale), application, service);
	}

	@Override
	public String getNextPagePath(String application, String service,
			String key, Locale locale) throws ServicePropertyException {
		return ResourceBundleServicePropertyHandlerUtil.getNextPagePath(
				getResourceBundle(application, locale), application, service,
				key);
	}

	@Override
	public String getServiceControllerName(String application, String service,
			Locale locale) throws ServicePropertyException {
		return ResourceBundleServicePropertyHandlerUtil
				.getServiceControllerName(
						getResourceBundle(application, locale), application,
						service);
	}

	@Override
	public String getServiceErrorPagePath(String application, String service,
			Locale locale) throws ServicePropertyException {
		return ResourceBundleServicePropertyHandlerUtil
				.getServiceErrorPagePath(getCommonBundle(locale),
						getResourceBundle(application, locale), application,
						service);
	}

	@Override
	public String getServiceErrorPagePath(String application, String service,
			String key, Locale locale) throws ServicePropertyException {
		return ResourceBundleServicePropertyHandlerUtil
				.getServiceErrorPagePath(getCommonBundle(locale),
						getResourceBundle(application, locale), application,
						service, key);
	}

	@Override
	public String getServiceErrorPagePath(String application, Locale locale)
			throws ServicePropertyException {
		return ResourceBundleServicePropertyHandlerUtil
				.getServiceErrorPagePath(getCommonBundle(locale),
						getResourceBundle(application, locale), application);
	}

	@Override
	public String getServiceErrorPagePath(Locale locale)
			throws ServicePropertyException {
		return ResourceBundleServicePropertyHandlerUtil
				.getServiceErrorPagePath(getCommonBundle(locale));
	}

	@Override
	public String getSystemErrorPagePath(String application, String service,
			Locale locale) throws ServicePropertyException {
		return ResourceBundleServicePropertyHandlerUtil.getSystemErrorPagePath(
				getCommonBundle(locale),
				getResourceBundle(application, locale), application, service);
	}

	@Override
	public String getSystemErrorPagePath(String application, String service,
			String key, Locale locale) throws ServicePropertyException {
		return ResourceBundleServicePropertyHandlerUtil.getSystemErrorPagePath(
				getCommonBundle(locale),
				getResourceBundle(application, locale), application, service,
				key);
	}

	@Override
	public String getSystemErrorPagePath(String application, Locale locale)
			throws ServicePropertyException {
		return ResourceBundleServicePropertyHandlerUtil.getSystemErrorPagePath(
				getCommonBundle(locale),
				getResourceBundle(application, locale), application);
	}

	@Override
	public String getSystemErrorPagePath(Locale locale)
			throws ServicePropertyException {
		return ResourceBundleServicePropertyHandlerUtil
				.getSystemErrorPagePath(getCommonBundle(locale));
	}

	@Override
	public String getTransitionName(String application, String service,
			Locale locale) throws ServicePropertyException {
		return ResourceBundleServicePropertyHandlerUtil.getTransitionName(
				getResourceBundle(application, locale), application, service);
	}

	@Override
	public boolean isDynamic() throws ServicePropertyException {
		return false;
	}

	@Override
	public void init(PropertyParam[] params) throws PropertyHandlerException {
		String bundleName = null;
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				if ("bundle".equals(params[i].getName())) {
					bundleName = params[i].getValue();
				}
			}
		}

		if (bundleName == null) {
			bundleName = "ServiceConfig";
		}

		this.setBundlePrefix(bundleName);
	}

	public static void main(String[] args) {
		DefaultServicePropertyHandler dsp = new DefaultServicePropertyHandler();
		String application = "application.service.controller";
		System.out.println(dsp.getPropertyPackage(application));
		System.out.println(dsp.getApplicationID(application));
	}

}
