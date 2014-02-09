package com.ladybug.framework.base.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.ladybug.framework.util.XMLDocumentProducer;
import com.ladybug.framework.util.XMLNode;

/**
 * DaoGroupModel创建器
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class DaoGroupModelProducer {
	
	/**
	 * 应用
	 */
	private String application;
	
	/**
	 * dao-key
	 */
	private String key;

	/**
	 * 取得包路径
	 * @param application，以.分割
	 * @return
	 */
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

	/**
	 * 取得应用ID
	 * @param application，以.分割
	 * @return
	 */
	private String getApplicationID(String application) {
		String[] paramAry = application.split("[.]");
		String id = paramAry[(paramAry.length - 1)];
		return id;
	}

	/**
	 * 创建DaoGroupModel实例
	 * 
	 * @param application 应用
	 * @param key dao-key
	 * @param prefix 前缀
	 * @return DaoGroupModel
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws DataPropertyException
	 * @throws IllegalArgumentException
	 */
	public DaoGroupModel createDaoGroupModel(String application, String key,
			String prefix) throws ParserConfigurationException, SAXException,
			IOException, DataPropertyException, IllegalArgumentException {
		DaoGroupModel model = null;

		this.application = application;
		this.key = key;

		XMLDocumentProducer producer = new XMLDocumentProducer();
		//设置文件名
		String fileName = this.getPropertyPackage(application) + prefix + "-"
				+ this.getApplicationID(application);
		//根据文件名创建Document
		Document doc = producer.getDocument(fileName);
		//根据Document创建根Node
		Node node = producer.getRoot(doc);
		//根据根Node封装XMLNode
		XMLNode root = new XMLNode(node);
		//查询GroupNode
		XMLNode groupNode = selectGroupNode(root);
		model = getDaoGroupModel(groupNode);
		return model;
	}

	/**
	 * 取得DaoGroupModel
	 * @param groupNode XMLNode
	 * @return DaoGroupModel
	 * @throws DataPropertyException
	 */
	private DaoGroupModel getDaoGroupModel(XMLNode groupNode)
			throws DataPropertyException {
		DaoGroupModel model = new DaoGroupModel();
		List<DaoModel> daos = getDaoModels(groupNode);

		for (int i = 0; i < daos.size(); i++) {
			DaoModel dao = (DaoModel) daos.get(i);
			if (dao.getConnectName() == null) {
				model.setDefaultDao(dao);
			} else {
				model.setDaoModel(dao.getConnectName(), dao);
			}
		}
		return model;
	}

	/**
	 * 取得dao-group标签内的所有dao标签
	 * @param groupNode XMLNode
	 * @return List
	 * @throws DataPropertyException
	 */
	private List<DaoModel> getDaoModels(XMLNode groupNode) throws DataPropertyException {
		List<DaoModel> daos = new ArrayList<DaoModel>();
		XMLNode[] daoNodes = groupNode.select("dao");
		for (int i = 0; i < daoNodes.length; i++) {
			DaoModel dao = getDaoModel(daoNodes[i]);
			daos.add(dao);
		}
		return daos;
	}

	/**
	 * 取得DaoModel
	 * @param daoNode
	 * @return
	 * @throws DataPropertyException
	 */
	private DaoModel getDaoModel(XMLNode daoNode) throws DataPropertyException {
		DaoModel dao = new DaoModel();
		dao.setConnectName(getConnectName(daoNode));
		dao.setDaoClass(getDaoClass(daoNode));
		dao.setConnectorName(getConnectorName(daoNode));
		return dao;
	}

	/**
	 * 取得connector-name标签值
	 * @param daoNode
	 * @return
	 * @throws DataPropertyException
	 */
	private String getConnectorName(XMLNode daoNode)
			throws DataPropertyException {
		String connectorName = daoNode.getString("connector-name");
		return connectorName;
	}

	/**
	 * 取得dao-class
	 * @param daoNode
	 * @return
	 * @throws DataPropertyException
	 */
	private String getDaoClass(XMLNode daoNode) throws DataPropertyException {
		String daoClass = daoNode.getString("dao-class");
		if ((daoClass == null) || ("".equals(daoClass))) {
			String message = null;
			try {
				message = ResourceBundle
						.getBundle("com.huateng.web.framework.base.data.i18n")
						.getString(
								"ResourceBundleDataPropertyHandlerUtil.param.DAOClassNotDeclared");
			} catch (MissingResourceException e) {

			}
			String connect = daoNode.getString("connect-name");
			if (connect == null) {
				throw new DataPropertyException(message + " : application = "
						+ this.application + ", key = " + this.key);
			}

			throw new DataPropertyException(message + " : application = "
					+ this.application + ", key = " + this.key + " connect = "
					+ connect);
		}
		return daoClass;
	}

	/**
	 * 取得connect-name标签值
	 * @param daoNode
	 * @return
	 * @throws DataPropertyException
	 */
	private String getConnectName(XMLNode daoNode) throws DataPropertyException {
		String connectName = daoNode.getString("connect-name");
		return connectName;

	}

	/**
	 * 查询对应dao-key值的dao-group节点
	 * @param root XMLNode
	 * @return XMLNode
	 * @throws DataPropertyException
	 */
	private XMLNode selectGroupNode(XMLNode root) throws DataPropertyException {
		XMLNode result = null;
		XMLNode[] groupNodes = root.select("dao-group");

		for (int i = 0; i < groupNodes.length; i++) {
			XMLNode groupNode = groupNodes[i];
			if (groupNode.getString("dao-key").equals(this.key)) {
				result = groupNodes[i];
				break;
			}
		}

		if (result == null) {
			throw new DataPropertyException("application = " + this.application
					+ " : key=" + this.key);
		}
		return result;
	}
}
