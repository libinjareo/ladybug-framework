package com.ladybug.framework.base.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.ladybug.framework.system.property.PropertyHandlerException;
import com.ladybug.framework.system.property.PropertyParam;
import com.ladybug.framework.util.XMLDocumentProducer;

/**
 * 用于解析XML文件的服务属性处理器，解析的配置文件名称为service-config.xml和service-config-applicationID.xml<br>
 * 关联ServiceConfigModelProducer
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class XmlServicePropertyHandler implements ServicePropertyHandler {

	/**
	 * 默认句柄名称
	 */
	public static final String DEFAULT_BUNDLE_NAME = "service-config";
	
	/**
	 * 默认句柄名称参数
	 */
	public static final String DEFAULT_BUNDLE_NAME_PARAM = "bundle";
	
	/**
	 * 动态参数名称
	 */
	public static final String PARAM_DYNAMIC = "dynamic";

	/**
	 * xml配置文件名称前缀
	 */
	private String xmlPrefix;
	
	/**
	 * 标志是否动态创建
	 */
	private boolean dynamic;
	
	/**
	 * 应用服务模型缓存
	 */
	@SuppressWarnings("rawtypes")
	private Map models = new HashMap();
	
	/**
	 * 通用服务模型缓存
	 */
	@SuppressWarnings("rawtypes")
	private Map commonServices = new HashMap();
	
	/**
	 * 服务配置模型创建器
	 */
	@SuppressWarnings("unused")
	private ServiceConfigModelProducer producer;
	
	private final Locale END_LOCALE = new Locale("", "");

	/**
	 * 默认的构造函数,初始化ServiceConfigModelProducer
	 */
	public XmlServicePropertyHandler() {
		setXmlPrefix(null);
		this.producer = new ServiceConfigModelProducer();
	}

	/**
	 * 取得XML文件前缀
	 * @return
	 */
	private String getXmlPrefix() {
		return xmlPrefix;
	}

	/**
	 *  设置XML文件前缀
	 * @param xmlPrefix
	 */
	private void setXmlPrefix(String xmlPrefix) {
		this.xmlPrefix = xmlPrefix;
	}

	/**
	 * 设置应用模型缓存
	 * @param applicationModels
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	private void setApplicationModels( Map applicationModels) {
		this.models = applicationModels;
	}

	/**
	 * 设置应用模型缓存
	 * @param application 应用Key
	 * @param model
	 */
	@SuppressWarnings("unused")
	private void setApplicationServiceModel(String application,
			ServiceModel model) {
		setApplicationServiceModel(application, model, (Locale) null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setApplicationServiceModel(String application,
			ServiceModel model, Locale locale) {
		Map appMap = (Map) this.models.get(locale);
		if (appMap == null) {
			appMap = new HashMap();
			this.models.put(locale, appMap);
		}
		appMap.put(application, model);
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	private Map getModels() {
		return this.models;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	private void setCommonServiceModel(Locale key,
			CommonServiceModel commonService) {
		this.commonServices.put(key, commonService);
	}

	private CommonServiceModel getCommonServiceModel()
			throws ServicePropertyException {
		return getCommonServiceModel(new Locale("", ""));
	}

	private CommonServiceModel getCommonServiceModel(Locale locale)
			throws ServicePropertyException {
		synchronized (this.commonServices) {
			CommonServiceModel model = _getCommonServiceModel(locale);
			if (model == null) {
				String message = null;
				try {
					message = ResourceBundle.getBundle(
							"com.huateng.web.framework.base.service.i18n")
							.getString("Xml.FileNotFound");
				} catch (MissingResourceException ex) {

				}

				throw new ServicePropertyException(message);
			}

			return model;
		}

	}

	@SuppressWarnings("unchecked")
	private CommonServiceModel _getCommonServiceModel(Locale locale)
			throws ServicePropertyException {

		CommonServiceModel commonService = null;
		if (!isDynamic()) {
			commonService = (CommonServiceModel) this.commonServices
					.get(locale);
		}
		if (commonService == null) {
			if (checkCommonServiceFileExist(locale)) {
				commonService = createCommonServiceModel(locale);

				if (!locale.equals(this.END_LOCALE)) {
					Locale parentLocale = getParentLocale(locale);
					CommonServiceModel parent = _getCommonServiceModel(parentLocale);
					commonService.setParent(parent);
				}
				this.commonServices.put(locale, commonService);
			} else if (!locale.equals(this.END_LOCALE)) {
				Locale parentLocale = getParentLocale(locale);
				commonService = _getCommonServiceModel(parentLocale);
			} else {
				return null;
			}
		}
		return commonService;
	}

	private Locale getParentLocale(Locale locale) {
		Locale newloc;

		if (locale == null) {
			newloc = new Locale("", "");
		} else {
			if ("".equals(locale.getVariant())) {
				if ("".equals(locale.getCountry())) {
					newloc = new Locale("", "");
				} else {
					newloc = new Locale(locale.getLanguage(), "", "");
				}
			} else {
				newloc = new Locale(locale.getLanguage(), locale.getCountry(),
						"");
			}
		}
		return newloc;
	}

	private CommonServiceModel createCommonServiceModel(Locale locale)
			throws ServicePropertyException {

		try {
			CommonServiceModelProducer producer = new CommonServiceModelProducer();
			String fileName = XMLDocumentProducer.getFileName(this
					.getXmlPrefix(), locale);
			CommonServiceModel commonModel = producer
					.createCommonServiceModel(fileName);

			return commonModel;
		} catch (ParserConfigurationException e) {
			throw new ServicePropertyException(e.getMessage(), e);
		} catch (SAXException e) {
			throw new ServicePropertyException(e.getMessage(), e);
		} catch (IOException e) {
			throw new ServicePropertyException(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			throw new ServicePropertyException(e.getMessage(), e);
		}

	}

	private boolean checkCommonServiceFileExist(Locale locale) {

		return XMLDocumentProducer.isFileExist(getXmlPrefix(), locale);
	}

	@SuppressWarnings("unused")
	private ServiceConfigModel getServiceConfigModel(String application)
			throws ServicePropertyException {
		return getServiceConfigModel(application, new Locale("", ""));
	}

	private ServiceConfigModel getServiceConfigModel(String application,
			Locale locale) throws ServicePropertyException {
		synchronized (this.models) {
			ServiceConfigModel model = _getServiceConfigModel(application,
					locale);
			return model;
		}

	}

	/**
	 * 根据服务参数和Locale创建ServiceConfigModel
	 * 
	 * @param application
	 * @param locale
	 * @return
	 * @throws ServicePropertyException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ServiceConfigModel _getServiceConfigModel(String application,
			Locale locale) throws ServicePropertyException {
		ServiceConfigModel result = null;
		Map appMaps = (HashMap) this.models.get(locale);
		//如果缓存中不存在
		if (appMaps == null) {
			appMaps = new HashMap();
			this.models.put(locale, appMaps);
		}

		//如果非主动创建
		if (!this.isDynamic()) {
			result = (ServiceConfigModel) appMaps.get(application);
		}

		if (result == null) {
			//检查文件是否存在
			if (checkServiceFile(application, locale)) {
				//创建模型
				result = createServiceConfigModel(application, locale);
				if ((locale == null) || (!locale.equals(this.END_LOCALE))) {
					locale = getParentLocale(locale);
					ServiceConfigModel parent = _getServiceConfigModel(
							application, locale);
					result.setParent(parent);
				}
				appMaps.put(application, result);
			} else if ((locale == null) || (!locale.equals(this.END_LOCALE))) {
				locale = getParentLocale(locale);
				result = _getServiceConfigModel(application, locale);
			} else {
				return null;
			}

		}
		return result;
	}

	/**
	 * 根据application和Loale从创建服务配置模型
	 * @param application
	 * @param locale Locale 1.0版本暂且不用
	 * @return
	 * @throws ServicePropertyException
	 */
	private ServiceConfigModel createServiceConfigModel(String application,
			Locale locale) throws ServicePropertyException {
		ServiceConfigModel model = null;
		String fn = this.getPropertyPackage(application) + this.getXmlPrefix()
				+ "-" + this.getApplicationID(application);
		//取得应用配置文件名称
		String fileName = XMLDocumentProducer.getFileName(fn, locale);

		ServiceConfigModelProducer producer = new ServiceConfigModelProducer();

		try {
			model = producer.createServiceConfigModel(fileName);
		} catch (IllegalArgumentException e) {
			throw new ServicePropertyException(e.getMessage(), e);
		} catch (ParserConfigurationException e) {
			throw new ServicePropertyException(e.getMessage(), e);
		} catch (SAXException e) {
			throw new ServicePropertyException(e.getMessage(), e);
		} catch (IOException e) {
			throw new ServicePropertyException(e.getMessage(), e);
		}
		return model;
	}

	/**
	 * 检查应用配置文件是否存在
	 * @param application
	 * @param locale
	 * @return
	 */
	private boolean checkServiceFile(String application, Locale locale) {
		String fileName = getPropertyPackage(application) + this.getXmlPrefix()
				+ "-" + getApplicationID(application);
		return XMLDocumentProducer.isFileExist(fileName, locale);
	}

	private String getApplicationID(String application) {
		String[] paramAry = application.split("[.]");
		String id = paramAry[(paramAry.length - 1)];
		return id;
	}

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

	@Override
	public String getClientEncoding() throws ServicePropertyException {
		CommonServiceModel model = getCommonServiceModel();
		String encoding = model.getClientEncoding();
		return encoding;
	}

	@Override
	public Locale getClientLocale() throws ServicePropertyException {
		CommonServiceModel model = getCommonServiceModel();
		String localeString = model.getClientLocale();
		Locale locale = null;

		if (localeString != null) {
			locale = getRealLocale(localeString);
		}
		return locale;
	}

	private static Locale getRealLocale(String localeString) {
		StringTokenizer tokenizer = new StringTokenizer(localeString, "-_");
		if (tokenizer.countTokens() == 2) {
			String language = tokenizer.nextToken();
			String country = tokenizer.nextToken();
			return new Locale(language, country);
		} else if (tokenizer.countTokens() == 3) {
			String language = tokenizer.nextToken();
			String country = tokenizer.nextToken();
			String variant = tokenizer.nextToken();
			return new Locale(language, country, variant);
		}
		String message = null;
		try {
			message = ResourceBundle.getBundle(
					"com.huateng.web.framework.basebase.web.tag.i18n")
					.getString("MessageTag.LocaleStringIncorrect");
		} catch (MissingResourceException e) {

		}
		throw new IllegalArgumentException(message + " : \"" + localeString
				+ "\"");
	}

	@Override
	public String getEncodingAttributeName() throws ServicePropertyException {
		CommonServiceModel commonModel = getCommonServiceModel();
		String attrName = null;
		if (commonModel != null) {
			attrName = commonModel.getEncodingAttributeName();
		}
		if (attrName == null) {
			attrName = "com.huateng.web.framework.base.service.encoding";
		}
		return attrName;
	}

	@Override
	public String getExceptionAttributeName() throws ServicePropertyException {
		CommonServiceModel commonModel = getCommonServiceModel();
		String attrName = null;
		if (commonModel != null) {
			attrName = commonModel.getExceptionAttributeName();
		}
		if (attrName == null) {
			attrName = "exception";
		}
		return attrName;
	}

	@Override
	public String getInputErrorPagePath(String application, String service,
			Locale locale) throws ServicePropertyException {
		String result = null;
		ServiceConfigModel serviceConfig = getServiceConfigModel(application,
				locale);
		if (serviceConfig != null) {
			result = serviceConfig.getInputErrorPagePath(service);
		}

		if (result == null) {
			try {
				result = getInputErrorPagePath(application, locale);
			} catch (ServicePropertyException e) {

			}
		}

		if (result == null) {
			String idMessage = " : application = " + application
					+ " : service = " + service;
			String message = ServiceResourceMessage
					.getResourceString("ResourceBundleServicePropertyHandlerUtil.FailedToGetInputErrorPagePath");
			throw new ServicePropertyException(message + idMessage);
		}
		return result;
	}

	@Override
	public String getInputErrorPagePath(String application, String service,
			String key, Locale locale) throws ServicePropertyException {
		String result = null;
		ServiceConfigModel serviceConfig = getServiceConfigModel(application,
				locale);

		if (serviceConfig != null) {
			result = serviceConfig.getInputErrorPagePath(service, key);
		}

		if (result == null) {
			try {
				result = getInputErrorPagePath(application, service, locale);
			} catch (ServicePropertyException e) {

			}
		}

		if (result == null) {
			String idMessage = " : application = " + application
					+ " : service = " + service + " : key = " + key;
			String message = ServiceResourceMessage
					.getResourceString("ResourceBundleServicePropertyHandlerUtil.FailedToGetInputErrorPagePath");
			throw new ServicePropertyException(message + idMessage);
		}
		return result;
	}

	@Override
	public String getInputErrorPagePath(String application, Locale locale)
			throws ServicePropertyException {
		String result = null;
		ServiceConfigModel serviceConfig = getServiceConfigModel(application,
				locale);
		result = serviceConfig.getInputErrorPagePath();

		if (result == null) {
			try {
				result = getInputErrorPagePath(locale);
			} catch (ServicePropertyException e) {

			}
		}

		if (result == null) {
			String idMessage = " : application = " + application;
			String message = ServiceResourceMessage
					.getResourceString("ResourceBundleServicePropertyHandlerUtil.FailedToGetInputErrorPagePath");
			throw new ServicePropertyException(message + idMessage);
		}
		return result;
	}

	@Override
	public String getInputErrorPagePath(Locale locale)
			throws ServicePropertyException {
		String result = null;
		CommonServiceModel commonService = getCommonServiceModel(locale);
		if (commonService != null) {
			result = commonService.getInputErrorPagePath();
		}
		if (result == null) {
			String message = ServiceResourceMessage
					.getResourceString("ResourceBundleServicePropertyHandlerUtil.FailedToGetInputErrorPagePath");

			throw new ServicePropertyException(message);
		}
		return result;
	}

	@Override
	public String getLocaleAttributeName() throws ServicePropertyException {
		CommonServiceModel commonModel = getCommonServiceModel();
		String attrName = commonModel.getLocaleAttributeName();
		if (attrName == null) {
			return "com.huateng.web.framework.base.service.locale";
		}

		return attrName;
	}

	@Override
	public String getNextPagePath(String application, String service,
			Locale locale) throws ServicePropertyException {
		String result = null;

		ServiceConfigModel serviceConfig = getServiceConfigModel(application,
				locale);
		if (serviceConfig != null) {
			result = serviceConfig.getNextPagePath(service);
		}

		if (result == null) {
			String idMessage = " : application = " + application
					+ " : service = " + service;

			String message = ServiceResourceMessage
					.getResourceString("ResourceBundleServicePropertyHandlerUtil.FailedToGetNextPagePath");

			throw new ServicePropertyException(message + idMessage);
		}
		return result;
	}

	@Override
	public String getNextPagePath(String application, String service,
			String key, Locale locale) throws ServicePropertyException {
		String result = null;

		ServiceConfigModel serviceConfig = getServiceConfigModel(application,
				locale);
		if (serviceConfig != null) {
			result = serviceConfig.getNextPagePath(service, key);
		}

		if (result == null) {
			try {
				result = getNextPagePath(application, service, locale);
			} catch (ServicePropertyException e) {

			}
		}

		if (result == null) {
			String idMessage = " : application = " + application
					+ " : service = " + service + " : key = " + key;

			String message = ServiceResourceMessage
					.getResourceString("ResourceBundleServicePropertyHandlerUtil.FailedToGetNextPagePath");

			throw new ServicePropertyException(message + idMessage);
		}
		return result;
	}

	@Override
	public String getServiceControllerName(String application, String service,
			Locale locale) throws ServicePropertyException {
		String result = null;
		ServiceConfigModel serviceConfig = getServiceConfigModel(application,
				locale);
		if (serviceConfig != null) {
			result = serviceConfig.getServiceControllerClassName(service);
		}
		return result;
	}

	@Override
	public String getServiceErrorPagePath(String application, String service,
			Locale locale) throws ServicePropertyException {
		String result = null;
		ServiceConfigModel serviceConfig = getServiceConfigModel(application,
				locale);
		if (serviceConfig != null) {
			result = serviceConfig.getServiceErrorPagePath(service);
		}

		if (result == null) {
			try {
				result = getServiceErrorPagePath(application, locale);
			} catch (ServicePropertyException e) {

			}
		}

		if (result == null) {
			String idMessage = " : application = " + application
					+ " : service = " + service;

			String message = ServiceResourceMessage
					.getResourceString("ResourceBundleServicePropertyHandlerUtil.ResourceBundleServicePropertyHandlerUtil.FailedToGetServiceErrorPagePath");

			throw new ServicePropertyException(message + idMessage);
		}
		return result;
	}

	@Override
	public String getServiceErrorPagePath(String application, String service,
			String key, Locale locale) throws ServicePropertyException {
		String result = null;
		ServiceConfigModel serviceConfig = getServiceConfigModel(application,
				locale);
		if (serviceConfig != null) {
			result = serviceConfig.getServiceErrorPagePath(service, key);
		}

		if (result == null) {
			try {
				result = getServiceErrorPagePath(application, service, locale);
			} catch (ServicePropertyException e) {

			}
		}

		if (result == null) {
			String idMessage = " : application = " + application
					+ " : service = " + service + " : key = " + key;

			String message = ServiceResourceMessage
					.getResourceString("ResourceBundleServicePropertyHandlerUtil.ResourceBundleServicePropertyHandlerUtil.FailedToGetServiceErrorPagePath");

			throw new ServicePropertyException(message + idMessage);
		}
		return result;
	}

	@Override
	public String getServiceErrorPagePath(String application, Locale locale)
			throws ServicePropertyException {
		String result = null;
		ServiceConfigModel serviceConfig = getServiceConfigModel(application,
				locale);
		if (serviceConfig != null) {
			result = serviceConfig.getSystemErrorPagePath();
		}

		if (result == null) {
			try {
				result = getServiceErrorPagePath(locale);
			} catch (ServicePropertyException e) {

			}
		}

		if (result == null) {
			String idMessage = " : application = " + application;
			String message = ServiceResourceMessage
					.getResourceString("ResourceBundleServicePropertyHandlerUtil.ResourceBundleServicePropertyHandlerUtil.FailedToGetServiceErrorPagePath");

			throw new ServicePropertyException(message + idMessage);

		}
		return result;
	}

	@Override
	public String getServiceErrorPagePath(Locale locale)
			throws ServicePropertyException {

		String result = null;
		CommonServiceModel commonService = getCommonServiceModel(locale);
		result = commonService.getServiceErrorPagePath();
		if (result == null) {
			String message = ServiceResourceMessage
					.getResourceString("ResourceBundleServicePropertyHandlerUtil.FailedToGetServiceErrorPagePath");

			throw new ServicePropertyException(message);
		}
		return result;
	}

	@Override
	public String getSystemErrorPagePath(String application, String service,
			Locale locale) throws ServicePropertyException {
		String result = null;
		ServiceConfigModel serviceConfig = getServiceConfigModel(application,
				locale);
		if (serviceConfig != null) {
			result = serviceConfig.getServiceErrorPagePath(service);
		}
		if (result == null) {
			try {
				result = getSystemErrorPagePath(application, locale);
			} catch (ServicePropertyException e) {

			}
		}
		if (result == null) {
			String idMessage = " : application = " + application
					+ " : service = " + service;
			String message = ServiceResourceMessage
					.getResourceString("ResourceBundleServicePropertyHandlerUtil.FailedToGetServiceErrorPagePath");
			throw new ServicePropertyException(message + idMessage);
		}
		return result;
	}

	@Override
	public String getSystemErrorPagePath(String application, String service,
			String key, Locale locale) throws ServicePropertyException {
		String result = null;
		ServiceConfigModel serviceConfig = getServiceConfigModel(application,
				locale);
		if (serviceConfig != null) {
			result = serviceConfig.getServiceErrorPagePath(service, key);
		}

		if (result == null) {
			try {
				result = getSystemErrorPagePath(application, service, locale);
			} catch (ServicePropertyException e) {

			}
		}

		if (result == null) {
			String idMessage = " : application = " + application
					+ " : service = " + service + " : key = " + key;

			String message = ServiceResourceMessage
					.getResourceString("ResourceBundleServicePropertyHandlerUtil.FailedToGetServiceErrorPagePath");

			throw new ServicePropertyException(message + idMessage);

		}
		return result;
	}

	@Override
	public String getSystemErrorPagePath(String application, Locale locale)
			throws ServicePropertyException {
		String result = null;
		ServiceConfigModel serviceConfig = getServiceConfigModel(application,
				locale);
		if (serviceConfig != null) {
			result = serviceConfig.getSystemErrorPagePath();
		}
		if (result == null) {
			try {
				result = getSystemErrorPagePath(locale);
			} catch (ServicePropertyException e) {

			}
		}

		if (result == null) {
			String idMessage = " : application = " + application;
			String message = ServiceResourceMessage
					.getResourceString("ResourceBundleServicePropertyHandlerUtil.FailedToGetServiceErrorPagePath");

			throw new ServicePropertyException(message + idMessage);
		}

		return result;
	}

	@Override
	public String getSystemErrorPagePath(Locale locale)
			throws ServicePropertyException {
		String result = null;
		CommonServiceModel commonService = getCommonServiceModel(locale);
		if (commonService != null) {
			result = commonService.getSystemErrorPagePath();
		}
		if (result == null) {
			String message = ServiceResourceMessage
					.getResourceString("ResourceBundleServicePropertyHandlerUtil.FailedToGetSystemErrorPagePath");

			throw new ServicePropertyException(message);
		}
		return result;
	}

	@Override
	public String getTransitionName(String application, String service,
			Locale locale) throws ServicePropertyException {
		String result = null;
		ServiceConfigModel serviceConfig = getServiceConfigModel(application,
				locale);

		if (serviceConfig != null) {
			result = serviceConfig.getTransitionName(service);
		}
		return result;
	}

	@Override
	public boolean isDynamic() throws ServicePropertyException {
		return this.dynamic;
	}

	private void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}

	@Override
	public void init(PropertyParam[] params) throws PropertyHandlerException {
		String bundleName = null;
		String dynamic = null;
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				if ("bundle".equals(params[i].getName())) {
					//由bundleName决定后缀名称
					bundleName = params[i].getValue();
				} else {
					//是否为动态
					if (!params[i].getName().equals("dynamic"))
						continue;
					dynamic = params[i].getValue();
				}
			}
		}

		if (bundleName == null) {
			//默认的配置名称前缀
			bundleName = "service-config";
		}

		this.setXmlPrefix(bundleName);

		Boolean dummyDynamic = new Boolean(dynamic);
		setDynamic(dummyDynamic.booleanValue());
	}

	public String getServiceServletPath() throws ServicePropertyException {
		CommonServiceModel model = getCommonServiceModel();
		String path = null;
		if (model != null) {
			path = model.getServiceServletPath();
		}

		return path;
	}

}
