package com.ladybug.framework.system.log;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.ladybug.framework.system.property.PropertyHandlerException;
import com.ladybug.framework.system.property.PropertyParam;

/**
 * XML配置文件日志属性处理器，关联LogModel，对应于log-config.xml配置文件
 * 
 * @author james.li
 * @version 1.0
 *
 */
public class XmlLogPropertyHandler implements LogPropertyHandler {

	public static final String DEFAULT_BUNDLE_NAME = "log-config";
	
	/**
	 * 默认的日志配置文件参数名称
	 */
	public static final String DEFAULT_BUNDLE_NAME_PARAM = "bundle";
	
	/**
	 * 日志模型
	 */
	private LogModel model;

	public XmlLogPropertyHandler() {
		this.model = null;
	}

	@Override
	public String getLogAgentName() throws LogPropertyException {
		return this.model.getLogAgentName();
	}

	@Override
	public LogAgentParam[] getLogAgentParams() throws LogPropertyException {
		return this.model.getLogAgentParams();
	}

	@Override
	public void init(PropertyParam[] params)
			throws PropertyHandlerException {
		String xmlName = null;
		if(params != null){
			for(int i = 0; i < params.length; i++){
				if("bundle".equals(params[i].getName())){
					//配置文件名称为bundle参数值
					xmlName = params[i].getValue();
				}
			}
		}
		
		if(xmlName == null){
			//xmlName = "log-config";
			xmlName = DEFAULT_BUNDLE_NAME;
		}
		
//		if(xmlName.equals(DEFAULT_BUNDLE_NAME)){
//			xmlName = XmlLogPropertyHandler.class.getResource("/").getPath() + "log-config.xml";
//		}
		
		this.model = createLogModel(xmlName);
	}

	/**
	 * 解析配置文件，创建LogModel
	 * @param xmlFileName
	 * @return
	 * @throws PropertyHandlerException
	 */
	private LogModel createLogModel(String xmlFileName)
			throws PropertyHandlerException {
		try {
			LogModelProducer producer = new LogModelProducer();
			LogModel result = producer.createLogModel(xmlFileName);
			return result;
		} catch (ParserConfigurationException e) {
			throw new PropertyHandlerException(e.getMessage(), e);
		} catch (SAXException e) {
			throw new PropertyHandlerException(e.getMessage(), e);
		} catch (IOException e) {
			throw new PropertyHandlerException(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			throw new PropertyHandlerException(e.getMessage(), e);
		} catch (LogPropertyException e) {
			throw new PropertyHandlerException(e.getMessage(), e);
		}

	}
	
	public static void main(String[] args) throws Exception{
		XmlLogPropertyHandler xmlLogPropertyHandler = new XmlLogPropertyHandler();
		xmlLogPropertyHandler.init(null);
		String logAgentName = xmlLogPropertyHandler.getLogAgentName();
		System.out.println("logAgentName==> " + logAgentName);
	}
}
