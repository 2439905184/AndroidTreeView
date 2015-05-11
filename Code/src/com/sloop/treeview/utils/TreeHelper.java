/**
 * @Title: TreeHelper.java
 * @Package com.sloop.treeview.utils
 * @Description: TODO
 * Copyright: Copyright (c) 2015
 * 
 * @author sloop
 * @date 2015��2��21�� ����3:19:27
 * @version V1.0
 */

package com.sloop.treeview.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.sloop.treeview.R;
import com.sloop.treeview.utils.annotation.TreeNodeId;
import com.sloop.treeview.utils.annotation.TreeNodeLabel;
import com.sloop.treeview.utils.annotation.TreeNodePid;

/**
 * ���νṹ�İ����� ��Ԫ����ת��Ϊ�ڵ�
 * @ClassName: TreeHelper
 * @Description: 
 * @author sloop
 * @date 2015��2��21�� ����3:19:27
 *
 */

public class TreeHelper {

	/**
	 * ���û�����ת��Ϊ��������
	 * @Title: convertDatas2Nodes
	 * @param datas
	 * @return List<Node>	Node���ݼ�
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static <T> List<Node> convertDatas2Nodes(List<T> datas) throws IllegalAccessException, IllegalArgumentException {
		
		List<Node> nodes = new ArrayList<Node>();
		Node node = null;
		for (T t : datas) {		//ѭ��������������
			int id = -1;
			int pId = -1;
			String label = null;
			
			Class clazz = t.getClass();	//ʹ�÷���+ע���ȡ���Ժͷ���
			
			//���������ֶ�(����)������ע���жϷ���
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if(field.getAnnotation(TreeNodeId.class) != null){
					//��չ-ͨ���û�ָ��������ǿ��ת����������
			/*		TreeNodeId annotation = field.getAnnotation(TreeNodeId.class);
					Class type = annotation.type();
					if (type==String.class) {
						//ǿתΪString
					}else if (type == Integer.class) {
						//ǿתΪint
					}*/
					
					field.setAccessible(true);	//���÷���Ȩ�� ǿ�Ƹ���Ϊ���Է���
					id = field.getInt(t);
				}
				if(field.getAnnotation(TreeNodePid.class) != null){
					field.setAccessible(true);	
					pId = field.getInt(t);
				}
				if(field.getAnnotation(TreeNodeLabel.class) != null){
					field.setAccessible(true);	
					label = (String) field.get(t);
				}
			}
			node = new Node(id, pId, label);
			nodes.add(node);
		}
		//Ϊnode���ù�����ϵ
	/*	for (Node n : nodes){
			for (Node m : nodes) {
				if (m.getpId() == n.getId()) {			//m��n���ӽڵ�
					n.getChildren().add(m);
					m.setParent(n);
				}else if (m.getId() == n.getpId()) {	//m��n�ĸ��ڵ�
					m.getChildren().add(n);
					n.setParent(m);
				}
			}
		}*/
		for (int i = 0; i < nodes.size(); i++) {
			Node n = nodes.get(i);
			for (int j = i+1; j < nodes.size(); j++) {
				Node m = nodes.get(j);
				if (m.getpId() == n.getId()) {			//m��n���ӽڵ�
					n.getChildren().add(m);
					m.setParent(n);
				}else if (m.getId() == n.getpId()) {	//m��n�ĸ��ڵ�
					m.getChildren().add(n);
					n.setParent(m);
				}
			}
		}
		
		for (Node n : nodes) {
			setNodeIcon(n);
		}
		
		return nodes;
	}

	/**
	 * ��ȡ�����Ľڵ�����
	 * @Title: getSortedNodes
	 * @param datas
	 * @return List<Node>
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static <T> List<Node> getSortedNodes(List<T> datas, int defaultExpandLevel) throws IllegalAccessException, IllegalArgumentException {
		
		List<Node> result = new ArrayList<Node>();		//������ɵĽڵ�
		List<Node> nodes = convertDatas2Nodes(datas);	//ת��������нڵ�
		List<Node> rootNodes = getRootNodes(nodes);
		Log.e("TAG", "ת��������нڵ����"+nodes.size());
		Log.e("TAG", "���ڵ����"+rootNodes.size());
		for (Node node : rootNodes) {
	//		Log.e("TAG", "���ڵ�--"+node.getName());
			addNode(result, node, defaultExpandLevel, 1);
		}
		Log.e("TAG", "������ɵĽڵ����"+result.size());
		for (Node node : result) {
			
		//	Log.e("TAG", "����----"+node.getName());
		}
		return result;
	}
	
	/**
	 * ��һ���ڵ�����к��ӽڵ㶼����result(�ݹ�)
	 * @Title: addNode
	 * @param result					��ӽ��ĸ����ڵ�
	 * @param node						��Ҫ��ӽ�ȥ��node
	 * @param defaultExpandLevel		Ĭ��չ���㼶
	 * @param currentLevel				��ǰ�㼶
	 */
	private static void addNode(List<Node> result, Node node, int defaultExpandLevel, int currentLevel) {
		
		result.add(node);
		if (node.isLeaf()){							//�����Ҷ�ӽڵ�˵���÷�֧��ӽ��� ����
			return;
		}
		if (defaultExpandLevel >= currentLevel) {	//��ǰ�㼶С��Ĭ��չ���㼶��չ����ǰ
			node.setExpend(true);
		}

		for (int i = 0; i < node.getChildren().size(); i++) {
			addNode(result, node.getChildren().get(i), defaultExpandLevel, currentLevel+1);
		}
	}
	
	/**
	 * ���˳���Ҫ��ʾ��node����
	 * @Title: fliterVisibleNodes
	 * @return List<Node>
	 */
	public static List<Node> fliterVisibleNodes(List<Node> nodes) {
		
		List<Node> result = new ArrayList<Node>();
		
		for (Node node : nodes) {
			if (node.isRoot() || node.isParentExpend()) {	//�����ǰ�ڵ��Ǹ��ڵ�������ĸ��ڵ㴦��չ��״̬����ʾ
				setNodeIcon(node);	//ˢ��ͼ��
				result.add(node);
			}
		}
		for (Node node : result) {
	//		Log.e("TAG", "��ʾ--"+node.getName());
		}
		return result;
	}

	/**
	 * �����нڵ��л�ȡ���ڵ㼯��
	 * @Title: getRootNodes
	 * @param nodes
	 * @return List<Node> 
	 */
	private static List<Node> getRootNodes(List<Node> nodes) {
		
		List<Node> root = new ArrayList<Node>();
		
		for (Node node : nodes) {
			if (node.isRoot()) {
				root.add(node);
			}
		}
		
		return root;
	}

	/**
	 * ��node����ͼƬ
	 * @Title: setNodeIcon
	 * @param n void 
	 */
	private static void setNodeIcon(Node n) {
		if (n.getChildren().size()>0 && n.isExpend()) {			//���ӽڵ㲢����չ����
			n.setIcon(R.drawable.item_open);
		}else if (n.getChildren().size()>0 && !n.isExpend()) {	//���ӽڵ㵫��δչ��
			n.setIcon(R.drawable.item_close);
		}
	}
}
