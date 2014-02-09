package com.ladybug.framework.base.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.ladybug.framework.system.property.PropertyHandlerException;
import com.ladybug.framework.system.property.PropertyParam;

/**
 * xml文件格式的数据文件处理器
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class XmlDataPropertyHandler implements DataPropertyHandler {

	public static final String DEFAULT_BUNDLE_NAME = "data-config";
	public static final String DEFAULT_BUNDLE_NAME_PARAM = "bundle";
	public static final String PARAM_DYNAMIC = "dynamic";

	private String xmlPrefix;
	private boolean dynamic;
	private Map<String,Map<String,DaoGroupModel> > models;
	private Map<String,ConnectorModel> connectors;
	private Map<String,ResourceModel> resources;

	public XmlDataPropertyHandler() {
		this.setXmlPrefix(null);
		this.setApplicationModels(new HashMap<String, Map<String, DaoGroupModel>>());
		this.setCommonDataModels(new HashMap<String, ConnectorModel>());
		this.setResourceParams(new HashMap<String, ResourceModel>());
	}

	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}

	public String getXmlPrefix() {
		return xmlPrefix;
	}

	public void setXmlPrefix(String xmlPrefix) {
		this.xmlPrefix = xmlPrefix;
	}

	private void setApplicationModels(Map<String,Map<String,DaoGroupModel> > applicationModels) {
		this.models = applicationModels;
	}

	private Map<String,Map<String,DaoGroupModel> > getApplicationModels() {
		return this.models;
	}

	private ConnectorModel getConnectorModel(String connectorName)
			throws DataPropertyException {
		return (ConnectorModel) getCommonDataModels().get(connectorName);
	}

	private Map<String,ConnectorModel> getCommonDataModels() throws DataPropertyException {
		if (this.isDynamic()) {
			return createConnectorModels(this.getXmlPrefix());
		}

		return this.connectors;
	}

	private void setCommonDataModels(Map<String,ConnectorModel> commonDataModels) {
		this.connectors = commonDataModels;
	}

	private void setResourceParams(Map<String,ResourceModel> resources) {
		this.resources = resources;
	}

	private Map<String,ResourceModel> getResources() throws DataPropertyException {
		if (isDynamic()) {
			return createResourceModels(this.getXmlPrefix());
		}

		return resources;
	}

	private Map<String,ResourceModel> createResourceModels(String xmlFileName)
			throws DataPropertyException {
		try {
			CommonDataModelProducer producer = new CommonDataModelProducer();
			Map<String,ResourceModel> result = producer.createResourceModels(xmlFileName.replace(
					'\\', '/'));
			return result;
		} catch (IllegalArgumentException e) {
			throw new DataPropertyException(e.getMessage(), e);
		} catch (ParserConfigurationException e) {
			throw new DataPropertyException(e.getMessage(), e);
		} catch (SAXException e) {
			throw new DataPropertyException(e.getMessage(), e);
		} catch (IOException e) {
			throw new DataPropertyException(e.getMessage(), e);
		}

	}

	private Map<String,ConnectorModel> createConnectorModels(String xmlFileName)
			throws DataPropertyException {

		try {
			CommonDataModelProducer producer = new CommonDataModelProducer();
			Map<String,ConnectorModel> result = producer.createConnectorModels(xmlFileName.replace(
					'\\', '/'));
			return result;
		} catch (IllegalArgumentException e) {
			throw new DataPropertyException(e.getMessage(), e);
		} catch (ParserConfigurationException e) {
			throw new DataPropertyException(e.getMessage(), e);
		} catch (SAXException e) {
			throw new DataPropertyException(e.getMessage(), e);
		} catch (IOException e) {
			throw new DataPropertyException(e.getMessage(), e);
		}

	}

	private DaoGroupModel getDaoGroupModel(String application, String key)
			throws DataPropertyException {
		DaoGroupModel result = null;
		synchronized (this.models) {
			Map<String,DaoGroupModel> keyModels = null;
			if (!isDynamic()) {
				keyModels = (Map<String, DaoGroupModel>) this.getApplicationModels().get(application);
			}
			if (keyModels == null) {
				keyModels = new HashMap<String,DaoGroupModel>();
				this.getApplicationModels().put(application, keyModels);
			}
			if (!isDynamic()) {
				result = (DaoGroupModel) keyModels.get(key);
			}
			if (result == null) {
				result = createDaoGroupModel(application, key);
				keyModels.put(key, result);
			}
		}
		return result;
	}

	private DaoGroupModel createDaoGroupModel(String application, String key)
			throws DataPropertyException {

		try {
			DaoGroupModelProducer producer = new DaoGroupModelProducer();
			DaoGroupModel dataModel = producer.createDaoGroupModel(application
					.replace('\\', '/'), key, this.getXmlPrefix());

			return dataModel;
		} catch (IllegalArgumentException e) {
			throw new DataPropertyException(e.getMessage() + ": application = "
					+ application + ", key = " + key, e);
		} catch (ParserConfigurationException e) {
			throw new DataPropertyException(e.getMessage() + ": application = "
					+ application + ", key = " + key, e);
		} catch (SAXException e) {
			throw new DataPropertyException(e.getMessage() + ": application = "
					+ application + ", key = " + key, e);
		} catch (IOException e) {
			throw new DataPropertyException(e.getMessage() + ": application = "
					+ application + ", key = " + key, e);
		}

	}

	@Override
	public String getConnectorClassName(String connectorName)
			throws DataPropertyException {
		ConnectorModel connector = this.getConnectorModel(connectorName);
		if (connector == null) {
			String message = null;
			try {
				message = ResourceBundle
						.getBundle("com.ladybug.framework.base.data.i18n")
						.getString(
								"ResourceBundleDataPropertyHandlerUtil.param.ConnectorClassNotDeclared");
			} catch (MissingResourceException e) {

			}
			throw new DataPropertyException(message + " : Connector name = "
					+ connectorName);
		}
		return connector.getConnectorClassName();
	}

	@Override
	public String getConnectorName(String application, String key,
			String connect) throws DataPropertyException {
		DaoGroupModel model = this.getDaoGroupModel(application, key);
		return model.getConnectorName(connect);
	}

	@Override
	public String getConnectorResource(String connectorName)
			throws DataPropertyException {
		ConnectorModel connector = this.getConnectorModel(connectorName);
		if (connector == null) {
			return null;
		}
		return connector.getConnectorResource();

	}

	@Override
	public String getDAOName(String application, String key, String connect)
			throws DataPropertyException {
		DaoGroupModel model = this.getDaoGroupModel(application, key);
		String daoName = null;

		if (connect != null) {
			daoName = model.getDAOName(connect);
		} else {
			DaoModel dao = model.getDefaultDao();
			daoName = dao.getDaoClass();
		}

		if ((daoName == null) || ("".equals(daoName))) {
			String message = null;
			try {
				message = ResourceBundle
						.getBundle("com.ladybug.framework.base.data.i18n")
						.getString(
								"ResourceBundleDataPropertyHandlerUtil.param.DAOClassNotDeclared");
			} catch (MissingResourceException e) {

			}
			throw new DataPropertyException(message + " : application = "
					+ application + ", key = " + key + ", connect = " + connect);
		}
		return daoName;
	}

	@Override
	public ResourceParam[] getResourceParams(String name)
			throws DataPropertyException {
		Map<String,ResourceModel>resources = getResources();

		ResourceModel resource = (ResourceModel) resources.get(name);
		if (resource == null) {
			return new ResourceParam[0];
		}

		ResourceParam[] params = resource.getParams();
		if (params == null) {
			return new ResourceParam[0];
		}
		return params;
	}

	@Override
	public boolean isDynamic() throws DataPropertyException {
		return this.dynamic;
	}

	@Override
	public void init(PropertyParam[] params) throws PropertyHandlerException {
		String xmlPrefix = null;
		String dynamic = null;

		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				if ("bundle".equals(params[i].getName())) {
					xmlPrefix = params[i].getValue();
				} else {
					if ("dynamic".equals(params[i].getName()))
						continue;
					dynamic = params[i].getValue();
				}
			}
		}

		if (xmlPrefix == null) {
			xmlPrefix = "data-config";
		}

		this.setXmlPrefix(xmlPrefix);
		Boolean dummyDynamic = new Boolean(dynamic);
		this.setDynamic(dummyDynamic.booleanValue());

		try {
			if (!isDynamic()) {
				this.setCommonDataModels(this.createConnectorModels(this
						.getXmlPrefix()));
				this.setResourceParams(this.createResourceModels(this
						.getXmlPrefix()));
			}
		} catch (Exception e) {
			throw new PropertyHandlerException(e.getMessage(), e);
		}

	}

}
