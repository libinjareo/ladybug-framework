package com.ladybug.framework.easy;

import java.sql.PreparedStatement;
import java.util.Hashtable;
import java.util.List;

import com.ladybug.framework.base.data.DAOException;

/**
 * 轻量级Dao接口
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public interface EasyDAOIF {

	/**
	 * 查询
	 * @param sql 查询Sql语句
	 * @return
	 * @throws DAOException
	 */
	public List<Hashtable<String,Object>> select(String sql) throws DAOException;

	/**
	 * 查询第一条记录
	 * @param sql 查询Sql语句
	 * @return
	 * @throws DAOException
	 */
	public Hashtable<String,Object> selectFirst(String sql) throws DAOException;

	/**
	 * 执行
	 * @param sql Sql语句
	 * @throws DAOException
	 */
	public void execute(String sql) throws DAOException;

	/**
	 * 插入
	 * @param tableName 数据库表名称
	 * @param model 数据模型 
	 * @throws DAOException
	 */
	public void insert(String tableName, EasyModel model) throws DAOException;

	/**
	 * 更新
	 * @param tableName
	 * @param whereSql
	 * @param model
	 * @throws DAOException
	 */
	public void update(String tableName, String whereSql, EasyModel model)
			throws DAOException;

	/**
	 *  内存分页查询
	 * @param sql 查询Sql语句
	 * @param from
	 * @param num
	 * @return
	 * @throws DAOException
	 */
	public List<Hashtable<String,Object>> fetch(String sql, int from, int num) throws DAOException;

	/**
	 * 取得记录条数
	 * 
	 * @param tableName 数据库表名称
	 * @param whereSql where条件语句
	 * @return
	 * @throws DAOException
	 */
	public int getCount(String tableName, String whereSql) throws DAOException;

	
	/**
	 * 取得PreparedStatement
	 * @param sql sql语句
	 * @return
	 * @throws DAOException
	 */
	public PreparedStatement prepareStatement(String sql) throws DAOException;

}
