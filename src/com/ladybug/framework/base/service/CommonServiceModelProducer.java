package com.ladybug.framework.base.service;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.ladybug.framework.system.property.PropertyParam;
import com.ladybug.framework.util.XMLDocumentProducer;
import com.ladybug.framework.util.XMLNode;

/**
 * 通用服务模型生产者
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class CommonServiceModelProducer {

	/**
	 * 解析配置文件，创建通用服务模型
	 * @param fileName 配置文件名称
	 * @return CommonServiceModel
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public CommonServiceModel createCommonServiceModel(String fileName)
			throws ParserConfigurationException, SAXException, IOException {
		CommonServiceModel model = null;
		XMLDocumentProducer producer = new XMLDocumentProducer();
		Document doc = producer.getDocument(fileName);
		//创建根节点
		Node node = producer.getRoot(doc);
		//封装根节点
		XMLNode root = new XMLNode(node);
		//根据根节点创建模型
		model = getCommonServiceModel(root);
		return model;

	}

	private ErrorPageModel getInputErrorPage(XMLNode node) {
		return getErrorPage(node);
	}

	private ErrorPageModel getServiceErrorPage(XMLNode node) {
		return getErrorPage(node);
	}

	private ErrorPageModel getSystemErrorPage(XMLNode node) {
		return getErrorPage(node);
	}

	private ErrorPageModel getErrorPage(XMLNode node) {
		ErrorPageModel errorPage = new ErrorPageModel();
		String page = null;
		if (node != null) {
			page = node.getString("page-path");
		}
		errorPage.setErrorPage(page);
		return errorPage;
	}

	private String getClientEncoding(XMLNode root) {
		return root.getString("client-encoding");
	}

	/**
	 * 根据根节点，创建CommonServiceModel
	 * @param root
	 * @return
	 */
	private CommonServiceModel getCommonServiceModel(XMLNode root) {
		CommonServiceModel model = new CommonServiceModel();

		XMLNode inputErrorPage = root.lookup("input-error");
		model.setInputErrorPage(this.getInputErrorPage(inputErrorPage));

		XMLNode serviceErrorPage = root.lookup("service-error");
		model.setServiceErrorPage(this.getServiceErrorPage(serviceErrorPage));

		XMLNode systemErrorPage = root.lookup("system-error");
		model.setSystemErrorPage(this.getSystemErrorPage(systemErrorPage));

		model.setClientEncoding(this.getClientEncoding(root));
		model.setEncodingAttributeName(getEncodingAttributeName(root));
		model.setClientLocale(getClientLocale(root));
		model.setLocaleAttributeName(getLocaleAttributeName(root));
		model.setContextPath(getContextPath(root));
		model.setServiceServletPath(getServiceServlet(root));
		model.setApplicationParamName(getApplicationParamName(root));
		model.setServiceParamName(getServiceParamName(root));
		model.setExceptionAttributeName(getExceptionAttributeName(root));
		return model;
	}

	private String getExceptionAttributeName(XMLNode root) {
		return root.getString("exception-attribute-name");
	}

	private String getServiceParamName(XMLNode root) {
		return root.getString("service-param-name");
	}

	private String getApplicationParamName(XMLNode root) {
		return root.getString("application-param-name");
	}

	private String getServiceServlet(XMLNode root) {
		return root.getString("service-servlet");
	}

	private String getContextPath(XMLNode root) {
		return root.getString("context-path");
	}

	private String getLocaleAttributeName(XMLNode root) {
		return root.getString("locale-attribute-name");
	}

	private String getClientLocale(XMLNode root) {
		return root.getString("client-locale");
	}

	private String getEncodingAttributeName(XMLNode root) {
		return root.getString("encoding-attribute-name");
	}

	@SuppressWarnings("unused")
	private PropertyParam[] getCacheParams(XMLNode cacheRuleNode) {
		StringBuffer initParamPath = new StringBuffer();
		initParamPath.append("cache").append("/").append("init-param");

		XMLNode[] initParamNodes = cacheRuleNode.select(initParamPath
				.toString());
		PropertyParam[] params = new PropertyParam[initParamNodes.length];
		for (int i = 0; i < params.length; i++) {
			params[i] = getInitParam(initParamNodes[i]);
		}
		return params;
	}

	private PropertyParam getInitParam(XMLNode initNode) {
		PropertyParam param = new PropertyParam();
		param.setName(initNode.getString("param-name"));
		param.setValue(initNode.getString("param-value"));
		return param;

	}

	@SuppressWarnings("unused")
	private String getCacheConditionClass(XMLNode cacheRuleNode) {
		StringBuffer path = new StringBuffer();
		path.append("cache-condition").append("/").append(
				"cache-condition-class");
		String cacheConditionClass = cacheRuleNode.getString(path.toString());
		return cacheConditionClass;
	}

	@SuppressWarnings("unused")
	private String getCacheClass(XMLNode cahceRuleNode) {
		StringBuffer path = new StringBuffer();
		path.append("cache").append("/").append("cache-class");

		String cacheClass = cahceRuleNode.getString(path.toString());
		return cacheClass;
	}

	/**
	 * 取得缓存参数数组
	 * @param cacheRuleNode
	 * @return
	 */
	@SuppressWarnings("unused")
	private PropertyParam[] getConditionParams(XMLNode cacheRuleNode) {
		StringBuffer initParamPath = new StringBuffer();
		initParamPath.append("cache-condition").append("/")
				.append("init-param");

		XMLNode[] initParamNodes = cacheRuleNode.select(initParamPath
				.toString());

		PropertyParam[] params = new PropertyParam[initParamNodes.length];

		for (int i = 0; i < initParamNodes.length; i++) {
			params[i] = getInitParam(initParamNodes[i]);
		}
		return params;
	}
}
