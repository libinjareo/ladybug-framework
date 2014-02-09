package com.ladybug.framework.base.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ladybug.framework.system.exception.SystemException;

/**
 * 转换器抽象类
 * 
 * @author james.li(libinjareo@163.com)
 *
 */
public abstract class Transition {
	
	/**
	 * 服务管理器
	 */
	private ServiceManager serviceManager;
	
	/**
	 * 应用
	 */
	private String application;
	
	/**
	 * 服务
	 */
	private String service;
	
	/**
	 * 请求
	 */
	private HttpServletRequest request;
	
	/**
	 * 响应
	 */
	private HttpServletResponse response;
	
	/**
	 *  服务结果对象
	 */
	private ServiceResult result;
	
	/**
	 * 默认构造函数
	 */
	public Transition(){
		
	}
	
	/**
	 * 取得服务管理器
	 * 
	 * @return ServiceManager
	 */
	public ServiceManager getServiceManager() {
		return serviceManager;
	}
	
	/**
	 * 设置服务管理器
	 * @param serviceManager
	 */
	public void setServiceManager(ServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}
	
	/**
	 * 取得应用
	 * @return
	 */
	public String getApplication() {
		return application;
	}
	
	/**
	 * 设置应用
	 * @param application
	 */
	public void setApplication(String application) {
		this.application = application;
	}
	
	/**
	 * 取得服务
	 * @return
	 */
	public String getService() {
		return service;
	}
	
	/**
	 * 设置服务
	 * @param service
	 */
	public void setService(String service) {
		this.service = service;
	}
	
	/**
	 * 取得请求对象
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return request;
	}
	
	/**
	 * 设置请求对象
	 * @param request
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	/**
	 * 取得响应对象
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return response;
	}
	/**
	 * 设置响应对象
	 * @param response
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	/**
	 * 取得服务结果
	 * @return
	 */
	public ServiceResult getResult() {
		return result;
	}
	
	/**
	 * 设置服务结果
	 * @param result
	 */
	public void setResult(ServiceResult result) {
		this.result = result;
	}
	
	/**
	 * 取得目标页面路径
	 * @return
	 * @throws ServicePropertyException
	 */
	protected String getNextPagePath() throws ServicePropertyException{
		Locale locale = getServiceManager().getLocale(getRequest(), getResponse());
		return getServiceManager().getServicePropertyHandler().getNextPagePath(getApplication(), getService(), locale);
	}
	
	/**
	 *  取得目标页面路径
	 * @param key
	 * @return
	 * @throws ServicePropertyException
	 */
	protected String getNextPagePath(String key) throws ServicePropertyException{
		Locale locale = getServiceManager().getLocale(getRequest(), getResponse());
		return getServiceManager().getServicePropertyHandler().getNextPagePath(getApplication(), getService(), key, locale);
	}
	
	// 以下留给子类实现
	
	/**
	 * 取得输入错误页面路径
	 */
	public abstract String getInputErrorPage(RequestException requestException) throws ServicePropertyException,TransitionException;
	
	/**
	 * 取得系统错误页面路径
	 * @param exception
	 * @return
	 * @throws ServicePropertyException
	 * @throws TransitionException
	 */
	public abstract String getSystemErrorPage(Exception exception) throws ServicePropertyException,TransitionException;
	
	/**
	 * 取得服务错误页面路径
	 * 
	 * @param exception
	 * @return
	 * @throws ServicePropertyException
	 * @throws TransitionException
	 */
	public abstract String getServiceErrorPage(Exception exception) throws ServicePropertyException,TransitionException;
	
	/**
	 * 设置服务信息
	 * @throws TransitionException
	 */
	public abstract void setInformation() throws TransitionException;
	/**
	 * 取得目标页面路径
	 * @return
	 * @throws ServicePropertyException
	 * @throws TransitionException
	 */
	public abstract String getNextPage() throws ServicePropertyException,TransitionException;
	
	/**
	 * 进行业务逻辑转发
	 * @throws SystemException
	 */
	public abstract void transfer()throws SystemException;
}
