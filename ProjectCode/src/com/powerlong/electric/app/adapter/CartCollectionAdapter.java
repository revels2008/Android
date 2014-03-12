/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * CollectionAdapter.java
 * 
 * 2013年8月28日-下午8:23:46
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.CollectionCache;
import com.powerlong.electric.app.entity.CollectionEntity;
import com.powerlong.electric.app.entity.ItemFavourListEntity;
import com.powerlong.electric.app.utils.AsyncImageLoader;
import com.powerlong.electric.app.utils.AsyncImageLoader.ImageCallback;
import com.powerlong.electric.app.utils.RoundCornerUtil;

/**
 * 
 * CollectionAdapter
 * 
 * @author: YangCheng Miao
 * 2013年8月28日-下午8:23:46
 * 
 * @version 1.0.0
 * 
 */
public class CartCollectionAdapter extends ArrayAdapter<ItemFavourListEntity> {
	private ListView mListView;
	private AsyncImageLoader asyncImageLoader;
	private List<ItemFavourListEntity> mList;
	private Activity mActicity;
	private Context mContext;
	public Map<Integer, Boolean> isSelected;
	
	/**
	 * 创建一个新的实例 CollectionAdapter.
	 *
	 * @param context
	 * @param textViewResourceId
	 * @param objects
	 */
	public CartCollectionAdapter(Activity activity, List<ItemFavourListEntity> entities, ListView mListView) {
		super(activity, 0, entities);
		this.mListView = mListView;
		this.mContext = activity;
		asyncImageLoader = new AsyncImageLoader();
		mList = entities;
		isSelected = new HashMap<Integer, Boolean>();
		for (int i=0; i<entities.size(); i++) {
			isSelected.put(i, false);
		}
	}
	
	 @Override
		public int getCount() {
			return mList.size();
		}
		@Override
		public ItemFavourListEntity getItem(int position) {
			return mList.get(position);
		}
		
		public View getView(final int position, View convertView, ViewGroup parent) {
	        Activity activity = (Activity) getContext();
	        // Inflate the views from XML
	        View rowView = convertView;
	        CollectionCache viewCache;
	        if (rowView == null) {
	            LayoutInflater inflater = activity.getLayoutInflater();
	            rowView = inflater.inflate(R.layout.shopping_cart_collect_list_item, null);
	            viewCache = new CollectionCache(rowView);
	            rowView.setTag(viewCache);
	        } else {
	            viewCache = (CollectionCache) rowView.getTag();
	        }
	        ItemFavourListEntity entities = getItem(position);

	        // Load the image and set it on the ImageView
	        String imageUrl = entities.getPicturePath();
	        ImageView imageView = viewCache.getLogo();
	        imageView.setTag(imageUrl+position);
	        Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl, new ImageCallback() {
	            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
	                ImageView imageViewByTag = (ImageView) mListView.findViewWithTag(imageUrl+position);
	                if (imageViewByTag != null && imageDrawable != null) {
	                	
	                    imageViewByTag.setImageDrawable(RoundCornerUtil.toRoundCorner(imageDrawable, 15));
	                }
	            }
	        });

	        if (cachedImage == null) {
	            imageView.setImageResource(R.drawable.pic_96);
//	            this.notifyDataSetChanged();
//	            Log.e("Adapter", position+"+null");
	        }else{
	            imageView.setImageDrawable(cachedImage);
	            Log.e("adapter","cachedImage"+position);
	        }
	        
	        // Set the text on the TextView
	        TextView tvBrand = viewCache.getBrand();
	        tvBrand.setText(entities.getItemName());
//	        TextView tvDescription = viewCache.getDescription();
//	        tvDescription.setText(entities.getDescription());
	        TextView tvPrice = viewCache.getPrice();
	        tvPrice.setText("￥" + entities.getPlPrice());
	        TextView tvPriceOrg = viewCache.getPriceOrg();
	        tvPriceOrg.setText("￥" + entities.getListPrice());
	        tvPriceOrg.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
	        TextView tvSales = viewCache.getSales();
	        tvSales.setText(entities.getSellNum()+"");
	        viewCache.getChecked().setChecked(isSelected.get(position));
	        viewCache.getChecked().setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					Message message;
					 if (isSelected.get(position)) {   
			                isSelected.put(position, false);   
			            } else {   
			                isSelected.put(position, true);   
			            }    

				}
			});
	        return rowView;
	    }
}
