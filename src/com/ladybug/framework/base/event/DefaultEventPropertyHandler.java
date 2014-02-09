package com.ladybug.framework.base.event;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.ladybug.framework.system.property.PropertyHandlerException;
import com.ladybug.framework.system.property.PropertyParam;

/**
 * 默认的事件属性处理器
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 * @deprecated
 */
public class DefaultEventPropertyHandler implements EventPropertyHandler {

	public static final String DEFAULT_BUNDLE_NAME = "EventConfig";
	public static final String DEFAULT_BUNDLE_NAME_PARAM = "bundle";

	private String bundlePrefix;
	private Map<String,ResourceBundle> bundles;
	private Map<String,Map<String, Collection<EventTriggerInfo>> > eventTriggers;
	private Map<String,Map<String, Collection<EventTriggerInfo>> > postEventTriggers;

	public void setApplicationBundles(Map<String,ResourceBundle> applicationBundles) {
		this.bundles = applicationBundles;
	}

	public Map<String,ResourceBundle> getApplicationBundles() {
		return this.bundles;
	}

	public String getBundlePrefix() {
		return bundlePrefix;
	}

	public void setBundlePrefix(String bundlePrefix) {
		this.bundlePrefix = bundlePrefix;
	}

	public Map<String,Map<String, Collection<EventTriggerInfo>> > getEventTriggers() {
		return eventTriggers;
	}

	public void setEventTriggers(Map<String,Map<String, Collection<EventTriggerInfo>> > eventTriggers) {
		this.eventTriggers = eventTriggers;
	}

	public Map<String,Map<String, Collection<EventTriggerInfo>> > getPostEventTriggers() {
		return postEventTriggers;
	}

	public void setPostEventTriggers(Map<String,Map<String, Collection<EventTriggerInfo>> > postEventTriggers) {
		this.postEventTriggers = postEventTriggers;
	}

	private ResourceBundle getResourceBundle(String application)
			throws EventPropertyException {
		ResourceBundle result = (ResourceBundle) this.getApplicationBundles()
				.get(application);
		if (result == null) {
			synchronized (this) {
				result = (ResourceBundle) this.getApplicationBundles().get(
						application);

				if (result == null) {
					result = createResourceBundle(application);
					this.getApplicationBundles().put(application, result);
				}
			}
		}
		return result;
	}

	private ResourceBundle createResourceBundle(String application)
			throws EventPropertyException {
		try {
			return ResourceBundle.getBundle(this
					.getPropertyPackage(application)
					+ this.getBundlePrefix()
					+ "_" + this.getApplicationID(application));
		} catch (MissingResourceException e) {
			throw new EventPropertyException(e.getMessage(), e);
		}

	}

	@Override
	public void init(PropertyParam[] params) throws PropertyHandlerException {
		String bundleName = null;

		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				if ("bundle".equals(params[i].getName())) {
					bundleName = params[i].getValue();
				}
			}
		}

		if (bundleName == null) {
			bundleName = "EventConfig";
		}

		this.setBundlePrefix(bundleName);
	}

	@Override
	public boolean isDynamic() throws EventPropertyException {
		return false;
	}

	@Override
	public String getEventName(String application, String key)
			throws EventPropertyException {
		return ResourceBundleEventPropertyHandlerUtil.getEventName(
				this.getResourceBundle(application), application, key);
	}

	@Override
	public String getEventListenerFactoryName(String application, String key)
			throws EventPropertyException {
		return ResourceBundleEventPropertyHandlerUtil
				.getEventListenerFactoryName(
						this.getResourceBundle(application), application, key);
	}

	@Override
	public EventListenerFactoryParam[] getEventListenerFactoryParams(
			String application, String key) throws EventPropertyException {
		return ResourceBundleEventPropertyHandlerUtil
				.getEventListenerFactoryParams(
						this.getResourceBundle(application), application, key);
	}

	@Override
	public Collection<EventTriggerInfo> getEventTriggerInfos(String application, String key)
			throws EventPropertyException {

		Map<String, Collection<EventTriggerInfo>> infoCollections = null;
		Collection<EventTriggerInfo> infos = null;

		if (isDynamic()) {
			infos = ResourceBundleEventPropertyHandlerUtil
					.getEventTriggerInfos(this.getResourceBundle(application),
							application, key);
		} else {
			synchronized (this.eventTriggers) {
				infoCollections = (Map<String, Collection<EventTriggerInfo>>) this.getEventTriggers()
						.get(application);
				if (infoCollections == null) {
					infoCollections = new HashMap<String, Collection<EventTriggerInfo>>();
					this.getEventTriggers().put(application, infoCollections);
				}
				infos = (Collection<EventTriggerInfo>) infoCollections.get(key);
				if (infos == null) {
					infos = ResourceBundleEventPropertyHandlerUtil
							.getEventTriggerInfos(
									this.getResourceBundle(application),
									application, key);
					infoCollections.put(key, infos);
				}
			}
		}
		return infos;
	}

	@Override
	public Collection<EventTriggerInfo> getPostEventTriggerInfos(String application, String key)
			throws EventPropertyException {
		Map<String,Collection<EventTriggerInfo>> infoCollections = null;
		Collection<EventTriggerInfo> infos = null;

		if (isDynamic()) {
			infos = ResourceBundleEventPropertyHandlerUtil
					.getPostEventTriggerInfos(
							this.getResourceBundle(application), application,
							key);
		} else {
			synchronized (this.eventTriggers) {
				infoCollections = (Map<String,Collection<EventTriggerInfo>>) this.getEventTriggers()
						.get(application);
				if (infoCollections == null) {
					infoCollections = new HashMap<String,Collection<EventTriggerInfo>>();
					this.getPostEventTriggers().put(application,
							infoCollections);
				}
				infos = (Collection<EventTriggerInfo>) infoCollections.get(key);
				if (infos == null) {
					infos = ResourceBundleEventPropertyHandlerUtil
							.getPostEventTriggerInfos(
									this.getResourceBundle(application),
									application, key);
					infoCollections.put(key, infos);
				}
			}
		}
		return infos;
	}

	private String getPropertyPackage(String application) {
		String[] paramAry = application.split("[.]");
		StringBuffer buf = new StringBuffer();
		if (paramAry.length > 1) {
			for (int i = 0; i < paramAry.length - 1; i++) {
				buf.append(paramAry[i]);
				buf.append(File.separator);
			}
		}
		return buf.toString();
	}

	private String getApplicationID(String application) {
		String[] paramAry = application.split("[.]");
		String id = paramAry[(paramAry.length - 1)];
		return id;
	}
}
