package com.ladybug.framework.easy;

import java.sql.PreparedStatement;
import java.util.Hashtable;
import java.util.List;

import com.ladybug.framework.base.data.DAOException;

/**
 * 序列DAO接口
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public interface SequenceDAOIF {
	
	/**
	 * 取得PreparedStatement
	 * @param sql 执行Sql语句
	 * @return
	 * @throws DAOException
	 */
	public PreparedStatement prepareStatement(String sql) throws DAOException;
	
	/**
	 * 执行Sql
	 * @param sql 执行Sql语句
	 * @return
	 * @throws DAOException
	 */
	public boolean execute(String sql) throws DAOException;
	
	/**
	 * 查询以列表对象返回
	 * @param ps
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public List<Hashtable<String,Object>> select(PreparedStatement ps,String sql) throws DAOException;
}
