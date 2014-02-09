package com.ladybug.framework.system.log;

import com.ladybug.framework.system.property.PropertyHandler;

/**
 * 日志属性处理器
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public interface LogPropertyHandler extends PropertyHandler {

	/**
	 * 取得日志代理名称
	 * @return
	 * @throws LogPropertyException
	 */
	public String getLogAgentName() throws LogPropertyException;

	/**
	 * 取得日志代理参数数组
	 * @return LogAgentParam[]
	 * @throws LogPropertyException
	 */
	public LogAgentParam[] getLogAgentParams() throws LogPropertyException;
}
