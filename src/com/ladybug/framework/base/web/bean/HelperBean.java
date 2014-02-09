package com.ladybug.framework.base.web.bean;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ladybug.framework.base.event.Event;
import com.ladybug.framework.base.event.EventException;
import com.ladybug.framework.base.event.EventManager;
import com.ladybug.framework.base.event.EventResult;
import com.ladybug.framework.base.service.ServiceManager;
import com.ladybug.framework.base.service.ServicePropertyHandler;
import com.ladybug.framework.system.exception.ApplicationException;
import com.ladybug.framework.system.exception.SystemException;

public abstract class HelperBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private EventManager eventManager;
	private ServicePropertyHandler serviceHandler;

	private HttpServletRequest request;
	private HttpServletResponse response;

	public HelperBean() throws HelperBeanException {
		setRequest(null);
		setResponse(null);

		try {
			this.eventManager = EventManager.getEventManager();
			this.serviceHandler = ServiceManager.getServiceManager()
					.getServicePropertyHandler();

		} catch (Exception e) {
			throw new HelperBeanException(e.getMessage(), e);
		}
	}

	public void init() throws HelperBeanException {

	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	protected Event createEvent(String application, String key)
			throws HelperBeanException {
		Event event = null;
		try {
			event = this.eventManager.createEvent(application, key);
		} catch (Exception e) {
			throw new HelperBeanException(e.getMessage(), e);
		}
		return event;
	}

	protected EventResult dispatchEvent(Event event) throws EventException,
			SystemException, ApplicationException {
		return this.eventManager.dispatch(event);
	}

	public ServicePropertyHandler getServicePropertyHandler() {
		return this.serviceHandler;
	}
}
