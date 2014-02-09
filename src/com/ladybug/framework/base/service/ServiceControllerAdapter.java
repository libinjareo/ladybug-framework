package com.ladybug.framework.base.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ladybug.framework.base.event.Event;
import com.ladybug.framework.base.event.EventException;
import com.ladybug.framework.base.event.EventManager;
import com.ladybug.framework.base.event.EventManagerException;
import com.ladybug.framework.base.event.EventResult;
import com.ladybug.framework.system.exception.ApplicationException;
import com.ladybug.framework.system.exception.SystemException;

/**
 * 
 * 服务控制器模型适配器,增加createEvent和dispatchEvent方法 
 * 
 * @author james.li(libinjare@163.comj)
 * @version 1.0
 */
public class ServiceControllerAdapter implements ServiceController {

	/**
	 * 请求对象
	 */
	private HttpServletRequest request;
	
	/**
	 * 响应对象
	 */
	private HttpServletResponse response;

	@Override
	public void check() throws RequestException, SystemException {

	}

	@Override
	public HttpServletRequest getRequest() {
		return request;
	}

	@Override
	public HttpServletResponse getResponse() {
		return response;
	}

	@Override
	public ServiceResult service() throws SystemException, ApplicationException {
		return null;
	}

	@Override
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * 创建事件对象
	 * @param application 应用
	 * @param key event-key值
	 * @return
	 * @throws ServiceControllerException
	 */
	protected Event createEvent(String application, String key)
			throws ServiceControllerException {
		Event event = null;

		try {
			event = EventManager.getEventManager().createEvent(application, key);
		} catch (Exception e) {
			throw new ServiceControllerException(e.getMessage(), e);
		}

		return event;
	}

	/**
	 * 事件派发
	 * @param event
	 * @return
	 * @throws EventManagerException
	 * @throws EventException
	 * @throws SystemException
	 * @throws ApplicationException
	 */
	protected EventResult dispatchEvent(Event event)
			throws EventManagerException, EventException, SystemException,
			ApplicationException {
		return EventManager.getEventManager().dispatch(event);
	}
}
