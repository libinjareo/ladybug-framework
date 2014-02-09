package com.ladybug.framework.base.data;

import java.sql.Connection;

public class ProxoolPoolDBDAO implements DAO {

	private String resource;
	private ProxoolPoolConnector connector;
	private Connection connection;

	public ProxoolPoolDBDAO() {
		this.resource = null;
		this.connector = null;
		this.connection = null;
	}

	@Override
	public void setConnectInfo(DataConnector connector, String resource,
			String key, String connect) throws DataConnectException {
		this.resource = resource;
		this.connector = (ProxoolPoolConnector) connector;
	}

	public Connection getConnection() throws DataConnectException,
			DataPropertyException {
		if (this.connection == null) {
			this.connection = this.connector.getConnection(resource);
		}
		return this.connection;
	}
}
