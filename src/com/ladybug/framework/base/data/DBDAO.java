package com.ladybug.framework.base.data;

import java.sql.Connection;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 数据库DAO抽象类，关联DataConnector
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public abstract class DBDAO implements DAO {

	private DataConnector connector;
	private String resource;
	private Connection connection;

	public DBDAO() {
		this.connector = null;
		this.connection = null;
		this.resource = null;
	}

	@Override
	public void setConnectInfo(DataConnector connector, String resource,
			String key, String connect) throws DataConnectException {
		if (connector == null) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.huateng.web.framework.base.data.i18n").getString(
						"DBDAO.DataConnectorRequired");
			} catch (MissingResourceException e) {

			}
			throw new DataConnectException(message);
		}
		if (resource == null) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.huateng.web.framework.base.data.i18n").getString(
						"DBDAO.ResourceRequired");
			} catch (MissingResourceException e) {

			}
			throw new DataConnectException(message);
		}
		this.connector = connector;
		this.resource = resource;
	}

	/**
	 * 取得Connection
	 * @return
	 * @throws DataConnectException
	 * @throws DataPropertyException
	 */
	protected Connection getConnection() throws DataConnectException,
			DataPropertyException {
		if (this.connection == null) {
			this.connection = ((DBConnector) this.connector)
					.getConnection(this.resource);
		}

		return this.connection;
	}

}
