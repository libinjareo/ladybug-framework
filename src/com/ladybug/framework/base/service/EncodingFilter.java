package com.ladybug.framework.base.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *  编码过滤器
 *  
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class EncodingFilter implements Filter {

	@SuppressWarnings("unused")
	private FilterConfig filterConfig;
	private ServiceManager manager;
	private ServicePropertyHandler handler;

	@Override
	public void destroy() {
		this.handler = null;
		this.filterConfig = null;
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		String encoding = null;
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession(false);

		try {
			encoding = manager.getEncoding(request, response);
		} catch (ServicePropertyException e) {
			throw new ServletException(e.getMessage(), e);
		}

		if (session != null) {
			try {
				session.setAttribute(this.handler.getEncodingAttributeName(),
						encoding);
			} catch (ServicePropertyException e) {
				throw new ServletException(e.getMessage(), e);
			}
		}

		if (Charset.isSupported(encoding)) {
			try {
				servletRequest.setCharacterEncoding(encoding);
			} catch (UnsupportedEncodingException e) {
				throw new ServletException(e.getMessage(), e);
			}

		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;

		try {
			this.manager = ServiceManager.getServiceManager();
		} catch (ServiceManagerException e) {
			throw new ServletException(e.getMessage(), e);
		}

		this.handler = this.manager.getServicePropertyHandler();
	}

}
