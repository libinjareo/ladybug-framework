package com.ladybug.framework.base.service;

import java.util.Locale;

import javax.servlet.RequestDispatcher;

import com.ladybug.framework.system.exception.SystemException;

/**
 * 默认转换器
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class DefaultTransition extends Transition {

	@Override
	public String getInputErrorPage(RequestException e)
			throws ServicePropertyException, TransitionException {
		Locale locale = getServiceManager().getLocale(getRequest(),
				getResponse());
		return getServiceManager().getServicePropertyHandler()
				.getInputErrorPagePath(getApplication(), getService(),
						e.getClass().getName(), locale);
	}

	@Override
	public String getNextPage() throws ServicePropertyException,
			TransitionException {
		return getNextPagePath();
	}

	@Override
	public String getServiceErrorPage(Exception e)
			throws ServicePropertyException, TransitionException {
		Locale locale = getServiceManager().getLocale(getRequest(),
				getResponse());
		return getServiceManager().getServicePropertyHandler()
				.getServiceErrorPagePath(getApplication(), getService(),
						e.getClass().getName(), locale);
	}

	@Override
	public String getSystemErrorPage(Exception e)
			throws ServicePropertyException, TransitionException {
		Locale locale = getServiceManager().getLocale(getRequest(),
				getResponse());
		return getServiceManager().getServicePropertyHandler()
				.getSystemErrorPagePath(getApplication(), getService(),
						e.getClass().getName(), locale);
	}

	@Override
	public void setInformation() throws TransitionException {

	}

	@Override
	public void transfer() throws SystemException {
		String nextPage = null;
		try {
			nextPage = getNextPage();
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}

		String requestURI = (String) getRequest().getAttribute(
				"javax.servlet.include.request_uri");

		String contextPath = (String) getRequest().getAttribute(
				"javax.servlet.include.context_path");

		String servletPath = (String) getRequest().getAttribute(
				"javax.servlet.include.servlet_path");

		RequestDispatcher dispatcher = getRequest().getRequestDispatcher(
				nextPage);
		try {
			if (((requestURI != null) && (!requestURI.equals("")))
					|| ((contextPath != null) && (!contextPath.equals("")))
					|| ((servletPath != null) && (!servletPath.equals("")))) {
				dispatcher.include(getRequest(), getResponse());
			} else {
				dispatcher.forward(getRequest(), getResponse());
			}
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}
	}

}
