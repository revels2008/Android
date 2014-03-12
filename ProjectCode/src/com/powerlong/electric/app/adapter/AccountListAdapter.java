/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * AccountListAdapter.java
 * 
 * 2013-9-4-上午09:33:36
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.adapter;

import java.util.ArrayList;
import java.util.List;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * AccountListAdapter
 * 
 * @author: Liang Wang 2013-9-4-上午09:33:36
 * 
 * @version 1.0.0
 * 
 */
public class AccountListAdapter extends BaseAdapter {
	private Context mContext = null;
	private List<String> mItemTitles = new ArrayList<String>();
	private LayoutInflater mLayoutInflater = null;

	private int[] iconIds = new int[] { R.drawable.my_order,
			R.drawable.my_ticket, R.drawable.my_coupon, R.drawable.my_favorite,
			R.drawable.my_setting };
	private  int[] iconIds_no_groupon = new int[] { R.drawable.my_order,
			R.drawable.my_ticket, R.drawable.my_coupon, R.drawable.my_favorite,
			R.drawable.my_setting };

	/**
	 * 
	 * 
	 * ViewHolder 列表item容器
	 * 
	 * @author: Liang Wang 2013-9-4-上午09:40:34
	 * 
	 * @version 1.0.0
	 * 
	 */
	private class ViewHolder {
		ImageView ivItem;
		TextView tvItem;
	}

	/**
	 * 初始化创建AccountListAdapter实例
	 * 
	 * @author wangliang
	 * @param context
	 *            上下文实例
	 * @param itemList
	 *            列表标题容器
	 */
	public AccountListAdapter(Context context, List<String> itemList) {
		mContext = context;
		mItemTitles.addAll(itemList);
		mLayoutInflater = LayoutInflater.from(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return mItemTitles != null ? mItemTitles.size() : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return mItemTitles != null ? mItemTitles.get(position) : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater
					.inflate(R.layout.user_info_item, null);
			holder.ivItem = (ImageView) convertView
					.findViewById(R.id.ivItemIcon);
			holder.tvItem = (TextView) convertView
					.findViewById(R.id.tvItemTitle);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvItem.setText(mItemTitles.get(position));
		//if(position>=1)
		if(Constants.isNeedGroupon)
			holder.ivItem.setBackgroundResource(iconIds[position]);
		else{
			holder.ivItem.setBackgroundResource(iconIds_no_groupon[position]);
		}
		return convertView;
	}
}
