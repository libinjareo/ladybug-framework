package com.ladybug.framework.base.web.tag;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class HelperBeanTagBeanInfo extends SimpleBeanInfo {

	@Override
	public PropertyDescriptor[] getPropertyDescriptors() {
		PropertyDescriptor[] properties = new PropertyDescriptor[3];

		try {
			properties[0] = new PropertyDescriptor("id", HelperBeanTag.class,
					"getId", "setId");
			properties[1] = new PropertyDescriptor("helperClass",
					HelperBeanTag.class, "getHelperClass", "setHelperClass");
			properties[2] = new PropertyDescriptor("class",
					HelperBeanTag.class, "getHelperClass", "setHelperClass");
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}

		return properties;
	}

}
