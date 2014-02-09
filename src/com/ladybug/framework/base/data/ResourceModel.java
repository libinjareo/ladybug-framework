package com.ladybug.framework.base.data;

/**
 * 连接资源模型,对应于data-config.xml中的resource标签值
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class ResourceModel {
	public static final String ID = "resource";
	public static final String P_ID_RESOURCE_NAME = "resource-name";
	public static final String P_ID_RESOURCE_PARAM = "init-param";
	public static final String P_ID_PARAM_NAME = "param-name";
	public static final String P_ID_PARAM_VALUE = "param-value";

	private String connectorResource;
	private ResourceParam[] params;

	public String getConnectorResource() {
		return connectorResource;
	}

	public void setConnectorResource(String connectorResource) {
		this.connectorResource = connectorResource;
	}

	public ResourceParam[] getParams() {
		return params;
	}

	public void setParams(ResourceParam[] params) {
		this.params = params;
	}

}
