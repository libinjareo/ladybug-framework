package com.ladybug.framework.easy;

/**
 * 轻量级日志类，仅打印信息至控制台
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class EasyLog {
	public static int LogType_System = 0;
	public static int LogType_Customer = 1;

	public static boolean isDebug = false;

	/**
	 * 是否为Debug级别
	 * @return
	 */
	public static boolean isDebug() {
		return isDebug;
	}

	/**
	 * 设置是否为Debug级别
	 * @param isDebug
	 */
	public static void setDebug(boolean isDebug) {
		EasyLog.isDebug = isDebug;
	}

	/**
	 * 打印信息
	 * @param value 信息
	 * @param LogType Log级别
	 */
	public static void Print(String value, int LogType) {
		if (isDebug()) {
			System.out.println(value);
			System.out.println(LogType);
		}
	}

	/**
	 * 打印信息
	 * @param obj 信息对象 
	 */
	public static void Print(Object obj) {
		if (isDebug()) {
			System.out.println(obj);
		}
	}

	/**
	 * 打印信息
	 * @param i
	 */
	public static void Print(int i) {
		if (isDebug()) {
			System.out.println(i);
		}
	}

	/**
	 * 打印信息字符串
	 * @param value
	 */
	public static void Print(String value) {
		if (isDebug) {
			Print(value, 1);
		}
	}

}
