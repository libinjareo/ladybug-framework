package com.ladybug.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * XML文件Document对象创建器
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */
public class XMLDocumentProducer {

	/**
	 * 根据XML文件名称，取得类路径下的XML文件Document对象
	 * @param fileName 文件名称
	 * @return Document
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public Document getDocument(String fileName)
			throws ParserConfigurationException, SAXException, IOException,
			IllegalArgumentException {
		//如果文件名不是以xml格式结尾，则添加xml后缀
		if (fileName.lastIndexOf(".xml") == -1) {
			fileName = fileName + ".xml";
		}
		Document document = null;
		DocumentBuilderFactory dfb = DocumentBuilderFactory.newInstance();
		dfb.setValidating(false);

		DocumentBuilder db = dfb.newDocumentBuilder();
// update at 20130811 start 
//		// 加载源代码路径下的资源,ClassLoader的getResourceAsStream方法只支持相对路径
//		ClassLoader classLoader = XMLDocumentProducer.class.getClassLoader();
//		InputStream inputStream = classLoader.getResourceAsStream(fileName);
//
//		if (inputStream == null) {
//			classLoader = Thread.currentThread().getContextClassLoader();
//			inputStream = classLoader.getResourceAsStream(fileName);
//		}

// update at 20130811 end
		// Class的getResourceAsStream方法只支持绝对路径
//		if (inputStream == null) {
//			inputStream = XMLDocumentProducer.class
//					.getResourceAsStream(fileName);
//		}
//		if (inputStream == null) {
//			// 硬加载磁盘上的资源
//			inputStream = new FileInputStream(new File(fileName));
//
//		}
		
		ClassPathResource classPathResource = new ClassPathResource(fileName);
		InputStream inputStream = classPathResource.getInputStream();
		

		//根据输入流得到Document对象
		document = db.parse(inputStream);
		
		return document;
	}

	/**
	 * 取得文件名称
	 * @param name 文件名称，无.xml后缀
	 * @param locale Locale对象，1.0版本暂且不用
	 * @return
	 */
	public static String getFileName(String name, Locale locale) {
		StringBuffer sb = new StringBuffer();
		sb.append(name);
//		if (locale != null) {
//			if (!locale.getLanguage().equals("")) {
//				sb.append("-");
//				sb.append(locale.getLanguage());
//			}
//			if (!locale.getCountry().equals("")) {
//				sb.append("_");
//				sb.append(locale.getCountry());
//			}
//			if (!locale.getVariant().equals("")) {
//				sb.append("_");
//				sb.append(locale.getVariant());
//			}
//		}
		sb.append(".xml");
		return sb.toString().replace('\\', '/');
	}

	/**
	 * 判断文件是否存在
	 * @param name 文件名称
	 * @param locale
	 * @return
	 */
	public static synchronized boolean isFileExist(String name, Locale locale) {
		boolean result = false;
		String fileName = getFileName(name, locale);

		// update at 20130811 start 
//		
//		//取相对路径
//		ClassLoader classLoader = XMLDocumentProducer.class.getClassLoader();
//		InputStream inputStream = classLoader.getResourceAsStream(fileName);
//		
//		if (inputStream == null) {
//			classLoader = Thread.currentThread().getContextClassLoader();
//			inputStream = classLoader.getResourceAsStream(fileName);
//		}

		// update at 20130811 end 
		
//		//取绝对路径
//		if(inputStream == null){
//			inputStream = XMLDocumentProducer.class.getResourceAsStream(fileName);
//		}
//		
//		//直接加载磁盘上的资源
//		if(inputStream == null){
//			try {
//				inputStream = new FileInputStream(new File(fileName));
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
		
		ClassPathResource classPathResource = new ClassPathResource(fileName);
		InputStream inputStream = null;
		try {
			inputStream = classPathResource.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (inputStream != null) {
			result = true;
		}

		return result;
	}

	/**
	 * 取得根节点
	 * @param doc_node Document对象
	 * @return
	 */
	public Node getRoot(Document doc_node) {
		Node node = null;
		node = doc_node.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == 1)
				break;
			node = node.getNextSibling();
		}
		return node;
	}
}
