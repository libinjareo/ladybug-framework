package com.ladybug.framework.base.data;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库连接抽象,继承自DataConnector,通过模板方法模式实现,真正创建数据库连接对象
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public abstract class DBConnector extends DataConnector {

	/**
	 * 数据库连接缓存
	 */
	protected Map<String,Connection> resources;

	/**
	 * 默认的构造函数
	 */
	public DBConnector() {
		resources = new HashMap<String,Connection>();
	}

	/**
	 * 根据配置文件属性，创建数据库连接对象，并放入缓存resources中,由子类实现
	 * 
	 * @param resource 资源属性，缓存key值,对应于data-config.xml中的resource-name标签属性
	 * @param params 属性参数数组
	 * @return
	 * @throws DataConnectException
	 */
	protected abstract Connection putResource(String resource,
			ResourceParam[] params) throws DataConnectException;

	@Override
	public void commit() throws DataConnectException {

	}

	@Override
	public Object getResource(String key, String connect, String resource)
			throws DataPropertyException, DataConnectException {
		//首先从缓存中取
		Object obj = this.resources.get(resource);
		if (obj == null) {
			ResourceParam[] resourceInfo = this.getDataPropertyHandler()
					.getResourceParams(resource);
			//如果缓存中不存在，则通过子类的putResource方法来实现
			obj = putResource(resource, resourceInfo);
		}
		return obj;
	}

	/**
	 * 取得数据库连接对象Connection
	 * @param resource 资源配置名称
	 * @return
	 * @throws DataPropertyException
	 * @throws DataConnectException
	 */
	public Connection getConnection(String resource)
			throws DataPropertyException, DataConnectException {
		return (Connection) getResource("", "", resource);
	}

	@Override
	public void release() throws DataConnectException {

	}

	@Override
	public void rollback() throws DataConnectException {

	}

}
