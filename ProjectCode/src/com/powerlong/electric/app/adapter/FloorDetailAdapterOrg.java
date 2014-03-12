/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * FloorDetailAdapter.java
 * 
 * 2013年8月19日-上午10:26:22
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.entity.FloorDetailEntity;

/**
 * 
 * FloorDetailAdapter
 * 
 * @author: YangCheng Miao
 * 2013年8月19日-上午10:26:22
 * 
 * @version 1.0.0
 * 
 */
public class FloorDetailAdapterOrg extends BaseAdapter{

	private List<FloorDetailEntity> list;
	private Context mContext;
	private LayoutInflater mInflater; 
	
	
	
	/**
	 * 创建一个新的实例 FloorDetailAdapter.
	 *
	 * @param list
	 * @param mContext
	 * @param mInflater
	 */
	public FloorDetailAdapterOrg(List<FloorDetailEntity> list, Context mContext) {
		super();
		this.list = list;
		this.mContext = mContext;
		mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.shop_list_item,
					null);
			holder.imageId = (ImageView) convertView
					.findViewById(R.id.floor_shopimage);
			holder.name = (TextView) convertView
					.findViewById(R.id.floor_title_shop);
			holder.description = (TextView) convertView
					.findViewById(R.id.floor_shop_details);
			holder.grade = (RatingBar) convertView
					.findViewById(R.id.floor_evaluation);
			holder.evaluate = (TextView) convertView.findViewById(R.id.tv_shop_list_evaluate);
			holder.category = (TextView) convertView
					.findViewById(R.id.floor_products_categories);
			holder.num = (TextView) convertView
					.findViewById(R.id.floor_favour_num);

			convertView.setMinimumHeight(100);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
//		holder.imageId.setBackgroundResource(list.get(position).getImageId());
		holder.name.setText(list.get(position).getName());
		holder.description.setText(list.get(position).getDescription());
		holder.grade.setRating(list.get(position).getGrade());
		holder.category.setText(list.get(position).getCategory());
		holder.evaluate.setText(list.get(position).getGrade()+"");
		holder.num.setText(list.get(position).getNum());
		return convertView;
	}

	static class ViewHolder {
		ImageView imageId;
		TextView name;
		TextView description;
		RatingBar grade;
		TextView evaluate;
		TextView category;
		TextView num;
	}
}
