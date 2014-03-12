/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * NavigationTextAdapter.java
 * 
 * 2013年9月4日-下午7:55:12
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.adapter;

import java.util.List;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.entity.NavigationBaseEntity;
import com.powerlong.electric.app.utils.LogUtil;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * NavigationTextAdapter
 * 
 * @author: YangCheng Miao
 * 2013年9月4日-下午7:55:12
 * 
 * @version 1.0.0
 * 
 */
public class NavigationTextAdapter extends BaseAdapter {
	private List<NavigationBaseEntity> mList;
	private Context mContext;
	private LayoutInflater mInflater; 
	public NavigationTextAdapter(Context context, List<NavigationBaseEntity> list){
		this.mContext = context;
		this.mList = list;
		mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() { 
		return mList.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return mList.get(position);
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
		if (convertView == null) {
			
			convertView = mInflater.inflate(R.layout.navigation_floor_item, null);
			holder=new ViewHolder();  
			holder.floor = (TextView) convertView.findViewById(R.id.tv_navigation_floor_name);
			holder.name = (TextView) convertView.findViewById(R.id.tv_navigation_floor);
			holder.category = (TextView) convertView.findViewById(R.id.tv_navigation_floor_category);
			holder.category.setVisibility(View.GONE);
			convertView.setMinimumHeight(134);
			convertView.setTag(holder);
			
		}else {
			
			holder = (ViewHolder)convertView.getTag();
		}
			String str = mList.get(position).getName();
			Log.e("<<<<<<<<<<<<<<<,floor", str);
			holder.floor.setText(str.substring(0, 2));
			holder.name.setText(str.substring(2, str.length()));
			holder.category.setText(mList.get(position).getName());
			convertView.setMinimumHeight(134);
			
			
		return convertView;
	}
	public final class ViewHolder{
		public TextView floor;
		public TextView name;
		public TextView category;
	}

}
