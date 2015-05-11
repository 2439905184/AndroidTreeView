package com.sloop.treeview.bean;

import android.R.integer;

import com.sloop.treeview.utils.annotation.TreeNodeId;
import com.sloop.treeview.utils.annotation.TreeNodeLabel;
import com.sloop.treeview.utils.annotation.TreeNodePid;


/**
 * @ClassName: OrgBean
 * @Description: 
 * @author sloop
 * @date 2015��2��21�� ����2:45:13
 *
 */
public class OrgBean {

	/**
	 * ��ǰid
	 */
	@TreeNodeId
	private int id;
	/**
	 * ���ڵ�id
	 */
	@TreeNodePid
	private int pId;
	/**
	 * �������
	 */
	@TreeNodeLabel
	private String lable;
	public OrgBean(int id, int pId, String lable) {
		this.id = id;
		this.pId = pId;
		this.lable = lable;
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
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	
	
	
}
