package com.ladybug.framework.base.data;

import com.ladybug.framework.system.exception.SystemException;

public class DataAccessException extends SystemException {

	private static final long serialVersionUID = 1L;

	public DataAccessException() {
		super();
	}

	public DataAccessException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DataAccessException(String msg) {
		super(msg);
	}

	public DataAccessException(Throwable throwable) {
		super(throwable);
	}

}
