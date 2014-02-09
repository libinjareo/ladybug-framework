package com.ladybug.framework.system.log;

/**
 * 日志模型
 * 
 * @author  james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class LogModel {
	
	/**
	 * 日志代理名称
	 */
	private String logAgentName;
	
	/**
	 * 日志代理参数数组
	 */
	private LogAgentParam[] logAgentParams;
	
	//以下为日志标签参数
	public static String ID = "log-config";
	public static String P_ID_AGENT_NAME = "agent-class";
	public static String P_ID_INIT_PARAM = "init-param";
	public static String P_ID_PARAM_NAME = "param-name";
	public static String P_ID_PARAM_VALUE = "param-value";

	/**
	 * 取得日志代理名称
	 * @return
	 */
	public String getLogAgentName() {
		return logAgentName;
	}

	/**
	 * 设置日志代理名称
	 * @param logAgentName
	 */
	public void setLogAgentName(String logAgentName) {
		this.logAgentName = logAgentName;
	}

	/**
	 * 取得日志代理参数数组
	 * @return
	 */
	public LogAgentParam[] getLogAgentParams() {
		return logAgentParams;
	}

	/**
	 * 设置日志代理参数数组
	 * @param logAgentParams
	 */
	public void setLogAgentParams(LogAgentParam[] logAgentParams) {
		this.logAgentParams = logAgentParams;
	}

}
