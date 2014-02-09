package com.ladybug.framework.base.event;

import java.io.Serializable;

/**
 * 事件模型抽象
 * 
 * @author james.li(libinjare@163.com)
 * @version 1.0
 *
 */
public abstract class Event implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 应用
	 */
	private String application;
	
	/**
	 * 事件Key值,与配置文件中设置
	 */
	private String key;

	/**
	 * 默认构造函数
	 */
	public Event() {
		setApplication(null);
		setKey(null);
	}

	/**
	 * 取得应用
	 * @return
	 */
	public String getApplication() {
		return application;
	}

	/**
	 * 设置应用
	 * @param application
	 */
	public void setApplication(String application) {
		this.application = application;
	}

	/**
	 * 取得Event标志Key
	 * @return
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 设置Event标志Key
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}

}
