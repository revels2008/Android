/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * NavigationShopAdapter.java
 * 
 * 2013年9月5日-上午10:53:54
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.adapter;

import java.util.List;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.NavigationNumAdapter.ViewHolder;
import com.powerlong.electric.app.entity.NavigationBaseEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * NavigationShopAdapter
 * 
 * @author: YangCheng Miao
 * 2013年9月5日-上午10:53:54
 * 
 * @version 1.0.0
 * 
 */
public class NavigationImgAdapter extends BaseAdapter {
	private List<NavigationBaseEntity> mList;
	private Context mContext;
	private LayoutInflater mInflater; 
	public NavigationImgAdapter(Context context, List<NavigationBaseEntity> list){
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
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			
			convertView = mInflater.inflate(R.layout.navigation_shop_item, null);
			holder=new ViewHolder();  
			holder.name = (TextView) convertView.findViewById(R.id.tv_navigation_shop);
			holder.iv = (ImageView) convertView.findViewById(R.id.iv_navigation_shop);
			convertView.setMinimumHeight(134);
			convertView.setTag(holder);
			
		}else {
			
			holder = (ViewHolder)convertView.getTag();
		}
			holder.name.setText(mList.get(position).getName());
			convertView.setMinimumHeight(134);
				
		return convertView;
	}
	public final class ViewHolder{
		public TextView name;
		public ImageView iv;
	}
}
