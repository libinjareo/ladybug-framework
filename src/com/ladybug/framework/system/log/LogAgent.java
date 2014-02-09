package com.ladybug.framework.system.log;

/**
 * 日志代理接口
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public interface LogAgent {
	
	/**
	 * 根据代理参数数组进行初始化
	 * @param paramArrayOfLogAgentParam
	 */
	public void init(LogAgentParam[] paramArrayOfLogAgentParam);

	/**
	 * 发送消息 
	 * @param category 异常类别
	 * @param level 异常级别
	 * @param message 异常信息
	 */
	public void sendMessage(String category, String level,
			String message);

	/**
	 * 发送消息
	 * @param category 异常种类
	 * @param level 异常级别
	 * @param message 异常信息
	 * @param detail 异常对象
	 */
	public void sendMessage(String category, String level,
			String message, Object detail);

}
