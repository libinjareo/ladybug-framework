package com.ladybug.framework.base.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ladybug.framework.system.log.LogManager;

/**
 * 数据源连接封装
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class DataSourceConnector extends DBConnector {

	/**
	 * 提交
	 */
	public void commit() throws DataConnectException {
		super.commit();
	}

	/**
	 * 回滚
	 */
	public void rollback() throws DataConnectException {
		super.rollback();
	}

	/**
	 * 关闭连接，释放资源
	 */
	public void release() throws DataConnectException {
		DataConnectException exception = null;
		if (this.resources != null) {
			Iterator<Connection> connections = this.resources.values().iterator();
			while (connections.hasNext()) {
				Connection connection = (Connection) connections.next();
				try {
					//关闭连接
					connection.close();
				} catch (Exception e) {
					LogManager
							.getLogManager()
							.getLogAgent()
							.sendMessage(DataSourceConnector.class.getName(),
									"ERROR",
									DataManager.LOG_HEAD + e.getMessage(), e);

					String message = null;
					try {
						message = ResourceBundle.getBundle(
								"com.huateng.web.framework.base.data.i18n")
								.getString("Common.FailedToReleaseResource");
					} catch (MissingResourceException ex) {

					}
					exception = new DataConnectException(message, e);
				}
			}
		}
		if (exception != null)
			throw exception;
	}

	@Override
	protected Connection putResource(String resource, ResourceParam[] params)
			throws DataConnectException {
		//jndi名称
		String jndiName = null;
		//数据库连接
		Connection connection = null;
		//初始化上下文
		InitialContext context = null;
		//数据源对象
		DataSource dataSource = null;
		//遍历参数配置
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				//参数名称 
				String key = params[i].getName();
				//参数值
				String value = params[i].getValue();
				if (!"jndi".equals(key))
					continue;
				//设置jdni参数值
				jndiName = value;
			}
		}
		//如果jdniName没有配置，打印异常信息
		if ((jndiName == null) || ("".equals(jndiName))) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.huateng.web.framework.base.data.i18n").getString(
						"DataSourceConnector.LookupNameNotDeclared");
			} catch (MissingResourceException e) {

			}

			throw new DataConnectException(message + " : resource = "
					+ resource);
		}

		try {
			//初始化上下文
			context = new InitialContext();
			//根据jndiName查找数据源
			dataSource = (DataSource) context.lookup(jndiName);
		} catch (NamingException e) {
			throw new DataConnectException(e.getMessage(), e);
		}

		try {
			//从数据源中取得连接
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			throw new DataConnectException(e.getMessage(), e);
		}

		try {
			//以resouce为Key值把连接放入缓存
			this.resources.put(resource, connection);
		} catch (Exception e) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.huateng.web.framework.base.data.i18n").getString(
						"Common.FailedToAddConnection");
			} catch (MissingResourceException ex) {

			}

			throw new DataConnectException(message + " : resource = "
					+ resource);

		}
		//返回连接
		return connection;
	}

}
