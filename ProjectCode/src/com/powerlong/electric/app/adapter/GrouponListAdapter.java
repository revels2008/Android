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

import net.tsz.afinal.FinalBitmap;

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
import com.powerlong.electric.app.utils.AsyncImageLoader;
import com.powerlong.electric.app.utils.AsyncImageLoader.ImageCallback;
import com.powerlong.electric.app.utils.StringUtil;

/**
 * 
 * FloorDetailAdapter
 * 
 * @author: Liang Wang
 * 2013年8月26日-下午4:45:29
 * 
 * @version 1.0.0
 * 
 */
public class GrouponListAdapter extends ArrayAdapter<GrouponItemEntity> {
	private ListView listView;
	private AsyncImageLoader asyncImageLoader;
	private List<GrouponItemEntity> mList;
	private Activity mActivity = null;
	
	public GrouponListAdapter(Activity activity, List<GrouponItemEntity> entities, ListView mListView) {
		super(activity, 0, entities);
		this.listView = mListView;
		asyncImageLoader = new AsyncImageLoader();
		mList = entities;
		mActivity = activity;
	}
	
    

    @Override
	public int getCount() {
//    	 Log.e("<<<<<<<<<<<>>>>>>>>>>>>", "getCount = "+mList.size());
		return mList.size();
	}

    

	@Override
	public GrouponItemEntity getItem(int position) {
		return mList.get(position);
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) getContext();

        // Inflate the views from XML
        View rowView = convertView;
        GrouponItemViewCache viewCache;
        if (rowView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.groupon_list_item, null);
            viewCache = new GrouponItemViewCache(rowView);
            rowView.setTag(viewCache);
        } else {
            viewCache = (GrouponItemViewCache) rowView.getTag();
        }
        GrouponItemEntity entities = getItem(position);

        
        // Load the image and set it on the ImageView
        String imageUrl = entities.getImage();
        boolean imgURLEnable = !StringUtil.isEmpty(imageUrl)?true:false;
                
        ImageView imageView = viewCache.getImageView();
        FinalBitmap fb = FinalBitmap.create(mActivity);
        fb.configDiskCachePath(Constants.IMG_CACHE_PATH);
        fb.configLoadfailImage(R.drawable.pic_56);
        fb.display(imageView, imageUrl);
        
        ImageView grouponFlag = viewCache.getGrouponView();
        if(grouponFlag!=null && Constants.isNeedGroupon){
        	String type = entities.getType();
        	if(StringUtil.toInt(type, 0)==1){
        		grouponFlag.setVisibility(View.VISIBLE);
        	}else{
        		grouponFlag.setVisibility(View.GONE);
        	}
        }
        
        
        // Set the text on the TextView
        TextView tvName = viewCache.getName();
        tvName.setText(entities.getName()+"");
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
        tvCount.setText(String.format(sellNumSuffix, StringUtil.convertIntToString(entities.getSellNum())));
        return rowView;
    }
	
	public void clear(){
		this.notifyDataSetChanged();
	}
}
