package com.ladybug.framework.base.service;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务模型,针对业务配置
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 */
public class ServiceModel {
	public static final String ID = "service";
	public static final String P_ID_SERVICE_ID="service-id";
	public static final String P_ID_SERVICE_CONTROLL="controller-class";
	public static final String P_ID_SERVICE_TRANSITION = "transition-class";
	
	/**
	 * 转出页面模型
	 */
	private NextPageModel defaultNextPage;
	
	/**
	 * 转出页面模型缓存
	 */
	private Map<String,NextPageModel> nextPages = new HashMap<String,NextPageModel>();
	
	/**
	 * 默认的输入错误页面模型
	 */
	private ErrorPageModel defaultInputErrorPage;
	
	/**
	 * 输入错误页面模型缓存
	 */
	private Map<String,ErrorPageModel> inputErrorPages = new HashMap<String,ErrorPageModel>();
	
	/**
	 * 默认的系统错误页面模型
	 */
	private ErrorPageModel defaultSystemErrorPage;
	
	/**
	 * 系统错误页面模型缓存
	 */
	private Map<String,ErrorPageModel> systemErrorPages = new HashMap<String,ErrorPageModel>();
	
	/**
	 * 默认的服务错误页面模型
	 */
	private ErrorPageModel defaultServiceErrorPage;
	
	/**
	 * 服务错误页面模型缓存
	 */
	private Map<String,ErrorPageModel> serviceErrorPages = new HashMap<String,ErrorPageModel>();
	
	/**
	 *  服务控制器名称
	 */
	private String serviceControllerName;
	
	/**
	 * 转换器名称
	 */
	private String transitionName;
	
	/**
	 * 服务ID
	 */
	private String serviceId;
	
	/**
	 * 取得转换器名称
	 * @return
	 */
	public String getTransitionName() {
		return transitionName;
	}
	
	/**
	 * 设置转换器名称
	 * @param transitionName
	 */
	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}
	
	/**
	 * 取得默认的转出页面模型
	 * @return
	 */
	public NextPageModel getDefaultNextPage() {
		return defaultNextPage;
	}
	
	/**
	 * 设置默认的转出页面模型
	 * @param defaultNextPage
	 */
	public void setDefaultNextPage(NextPageModel defaultNextPage) {
		this.defaultNextPage = defaultNextPage;
	}
	
	/**
	 * 返回转出页面模型缓存
	 * @return
	 */
	public Map<String,NextPageModel> getNextPages() {
		return nextPages;
	}
	
	/**
	 * 设置转出页面模型缓存
	 * @param nextPages
	 */
	public void setNextPages(Map<String,NextPageModel> nextPages) {
		this.nextPages = nextPages;
	}
	
	/**
	 * 根据Key值，设置转出页面模型到缓存中
	 * @param key 缓存Key 
	 * @param nextPage 转出页面模型 
	 */
	public void setNextPage(String key,NextPageModel nextPage){
		this.nextPages.put(key, nextPage);
	}
	
	/**
	 * 取得默认的输入错误页面模型
	 * @return
	 */
	public ErrorPageModel getDefaultInputErrorPage() {
		return defaultInputErrorPage;
	}
	
	/**
	 * 设置默认的输入错误页面模型
	 * @param defaultInputErrorPage
	 */
	public void setDefaultInputErrorPage(ErrorPageModel defaultInputErrorPage) {
		this.defaultInputErrorPage = defaultInputErrorPage;
	}
	
	/**
	 * 根据Key值，把错误页面模型设置到缓存中
	 * @param key 缓存Key
	 * @param errorPage 错误页面模型
	 */
	public void setInputErrorPage(String key,ErrorPageModel errorPage){
		this.inputErrorPages.put(key, errorPage);
	}
	
	/**
	 * 返回默认的系统错误页面模型
	 * @return
	 */
	public ErrorPageModel getDefaultSystemErrorPage() {
		return defaultSystemErrorPage;
	}
	/**
	 * 设置默认的系统错误页面模型
	 * @param defaultSystemErrorPage
	 */
	public void setDefaultSystemErrorPage(ErrorPageModel defaultSystemErrorPage) {
		this.defaultSystemErrorPage = defaultSystemErrorPage;
	}
	
	/**
	 * 根据Key值，把错误页面模型设置到
	 * @param key
	 * @param errorPage
	 */
	public void setSystemErrorPage(String key,ErrorPageModel errorPage){
		this.systemErrorPages.put(key, errorPage);
	}
	
	/**
	 * 返回默认的服务错误页面模型
	 * @return
	 */
	public ErrorPageModel getDefaultServiceErrorPage() {
		return defaultServiceErrorPage;
	}
	
	/**
	 * 设置默认的服务错误页面模型
	 * @param defaultServiceErrorPage
	 */
	public void setDefaultServiceErrorPage(ErrorPageModel defaultServiceErrorPage) {
		this.defaultServiceErrorPage = defaultServiceErrorPage;
	}
	
	/**
	 * 返回服务错误页面模型缓存
	 * @return
	 */
	public Map<String,ErrorPageModel> getServiceErrorPages() {
		return serviceErrorPages;
	}
	
	/**
	 * 设置服务错误页面模型缓存
	 * @param serviceErrorPages
	 */
	public void setServiceErrorPages(Map<String,ErrorPageModel> serviceErrorPages) {
		this.serviceErrorPages = serviceErrorPages;
	}
	
	/**
	 * 根据Key值把服务错误页面模型设置到缓存中
	 * @param key
	 * @param errorPage
	 */
	public void setServiceErrorPage(String key,ErrorPageModel errorPage){
		this.serviceErrorPages.put(key, errorPage);
	}
	
	/**
	 * 取得服务控制器名称
	 * @return
	 */
	public String getServiceControllerName() {
		return serviceControllerName;
	}
	
	/**
	 * 设置服务控制器名称
	 * @param serviceControllerName
	 */
	public void setServiceControllerName(String serviceControllerName) {
		this.serviceControllerName = serviceControllerName;
	}
	
	/**
	 * 取得服务ID
	 * @return
	 */
	public String getServiceId() {
		return serviceId;
	}
	
	/**
	 * 设置服务Id
	 * @param serviceId
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	/**
	 * 根据Key值从缓存中取得输入错误页面模型，任何取得输入错误页面路径
	 * @param key 错误页面模型缓存Key
	 * @return
	 */
	public String getInputErrorPagePath(String key){
		String result = null;
		ErrorPageModel errorPage = (ErrorPageModel) this.inputErrorPages.get(key);
		if(errorPage != null){
			result = errorPage.getErrorPage();
		}
		return result;
	}
	
	/**
	 * 返回默认的输入错误页面路径
	 * @return
	 */
	public String getInputErrorPagePath(){
		if(this.defaultInputErrorPage != null){
			return this.defaultInputErrorPage.getErrorPage();
		}
		return null;
	}
	
	/**
	 * 根据Key从缓存中取得服务错误页面模型，然后从中取得服务错误页面路径
	 * @param key 错误页面模型缓存key值
	 * @return
	 */
	public String getServiceErrorPagePath(String key){
		String result = null;
		ErrorPageModel errorPage = (ErrorPageModel) this.serviceErrorPages.get(key);
		if(errorPage != null){
			result = errorPage.getErrorPage();
		}
		return result;
	}
	
	/**
	 * 返回默认的服务错误页面路径
	 * @return
	 */
	public String getServiceErrorPagePath(){
		if(this.defaultServiceErrorPage != null){
			return this.defaultServiceErrorPage.getErrorPage();
		}
		return null;
	}
	
	/**
	 * 从缓存中取得系统错误页面模型，然后从中取得系统错误页面路径
	 * @param key  错误页面缓存Key值
	 * @return
	 */
	public String getSystemErrorPagePath(String key){
		String result = null;
		ErrorPageModel errorPage = (ErrorPageModel) this.systemErrorPages.get(key);
		if(errorPage != null){
			result = errorPage.getErrorPage();
		}
		return result;
		
	}
	
	/**
	 * 返回默认的系统错误页面路径
	 * @return
	 */
	public String getSystemErrorPagePath(){
		if(this.defaultSystemErrorPage != null){
			return this.defaultSystemErrorPage.getErrorPage();
		}
		return null;
	}
	
	/**
	 * 根据Key值从缓存中取得转出页面模型，然后从中取得转出页面路径
	 * @param key 转出页面模型缓存Key值
	 * @return
	 */
	public String getNextPagePath(String key){
		String result = null;
		NextPageModel errorPage = (NextPageModel) this.nextPages.get(key);
		if(errorPage != null){
			result = errorPage.getPagePath();
		}
		return result;
	}
	
	/**
	 * 取得默认的转出页面路径
	 * @return
	 */
	public String getNextPagePath(){
		if(this.defaultNextPage != null){
			return this.defaultNextPage.getPagePath();
		}
		return null;
	}
}
