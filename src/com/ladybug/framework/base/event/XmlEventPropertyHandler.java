package com.ladybug.framework.base.event;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.ladybug.framework.system.property.PropertyHandlerException;
import com.ladybug.framework.system.property.PropertyParam;

/**
 * XML事件属性处理器
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class XmlEventPropertyHandler implements EventPropertyHandler {

	public static final String DEFAULT_BUNDLE_NAME = "event-config";
	public static final String DEFAULT_BUNDLE_NAME_PARAM = "bundle";
	
	/**
	 * 是否为动态创建参数值
	 */
	public static final String PARAM_DYNAMIC = "dynamic";
	
	/**
	 * xml文件前缀
	 */
	private String xmlPrefix;
	
	/**
	 * 是否为动态创建标志
	 */
	private boolean dynamic = false;
	
	/**
	 * 二级缓存
	 */
	private Map<String,Map<String,EventGroupModel>> applicationModels;

	/**
	 * 默认构造函数，初始化缓存
	 */
	public XmlEventPropertyHandler() {
		this.setXmlPrefix(null);
		applicationModels = new HashMap<String,Map<String,EventGroupModel>>();
	}

	/**
	 * 取得配置文件前缀
	 * @return
	 */
	public String getXmlPrefix() {
		return xmlPrefix;
	}

	/**
	 * 设置配置文件前置
	 * @param xmlPrefix
	 */
	public void setXmlPrefix(String xmlPrefix) {
		this.xmlPrefix = xmlPrefix;
	}

	/**
	 * 取得Event组模型
	 * @param application
	 * @param key
	 * @return
	 * @throws EventPropertyException
	 */
	private synchronized EventGroupModel getEventModel(String application,
			String key) throws EventPropertyException {
		EventGroupModel eventModel = null;
		
		//同步缓存
		synchronized (this.applicationModels) {
			Map<String,EventGroupModel> keyModels = null;
			if (!isDynamic()) {
				//如果非动态创建则从缓存中取
				keyModels = (Map<String,EventGroupModel>) this.applicationModels.get(application);
			}
			if (keyModels == null) {
				//如果缓存中不存在，则新建一级缓存
				keyModels = new HashMap<String,EventGroupModel>();
				this.applicationModels.put(application, keyModels);
			}

			if (!isDynamic()) {
				//如果非动态创建则从缓存中取EventGroupModel
				eventModel = (EventGroupModel) keyModels.get(key);
			}

			if (eventModel == null) {
				//如果缓存中不存在,则重新创建
				eventModel = createEventModel(application, key);
				keyModels.put(key, eventModel);
			}
		}
		return eventModel;
	}

	/**
	 * 创建EventGroupModel
	 * @param application
	 * @param key
	 * @return
	 * @throws EventPropertyException
	 */
	private EventGroupModel createEventModel(String application, String key)
			throws EventPropertyException {

		try {
			EventModelProducer producer = new EventModelProducer();
			EventGroupModel eventModel = producer.createEventModel(application,
					key, this.getXmlPrefix());
			return eventModel;
		} catch (IllegalArgumentException e) {
			throw new EventPropertyException(e.getMessage()
					+ ": application = " + application + ", key = " + key, e);
		} catch (ParserConfigurationException e) {
			throw new EventPropertyException(e.getMessage()
					+ ": application = " + application + ", key = " + key, e);
		} catch (SAXException e) {
			throw new EventPropertyException(e.getMessage()
					+ ": application = " + application + ", key = " + key, e);
		} catch (IOException e) {
			throw new EventPropertyException(e.getMessage()
					+ ": application = " + application + ", key = " + key, e);
		}

	}

	@Override
	public void init(PropertyParam[] params) throws PropertyHandlerException {
		String bundleName = null;
		String dynamic = null;

		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				//参数bundle的值为文件前缀
				if ("bundle".equals(params[i].getName())) {
					bundleName = params[i].getValue();
				} else {
					if (!"dynamic".equals(params[i].getName()))
						continue;
					dynamic = params[i].getValue();
				}
			}
		}

		if (bundleName == null) {
			bundleName = "event-config";
		}
		
		//设置文件前置，如果bundle未设置值，默认为event-config
		this.setXmlPrefix(bundleName);
		
		//设置为是否动态创建
		Boolean dummyDynamic = new Boolean(dynamic);
		this.setDynamic(dummyDynamic.booleanValue());
	}

	private void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}

	@Override
	public boolean isDynamic() throws EventPropertyException {
		return dynamic;
	}

	@Override
	public String getEventName(String application, String key)
			throws EventPropertyException {
		EventGroupModel eventModel = getEventModel(application, key);
		return eventModel.getEventName();

	}

	@Override
	public String getEventListenerFactoryName(String application, String key)
			throws EventPropertyException {
		EventGroupModel eventModel = getEventModel(application, key);
		return eventModel.getEventFactory().getFactoryName();
	}

	@Override
	public EventListenerFactoryParam[] getEventListenerFactoryParams(
			String application, String key) throws EventPropertyException {
		EventGroupModel eventModel = getEventModel(application, key);
		return eventModel.getEventFactory().getFactoryParams();
	}

	@Override
	public Collection<EventTriggerInfo> getEventTriggerInfos(String application, String key)
			throws EventPropertyException {
		EventGroupModel eventModel = getEventModel(application, key);
		return eventModel.getPreTriggerInfos();
	}

	@Override
	public Collection<EventTriggerInfo> getPostEventTriggerInfos(String application, String key)
			throws EventPropertyException {
		EventGroupModel eventModel = getEventModel(application, key);
		return eventModel.getPostTriggerInfos();
	}

}
