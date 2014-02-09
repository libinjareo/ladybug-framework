package com.ladybug.framework.system.property;

/**
 * 属性参数实现类
 * 
 * @author libinjareo@163.com
 * @version 1.0
 *
 */
public class PropertyParam {
	
	/**
	 * 参数名称
	 */
	private String name;
	
	/**
	 * 参数值
	 */
	private String value;

	/**
	 * 构造函数
	 */
	public PropertyParam() {
		setName(null);
		setValue(null);
	}

	/**
	 * 取得参数名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置参数名称
	 * @param name 参数名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 取得参数值
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 设置参数值
	 * @param value 参数值
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
