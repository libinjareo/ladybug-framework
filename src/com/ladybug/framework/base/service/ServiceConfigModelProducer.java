package com.ladybug.framework.base.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.ladybug.framework.util.XMLDocumentProducer;
import com.ladybug.framework.util.XMLNode;

/**
 * 服务配置模型创建器
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class ServiceConfigModelProducer {
	
	/**
	 * 根据文件名称创建服务配置模型
	 * 
	 * @param fileName 文件名称
	 * @return
	 * @throws IllegalArgumentException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public ServiceConfigModel createServiceConfigModel(String fileName)
			throws IllegalArgumentException, ParserConfigurationException,
			SAXException, IOException {
		ServiceConfigModel model = null;
		//构建XML文件Document对象创建器
		XMLDocumentProducer producer = new XMLDocumentProducer();
		//根据文件名称，取得Document对象
		Document doc = producer.getDocument(fileName);
		//取得根节点
		Node node = producer.getRoot(doc);
		XMLNode root = new XMLNode(node);
		model = getServiceConfigModel(root);
		return model;
	}

	/**
	 * 创建服务配置模型
	 * @param rootNode
	 * @return
	 */
	private ServiceConfigModel getServiceConfigModel(XMLNode rootNode) {
		ServiceConfigModel config = new ServiceConfigModel();
		//创建相应服务模型并放入缓存中
		Map<String,ServiceModel> map = getServiceModels(rootNode);
		config.setServices(map);

		XMLNode inputErrorPageNode = rootNode.lookup("input-error");
		config.setInputErrorPage(getInputErrorPage(inputErrorPageNode));

		XMLNode serviceErrorPageNode = rootNode.lookup("service-error");
		config.setServiceErrorPage(getServiceErrorPage(serviceErrorPageNode));

		XMLNode systemErrorPageNode = rootNode.lookup("system-error");
		config.setSystemErrorPage(getSystemErrorPage(systemErrorPageNode));

		return config;
	}

	private ErrorPageModel getSystemErrorPage(XMLNode systemErrorPageNode) {
		return getErrorPage(systemErrorPageNode);
	}

	private ErrorPageModel getServiceErrorPage(XMLNode serviceErrorPageNode) {
		return getErrorPage(serviceErrorPageNode);
	}

	private ErrorPageModel getInputErrorPage(XMLNode inputErrorPageNode) {
		return getErrorPage(inputErrorPageNode);
	}

	/**
	 * 创建所有service配置
	 * 
	 * @param rootNode XMLNode
	 * @return
	 */
	private Map<String,ServiceModel> getServiceModels(XMLNode rootNode) {
		Map<String,ServiceModel> services = new HashMap<String,ServiceModel>();
		XMLNode[] serviceNodes = rootNode.select("service");
		for (int i = 0; i < serviceNodes.length; i++) {
			ServiceModel service = getServiceModel(serviceNodes[i]);
			services.put(service.getServiceId(), service);
		}
		return services;
	}

	/**
	 * 创建service业务模型
	 * 
	 * @param serviceNode
	 * @return
	 */
	private ServiceModel getServiceModel(XMLNode serviceNode) {
		ServiceModel service = new ServiceModel();
		
		//service-id
		service.setServiceId(getServiceId(serviceNode));
		//controller-class
		service.setServiceControllerName(getServiceControllerName(serviceNode));
		//transition-class
		service.setTransitionName(getTransitionName(serviceNode));
		//next-page
		service.setDefaultNextPage(getDefaultNextPage(serviceNode));

		List<NextPageModel> nextPages = getNextPages(serviceNode);
		for (int i = 0; i < nextPages.size(); i++) {
			NextPageModel nextPage = (NextPageModel) nextPages.get(i);
			service.setNextPage(nextPage.getPageKey(), nextPage);
		}

		List<ErrorPageModel> inputErrorPages = getInputErrorPages(serviceNode);
		for (int i = 0; i < inputErrorPages.size(); i++) {
			ErrorPageModel errorPage = (ErrorPageModel) inputErrorPages.get(i);
			if (errorPage.getPageKey() == null) {
				service.setDefaultInputErrorPage(errorPage);
			} else {
				service.setInputErrorPage(errorPage.getPageKey(), errorPage);
			}
		}

		List<ErrorPageModel> serviceErrorPages = getServiceErrorPages(serviceNode);
		for (int i = 0; i < serviceErrorPages.size(); i++) {
			ErrorPageModel errorPage = (ErrorPageModel) serviceErrorPages
					.get(i);

			if (errorPage.getPageKey() == null)
				service.setDefaultServiceErrorPage(errorPage);
			else {
				service.setServiceErrorPage(errorPage.getPageKey(), errorPage);
			}
		}

		List<ErrorPageModel> systemErrorPages = getSystemErrorPages(serviceNode);
		for (int i = 0; i < systemErrorPages.size(); i++) {
			ErrorPageModel errorPage = (ErrorPageModel) systemErrorPages.get(i);
			if (errorPage.getPageKey() == null)
				service.setDefaultSystemErrorPage(errorPage);
			else {
				service.setSystemErrorPage(errorPage.getPageKey(), errorPage);
			}
		}
		return service;
	}

	private List<ErrorPageModel> getServiceErrorPages(XMLNode serviceNode) {
		List<ErrorPageModel> errorPages = new ArrayList<ErrorPageModel>();
		XMLNode[] errorPageNodes = serviceNode.select("service-error");

		for (int i = 0; i < errorPageNodes.length; i++) {
			ErrorPageModel serviceError = getErrorPage(errorPageNodes[i]);
			errorPages.add(serviceError);
		}
		return errorPages;
	}

	private List<ErrorPageModel> getSystemErrorPages(XMLNode serviceNode) {
		List<ErrorPageModel> errorPages = new ArrayList<ErrorPageModel>();
		XMLNode[] errorPageNodes = serviceNode.select("system-error");

		for (int i = 0; i < errorPageNodes.length; i++) {
			ErrorPageModel serviceError = getErrorPage(errorPageNodes[i]);
			errorPages.add(serviceError);
		}
		return errorPages;
	}

	private NextPageModel getDefaultNextPage(XMLNode serviceNode) {
		NextPageModel nextPage = null;
		//String page = null;

		XMLNode[] nodeList = serviceNode.select("next-page");
		for (int i = 0; i < nodeList.length; i++) {
			if (nodeList[i].getString("page-key") == null) {
				nextPage = new NextPageModel();
				nextPage.setPagePath(nodeList[i].getString("page-path"));
			}
		}
		return nextPage;
	}

	private String getTransitionName(XMLNode serviceNode) {
		return serviceNode.getString("transition-class");
	}

	private String getServiceControllerName(XMLNode serviceNode) {

		return serviceNode.getString("controller-class");
	}

	private String getServiceId(XMLNode serviceNode) {
		String id = serviceNode.getString("service-id");
		return id;
	}

	private NextPageModel getNextPage(XMLNode nextPageNode) {
		NextPageModel nextPage = new NextPageModel();

		String key = nextPageNode.getString("page-key");
		String page = nextPageNode.getString("page-path");

		nextPage.setPageKey(key);
		nextPage.setPagePath(page);

		return nextPage;
	}

	private List<NextPageModel> getNextPages(XMLNode serviceNode) {
		List<NextPageModel> nextPages = new ArrayList<NextPageModel>();
		XMLNode[] nextPageNodes = serviceNode.select("next-page");

		for (int i = 0; i < nextPageNodes.length; i++) {
			NextPageModel nextPage = getNextPage(nextPageNodes[i]);
			nextPages.add(nextPage);
		}
		return nextPages;
	}

	private List<ErrorPageModel> getInputErrorPages(XMLNode serviceNode) {
		List<ErrorPageModel> errorPages = new ArrayList<ErrorPageModel>();
		XMLNode[] errorPageNodes = serviceNode.select("input-error");

		for (int i = 0; i < errorPageNodes.length; i++) {
			ErrorPageModel inputError = getErrorPage(errorPageNodes[i]);
			errorPages.add(inputError);
		}
		return errorPages;
	}

	private ErrorPageModel getErrorPage(XMLNode node) {
		ErrorPageModel errorPage = new ErrorPageModel();
		String key = null;
		String page = null;

		if (node != null) {
			key = node.getString("page-key");
			page = node.getString("page-path");
		}
		errorPage.setPageKey(key);
		errorPage.setErrorPage(page);

		return errorPage;
	}
}
