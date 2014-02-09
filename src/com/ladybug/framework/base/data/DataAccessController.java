package com.ladybug.framework.base.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 数据传输(DAO)控制器
 * 
 * @author  james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class DataAccessController {
	
	/**
	 * DataConnector缓存
	 */
	private Map<String,DataConnector> connectors;
	
	/**
	 * 数据属性处理器
	 */
	private DataPropertyHandler handler;

	/**
	 * 构造函数,同时初始化数据库连接缓存和数据属性处理器
	 * 
	 * @param handler DataPropertyHandler
	 */
	public DataAccessController(DataPropertyHandler handler) {
		this.connectors = new HashMap<String,DataConnector>();
		this.handler = handler;
	}

	/**
	 * 取得数据库连接缓存
	 * @return
	 */
	protected Map<String,DataConnector> getDataConnectors() {
		return this.connectors;
	}

	/**
	 * 取得数据属性处理器
	 * @return
	 */
	public DataPropertyHandler getDataPropertyHandler() {
		return this.handler;
	}

	/**
	 * 取得Dao对象
	 * @param application data配置业务名称，如data-config-crm.xml中的crm
	 * @param key (dao-key)配置名称
	 * @param connectName data-config-相应业务.xml中的(connector-name)配置名称
	 * @return DAO DAO接口实现
	 * @throws DataPropertyException
	 * @throws DataConnectorException
	 * @throws DAOException
	 * @throws DataConnectException
	 */
	public Object getDAO(String application, String key, String connectName)
			throws DataPropertyException, DataConnectorException, DAOException,
			DataConnectException {
		//数据库连接对象
		Object connectorObject = null;
		//数据连接实现
		DataConnector connector = null;
		//配置的连接名称
		String connectorName = null;
		//数据库连接类名称
		String connectorClassName = null;
		//资源配置属性
		String resource = null;
		//dao名称
		String daoName = null;
		//dao对象
		Object daoObject = null;
		DAO result = null;

		//取得连接名称
		connectorName = this.handler
				.getConnectorName(application, key, connectName);
		if ((connectorName == null) || ("".equals(connectorName))) {
			connector = null;
		} else {
			//从缓存中取得数据连接对象
			connector = (DataConnector) this.connectors.get(connectorName);
			//如果缓存中不存在,
			if (connector == null) {
			//根据连接名称取得连接类名
				connectorClassName = this.handler
						.getConnectorClassName(connectorName);
				try {
					//创建数据连接对象
					connectorObject = Class.forName(connectorClassName)
							.newInstance();
				} catch (Exception e) {
					String message = null;
					try {
						message = ResourceBundle
								.getBundle(
										"com.ladybug.framework.base.data.i18n")
								.getString(
										"DataAccessController.FailedToCreateDataConnector");
					} catch (MissingResourceException ex) {

					}
					throw new DataConnectorException(message
							+ " : connector name = " + connectorName
							+ ", class = " + connectorClassName
							+ ", application = " + application + ", key = "
							+ key + ", connect = " + connectName, e);
				}
				
				//如果为数据连接接口实现，则进行强制转换
				if (connectorObject instanceof DataConnector) {
					connector = (DataConnector) connectorObject;
				} else {
					String message = null;
					try {
						message = ResourceBundle
								.getBundle(
										"com.ladybug.framework.base.data.i18n")
								.getString(
										"DataAccessController.DataConnectorNotExtended");
					} catch (MissingResourceException ex) {

					}
					throw new DataConnectorException(message + " : class = "
							+ connectorClassName + ", application = "
							+ application + ", key = " + key + ", connect = "
							+ connectName);

				}
				//为数据连接对象设置数据处理器
				connector.setDataPropertyHandler(handler);
				
				//放入DataConnector缓存中，以connectorName(connector-name配置属性值)为主键
				this.connectors.put(connectorName, connector);
			}
			
			//根据connectorName取得resource-name
			resource = this.handler.getConnectorResource(connectorName);
		}
		//取得Dao名称
		daoName = this.handler.getDAOName(application, key, connectName);
		try {
			//创建DAO对象
			daoObject = Class.forName(daoName).newInstance();
		} catch (Exception e) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.data.i18n").getString(
						"DataAccessController.FailedToCreateDAO");
			} catch (MissingResourceException ex) {

			}
			throw new DataConnectorException(message + " : class = " + daoName
					+ ", application = " + application + ", key = " + key
					+ ", connect = " + connectName, e);
		}
		//如果是DAO接口实现，则进行强制转换
		if (daoObject instanceof DAO) {
			result = (DAO) daoObject;
		} else {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.data.i18n").getString(
						"DataAccessController.DAONotImplemented");
			} catch (MissingResourceException ex) {

			}
			throw new DataConnectorException(message + " : class = " + daoName
					+ ", application = " + application + ", key = " + key
					+ ", connect = " + connectName);

		}

		//为Dao设置连接信息
		result.setConnectInfo(connector, resource, key, connectName);
		
		return result;
	}

	/**
	 * 提交
	 * 
	 * @throws DataConnectException
	 */
	public void commit() throws DataConnectException {
		Iterator<DataConnector> connectorIterator = this.connectors.values().iterator();
		DataConnectException exception = null;
		//遍历连接对象
		while (connectorIterator.hasNext()) {
			//取得数据连接对象
			DataConnector connector = (DataConnector) connectorIterator.next();

			try {
				//通过连接对象进行提交
				connector.commit();
				
			} catch (DataConnectException e) {
				exception = e;
			}

		}

		//判断异常
		if (exception != null) {
			throw exception;
		}
	}

	/**
	 * 回滚
	 * 
	 * @throws DataConnectException
	 */
	public void rollback() throws DataConnectException {
		Iterator<DataConnector> connectorIterator = this.connectors.values().iterator();
		DataConnectException exception = null;
		while (connectorIterator.hasNext()) {
			DataConnector connector = (DataConnector) connectorIterator.next();

			try {
				//通过连接对象进行回滚
				connector.rollback();
			} catch (DataConnectException e) {
				exception = e;
			}

		}

		if (exception != null) {
			throw exception;
		}
	}

	/**
	 * 释放资源，同时清空缓存
	 * @throws DataConnectException
	 */
	public void release() throws DataConnectException {
		Iterator<DataConnector> connectorIterator = this.connectors.values().iterator();
		DataConnectException exception = null;
		while (connectorIterator.hasNext()) {
			DataConnector connector = (DataConnector) connectorIterator.next();

			try {
				connector.release();
			} catch (DataConnectException e) {
				exception = e;
			}

		}
		
		//清空缓存
		this.connectors.clear();

		if (exception != null) {
			throw exception;
		}
	}
}
