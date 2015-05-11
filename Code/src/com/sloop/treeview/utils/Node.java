/**
 * @Title: Node.java
 * @Package com.sloop.treeview.utils
 * @Description:
 * Copyright: Copyright (c) 2015
 * 
 * @author sloop
 * @date 2015��2��21�� ����3:40:42
 * @version V1.0
 */

package com.sloop.treeview.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * ���νṹ�Ľڵ�
 * 
 * @ClassName: Node
 * @author sloop
 * @date 2015��2��21�� ����3:42:26
 */

public class Node {

	/**
	 * ��ǰid
	 */
	private int id;
	/**
	 * ���ڵ�id ���ڵ��pid=0
	 */
	private int pId;
	/**
	 * ��ʾ����
	 */
	private String name;
	/**
	 * ���Ĳ㼶
	 */
	private int level;
	/**
	 * �Ƿ�չ��
	 */
	private boolean isExpend = false;
	/**
	 * ͼ��
	 */
	private int icon;
	/**
	 * ���ڵ�
	 */
	private Node parent;
	/**
	 * �ӽڵ�
	 */
	private List<Node> children = new ArrayList<Node>();

	/**
	 * 
	 * ����һ���µ�ʵ�� Node.
	 * 
	 * @param id ��ǰ�ڵ�id
	 * @param pId ���ڵ�id
	 * @param name ��ʾ����
	 */
	public Node(int id, int pId, String name) {
		this.id = id;
		this.pId = pId;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * �õ���ǰ�ڵ�Ĳ㼶
	 * 
	 * @Title: getLevel
	 * @return int
	 */
	public int getLevel() {
		return parent == null ? 0 : parent.getLevel() + 1;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isExpend() {
		return isExpend;
	}

	/**
	 * �ı�չ��״̬
	 * 
	 * @Title: setExpend
	 * @param isExpend void
	 */
	public void setExpend(boolean isExpend) {
		this.isExpend = isExpend;
		if (!isExpend) { // �����ӽڵ�
			for (Node node : children) {
				node.setExpend(false);
			}
		}
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	/**
	 * �ж��Ƿ��Ǹ��ڵ�
	 * 
	 * @Title: isRoot
	 * @return boolean
	 */
	public boolean isRoot() {
		return parent == null;
	}

	/**
	 * �жϸ��ڵ��Ƿ���չ��״̬
	 * 
	 * @Title: isParentExpend
	 * @return boolean
	 */
	public boolean isParentExpend() {
		if (parent == null)
			return false;
		return parent.isExpend();
	}

	/**
	 * �ж��Ƿ���Ҷ�ӽڵ�
	 * 
	 * @Title: isLeaf
	 * @return boolean
	 */
	public boolean isLeaf() {
		return children.size() == 0;
	}
}
