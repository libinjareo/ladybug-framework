package com.ladybug.framework.base.data.container;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.ladybug.framework.base.data.DataAccessController;
import com.ladybug.framework.base.data.DataPropertyHandler;
import com.ladybug.framework.system.exception.ContainerException;
import com.ladybug.framework.system.property.PropertyHandlerException;
import com.ladybug.framework.system.property.PropertyManager;
import com.ladybug.framework.system.property.PropertyManagerException;

/**
 * 数据容器实现,首先初始化取得数据属性处理器，然后构建DAO控制器
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class DataContainerImpl implements DataContainer {

	/**
	 * 数据属性处理器
	 */
	private DataPropertyHandler dataPropertyHandler;

	@Override
	public DataAccessController getDataAccessController() {
		return new DataAccessController(this.dataPropertyHandler);
	}

	@Override
	public DataPropertyHandler getDataPropertyHandler() {

		return this.dataPropertyHandler;
	}

	@Override
	public void init() throws ContainerException {
		PropertyManager propertyManager;
		try {
			//取得属性管理器
			propertyManager = PropertyManager.getPropertyManager();
		} catch (PropertyManagerException e) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.data.i18n").getString(
						"DataManager.FailedToGetPropertyManager");
			} catch (MissingResourceException ex) {

			}
			throw new ContainerException(message, e);
		}

		try {
			//通过属性管理器取得数据属性处理器
			this.dataPropertyHandler = (DataPropertyHandler) propertyManager
					.getPropertyHandler("data");
			
		} catch (PropertyHandlerException e) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.data.i18n").getString(
						"DataManager.FailedToGetDataPropertyHandler");
			} catch (MissingResourceException ex) {

			}
			throw new ContainerException(message + " : key = " + "data", e);

		}
	}

}
