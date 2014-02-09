package com.ladybug.framework.base.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.ladybug.framework.system.log.LogManager;

/**
 * JDBC数据库连接封装对象
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class JDBCConnector extends DBConnector {

	/**
	 * 数据库连接驱动
	 */
	public static final String KEY_DRIVER = "driver";
	
	/**
	 * 数据库连接URL
	 */
	public static final String KEY_URL = "url";
	
	/**
	 * 数据库连接用户名
	 */
	public static final String KEY_USERNAME = "username";
	
	/**
	 * 数据库连接用户名密码
	 */
	public static final String KEY_PASSWORD = "password";

	public void commit() throws DataConnectException {
		DataConnectException exception = null;
		//遍历缓存
		if (this.resources != null) {
			Iterator<Connection> connections = this.resources.values().iterator();
			while (connections.hasNext()) {
				Connection connection = (Connection) connections.next();
				try {
					//提交
					connection.commit();
				} catch (Exception e) {
					
					LogManager
							.getLogManager()
							.getLogAgent()
							.sendMessage(JDBCConnector.class.getName(),
									"ERROR",
									DataManager.LOG_HEAD + e.getMessage(), e);

					String message = null;
					try {
						message = ResourceBundle.getBundle(
								"com.ladybug.framework.base.data.i18n")
								.getString("Common.FailedToCommit");
					} catch (MissingResourceException ex) {

					}
					//构造数据连接异常
					exception = new DataConnectException(message, e);
				}
			}
		}
		//如果异常不为空，抛出异常
		if (exception != null) {
			throw exception;
		}
	}

	/**
	 * 回滚
	 */
	public void rollback() throws DataConnectException {
		DataConnectException exception = null;
		if (this.resources != null) {
			Iterator<Connection> connections = this.resources.values().iterator();
			while (connections.hasNext()) {
				Connection connection = (Connection) connections.next();
				try {
					//回滚
					connection.rollback();
				} catch (Exception e) {
					LogManager
							.getLogManager()
							.getLogAgent()
							.sendMessage(JDBCConnector.class.getName(),
									"ERROR",
									DataManager.LOG_HEAD + e.getMessage(), e);

					String message = null;
					try {
						message = ResourceBundle.getBundle(
								"com.ladybug.framework.base.data.i18n")
								.getString("Common.FailedToRollBack");
					} catch (MissingResourceException ex) {

					}

					exception = new DataConnectException(message, e);
				}
			}
		}

		if (exception != null) {
			throw exception;
		}
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
							.sendMessage(JDBCConnector.class.getName(),
									"ERROR",
									DataManager.LOG_HEAD + e.getMessage(), e);

					String message = null;
					try {
						message = ResourceBundle.getBundle(
								"com.ladybug.framework.base.data.i18n")
								.getString("Common.FailedToReleaseResource");
					} catch (MissingResourceException ex) {

					}

					exception = new DataConnectException(message, e);
				}
			}
		}

		if (exception != null) {
			throw exception;
		}
	}

	@Override
	protected Connection putResource(String resource, ResourceParam[] params)
			throws DataConnectException {
		//数据库连接驱动
		String driver = null;
		//数据库连接URL
		String url = null;
		//数据库连接用户名
		String username = null;
		//数据库连接密码
		String password = null;
		//数据库连接对象
		Connection connection = null;
		//如果数据库连接参数不为空
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				//参数名称
				String key = params[i].getName();
				//参数值
				String value = params[i].getValue();
				//以下为参数判断
				if ("driver".equals(key)) {
					driver = value;
				} else if ("url".equals(key)) {
					url = value;
				} else if ("username".equals(key)) {
					username = value;
				} else {
					if (!"password".equals(key)) {
						continue;
					}
					password = value;
				}
			}
		}

		try {
			//加载驱动
			Class.forName(driver);
		} catch (Exception e) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.data.i18n").getString(
						"JDBCConnector.FailedToLoadJDBCDriver");
			} catch (MissingResourceException ex) {

			}

			throw new DataConnectException(message + " : driver class = "
					+ driver, e);
		}

		try {
			//创建连接
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.data.i18n").getString(
						"Common.FailedToGetConnection");
			} catch (MissingResourceException ex) {

			}

			throw new DataConnectException(message + " : url = " + url, e);

		}

		try {
			//设置非自动提交
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.data.i18n").getString(
						"JDBCConnector.FailedToSetAutoCommit");
			} catch (MissingResourceException ex) {

			}

			throw new DataConnectException(message + " : url = " + url, e);

		}

		try {
			//把连接放入缓存中
			this.resources.put(resource, connection);
		} catch (Exception e) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.base.data.i18n").getString(
						"Common.FailedToAddConnection");
			} catch (MissingResourceException ex) {

			}

			throw new DataConnectException(message + " : url = " + url
					+ ", resource = " + resource, e);

		}
		//返回连接
		return connection;
	}

}
