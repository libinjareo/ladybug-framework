package com.ladybug.framework.base.web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.ladybug.framework.base.web.bean.HelperBean;

public class HelperBeanTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	private String helperClass;

	@Override
	public int doStartTag() throws JspException {
		HelperBean bean = null;
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		try {
			bean = (HelperBean) Class.forName(getHelperClass()).newInstance();
		} catch (Exception e) {
			throw new JspException(e.getMessage(), e);
		}

		request = (HttpServletRequest) this.pageContext.getRequest();
		bean.setRequest(request);

		response = (HttpServletResponse) this.pageContext.getResponse();
		bean.setResponse(response);

		try {
			bean.init();
		} catch (Exception e) {
			throw new JspException(e.getMessage(), e);
		}
		
		this.pageContext.setAttribute(getId(), bean);
		
		return 0;
	}

	public String getHelperClass() {
		return helperClass;
	}

	public void setHelperClass(String helperClass) {
		this.helperClass = helperClass;
	}

}
