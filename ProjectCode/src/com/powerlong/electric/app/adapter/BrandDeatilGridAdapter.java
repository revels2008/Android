package com.powerlong.electric.app.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.R.drawable;
import com.powerlong.electric.app.cache.BrandDetaiViewCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.ShopItemListEntity;
import com.powerlong.electric.app.utils.AsyncImageLoader;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.RoundCornerUtil;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.utils.AsyncImageLoader.ImageCallback;

//import com.blade.qianghaoqi.util.AsyncImageLoader;

public class BrandDeatilGridAdapter extends ArrayAdapter<ShopItemListEntity> {
	private GridView mGidView;
	private AsyncImageLoader asyncImageLoader;
	private HashMap<Integer, View> mViewMap = new HashMap<Integer, View>();
	private List<ShopItemListEntity> mList;
	
	public BrandDeatilGridAdapter(Activity activity,
			List<ShopItemListEntity> entities, GridView gridView) {
		super(activity, 0, entities);
		this.mGidView = gridView;
		asyncImageLoader = new AsyncImageLoader();
		mList = entities;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public ShopItemListEntity getItem(int position) {
		return mList.get(position);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Activity activity = (Activity) getContext();
		BrandDetaiViewCache viewCache;
		int isFavour; //是否已加入收藏
		int isCart; //是否已加入购物车
		if (convertView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.brand_detail_grid_item, null);
			viewCache = new BrandDetaiViewCache(convertView);
			convertView.setTag(viewCache);
		} else {
			viewCache = (BrandDetaiViewCache) convertView.getTag();
		}
		ShopItemListEntity entity = getItem(position);

		if (entity != null) {
			// Load the image and set it on the ImageView
			RelativeLayout.LayoutParams childItemParams = new RelativeLayout.LayoutParams(
					0, LayoutParams.MATCH_PARENT);
			childItemParams.height = (int) (263 * Constants.displayWidth / 480);
			childItemParams.width = (int) (213 * Constants.displayWidth / 480);
			RelativeLayout rl = viewCache.getLayoutOut();
			rl.setLayoutParams(childItemParams);
			String imageUrl = entity.getImage();
			ImageView imageView = viewCache.getImageView();
			android.view.ViewGroup.LayoutParams para;
	        para = imageView.getLayoutParams();	                
	        para.width = (int) (164 * Constants.displayWidth / 480);
	        para.height = (int) (164 * Constants.displayWidth / 480);
	        imageView.setLayoutParams(para);
			imageView.setTag(Integer.valueOf(position) + imageUrl);
			Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl,
					new ImageCallback() {
						public void imageLoaded(Drawable imageDrawable,
								String imageUrl) {
							ImageView imageViewByTag = (ImageView) mGidView
									.findViewWithTag(Integer.valueOf(position)
											+ imageUrl);
							if (imageViewByTag != null) {
								if (imageDrawable != null) {
									imageViewByTag.setImageDrawable(RoundCornerUtil.toRoundCorner(imageDrawable, 35));
								}
							}
						}
					});
			if (cachedImage == null) {
				imageView.setImageResource(R.drawable.pic_130);
				// Log.e("Adapter", "null");
			} else {
				imageView.setImageDrawable(RoundCornerUtil.toRoundCorner(cachedImage, 35));
			}
			
	        ImageView grouponFlag = viewCache.getGrouponView();
	        if(grouponFlag!=null && Constants.isNeedGroupon){
	        	int type = entity.getType();
	        	LogUtil.d("gridadpter", "type"+type);
	        	if(type == 1){
	        		grouponFlag.setVisibility(View.VISIBLE);
	        	}else{
	        		grouponFlag.setVisibility(View.GONE);
	        	}
	        }
			// Set the text on the TextView
			String name = entity.getName();
			TextView tvName = viewCache.getNameView();
			tvName.setText(entity.getName());
			String price = Constants.moneyTag + entity.getPlPrice();
			
			LogUtil.d("gridadpter", "name ="+name);
			LogUtil.d("gridadpter", "price ="+price);
			
			TextView tvPrice = viewCache.getPriceView();
			tvPrice.setText(Constants.moneyTag + entity.getPlPrice()+"");
			
			isFavour = entity.getIsFavour();
			isCart = entity.getIsCart();
			ImageView ivFavour = viewCache.getImageFavour();
			ImageView ivCart = viewCache.getImageCart();
			TextView tvFavour = viewCache.getTextFavour();
			TextView tvCart = viewCache.getTextCart();
			if (isFavour == 1) {
				ivFavour.setImageResource(R.drawable.btn_favorite_press);
				tvFavour.setText("已收藏");
			}else{
				ivFavour.setImageResource(R.drawable.btn_favorite_nor);
				tvFavour.setText("收藏");
			}
			
			if (isCart == 1) {
				ivCart.setImageResource(R.drawable.btn_shopcar_press);
				tvCart.setText("已加入");
			}else{
				ivCart.setImageResource(R.drawable.btn_shopcar_nor);
				tvCart.setText("购物车");
			}
		}

		return convertView;
	}

	public void clear() {
		// this.mViewMap.clear();
		this.notifyDataSetChanged();
	}

}