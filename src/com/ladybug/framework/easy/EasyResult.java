package com.ladybug.framework.easy;

import com.ladybug.framework.base.event.EventResult;
import com.ladybug.framework.base.service.ServiceResult;

/**
 * 轻量级ServiceResult实现,关联EasyModel取值,设置响应值
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class EasyResult implements ServiceResult, EventResult {

	private static final long serialVersionUID = 1L;

	/**
	 * 数据模型
	 */
	private EasyModel model;

	/**
	 * 默认的构造函数，构建EasyModel
	 * @param def String
	 */
	public EasyResult(String def) {
		this.CreateModel(def);
	}

	/**
	 * 默认的构造函数，构建EasyModel
	 * @param def String[]
	 */
	public EasyResult(String[] def) {
		this.CreateModel(def);
	}

	/**
	 * 创建模型
	 * @param def String[]
	 */
	protected void CreateModel(String[] def) {
		model = new EasyModel(def);
	}

	/**
	 * 创建模型
	 * @param def String
	 */
	protected void CreateModel(String def) {
		model = new EasyModel(def);
	}

	/**
	 * 设置模型
	 * @param value EasyModel
	 */
	public void SetModel(EasyModel value) {
		model = value;
	}

	/**
	 * 取得模型
	 * @return
	 */
	public EasyModel Model() {
		return model;
	}

	/**
	 * 设置模型值
	 * @param key 键
	 * @param value 值
	 */
	public void Set(String key, Object value) {
		this.model.set(key, value);
	}

	/**
	 * 根据Key取得模型中的值
	 * @param key
	 * @return
	 */
	public String Get(String key) {
		return this.model.get(key);
	}

	/**
	 * 根据Key取得模型中的对象
	 * @param key
	 * @return
	 */
	public Object GetAsObject(String key) {
		return this.Model().getAsObject(key);
	}
}
