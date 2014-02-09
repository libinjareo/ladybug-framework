package com.ladybug.framework.base.data;

import java.io.File;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.ladybug.framework.system.property.PropertyHandlerException;
import com.ladybug.framework.system.property.PropertyParam;

/**
 * 默认的Data属性处理器,对应于DataConfig.properties属性文件
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 */
public class DefaultDataPropertyHandler implements DataPropertyHandler {

	public static final String DEFAULT_BUNDLE_NAME = "DataConfig";
	public static final String DEFAULT_BUNDLE_NAME_PARAM = "bundle";

	private String bundlePrefix;
	private Map<String,ResourceBundle> bundles;
	private ResourceBundle commonBundle;

	public DefaultDataPropertyHandler() {
		this.setBundlePrefix(null);
		this.setApplicationBundles(null);
		this.setCommonBundle(null);
	}

	public String getBundlePrefix() {
		return bundlePrefix;
	}

	public void setBundlePrefix(String bundlePrefix) {
		this.bundlePrefix = bundlePrefix;
	}

	public Map<String,ResourceBundle> getApplicationBundles() {
		return bundles;
	}

	public void setApplicationBundles(Map<String,ResourceBundle> bundles) {
		this.bundles = bundles;
	}

	public ResourceBundle getCommonBundle() {
		return commonBundle;
	}

	public void setCommonBundle(ResourceBundle commonBundle) {
		this.commonBundle = commonBundle;
	}

	@Override
	public String getConnectorClassName(String connectorName)
			throws DataPropertyException {
		return ResourceBundleDataPropertyHandlerUtil.getConnectorClassName(getCommonBundle(), connectorName);
	}

	@Override
	public String getConnectorName(String application, String key,
			String connect) throws DataPropertyException {
		return ResourceBundleDataPropertyHandlerUtil.getConnectorName(this.getResourceBundle(application), application, key, connect);
	}

	@Override
	public String getConnectorResource(String connectorName)
			throws DataPropertyException {
		return ResourceBundleDataPropertyHandlerUtil.getConnectorResource(getCommonBundle(), connectorName);
	}

	@Override
	public String getDAOName(String application, String key,
			String connect) throws DataPropertyException {
		return ResourceBundleDataPropertyHandlerUtil.getDAOName(this.getResourceBundle(application), application, key, connect);
	}

	@Override
	public ResourceParam[] getResourceParams(String name)
			throws DataPropertyException {
		return ResourceBundleDataPropertyHandlerUtil.getResourceParams(getCommonBundle(), name);
	}

	@Override
	public boolean isDynamic() throws DataPropertyException {
		return false;
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

		if (bundleName == null) {
			bundleName = "DataConfig";
		}

		this.setBundlePrefix(bundleName);

		try {
			this.setCommonBundle(ResourceBundle.getBundle(bundleName));
		} catch (MissingResourceException e) {
			throw new PropertyHandlerException(e.getMessage(), e);
		}
	}

	private ResourceBundle getResourceBundle(String application)
			throws DataPropertyException {
		ResourceBundle result;
		synchronized (this.bundles) {
			result = (ResourceBundle) this.getApplicationBundles().get(
					application);
			if (result == null) {
				result = createResourceBundle(application);
				this.getApplicationBundles().put(application, result);
			}
		}
		return result;
	}

	private ResourceBundle createResourceBundle(String application)
			throws DataPropertyException {

		try {
			return ResourceBundle.getBundle(this
					.getPropertyPackage(application)
					+ this.getBundlePrefix()
					+ "_"
					+ this.getApplicationID(application));
		} catch (MissingResourceException e) {
			throw new DataPropertyException(e.getMessage(), e);
		}

	}

	private String getPropertyPackage(String application) {
		String[] paramAry = application.split("[.]");
		StringBuffer buf = new StringBuffer();
		if (paramAry.length > 1) {
			for (int i = 0; i < paramAry.length - 1; i++) {
				buf.append(paramAry[i]);
				buf.append(File.separator);
			}
		}
		return buf.toString();
	}

	private String getApplicationID(String application) {
		String[] paramAry = application.split("[.]");
		String id = paramAry[(paramAry.length - 1)];
		return id;
	}
}
