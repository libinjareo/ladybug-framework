package com.ladybug.framework.base.web.tag;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

public class HelperBeanTEI extends TagExtraInfo {

	@Override
	public VariableInfo[] getVariableInfo(TagData tagData) {
		VariableInfo info = new VariableInfo(tagData.getId(),
				tagData.getAttributeString("class"), true, 1);

		VariableInfo[] result = { info };

		return result;
	}

	@Override
	public boolean isValid(TagData tagData) {
		Object helperClassObject = tagData.getAttribute("class");

		Object helperClasssDeprecatedObject = tagData
				.getAttribute("helperClass");

		if ((helperClassObject == null)
				&& (helperClasssDeprecatedObject == null)) {
			return false;
		}

		if ((helperClassObject != null)
				&& (helperClasssDeprecatedObject != null)) {
			return false;
		}

		if (helperClassObject != null) {
			if (helperClassObject.equals(TagData.REQUEST_TIME_VALUE)) {
				return false;
			}

			String helperClass = tagData.getAttributeString("class");
			if (helperClass.trim().equals("")) {
				return false;
			}
		}

		if (helperClasssDeprecatedObject != null) {
			if (helperClasssDeprecatedObject.equals(TagData.REQUEST_TIME_VALUE)) {
				return false;
			}
			String helperClassDeprecated = tagData
					.getAttributeString("helperClass");

			if (helperClassDeprecated.trim().equals("")) {
				return false;
			}
		}
		return true;
	}

}
