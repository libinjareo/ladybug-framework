package com.ladybug.framework.base.service;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务配置模型
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class ServiceConfigModel {
	
	public static final String ID = "service-config";
	
	/**
	 * 服务模型缓存
	 */
	private Map<String,ServiceModel> services;
	
	/**
	 * 输入错误页面配置模型
	 */
	private ErrorPageModel inputErrorPage;
	
	/**
	 * 系统错误页面配置模型
	 */
	private ErrorPageModel systemErrorPage;
	
	/**
	 * 服务错误页面配置模型
	 */
	private ErrorPageModel serviceErrorPage;
	
	/**
	 * 父服务配置模型
	 */
	private ServiceConfigModel parent;

	/**
	 * 默认构造函数,初始化缓存
	 */
	public ServiceConfigModel() {
		setServices(new HashMap<String,ServiceModel>());
	}

	/**
	 * 取得父配置模型
	 * @return
	 */
	public ServiceConfigModel getParent() {
		return parent;
	}

	/**
	 * 设置父配置模型
	 * @param parent
	 */
	public void setParent(ServiceConfigModel parent) {
		this.parent = parent;
	}

	/**
	 * 取得服务缓存
	 * @return
	 */
	public Map<String,ServiceModel> getServices() {
		return services;
	}

	/**
	 * 设置服务缓存
	 * 
	 * @param services
	 */
	public void setServices(Map<String,ServiceModel> services) {
		this.services = services;
	}

	/**
	 * 取得输入错误页面配置模型
	 * @return
	 */
	public ErrorPageModel getInputErrorPage() {
		return inputErrorPage;
	}

	/**
	 * 设置输入错误页面配置模型
	 * 
	 * @param inputErrorPage
	 */
	public void setInputErrorPage(ErrorPageModel inputErrorPage) {
		this.inputErrorPage = inputErrorPage;
	}

	/**
	 * 取得系统错误页面配置模型
	 * @return
	 */
	public ErrorPageModel getSystemErrorPage() {
		return systemErrorPage;
	}

	/**
	 * 设置系统错误页面配置模型
	 * @param systemErrorPage
	 */
	public void setSystemErrorPage(ErrorPageModel systemErrorPage) {
		this.systemErrorPage = systemErrorPage;
	}

	/**
	 * 取得服务错误页面配置模型
	 * @return
	 */
	public ErrorPageModel getServiceErrorPage() {
		return serviceErrorPage;
	}

	/**
	 * 设置服务错误页面配置模型
	 * 
	 * @param serviceErrorPage
	 */
	public void setServiceErrorPage(ErrorPageModel serviceErrorPage) {
		this.serviceErrorPage = serviceErrorPage;
	}

	/**
	 * 根据Key值从缓存中获取服务模型
	 * 
	 * @param key
	 * @return
	 */
	public ServiceModel getService(String key) {
		return (ServiceModel) this.services.get(key);
	}

	/**
	 * 取得输入错误页面路径，首先根据service参数从缓存中取得服务模型，然后再根据Key值从服务模型中取得输入错误页面路径
	 * @param service 服务模型缓存key值
	 * @param key  错误页面模型缓存key值
	 * @return
	 */
	public String getInputErrorPagePath(String service, String key) {
		String result = null;
		//从缓存中取得ServiceModel
		ServiceModel serviceModel = getService(service);
		if (serviceModel != null) {
			result = serviceModel.getInputErrorPagePath(key);
		}
		if ((result == null) && (getParent() != null)) {
			//从父模型中提取
			result = getParent().getInputErrorPagePath(service, key);
		}
		return result;
	}

	/**
	 * 取得默认的输入错误页面路径，首先根据service参数从缓存中取得服务模型，然后根据服务模型取得默认的输入错误页面路径
	 * @param service 服务模型缓存Key值
	 * @return
	 */
	public String getInputErrorPagePath(String service) {
		String result = null;
		//从缓存中取得ServiceModel
		ServiceModel serviceModel = getService(service);
		if (serviceModel != null) {
			result = serviceModel.getInputErrorPagePath();
		}
		if ((result == null) && (getParent() != null)) {
			//从父模型中提取
			result = getParent().getInputErrorPagePath(service);
		}
		return result;
	}

	/**
	 * 取得业务配置中的错误页面路径
	 * @return
	 */
	public String getInputErrorPagePath() {
		String result = null;
		if (this.inputErrorPage != null) {
			result = this.inputErrorPage.getErrorPage();
		}
		if ((result == null) && (getParent() != null)) {
			result = getParent().getInputErrorPagePath();
		}
		return result;
	}

	/**
	 * 取得服务错误页面路径，首先根据service参数从服务模型缓存中提取服务模型，然后根据Key值从服务模型中提取服务错误页面路径
	 * @param service 服务模型缓存key值
	 * @param key 错误页面模型缓存key值
	 * @return
	 */
	public String getServiceErrorPagePath(String service, String key) {
		String result = null;
		//首先从缓存中提取服务模型
		ServiceModel serviceModel = getService(service);
		if (serviceModel != null) {
			result = serviceModel.getServiceErrorPagePath(key);
		}
		if ((result == null) && (getParent() != null)) {
			result = getParent().getServiceErrorPagePath(service, key);
		}
		return result;
	}

	/**
	 * 根据service参数，取得默认的服务错误页面路径，首先根据service参数从服务模型缓存中提取服务模型，然后根据Key值从服务模型中提取默认的服务错误页面路径
	 * @param service 服务模型缓存key值
	 * @return
	 */
	public String getServiceErrorPagePath(String service) {
		String result = null;
		//首先从缓存中提取服务模型
		ServiceModel serviceModel = getService(service);
		if (serviceModel != null) {
			result = serviceModel.getServiceErrorPagePath();
		}
		if ((result == null) && (getParent() != null)) {
			result = getParent().getServiceErrorPagePath(service);
		}
		return result;
	}

	/**
	 * 返回服务错误页面路径
	 * @return
	 */
	public String getServiceErrorPagePath() {
		String result = null;
		if (this.serviceErrorPage != null) {
			result = this.serviceErrorPage.getErrorPage();
		}
		if ((result == null) && (getParent() != null)) {
			result = getParent().getServiceErrorPagePath();
		}
		return result;
	}

	/**
	 * 根据Service参数和Key参数，取得系统错误页面路径，首先根据Service参数从服务模型缓存中取得服务模型，然后根据Key参数从服务模型中提取系统错误页面路径
	 * @param service 服务模型缓存key值
	 * @param key 错误页面模型缓存key值
	 * @return
	 */
	public String getSystemErrorPagePath(String service, String key) {
		String result = null;
		// 根据Service参数从服务模型缓存中取得服务模型
		ServiceModel serviceModel = getService(service);
		if (serviceModel != null) {
			result = serviceModel.getSystemErrorPagePath(key);
		}
		if ((result == null) && (getParent() != null)) {
			result = getParent().getSystemErrorPagePath(service, key);
		}
		return result;
	}

	/**
	 * 根据Service参数，取得系统错误页面路径，首先根据Service参数从服务模型缓存中取得服务模型，然后根据服务模型提取默认的系统错误页面路径
	 * @param service 服务模型缓存key值
	 * @return
	 */
	public String getSystemErrorPagePath(String service) {
		String result = null;
		//根据Service参数，从缓存中提取服务模型
		ServiceModel serviceModel = getService(service);
		if (serviceModel != null) {
			result = serviceModel.getSystemErrorPagePath();
		}
		if ((result == null) && (getParent() != null)) {
			result = getParent().getSystemErrorPagePath(service);
		}
		return result;
	}

	/**
	 * 取得系统错误页面路径
	 * @return 错误页面模型缓存key值
	 */
	public String getSystemErrorPagePath() {
		String result = null;
		if (this.systemErrorPage != null) {
			result = this.systemErrorPage.getErrorPage();
		}
		if ((result == null) && (getParent() != null)) {
			result = getParent().getSystemErrorPagePath();
		}
		return result;
	}

	/**
	 * 根据Service参数和Key参数，取得转出页面路径。首先根据Service参数从缓存中取得服务模型，然后根据Key参数从服务模型中提取转出页面路径
	 * @param service 服务模型缓存key值
	 * @param key
	 * @return
	 */
	public String getNextPagePath(String service, String key) {
		String result = null;
		ServiceModel serviceModel = getService(service);
		if (serviceModel != null) {
			result = serviceModel.getNextPagePath(key);
		}
		if ((result == null) && (getParent() != null)) {
			result = getParent().getNextPagePath(service, key);
		}
		return result;
	}

	/**
	 *  根据Service参数，取得默认的转出页面路径。首先根据Service参数从缓存中提取服务模型，然后根据服务模型取得默认的转出页面路径
	 * @param service 服务模型缓存key值
	 * @return
	 */
	public String getNextPagePath(String service) {
		String result = null;
		ServiceModel serviceModel = getService(service);
		if (serviceModel != null) {
			result = serviceModel.getNextPagePath();
		}
		if ((result == null) && (getParent() != null)) {
			result = getParent().getNextPagePath(service);
		}
		return result;
	}

	/**
	 * 根据Service参数，取得服务控制器类名称
	 * @param service 服务模型缓存key值
	 * @return
	 */
	public String getServiceControllerClassName(String service) {
		String result = null;
		//根据Service参数，取得服务模型
		ServiceModel serviceModel = getService(service);
		if (serviceModel != null) {
			result = serviceModel.getServiceControllerName();
		}
		if ((result == null) && (getParent() != null)) {
			result = getParent().getServiceControllerClassName(service);
		}
		return result;
	}

	/**
	 * 根据Service参数，取得转换器名称
	 * @param service  服务模型缓存key值
	 * @return
	 */
	public String getTransitionName(String service) {
		String result = null;
		ServiceModel serviceModel = getService(service);
		if (serviceModel != null) {
			result = serviceModel.getTransitionName();
		}
		if ((result == null) && (getParent() != null)) {
			result = getParent().getTransitionName(service);
		}
		return result;
	}
}
