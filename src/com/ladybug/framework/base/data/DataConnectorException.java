package com.ladybug.framework.base.data;

import com.ladybug.framework.system.exception.SystemException;

public class DataConnectorException extends SystemException {


	private static final long serialVersionUID = 1L;

	public DataConnectorException() {
		super();
	}

	public DataConnectorException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DataConnectorException(String msg) {
		super(msg);
	}

	public DataConnectorException(Throwable throwable) {
		super(throwable);
	}
	
	

}
