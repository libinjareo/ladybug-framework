package com.ladybug.framework.easy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import com.ladybug.framework.base.data.DAOException;
import com.ladybug.framework.base.data.DataAccessException;
import com.ladybug.framework.easy.tools.EscapeTools;

/**
 * 轻量级数据库连接封装类
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class EasyConnection {

	/**
	 * 数据库连接对象
	 */
	protected Connection conn;
	protected String DBType = "Oracle";

	/**
	 * 取得查询结果数量
	 * @param tableName 数据库表名称
	 * @param whereSql 条件字符串
	 * @return
	 * @throws DataAccessException
	 */
	public int getCount(String tableName, String whereSql)
			throws DataAccessException {
		Statement stmt = null;
		ResultSet result = null;
		int intReturn = 0;
		StringBuffer strSql = null;

		if (tableName == null || tableName.equals("")) {
			return -1;
		}
		strSql = new StringBuffer("SELECT * FROM " + tableName);
		if (whereSql != null && !whereSql.equals("")) {
			strSql.append("  WHERE " + whereSql);
		}

		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery(strSql.toString());
			while (result.next()) {
				intReturn++;
			}
		} catch (SQLException e) {
			intReturn = 0;
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			try {
				if (result != null) {
					result.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				intReturn = 0;
			}
		}

		return intReturn;
	}

	/**
	 * 数据库插入操作
	 * @param tableName  数据库表名称
	 * @param model 数据模型
	 * @return
	 * @throws DataAccessException
	 */
	public int insert(String tableName, EasyModel model)
			throws DataAccessException {
		Statement stmt = null;
		String strSql = this.getSQLFromEasyModel(tableName, null, model, false);

		int intReturn = 0;
		if (strSql == null || strSql.equals("")) {
			return -1;
		}

		try {
			stmt = conn.createStatement();
			intReturn = stmt.executeUpdate(strSql);
		} catch (SQLException e) {
			intReturn = -1;
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				intReturn = -1;
			}
		}

		return intReturn;
	}

	/**
	 * 数据库更新操作
	 * @param tableName 数据库表名称
	 * @param whereString where条件字符串
	 * @param model 数据模型
	 * @return
	 * @throws DataAccessException
	 */
	public int update(String tableName, String whereString, EasyModel model)
			throws DataAccessException {
		Statement stmt = null;
		String strSql = this.getSQLFromEasyModel(tableName, whereString, model,
				true);

		int intReturn = 0;
		if (strSql == null || strSql.equals("")) {
			return -1;
		}

		try {
			stmt = conn.createStatement();
			intReturn = stmt.executeUpdate(strSql);
		} catch (SQLException e) {
			intReturn = -1;
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				intReturn = -1;
			}
		}

		return intReturn;
	}

	/**
	 * 取得对象值
	 * @param objectTemp
	 * @return
	 */
	public Object getObjectNoNULL(Object objectTemp) {
		if (objectTemp == null) {
			return "";
		}
		return objectTemp;
	}

	/**
	 * 执行
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 */
	public boolean execute(String sql) throws DataAccessException {
		Statement stmt = null;
		boolean blnReturn = false;

		if ((sql == null) || ("").equals(sql)) {
			return false;
		}

		try {
			stmt = conn.createStatement();
			blnReturn = stmt.execute(sql);
		} catch (SQLException e) {
			blnReturn = false;
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException ex) {
				blnReturn = false;
			}
		}

		return blnReturn;
	}

	/**
	 * 从数据模型构建数据库操作字符串
	 * @param tableName 数据库表名称
	 * @param whereString where查询条件
	 * @param easyModel 数据模型
	 * @param isUpdate 是否是更新操作
	 * @return
	 */
	public String getSQLFromEasyModel(String tableName, String whereString,
			EasyModel easyModel, boolean isUpdate) {
		StringBuffer strSqlBuffer = new StringBuffer();
		StringBuffer strInsertValuesBuffer = new StringBuffer();
		Hashtable<String, Object> hashtable = null;
		boolean isFirstKey = true;
		String strKey = "";
		String strValue = "";

		if (easyModel != null) {
			hashtable = easyModel.getHashTable();
		}
		if (hashtable == null || hashtable.isEmpty()) {
			return null;
		}

		if (tableName == null || "".equals(tableName)) {
			return null;
		}

		for (Enumeration<String> e = hashtable.keys(); e.hasMoreElements();) {
			strKey = EscapeTools.toSqlString(e.nextElement().toString());
			strValue = EscapeTools
					.toSqlString(hashtable.get(strKey).toString());
			if (strValue == null) {
				strValue = "";
			}

			strValue = "'" + strValue + "'";

			if (isUpdate) {
				if (isFirstKey) {
					isFirstKey = false;
					strSqlBuffer.append("UPDATE" + tableName + " SET ");
					strSqlBuffer.append(strKey + "=" + strValue);
				} else {
					strSqlBuffer.append("," + strKey + "=" + strValue);
				}
			} else {
				if (isFirstKey) {
					isFirstKey = false;
					strSqlBuffer.append("INSERT INTO " + tableName + "  (");
					strSqlBuffer.append(strKey);
					strInsertValuesBuffer.append(" VALUES  (" + strValue);
				} else {
					strSqlBuffer.append("," + strKey);
					strInsertValuesBuffer.append("," + strValue);
				}
			}
		}

		if (isUpdate) {
			if (whereString != null && !("").equals(whereString)) {
				strSqlBuffer.append(" WHERE " + whereString);
			}
		} else {
			strSqlBuffer.append(")" + strInsertValuesBuffer.toString() + ")");
		}

		return strSqlBuffer.toString();
	}

	/**
	 * 查询第一条记录
	 * 
	 * @param sql 查询Sql
	 * @return
	 * @throws DataAccessException
	 */
	public Hashtable<String,Object> selectfirst(String sql) throws DataAccessException {
		Statement stmt = null;
		ResultSet result = null;
		Hashtable<String,Object> table = null;
		String strColumnName = null;

		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = result.getMetaData();

			int numberOfColumns = rsmd.getColumnCount();

			if (result.next()) {
				table = new Hashtable<String,Object>();
				for (int i = 1; i < numberOfColumns; i++) {
					strColumnName = rsmd.getColumnName(i);
					table.put(strColumnName.toLowerCase(),
							getObjectNoNULL(result.getObject(i)));
				}
			}
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			try {
				if (result != null) {
					result.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.getMessage();
			}
		}

		return table;
	}

	/**
	 * 查询记录，以List返回
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 */
	public List<Hashtable<String,Object>> select(String sql) throws DataAccessException {
		Statement stmt = null;
		ResultSet result = null;
	
		List<Hashtable<String,Object>> list = new ArrayList<Hashtable<String,Object>>();
		String strColumnName = null;

		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = result.getMetaData();

			int numberOfColumns = rsmd.getColumnCount();

			while (result.next()) {
				Hashtable<String,Object> hashtable = new Hashtable<String,Object>();
				for (int i = 1; i <= numberOfColumns; i++) {
					strColumnName = rsmd.getColumnName(i);
					hashtable.put(strColumnName.toLowerCase(),
							getObjectNoNULL(result.getObject(i)));
				}
				list.add(hashtable);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			try {
				if (result != null) {
					result.close();
				}
				if (stmt != null) {
					stmt.close();
				}

			} catch (SQLException e) {
				e.getMessage();
			}
		}

		return list;
	}

	/**
	 * 根据Sql构建PreparedStatement
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public PreparedStatement prepareStatement(String sql) throws DAOException {
		try {
			return conn.prepareStatement(sql);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}
	
	/**
	 * 用PreparedStatement查询所有记录
	 * @param pstmt PreparedStatement
	 * @param sql 查询Sql语句
	 * @return
	 * @throws DataAccessException
	 */
	public List<Hashtable<String,Object>> preparedStatemtSelect(PreparedStatement pstmt,String sql) throws DataAccessException {
		ResultSet result = null;
		List<Hashtable<String,Object>> list = new ArrayList<Hashtable<String,Object>>();
		String strColumnName = null;

		try {
			result = pstmt.executeQuery(sql);
			
			ResultSetMetaData rsmd = result.getMetaData();

			int numberOfColumns = rsmd.getColumnCount();

			while (result.next()) {
				Hashtable<String,Object> hashtable = new Hashtable<String,Object>();
				for (int i = 1; i <= numberOfColumns; i++) {
					strColumnName = rsmd.getColumnName(i);
					hashtable.put(strColumnName.toLowerCase(),
							getObjectNoNULL(result.getObject(i)));
				}
				list.add(hashtable);
			}
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			try {
				if (result != null) {
					result.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}

			} catch (SQLException e) {
				e.getMessage();
			}
		}

		return list;
	}
}
