package com.ladybug.framework.base.data;

/**
 * 数据库配置连接模型,对应于data-config.xml文件
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class ConnectorModel {

	public static final String ID = "connector";
	public static final String P_ID_CONNECTOR_NAME = "connector-name";
	public static final String P_ID_CONNECTOR_CLASS = "connector-class";
	public static final String P_ID_RESOURCE_NAME = "resource-name";

	private String connectorName;
	private String connectorClassName;
	private String connectorResource;

	public String getConnectorName() {
		return connectorName;
	}

	public void setConnectorName(String connectorName) {
		this.connectorName = connectorName;
	}

	public String getConnectorClassName() {
		return connectorClassName;
	}

	public void setConnectorClassName(String connectorClassName) {
		this.connectorClassName = connectorClassName;
	}

	public String getConnectorResource() {
		return connectorResource;
	}

	public void setConnectorResource(String connectorResource) {
		this.connectorResource = connectorResource;
	}

}
