package com.ladybug.framework.easy.tools;

import java.util.Hashtable;

/**
 * 字符串工具类,主要为字符串的转换
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class EscapeTools {

	public EscapeTools() {

	}

	/**
	 * 转换成HTML字符串
	 * @param p  带转换的字符串
	 * @param b 是否需要进行空格(' ')和换行(\n)转换
	 * @return
	 */
	public static String toHtmlString(String p, boolean b) {
		if (p == null) {
			return "";
		}
		StringBuffer sf = new StringBuffer();
		int AmpFlag = 0;
		for (int i = 0; i < p.length(); i++) {
			char temp = p.charAt(i);
			if (AmpFlag == 1) {
				AmpFlag = 0;
				if (temp == '#') {
					sf.append("&#");
					continue;
				} else {
					sf.append("&amp;");
					// fall through
				}
			}
			if (b) {
				switch (temp) {
				case ' ':
					sf.append("&nbsp;");
					break;
				case '"':
					sf.append("&quot;");
					break;
				case '&':
					AmpFlag = 1;
					break;
				case '<':
					sf.append("&lt;");
					break;
				case '>':
					sf.append("&gt;");
					break;
				case '\'':
					sf.append("&#39;");
					break;
				case '\n':
					sf.append("<br>");
					break;
				default:
					sf.append(temp);
				}
			} else {
				switch (temp) {
				case '"':
					sf.append("&quot;");
					break;
				case '&':
					AmpFlag = 1;
					break;
				case '<':
					sf.append("&lt;");
					break;
				case '>':
					sf.append("&gt;");
					break;
				case '\'':
					sf.append("&#39;");
					break;
				default:
					sf.append(temp);
				}
			}
		}
		if (AmpFlag == 1) {
			AmpFlag = 0;
			sf.append("&amp;");
		}
		return sf.toString();
	}

	/**
	 * 转换成HTM字符串，不进行空格(' ')和换行(\n)转换
	 * @param p 待转换的字符串
	 * @return
	 */
	public static String toHtmlString(String p) {
		return toHtmlString(p, false);
	}

	/**
	 * 转出成JavaScript字符串
	 * @param p 待转换的字符串
	 * @param b 是否需要(&)和(\\\)的转换
	 * @return
	 */
	public static String toJsString(String p, boolean b) {
		if (p == null) {
			return "";
		}
		StringBuffer sf = new StringBuffer();
		int AmpFlag = 0;
		for (int i = 0; i < p.length(); i++) {
			char temp = p.charAt(i);
			if (AmpFlag == 1) {
				AmpFlag = 0;
				if (temp == '#') {
					sf.append("&#");
					continue;
				} else {
					if (b) {
						sf.append("&amp;");
					} else {
						sf.append("&");
					}
					// fall through
				}
			}
			switch (temp) {
			case '"':
				if (b) {
					sf.append("&quot;");
				} else {
					sf.append("\\\"");
				}
				break;
			case '\'':
				sf.append("\\\'");
				break;
			case '\\':
				sf.append("\\\\");
				break;
			case '\n':
				sf.append("\\n");
				break;
			case '\r':
				sf.append("\\r");
				break;
			case '&':
				AmpFlag = 1;
				break;
			default:
				sf.append(temp);
			}
		}
		if (AmpFlag == 1) {
			AmpFlag = 0;
			if (b) {
				sf.append("&amp;");
			} else {
				sf.append("&");
			}
		}
		return sf.toString();
	}

	/**
	 * 转换成javascript字符串,不需要需要(&)和(\\\)的转换
	 * 
	 * @param p 带转换的字符串
	 * @return
	 */
	public static String toJsString(String p) {
		return toJsString(p, false);
	}

	/**
	 * 转换成Sql字符串
	 * @param p 待转换的字符串
	 * @return
	 */
	public static String toSqlString(String p) {
		if (p == null) {
			return "";
		}
		p = Tools.replaceAll(p, "'", "''");
		return p;
	}

	/**
	 * 转换成CSV输出字符串
	 * @param strValue 待转换的字符串
	 * @return
	 */
	public static String toCSVOutString(String strValue) {

		if (strValue == null || strValue.equals("")) {
			// return "";
			return "\"\"";
		} else if (strValue.indexOf("\"") != -1) {
			strValue = Tools.replaceAll(strValue, "\"", "\"\"");
			strValue = "\"" + strValue + "\"";
		} else {
			strValue = "\"" + strValue + "\"";
		}

		return strValue;
	}

	/**
	 * 转换成CSV输出字符串
	 * @param hashtable
	 * @param strKey 为Double值的键值
	 * @param isNumber 是否需要取得Double值
	 * @return
	 */
	public static String toCSVOutString(Hashtable<String,Object> hashtable, String strKey,
			boolean isNumber) {

		if (hashtable == null) {
			return "\"\"";
		}
		if (strKey == null || strKey.equals("")) {
			return "\"\"";
		}

		if (hashtable.get(strKey) == null) {
			return "\"\"";
		} else {
			if (isNumber) {
				Double dblValue = (Double) hashtable.get(strKey);
				return toCSVOutString(String.valueOf(dblValue.intValue()));
			} else {
				return toCSVOutString(hashtable.get(strKey).toString());
			}
		}
	}

	/**
	 * 转换成CSV输出字符串,不需要进行Double的提取
	 * @param hashtable
	 * @param strKey
	 * @return
	 */
	public static String toCSVOutString(Hashtable<String,Object> hashtable, String strKey) {
		return toCSVOutString(hashtable, strKey, false);
	}

	/**
	 * 转换成HTML TextArea字符串
	 * @param p
	 * @return
	 */
	public static String toHtmlStringTextArea(String p) {
		if (p == null) {
			return "";
		}
		StringBuffer sf = new StringBuffer();
		int AmpFlag = 0;
		for (int i = 0; i < p.length(); i++) {
			char temp = p.charAt(i);
			if (AmpFlag == 1) {
				AmpFlag = 0;
				if (temp == '#') {
					sf.append("&#");
					continue;
				} else {
					sf.append("&amp;");
					// fall through
				}
			}
			switch (temp) {
			case '\'':
				sf.append("&#39;");
				break;
			case '"':
				sf.append("&quot;");
				break;
			case '&':
				AmpFlag = 1;
				break;
			case '<':
				sf.append("&lt;");
				break;
			case '>':
				sf.append("&gt;");
				break;
			default:
				sf.append(temp);
			}
		}
		if (AmpFlag == 1) {
			AmpFlag = 0;
			sf.append("&amp;");
		}
		return sf.toString();
	}
}
