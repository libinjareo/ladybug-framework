package com.ladybug.framework.base.event;

/**
 * 事件触发器信息
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class EventTriggerInfo {
	
	/**
	 * 序号
	 */
	private int number;
	
	/**
	 * 名称
	 */
	private String name;

	/**
	 * 默认构造函数
	 */
	public EventTriggerInfo() {
		setNumber(0);
		setName("");
	}

	/**
	 * 取得序号
	 * @return
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * 设置序号
	 * @param number
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * 取得名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

}
