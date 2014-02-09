package com.ladybug.framework.base.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.logicalcobwebs.proxool.ProxoolFacade;

import com.ladybug.framework.system.log.LogManager;

/**
 * ProxoolPool数据库连接池连接
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class ProxoolPoolConnector extends DBConnector {

	/**
	 * 数据库连接驱动
	 */
	public static final String KEY_DRIVER = "driver";
	
	/**
	 * 数据库连接url
	 */
	public static final String KEY_URL = "url";
	
	/**
	 * 数据库连接用户名 
	 */
	public static final String KEY_USERNAME = "username";
	
	/**
	 * 数据库连接密码
	 */
	public static final String KEY_PASSWORD = "password";
	
	/**
	 * 最小连接数量
	 */
	public static final String KEY_POOL_MINSIZE = "pool_minsize";
	
	/**
	 * 最大连接数量
	 */
	public static final String KEY_POOL_MAXSIZE = "pool_maxsize";
	
	/**
	 * 测试Sql
	 */
	public static final String KEY_TEST_SQL = "test_sql";

	/**
	 * 方言
	 */
	private String alias2 = null;
	private String alias = null;
	
	@Override
	protected Connection putResource(String resource, ResourceParam[] params)
			throws DataConnectException {
		String driver = null;
		String url = null;
		String username = null;
		String password = null;
		String pool_minsize = null;
		String pool_maxsize = null;
		String test_sql = null;
		
		String poolurl = null;

		Connection connection = null;

		//如果存在 配置参数
		if (params != null) {
			//遍历配置参数
			for (int i = 0; i < params.length; i++) {
				//参数名称
				String key = params[i].getName();
				//参数值
				String value = params[i].getValue();

				if ("driver".equals(key)) {
					driver = value;
				} else if ("url".equals(key)) {
					url = value;
				} else if ("username".equals(key)) {
					username = value;
				} else if ("password".equals(key)) {
					password = value;
				} else if ("pool-minsize".equals(key)) {
					pool_minsize = value;
				} else if ("pool-maxsize".equals(key)) {
					pool_maxsize = value;
				} else if ("alias".equals(key)) {
					alias = value;
				} else {
					if (!"test-sql".equals(key)) {
						continue;
					}
					test_sql = value;
				}
			}
		}
		
		//方言
		this.alias2 = "proxool." + alias;

		//以下为设置连接属性
		Properties info = new Properties();

		//设置最大连接数到属性proxool.maximum-connection-count中
		info.setProperty("proxool.maximum-connection-count", pool_maxsize);
		//设置最小连接数到属性proxool.minimum-connection-count中
		info.setProperty("proxool.minimum-connection-count", pool_minsize);
		if (test_sql != null && test_sql.length() > 1) {
			//设置测试Sql到属性proxool.house-keeping-test-sql
			info.setProperty("proxool.house-keeping-test-sql", test_sql);
		}
		//设置用户名到属性user
		info.setProperty("user", username);
		//设置密码到属性password
		info.setProperty("password", password);

		//连接池URLproxool.方言：驱动：url
		poolurl = "proxool." + alias + ":" + driver + ":" + url;

		try {
			//注册连接池驱动
			Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
			//根据URL和连接属性注册连接池
			ProxoolFacade.registerConnectionPool(poolurl, info);
		} catch (Exception e) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.huateng.web.framework.base.data.i18n").getString(
						"JDBCConnector.FailedToLoadProxoolPoolDriver");
			} catch (MissingResourceException ex) {

			}

			throw new DataConnectException(message + " : driver class = "
					+ driver, e);
		}

		try {
			//根据方言取得连接
			connection = DriverManager.getConnection(this.alias2);
		} catch (SQLException e) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.huateng.web.framework.base.data.i18n").getString(
						"Common.FailedToGetConnection");
			} catch (MissingResourceException ex) {

			}

			throw new DataConnectException(message + " : url = " + url, e);
		}

		try {
			//设置为非自动提交
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.huateng.web.framework.base.data.i18n").getString(
						"JDBCConnector.FailedToSetAutoCommit");
			} catch (MissingResourceException ex) {

			}

			throw new DataConnectException(message + " : url = " + url, e);

		}

		try {
			//以resource为Key值，把连接设置到缓存中
			this.resources.put(resource, connection);
		} catch (Exception e) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.huateng.web.framework.base.data.i18n").getString(
						"Common.FailedToAddConnection");
			} catch (MissingResourceException ex) {

			}

			throw new DataConnectException(message + " : url = " + url
					+ ", resource = " + resource, e);

		}
		//返回连接
		return connection;
	}

	@Override
	public void release() throws DataConnectException {
		DataConnectException exception = null;
		try {
			//根据方言关闭所有连接
			ProxoolFacade.killAllConnections(this.alias, "kill it by hand");
			
		} catch (Exception e) {

			LogManager.getLogManager().getLogAgent().sendMessage(
					JDBCConnector.class.getName(), "ERROR",
					DataManager.LOG_HEAD + e.getMessage(), e);

			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.huateng.web.framework.base.data.i18n").getString(
						"Common.FailedToReleaseResource");
			} catch (MissingResourceException ex) {

			}

			exception = new DataConnectException(message, e);

		}
		if (exception != null) {
			throw exception;
		}
	}
	/**
	 * 释放连接资源，清空连接池
	 * @throws DataConnectException
	 */
	public void shutdown() throws DataConnectException{
		this.release();
		try {
			ProxoolFacade.removeConnectionPool(this.alias2);
		} catch (Exception e) {
			throw new DataConnectException(e.getMessage(),e);
		}

	}

	
}
