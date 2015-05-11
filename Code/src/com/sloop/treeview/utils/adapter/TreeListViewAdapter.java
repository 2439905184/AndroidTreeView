/**
 * @Title: TreeListViewAdapter.java
 * @Package com.sloop.treeview.utils.adapter
 * Copyright: Copyright (c) 2015
 * 
 * @author sloop
 * @date 2015��2��22�� ����1:16:25
 * @version V1.0
 */

package com.sloop.treeview.utils.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.sloop.treeview.utils.Node;
import com.sloop.treeview.utils.TreeHelper;

/**
 * @ClassName: TreeListViewAdapter
 * @author sloop
 * @date 2015��2��22�� ����1:16:25
 */

public abstract class TreeListViewAdapter<T> extends BaseAdapter {

	protected Context mContext;				//������
	protected List<Node> mAllNodes;			//���нڵ�
	protected List<Node> mVisibleNodes;		//��ʾ�Ľڵ�
	protected LayoutInflater mInflater;		//ҳ�������
	protected ListView mTree;					//չʾ�õ�ListView
	
	/**
	 * �����û�node�ĵ���ص�
	 * ���û��ṩ����Ŀ�����Ӧ
	 * �����ֲ�OnItemClickListener��ռ�õĲ���
	 * @ClassName: OnTreeNodeClickListener
	 * @author sloop
	 * @date 2015��2��22�� ����1:44:19
	 */
	public interface OnTreeNodeClickListener{
		void onClick(Node node, int position);
	}
	
	protected OnTreeNodeClickListener mListener;
	
	public void setOnTreeNodeClickLitener(OnTreeNodeClickListener mListener) {
		this.mListener = mListener;
	}
	
	/**
	 * ����һ���µ�ʵ�� TreeListViewAdapter. 
	 * @param context			������
	 * @param mTree				չʾ�õ�ListView
	 * @param datas				���ݼ�
	 * @param defaultLevel		Ĭ��չ���㼶
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public TreeListViewAdapter(Context context,ListView tree, List<T> datas, int defaultExpandLevel) throws IllegalAccessException, IllegalArgumentException {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mAllNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
		mVisibleNodes = TreeHelper.fliterVisibleNodes(mAllNodes);
		for (Node node : mVisibleNodes) {
			Log.e("TAG", "��ʾ--"+node.getName());
		}
		mTree = tree;
		
		mTree.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				expandOrCollapse(position);
				
				if (mListener != null) {
					mListener.onClick(mVisibleNodes.get(position), position);
				}
			}
		});
	}
	
	/**
	 * �����������չ��
	 * @Title: expandOrCollapse
	 * @param position 
	 */
	protected void expandOrCollapse(int position) {
		Node node = mVisibleNodes.get(position);
		if (node!=null) {
			if (node.isLeaf()) {
				return;
			}
			node.setExpend(!node.isExpend());
			mVisibleNodes = TreeHelper.fliterVisibleNodes(mAllNodes);
			notifyDataSetChanged();	//ˢ��
		}
	}
	

	/**
	 * @Override
	 * Title: getCount
	 * @return 
	 */
	@Override
	public int getCount() {
		return mVisibleNodes.size();
	}

	/**
	 * @Override
	 * Title: getItem
	 * @param position
	 * @return 
	 */
	@Override
	public Object getItem(int position) {
		return mVisibleNodes.get(position);
	}

	/**
	 * @Override
	 * Title: getItemId
	 * @param position
	 * @return 
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * @Override
	 * Title: getView
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return 
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Node node = mVisibleNodes.get(position);
		convertView = getConvertView(node, position, convertView, parent);
		convertView.setPadding(node.getLevel()*30, 3, 3, 3);	//����padding�ڱ߾�
		return convertView;
	}

	/**
	 * �ṩ���û����Զ�����Ŀ�ķ�ʽ
	 * @Title: getConvertView
	 * @param node
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return View
	 */
	public abstract View getConvertView(Node node, int position, View convertView, ViewGroup parent);
}
