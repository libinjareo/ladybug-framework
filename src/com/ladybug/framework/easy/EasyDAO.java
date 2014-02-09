package com.ladybug.framework.easy;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.ladybug.framework.base.data.DAOException;
import com.ladybug.framework.base.data.DBDAO;

/**
 * 轻量级Dao
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class EasyDAO extends DBDAO implements EasyDAOIF {

	/**
	 * 轻量级数据库连接封装类
	 */
	private EasyConnection easyconn = new EasyConnection();

	/**
	 * 取得数据库连接封装对象
	 * @return EasyConnection
	 * @throws DAOException
	 */
	public EasyConnection DB() throws DAOException {
		try {
			easyconn.conn = getConnection();
			return easyconn;
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}

	}

	@Override
	public void execute(String sql) throws DAOException {
		try {
			this.DB().execute(sql);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}

	@Override
	public List<Hashtable<String,Object>> fetch(String sql, int from, int num) throws DAOException {
		List<Hashtable<String,Object>> list = new ArrayList<Hashtable<String,Object>>();
		try {
			List<Hashtable<String,Object>> listTemp = this.DB().select(sql);
			int listSize = listTemp.size();
			for (int i = from - 1; i < listSize && i < from + num - 1; i++) {
				list.add(listTemp.get(i));
			}
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
		return list;
	}

	@Override
	public int getCount(String tableName, String whereSql) throws DAOException {
		try {
			return this.DB().getCount(tableName, whereSql);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}

	@Override
	public void insert(String tableName, EasyModel model) throws DAOException {
		try {
			this.DB().insert(tableName, model);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws DAOException {
		return this.DB().prepareStatement(sql);
	}

	@Override
	public List<Hashtable<String,Object>> select(String sql) throws DAOException {
		try {
			return this.DB().select(sql);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}

	}

	@Override
	public Hashtable<String,Object> selectFirst(String sql) throws DAOException {
		try {
			return this.DB().selectfirst(sql);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}

	@Override
	public void update(String tableName, String whereSql, EasyModel model)
			throws DAOException {
		try {
			this.DB().update(tableName, whereSql, model);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}

}
