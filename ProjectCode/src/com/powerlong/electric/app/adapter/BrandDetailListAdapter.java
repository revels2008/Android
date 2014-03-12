/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * FloorDetailAdapter.java
 * 
 * 2013年8月26日-下午4:45:29
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.FloorDetailViewCache;
import com.powerlong.electric.app.cache.GrouponDetailViewCache;
import com.powerlong.electric.app.cache.GrouponItemViewCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.FloorDetailEntity;
import com.powerlong.electric.app.entity.GrouponItemEntity;
import com.powerlong.electric.app.entity.NavigationGrouponDetailsEntity;
import com.powerlong.electric.app.entity.ShopItemListEntity;
import com.powerlong.electric.app.utils.AsyncImageLoader;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.RoundCornerUtil;
import com.powerlong.electric.app.utils.AsyncImageLoader.ImageCallback;
import com.powerlong.electric.app.utils.StringUtil;

/**
 * 
 * FloorDetailAdapter
 * 
 * @author: YangCheng Miao
 * 2013年8月26日-下午4:45:29
 * 
 * @version 1.0.0
 * 
 */
public class BrandDetailListAdapter extends ArrayAdapter<ShopItemListEntity> {
	private ListView listView;
	private AsyncImageLoader asyncImageLoader;
	private List<ShopItemListEntity> mList;
	private Activity mActivity = null;
	
	public BrandDetailListAdapter(Activity activity, List<ShopItemListEntity> entities, ListView mListView) {
		super(activity, 0, entities);
		this.listView = mListView;
		asyncImageLoader = new AsyncImageLoader();
		mList = entities;
		mActivity = activity;
	}

    @Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public ShopItemListEntity getItem(int position) {
		return mList.get(position);
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) getContext();

        // Inflate the views from XML
        View rowView = convertView;
        GrouponItemViewCache viewCache;
        if (rowView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.shop_detail_list_item, null);
            viewCache = new GrouponItemViewCache(rowView);
            rowView.setTag(viewCache);
        } else {
            viewCache = (GrouponItemViewCache) rowView.getTag();
        }
        ShopItemListEntity entities = getItem(position);

        
        // Load the image and set it on the ImageView
        String imageUrl = entities.getImage();
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
	                	 Log.e("Adapter", "imageLoaded in step0 = "+position);
	                	 if (imageDrawable != null) {
								imageViewByTag.setImageDrawable(RoundCornerUtil.toRoundCorner(imageDrawable, 35));
							}
	                }
	            }
	        });
        }
        if (cachedImage == null) {
            imageView.setImageResource(R.drawable.pic_56);
//            this.notifyDataSetChanged();
            Log.e("Adapter", position+"+null");
        }else{
            imageView.setImageDrawable(cachedImage);
            Log.e("adapter","cachedImage"+position);
        }
        
        ImageView grouponFlag = viewCache.getGrouponView();
        LogUtil.d("listadpter", "grouponFlag = "+grouponFlag);
        if(grouponFlag!=null && Constants.isNeedGroupon){
        	int type = entities.getType();
        	LogUtil.d("listadpter", "type = "+type);
        	if(type == 1){
        		grouponFlag.setVisibility(View.VISIBLE);
        	}else{
        		grouponFlag.setVisibility(View.GONE);
        	}
        }
        // Set the text on the TextView
        TextView tvName = viewCache.getName();
        tvName.setText(entities.getName()+"");
        LogUtil.d("listadpter", "name = "+entities.getName());
    	
//        TextView tvDescription = viewCache.getDescription();
//        tvDescription.setText(entities.get()+"");
//        RatingBar rbGrade = viewCache.getGrade();
//        rbGrade.setRating(5);
        String pricePrfix = mActivity.getBaseContext().getResources().getString(R.string.str_price_prifix);
        
        TextView tvPriceNow = viewCache.getPriceNow();
        tvPriceNow.setText(String.format(pricePrfix, StringUtil.convertDoubleToString(entities.getPlPrice())));
        
        TextView tvPriceBefore = viewCache.getPriceBefore();
        tvPriceBefore.setText(String.format(pricePrfix, StringUtil.convertDoubleToString(entities.getListPrice())));
        tvPriceBefore.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        String sellNumSuffix = mActivity.getBaseContext().getResources().getString(R.string.str_sellnum_suffix);
        
        TextView tvCount = viewCache.getNum();
        tvCount.setText(String.format(sellNumSuffix, StringUtil.convertIntToString(entities.getSellNumMonth())));
        return rowView;
    }
	
	public void clear(){
		this.notifyDataSetChanged();
	}
}
