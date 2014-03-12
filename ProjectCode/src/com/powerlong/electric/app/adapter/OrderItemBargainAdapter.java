/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * ShoppingCartSettleAdapter.java
 * 
 * 2013年8月29日-下午7:57:45
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.adapter;

import java.util.List;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.AbstractSpinerAdapter.ViewHolder;
import com.powerlong.electric.app.cache.FloorDetailViewCache;
import com.powerlong.electric.app.cache.ShoppingCartSettleCache;
import com.powerlong.electric.app.entity.FloorDetailEntity;
import com.powerlong.electric.app.entity.ItemBargainListEntity;
import com.powerlong.electric.app.entity.ShoppingCartSettleEntity;
import com.powerlong.electric.app.utils.AsyncImageLoader;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.utils.AsyncImageLoader.ImageCallback;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * ShoppingCartSettleAdapter
 * 
 * @author: YangCheng Miao
 * 2013年8月29日-下午7:57:45
 * 
 * @version 1.0.0
 * 
 */
public class OrderItemBargainAdapter extends ArrayAdapter<ItemBargainListEntity> {
	private ListView listView;
	private LayoutInflater mInflater;
	private List<ItemBargainListEntity> mList;
	
	/**
	 * 创建一个新的实例 ShoppingCartSettleAdapter.
	 *
	 * @param context
	 * @param textViewResourceId
	 */
	public OrderItemBargainAdapter(Context mContext, List<ItemBargainListEntity> entities, ListView mListView) {
		super(mContext, 0, entities);
		this.listView = mListView;
		mList = entities;
		mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
//    	 Log.e("<<<<<<<<<<<>>>>>>>>>>>>", "getCount = "+mList.size());
		return mList.size();
	}

    

	@Override
	public ItemBargainListEntity getItem(int position) {
		return mList.get(position);
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
   	     if (convertView == null) {
   	    	 convertView = mInflater.inflate(R.layout.order_item_bargain_item, null);
   	         viewHolder = new ViewHolder();
   	         viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.im_commodity_giftimg);
   	         viewHolder.mTextView = (TextView) convertView.findViewById(R.id.tv_order_item_bargain);
   	         convertView.setTag(viewHolder);
   	     } else {
   	         viewHolder = (ViewHolder) convertView.getTag();
   	     }

   	     ItemBargainListEntity entity = mList.get(position);
   	     String item = entity.getName();  	     
   		 viewHolder.mTextView.setText(item);
   		 String type = entity.getType();
   		 if(type.equals(1)) {
   			 viewHolder.mImageView.setVisibility(View.VISIBLE);
   		 } else {
   			viewHolder.mImageView.setVisibility(View.GONE);
   		 }
   	     return convertView;
   	}

   	public static class ViewHolder
   	{
   		public ImageView mImageView;
   	    public TextView mTextView;
       
   	}
	public void clear(){
		this.notifyDataSetChanged();
	}
}
