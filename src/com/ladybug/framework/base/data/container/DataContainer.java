package com.ladybug.framework.base.data.container;

import com.ladybug.framework.base.data.DataAccessController;
import com.ladybug.framework.base.data.DataPropertyHandler;
import com.ladybug.framework.system.container.Container;


/**
 * 数据容器接口, 主要功能：
 * 1）取得DataPropertyHandler
 * 2）取得DataAccessController
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public interface DataContainer extends Container {

	public static final String DATA_PROPERTY_HANDLER_KEY = "data";

	/**
	 * 取得数据属性处理器
	 * @return
	 */
	public  DataPropertyHandler getDataPropertyHandler();

	/**
	 * 取得数据传输(DAO)控制器
	 * @return
	 */
	public  DataAccessController getDataAccessController();
}
