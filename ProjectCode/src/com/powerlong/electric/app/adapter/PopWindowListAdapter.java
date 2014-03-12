/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * PopWindowListAdapter.java
 * 
 * 2013-8-3-下午03:31:18
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.utils.LogUtil;

/**
 * 
 * PopWindowListAdapter
 * 
 * @author: Liang Wang
 * 2013-8-3-下午03:31:18
 * 
 * @version 1.0.0
 * 
 */
public class PopWindowListAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater = null;
	private Context mContext = null;
	private List<String> mDataList = null;
	
	private class ViewHolder
	{
		TextView title;
	}
	
	public PopWindowListAdapter(Context context,List<String> data_list){
		mContext = context;
		mDataList = new ArrayList<String>();
		for(String data:data_list){
			mDataList.add(data);
		}
		mLayoutInflater = LayoutInflater.from(mContext);
	}
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return mDataList!=null?mDataList.size():0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return mDataList.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView==null){
			convertView = mLayoutInflater.inflate(R.layout.pop_window_list_item, null);
			holder = new ViewHolder();
			holder.title = (TextView)convertView.findViewById(R.id.title);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		LogUtil.d("HomeFragment", "holder.title ="+holder.title);
		LogUtil.d("HomeFragment", "position ="+position);
		LogUtil.d("HomeFragment", "data ="+mDataList.get(position));
		
		holder.title.setText(mDataList.get(position));
		return convertView;
	}

}