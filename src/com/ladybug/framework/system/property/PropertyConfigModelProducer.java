package com.ladybug.framework.system.property;

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
 * 属性配置模型创建者,解析property-config.xml配置文件，构建PropertyConfigModel
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class PropertyConfigModelProducer {

	/**
	 * 解析配置文件，构建PropertyConfigModel模型
	 * @param fileName 配置文件名称
	 * @return PropertyConfigModel
	 * @throws IllegalArgumentException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws Exception
	 */
	protected PropertyConfigModel createPropertyConfigModel(String fileName)
			throws IllegalArgumentException, ParserConfigurationException,
			SAXException, IOException, Exception {
		PropertyConfigModel model = null;
		//创建xml document构建器
		XMLDocumentProducer producer = new XMLDocumentProducer();
		//根据文件名取得Document
		Document doc = producer.getDocument(fileName);
		//取得根节点
		Node node = producer.getRoot(doc);
		//根据根节点构建XMLNode
		XMLNode root = new XMLNode(node);
		//根据根节点创建PropertyConfigModel
		model = getPropertyConfigModel(root);
		return model;

	}

	/**
	 * 根据根节点创建PropertyConfigModel
	 * @param node XMLNode xml根节点 
	 * @return PropertyConfigModel
	 */
	private PropertyConfigModel getPropertyConfigModel(XMLNode node) {
		PropertyConfigModel model = new PropertyConfigModel();
		Map<String,PropertyModel> map = new HashMap<String,PropertyModel>();

		//设置Service属性模型
		map.put("service", getPropertyModel(node, "service"));
		//设置event属性模型
		map.put("event", getPropertyModel(node, "event"));
		//设置data属性模型
		map.put("data", getPropertyModel(node, "data"));
		//设置log属性模型
		map.put("log", getPropertyModel(node, "log"));
		
		//设置缓存Map
		model.setProperties(map);

		return model;
	}

	/**
	 * 根据根节点和配置属性名称创建PropertyModel
	 * @param node XMLNode 根节点
	 * @param propId 属性配置名称，如service
	 * @return PropertyModel
	 */
	private PropertyModel getPropertyModel(XMLNode node, String propId) {
		PropertyModel model = new PropertyModel();
		//根据属性名称，查找节点元素
		XMLNode subNode = node.lookup(propId);
		if (subNode != null) {
			//取得handler-class标签值
			String handler = subNode.getString("handler-class");
			
			//设置属性处理器类名称(包含路径)
			model.setPropertyHandlerName(handler);
			
			//取得初始化参数列表
			List<PropertyHandlerParam> list = getInitParamProperties(subNode);
			//创建属性处理器参数数组并赋值
			PropertyHandlerParam[] param = new PropertyHandlerParam[list.size()];
			for (int i = 0; i < list.size(); i++) {
				param[i] = (PropertyHandlerParam) list.get(i);
			}
			
			//设置参数数组
			model.setParams(param);
		}
		
		return model;
	}

	/**
	 * 取得相应节点下的初始化配置参数列表
	 * 
	 * @param node XMLNode
	 * @return
	 */
	private List<PropertyHandlerParam> getInitParamProperties(XMLNode node) {
		//取得init-param标签数组
		XMLNode[] paramNode = node.select("init-param");
		List<PropertyHandlerParam> list = new ArrayList<PropertyHandlerParam>();
		//便利节点数组
		for (int i = 0; i < paramNode.length; i++) {
			PropertyHandlerParam param = new PropertyHandlerParam();
			//设置参数名称
			param.setName(paramNode[i].getString("param-name"));
			//设置参数值
			param.setValue(paramNode[i].getString("param-value"));

			list.add(param);
		}

		return list;
	}
}
