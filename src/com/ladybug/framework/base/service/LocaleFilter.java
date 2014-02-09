package com.ladybug.framework.base.service;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LocaleFilter implements Filter {

	@SuppressWarnings("unused")
	private FilterConfig filterConfig;
	private ServiceManager manager;
	private ServicePropertyHandler handler;

	@Override
	public void destroy() {
		this.filterConfig = null;
		this.manager = null;
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		Locale locale = null;
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession(false);

		try {
			locale = this.manager.getLocale(request, response);
		} catch (ServicePropertyException e) {
			throw new ServletException(e.getMessage(), e);
		}
		if (session != null) {
			try {
				//把Locale放入缓存中
				session.setAttribute(this.handler.getLocaleAttributeName(),
						locale);
			} catch (ServicePropertyException e) {
				throw new ServletException(e.getMessage(), e);
			}
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		try {
			manager = ServiceManager.getServiceManager();
		} catch (ServiceManagerException e) {
			throw new ServletException(e.getMessage(), e);
		}

		this.handler = manager.getServicePropertyHandler();
	}

}
