package com.ladybug.framework.base.event;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.Vector;

/**
 * Event属性处理器工具类，针对properties文件
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class ResourceBundleEventPropertyHandlerUtil {

	public static String getEventName(ResourceBundle applicationBundle,
			String application, String key) throws EventPropertyException {
		String name = null;
		try {
			name = applicationBundle.getString("event.class." + key);
		} catch (MissingResourceException e) {
			name = null;
		}
		return name;
	}

	public static String getEventListenerFactoryName(
			ResourceBundle applicationBundle, String application, String key)
			throws EventPropertyException {
		String name = null;
		try {
			name = applicationBundle.getString("factory.class." + key);
		} catch (MissingResourceException e) {
			String message = null;
			try {
				message = ResourceBundle
						.getBundle("com.ladybug.framework.base.event.i18n")
						.getString(
								"ResourceBundleEventPropertyHandlerUtil.FailedToGetFactory");
			} catch (MissingResourceException ex) {

			}
			throw new EventPropertyException(message + " : application = "
					+ application + ", key = " + key, e);
		}

		if ((name == null) || ("".equals(name))) {
			String message = null;
			try {
				message = ResourceBundle
						.getBundle("com.ladybug.framework.base.event.i18n")
						.getString(
								"ResourceBundleEventPropertyHandlerUtil.FacotryClassNotDeclared");
			} catch (MissingResourceException ex) {

			}
			throw new EventPropertyException(message + " : application = "
					+ application + ", key = " + key);

		}
		return name;

	}

	public static EventListenerFactoryParam[] getEventListenerFactoryParams(
			ResourceBundle applicationBundle, String application, String key)
			throws EventPropertyException {
		Vector<EventListenerFactoryParam> params = new Vector<EventListenerFactoryParam>();
		String prefix = "factory.param." + key + ".";

		Enumeration<String> enume = applicationBundle.getKeys();
		while (enume.hasMoreElements()) {
			String propertyName = (String) enume.nextElement();
			if (propertyName.startsWith(prefix)) {
				EventListenerFactoryParam param = new EventListenerFactoryParam();
				param.setName(propertyName.substring(prefix.length()));
				try {
					param.setValue(applicationBundle.getString(propertyName));
				} catch (MissingResourceException e) {
					String message = null;
					try {
						message = ResourceBundle
								.getBundle(
										"com.ladybug.framework.base.event.i18n")
								.getString(
										"ResourceBundleEventPropertyHandlerUtil.FailedToGetFactoryParameter");
					} catch (MissingResourceException ex) {

					}
					throw new EventPropertyException(message
							+ " : application = " + application + ", key = "
							+ key);

				}

				params.add(param);
			}
		}

		EventListenerFactoryParam[] result = new EventListenerFactoryParam[params
				.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = (EventListenerFactoryParam) params.elementAt(i);
		}

		return result;
	}

	public static Collection<EventTriggerInfo> getEventTriggerInfos(
			ResourceBundle applicationBundle, String application, String key)
			throws EventPropertyException {
		Enumeration<String> enume = null;
		EventTriggerInfo info = null;
		Map<Integer,EventTriggerInfo> infos = new TreeMap<Integer,EventTriggerInfo>();

		String prefix = "trigger.class." + key + ".";
		String resourceKey = null;
		String name = null;

		int num = 0;

		enume = applicationBundle.getKeys();

		while (enume.hasMoreElements()) {
			resourceKey = (String) enume.nextElement();
			if (resourceKey.startsWith(prefix)) {
				int period = resourceKey.indexOf(".", prefix.length() + 1);

				if (period != -1) {
					if (resourceKey.substring(period).equals(".pre")) {
						num = Integer.parseInt(resourceKey.substring(
								prefix.length(),
								resourceKey.length() - ".pre".length()));
					}
				} else {
					num = Integer.parseInt(resourceKey.substring(prefix
							.length()));
				}

				info = new EventTriggerInfo();
				info.setNumber(num);
				try {
					name = applicationBundle.getString(resourceKey);
				} catch (MissingResourceException e) {
					String message = null;
					try {
						message = ResourceBundle
								.getBundle(
										"com.ladybug.framework.base.event.i18n")
								.getString(
										"ResourceBundleEventPropertyHandlerUtil.FailedToGetTrigger");
					} catch (MissingResourceException ex) {

					}
					throw new EventPropertyException(message
							+ " : application = " + application + ", key = "
							+ key, e);
				}

				if ((name == null) || "".equals(name)) {
					String message = null;
					try {
						message = ResourceBundle
								.getBundle(
										"com.ladybug.framework.base.event.i18n")
								.getString(
										"ResourceBundleEventPropertyHandlerUtil.FailedToGetTrigger");
					} catch (MissingResourceException ex) {

					}
					throw new EventPropertyException(message
							+ " : application = " + application + ", key = "
							+ key);
				}

				info.setName(name);

				infos.put(new Integer(num), info);
			}
		}
		return infos.values();
	}

	public static Collection<EventTriggerInfo> getPostEventTriggerInfos(
			ResourceBundle applicationBundle, String application, String key)
			throws EventPropertyException {
		Enumeration<String> enume = null;
		EventTriggerInfo info = null;
		Map<Integer,EventTriggerInfo> infos = new TreeMap<Integer,EventTriggerInfo>();

		String prefix = "trigger.class." + key + ".";
		String resourceKey = null;
		String name = null;

		int num = 0;

		enume = applicationBundle.getKeys();

		while (enume.hasMoreElements()) {
			resourceKey = (String) enume.nextElement();
			if (resourceKey.startsWith(prefix)) {
				int period = resourceKey.indexOf(".", prefix.length() + 1);

				if ((period == -1)
						|| (!resourceKey.substring(period).equals(".post")))
					continue;
				num = Integer.parseInt(resourceKey.substring(prefix.length(),
						resourceKey.length() - ".post".length()));

				info = new EventTriggerInfo();
				info.setNumber(num);
				try {
					name = applicationBundle.getString(resourceKey);
				} catch (MissingResourceException e) {
					String message = null;
					try {
						message = ResourceBundle
								.getBundle(
										"com.ladybug.framework.base.event.i18n")
								.getString(
										"ResourceBundleEventPropertyHandlerUtil.FailedToGetTrigger");
					} catch (MissingResourceException ex) {

					}
					throw new EventPropertyException(message
							+ " : application = " + application + ", key = "
							+ key, e);
				}

				if ((name == null) || "".equals(name)) {
					String message = null;
					try {
						message = ResourceBundle
								.getBundle(
										"com.ladybug.framework.base.event.i18n")
								.getString(
										"ResourceBundleEventPropertyHandlerUtil.FailedToGetTrigger");
					} catch (MissingResourceException ex) {

					}
					throw new EventPropertyException(message
							+ " : application = " + application + ", key = "
							+ key);
				}

				info.setName(name);

				infos.put(new Integer(num), info);
			}
		}
		return infos.values();
	}
}
