package com.ladybug.framework.easy;

import java.util.Enumeration;
import java.util.Hashtable;

import com.ladybug.framework.base.service.ServiceControllerAdapter;
import com.ladybug.framework.base.service.ServiceControllerException;
import com.ladybug.framework.easy.database.DBType;
import com.ladybug.framework.system.exception.ApplicationException;
import com.ladybug.framework.system.exception.SystemException;

/**
 * 轻量级服务控制器，具有业务要继承此类
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 * 
 */
public class EasyController extends ServiceControllerAdapter {

	/**
	 * 事件
	 */
	private EasyEvent event;

	/**
	 * 响应结果
	 */
	private EasyResult result;

	/**
	 * 返回EasyEvent
	 * 
	 * @return EasyEvent
	 */
	public EasyEvent Event() {
		return event;
	}

	/**
	 * 返回EasyResult
	 * 
	 * @return EasyResult
	 */
	public EasyResult Result() {
		return result;
	}

	/**
	 * 初始化Event,为Event设置数据模型
	 * 
	 * @param application
	 *            应用
	 * @param key
	 *            event-key值
	 * @param def
	 *            模型数据字符串
	 * @throws ServiceControllerException
	 */
	public void initEvent(String application, String key, String def)
			throws ServiceControllerException {
		// 创建EasyEvent
		event = (EasyEvent) this.createEvent(application, key);
		// 设置数据模型
		event.CreateModel(def);
		// 设置默认值
		setEventDefaultValue();
	}

	/**
	 * 初始化Event,为Event设置数据模型
	 * 
	 * @param application
	 *            应用
	 * @param key
	 *            event-key值
	 * @param def
	 *            模型数据字符串数组
	 * @throws ServiceControllerException
	 */
	public void initEvent(String application, String key, String[] def)
			throws ServiceControllerException {
		event = (EasyEvent) this.createEvent(application, key);
		event.CreateModel(def);
		setEventDefaultValue();
	}

	/**
	 * 执行相应业务逻辑的Event,处理真正的业务逻辑
	 * 
	 * @throws SystemException
	 * @throws ApplicationException
	 */
	public void FireEvent() throws SystemException, ApplicationException {
		result = (EasyResult) dispatchEvent(this.Event());
	}

	/**
	 * 执行相应业务逻辑的Event，处理真正的业务逻辑
	 * 
	 * @param easyevent
	 *            EasyEvent
	 * @return
	 * @throws SystemException
	 * @throws ApplicationException
	 */
	public EasyResult FireEvent(EasyEvent easyevent) throws SystemException,
			ApplicationException {
		return (EasyResult) dispatchEvent(easyevent);
	}

	/**
	 * 设置请求属性
	 * 
	 * @param easyresult
	 *            EasyResult
	 * @param fromparamter
	 *            如果为false，则需要把model中为null的值转换为空字符串设置到请求属性中
	 */
	public void setRequestAttribute(EasyResult easyresult, boolean fromparamter) {
		if (easyresult != null) {
			this.setRequestAttibute(easyresult.Model(), fromparamter);
		}
	}

	/**
	 * 设置请求属性，同时model中为null的值转换为空字符串设置到请求属性中
	 * 
	 * @param easyresult
	 */
	public void setRequestAttribute(EasyResult easyresult) {
		if (easyresult != null) {
			this.setRequestAttibute(easyresult.Model(), false);
		}
	}

	/**
	 * 设置请求属性值
	 * 
	 * @param model
	 *            数据模型
	 * @param fromparamter
	 *            如果为false，则需要把model中为null的值转换为空字符串设置到请求属性中
	 */
	public void setRequestAttibute(EasyModel model, boolean fromparamter) {
		if (model != null) {
			Hashtable<String,Object> data = model.getHashTable();
			for (Enumeration<String> e = data.keys(); e.hasMoreElements();) {
				Object temp;
				String key = e.nextElement().toString();
				temp = model.getAsObject(key);
				if (!fromparamter) {
					if (temp == null) {
						this.getRequest().setAttribute(key, "");
					} else {
						this.getRequest().setAttribute(key, temp);
					}
				} else {
					if (temp != null && (!temp.equals(""))) {
						this.getRequest().setAttribute(key, temp);
					} else {
						this.getRequest().setAttribute(key,
								this.getRequest().getParameter(key));
					}
				}
			}
		}
	}

	/**
	 * 设置请求属性
	 * 
	 * @param key
	 * @param value
	 */
	public void setRequestAttribute(String key, Object value) {
		if (value == null) {
			this.getRequest().setAttribute(key, "");
		} else {
			this.getRequest().setAttribute(key, value);
		}
	}

	/**
	 * 设置默认值
	 */
	private void setEventDefaultValue() {
		if (event != null) {
			DBType.isOracle(getRequest());
			DBType.isSqlServer(getRequest());
		}
	}
}
