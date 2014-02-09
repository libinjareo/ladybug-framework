package com.ladybug.framework.base.data;

/**
 * DAO模型类,对应于dao-config-应用.xml中的dao标签
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class DaoModel {
	public static final String ID = "dao";
	public static final String P_ID_CONNECT_NAME = "connect-name";
	public static final String P_ID_DAO_CLASS = "dao-class";
	public static final String P_ID_DAO_CONNECTOR_NAME = "connector-name";

	private String connectName;
	private String daoClass;
	private String connectorName;

	public String getConnectName() {
		return connectName;
	}

	public void setConnectName(String connectName) {
		this.connectName = connectName;
	}

	public String getDaoClass() {
		return daoClass;
	}

	public void setDaoClass(String daoClass) {
		this.daoClass = daoClass;
	}

	public String getConnectorName() {
		return connectorName;
	}

	public void setConnectorName(String connectorName) {
		this.connectorName = connectorName;
	}

}
