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

import java.util.ArrayList;
import java.util.List;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.cache.FloorDetailViewCache;
import com.powerlong.electric.app.cache.ShoppingCartSettleCache;
import com.powerlong.electric.app.entity.FloorDetailEntity;
import com.powerlong.electric.app.entity.ItemBargainListEntity;
import com.powerlong.electric.app.entity.ItemListEntity;
import com.powerlong.electric.app.entity.ShoppingCartSettleEntity;
import com.powerlong.electric.app.utils.AsyncImageLoader;
import com.powerlong.electric.app.utils.DoubleUtil;
import com.powerlong.electric.app.utils.ListViewUtil;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.utils.AsyncImageLoader.ImageCallback;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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
public class ShoppingCartSettleAdapter extends ArrayAdapter<ShoppingCartSettleEntity> {
	private ListView listView;
	private AsyncImageLoader asyncImageLoader;
	private List<ShoppingCartSettleEntity> mList;
	private List<ItemListEntity> itemEntities;
	
	/**
	 * 创建一个新的实例 ShoppingCartSettleAdapter.
	 *
	 * @param context
	 * @param textViewResourceId
	 */
	public ShoppingCartSettleAdapter(Activity activity, List<ShoppingCartSettleEntity> entities, ListView mListView, List<ItemListEntity> mEntities) {
		super(activity, 0, entities);
		this.listView = mListView;
		asyncImageLoader = new AsyncImageLoader();
		mList = entities;
		itemEntities = mEntities;
		
	}

	@Override
	public int getCount() {
//    	 Log.e("<<<<<<<<<<<>>>>>>>>>>>>", "getCount = "+mList.size());
		return mList.size();
	}

    

	@Override
	public ShoppingCartSettleEntity getItem(int position) {
		return mList.get(position);
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) getContext();

        // Inflate the views from XML
        View rowView = convertView;
        ShoppingCartSettleCache viewCache;
        if (rowView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.shopping_cart_settle_item, null);
            viewCache = new ShoppingCartSettleCache(rowView);
            rowView.setTag(viewCache);
        } else {
            viewCache = (ShoppingCartSettleCache) rowView.getTag();
        }
        ShoppingCartSettleEntity entities = getItem(position);

        
        // Load the image and set it on the ImageView
        String imageUrl = entities.getImgUrl();
        boolean imgURLEnable = !StringUtil.isEmpty(imageUrl)?true:false;
        
        
        ImageView imageView = viewCache.getImageView();
        Drawable cachedImage = null;
        if(imgURLEnable){
	        imageView.setTag(imageUrl+position);
	        cachedImage = asyncImageLoader.loadDrawable(imageUrl, new ImageCallback() {
	            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
	            	Log.e("Adapter", "imageLoaded in");
	                ImageView imageViewByTag = (ImageView) listView.findViewWithTag(imageUrl+position);
	                if (imageViewByTag != null) {
	                	 Log.v("Adapter", "imageLoaded in step0 = "+position);
	                    imageViewByTag.setImageDrawable(imageDrawable);
	                }
	            }
	        });
        }
        if (cachedImage == null) {
            imageView.setImageResource(R.drawable.pic_56);
//            this.notifyDataSetChanged();
            Log.v("Adapter", position+"+null");
        }else{
            imageView.setImageDrawable(cachedImage);
            Log.v("adapter","cachedImage"+position);
        }
        // Set the text on the TextView
        TextView tvName = viewCache.getName();
        tvName.setText(entities.getItemName());
        TextView tvDescription = viewCache.getDescription();
        tvDescription.setText(entities.getDescription());
        TextView tvPrice = viewCache.getPrice();
        tvPrice.setText(DoubleUtil.subPrice(Double.parseDouble(entities.getPrice())));
        TextView tvCount = viewCache.getCount();
        tvCount.setText("x" + entities.getCount());
        int id = new Long(itemEntities.get(position).getItemId()).intValue();
		ArrayList<ItemBargainListEntity> itemBargainEntities = DataCache.itemBargainListCache.get(id);
		if(null != itemBargainEntities && itemBargainEntities.size() > 0) {
			ListView itemList = viewCache.getList();
			List<String> data = new ArrayList<String>();
			for (int m=0; m<itemBargainEntities.size(); m++) {
				ItemBargainListEntity bargainEntity = itemBargainEntities.get(m);
				data.add(bargainEntity.getName());
			}
			ItemBargainAdapter adapter = new ItemBargainAdapter(activity, data, itemList);
			
			itemList.setAdapter(adapter);
			itemList.setEnabled(false);
			ListViewUtil.setListViewHeightBasedOnChildren(itemList);
		}

        return rowView;
    }
	
	public void clear(){
		this.notifyDataSetChanged();
	}
}
