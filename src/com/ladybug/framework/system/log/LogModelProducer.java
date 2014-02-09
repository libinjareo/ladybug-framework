package com.ladybug.framework.system.log;

import java.io.IOException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.ladybug.framework.util.XMLDocumentProducer;
import com.ladybug.framework.util.XMLNode;

/**
 * 日志模型创建者
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class LogModelProducer {

	public LogModel createLogModel(String fileName) throws ParserConfigurationException, SAXException, LogPropertyException, IOException, IllegalArgumentException{
		XMLDocumentProducer producer = new XMLDocumentProducer();
		//根据文件名创建Document对象
		Document doc = producer.getDocument(fileName);
		//根据Document对象创建根节点
		Node node = producer.getRoot(doc);
		//把根节点封装成XMLNode
		XMLNode root = new XMLNode(node);
		LogModel model = getLogModel(root);
		return model;
		
	}
	
	/**
	 * 根据XMLNode节点解析配置文件，创建LogModel
	 * @param root XMLNode根节点
	 * @return　LogModel
	 * @throws LogPropertyException
	 */
	private LogModel getLogModel(XMLNode root) throws LogPropertyException{
		LogModel model = new LogModel();
		model.setLogAgentName(getLogAgentName(root));
		model.setLogAgentParams(getLogAgentParams(root));
		return model;
	}
	
	/**
	 * 取得日志代理名称 
	 * @param node XMLNode根节点
	 * @return
	 * @throws LogPropertyException
	 */
	private String getLogAgentName(XMLNode node) throws LogPropertyException {
		String logAgentName = node.getString(LogModel.P_ID_AGENT_NAME);//agent-class
		if ((logAgentName == null) || "".equals(logAgentName)) {
			return null;
		}
		return logAgentName;
	}
	
	/**
	 * 取得日志代理参数数组
	 * @param root
	 * @return
	 * @throws LogPropertyException
	 */
	private LogAgentParam[] getLogAgentParams(XMLNode root) throws LogPropertyException{
		//便利所有init-param标签
		XMLNode[] paramNodes = root.select(LogModel.P_ID_INIT_PARAM);//init-param
		
		//根据配置文件节点个数创建代理参数数组
		LogAgentParam[] params = new LogAgentParam[paramNodes.length];
		for(int i = 0; i < params.length; i++){
			params[i] = getLogAgentParam(paramNodes[i]);	
		}
		
		return params;
	}
	
	/**
	 * 根据相应节点构建日志代理参数
	 * @param paramNode XMLNode (init-param)
	 * @return LogAgentParam
	 * @throws LogPropertyException
	 */
	private LogAgentParam getLogAgentParam(XMLNode paramNode)throws LogPropertyException {
		LogAgentParam param = new LogAgentParam();
		param.setName(getLogAgentParamName(paramNode));
		param.setValue(getLogAgentParamValue(paramNode));
		
		return param;
	}

	/**
	 * 根据节点取得日志参数名称
	 * @param paramNode XMLNode
	 * @return
	 * @throws LogPropertyException
	 */
	private String getLogAgentParamName(XMLNode paramNode)
			throws LogPropertyException {
		String paramName = paramNode.getString(LogModel.P_ID_PARAM_NAME);//param-name
		if ((paramName == null) || (paramName.equals(""))) {
			String message = null;
			try {
				message =ResourceBundle.getBundle(
						"com.ladybug.framework.system.log.i18n").getString(
						"LogManager.FailedToGetAgentParameters");
			} catch (MissingResourceException ex) {
			}

			throw new LogPropertyException(message);
		}
		return paramName;
	}

	/**
	 * 根据节点取得日志参数值
	 * @param paramNode XMLNode
	 * @return
	 * @throws LogPropertyException
	 */
	private String getLogAgentParamValue(XMLNode paramNode)
			throws LogPropertyException {
		String paramValue = paramNode.getString(LogModel.P_ID_PARAM_VALUE);//param-value
		if ((paramValue == null) || (paramValue.equals(""))) {
			String message = null;
			try {
				message = ResourceBundle.getBundle(
						"com.ladybug.framework.system.log.i18n").getString(
						"LogManager.FailedToGetAgentParameters");
			} catch (MissingResourceException ex) {
			}

			throw new LogPropertyException(message);
		}
		return paramValue;
	}
}
