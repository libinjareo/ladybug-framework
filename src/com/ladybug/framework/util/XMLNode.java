package com.ladybug.framework.util;

import java.util.StringTokenizer;
import java.util.Vector;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * XML解析文件节点Node对象封装
 * 
 * @author james.li(libinjareo@163.com)
 * @version 1.0
 *
 */

public class XMLNode {
	/**
	 * Node接口
	 */
	private Node node;
	
	/**
	 * 文本值
	 */
	private String value = null;
	
	/**
	 * Text接口
	 */
	private Text text = null;
	private String uri = null;
	
	/**
	 * 父节点
	 */
	private Node parent_node = null;

	/**
	 * 构造函数，根据Document创建Node
	 * @param d Document
	 */
	public XMLNode(Document d) {
		this.node = d;
	}

	/**
	 * 构造函数，根据Element创建Node
	 * @param e
	 */
	public XMLNode(Element e) {
		this.node = e;
	}

	public XMLNode(Attr a) {
		this.node = a;
	}

	public XMLNode(Node node) {
		this.node = node;
	}

	public XMLNode(Node p, Node n) {
		this.parent_node = p;
		this.node = n;
	}

	public Node getNode() {
		return this.node;
	}

	public String getNodeName() {
		return this.node.getNodeName();
	}

	public void setNodeName(String name) {
		if (this.node instanceof Element) {
			Element ele = (Element) this.node;
			NodeList list = ele.getChildNodes();

			Node new_node = ele.getOwnerDocument().createElement(name);
			if (list.getLength() > 0) {
				Node[] items = new Node[list.getLength()];
				for (int i = 0; i < list.getLength(); i++) {
					items[i] = list.item(i);
				}
				for (int i = 0; i < items.length; i++) {
					new_node.appendChild(items[i]);
				}

			}

			ele.getParentNode().replaceChild(new_node, ele);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Vector parseTags(String tags) {
		Vector v = new Vector();
		if (tags != null) {
			StringTokenizer st = new StringTokenizer(tags, "/");
			while (st.hasMoreElements()) {
				v.add(st.nextToken());
			}
		}

		return v;
	}

	public XMLNode lookup(String tags) {
		return lookup(parseTags(tags));
	}

	@SuppressWarnings({ "rawtypes" })
	public XMLNode lookup(Vector tags) {
		XMLNode res = null;

		if (this.node == null) {
			return res;
		}
		if (tags.size() == 0) {
			return this;
		}

		String tag = (String) tags.elementAt(0);
		tags.removeElementAt(0);

		if (this.node instanceof Document) {
			Document doc = (Document) this.node;
			if (doc.hasChildNodes()) {
				NodeList nList = doc.getChildNodes();
				for (int i = 0; i < nList.getLength(); i++) {
					if (tag.equals(nList.item(i).getNodeName())) {
						res = new XMLNode(nList.item(i));
						if (tags.size() <= 0)
							break;
						res = res.lookup(tags);
						break;
					}
				}
			}
		} else if (this.node instanceof Element) {
			Element ele = (Element) this.node;
			NamedNodeMap alist = ele.getAttributes();
			if ((alist != null) && (tags.size() == 0)
					&& (alist.getLength() > 0)) {
				Attr at = ele.getAttributeNode(tag);
				if (at != null) {
					return new XMLNode(at);
				}

			}

			if (ele.hasChildNodes()) {
				NodeList nlist = ele.getChildNodes();
				for (int i = 0; i < nlist.getLength(); i++) {
					if (tag.equals(nlist.item(i).getNodeName())) {
						res = new XMLNode(nlist.item(i));
						if (tags.size() <= 0)
							break;
						res = res.lookup(tags);
						break;
					}
				}
			}

		}

		return res;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public XMLNode[] select(String tags) {
		Vector res = new Vector();
		Vector vtag = parseTags(tags);
		XMLNode cn;
		String target = null;
		int size = vtag.size();
		if (size > 0) {
		    target = (String) vtag.lastElement();
			vtag.removeElementAt(size - 1);
			if ((cn = lookup(vtag)) == null) {
				return new XMLNode[0];
			}
		} else {
			return new XMLNode[0];
		}
		
	
		if ((cn.getNode() instanceof Document)) {
			Document doc = (Document) cn.getNode();

			if (doc.hasChildNodes()) {
				NodeList nlist = doc.getChildNodes();
				for (int i = 0; i < nlist.getLength(); i++) {
					if (target.equals(nlist.item(i).getNodeName()))
						res.add(new XMLNode(nlist.item(i)));
				}
			}
		} else if ((cn.getNode() instanceof Element)) {
			Element ele = (Element) cn.getNode();

			NamedNodeMap alist = ele.getAttributes();
			if ((alist != null) && (alist.getLength() > 0)) {
				Attr at = ele.getAttributeNode(target);
				if (at != null) {
					res.add(new XMLNode(at));
				}

			}

			if ((res.size() == 0) && (ele.hasChildNodes())) {
				NodeList nlist = ele.getChildNodes();
				for (int i = 0; i < nlist.getLength(); i++) {
					if (target.equals(nlist.item(i).getNodeName())) {
						res.add(new XMLNode(nlist.item(i)));
					}
				}
			}

		}
		return (XMLNode[]) res.toArray(new XMLNode[res.size()]);
	}

	public XMLNode addAttr(String attr, String value) {
		XMLNode res = null;
		if (this.node instanceof Element) {
			Element ele = (Element) this.node;
			Attr at = ele.getAttributeNode(attr);
			if (at == null) {
				at = ele.getOwnerDocument().createAttribute(attr);
				at.setValue(value);
				at = ele.setAttributeNode(at);
				res = new XMLNode(at);
			} else {
				at.setValue(value);
				res = new XMLNode(at);
			}

		}
		return res;
	}

	public XMLNode delAttr(String attr) {
		XMLNode res = null;
		if (this.node instanceof Element) {
			Element ele = (Element) this.node;
			Attr at = ele.getAttributeNode(attr);
			if (at != null) {
				ele.removeAttributeNode(at);
				res = new XMLNode(at);
			}

		}
		return res;
	}

	public XMLNode addNode(String name) {
		XMLNode res = null;
		if ((this.node instanceof Element) || (this.node instanceof Document)) {
			Node n = this.node.appendChild(this.node.getOwnerDocument()
					.createElement(name));
			res = new XMLNode(n);
		}
		return res;
	}

	public XMLNode addNode(String name, String target) {
		XMLNode res = null;
		Node cn = null;
		XMLNode xn = null;

		if (((this.node instanceof Document))
				|| ((this.node instanceof Element)))
			if (this.node.hasChildNodes()) {
				Node n = this.node.appendChild(this.node.getOwnerDocument()
						.createElement(name));
				cn = this.node.getFirstChild();
				if ((target != null) && ((xn = lookup(target)) != null)) {
					cn = xn.getNode();
				}
				res = new XMLNode(this.node.insertBefore(n, cn));
			} else {
				res = addNode(name);
			}
		return res;
	}

	public XMLNode delNode(String tag) {
		XMLNode res = null;

		res = lookup(tag);

		if (res == null) {
			return res;
		}

		if ((((this.node instanceof Document)) || ((this.node instanceof Element)))
				&& (this.node.removeChild(res.getNode()) == null))
			res = null;

		return res;
	}

	public XMLNode delNode(XMLNode child) {
		if ((child != null)
				&& (((this.node instanceof Document)) || ((this.node instanceof Element)))
				&& (this.node.removeChild(child.getNode()) == null))
			child = null;

		return child;
	}

	public XMLNode delNode() {
		Node parent = this.node.getParentNode();
		if ((((parent instanceof Document)) || ((parent instanceof Element)))
				&& (parent.removeChild(this.node) == null)) {
			return null;
		}

		return this;
	}


	public String getValue() {
		return lookupValue();
	}

	public String getValue(String def) {
		String cont = lookupValue();
		return cont != null ? cont : def;
	}

	public void setValue(String str) {
		lookupValue();
		if (((this.node instanceof Document))
				|| ((this.node instanceof Element))) {
			if (this.text == null) {
				this.text = this.node.getOwnerDocument().createTextNode(str);
				if (this.node.hasChildNodes())
					this.node
							.insertBefore(this.text, this.node.getFirstChild());
				else {
					this.node.appendChild(this.text);
				}
				this.value = str;
			} else {
				this.text.setNodeValue(str);
			}
		} else if ((this.node instanceof Attr)) {
			Attr atr = (Attr) this.node;
			atr.setValue(str);
			this.value = str;
		}
	}

	public void setValue(String tags, String str) {
		XMLNode cn = lookup(tags);
		if (cn != null)
			cn.setValue(str);
	}

	public void removeValue() {
		lookupValue();
		if ((((this.node instanceof Document)) || ((this.node instanceof Element)))
				&& (this.text != null)) {
			this.node.removeChild(this.text);
			this.text = null;
			this.value = null;
		}
	}

	private String lookupValue() {
		if (this.value != null) {
			return this.value;
		}
		Attr atr;
		if (((this.node instanceof Document))
				|| ((this.node instanceof Element))) {
			if (this.node.hasChildNodes()) {
				NodeList nlist = this.node.getChildNodes();
				for (int i = 0; i < nlist.getLength(); i++) {
					Node cn = nlist.item(i);

					if ((cn instanceof Text)) {
						String nv = cn.getNodeValue().trim();
						if (nv.length() > 0) {
							this.value = nv;
							this.text = ((Text) cn);
							break;
						}
					}
				}
			}
		} else if ((this.node instanceof Attr)) {
			atr = (Attr) this.node;
			this.value = atr.getValue();
			this.text = null;
		}

		return this.value;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public XMLNode[] getNodeList() {
		Vector list = new Vector();
		getChildNodeList(list);
		return (XMLNode[]) list.toArray(new XMLNode[list.size()]);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void getChildNodeList(Vector list) {
		NamedNodeMap alist = this.node.getAttributes();
		if ((alist != null) && (alist.getLength() > 0)) {
			for (int i = 0; i < alist.getLength(); i++) {
				XMLNode cn = new XMLNode(this.node, alist.item(i));
				list.add(cn);
			}

		}

		if (this.node.hasChildNodes()) {
			NodeList nlist = this.node.getChildNodes();
			if ((nlist.getLength() == 1) && ((nlist.item(0) instanceof Text))) {
				list.add(this);
			}
			for (int i = 0; i < nlist.getLength(); i++) {
				XMLNode cn = new XMLNode(nlist.item(i));
				if ((cn.getNode() instanceof Element))
					cn.getChildNodeList(list);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public XMLNode[] getChildNode() {
		Vector result = new Vector();

		NamedNodeMap alist = this.node.getAttributes();
		if ((alist != null) && (alist.getLength() > 0)) {
			for (int i = 0; i < alist.getLength(); i++) {
				XMLNode cn = new XMLNode(this.node, alist.item(i));
				result.add(cn);
			}

		}

		if (this.node.hasChildNodes()) {
			NodeList nlist = this.node.getChildNodes();
			if ((nlist.getLength() == 1) && ((nlist.item(0) instanceof Text))) {
				result.add(this);
			}
			for (int i = 0; i < nlist.getLength(); i++) {
				XMLNode cn = new XMLNode(nlist.item(i));
				if ((cn.getNode() instanceof Element)) {
					result.add(cn);
				}
			}
		}
		return (XMLNode[]) result.toArray(new XMLNode[result.size()]);
	}

	public String getURI() {
		if (this.uri != null) {
			return this.uri;
		}

		if (this.node == null) {
			this.uri = "";
		} else {
			this.uri = this.node.getNodeName();

			Node parent;
			if ((this.node instanceof Attr)) {
				Attr at = (Attr) this.node;
				try {
					parent = at.getOwnerElement();
				} catch (NoSuchMethodError e) {

					parent = this.parent_node;
				}
			} else {
				parent = this.node.getParentNode();
			}
			while (parent != null) {
				this.uri = (parent.getNodeName() + "/" + this.uri);
				parent = parent.getParentNode();
				if (!(parent instanceof Document))
					continue;
			}
		}
		return this.uri;
	}

	public String getURI(String root) {
		String node_uri = "";

		if (this.node != null) {
			node_uri = this.node.getNodeName();

			Node parent;
			if ((this.node instanceof Attr)) {
				Attr at = (Attr) this.node;
				try {
					parent = at.getOwnerElement();
				} catch (NoSuchMethodError e) {

					parent = this.parent_node;
				}
			} else {
				parent = this.node.getParentNode();
			}
			while (parent != null) {
				node_uri = parent.getNodeName() + "/" + node_uri;
				if (parent.getNodeName().equals(root)) {
					break;
				}
				parent = parent.getParentNode();
				if ((parent instanceof Document)) {
					break;
				}
			}
		}

		return node_uri;
	}

	public String getString(String aName) {
		return getString(aName, null);
	}

	public String getString(String sName, String def) {
		XMLNode cn = lookup(sName);
		if (cn != null)
			return cn.getValue(def);
		return def;
	}

	public float getFloat(String sName, float nInit) {
		try {
			return Float.parseFloat(getString(sName));
		} catch (Exception e) {
		}
		return nInit;
	}

	public float getFloat(String sName) {
		try {
			return Float.parseFloat(getString(sName));
		} catch (Exception e) {
		}
		return 0.0F;
	}

	public double getDouble(String sName, double nInit) {
		try {
			return Double.parseDouble(getString(sName));
		} catch (Exception e) {
		}
		return nInit;
	}

	public double getDouble(String sName) {
		try {
			return Double.parseDouble(getString(sName));
		} catch (Exception e) {
		}
		return 0.0D;
	}

	public int getInteger(String sName, int nInit) {
		try {
			return Integer.parseInt(getString(sName));
		} catch (Exception e) {
		}
		return nInit;
	}

	public int getInteger(String sName) {
		try {
			return Integer.parseInt(getString(sName));
		} catch (Exception e) {
		}
		return 0;
	}

	public long getLong(String sName, long nInit) {
		try {
			return Long.parseLong(getString(sName));
		} catch (Exception e) {
		}
		return nInit;
	}

	public long getLong(String sName) {
		try {
			return Long.parseLong(getString(sName));
		} catch (Exception e) {
		}
		return 0L;
	}

	public boolean isValid(String sName) {
		return lookup(sName) != null;
	}

	public boolean isTrue(String sName) {
		String val = getString(sName);
		return (val != null)
				&& ((val.equals("ON")) || (val.toUpperCase().equals("TRUE")));
	}

	public boolean isFalse(String sName) {
		String val = getString(sName);
		return (val != null)
				&& ((val.equals("OFF")) || (val.toUpperCase().equals("FALSE")));
	}

}
