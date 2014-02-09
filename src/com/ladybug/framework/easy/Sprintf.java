package com.ladybug.framework.easy;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 格式化工具类
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class Sprintf {

	/**
	 * 把int格式化成String
	 * @param format  格式化字符串
	 * @param value 格式化值
	 * @return
	 */
	public static String int2String(String format, int value) {
		NumberFormat formatter = new DecimalFormat(format);
		String s = formatter.format(value);

		return s;
	}
}
