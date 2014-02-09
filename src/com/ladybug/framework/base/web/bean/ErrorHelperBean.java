package com.ladybug.framework.base.web.bean;

public class ErrorHelperBean extends HelperBean {

	private static final long serialVersionUID = 1L;

	private Throwable exception;

	public ErrorHelperBean() throws HelperBeanException {
		this.exception = null;
	}

	public Throwable getException() throws HelperBeanException {
		if (this.exception == null) {
			synchronized (this) {
				if (this.exception == null) {
					try {
						this.exception = (Throwable) getRequest().getAttribute(
								getServicePropertyHandler()
										.getExceptionAttributeName());
					} catch (Exception e) {
						throw new HelperBeanException(e.getMessage(), e);
					}
				}
			}
		}
		return this.exception;
	}

}
