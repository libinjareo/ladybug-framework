package com.ladybug.framework.easy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ladybug.framework.easy.database.DBType;
import com.ladybug.framework.easy.tools.EscapeTools;
import com.ladybug.framework.easy.tools.Tools;

/**
 * 轻量级模型封装类
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class EasyModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 数据缓存
	 */
	private Hashtable<String,Object> data = new Hashtable<String,Object>();

	/**
	 * 取得数据缓存
	 * @return
	 */
	public Hashtable<String,Object> getHashTable() {
		return data;
	}

	/**
	 * 构造函数,参数字符串以逗号分隔
	 * @param def
	 */
	public EasyModel(String def) {
		ArrayList<String> ary = Tools.splitA(def, ",");
		for (int i = 0; i < ary.size(); i++) {
			data.put((String) ary.get(i), "");
		}
	}

	/**
	 * 构造函数
	 * @param def
	 */
	public EasyModel(String[] def) {
		for (int i = 0; i < def.length; i++) {
			data.put(def[i], "");
		}
	}

	/**
	 * 设置数据值,前提是数据缓存中存在Key值数据，如果参数value为null,则复制为空
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value) {
		if (data.containsKey(key)) {
			if (value == null) {
				data.put(key, "");
			} else {
				data.put(key, value);
			}
		}
	}

	/**
	 * 取得数据对象,并进行数据库转换,如果不存在key值元素，则直接返回NULL
	 * @param key
	 * @return
	 */
	public String get(String key) {
		if (data.containsKey(key)) {
			return convertChr(data.get(key).toString());
		} else {
			return null;
		}
	}

	/**
	 * 返回字符串数据，如果不存在Key值元素，则返回空字符串
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		if (data.containsKey(key)) {
			if (data.get(key) == null) {
				return "";
			} else {
				return convertChr(data.get(key).toString());
			}
		} else {
			return "";
		}
	}

	/**
	 * 返回数据对象，如果不存在则返回NULL
	 * @param key
	 * @return
	 */
	public Object getAsObject(String key) {
		if (data.containsKey(key)) {
			return data.get(key);
		} else {
			return null;
		}
	}

	/**
	 * 通过Session设置数据值,如果Session中不存在参数Key值的数据，则赋值为空字符串
	 * @param session HttpSession
	 * @param keys String[] 要设置值的Key值数组
	 */
	public void setBySession(HttpSession session, String[] keys) {
		if (session != null) {
			for (int i = 0; i < keys.length; i++) {
				Object temp;
				String key = keys[i];
				if (data.containsKey(key)) {
					temp = session.getAttribute(key);
					if (temp == null) {
						data.put(key, "");
					} else {
						data.put(key, temp);
					}
				}
			}
		}
	}

	/**
	 * 根据Session复制，如果参数Session为NULL,则数据全部赋值为空字符串
	 * 
	 * @param session
	 */
	public void setBySession(HttpSession session) {
		if (session == null) {
			for (Enumeration<String> e = data.keys(); e.hasMoreElements();) {
				data.put(e.nextElement().toString(), "");
			}
		} else {
			for (Enumeration<String> e = data.keys(); e.hasMoreElements();) {
				Object temp;
				String key = e.nextElement().toString();
				temp = session.getAttribute(key);
				if (temp == null) {
					data.put(key, "");
				} else {
					data.put(key, temp);
				}
			}
		}
	}

	/**
	 * 通过request设置数据值
	 * @param request HttpServletRequest
	 * @param keys 要赋值的参数数组
	 */
	public void setByRequest(HttpServletRequest request, String[] keys) {
		if (request != null) {
			for (int i = 0; i < keys.length; i++) {
				Object temp;
				String key = keys[i];
				if (data.containsKey(key)) {
					temp = request.getParameter(key);
					if (temp == null) {
						data.put(key, "");
					} else {
						data.put(key, temp);
					}
				}

			}
		}
	}

	/**
	 * 通过Request设置数据值
	 * @param request
	 */
	public void setByRequest(HttpServletRequest request) {
		if (request == null) {
			for (Enumeration<String> e = data.keys(); e.hasMoreElements();) {
				data.put(e.nextElement().toString(), "");
			}
		} else {
			for (Enumeration<String> e = data.keys(); e.hasMoreElements();) {
				Object temp;
				String key = e.nextElement().toString();
				temp = request.getParameter(key);
				if (temp == null) {
					data.put(key, "");
				} else {
					data.put(key, temp);
				}
			}
		}
	}

	/**
	 * 通过Hashtable为数据缓存赋值
	 * @param table
	 */
	public void setByRecordset(Hashtable<String,Object> table) {
		if (table == null) {
			for (Enumeration<String> e = data.keys(); e.hasMoreElements();) {
				data.put(e.nextElement().toString(), "");
			}
		} else {
			for (Enumeration<String> e = data.keys(); e.hasMoreElements();) {
				Object temp;
				String key = e.nextElement().toString();
				temp = table.get(key);
				if (temp == null) {
					data.put(key, "");
				} else {
					data.put(key, temp);
				}
			}
		}
	}

	/**
	 * 重新创建数据模型
	 * @param def
	 * @return
	 */
	public EasyModel rebuild(String[] def) {
		EasyModel newModel = new EasyModel(def);
		for (int i = 0; i < def.length; i++) {
			newModel.set(def[i], convertObject(this.getAsObject(def[i])));
		}
		return newModel;
	}

	/**
	 * 移除一组数据
	 * @param def
	 */
	public void remove(String[] def) {
		for (int i = 0; i < def.length; i++) {
			this.data.remove(def[i]);
		}
	}

	/**
	 * 移除数据
	 * @param def
	 */
	public void remove(String def) {
		this.data.remove(def);
	}

	/**
	 * 增加数据
	 * @param key
	 */
	public void add(String key) {
		this.data.put(key, "");
	}

	/**
	 * 增加数据
	 * @param key
	 * @param value
	 */
	public void add(String key, Object value) {
		if (value == null) {
			this.data.put(key, "");
		} else {
			this.data.put(key, value);
		}
	}

	/**
	 * 根据模型增加数据
	 * 
	 * @param model EasyModel
	 */
	public void add(EasyModel model) {
		if (model != null) {
			for (Enumeration<String> e = model.data.keys(); e.hasMoreElements();) {
				Object temp;
				String key = e.nextElement().toString();
				temp = model.getAsObject(key);
				if (temp == null) {
					data.put(key, "");
				} else {
					data.put(key, temp);
				}
			}
		}
	}

	/**
	 * 转换对象
	 * @param value
	 * @return
	 */
	public Object convertObject(Object value) {
		if (DBType.isOracle()) {
			return value;
		}

		// if(value == null) return "";
		if (value == null || value.toString() == "")
			return "";

		value = Tools.replaceAll(value.toString(), "\\\r\n", "\\ \r\n");
		value = Tools.replaceAll(value.toString(), "\\\r", "\\ \r");
		value = Tools.replaceAll(value.toString(), "\\\n", "\\ \n");
		return value;
	}

	/**
	 * 转换字符串
	 * 
	 * @param value
	 * @return
	 */
	public String convertChr(String value) {
		if (DBType.isOracle()) {
			return value;
		}

		if (value == null || value == "")
			return "";
		value = Tools.replaceAll(value, "\\\r\n", "\\ \r\n");
		value = Tools.replaceAll(value, "\\\r", "\\ \r");
		value = Tools.replaceAll(value, "\\\n", "\\ \n");
		return value;
	}

	public static String getForSql(String sqlStr) {
		return EscapeTools.toSqlString(sqlStr);
	}

}
