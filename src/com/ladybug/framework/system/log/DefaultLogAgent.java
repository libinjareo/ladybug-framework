package com.ladybug.framework.system.log;

import java.io.PrintStream;

/**
 * 默认的异常代理对象,只打印日志信息不生成日志文件
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class DefaultLogAgent implements LogAgent {

	@Override
	public void init(LogAgentParam[] params) {

	}

	@Override
	public void sendMessage(String category, String level, String message) {
		String logMessage = createMessage(category, level, message);
		if (!"ERROR".equals(level))
			System.out.println(logMessage);
		else
			System.err.println(logMessage);
	}

	@Override
	public void sendMessage(String category, String level, String message,
			Object detail) {

		PrintStream out = null;
		String logMessage = createMessage(category, level, message);
		//判断是否为错误级别
		if (!"ERROR".equals(level))
			out = System.out;
		else
			out = System.err;
		out.println(logMessage);
		
		//判断是否为异常对象
		if (detail != null) {
			if (detail instanceof Throwable) {
				((Throwable) detail).printStackTrace(out);
			} else {
				out.print(detail.toString());
			}
		}
	}

	/**
	 * 格式化异常信息
	 * 
	 * @param category 异常种类
	 * @param level 异常级别
	 * @param message 异常信息
	 * @return
	 */
	private String createMessage(String category, String level, String message) {
		return "[" + category + "][" + level + "]" + message;
	}

}
