package com.ladybug.framework.base.data;

/**
 * DAO接口
 * @author james.li
 * @version 1.0
 *
 */
public interface DAO {
	
	/**
	 * 为DAO设置数据连接信息
	 * @param connector 数据连接实现
	 * @param resource resource-name
	 * @param key dao-key
	 * @param connect connector-name
	 * @throws DataConnectException
	 */
	public void setConnectInfo(DataConnector connector, String resource,
			String key, String connect) throws DataConnectException;
}
