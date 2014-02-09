package com.ladybug.framework.base.service;

/**
 * 错误页面模型
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class ErrorPageModel {
	public static final String P_ID_NEXT_PAGE = "page-key";
	public static final String P_ID_ERROR_PAGE = "page-path";
	public static final String P_ID_INPUT_ERROR = "input-error";
	public static final String P_ID_SERVICE_ERROR = "service-error";
	public static final String P_ID_SYSTEM_ERROR = "system-error";

	/**
	 * 页面键值
	 */
	private String pageKey;
	
	/**
	 * 错误页面路径
	 */
	private String errorPage;

	/**
	 * 取得页面键值
	 * @return
	 */
	public String getPageKey() {
		return pageKey;
	}

	/**
	 * 设置页面键值
	 * @param pageKey
	 */
	public void setPageKey(String pageKey) {
		this.pageKey = pageKey;
	}

	/**
	 * 取得错误页面路径
	 * @return
	 */
	public String getErrorPage() {
		return errorPage;
	}

	/**
	 * 设置错误页面路径 
	 * 
	 * @param errorPage
	 */
	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

}
