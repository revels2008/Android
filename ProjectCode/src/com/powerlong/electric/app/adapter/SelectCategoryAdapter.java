/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * SelectCategoryAdapter.java
 * 
 * 2013年7月29日-下午2:54:08
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.adapter;

import java.util.ArrayList;
import java.util.Vector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.entity.CategoryEntity;

/**
 * 
 * SelectCategoryAdapter
 * 
 * @author: YangCheng Miao
 * 2013年7月29日-下午2:54:08
 * 
 * @version 1.0.0
 * 
 */
public class SelectCategoryAdapter extends BaseAdapter{
	
	/**
	 * 创建一个新的实例 SelectCategoryAdapter.
	 *
	 * @param context
	 * @param textViewResourceId
	 */
	
	private ArrayList<CategoryEntity> list;
	private Context mContext;
	private LayoutInflater mInflater;
	private Vector<CategoryEntity> items;
	
	public SelectCategoryAdapter(Context context)
	{
		this.mContext = context;
		mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		items = new Vector<CategoryEntity>();
	}
	
	public CategoryEntity getMessageByIndex(int index) {
		return items.elementAt(index);
	}

	public void addItem(CategoryEntity item) {
		items.add(item);
		this.notifyDataSetChanged();
	}
	
	/**
	 * 将Item内的对象复制到指定的Vector中
	 * @param messages
	 */
	public void copyItems(Vector<CategoryEntity> messages){
		if(messages==null){
			messages = new Vector<CategoryEntity>();
		}
		if(items != null && items.size() > 0){
			for(int i = 0;i < items.size();i++){
				messages.add(items.get(i));
			}
		}
	}

	public void removeAll() {
		items.clear();
		this.notifyDataSetChanged();
	}
	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */

	@Override
	public int getCount() {
		return items.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int arg0) {
		return items.get(arg0);
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
			
			holder=new ViewHolder();  
			convertView = mInflater.inflate(R.layout.select_category, null);
			holder.partLayout = (LinearLayout)convertView.findViewById(R.id.classGroupLayout);
			holder.icon = (ImageView)convertView.findViewById(R.id.category_item_img);
			holder.name = (TextView)convertView.findViewById(R.id.category_item_name);
			holder.img = (ImageView)convertView.findViewById(R.id.category_item_img2);
			
			convertView.setMinimumHeight(100);
			convertView.setTag(holder);
			
		}else {
			
			holder = (ViewHolder)convertView.getTag();
		}
		
		CategoryEntity entity = items.get(position);
		if (entity != null) {
			if (entity.ifTop){
				holder.partLayout.setVisibility(View.VISIBLE);
				holder.icon.setBackgroundResource(items.get(position).getIcon());
				holder.name.setText(items.get(position).getName());
			}else {
				holder.partLayout.setVisibility(View.GONE);
				holder.icon.setBackgroundResource(items.get(position).getIcon());
				holder.name.setText(items.get(position).getName());
			}
			
			holder.img.setBackgroundResource(R.drawable.arrow_normal);
		}
				
		return convertView;
	}
	
	public final class ViewHolder{
		LinearLayout partLayout;
		public ImageView icon;
		public TextView name;
		public ImageView img;
	}
}
