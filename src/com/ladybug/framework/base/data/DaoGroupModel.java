package com.ladybug.framework.base.data;

import java.util.HashMap;
import java.util.Map;

/**
 * DAO Group模型类，对应于data-config-应用.xml中的dao-group标签各属性值
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class DaoGroupModel {
	
	/**
	 * DaoModel缓存
	 */
	private Map<String,DaoModel> daoModels;
	
	/**
	 * dao-key
	 */
	private String daoKey;
	
	/**
	 * 默认的DaoModel
	 */
	private DaoModel defaultDao;

	public static final String ID = "dao-group";
	public static final String DATA_CONFIG = "data-config";
	public static final String P_ID_DAO_KEY = "dao-key";

	/**
	 * 默认的构造函数
	 */
	public DaoGroupModel() {
		this.daoModels = new HashMap<String,DaoModel>();
		this.defaultDao = new DaoModel();
	}

	/**
	 * 根据connector-name名称取得Dao实现类路径
	 * 
	 * @param connect connector-name缓存Key
	 * @return
	 */
	public String getDAOName(String connect) {
		String result = null;
		//首先从缓存中取
		DaoModel dao = (DaoModel) this.daoModels.get(connect);
		if (dao != null) {
			result = dao.getDaoClass();
		}
		if (result == null) {
			return this.defaultDao.getDaoClass();
		}

		return result;
	}

	/**
	 * 取得connector-name
	 * @param connect
	 * @return
	 */
	public String getConnectorName(String connect) {
		String result = null;
		//首先从缓存中取
		DaoModel dao = (DaoModel) this.daoModels.get(connect);
		if (dao != null) {
			result = dao.getConnectorName();
		}

		if (result == null) {
			return this.defaultDao.getConnectorName();
		}

		return result;
	}

	/**
	 * 设置DaoModel缓存
	 * @param connect connector-name
	 * @param dao DaoModel
	 */
	public void setDaoModel(String connect, DaoModel dao) {
		this.daoModels.put(connect, dao);
	}

	/**
	 * 取得dao-key
	 * @return
	 */
	public String getDaoKey() {
		return daoKey;
	}

	/**
	 * 设置dao-key
	 * @param daoKey
	 */
	public void setDaoKey(String daoKey) {
		this.daoKey = daoKey;
	}

	/**
	 * 取得默认的DaoModel
	 * @return
	 */
	public DaoModel getDefaultDao() {
		return defaultDao;
	}

	/**
	 * 设置默认的DaoModel
	 * @param defaultDao
	 */
	public void setDefaultDao(DaoModel defaultDao) {
		this.defaultDao = defaultDao;
	}

}
