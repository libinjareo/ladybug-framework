package com.ladybug.framework.base.service;

import java.util.Collection;

/**
 * 共用服务模型，有些属性待后续使用。
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class CommonServiceModel {
	public static final String ID = "service-config";
	public static final String P_ID_CLIENT_ENCODING = "client-encoding";
	public static final String P_ID_ENCODING_ATTRIBUTE_NAME = "encoding-attribute-name";
	public static final String P_ID_CLIENT_LOCALE = "client-locale";
	public static final String P_ID_LOCALE_ATTRIBUTE = "locale-attribute-name";
	public static final String P_ID_CONTEXT_PATH = "context-path";
	public static final String P_ID_SERVICE_SERVLET = "service-servlet";
	public static final String P_ID_APPLICATION_PARAM_NAME = "application-param-name";
	public static final String P_ID_SERVICE_PARAM_NAME = "service-param-name";
	public static final String P_ID_EXCEPTION_ATTRIBUTE_NAME = "exception-attribute-name";
	public static final String P_ID_CACHE_RULE = "cache-rule";
	public static final String P_ID_CACHE_CONDITION = "cache-condition";
	public static final String P_ID_CACHE_CONDITION_CLASS = "cache-condition-class";
	public static final String P_ID_INIT_PARAM = "init-param";
	public static final String P_ID_PARAM_NAME = "param-name";
	public static final String P_ID_PARAM_VALUE = "param-value";
	public static final String P_ID_CACHE = "cache";
	public static final String P_ID_CACHE_CLASS = "cache-class";

	private String clientEncoding;
	private String encodingAttributeName;
	private String clientLocale;
	private String localeAttributeName;
	private String contextPath;
	private String serviceServletPath;
	private String applicationParamName;
	private String serviceParamName;
	private String exceptionAttributeName;
	private ErrorPageModel inputErrorPage;
	private ErrorPageModel systemErrorPage;
	private ErrorPageModel serviceErrorPage;
	@SuppressWarnings({ "rawtypes" })
	private Collection cacheRuleInfos;
	private CommonServiceModel parent;

	public String getClientEncoding() {
		return clientEncoding;
	}

	public void setClientEncoding(String clientEncoding) {
		this.clientEncoding = clientEncoding;
	}

	public String getEncodingAttributeName() {
		return encodingAttributeName;
	}

	public void setEncodingAttributeName(String encodingAttributeName) {
		this.encodingAttributeName = encodingAttributeName;
	}

	public String getClientLocale() {
		return clientLocale;
	}

	public void setClientLocale(String clientLocale) {
		this.clientLocale = clientLocale;
	}

	public String getLocaleAttributeName() {
		return localeAttributeName;
	}

	public void setLocaleAttributeName(String localeAttributeName) {
		this.localeAttributeName = localeAttributeName;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getServiceServletPath() {
		return serviceServletPath;
	}

	public void setServiceServletPath(String serviceServletPath) {
		this.serviceServletPath = serviceServletPath;
	}

	public String getApplicationParamName() {
		return applicationParamName;
	}

	public void setApplicationParamName(String applicationParamName) {
		this.applicationParamName = applicationParamName;
	}

	public String getServiceParamName() {
		return serviceParamName;
	}

	public void setServiceParamName(String serviceParamName) {
		this.serviceParamName = serviceParamName;
	}

	public String getExceptionAttributeName() {
		return exceptionAttributeName;
	}

	public void setExceptionAttributeName(String exceptionAttributeName) {
		this.exceptionAttributeName = exceptionAttributeName;
	}

	public ErrorPageModel getInputErrorPage() {
		return inputErrorPage;
	}

	public void setInputErrorPage(ErrorPageModel inputErrorPage) {
		this.inputErrorPage = inputErrorPage;
	}

	public ErrorPageModel getSystemErrorPage() {
		return systemErrorPage;
	}

	public void setSystemErrorPage(ErrorPageModel systemErrorPage) {
		this.systemErrorPage = systemErrorPage;
	}

	public ErrorPageModel getServiceErrorPage() {
		return serviceErrorPage;
	}

	public void setServiceErrorPage(ErrorPageModel serviceErrorPage) {
		this.serviceErrorPage = serviceErrorPage;
	}

	@SuppressWarnings("rawtypes")
	public Collection getCacheRuleInfos() {
		return cacheRuleInfos;
	}

	public void setCacheRuleInfos(@SuppressWarnings("rawtypes") Collection cacheRuleInfos) {
		this.cacheRuleInfos = cacheRuleInfos;
	}

	public CommonServiceModel getParent() {
		return parent;
	}

	public void setParent(CommonServiceModel parent) {
		this.parent = parent;
	}

	public String getInputErrorPagePath() {
		String result = null;
		if (this.inputErrorPage != null) {
			result = this.inputErrorPage.getErrorPage();
		}
		if ((result == null) && (this.parent != null)) {
			result = this.parent.getInputErrorPagePath();
		}
		return result;
	}

	public String getServiceErrorPagePath() {
		String result = null;
		if (this.serviceErrorPage != null) {
			result = this.serviceErrorPage.getErrorPage();
		}
		if ((result == null) && (this.parent != null)) {
			result = this.parent.getServiceErrorPagePath();
		}
		return result;
	}

	public String getSystemErrorPagePath() {
		String result = null;
		if (this.systemErrorPage != null) {
			result = this.systemErrorPage.getErrorPage();
		}
		if ((result == null) && (this.parent != null)) {
			result = this.parent.getSystemErrorPagePath();
		}
		return result;
	}
}
