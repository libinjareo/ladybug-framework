package com.ladybug.framework.base.event;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.ladybug.framework.util.XMLDocumentProducer;
import com.ladybug.framework.util.XMLNode;

/**
 * 事件模型创建者
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class EventModelProducer {

	/**
	 * 应用
	 */
	private String application;
	
	/**
	 * event-key
	 */
	private String key;

	/**
	 * 创建EventGroupModel
	 * @param application 应用
	 * @param key event-key
	 * @param prefix 配置文件前缀
	 * @return EventGroupModel
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws EventPropertyException
	 * @throws IllegalArgumentException
	 */
	public EventGroupModel createEventModel(String application, String key,
			String prefix) throws ParserConfigurationException, SAXException,
			IOException, EventPropertyException, IllegalArgumentException {
		this.application = application;
		this.setKey(key);

		EventGroupModel model = null;
		XMLDocumentProducer producer = new XMLDocumentProducer();

		//取得文件名称
		String fileName = this.getPropertyPackage(application) + prefix + "-"
				+ this.getApplicationID(application);
		
		//根据文件名称封装Document对象
		Document doc = producer.getDocument(fileName);
		//根据Document取得根节点
		Node node = producer.getRoot(doc);
		//根据根节点封装XMLNode
		XMLNode root = new XMLNode(node);
		XMLNode groupNode = selectXmlNodeByKey(root, key);
		//构建EventGroupModel
		model = getEventGroupModel(groupNode);

		return model;
	}

	/**
	 * 创建EventGroupModel 
	 * 
	 * @param groupNode 根节点
	 * @return
	 * @throws EventPropertyException
	 */
	private EventGroupModel getEventGroupModel(XMLNode groupNode)
			throws EventPropertyException {
		EventGroupModel model = new EventGroupModel();
		model.setEventKey(groupNode.getString("event-key"));
		model.setEventName(groupNode.getString("event-class"));

		//设置event-factory模型
		model.setEventFactory(getEventFactoryModel(groupNode));
		//设置前置触发器集合
		model.setPreTriggerInfos(getPreTriggerInfos(application, groupNode));
		//设置响应触发器集合
		model.setPostTriggerInfos(getPostTriggerInfos(this.application, groupNode));
		
		return model;
	}

	/**
	 * 取得前置触发器集合
	 * 
	 * @param application 应用
	 * @param groupNode 根节点
	 * @return Collection，能够保持元素顺序
	 * @throws EventPropertyException
	 */
	public Collection<EventTriggerInfo> getPreTriggerInfos(String application, XMLNode groupNode)
			throws EventPropertyException {
		//触发器缓存，保持触发器顺序
		Map<Integer,EventTriggerInfo> infos = new TreeMap<Integer,EventTriggerInfo>();
		//查找所有pre-trigger标签
		XMLNode[] triggerNodes = groupNode.select("pre-trigger");
		if (triggerNodes != null) {
			for (int i = 0; i < triggerNodes.length; i++) {
				EventTriggerInfo info = new EventTriggerInfo();
				//设置序号
				info.setNumber(i + 1);
				//取得触发器类名称
				String triggerClass = triggerNodes[i].getString("trigger-class");
				
				if ((triggerClass == null) || ("".equals(triggerClass))) {
					//如果触发器类名称不存在，打印错误信息
					String message = null;
					try {
						message = ResourceBundle
								.getBundle(
										"com.ladybug.framework.base.event.i18n")
								.getString(
										"ResourceBundleEventPropertyHandlerUtil.TriggerNotDeclared");
					} catch (MissingResourceException e) {

					}

					throw new EventPropertyException(message
							+ " : application = " + this.application
							+ ", key = " + groupNode.getString("event-key"));

				}

				//设置触发器名称
				info.setName(triggerClass);
				//根据序号放入缓存
				infos.put(new Integer(i), info);
			}
		}

		return infos.values();
	}

	/**
	 * 设置响应触发器集合
	 * 
	 * @param application 应用
	 * @param groupNode 根节点
	 * @return Collection,能够保持元素顺序
	 * @throws EventPropertyException
	 */
	public Collection<EventTriggerInfo>getPostTriggerInfos(String application, XMLNode groupNode)
			throws EventPropertyException {
		Map<Integer,EventTriggerInfo> infos = new TreeMap<Integer,EventTriggerInfo>();
		//查找所有post-trigger标签
		XMLNode[] triggerNodes = groupNode.select("post-trigger");
		if (triggerNodes != null) {
			//遍历post-trigger标签数组
			for (int i = 0; i < triggerNodes.length; i++) {
				EventTriggerInfo info = new EventTriggerInfo();
				//设置顺序
				info.setNumber(i + 1);
				//取得触发器类路径
				String triggerClass = triggerNodes[i].getString("trigger-class");
				
				//如果触发器类不存在，打印错误信息
				if ((triggerClass == null) || ("".equals(triggerClass))) {
					String message = null;
					try {
						message = ResourceBundle
								.getBundle(
										"com.ladybug.framework.base.event.i18n")
								.getString(
										"ResourceBundleEventPropertyHandlerUtil.TriggerNotDeclared");
					} catch (MissingResourceException e) {

					}

					throw new EventPropertyException(message
							+ " : application = " + this.application
							+ ", key = " + groupNode.getString("event-key"));

				}
				
				//设置触发器类名称
				info.setName(triggerClass);
				
				//按照顺序放入缓存
				infos.put(new Integer(i), info);
			}
		}

		return infos.values();
	}

	/**
	 * 取得EventFactoryModel
	 * @param eventGroupNode
	 * @return
	 * @throws EventPropertyException
	 */
	private EventFactoryModel getEventFactoryModel(XMLNode eventGroupNode)
			throws EventPropertyException {
		EventFactoryModel factory = new EventFactoryModel();
		//设置工厂类路径
		factory.setFactoryName(getFactoryName(eventGroupNode));
		//设置工厂类参数
		factory.setFactoryParams(getEventFactoryParams(eventGroupNode));
		
		return factory;
	}

	/**
	 * 查找event-factory/init-param标签值，设置事件监听工厂数组
	 * @param groupNode
	 * @return
	 * @throws EventPropertyException
	 */
	private EventListenerFactoryParam[] getEventFactoryParams(XMLNode groupNode)
			throws EventPropertyException {
		//遍历所有标签值
		XMLNode[] paramNodes = groupNode.select("event-factory/init-param");
		EventListenerFactoryParam[] factoryParams;
		if (paramNodes != null) {
			factoryParams = new EventListenerFactoryParam[paramNodes.length];
			//遍历参数标签
			for (int i = 0; i < paramNodes.length; i++) {
				factoryParams[i] = new EventListenerFactoryParam();
				//设置参数名称
				factoryParams[i].setName(paramNodes[i].getString("param-name"));
				//设置参数值
				factoryParams[i].setValue(paramNodes[i].getString("param-value"));
				
				//如果还含有参数设置，则继续循环
				if ((factoryParams[i].getName() != null)
						&& (factoryParams[i].getValue() != null))
					continue;
				
				//如果没有设置，打印错误信息
				String message = null;
				try {
					message = ResourceBundle
							.getBundle(
									"com.ladybug.framework.base.event.i18n")
							.getString(
									"ResourceBundleEventPropertyHandlerUtil.FailedToGetFactoryParameter");
				} catch (MissingResourceException e) {

				}

				throw new EventPropertyException(message + " : application = "
						+ this.application + ", key = "
						+ groupNode.getString("event-key"));

			}

		} else {
			factoryParams = new EventListenerFactoryParam[0];
		}
		return factoryParams;
	}

	/**
	 * 查找event-factory/factory-class标签值，返回工厂类路径名称
	 * @param groupNode
	 * @return
	 * @throws EventPropertyException
	 */
	private String getFactoryName(XMLNode groupNode)
			throws EventPropertyException {
		String name = groupNode.getString("event-factory/factory-class");

		if ((name == null) || ("".equals(name))) {
			//如果名称为空打印错误信息
			String message = null;
			try {
				message = ResourceBundle
						.getBundle("com.ladybug.framework.base.event.i18n")
						.getString(
								"ResourceBundleEventPropertyHandlerUtil.FacotryClassNotDeclared");
			} catch (MissingResourceException e) {

			}

			throw new EventPropertyException(message + " : application = "
					+ this.application + ", key = "
					+ groupNode.getString("event-key"));
		}
		return name;
	}

	/**
	 * 查找目标值与event-group下event-key值相同的节点
	 * @param node
	 * @param targetKey 要查找的event-key目标值
	 * @return
	 * @throws EventPropertyException
	 */
	private XMLNode selectXmlNodeByKey(XMLNode node, String targetKey)
			throws EventPropertyException {
		XMLNode[] groups = node.select("event-group");
		XMLNode groupNode = null;
		for (int i = 0; i < groups.length; i++) {
			String key = groups[i].getString("event-key");
			if (targetKey.equals(key)) {
				groupNode = groups[i];
				break;
			}

		}

		if (groupNode == null) {
			throw new EventPropertyException("application = "
					+ this.application + ", key = " + targetKey);
		}
		return groupNode;
	}

	private String getPropertyPackage(String application) {
		String[] paramAry = application.split("[.]");
		StringBuffer buf = new StringBuffer();
		if (paramAry.length > 1) {
			for (int i = 0; i < paramAry.length - 1; i++) {
				buf.append(paramAry[i]);
				buf.append('/');
			}
		}
		return buf.toString();
	}

	private String getApplicationID(String application) {
		String[] paramAry = application.split("[.]");
		String id = paramAry[(paramAry.length - 1)];
		return id;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
