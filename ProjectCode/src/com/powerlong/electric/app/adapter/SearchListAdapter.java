/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * SearchListAdapter.java
 * 
 * 2013-8-5-下午04:04:53
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.base.AdapterBase;
import com.powerlong.electric.app.cache.FileCache;
import com.powerlong.electric.app.cache.LCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.config.Constants.ListType;
import com.powerlong.electric.app.entity.SearchCategoryDetailEntity;
import com.powerlong.electric.app.entity.SearchCategoryEntity;
import com.powerlong.electric.app.entity.SearchEntity;
import com.powerlong.electric.app.entity.SearchHistoryEntity;
import com.powerlong.electric.app.entity.SearchResultEntity;
import com.powerlong.electric.app.utils.LogUtil;

/**
 * 
 * SearchListAdapter
 * 
 * @author: Liang Wang 2013-8-5-下午04:04:53
 * 
 * @version 1.0.0
 * @param <T>
 * @param <T>
 * 
 */
public class SearchListAdapter<T> extends AdapterBase<T> {
	private LayoutInflater mLayoutInflater = null;
	private RequestQueue mQueue;
    private ImageLoader mImageLoader;
    private LCache mBmpCache = null;
    
	private class ViewHolder {
		ImageView itemIcon;
		TextView itemTitle;
		TextView itemSummary;
		ImageView itemIndicator;
	}

	public SearchListAdapter(Context context) {
		mLayoutInflater = LayoutInflater.from(context);
		mQueue = Volley.newRequestQueue(context);
		mBmpCache =  Constants.mBmpCache;
		LogUtil.e("BannerAdapter", "init...");
		mImageLoader = new ImageLoader(new FileCache(context,"search"),mQueue, mBmpCache,0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.powerlong.electric.app.adapter.base.AdapterBase#getExView(int,
	 * android.view.View, android.view.ViewGroup)
	 */
	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(
					R.layout.search_fragment_list_item, null);
			holder = new ViewHolder();
			holder.itemIcon = (ImageView) convertView
					.findViewById(R.id.listIcon);
			holder.itemTitle = (TextView) convertView
					.findViewById(R.id.listTitle);
			holder.itemSummary = (TextView) convertView
					.findViewById(R.id.listSummary);
			holder.itemIndicator = (ImageView) convertView
					.findViewById(R.id.listArrow);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		//LogUtil.d(SearchFragment.class.getName(), "getExView.. + listType = "
		//		+ getListType());
		holder.itemIndicator.setVisibility(View.GONE);

		if (getList().get(position) instanceof SearchCategoryEntity) {
			if (getListType() != ListType.NORMAL)
				return convertView;

			SearchCategoryEntity entity = (SearchCategoryEntity) getList().get(position);
			if (entity != null) {
				holder.itemTitle.setText(entity.getName());
				holder.itemTitle.setVisibility(View.VISIBLE);
				holder.itemSummary.setText(entity.getDescription());
				holder.itemSummary.setVisibility(View.VISIBLE);
				//holder.itemIcon.setImageResource(entity.getLogo());
				ImageListener listener = ImageLoader.getImageListener(holder.itemIcon, R.drawable.pic_96, R.drawable.pic_96);
			    mImageLoader.get(entity.getLogo(), listener);
				holder.itemIcon.setVisibility(View.VISIBLE);
				holder.itemSummary.setVisibility(View.VISIBLE);
			}
		} else if (getList().get(position) instanceof SearchHistoryEntity) {
			if (getListType() != ListType.SEARCHING)
				return convertView;

			SearchHistoryEntity entity = (SearchHistoryEntity) getList().get(
					position);
			if (entity != null) {
				holder.itemIcon.setVisibility(View.GONE);
				holder.itemTitle.setText(entity.getContent());
				holder.itemTitle.setVisibility(View.VISIBLE);
				holder.itemSummary.setVisibility(View.GONE);
			}
		} else if (getList().get(position) instanceof SearchResultEntity) {
			if (getListType() != ListType.MATCHED)
				return convertView;

			SearchResultEntity entity = (SearchResultEntity) getList().get(
					position);
			if (entity != null) {
				holder.itemTitle.setVisibility(View.VISIBLE);
				holder.itemIcon.setVisibility(View.GONE);
				holder.itemSummary.setVisibility(View.GONE);
				String result = entity.getResultText();
				if(position ==0)
					result = "搜索\""+result+"\" 商品";
				if(position ==1 )
					result = "搜索\""+result+"\" 店铺";
				if (position <= 1) {
					int len = result.length();
					SpannableString ss = new SpannableString(
							result);
					// 设置最后2个字符颜色
					ss.setSpan(new ForegroundColorSpan(Color.GREEN), len-2, len,
							Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					holder.itemTitle.setText(ss);
					holder.itemIndicator.setVisibility(View.VISIBLE);
				}else{
					holder.itemTitle.setText(entity.getResultText());
				}
			}
		}else if(getList().get(position) instanceof SearchCategoryDetailEntity){
			
			SearchCategoryDetailEntity entity = (SearchCategoryDetailEntity) getList().get(
					position);
			
			if(getListType() == ListType.SUMMARY){
				holder.itemIcon.setVisibility(View.GONE);
				holder.itemTitle.setVisibility(View.GONE);
				holder.itemSummary.setVisibility(View.VISIBLE);
				holder.itemSummary.setText(entity.getName());
				holder.itemIndicator.setVisibility(View.VISIBLE);
			}
		}
		return convertView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.powerlong.electric.app.adapter.base.AdapterBase#onReachBottom()
	 */
	@Override
	protected void onReachBottom() {
		// TODO Auto-generated method stub

	}

}
