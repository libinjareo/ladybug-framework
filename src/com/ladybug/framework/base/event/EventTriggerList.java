package com.ladybug.framework.base.event;

import java.util.Collection;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import com.ladybug.framework.base.data.DataAccessController;
import com.ladybug.framework.system.exception.ApplicationException;
import com.ladybug.framework.system.exception.SystemException;

/**
 * 事件触发器列表
 * 
 * @author Administrator
 * 
 */
public class EventTriggerList {
	
	/**
	 * 触发器集合缓存,保持元素配置顺序
	 */
	private Collection<EventTrigger> triggers;

	// private DataAccessController dataAccessController;

	/**
	 * 构造函数,默认前置触发器列表
	 * @param application 应用
	 * @param key event-key
	 * @throws EventManagerException
	 * @throws EventPropertyException
	 * @throws EventTriggerException
	 */
	public EventTriggerList(String application, String key)
			throws EventManagerException, EventPropertyException,
			EventTriggerException {
		this(application, key, true);
	}

	/**
	 * 构造函数, 创建触发器集合
	 * 
	 * @param application 应用
	 * @param key event-key
	 * @param pre 是否为前置触发器
	 * @throws EventManagerException
	 * @throws EventPropertyException
	 * @throws EventTriggerException
	 */
	public EventTriggerList(String application, String key, boolean pre)
			throws EventManagerException, EventPropertyException,
			EventTriggerException {
		
		//迭代器
		Iterator<EventTriggerInfo> triggerInfos = null;
		Object triggerObject = null;
		
		//事件触发器
		EventTrigger trigger = null;
		//事件触发器信息
		EventTriggerInfo currentInfo = null;
		
		//如果为前置触发器
		if (pre) {
			//前置触发器集合
			triggerInfos = EventManager.getEventManager()
					.getEventPropertyHandler().getEventTriggerInfos(
							application, key).iterator();
		} else {
			//Post触发器集合
			triggerInfos = EventManager.getEventManager()
					.getEventPropertyHandler().getPostEventTriggerInfos(
							application, key).iterator();
		}

		this.triggers = new Vector<EventTrigger>();
		//进行集合遍历
		while (triggerInfos.hasNext()) {
			//依次取得触发器信息类
			currentInfo = (EventTriggerInfo) triggerInfos.next();
			try {
				//创建触发器对象
				triggerObject = Class.forName(currentInfo.getName())
						.newInstance();
			} catch (Exception e) {
				//打印异常信息
				String message = null;
				try {
					message = ResourceBundle
							.getBundle(
									"com.ladybug.framework.base.event.i18n")
							.getString("EventTriggerList.FailedToCreateTrigger");
				} catch (MissingResourceException ex) {

				}

				throw new EventTriggerException(message + " : trigger class = "
						+ currentInfo.getName() + ", application = "
						+ application + ", key = " + key, e);
			}
			//进行强制转换
			if (triggerObject instanceof EventTrigger) {
				trigger = (EventTrigger) triggerObject;
			} else {
				String message = null;
				try {
					message = ResourceBundle.getBundle(
							"com.ladybug.framework.base.event.i18n")
							.getString("EventTriggerList.TriggerImplemented");
				} catch (MissingResourceException ex) {

				}

				throw new EventTriggerException(message + " : trigger class = "
						+ currentInfo.getName() + ", application = "
						+ application + ", key = " + key);

			}
			//加入到缓存中
			this.triggers.add(trigger);
		}
	}

	/**
	 * 批量执行触发器
	 * 
	 * @param event 暂不使用
	 * @param controller 暂不使用
	 * @throws SystemException
	 * @throws ApplicationException
	 */
	public void fireAll(Event event, DataAccessController controller)
			throws SystemException, ApplicationException {
		if (this.triggers == null) {
			return;
		}
		
		//遍历缓存集合
		Iterator<EventTrigger>triggerIterator = this.triggers.iterator();
		while (triggerIterator.hasNext()) {
			//依次取得事件触发器对象
			EventTrigger currentTrigger = (EventTrigger) triggerIterator.next();
			//执行各个触发器业务逻辑
			currentTrigger.fire(event, controller);
		}
	}
}
