/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * NavigationAdapter.java
 * 
 * 2013年9月4日-下午3:01:08
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.adapter;

import java.util.ArrayList;
import java.util.List;

import com.powerlong.electric.app.entity.BrandEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 
 * NavigationAdapter
 * 
 * @author: YangCheng Miao
 * 2013年9月4日-下午3:01:08
 * 
 * @version 1.0.0
 * 
 */
public class NavigationAdapter extends BaseAdapter {
	private List<String> list;
	private List<String> groupkey=new ArrayList<String>();  
	private Context mContext;
	public NavigationAdapter(Context context, List<String> list) {
		this.mContext = context;
		this.list = list;
	}
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		
		return list.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return list.get(position);
		
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	 @Override  
     public boolean isEnabled(int position) {  
          if(groupkey.contains(getItem(position))){  
              return false;  
          }  
          return super.isEnabled(position);  
     }  

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;

	}
}
