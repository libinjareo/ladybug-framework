package com.ladybug.framework.base.service;

/**
 * 转出页面模型
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class NextPageModel {
	public static final String P_ID_NEXT_PAGE = "next-page";
	public static final String P_ID_PAGE_KEY = "page-key";
	public static final String P_ID_PAGE_PATH = "page-path";
	
	/**
	 * 页面Key值
	 */
	private String pageKey;
	
	/**
	 * 页面路径
	 */
	private String pagePath;

	/**
	 * 返回页面Key值
	 * @return
	 */
	public String getPageKey() {
		return pageKey;
	}

	/**
	 * 设置页面key值
	 * @param pageKey
	 */
	public void setPageKey(String pageKey) {
		this.pageKey = pageKey;
	}

	/**
	 * 取得页面路径
	 * @return
	 */
	public String getPagePath() {
		return pagePath;
	}

	/**
	 * 设置页面路径
	 * @param pagePath
	 */
	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}

}
