package com.ladybug.framework.system.log;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import com.ladybug.framework.system.property.PropertyHandlerException;
import com.ladybug.framework.system.property.PropertyParam;

/**
 * 默认的Log属性处理器，对应于配置文件LogConfig.properties,关联ResourceBundle
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 */
public class DefaultLogPropertyHandler implements LogPropertyHandler {

	public static final String DEFAULT_BUNDLE_NAME = "LogConfig";
	public static final String DEFAULT_BUNDLE_NAME_PARAM = "bundle";
	private ResourceBundle bundle;

	public DefaultLogPropertyHandler() {
		bundle = null;
	}

	@Override
	public String getLogAgentName() throws LogPropertyException {
		String name;
		try {
			name = this.bundle.getString("agent.class");
		} catch (MissingResourceException e) {
			name = null;
		} catch (Throwable e) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.system.log.i18n").getString(
						"DefaultLogPropertyHandler.FailedToGetAgent");
			} catch (MissingResourceException ex) {
			}
			throw new LogPropertyException(message, e);
		}
		return name;
	}

	@Override
	public LogAgentParam[] getLogAgentParams() throws LogPropertyException {
		Enumeration<String> keys = null;
		String key = null;
		String value = null;
		LogAgentParam param = null;
		LogAgentParam[] params = null;
		Vector<LogAgentParam> temp = new Vector<LogAgentParam>();

		keys = this.bundle.getKeys();
		while (keys.hasMoreElements()) {
			key = (String) keys.nextElement();
			if ("agent.param".startsWith(key)) {
				value = this.bundle.getString(key);
				//构建日志参数对象
				param = new LogAgentParam();
				param.setName(key);
				param.setValue(value);
				temp.add(param);
			}
		}

		params = new LogAgentParam[temp.size()];
		for (int i = 0; i < temp.size(); i++) {
			params[i] = (LogAgentParam) temp.elementAt(i);
		}
		return params;
	}

	@Override
	public void init(PropertyParam[] params) throws PropertyHandlerException {
		String bundleName = null;

		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				if ("bundle".equals(params[i].getName())) {
					bundleName = params[i].getValue();
				}
			}
		}

		if (bundleName == null)
			bundleName = "LogConfig";
		
		this.bundle = ResourceBundle.getBundle(bundleName);
	}
	
	public static void main(String[] args) throws Exception{
		DefaultLogPropertyHandler ph = new DefaultLogPropertyHandler();
		ph.init(null);
		String logAgentName = ph.getLogAgentName();
		System.out.println("logAgentName===> " + logAgentName);
	}

}
