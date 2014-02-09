package com.ladybug.framework.easy;

import com.ladybug.framework.base.event.Event;
import com.ladybug.framework.easy.tools.EscapeTools;

/**
 * 简单事件模型实现类,关联EasyModel,设置请求值
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class EasyEvent extends Event {
	private static final long serialVersionUID = 1L;

	/**
	 * 模型对象
	 */
	private EasyModel model;

	/**
	 * Event-key
	 */
	private String eventid;

	/**
	 * 取得模型
	 * @return EasyModel
	 */
	public EasyModel GetModel() {
		return model;
	}

	/**
	 * 设置模型
	 * @param model EasyModel
	 */
	public void SetModel(EasyModel model) {
		this.model = model;
	}

	/**
	 * 取得Event-key
	 * @return
	 */
	public String GetEventid() {
		return eventid;
	}

	/**
	 * 设置Event-key
	 * @param eventid
	 */
	public void SetEventid(String eventid) {
		this.eventid = eventid;
	}

	/**
	 * 创建模型
	 * @param def String
	 */
	protected void CreateModel(String def) {
		model = new EasyModel(def);
	}

	/**
	 * 创建模型
	 * 
	 * @param def String
	 */
	protected void CreateModel(String[] def) {
		model = new EasyModel(def);
	}

	/**
	 * 取得模型
	 * 
	 * @return EasyModel
	 */
	public EasyModel Model() {
		return model;
	}

	/**
	 * 为模型设置值
	 * @param key
	 * @param value
	 */
	public void Set(String key, Object value) {
		this.model.set(key, value);
	}

	/**
	 * 从模型中取值
	 * @param key
	 * @return
	 */
	public String Get(String key) {
		return this.model.get(key);
	}

	/**
	 * 从模型中取Sql字符串
	 * @param key
	 * @return
	 */
	public String GetForSql(String key) {
		return EscapeTools.toSqlString(this.model.get(key));
	}

	/**
	 * 从模型中取值
	 * @param key
	 * @return
	 */
	public Object GetAsObject(String key) {
		return this.Model().getAsObject(key);
	}
}
