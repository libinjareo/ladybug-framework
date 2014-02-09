package com.ladybug.framework.base.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.ladybug.framework.util.XMLDocumentProducer;
import com.ladybug.framework.util.XMLNode;

/**
 * 公共DataModel创建器
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class CommonDataModelProducer {

	/**
	 * 根据dao-config.xml文件创建ConnectorModel缓存
	 * 
	 * @param fileName data-config.xml配置文件
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws DataPropertyException
	 * @throws IllegalArgumentException
	 */
	public Map<String,ConnectorModel> createConnectorModels(String fileName)
			throws ParserConfigurationException, SAXException, IOException,
			DataPropertyException, IllegalArgumentException {
		Map<String,ConnectorModel> models = null;
		XMLDocumentProducer producer = new XMLDocumentProducer();
		Document doc = producer.getDocument(fileName);
		Node node = producer.getRoot(doc);
		XMLNode root = new XMLNode(node);

		models = createModels(root);

		return models;
	}

	private Map<String,ConnectorModel> createModels(XMLNode root) throws DataPropertyException {
		Map<String,ConnectorModel> models = new HashMap<String,ConnectorModel>();
		XMLNode[] nodes = root.select("connector");
		for (int i = 0; i < nodes.length; i++) {
			ConnectorModel connector = getConnectorModel(nodes[i]);
			models.put(connector.getConnectorName(), connector);
		}
		return models;
	}

	private ConnectorModel getConnectorModel(XMLNode connectorNode)
			throws DataPropertyException {
		ConnectorModel model = new ConnectorModel();
		model.setConnectorName(getConnectorName(connectorNode));
		model.setConnectorClassName(getConnectorClassName(connectorNode));
		model.setConnectorResource(getConnectorResource(connectorNode));

		return model;
	}

	private String getConnectorResource(XMLNode connectorNode)
			throws DataPropertyException {

		return connectorNode.getString("resource-name");
	}

	private String getConnectorClassName(XMLNode connectorNode)
			throws DataPropertyException {
		String connectorClass = connectorNode.getString("connector-class");
		if ((connectorClass == null) || ("".equals(connectorClass))) {
			String message = null;
			try {
				message = ResourceBundle
						.getBundle("com.ladybug.framework.base.data.i18n")
						.getString(
								"ResourceBundleDataPropertyHandlerUtil.param.ConnectorClassNotDeclared");
			} catch (MissingResourceException e) {

			}

			throw new DataPropertyException(message + " : Connector name = "
					+ connectorNode.getString("connector-name"));
		}
		return connectorClass;
	}

	private String getConnectorName(XMLNode connectorNode)
			throws DataPropertyException {
		String connectorName = connectorNode.getString("connector-name");
		if ((connectorName == null) || ("".equals(connectorName))) {
			throw new DataPropertyException();
		}
		return connectorName;
	}

	/**
	 * 根据data-config.xml配置文件创建ResourcModel缓存
	 * @param fileName
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws DataPropertyException
	 * @throws IllegalArgumentException
	 */
	public Map<String,ResourceModel> createResourceModels(String fileName)
			throws ParserConfigurationException, SAXException, IOException,
			DataPropertyException, IllegalArgumentException {

		Map<String,ResourceModel> models = null;
		XMLDocumentProducer producer = new XMLDocumentProducer();
		Document doc = producer.getDocument(fileName);
		Node node = producer.getRoot(doc);
		XMLNode root = new XMLNode(node);

		models = createResourceModels(root);

		return models;

	}

	private Map<String,ResourceModel> createResourceModels(XMLNode root) throws DataPropertyException {
		Map<String,ResourceModel> models = new HashMap<String,ResourceModel>();
		XMLNode[] nodes = root.select("resource");

		for (int i = 0; i < nodes.length; i++) {
			ResourceModel resource = getResourceModel(nodes[i]);
			models.put(resource.getConnectorResource(), resource);
		}
		return models;
	}

	private ResourceModel getResourceModel(XMLNode resourceNode)
			throws DataPropertyException {
		ResourceModel resourceModel = new ResourceModel();
		String resourceName = resourceNode.getString("resource-name");

		resourceModel.setConnectorResource(resourceName);

		if ((resourceName == null) || ("".equals(resourceName))) {
			throw new DataPropertyException();
		}

		ResourceParam[] initParams = getResourceParams(resourceNode);
		if ((initParams == null) || (initParams.length == 0)) {
			resourceModel.setParams(new ResourceParam[0]);
		} else {
			resourceModel.setParams(initParams);
		}

		return resourceModel;
	}

	private ResourceParam[] getResourceParams(XMLNode resourceNode)
			throws DataPropertyException {
		XMLNode[] initParamNodes = resourceNode.select("init-param");

		ResourceParam[] params = new ResourceParam[initParamNodes.length];
		for (int i = 0; i < params.length; i++) {
			params[i] = getResourceParam(initParamNodes[i]);
		}
		return params;
	}

	private ResourceParam getResourceParam(XMLNode initParam)
			throws DataPropertyException {
		ResourceParam param = new ResourceParam();
		param.setName(getResourceParamName(initParam));
		param.setValue(getResourceParamValue(initParam));
		return param;
	}

	private String getResourceParamValue(XMLNode initParam)
			throws DataPropertyException {
		String paramName = initParam.getString("param-value");
		if ((paramName == null) || ("".equals(paramName))) {
			String message = null;
			try {
				message = ResourceBundle
						.getBundle("com.ladybug.framework.base.data.i18n")
						.getString(
								"ResourceBundleDataPropertyHandlerUtil.param.ResourceDetailNotDeclared");
			} catch (MissingResourceException e) {

			}
			throw new DataPropertyException(message);
		}
		return paramName;
	}

	private String getResourceParamName(XMLNode initParam)
			throws DataPropertyException {
		String paramValue = initParam.getString("param-name");
		if ((paramValue == null) || ("".equals(paramValue))) {
			String message = null;
			try {
				message = ResourceBundle
						.getBundle("com.ladybug.framework.base.data.i18n")
						.getString(
								"ResourceBundleDataPropertyHandlerUtil.param.ResourceDetailNotDeclared");
			} catch (MissingResourceException e) {

			}
			throw new DataPropertyException(message + " : resource name = "
					+ initParam.getString("param-name"));
		}
		return paramValue;
	}

}
