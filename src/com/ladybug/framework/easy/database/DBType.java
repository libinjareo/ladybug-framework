package com.ladybug.framework.easy.database;

import javax.servlet.http.HttpServletRequest;

/**
 * 数据库类型
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class DBType {
	private static boolean oraBool;
	private static boolean sqlBool;

	public static boolean isOracle() {
		return oraBool;
	}

	public static boolean isSqlServer() {
		return sqlBool;
	}

	public static void isOracle(HttpServletRequest request) {
//		String info = "";
//		NativeObject temp = (NativeObject) request.getSession().getAttribute(
//				"USER");
//		if (temp != null) {
//			if (temp.has("DBTYPE", temp)) {
//				info = temp.get("DBTYPE", temp).toString();
//				oraBool = info.indexOf("Oracle") < 0 ? false : true;
//			}
//		} else {
//			oraBool = false;
//		}
	}

	public static void isSqlServer(HttpServletRequest request) {
//		String info = "";
//		NativeObject temp = (NativeObject) request.getSession().getAttribute(
//				"USER");
//		if (temp != null) {
//			if (temp.has("DBTYPE", temp)) {
//				info = temp.get("DBTYPE", temp).toString();
//				sqlBool = info.indexOf("SQL") < 0 ? false : true;
//			}
//		} else {
//			sqlBool = false;
//		}
	}

	public static void main(String[] args) {
//		NativeObject no = new NativeObject();
//
//		no.put("DBTYPE", no, "is oracle database");
//
//		boolean bol = no.has("DBTYPE", no);
//		System.out.println("bol===> " + bol);
//
//		String value = (String) no.get("DBTYPE", no);
//		System.out.println("value===> " + value);
	}
}
