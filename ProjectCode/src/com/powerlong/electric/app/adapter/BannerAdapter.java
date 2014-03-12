/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * BannerAdapter.java
 * 
 * 2013-8-22-上午09:34:40
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.volleytest.cache.BitmapCache;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.cache.FileCache;
import com.powerlong.electric.app.cache.LCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.BannerEntity;
import com.powerlong.electric.app.ui.HomeActivityNew;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;


/**
 * 
 * BannerAdapter:Banner adapter
 * 
 * @author: Liang Wang
 * 2013-8-22-上午09:34:40
 * 
 * @version 1.0.0
 * 
 */
public class BannerAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	//private static final int[] ids = {R.drawable.home_banner_big02, R.drawable.home_banner_big01,R.drawable.home_banner_big03};
	private RequestQueue mQueue;
    private ImageLoader mImageLoader;
    private LCache mBmpCache = null;
    public static int picCount;
    
	public BannerAdapter(Context context) {
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mQueue = Volley.newRequestQueue(context);
		mBmpCache =  Constants.mBmpCache;
		LogUtil.e("BannerAdapter", "init...");
		mImageLoader = new ImageLoader(new FileCache(mContext,"banner"),mQueue, mBmpCache,8);
	}

	@Override
	public int getCount() {
		if(DataCache.BannerCache.size()>0)
			return Integer.MAX_VALUE;   //返回很大的值使得getView中的position不断增大来实现循环
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.banner_image_item, null);
		}
		
		final int size = DataCache.BannerCache.size();
		picCount = size;
		if(size==0)
			return null;
		//((ImageView) convertView.findViewById(R.id.imgView)).setImageResource(R.drawable.home_banner_big);
		final BannerEntity entity = DataCache.BannerCache.get(position%size);
		//ImageListener listener = ImageLoader.getImageListener(((NetworkImageView) convertView.findViewById(R.id.imgView)), R.drawable.home_banner_big, R.drawable.home_banner_big);
	    //mImageLoader.get(size!=0?entity.getAdImage():"", listener);
		if(entity!=null){
			String url = entity.getAdImage();
			NetworkImageView imgView = (NetworkImageView) convertView.findViewById(R.id.imgView);
			imgView.setDefaultImageResId(R.drawable.home_banner_big);
			imgView.setErrorImageResId(R.drawable.home_banner_big);
			if(StringUtil.isValidUrl(url)){
				imgView.setImageUrl(size!=0?entity.getAdImage():"", mImageLoader);
			}else{
				imgView.setBackgroundResource(R.drawable.home_banner_big);
			}
			/*else{
				ImageListener listener = ImageLoader.getImageListener(((ImageView) convertView.findViewById(R.id.imgView)), R.drawable.home_banner_big, R.drawable.home_banner_big);
				mImageLoader.get(size!=0?entity.getAdImage():"", listener);
			}*/
		    
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					/*Intent intent = new Intent(mContext,DetailActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("image_id", ids[position%ids.length]);
					intent.putExtras(bundle);
					mContext.startActivity(intent);*/
					LogUtil.d("banneradapter", "onClick..");
					LogUtil.d("banneradapter", "type ="+entity.getType());
					
					if(entity.getType() == 0) {
						IntentUtil.startHomeLinkActivity(mContext,entity.getAdLink(), entity.getAdDis());
						LogUtil.d("banneradapter", "adlink ="+entity.getAdLink());
					} else {
						IntentUtil.startHomePageLinkActivity(mContext,entity.getType(),entity.getParams());
						LogUtil.d("banneradapter", "params ="+entity.getParams());
					}
					
					
				}
			});
		}
		return convertView;
	}

}
