package com.ladybug.framework.base.data;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.ladybug.framework.base.data.container.DataContainer;
import com.ladybug.framework.base.data.container.factory.DataContainerFactory;
import com.ladybug.framework.system.exception.ContainerException;
import com.ladybug.framework.system.log.LogManager;

/**
 * 数据管理器，单例模式实现，主要功能如下：
 * 1）取得 DataAccessController
 * 2）取得 DataPropertyHandler
 * 
 * @author libinjareo(libinjareo@163.com)
 * @version 1.0
 *
 */
public class DataManager {

	public static String LOG_HEAD = "[JEE][Data]";
	public static final String DATA_PROPERTY_HANDLER_KEY = "data";
	/**
	 * 是否创建实例标志，用户线程安全
	 */
	public static Boolean managerFlag = new Boolean(false);
	
	/**
	 * 私有静态实例
	 */
	private static DataManager manager;
	
	/**
	 * 数据容器
	 */
	private DataContainer dataContainer;

	/**
	 * 构造函数，通过数据容器工厂创建数据容器
	 * @throws DataManagerException
	 */
	private DataManager() throws DataManagerException {
		try {

			//通过数据容器创建工厂创建数据容器
			this.dataContainer = (DataContainer) new DataContainerFactory()
					.create();
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.data.i18n").getString(
						"DataManager.SuccessedToCreateContainer");
			} catch (MissingResourceException ignore) {

			}

			LogManager
					.getLogManager()
					.getLogAgent()
					.sendMessage(
							DataManager.class.getName(),
							"INFO",
							LOG_HEAD + message + " - "
									+ this.dataContainer.getClass().getName());
		} catch (ContainerException e) {
			throw new DataManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 入口方法，取得DataManager
	 * @return
	 * @throws DataManagerException
	 */
	public static DataManager getDataManager() throws DataManagerException {
		//如果没有创建过实例
		if (!managerFlag.booleanValue()) {
			//保证线程安全
			synchronized (managerFlag) {
				if (!managerFlag.booleanValue()) {
					try {
						//创建实例
						manager = new DataManager();
					} catch (DataManagerException e) {
						String message = null;
						try {
							message = ResourceBundle
									.getBundle(
											"com.ladybug.framework.base.data.i18n")
									.getString(
											"DataManager.FailedToCreateDataManager");
						} catch (MissingResourceException ex) {

						}

						LogManager
								.getLogManager()
								.getLogAgent()
								.sendMessage(DataManager.class.getName(),
										"ERROR", LOG_HEAD + message, e);
						throw e;
					}
					
					//更改唯一实例标志
					managerFlag = new Boolean(true);
					
					//打印创建成功信息
					String message = null;
					try {
						message = ResourceBundle
								.getBundle(
										"com.ladybug.framework.base.data.i18n")
								.getString(
										"DataManager.SuccessToCreateDataManager");
					} catch (MissingResourceException ex) {

					}

					LogManager
							.getLogManager()
							.getLogAgent()
							.sendMessage(DataManager.class.getName(), "INFO",
									LOG_HEAD + message);

				}
			}
		}
		return manager;
	}

	/**
	 * 创建DAO控制器DataPropertyHandler,主要通过DataContainer来创建
	 * 
	 * @return DataAccessController
	 */
	public DataAccessController getDataAccessController() {
		return this.dataContainer.getDataAccessController();
	}

	/**
	 * 创建DataPropertyHandler,主要通过DataContainer来创建
	 * @return
	 */
	public DataPropertyHandler getDataPropertyHandler() {
		return this.dataContainer.getDataPropertyHandler();
	}
}
