package com.ladybug.framework.base.data;

/**
 * 数据连接抽象，真正创建数据库连接(读取配置文件创建连接实例)
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0 
 */
public abstract class DataConnector {

	/**
	 * 数据属性处理器
	 */
	private DataPropertyHandler handler;

	/**
	 * 默认的构造函数
	 */
	public DataConnector() {
		this.handler = null;
	}

	/**
	 *  取得属性处理器
	 * @return  DataPropertyHandler
	 */
	public DataPropertyHandler getDataPropertyHandler() {
		return handler;
	}

	/**
	 * 设置数据处理器
	 * 
	 * @param handler DataPropertyHandler
	 */
	public void setDataPropertyHandler(DataPropertyHandler handler) {
		this.handler = handler;
	}

	/**
	 * 读取配置文件，获取资源对象，由子类实现
	 * @param key 预留参数
	 * @param connect 预留参数
	 * @param resource 资源配置名称，对对于data-config.xml配置文件中的resource-name配置属性
	 * @return Connection 数据库连接对象
	 * @throws DataPropertyException
	 * @throws DataConnectException
	 */
	public abstract Object getResource(String key,
			String connect, String resource)
			throws DataPropertyException, DataConnectException;

	/**
	 * 提交
	 * @throws DataConnectException
	 */
	public abstract void commit() throws DataConnectException;

	/**
	 * 回滚
	 * @throws DataConnectException
	 */
	public abstract void rollback() throws DataConnectException;

	/**
	 * 释放
	 * @throws DataConnectException
	 */
	public abstract void release() throws DataConnectException;

}
