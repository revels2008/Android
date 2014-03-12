/**
 * 宝龙电商
 * com.powerlong.electric.app.widget
 * GridLayout.java
 * 
 * 2013-8-24-上午10:23:45
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.widget;

import java.util.ArrayList;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.cache.FileCache;
import com.powerlong.electric.app.cache.LCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.RecommendEntity;
import com.powerlong.electric.app.utils.AsyncImageLoader;
import com.powerlong.electric.app.utils.ImageDownloadHandler;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.View.OnClickListener;;

/**
 * 
 * GridLayout:首页广告位视图
 * 
 * @author: Liang Wang 2013-8-24-上午10:23:45
 * 
 * @version 1.0.0
 * 
 */
public class GridLayout extends LinearLayout implements OnClickListener {
	private static final int CELL_MARGIN_DP = 4;
	//private static final int[] MIDDLE_INDEX = new int[] {2,3,6,7};
	private static final int ROW_COUNT = 2;
	//private static final int VIEW_COUNT = 10;
	private LayoutInflater mInflater;
	private ArrayList<LinearLayout> mRecommandViews;
	private AsyncImageLoader asyncImageLoader;
	private RequestQueue mQueue;
    private ImageLoader mImageLoader;
    private LCache mBmpCache = null;
    private Context mContext = null;
    private ImageDownloadHandler mDownloadHandler;
	
	public ImageDownloadHandler getmDownloadHandler() {
		return mDownloadHandler;
	}

	public void setmDownloadHandler(ImageDownloadHandler mDownloadHandler) {
		this.mDownloadHandler = mDownloadHandler;
	}

	/**
	 * 创建一个新的实例 GridLayout.
	 * 
	 * @param context
	 */
	public GridLayout(Context context) {
		super(context);
		mContext= context;
		init();
	}

	public GridLayout(Context context, AttributeSet attr) {
		super(context, attr);
		mContext= context;
		init();
	}

	protected void init() {
		mRecommandViews = new ArrayList<LinearLayout>();
		mInflater = LayoutInflater.from(getContext());
		createRootView();
		asyncImageLoader = new AsyncImageLoader();
		mQueue = Volley.newRequestQueue(getContext());
		mBmpCache = Constants.mBmpCache;
		LogUtil.e("GridLayout", "init...");
		mImageLoader = new ImageLoader(new FileCache(mContext,"recommend"),mQueue, mBmpCache,Constants.displayWidth/ROW_COUNT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onFinishInflate()
	 */
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		//createRecommandView();
		super.onFinishInflate();
	}

	private void createRootView() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		setLayoutParams(params);
		setOrientation(VERTICAL);
	}

	@SuppressWarnings("unchecked")
	public void createRecommandView(){
		LogUtil.d("GridLayout","createRecommandView in... + view count = "+Constants.VIEW_COUNT);
		@SuppressWarnings("rawtypes")
		//Collection TEMPLATE_COLL = new ArrayList();
		//for (int child : MIDDLE_INDEX) {
		//	TEMPLATE_COLL.add(child);
		//}
		Context context = getContext();
		LinearLayout childView = new LinearLayout(context);
		LinearLayout.LayoutParams childViewParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		childView.setLayoutParams(childViewParams);

		int i = 0;
		float f = Constants.screen_density;
		int k = (int) (CELL_MARGIN_DP * f);
		while (i < Constants.VIEW_COUNT ) {
			LogUtil.d("GridLayout","createRecommandView:i = "+i);
			RecommendEntity entity = DataCache.RecommendCache.get(i);
			LinearLayout childItem = new LinearLayout(context);
			ImageView ivRecommend;
			boolean isSmallFull = false;
			ImageListener listener = null;
			
			/*switch (i) {
			case 0:
				childItem = (LinearLayout) mInflater.inflate(
						R.layout.index_left_item, null);
				ivRecommend = (ImageView) childItem.findViewById(R.id.index_item_pic);
				ivRecommend.setImageResource(R.drawable.home_half_01);
				listener = ImageLoader.getImageListener(ivRecommend, R.drawable.home_half, R.drawable.home_half);
			    mImageLoader.get(entity.getAdImage(), listener);
				break;
			case 1:
				childItem = (LinearLayout) mInflater.inflate(
						R.layout.index_right_item, null);
				ivRecommend = (ImageView) childItem.findViewById(R.id.index_item_pic);
				ivRecommend.setImageResource(R.drawable.home_half_02);
				listener = ImageLoader.getImageListener(ivRecommend, R.drawable.home_half, R.drawable.home_half);
			    mImageLoader.get(entity.getAdImage(), listener);
				break;
			case 2:
				childItem = (LinearLayout) mInflater.inflate(
						R.layout.index_pic_item, null);
				ivRecommend = (ImageView) childItem.findViewById(R.id.index_item_pic);
				ivRecommend.setImageResource(R.drawable.home_full_01);
				listener = ImageLoader.getImageListener(ivRecommend, R.drawable.home_full, R.drawable.home_full);
			    mImageLoader.get(entity.getAdImage(), listener);
				break;
			case 3:
				childItem = (LinearLayout) mInflater.inflate(
						R.layout.index_pic_item_small, null);
				ivRecommend = (ImageView) childItem.findViewById(R.id.index_item_pic);
				ivRecommend.setImageResource(R.drawable.home_banner_small01);
				isSmallFull = true;
				listener = ImageLoader.getImageListener(ivRecommend, R.drawable.home_banner_small, R.drawable.home_banner_small);
			    mImageLoader.get(entity.getAdImage(), listener);
				break;
			case 4:
				childItem = (LinearLayout) mInflater.inflate(
						R.layout.index_left_item, null);
				ivRecommend = (ImageView) childItem.findViewById(R.id.index_item_pic);
				ivRecommend.setImageResource(R.drawable.home_half_03);
				break;
			case 5:
				childItem = (LinearLayout) mInflater.inflate(
						R.layout.index_right_item, null);
				ivRecommend = (ImageView) childItem.findViewById(R.id.index_item_pic);
				ivRecommend.setImageResource(R.drawable.home_half_04);
				break;
			case 6:
				childItem = (LinearLayout) mInflater.inflate(
						R.layout.index_pic_item, null);
				ivRecommend = (ImageView) childItem.findViewById(R.id.index_item_pic);
				ivRecommend.setImageResource(R.drawable.home_full_02);
				break;
			case 7:
				childItem = (LinearLayout) mInflater.inflate(
						R.layout.index_pic_item_small, null);
				ivRecommend = (ImageView) childItem.findViewById(R.id.index_item_pic);
				ivRecommend.setImageResource(R.drawable.home_banner_small02);
				isSmallFull = true;
				break;
			case 8:
				childItem = (LinearLayout) mInflater.inflate(
						R.layout.index_left_item, null);
				ivRecommend = (ImageView) childItem.findViewById(R.id.index_item_pic);
				ivRecommend.setImageResource(R.drawable.home_half_05);
				break;
			case 9:
				childItem = (LinearLayout) mInflater.inflate(
						R.layout.index_right_item, null);
				ivRecommend = (ImageView) childItem.findViewById(R.id.index_item_pic);
				ivRecommend.setImageResource(R.drawable.home_half_06);
				break;
			}*/
			if (!Constants.TEMPLATE_COLL.contains(i)) {
				//if(i%2==0)
				//{
					childItem = (LinearLayout) mInflater.inflate(
							R.layout.index_left_item, null);

					ivRecommend = (ImageView) childItem.findViewById(R.id.index_item_pic);
					
					if(StringUtil.isValidUrl(entity.getAdImage())){
						//ivRecommend.setImageResource(R.drawable.home_half_01);
						//listener = ImageLoader.getImageListener(ivRecommend, R.drawable.home_half, R.drawable.home_half);
					    //mImageLoader.get(entity.getAdImage(), listener);
//						ivRecommend.setDefaultImageResId(R.drawable.home_half);
//						ivRecommend.setImageUrl(entity.getAdImage(), mImageLoader);

						if (getmDownloadHandler() != null) {
							getmDownloadHandler().downloadImage(entity.getAdImage(), ivRecommend);
						}
						
						//ivRecommend = (ImageView) childItem.findViewById(R.id.index_item_pic);
						//ivRecommend.setImageResource(R.drawable.home_half);
					}else{
						ivRecommend.setBackgroundResource(R.drawable.home_half);
					}
/*				}else{
					childItem = (LinearLayout) mInflater.inflate(
							R.layout.index_pic_item, null);
					ivRecommend = (ImageView) childItem.findViewById(R.id.index_item_pic);
					ivRecommend.setImageResource(R.drawable.home_full_01);
					listener = ImageLoader.getImageListener(ivRecommend, R.drawable.home_full, R.drawable.home_full);
				    mImageLoader.get(entity.getAdImage(), listener);
				}*/
				
				LinearLayout.LayoutParams childItemParams = new LinearLayout.LayoutParams(
						0, LayoutParams.MATCH_PARENT);
				childItemParams.weight = 1.0F;
				childItemParams.height = (int) ((entity.getAdHeight()/1.25) * Constants.displayWidth / 480);
				childItemParams.setMargins(k, k, k, k);
				childItem.setLayoutParams(childItemParams);
				LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				ivRecommend.setLayoutParams(mLayoutParams);
				childView.addView(childItem);
			} else {
				childItem = (LinearLayout) mInflater.inflate(
						R.layout.index_pic_item, null);
				ivRecommend = (ImageView) childItem.findViewById(R.id.index_item_pic);
				if(i%2==0){
//					ivRecommend.setDefaultImageResId(R.drawable.home_full);
					if(StringUtil.isValidUrl(entity.getAdImage())){
						if (getmDownloadHandler() != null) {
							getmDownloadHandler().downloadImage(entity.getAdImage(), ivRecommend);
						}
					}else{
						ivRecommend.setBackgroundResource(R.drawable.home_full);
					}
				}
				else{
//					ivRecommend.setDefaultImageResId(R.drawable.home_banner_small);
					if(StringUtil.isValidUrl(entity.getAdImage())){
						if (getmDownloadHandler() != null) {
							getmDownloadHandler().downloadImage(entity.getAdImage(), ivRecommend);
						}
					}else{
						ivRecommend.setBackgroundResource(R.drawable.home_banner_small);
					}
				}

				LinearLayout.LayoutParams childItemParams = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				childItemParams.setMargins(k, k, k, k);
				childItemParams.height = (int) ((entity.getAdHeight()/1.25)* Constants.displayWidth / 480);
				childItem.setLayoutParams(childItemParams);
				LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				ivRecommend.setLayoutParams(mLayoutParams);
				addView(childItem, childItemParams);
			}
			childItem.setTag(i);
			childItem.setOnClickListener(this);
			i++;
			
			if (((i >= Constants.VIEW_COUNT) || (i % ROW_COUNT != 1)) && ((i <= Constants.VIEW_COUNT) || (i % ROW_COUNT != 0))){
				
			}
			else{
				LogUtil.d("GridLayout","createRecommandView:i = "+i+"continue");
				continue;
			}
			LogUtil.d("GridLayout","createRecommandView:i = "+i+"");
			LinearLayout.LayoutParams childItemLayParams = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			addView(childView,childItemLayParams);
			
			
			LinearLayout localLinearLayout3 = new LinearLayout(context);
			childView = localLinearLayout3;
		}
		invalidate();
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		RecommendEntity entity = DataCache.RecommendCache.get((Integer)view.getTag());
		int type = entity.getLinkType();
		String params = entity.getParams();
//		IntentUtil.startHomePageLinkActivity(mContext, type, params);
		if(type == 0) {
			IntentUtil.startHomeLinkActivity(mContext,entity.getAdLink(), entity.getAdDis());
			LogUtil.d("banneradapter", "adlink ="+entity.getAdLink());
		} else {
			IntentUtil.startHomePageLinkActivity(mContext,type,params);
			LogUtil.d("banneradapter", "params ="+entity.getParams());
		}
	}

}
