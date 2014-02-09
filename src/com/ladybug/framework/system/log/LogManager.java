package com.ladybug.framework.system.log;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.ladybug.framework.system.property.PropertyHandlerException;
import com.ladybug.framework.system.property.PropertyManager;
import com.ladybug.framework.system.property.PropertyManagerException;

/**
 * Log管理者，单例模式实现
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class LogManager {
	public static String LOG_HEAD = "[JEE][Log]";
	public static final String LOG_PROPERTY_HANDLER_KEY = "key";
	
	/**
	 * 是否创建实例标志
	 */
	private static Boolean managerFlag = new Boolean(false);

	/**
	 * 私有静态实例
	 */
	private static LogManager manager = null;
	
	/**
	 * 日志属性处理器
	 */
	private LogPropertyHandler logPropertyHandler = null;
	
	/**
	 * 日志代理
	 */
	private LogAgent agent = null;

	/**
	 * 私有构造函数,根据PropertyManager创建LogPropertyHandler和LogAgent
	 */
	private LogManager() {
		PropertyManager propertyManager = null;
		Object agentObject = null;
		String className = null;
		LogAgent tempAgent = null;

		//取得PropertyManager
		try {
			propertyManager = PropertyManager.getPropertyManager();
		} catch (PropertyManagerException e) {
			this.agent = new DefaultLogAgent();
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.system.log.i18n").getString(
						"LogManager.FailedToGetPropertyManager");
			} catch (MissingResourceException ex) {
			}

			this.agent.sendMessage(LogManager.class.getName(), "ERROR", message
					+ " : " + "log", e);
			return;
		}

		//取得LogPropertyHandler
		try {
			this.logPropertyHandler = (LogPropertyHandler) propertyManager
					.getPropertyHandler("log");
		} catch (PropertyHandlerException e) {
			this.agent = new DefaultLogAgent();
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.system.log.i18n").getString(
						"LogManager.FailedToGetLogPropertyHandler");
			} catch (MissingResourceException ex) {
			}

			this.agent.sendMessage(LogManager.class.getName(), "WARNNING",
					message + " : " + "log", e);
			return;
		}

		//取得日志代理类名称
		try {
			className = this.logPropertyHandler.getLogAgentName();
		} catch (LogPropertyException e) {
			this.agent = new DefaultLogAgent();
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.system.log.i18n").getString(
						"LogManager.FailedToGetAgentName");
			} catch (MissingResourceException ex) {
			}

			this.agent.sendMessage(LogManager.class.getName(), "ERROR", message
					+ " : " + "log", e);
			return;

		}

		if (className != null) {
			try {
				//创建代理实例
				agentObject = Class.forName(className).newInstance();
			} catch (Exception e) {
				this.agent = new DefaultLogAgent();
				String message = null;
				try {
					message = ResourceBundle.getBundle(
							"com.ladybug.framework.system.log.i18n")
							.getString("LogManager.FailedToCreateAgent");
				} catch (MissingResourceException ex) {
				}

				this.agent.sendMessage(LogManager.class.getName(), "ERROR",
						message + " : " + "log", e);
				return;
			}
			
			//强制转换
			if (agentObject instanceof LogAgent) {
				tempAgent = (LogAgent) agentObject;
			} else {
				this.agent = new DefaultLogAgent();
				String message = null;
				try {
					message = ResourceBundle.getBundle(
							"com.ladybug.framework.system.log.i18n")
							.getString("LogManager.AgentImplemented");
				} catch (MissingResourceException ex) {
				}

				this.agent.sendMessage(LogManager.class.getName(), "ERROR",
						message + " (" + agentObject.getClass().getName()
								+ ") : " + "log");
				return;

			}

		}

		
		//取得日志参数数组
		LogAgentParam[] params = null;
		try {
			params = this.logPropertyHandler.getLogAgentParams();

		} catch (LogPropertyException e) {
			this.agent = tempAgent;
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.system.log.i18n").getString(
						"LogManager.FailedToGetAgentParameters");
			} catch (MissingResourceException ex) {
			}

			this.agent.sendMessage(LogManager.class.getName(), "ERROR", message
					+ " : " + "log", e);
			return;
		}

		//为代理设置日志参数数组
		tempAgent.init(params);
		
		this.agent = tempAgent;
	}

	/**
	 * 入口方法，取得日志管理者
	 * @return
	 */
	public static LogManager getLogManager() {
		//如果还没有创建实例
		if (!managerFlag.booleanValue()) {
			//保持线程安全
			synchronized (managerFlag) {
				String message = null;
				//如果没有创建
				if (!managerFlag.booleanValue()) {
					manager = new LogManager();
					//设置创建标志
					managerFlag = new Boolean(true);
				}
				
				//打印创建成功信息
				try {
					message = ResourceBundle
							.getBundle(
									"com.ladybug.framework.system.log.i18n")
							.getString("LogManager.SuccessedToCreateManager");
				} catch (MissingResourceException e) {

				}

				manager.getLogAgent().sendMessage(LogManager.class.getName(),
						"INFO", LOG_HEAD + message);
			}
		}
		return manager;
	}

	/**
	 * 取得日志代理对象
	 * @return
	 */
	public LogAgent getLogAgent() {
		return this.agent;
	}
}
