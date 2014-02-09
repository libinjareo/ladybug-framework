package com.ladybug.framework.base.service.container;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.ladybug.framework.base.service.DefaultTransition;
import com.ladybug.framework.base.service.ServiceController;
import com.ladybug.framework.base.service.ServiceControllerException;
import com.ladybug.framework.base.service.ServicePropertyException;
import com.ladybug.framework.base.service.ServicePropertyHandler;
import com.ladybug.framework.base.service.Transition;
import com.ladybug.framework.base.service.TransitionException;
import com.ladybug.framework.system.exception.ContainerException;
import com.ladybug.framework.system.property.PropertyHandlerException;
import com.ladybug.framework.system.property.PropertyManager;
import com.ladybug.framework.system.property.PropertyManagerException;

/**
 * 服务容器实现类,主要提供以下功能：<br>
 * (1)取得服务控制器ServiceController实例
 * (2)取得服务属性处理器ServicePropertyHandler实例
 * (3)取得转换器Transition实例
 * (4)初始化，通过资源管理器PropertyManager取得服务属性处理器
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class ServiceContainerImpl implements ServiceContainer {

	/**
	 * 服务属性处理器
	 */
	private ServicePropertyHandler servicePropertyHandler;

	@Override
	public ServiceController getServiceController(String application,
			String service, Locale locale) throws ServicePropertyException,
			ServiceControllerException {

		//控制器名称
		String name = null;
		//控制器实例对象，需要进行强制转换
		Object controllerObject = null;
		//服务控制器实例
		ServiceController controller = null;

		//取得控制器名称
		name = this.servicePropertyHandler.getServiceControllerName(
				application, service, locale);

		if (name == null) {
			controller = null;
		} else {
			try {
				//构建对象实例
				controllerObject = Class.forName(name).newInstance();
			} catch (Exception e) {
				String message = null;
				try {
					message = ResourceBundle.getBundle(
							"com.ladybug.framework.base.service.i18n")
							.getString(
									"ServiceManager.FailedToCreateController");
				} catch (MissingResourceException ex) {

				}
				throw new ServiceControllerException(message
						+ " : controller class = " + name + ", application = "
						+ application + ", service = " + service
						+ ", locale = " + locale, e);
			}

			if (controllerObject instanceof ServiceController) {
				//进行强制转换
				controller = (ServiceController) controllerObject;
			} else {
				String message = null;
				try {
					message = ResourceBundle.getBundle(
							"com.ladybug.framework.base.service.i18n")
							.getString("ServiceManager.ControllerImplemented");
				} catch (MissingResourceException ex) {

				}
				throw new ServiceControllerException(message
						+ " : controller class = " + name + ", application = "
						+ application + ", service = " + service
						+ ", locale = " + locale);

			}
		}
		return controller;
	}

	@Override
	public ServicePropertyHandler getServicePropertyHandler() {
		return this.servicePropertyHandler;
	}

	@Override
	public Transition getTransition(String application, String service,
			Locale locale) throws ServicePropertyException, TransitionException {
		String name = null;
		Object transitionObject = null;
		Transition transition = null;

		name = this.servicePropertyHandler.getTransitionName(application,
				service, locale);
		if (name == null) {
			transition = new DefaultTransition();
		} else {
			try {
				transitionObject = Class.forName(name).newInstance();
			} catch (Exception e) {
				String message = null;
				try {
					message = ResourceBundle.getBundle(
							"com.ladybug.framework.base.service.i18n")
							.getString(
									"ServiceManager.FailedToCreateTransition");
				} catch (MissingResourceException ex) {

				}
				throw new TransitionException(message
						+ " : transition class = " + name + ", application = "
						+ application + ", service = " + service
						+ ", locale = " + locale, e);
			}

			if (transitionObject instanceof Transition) {
				transition = (Transition) transitionObject;
			} else {
				String message = null;
				try {
					message = ResourceBundle.getBundle(
							"com.ladybug.framework.base.service.i18n")
							.getString("ServiceManager.TransitionExtended");
				} catch (MissingResourceException ex) {

				}
				throw new TransitionException(message
						+ " : transition class = " + name + ", application = "
						+ application + ", service = " + service
						+ ", locale = " + locale);

			}
		}

		return transition;
	}

	@Override
	public void init() throws ContainerException {
		PropertyManager propertyManager;
		try {
			propertyManager = PropertyManager.getPropertyManager();
		} catch (PropertyManagerException e) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.service.i18n")
						.getString("ServiceManager.FailedToGetPropertyManager");
			} catch (MissingResourceException ex) {

			}
			throw new ContainerException(message, e);
		}

		try {
			this.servicePropertyHandler = (ServicePropertyHandler) propertyManager
					.getPropertyHandler("service");
		} catch (PropertyHandlerException e) {
			String message = null;
			try {
				message = ResourceBundle
						.getBundle(
								"com.ladybug.framework.base.service.i18n")
						.getString(
								"ServiceManager.FailedToGetServicePropertyHandler");
			} catch (MissingResourceException ex) {

			}
			throw new ContainerException(message + " : " + "service", e);
		}
	}

}
