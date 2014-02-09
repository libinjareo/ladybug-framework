package com.ladybug.framework.easy.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 字符串处理工具类
 * 
 * @author Administrator
 *
 */
public class Tools {

	private static final String SQLESCAPESTRING = "$";

	public Tools() {

	}

	/**
	 * 格式化电话号码字符串
	 * @param tel
	 * @return
	 */
	public static String formattel(String tel) {
		StringBuffer rt = new StringBuffer();
		for (int i = 0; i < tel.length(); i++) {
			char temp = tel.charAt(i);
			if (temp != '-') {
				rt.append(temp);
			}
		}
		return rt.toString();
	}

	/**
	 * 以特定分隔符分隔字符串，并以ArrayList返回
	 * @param input 要分割的字符串
	 * @param splitor 分隔符
	 * @return ArrayList 
	 */
	public static ArrayList<String> splitA(String input, String splitor) {
		List<String> ary = new ArrayList<String>();
		int fromindex = 0;
		int toindex = -1;
		boolean loopflg = true;
		while (loopflg) {
			toindex = input.indexOf(splitor, fromindex);
			if (toindex != -1) {
				ary.add(input.substring(fromindex, toindex));
			} else {
				ary.add(input.substring(fromindex));
				loopflg = false;
			}
			fromindex = toindex + splitor.length();
		}
		return (ArrayList<String>) ary;
	}

	/**
	 * 以特定分隔符分隔字符串，以字符串数组的形式返回
	 * @param input 要分割的字符串
	 * @param splitor 分隔符
	 * @return 字符串数组
	 */
	public static String[] split(String input, String splitor) {
		ArrayList<String> ary = splitA(input, splitor);
		String[] rt = new String[ary.size()];
		for (int i = 0; i < ary.size(); i++) {
			rt[i] = (String) ary.get(i);
		}
		return rt;
	}

	/**
	 * 替换字符串中的分隔符
	 * @param text 字符串
	 * @param repl 要替换分隔符
	 * @param with  替换分隔符
	 * @return
	 */
	public static String replaceAll(String text, String repl, String with) {
		if (text == null) {
			return "";
		}
		StringBuffer rt = new StringBuffer("");
		String[] ary = split(text, repl);
		for (int i = 0; i < ary.length; i++) {
			if (i == ary.length - 1) {
				rt.append(ary[i]);
			} else {
				rt.append(ary[i] + with);
			}
		}
		return rt.toString();
	}

	/**
	 * 
	 * @param text
	 * @param repl
	 * @param with
	 * @return
	 */
	public static String replaceAllIgnoreCase(String text, String repl,
			String with) {

		String textCopy = text.toLowerCase();
		String replCopy = repl.toLowerCase();

		int index = textCopy.indexOf(replCopy);
		while (index > -1) {
			String header = text.substring(0, index);
			String footer = text.substring(index + repl.length());
			text = header + with + footer;
			textCopy = text.toLowerCase();
			index = textCopy.indexOf(replCopy);
		}
		return text;
	}

	public static String getTimeString() {
		return "";
	}

	public static String getSplitValue(String string, String delim, int i) {
		StringTokenizer st = new StringTokenizer(string, delim);
		List<String> ary = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			ary.add(st.nextToken());
		}
		if (i < 1 || i > ary.size()) {
			return null;
		}
		return (String) ary.get(i - 1);
	}

	public static Object defaultString(Object param) {
		if (param == null) {
			return "";
		}
		return param;
	}

	public static String getValue(Map<String,String> hashMap, String key) {
		return (String) hashMap.get(key);
	}

	public static String replace(String text, String repl, String with, int max) {
		for (int i = 0; i < max; i++) {
			int index = text.indexOf(repl);
			if (index > -1) {
				String header = text.substring(0, index - 1);
				String footer = text.substring(index + repl.length() - 1);
				text = header + with + footer;
			} else {
				break;
			}
		}
		return text;
	}

	public static String formatDecimal(String param) {
		int index = param.indexOf(".");
		if (index > -1) {
			String before = param.substring(0, index);
			String after = param.substring(index + 1);
			after = after + "0000";
			after = after.substring(0, 4);
			param = before + "." + after;
		} else {
			param = param + ".0000";
		}
		return param;
	}

	public static String defaultNumber(String param) {

		if (param == null) {
			return "0";
		}
		if (param.length() == 0) {
			return "0";
		}
		return param;
	}

	public static String getSqlEscapeString() {
		return SQLESCAPESTRING;
	}

	public static String formatZero(String resultCode, int integralSize) {
		int zerocount = integralSize - resultCode.length();
		for (int i = 0; i < zerocount; i++) {
			resultCode = "0" + resultCode;
		}
		return resultCode;
	}

	public static void main(String[] args) {
		// String testStr = "com.";
		// List list = Tools.splitA(testStr, ".");
		// System.out.println("result length: " + list.size());
		// for (int i = 0; i < list.size(); i++) {
		// System.out.println(list.get(i));
		// }

		// =======================================================
		String testStr = "AcBcDcEc";
		String result = Tools.replaceAllIgnoreCase(testStr, "c", "C");
		System.out.println("result===> " + result);
	}
}
