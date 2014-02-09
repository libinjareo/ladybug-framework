package com.ladybug.framework.util;

import java.util.StringTokenizer;

/**
 *  名称字符串工具类
 *  
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class NameUtil {

	/**
	 * 是否为名称字符,数字、字母，下划线和点号
	 * @param chr
	 * @return
	 */
	public static boolean isNameChar(char chr) {
		return ((chr >= 'a') && (chr <= 'z')) || ((chr >= 'A') && (chr <= 'Z'))
				|| ((chr >= '0') && (chr <= '9')) || (chr == '_')
				|| (chr == '.');
	}

	/**
	 * 是否为有效名称
	 * @param name
	 * @return
	 */
	public static boolean isValidName(String name) {
		int length = 0;
		if (name == null) {
			return false;
		}
		length = name.length();
		if (length == 0) {
			return false;
		}

		for (int i = 0; i < length; i++) {
			if (!isNameChar(name.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 是否为有效的应用名称,最后一个字符不能为点
	 * @param name
	 * @return
	 */
	public static boolean isValidApplicationName(String name) {
		if (name == null) {
			return false;
		}

		int nameLength = name.length();
		int count = 0;

		if (nameLength == 0) {
			return false;
		}

		if (!isNameChar(name.charAt(count++))) {
			return false;
		}

		while (count < nameLength) {
			char chr = name.charAt(count++);
			if (chr == '.') {
				if (count >= nameLength) {
					return false;
				}
				if (!isNameChar(name.charAt(count++))) {
					return false;
				}
			}

			if (!isNameChar(chr)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否为有效路径 
	 * @param path
	 * @return
	 */
	public static boolean isValidPath(String path) {
		int len = 0;
		StringTokenizer tokens = null;
		String token = null;

		if (path == null) {
			return false;
		}
		len = path.length();
		if (len == 0) {
			return false;
		}
		if ("/".equals(path)) {
			return true;
		}

		// 把分隔符当做标记
		tokens = new StringTokenizer(path, "/", true);
		while (tokens.hasMoreTokens()) {
			token = tokens.nextToken();
			if (token.charAt(0) != '/') {
				return false;
			}
			if (!tokens.hasMoreElements()) {
				return false;
			}
			token = tokens.nextToken();
			if (!isValidName(token)) {
				return false;
			}
		}
		return true;

	}

}
