package com.ladybug.framework.base.service;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * (i18n)资源消息处理工具类
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class ServiceResourceMessage {

	/**
	 * 取得i18n资源文件中的Key属性值
	 * @param key
	 * @return
	 */
	public static String getResourceString(String key) {
		String message = null;
		try {
			message = ResourceBundle.getBundle(
					"com.ladybug.framework.base.service.i18n").getString(
					key);
		} catch (MissingResourceException e) {

		}

		return message;
	}
}
