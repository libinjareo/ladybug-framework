package com.ladybug.framework.system.exception;

import java.io.PrintStream;
import java.io.PrintWriter;


/**
 * 框架级异常
 * 
 * @author li_james(libinjareo@163.com)
 * @version 1.0
 *
 */
public class FrameworkException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 默认的构造函数
	 */
	public FrameworkException() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 * @param throwable Throwable
	 */
	public FrameworkException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 */
	public FrameworkException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数
	 * 
	 * @param throwable Throwable
	 */
	public FrameworkException(Throwable throwable) {
		super(throwable);
	}
	
	/**
	 * 取得Throwable
	 * 
	 * @return Throwable
	 */
	public Throwable getException(){
		return getCause();
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}

	@Override
	public void printStackTrace(PrintStream printStream) {
		super.printStackTrace(printStream);
	}

	@Override
	public void printStackTrace(PrintWriter printWriter) {
		super.printStackTrace(printWriter);
	}
	
	

}
