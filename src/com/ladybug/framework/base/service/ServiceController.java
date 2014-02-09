package com.ladybug.framework.base.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ladybug.framework.system.exception.ApplicationException;
import com.ladybug.framework.system.exception.SystemException;

/**
 *  服务控制器接口
 *  
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public interface ServiceController {

	/**
	 * 设置请求
	 * @param paramHttpServletRequest
	 */
	public void setRequest(HttpServletRequest paramHttpServletRequest);

	/**
	 * 取得请求
	 * @return
	 */
	public HttpServletRequest getRequest();

	/**
	 * 设置响应
	 * @param paramHttpServletResponse
	 */
	public void setResponse(HttpServletResponse paramHttpServletResponse);

	/**
	 * 取得响应
	 * @return
	 */
	public HttpServletResponse getResponse();

	/**
	 * 检查
	 * @throws RequestException
	 * @throws SystemException
	 */
	public void check() throws RequestException, SystemException;

	/**
	 * 服务执行方法
	 * @return
	 * @throws SystemException
	 * @throws ApplicationException
	 */
	public ServiceResult service() throws SystemException, ApplicationException;
}
