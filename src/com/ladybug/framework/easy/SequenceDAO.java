package com.ladybug.framework.easy;

import java.sql.PreparedStatement;
import java.util.Hashtable;
import java.util.List;

import com.ladybug.framework.base.data.DAOException;
import com.ladybug.framework.base.data.DBDAO;

/**
 * 序列DAO
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class SequenceDAO extends DBDAO implements SequenceDAOIF {

	/**
	 * 数据库连接封装对象
	 */
	private EasyConnection easyconn = new EasyConnection();

	/**
	 * 取得数据库连接
	 * @return
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
	public PreparedStatement prepareStatement(String sql) throws DAOException {
		return this.DB().prepareStatement(sql);
	}

	@Override
	public boolean execute(String sql) throws DAOException {
		PreparedStatement preparedStatment = this.prepareStatement(sql);
		boolean result  = false;
		try {
			result =  preparedStatment.execute(sql);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(),e);
		}
		
		return result;
		
	}

	@Override
	public List<Hashtable<String,Object>> select(PreparedStatement ps,String sql) throws DAOException {
		try {
			return this.DB().preparedStatemtSelect(ps,sql);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}

}
