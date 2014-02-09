package com.ladybug.framework.base.data;

import com.ladybug.framework.system.property.PropertyHandler;

/**
 * 数据属性处理器
 * 
 * @author james.li(libinjareo@163.com)
 *
 */
public interface DataPropertyHandler extends PropertyHandler {

	/**
	 * 是否为动态创建
	 * @return
	 * @throws DataPropertyException
	 */
	public boolean isDynamic() throws DataPropertyException;

	/**
	 * 取得dao-config-相应业务.xml中的dao-class标签值
	 * @param application data配置业务名称，如data-config-crm.xml中的crm
	 * @param key (dao-key)配置名称
	 * @param connect data-config-相应业务.xml中的(connector-name)配置名称
	 * @return
	 * @throws DataPropertyException
	 */
	public String getDAOName(String application, String key, String connect)
			throws DataPropertyException;

	/**
	 * 取得connector-name
	 * 
	 * @param application data配置业务名称，如data-config-crm.xml中的crm
	 * @param key (dao-key)配置名称
	 * @param connect data-config-相关业务.xml中的(connector-name)配置名称
	 * @return
	 * @throws DataPropertyException
	 */
	public String getConnectorName(String application, String key,
			String connect) throws DataPropertyException;

	/**
	 * 取得data-config.xml配置文件中connector-class标签值
	 * 
	 * @param connectorName data-config-相关业务.xml中的(connector-name)配置名称
	 * @return
	 * @throws DataPropertyException
	 */
	public String getConnectorClassName(String connectorName)
			throws DataPropertyException;

	/**
	 * 取得dao-config.xml中的resource-name配置名称
	 * @param connectorName data-config-相关业务.xml中的(connector-name)配置名称
	 * @return
	 * @throws DataPropertyException
	 */
	public String getConnectorResource(String connectorName)
			throws DataPropertyException;

	/**
	 * 根据配置名称取得相关配置参数数组
	 * 
	 * @param name  相关业务的配置名称
	 * @throws DataPropertyException
	 */
	public ResourceParam[] getResourceParams(String name)
			throws DataPropertyException;
}
