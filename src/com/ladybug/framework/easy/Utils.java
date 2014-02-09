package com.ladybug.framework.easy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 工具类 
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class Utils {

	/**
	 * 字符串数组转换成ArrayList
	 * 
	 * @param array 字符串数组
	 * @return
	 */
	public static ArrayList<String> arrayToList(String[] array) {
		if (array == null) {
			return null;
		}
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
		return list;
	}

	/**
	 * ArrayList转换成字符串数组
	 * 
	 * @param arrayList ArrayList
	 * @return
	 */
	public static String[] ListToArray(ArrayList<String> arrayList) {
		if (arrayList == null) {
			return null;
		}
		String[] Array = new String[arrayList.size()];
		for (int i = 0; i < arrayList.size(); i++) {
			Array[i] = (String) arrayList.get(i);
		}
		return Array;
	}

	/**
	 * 通过Event设置数据模型
	 * 
	 * @param model EasyModel
	 * @param temp key值数组
	 * @param event EasyEvent
	 * @param prefix key值前缀
	 * @param ForSql 是否是sql前缀
	 * @return
	 */
	public static EasyModel setModelByEvent(EasyModel model, String[] temp,
			EasyEvent event, String prefix, boolean ForSql) {
		for (int key = 0; key < temp.length; key++) {
			if (ForSql == true) {
				model.add(temp[key], event.Get(prefix + temp[key]));
			} else {
				model.add(prefix + temp[key], event.Get(prefix + temp[key]));
			}
		}
		return model;
	}

	/**
	 * 通过字段数组设置Where查询条件
	 * 
	 * @param prefix 字段前缀
	 * @param where where查询条件
	 * @param tableName 数据库表名
	 * @param fields 字段数组
	 * @param event EasyEvent
	 * @param DeleteEnd 是否以Delete结束
	 * @param ANDExists 是否是第一个where条件语句
	 * @return String
	 */
	public static String setWhereSqlByFields(String prefix, String where,
			String tableName, String[] fields, EasyEvent event,
			boolean DeleteEnd, boolean ANDExists) {
		StringBuffer sbWhere = new StringBuffer(where);
		boolean ANDExists1 = ANDExists;
		for (int i = 0; i < fields.length; i++) {
			if (event.GetForSql(prefix + fields[i]) != null) {
				if (event.GetForSql(prefix + fields[i]).length() > 0) {
					if (ANDExists1 == true) {
						sbWhere.append(" ");
						sbWhere.append(tableName);
						sbWhere.append(".");
						sbWhere.append(fields[i]);
						sbWhere.append("='");
						sbWhere.append(event.GetForSql(prefix + fields[i]));
						sbWhere.append("' ");
						ANDExists1 = false;
					} else {
						sbWhere.append(" AND ");
						sbWhere.append(tableName);
						sbWhere.append(".");
						sbWhere.append(fields[i]);
						sbWhere.append("='");
						sbWhere.append(event.GetForSql(prefix + fields[i]));
						sbWhere.append("' ");
					}
				}
			}
		}
		if (DeleteEnd == true) {
			if (where.endsWith("AND")) {
				sbWhere = new StringBuffer("");
				sbWhere.append(where.substring(0, where.length() - 3));
			}
		}
		return sbWhere.toString();
	}

	/**
	 * 根据字符串数据取得查询字符串
	 * @param prefix 前缀
	 * @param tableName 数据库表名称
	 * @param fields 字段数组
	 * @param posBefore 是否前置逗号
	 * @return String
	 */
	public static String getSelectSqlByFields(String prefix, String tableName,
			String[] fields, boolean posBefore) {
		StringBuffer sql = new StringBuffer();
		sql.append(" ");
		for (int i = 0; i < fields.length; i++) {
			if (!"".equals(fields[i])) {
				if (posBefore) {
					sql.append(",");
				}
				sql.append(tableName + "." + fields[i] + " AS " + prefix
						+ fields[i]);
				if (!posBefore) {
					sql.append(",");
				}
			}
		}
		sql.append(" ");
		return sql.toString();
	}

	/**
	 * 取得列字符串
	 * @param list 列数组
	 * @param Comma 是否前置逗号
	 * @return String
	 */
	public static String getColumnsStrForSQL(String[] list, boolean Comma) {
		StringBuffer sqls = new StringBuffer();

		if (list == null || list.length == 0) {
			return "";
		}
		for (int i = 0; i < list.length; i++) {
			if (list[i] != null && !list[i].equals("")) {
				if (Comma == true) {
					sqls.append("," + list[i]);
				} else {
					sqls.append(list[i] + ",");
				}
			}
		}
		return sqls.toString();
	}

	/**
	 * 构建数据库字符串
	 * 
	 * @param list
	 * @param prefix
	 * @param event
	 * @param Comma
	 * @return
	 */
	public static String getTextValuesFromEventForSQL(String[] list,
			String prefix, EasyEvent event, boolean Comma) {
		StringBuffer values = new StringBuffer();

		if (list == null || list.length == 0) {
			return "";
		}
		for (int i = 0; i < list.length; i++) {
			if (list[i] != null && !list[i].equals("")) {
				if (Comma == true) {
					values.append(",'" + event.GetForSql(prefix + list[i])
							+ "'");
				} else {
					values.append("'" + event.GetForSql(prefix + list[i])
							+ "',");
				}
			}
		}
		return values.toString();
	}

	/**
	 *  从字段数组中取得查询字符串
	 * @param fields
	 * @return
	 */
	public static String getSelectSqlByFields(String[] fields) {
		StringBuffer sql = new StringBuffer();
		sql.append(" ");
		for (int i = 0; i < fields.length; i++) {
			if (i == 0) {
				sql.append(fields[i]);
			} else {
				sql.append("," + fields[i]);
			}
		}
		sql.append(" ");
		return sql.toString();
	}

	/**
	 * 通过Hashtable为Model设值
	 * @param model EasyModel
	 * @param temp Hashtable
	 * @param prefix 前缀
	 * @param keys 字符串数组 
	 * @return EasyModel
	 */
	public static EasyModel setModelByHashtable(EasyModel model,
			Hashtable<String,Object> temp, String prefix, String[] keys) {
		if (temp == null) {
			return model;
		}
		for (int i = 0; i < keys.length; i++) {
			model.add(prefix + keys[i], temp.get(prefix + keys[i]));
		}
		return model;
	}

	/**
	 * 设置模型值
	 * @param model EasyModel
	 * @param temp 取值EasyModel
	 * @param prefix 前缀
	 * @param keys 值数组
	 * @param isPrefix 是否要加前缀
	 * @return
	 */
	public static EasyModel setModelByModel(EasyModel model, EasyModel temp,
			String prefix, String[] keys, boolean isPrefix) {
		for (int i = 0; i < keys.length; i++) {
			if (isPrefix == true) {
				model.add(prefix + keys[i], temp.get(prefix + keys[i]));
			} else {
				model.add(keys[i], temp.get(prefix + keys[i]));
			}
		}
		return model;
	}

	/**
	 * 设置请求值
	 * @param model EasyModel
	 * @param request HttpServletRequest
	 * @param prefix 前缀
	 * @param keys 参数值
	 * @return
	 */
	public static EasyModel setModelByRequest(EasyModel model,
			HttpServletRequest request, String prefix, String[] keys) {
		for (int i = 0; i < keys.length; i++) {
			model.add(prefix + keys[i], request.getParameter(prefix + keys[i]));
		}
		return model;
	}

	/**
	 * 设置模型
	 * @param model EasyModel
	 * @param event EasyEvent
	 * @return
	 */
	public static EasyModel setModelByEvent(EasyModel model, EasyEvent event) {
		for (Enumeration<String> e = model.getHashTable().keys(); e.hasMoreElements();) {
			String key = e.nextElement().toString();
			model.set(key, event.GetForSql(key));
		}
		return model;
	}

	/**
	 * 序列化字符串
	 * 
	 * @param ary String[]
	 * @return
	 */
	public static String serializeAry(String[] ary) {
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < ary.length; i++) {
			if (i == (ary.length - 1)) {
				temp.append(ary[i]);
			} else {
				temp.append(ary[i]);
				temp.append(",");
			}
		}
		return temp.toString();
	}

	/**
	 * 序列化字符串
	 * @param prefix 字符串前缀
	 * @param ary 字符串数组
	 * @return String
	 */
	public static String serializeAry(String prefix, String[] ary) {
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < ary.length; i++) {
			if (i == (ary.length - 1)) {
				temp.append(prefix);
				temp.append(ary[i]);
			} else {
				temp.append(prefix);
				temp.append(ary[i]);
				temp.append(",");
			}
		}
		return temp.toString();
	}

	/**
	 * 把Double转换成String
	 * 
	 * @param doubleValue
	 * @return
	 */
	public static String DoubleToString(Double doubleValue) {
		Integer int_id = new Integer(doubleValue.intValue());
		return int_id.toString();
	}

	/**
	 * Double转换成String
	 * @param doubleValue
	 * @return
	 */
	public static String DoubleToString(Object doubleValue) {
		if (doubleValue == null || doubleValue.equals("")) {
			return "";
		}
		return String
				.valueOf(Double.valueOf(doubleValue.toString()).intValue());
	}

	/**
	 * Double值转换成Int值
	 * @param doubleValue
	 * @return
	 */
	public static int DoubleToInt(Double doubleValue) {
		return doubleValue.intValue();
	}

	/**
	 * 取得数据库分页集合
	 * @param result Collection
	 * @param currentPageNo 当前分页号
	 * @param maxShowed 最大显示记录数
	 * @return
	 */
	public static Collection<Object> GetPageFromResult(Collection<Object> result,
			int currentPageNo, int maxShowed) {
		ArrayList<Object> tmpArrayList = new ArrayList<Object>(result);
		Collection<Object> retCollection = new ArrayList<Object>();

		if (result.size() < maxShowed) {
			retCollection = result;
		} else {
			int intFrom = 0;
			int intTo = 0;

			intFrom = maxShowed * (currentPageNo - 1);
			intTo = maxShowed * currentPageNo;

			for (int i = intFrom; i < intTo && i < tmpArrayList.size(); i++) {
				retCollection.add(tmpArrayList.get(i));
			}
		}

		return retCollection;
	}

	/**
	 * 为数据库列设置值，并以List对象返回
	 * @param list 列值List
	 * @param TABLE_ITEMS 表字段
	 * @return ArrayList
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList convertDBResultToArrayList(List list,
			String TABLE_ITEMS) {
		Iterator iterator = list.iterator();
		Hashtable<String,Object> hashtable = null;
		ArrayList collection = new ArrayList();

		while (iterator.hasNext()) {
			hashtable = (Hashtable) iterator.next();
			if (!hashtable.isEmpty()) {
				EasyModel masterModel = new EasyModel(TABLE_ITEMS);
				masterModel.setByRecordset(hashtable);
				collection.add(masterModel);
			}
		}
		return collection;
	}

	/**
	 * Hashtable中取值
	 * 
	 * @param hashtable Hashtable
	 * @param strKey
	 * @return
	 */
	public static String getStringFromHashtable(Hashtable<String,Object> hashtable,
			String strKey) {

		String strRetrun = "";

		if (hashtable == null || hashtable.isEmpty()) {
			return "";
		}

		if (strKey == null) {
			return "";
		}

		if (hashtable.get(strKey) != null) {
			strRetrun = hashtable.get(strKey).toString();
		}

		if (strRetrun == null) {
			strRetrun = "";
		}

		return strRetrun;
	}

	/**
	 * 判断Request请求值中是否有null值
	 * @param array 请求参数数组
	 * @param request HttpServletRequest
	 * @return 请求参数数组中只有有值为NULL，则返回false
	 */
	public static boolean paramIsNull(String[] array, HttpServletRequest request) {
		boolean bool = false;

		if (array != null && request != null) {
			for (int i = 1; i < array.length; i++) {
				if (request.getParameter(array[i]) == null) {
					bool = true;
					break;
				}
			}
		}

		return bool;

	}

}
