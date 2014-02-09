package com.ladybug.framework.base.service.container;

import java.util.Locale;

import com.ladybug.framework.base.service.ServiceController;
import com.ladybug.framework.base.service.ServiceControllerException;
import com.ladybug.framework.base.service.ServicePropertyException;
import com.ladybug.framework.base.service.ServicePropertyHandler;
import com.ladybug.framework.base.service.Transition;
import com.ladybug.framework.base.service.TransitionException;
import com.ladybug.framework.system.container.Container;

/**
 * 服务容器接口,主要提供以下功能：<br>
 *  (1) getServicePropertyHandler:取得服务属性处理器<br>
 *  (2) getServiceController:取得服务控制器<br>
 *  (3) getTransition:取得转换器<br>
 *  
 * @author james.li(libinjareo.163.com)
 *
 */
public interface ServiceContainer extends Container {
	
	/**
	 * 服务属性处理Key
	 */
	public static final String SERVICE_PROPERTY_HANDLER_KEY = "service";

	/**
	 * 取得服务属性处理器
	 * 
	 * @return ServicePropertyHandler
	 */
	public ServicePropertyHandler getServicePropertyHandler();

	/**
	 * 取得服务控制器
	 * @param application 应用
	 * @param service 服务
	 * @param locale Locale
	 * @return ServiceController
	 * @throws ServicePropertyException
	 * @throws ServiceControllerException
	 */
	public ServiceController getServiceController(String application,
			String service, Locale locale)
			throws ServicePropertyException, ServiceControllerException;

	/**
	 * 取得转换器
	 * @param application 应用
	 * @param service 服务
	 * @param locale  Locale
	 * @return Transition
	 * @throws ServicePropertyException
	 * @throws TransitionException
	 */
	public Transition getTransition(String application, String service,
			Locale locale) throws ServicePropertyException,
			TransitionException;
}
