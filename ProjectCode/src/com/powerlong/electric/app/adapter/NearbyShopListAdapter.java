/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * FloorDetailAdapter.java 
 * 
 * 2013年8月26日-下午4:45:29
 *  2013宝龙公司-版权所有
 * 
 */
/**
 * 店铺列表适配器
 */
package com.powerlong.electric.app.adapter;

import java.util.List;

import android.app.Activity;
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
import com.powerlong.electric.app.entity.FloorDetailEntity;
import com.powerlong.electric.app.utils.AsyncImageLoader;
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
public class NearbyShopListAdapter extends ArrayAdapter<FloorDetailEntity> {
	private ListView listView;
	private AsyncImageLoader asyncImageLoader;
	private List<FloorDetailEntity> mList;
	
	public NearbyShopListAdapter(Activity activity, List<FloorDetailEntity> entities, ListView mListView) {
		super(activity, 0, entities);
		this.listView = mListView;
		asyncImageLoader = new AsyncImageLoader();
		mList = entities;
	}
	
    

    @Override
	public int getCount() {
//    	 Log.e("<<<<<<<<<<<>>>>>>>>>>>>", "getCount = "+mList.size());
		return mList.size();
	}

    

	@Override
	public FloorDetailEntity getItem(int position) {
		return mList.get(position);
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) getContext();

        // Inflate the views from XML
        View rowView = convertView;
        FloorDetailViewCache viewCache;
        if (rowView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.nearby_shop_list_item, null);
            viewCache = new FloorDetailViewCache(rowView);
            rowView.setTag(viewCache);
        } else {
            viewCache = (FloorDetailViewCache) rowView.getTag();
        }
        FloorDetailEntity entities = getItem(position);

        
        // Load the image and set it on the ImageView
        String imageUrl = entities.getImageUrl();
        boolean imgURLEnable = !StringUtil.isEmpty(imageUrl)?true:false;
        
        
        ImageView imageView = viewCache.getImageView();
        Drawable cachedImage = null;
        if(imgURLEnable){
	        imageView.setTag(imageUrl+position);
	        cachedImage = asyncImageLoader.loadDrawable(imageUrl, new ImageCallback() {
	            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
	            	Log.v("Adapter", "imageLoaded in");
	                ImageView imageViewByTag = (ImageView) listView.findViewWithTag(imageUrl+position);
	                if (imageViewByTag != null) {
	                	 Log.v("Adapter", "imageLoaded in step0 = "+position);
	                    imageViewByTag.setImageDrawable(imageDrawable);
	                }
	            }
	        });
        }
        if (cachedImage == null) {
            imageView.setImageResource(R.drawable.pic_246);
//            this.notifyDataSetChanged();
            Log.v("Adapter", position+"+null");
        }else{
            imageView.setImageDrawable(cachedImage);
            Log.v("adapter","cachedImage"+position);
        }
        // Set the text on the TextView
        TextView tvName = viewCache.getName();
        tvName.setText(entities.getName());
        TextView tvDescription = viewCache.getDescription();
        tvDescription.setText(entities.getDescription());
        RatingBar rbGrade = viewCache.getGrade();
        rbGrade.setRating(entities.getGrade());
        TextView tvEvaluate = viewCache.getEvaluate();
        if(tvEvaluate != null){
        	tvEvaluate.setText(entities.getGrade()+"");
        }
        TextView tvCategory = viewCache.getCategory();
        tvCategory.setText(entities.getCategory());
//        TextView tvNum = viewCache.getNum();
//        tvNum.setText(entities.getNum());

        return rowView;
    }
	
	public void clear(){
		this.notifyDataSetChanged();
	}
}
