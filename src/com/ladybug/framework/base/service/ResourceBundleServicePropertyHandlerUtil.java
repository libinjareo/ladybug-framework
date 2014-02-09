package com.ladybug.framework.base.service;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * 资源句柄服务属性处理工具类
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class ResourceBundleServicePropertyHandlerUtil {

	public static String getClientEncoding(ResourceBundle commonBundle)
			throws ServicePropertyException {
		String encoding;
		try {
			encoding = commonBundle.getString("client.encoding");
		} catch (MissingResourceException ex) {
			String message = null;
			try {
				message = ResourceBundle
						.getBundle(
								"com.ladybug.framework.base.service.i18n")
						.getString(
								"ResourceBundleServicePropertyHandlerUtil.FailedToGetClientEncoding");
			} catch (MissingResourceException es) {

			}
			throw new ServicePropertyException(message, ex);
		}
		return encoding;
	}

	private static Locale getRealLocale(String localeString) {
		StringTokenizer tokenizer = new StringTokenizer(localeString, "-_");
		if (tokenizer.countTokens() == 2) {
			String language = tokenizer.nextToken();
			String country = tokenizer.nextToken();
			return new Locale(language, country);
		} else if (tokenizer.countTokens() == 3) {
			String language = tokenizer.nextToken();
			String country = tokenizer.nextToken();
			String variant = tokenizer.nextToken();

			return new Locale(language, country, variant);
		}

		String message = null;
		try {
			message = ResourceBundle.getBundle(
					"com.ladybug.framework.base.web.tag.i18n").getString(
					"MessageTag.LocaleStringIncorrect");
		} catch (MissingResourceException es) {

		}

		throw new IllegalArgumentException(message + " : \"" + localeString
				+ "\"");
	}

	public static Locale getClientLocale(ResourceBundle commonBundle)
			throws ServicePropertyException {
		String localeString = null;
		Locale locale = null;
		try {
			localeString = commonBundle.getString("client.locale");
		} catch (MissingResourceException ex) {
			String message = null;
			try {
				message = ResourceBundle
						.getBundle(
								"com.ladybug.framework.base.service.i18n")
						.getString(
								"ResourceBundleServicePropertyHandlerUtil.FailedToGetClientLocale");
			} catch (MissingResourceException es) {

			}
			throw new ServicePropertyException(message, ex);
		}

		if (localeString != null) {
			locale = getRealLocale(localeString);
		}

		return locale;
	}

	public static String getInputErrorPagePath(ResourceBundle commonBundle)
			throws ServicePropertyException {
		String page = null;
		try {
			page = commonBundle.getString("input.error.page.path");
		} catch (MissingResourceException e) {
			String message = null;
			try {
				message = ResourceBundle
						.getBundle(
								"com.ladybug.framework.base.service.i18n")
						.getString(
								"ResourceBundleServicePropertyHandlerUtil.FailedToGetInputErrorPagePath");
			} catch (MissingResourceException es) {

			}
			throw new ServicePropertyException(message, e);
		}

		return page;
	}

	public static String getInputErrorPagePath(ResourceBundle commonBundle,
			ResourceBundle applicationBundle, String application)
			throws ServicePropertyException {
		String page = null;
		try {
			page = applicationBundle.getString("input.error.page.path");
		} catch (MissingResourceException e) {
			try {
				page = getInputErrorPagePath(commonBundle);
			} catch (ServicePropertyException ex) {
				String message = null;
				try {
					message = ResourceBundle
							.getBundle(
									"com.ladybug.framework.base.service.i18n")
							.getString(
									"ResourceBundleServicePropertyHandlerUtil.FailedToGetInputErrorPagePath");
				} catch (MissingResourceException es) {

				}
				throw new ServicePropertyException(message
						+ " : application = " + application, e);
			}
		}
		return page;
	}

	public static String getInputErrorPagePath(ResourceBundle commonBundle,
			ResourceBundle applicationBundle, String application, String service)
			throws ServicePropertyException {
		String page = null;
		try {
			page = applicationBundle.getString("input.error.page.path."
					+ service);
		} catch (MissingResourceException e) {
			try {
				page = getInputErrorPagePath(commonBundle, applicationBundle,
						application);
			} catch (ServicePropertyException ex) {
				String message = null;
				try {
					message = ResourceBundle
							.getBundle(
									"com.ladybug.framework.base.service.i18n")
							.getString(
									"ResourceBundleServicePropertyHandlerUtil.FailedToGetInputErrorPagePath");
				} catch (MissingResourceException exc) {
				}

				throw new ServicePropertyException(message
						+ " : application = " + application + ", service = "
						+ service, e);
			}

		}

		return page;
	}

	public static String getInputErrorPagePath(ResourceBundle commonBundle,
			ResourceBundle applicationBundle, String application,
			String service, String key) throws ServicePropertyException {
		String page = null;
		try {
			page = applicationBundle.getString("input.error.page.path."
					+ service + "." + key);
		} catch (MissingResourceException e) {
			try {
				page = getInputErrorPagePath(commonBundle, applicationBundle,
						application, service);
			} catch (ServicePropertyException ex) {
				String message = null;
				try {
					message = ResourceBundle
							.getBundle(
									"com.ladybug.framework.base.service.i18n")
							.getString(
									"ResourceBundleServicePropertyHandlerUtil.FailedToGetInputErrorPagePath");
				} catch (MissingResourceException exc) {
				}

				throw new ServicePropertyException(message
						+ " : application = " + application + ", service = "
						+ service + ", key = " + key, e);
			}

		}

		return page;
	}

	public static String getServiceErrorPagePath(ResourceBundle commonBundle)
			throws ServicePropertyException {
		String page = null;
		try {
			page = commonBundle.getString("service.error.page.path");
		} catch (MissingResourceException e) {
			String message = null;
			try {
				message = ResourceBundle
						.getBundle(
								"com.ladybug.framework.base.service.i18n")
						.getString(
								"ResourceBundleServicePropertyHandlerUtil.FailedToGetServiceErrorPagePath");
			} catch (MissingResourceException es) {

			}
			throw new ServicePropertyException(message, e);
		}

		return page;
	}

	public static String getServiceErrorPagePath(ResourceBundle commonBundle,
			ResourceBundle applicationBundle, String application)
			throws ServicePropertyException {
		String page = null;
		try {
			page = applicationBundle.getString("service.error.page.path");
		} catch (MissingResourceException e) {
			try {
				page = getServiceErrorPagePath(commonBundle);
			} catch (ServicePropertyException ex) {
				String message = null;
				try {
					message = ResourceBundle
							.getBundle(
									"com.ladybug.framework.base.service.i18n")
							.getString(
									"ResourceBundleServicePropertyHandlerUtil.FailedToGetServiceErrorPagePath");
				} catch (MissingResourceException exc) {
				}

				throw new ServicePropertyException(message
						+ " : application = " + application, e);
			}

		}

		return page;
	}

	public static String getServiceErrorPagePath(ResourceBundle commonBundle,
			ResourceBundle applicationBundle, String application, String service)
			throws ServicePropertyException {
		String page;
		try {
			page = applicationBundle.getString("service.error.page.path."
					+ service);
		} catch (MissingResourceException e) {
			try {

				page = getServiceErrorPagePath(commonBundle, applicationBundle,
						application);
			} catch (ServicePropertyException ex) {

				String message = null;
				try {
					message = ResourceBundle
							.getBundle(
									"com.ladybug.framework.base.service.i18n")
							.getString(
									"ResourceBundleServicePropertyHandlerUtil.FailedToGetServiceErrorPagePath");
				} catch (MissingResourceException exc) {
				}

				throw new ServicePropertyException(message
						+ " : application = " + application + ", service = "
						+ service, e);
			}
		}

		return page;
	}

	public static String getServiceErrorPagePath(ResourceBundle commonBundle,
			ResourceBundle applicationBundle, String application,
			String service, String key) throws ServicePropertyException {
		String page = null;
		try {
			page = applicationBundle.getString("service.error.page.path."
					+ service + "." + key);
		} catch (MissingResourceException e) {
			try {
				page = getServiceErrorPagePath(commonBundle, applicationBundle,
						application, service);
			} catch (ServicePropertyException ex) {
				String message = null;
				try {
					message = ResourceBundle
							.getBundle(
									"com.ladybug.framework.base.service.i18n")
							.getString(
									"ResourceBundleServicePropertyHandlerUtil.FailedToGetServiceErrorPagePath");
				} catch (MissingResourceException exc) {
				}

				throw new ServicePropertyException(message
						+ " : application = " + application + ", service = "
						+ service + ", key = " + key, e);
			}

		}

		return page;
	}

	public static String getSystemErrorPagePath(ResourceBundle commonBundle)
			throws ServicePropertyException {
		String page = null;
		try {
			page = commonBundle.getString("system.error.page.path");
		} catch (MissingResourceException e) {
			String message = null;
			try {
				message = ResourceBundle
						.getBundle(
								"com.ladybug.framework.base.service.i18n")
						.getString(
								"ResourceBundleServicePropertyHandlerUtil.FailedToGetSystemErrorPagePath");
			} catch (MissingResourceException es) {

			}
			throw new ServicePropertyException(message, e);
		}

		return page;
	}

	public static String getSystemErrorPagePath(ResourceBundle commonBundle,
			ResourceBundle applicationBundle, String application)
			throws ServicePropertyException {
		String page = null;
		try {
			page = applicationBundle.getString("system.error.page.path");
		} catch (MissingResourceException e) {
			try {
				page = getSystemErrorPagePath(commonBundle);
			} catch (ServicePropertyException ex) {
				String message = null;
				try {
					message = ResourceBundle
							.getBundle(
									"com.ladybug.framework.base.service.i18n")
							.getString(
									"ResourceBundleServicePropertyHandlerUtil.FailedToGetSystemErrorPagePath");
				} catch (MissingResourceException exc) {
				}

				throw new ServicePropertyException(message
						+ " : application = " + application, e);
			}

		}

		return page;
	}

	public static String getSystemErrorPagePath(ResourceBundle commonBundle,
			ResourceBundle applicationBundle, String application, String service)
			throws ServicePropertyException {
		String page = null;
		try {
			page = applicationBundle.getString("system.error.page.path."
					+ service);
		} catch (MissingResourceException e) {
			try {
				page = getSystemErrorPagePath(commonBundle, applicationBundle,
						application);
			} catch (ServicePropertyException ex) {
				String message = null;
				try {
					message = ResourceBundle
							.getBundle(
									"com.ladybug.framework.base.service.i18n")
							.getString(
									"ResourceBundleServicePropertyHandlerUtil.FailedToGetSystemErrorPagePath");
				} catch (MissingResourceException exc) {
				}

				throw new ServicePropertyException(message
						+ " : application = " + application + ", service = "
						+ service, e);
			}

		}

		return page;
	}

	public static String getSystemErrorPagePath(ResourceBundle commonBundle,
			ResourceBundle applicationBundle, String application,
			String service, String key) throws ServicePropertyException {
		String page = null;
		try {
			page = applicationBundle.getString("system.error.page.path."
					+ service + "." + key);
		} catch (MissingResourceException e) {
			try {
				page = getSystemErrorPagePath(commonBundle, applicationBundle,
						application, service);
			} catch (ServicePropertyException ex) {
				String message = null;
				try {
					message = ResourceBundle
							.getBundle(
									"com.ladybug.framework.base.service.i18n")
							.getString(
									"ResourceBundleServicePropertyHandlerUtil.FailedToGetSystemErrorPagePath");
				} catch (MissingResourceException exc) {
				}

				throw new ServicePropertyException(message
						+ " : application = " + application + ", service = "
						+ service + ", key = " + key, e);
			}

		}

		return page;
	}

	public static String getServiceServletPath(ResourceBundle commonBundle)
			throws ServicePropertyException {
		String path;
		try {
			path = commonBundle.getString("servlet.path");
		} catch (MissingResourceException e) {
			String message = null;
			try {
				message = ResourceBundle
						.getBundle(
								"com.ladybug.framework.base.service.i18n")
						.getString(
								"ResourceBundleServicePropertyHandlerUtil.FailedToGetServiceServletPath");
			} catch (MissingResourceException es) {

			}
			throw new ServicePropertyException(message, e);
		}

		return path;
	}

	public static String getNextPagePath(ResourceBundle applicationBundle,
			String application, String service) throws ServicePropertyException {
		String page;
		try {
			page = applicationBundle.getString("nextpage.path." + service);
		} catch (MissingResourceException e) {

			String message = null;
			try {
				message = ResourceBundle
						.getBundle(
								"com.ladybug.framework.base.service.i18n")
						.getString(
								"ResourceBundleServicePropertyHandlerUtil.FailedToGetNextPagePath");
			} catch (MissingResourceException ex) {
			}

			throw new ServicePropertyException(message + " : application = "
					+ application + ", service = " + service, e);
		}

		return page;
	}

	public static String getNextPagePath(ResourceBundle applicationBundle,
			String application, String service, String key)
			throws ServicePropertyException {
		String page;
		try {
			page = applicationBundle.getString("nextpage.path." + service + "."
					+ key);
		} catch (MissingResourceException e) {

			String message = null;
			try {
				message = ResourceBundle
						.getBundle(
								"com.ladybug.framework.base.service.i18n")
						.getString(
								"ResourceBundleServicePropertyHandlerUtil.FailedToGetNextPagePath");
			} catch (MissingResourceException ex) {
			}

			throw new ServicePropertyException(
					message + " : application = " + application
							+ ", service = " + service + ", key = " + key, e);
		}

		return page;
	}

	public static String getServiceControllerName(
			ResourceBundle applicationBundle, String application, String service)
			throws ServicePropertyException {
		String controller;
		try {
			controller = applicationBundle.getString("controller.class."
					+ service);
		} catch (MissingResourceException e) {

			controller = null;
		}

		return controller;
	}

	public static String getTransitionName(ResourceBundle applicationBundle,
			String application, String service) throws ServicePropertyException {
		String controller;
		try {
			controller = applicationBundle.getString("transition.class."
					+ service);
		} catch (MissingResourceException e) {

			controller = null;
		}

		return controller;
	}

	public static String getApplicationParamName(ResourceBundle commonBundle) {
		try {
			return commonBundle.getString("application.param");
		} catch (MissingResourceException e) {
		}
		return "application";
	}

	public static String getServiceParamName(ResourceBundle commonBundle) {
		try {
			return commonBundle.getString("service.param");
		} catch (MissingResourceException e) {
		}
		return "service";
	}

	public static String getExceptionAttributeName(ResourceBundle commonBundle)
			throws MissingResourceException {
		return commonBundle.getString("exception.attirbute");
	}

	public static String getEncodingAttributeName(ResourceBundle commonBundle) {
		try {
			return commonBundle.getString("encoding.attribute");
		} catch (MissingResourceException e) {
		}
		return "com.ladybug.framework.base.service.encoding";
	}

	public static String getLocaleAttributeName(ResourceBundle commonBundle) {
		try {
			return commonBundle.getString("locale.attribute");
		} catch (MissingResourceException e) {
		}
		return "com.ladybug.framework.base.service.locale";
	}
}
