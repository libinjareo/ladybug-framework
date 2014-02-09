package com.ladybug.framework.base.data;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * properties属性文件解析工具类
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class ResourceBundleDataPropertyHandlerUtil {

	// dao.class.item=sample.presentation.item.model.data.ItemMasterDAO
	public static String getDAOName(ResourceBundle applicationBundle,
			String application, String key, String connect)
			throws DataPropertyException {
		String name;

		try {
			name = applicationBundle.getString("dao.class." + key + "."
					+ connect);
		} catch (MissingResourceException e) {
			try {
				name = applicationBundle.getString("dao.class." + key);
			} catch (MissingResourceException ex) {
				String message = null;
				try {
					ResourceBundle messageBundle = ResourceBundle
							.getBundle("com.ladybug.framework.base.data.i18n");
					message = messageBundle
							.getString("ResourceBundleDataPropertyHandlerUtil.param.DAOClassNotDeclared");
				} catch (MissingResourceException exc) {

				}
				throw new DataPropertyException(message + " : application = "
						+ application + ", key = " + key + ", connect = "
						+ connect, ex);
			}
		}

		return name;
	}

	// dao.connector.item=test_db
	public static String getConnectorName(ResourceBundle applicationBundle,
			String application, String key, String connect)
			throws DataPropertyException {
		String name;
		try {
			name = applicationBundle.getString("dao.connector." + key + "."
					+ connect);
		} catch (MissingResourceException e) {
			try {
				name = applicationBundle.getString("dao.connector." + key);
			} catch (MissingResourceException ex) {
				try {
					name = applicationBundle.getString("dao.connector");
				} catch (MissingResourceException exc) {
					name = null;
				}
			}
		}

		return name;
	}

	// connector.class.system_db=com.huateng.web.framework.base.data.SystemDBConnector
	public static String getConnectorClassName(ResourceBundle commonBundle,
			String connectorName) throws DataPropertyException {
		String name;
		try {
			name = commonBundle.getString("connector.class." + connectorName);
		} catch (MissingResourceException e) {
			String message = null;
			try {
				ResourceBundle messageBundle = ResourceBundle
						.getBundle("com.ladybug.framework.base.data.i18n");
				message = messageBundle
						.getString("ResourceBundleDataPropertyHandlerUtil.param.ConnectorClassNotDeclared");
			} catch (MissingResourceException exc) {

			}
			throw new DataPropertyException(message + " : Connector name = "
					+ connectorName, e);
		}

		return name;
	}

	public static String getConnectorResource(ResourceBundle commonBundle,
			String connectorName) throws DataPropertyException {
		String name;
		try {
			name = commonBundle
					.getString("connector.resource." + connectorName);
		} catch (MissingResourceException e) {
			name = null;
		}
		return name;
	}

	public static ResourceParam[] getResourceParams(
			ResourceBundle commonBundle, String name)
			throws DataPropertyException {
		String prefix = "resource.param." + name + ".";
		String value;
		Enumeration<String> keys = commonBundle.getKeys();

		Vector<ResourceParam> params = new Vector<ResourceParam>();

		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			if (key.startsWith(prefix)) {
				String paramName = key.substring(prefix.length());
				try {
					value = commonBundle.getString(key);
				} catch (MissingResourceException e) {
					String message = null;
					try {
						message = ResourceBundle
								.getBundle(
										"com.ladybug.framework.base.data.i18n")
								.getString(
										"ResourceBundleDataPropertyHandlerUtil.param.ResourceDetailNotDeclared");
					} catch (MissingResourceException ex) {

					}

					throw new DataPropertyException(message
							+ " : resource name = " + name, e);
				}

				ResourceParam param = new ResourceParam();
				param.setName(paramName);
				param.setValue(value);

				params.add(param);
			}
		}

		ResourceParam[] result = new ResourceParam[params.size()];
		for (int i = 0; i < params.size(); i++) {
			result[i] = (ResourceParam) params.get(i);
		}

		return result;
	}
}
