package com.ladybug.framework.base.service;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ladybug.framework.base.service.container.ServiceContainer;
import com.ladybug.framework.base.service.container.factory.ServiceContainerFactory;
import com.ladybug.framework.system.exception.ContainerException;
import com.ladybug.framework.system.log.LogManager;
import com.ladybug.framework.util.NameUtil;

/**
 * 服务管理器,由单例模式来实现,主要实现以下功能：<br>
 * (1)getServicePropertyHandler:取得服务属性处理器<br>
 * (2)getServiceController:取得服务控制器<br>
 * (3)getTransition:取得转换器<br>
 * (4)getApplication:取得应用字符串<br>
 * (5)getService:取得服务字符串<br>
 * (6)getLocale:取得Locale<br>
 * (7)getEncoding:取得客户端编码<br>
 * 
 * 关联ServiceContainer
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 */
public class ServiceManager {
	/**
	 * 日志头部信息
	 */
	public static String LOG_HEAD = "[JEE][Service]";
	
	/**
	 * 服务属性处理key值
	 */
	
	public static final String SERVICE_PROPERTY_HANDLER_KEY = "service";
	
	/**
	 * 服务扩展名称key值
	 */
	public static final String SERVICE_EXTENSION_KEY = "com.ladybug.framework.base.service.ServiceManager.extesion";
	
	/**
	 * 默认的服务扩展名称
	 */
	public static final String DEFAULT_SERVICE_EXTENSION = "service";

	/**
	 * 静态Boolean实例，作为是否生成标志，用于单例实现
	 */
	private static Boolean managerFlag = new Boolean(false);
	
	/**
	 * 私有静态实例
	 */
	private static ServiceManager manager;
	
	/**
	 * 服务容器
	 */
	private ServiceContainer serviceContainer;

	/**
	 * 取得ServiceManager
	 * 
	 * @return ServiceManager实例
	 * @throws ServiceManagerException
	 */
	public static ServiceManager getServiceManager()
			throws ServiceManagerException {
		//初始false
		if (!managerFlag.booleanValue()) {
			//同步
			synchronized (managerFlag) {
				//如果没有创建
				if (!managerFlag.booleanValue()) {
					try {
						//新建实例
						manager = new ServiceManager();
					} catch (ServiceManagerException e) {
						String message = null;
						try {
							message = ResourceBundle
									.getBundle(
											"com.ladybug.framework.base.service.i18n")
									.getString(
											"ServiceManager.FailedToCreateManager");
						} catch (MissingResourceException ex) {

						}
						LogManager.getLogManager().getLogAgent().sendMessage(
								ServiceManager.class.getName(), "ERROR",
								LOG_HEAD + message);
						throw e;
					}
					
					//更改创建标志
					managerFlag = new Boolean(true);
					
					//记录成功信息
					String message = null;
					try {
						message = ResourceBundle.getBundle(
								"com.ladybug.framework.base.service.i18n")
								.getString(
										"ServiceManager.SuccessedToCreateManager");
					} catch (MissingResourceException ex) {

					}
					LogManager.getLogManager().getLogAgent().sendMessage(
							ServiceManager.class.getName(), "INFO",
							LOG_HEAD + message);
				}
			}
		}
		
		return manager;
	}

	/**
	 * 私有构造方法,创建服务容器
	 * 
	 * @throws ServiceManagerException
	 */
	private ServiceManager() throws ServiceManagerException {
		try {
			//创建服务容器
			this.serviceContainer = (ServiceContainer) new ServiceContainerFactory()
					.create();
			//创建成功信息
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.service.i18n")
						.getString("ServiceManager.SuccessedToCreateContainer");
			} catch (MissingResourceException es) {

			}
			//打印成功信息
			LogManager.getLogManager().getLogAgent().sendMessage(
					ServiceManager.class.getName(),
					"INFO",
					LOG_HEAD + message + " - "
							+ this.serviceContainer.getClass().getName());

		} catch (ContainerException e) {
			throw new ServiceManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 取得服务属性处理器，由serviceContainer实现
	 * @return ServicePropertyHandler
	 */
	public ServicePropertyHandler getServicePropertyHandler() {
		return this.serviceContainer.getServicePropertyHandler();
	}

	/**
	 *  通过ServicePropertyHandler取得服务控制器类名，然后创建服务控制器实例
	 *  
	 * @param application 应用
	 * @param service 服务
	 * @param locale Locale
	 * @return ServiceController
	 * @throws ServicePropertyException
	 * @throws ServiceControllerException
	 */
	public ServiceController getServiceController(String application,
			String service, Locale locale) throws ServicePropertyException,
			ServiceControllerException {
		return this.serviceContainer.getServiceController(application, service,
				locale);
	}

	/**
	 * 取得转换器
	 * @param application 应用
	 * @param service 服务
	 * @param locale Locale
	 * @return Transition
	 * @throws ServicePropertyException
	 * @throws TransitionException
	 */
	public Transition getTransition(String application, String service,
			Locale locale) throws ServicePropertyException, TransitionException {
		Transition transition = this.serviceContainer.getTransition(
				application, service, locale);
		if (transition != null) {
			//设置服务管理
			transition.setServiceManager(this);
			//设置应用
			transition.setApplication(application);
			//设置服务
			transition.setService(service);
		}
		return transition;
	}

	/**
	 * 取得应用字符串
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getApplication(HttpServletRequest request,
			HttpServletResponse response) throws ServicePropertyException {
		String application = null;
		String servletPath = request.getServletPath();
		String pathInfo = request.getPathInfo();
		String baseString;

		if (pathInfo == null) {
			if (servletPath.charAt(0) == '/') {
				//截取后缀
				baseString = servletPath.substring(1, servletPath
						.lastIndexOf("."));
			} else {
				baseString = servletPath.substring(0, servletPath
						.lastIndexOf("."));
			}
		} else {
			baseString = pathInfo;
		}

		String[] infoString = baseString.split("-", -1);
		if (infoString.length == 0) {
			return null;
		}
		if (NameUtil.isValidName(infoString[0])) {
			 //取"-"前的应用字符串
			application = infoString[0];
		}

		return application;
	}

	/**
	 * 取得服务字符串
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getService(HttpServletRequest request,
			HttpServletResponse response) throws ServicePropertyException {
		String service = null;

		String servletPath = request.getServletPath();
		String pathInfo = request.getPathInfo();
		String baseString;

		if (pathInfo == null) {
			if (servletPath.charAt(0) == '/') {
				baseString = servletPath.substring(1, servletPath
						.lastIndexOf("."));
			} else {
				baseString = servletPath.substring(0, servletPath
						.lastIndexOf("."));
			}
		} else {
			baseString = pathInfo;
		}

		String[] infoString = baseString.split("-", -1);
		if (infoString.length < 2) {
			return null;
		}
		if (NameUtil.isValidName(infoString[1])) {
			// //取"-"后的服务字符串
			service = infoString[1];
		}

		return service;
	}

	/**
	 * 取得Locale
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return
	 * @throws ServicePropertyException
	 */
	public Locale getLocale(HttpServletRequest request,
			HttpServletResponse response) throws ServicePropertyException {
		Locale locale = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
			//首先从Session中取
			String localeAttribute = getServicePropertyHandler()
					.getLocaleAttributeName();
			locale = (Locale) session.getAttribute(localeAttribute);
		}

		//从配置文件service-config.xml--->client-locale取
		if (locale == null) {
			locale = getServicePropertyHandler().getClientLocale();
		}

		if (locale == null) {
			locale = request.getLocale();
		}
		if (locale == null) {
			locale = Locale.getDefault();
		}
		return locale;
	}

	/**
	 * 取得客户端编码
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return
	 * @throws ServicePropertyException
	 */
	public String getEncoding(HttpServletRequest request,
			HttpServletResponse response) throws ServicePropertyException {
		String encoding = null;
		HttpSession session = request.getSession(false);
		
		//首先从Session中取
		if (session != null) {
			String encodingAttribute = getServicePropertyHandler()
					.getEncodingAttributeName();
			encoding = (String) session.getAttribute(encodingAttribute);
		}
		if (encoding == null) {
			//再取client-encoding配置
			encoding = getServicePropertyHandler().getClientEncoding();
		}
		if (encoding == null) {
			encoding = request.getCharacterEncoding();
		}

		return encoding;
	}

	public String getExtesion() throws ServiceManagerException{
		 return System.getProperty("com.ladybug.framework.base.service.ServiceManager.extesion", "service");
	}
	
}
