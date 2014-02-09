package com.ladybug.framework.base.service;

import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ladybug.framework.system.exception.ApplicationException;
import com.ladybug.framework.system.exception.SystemException;
import com.ladybug.framework.system.log.LogManager;

/**
 * 应用程序主控Servlet,用于解析如下格式的请求：<br>
 *  action="./msg-msgsend_check.service"
 *  
 *  @author james.li(libinjareo@163.com)
 *  @version 1.0
 */
public class ServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 请求字符串中应用与服务分隔符
	 */
	public static final String REQUEST_SEPARATOR = "-";

	/**
	 * 服务管理器
	 */
	private ServiceManager manager;
	
	/**
	 * 服务属性处理器
	 */
	private ServicePropertyHandler handler;

	/**
	 * 构造函数
	 * @see HttpServlet#HttpServlet()
	 */
	public ServiceServlet() {
		super();
	}

	/**
	 * 处理Get请求
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			execute(request,response);
		} catch (ServiceServletException e) {
			  throw new ServletException(e.getMessage(), e);
		} catch (ServicePropertyException e) {
			  throw new ServletException(e.getMessage(), e);
		} catch (ServiceControllerException e) {
			  throw new ServletException(e.getMessage(), e);
		} catch (TransitionException e) {
			  throw new ServletException(e.getMessage(), e);
		}
	}

	/**
	 * 处理Post请求
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			execute(request,response);
		} catch (ServiceServletException e) {
			  throw new ServletException(e.getMessage(), e);
		} catch (ServicePropertyException e) {
			  throw new ServletException(e.getMessage(), e);
		} catch (ServiceControllerException e) {
			  throw new ServletException(e.getMessage(), e);
		} catch (TransitionException e) {
			  throw new ServletException(e.getMessage(), e);
		}
	}

	/**
	 * 执行所有请求
	 * @param request HttpServletRequest请求对象
	 * @param response HttpServletResponse 响应对象
	 * @throws ServiceServletException 服务Servlet异常
	 * @throws ServicePropertyException 服务属性异常
	 * @throws ServiceControllerException 服务控制器异常
	 * @throws TransitionException 转换异常
	 * @throws ServletException
	 * @throws IOException
	 */
	private void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServiceServletException,
			ServicePropertyException, ServiceControllerException,
			TransitionException, ServletException, IOException {
		//应用
		String application = null;
		//服务
		String service = null;
		//服务控制器
		ServiceController controller = null;
		//转换器
		Transition transition = null;
		//服务结果对象
		ServiceResult result =null;
		//目标页面地址
		String nextPage = null;
		
		//取得Locale
		Locale locale = this.manager.getLocale(request, response);
		//取得应用字符串,如msg
		application = this.manager.getApplication(request, response);
		
		if((application == null) || ("".equals(application))){
			String errorMessage = null;
			try {
				errorMessage = ResourceBundle.getBundle(
						"com.ladybug.framework.base.service.i18n")
						.getString(
								"ServiceServlet.ApplicationNotRequested")+ " - " + request.getServletPath();
			} catch (MissingResourceException ex) {

			}
			
			ServiceServletException exception = new ServiceServletException(errorMessage);
			LogManager.getLogManager().getLogAgent().sendMessage(ServiceServlet.class.getName(),  "ERROR", ServiceManager.LOG_HEAD + errorMessage, exception);
			throw exception;
		}
		
		//取得服务字符串，如msgsend_check
		service = this.manager.getService(request, response);
		
		if((service == null) || ("".equals(service))){
			String errorMessage = null;
			try {
				errorMessage = ResourceBundle.getBundle(
						"com.ladybug.framework.base.service.i18n")
						.getString(
								"ServiceServlet.ServiceNotRequested")+ " - " + request.getServletPath();
			} catch (MissingResourceException ex) {

			}
			
			ServiceServletException exception = new ServiceServletException(errorMessage);
			LogManager.getLogManager().getLogAgent().sendMessage(ServiceServlet.class.getName(),  "ERROR", ServiceManager.LOG_HEAD + errorMessage, exception);
			throw exception;
		}
		
		//首先通过ServicePropertyHandler解析service-config-application参数.xml文件，查找service-id标签值为service的服务配置，取得controller-class标签值并创建实例
		controller = this.manager.getServiceController(application, service, locale);
		//取得转换器，如果没有配置transition-class，则默认为DefaultTransition
		transition = this.manager.getTransition(application, service, locale);
		transition.setRequest(request);
		transition.setResponse(response);
		
		//如果存在相应业务控制器
		if(controller != null){
			//设置请求
			controller.setRequest(request);
			//设置相应
			controller.setResponse(response);
			
			try {
				//进行检查操作
				controller.check();
			} catch (RequestException e) {
				nextPage = transition.getInputErrorPage(e);
				request.setAttribute(this.handler.getExceptionAttributeName(), e);
				getServletConfig().getServletContext().getRequestDispatcher(nextPage).forward(request, response);
				return;
			} catch (SystemException e) {
				LogManager.getLogManager().getLogAgent().sendMessage(ServiceServlet.class.getName(),  "ERROR", ServiceManager.LOG_HEAD + e.getMessage(), e);
				
				nextPage = transition.getSystemErrorPage(e);
				request.setAttribute(this.handler.getExceptionAttributeName(), e);
				getServletConfig().getServletContext().getRequestDispatcher(nextPage).forward(request, response);
				
				return;
			}
			
			try {
				//执行服务逻辑操作
				result = controller.service();
			} catch (SystemException e) {
				LogManager.getLogManager().getLogAgent().sendMessage(ServiceServlet.class.getName(),  "ERROR", ServiceManager.LOG_HEAD + e.getMessage(), e);
				
				nextPage = transition.getSystemErrorPage(e);
				request.setAttribute(this.handler.getExceptionAttributeName(), e);
				getServletConfig().getServletContext().getRequestDispatcher(nextPage).forward(request, response);
				
				return;

			} catch (ApplicationException e) {
				LogManager.getLogManager().getLogAgent().sendMessage(ServiceServlet.class.getName(),  "ERROR", ServiceManager.LOG_HEAD + e.getMessage(), e);
				
				nextPage = transition.getServiceErrorPage(e);
				request.setAttribute(this.handler.getExceptionAttributeName(), e);
				getServletConfig().getServletContext().getRequestDispatcher(nextPage).forward(request, response);
				
				return;

			}
		}
		
		// 为转换器设置服务结果
		transition.setResult(result);
		transition.setInformation();
		
		try {
			//进行页面的转发
			transition.transfer();
		} catch (SystemException e) {
			LogManager.getLogManager().getLogAgent().sendMessage(ServiceServlet.class.getName(),  "ERROR", ServiceManager.LOG_HEAD + e.getMessage(), e);
			
			nextPage = transition.getSystemErrorPage(e);
			request.setAttribute(this.handler.getExceptionAttributeName(), e);
			getServletConfig().getServletContext().getRequestDispatcher(nextPage).forward(request, response);
			
			return;
		}
	}

	@Override
	public void destroy() {
		this.manager = null;
		super.destroy();
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		try {
			this.manager = ServiceManager.getServiceManager();
		} catch (ServiceManagerException e) {
			throw new ServletException(e.getMessage(), e);
		}

		this.handler = this.manager.getServicePropertyHandler();
	}

}
