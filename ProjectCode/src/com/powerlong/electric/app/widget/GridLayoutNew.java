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
import java.util.Collection;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.utils.LogUtil;

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
public class GridLayoutNew extends LinearLayout implements OnClickListener {
	private static final int CELL_MARGIN_DP = 4;
	public  static  int[] MIDDLE_INDEX = new int[] {2,3,6,7};
	public  static  int ROW_COUNT = 2;
	public  static  int VIEW_COUNT = 10;
	private LayoutInflater mInflater;
	private ArrayList<LinearLayout> mRecommandViews;

	/**
	 * 创建一个新的实例 GridLayout.
	 * 
	 * @param context
	 */
	public GridLayoutNew(Context context) {
		super(context);
		init();
	}

	public GridLayoutNew(Context context, AttributeSet attr) {
		super(context, attr);
		init();
	}

	protected void init() {
		mRecommandViews = new ArrayList<LinearLayout>();
		mInflater = LayoutInflater.from(getContext());
		createRootView();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onFinishInflate()
	 */
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		createRecommandView();
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
		/*Context context = getContext();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		LinearLayout rooterView = new LinearLayout(context);
		rooterView.setOrientation(VERTICAL);
		rooterView.setLayoutParams(params);
		
		@SuppressWarnings("rawtypes")
		Collection TEMPLATE_COLL = new ArrayList();
		for (int child : MIDDLE_INDEX) {
			TEMPLATE_COLL.add(child);
		}
		Context context = getContext();
		LinearLayout childView = new LinearLayout(context);
		LinearLayout.LayoutParams childViewParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		childView.setLayoutParams(childViewParams);
		childView.setOrientation(VERTICAL);
		
		int i = 0;
		float f = Constants.screen_density;
		int k = (int) (CELL_MARGIN_DP * f);
		while (i < VIEW_COUNT) {
			//LogUtil.d("GridLayout","createRecommandView:i = "+i);
			LinearLayout childItem = new LinearLayout(context);
			ImageView ivRecommend;
			boolean isSmallFull = false;
			switch (i) {
			case 0:
				childItem = (LinearLayout) mInflater.inflate(
						R.layout.index_left_item, null);
				ivRecommend = (ImageView) childItem.findViewById(R.id.index_item_pic);
				ivRecommend.setImageResource(R.drawable.home_half_01);
				break;
			case 1:
				childItem = (LinearLayout) mInflater.inflate(
						R.layout.index_right_item, null);
				ivRecommend = (ImageView) childItem.findViewById(R.id.index_item_pic);
				ivRecommend.setImageResource(R.drawable.home_half_02);
				break;
			case 2:
				childItem = (LinearLayout) mInflater.inflate(
						R.layout.index_pic_item, null);
				ivRecommend = (ImageView) childItem.findViewById(R.id.index_item_pic);
				ivRecommend.setImageResource(R.drawable.home_full_01);
				break;
			case 3:
				childItem = (LinearLayout) mInflater.inflate(
						R.layout.index_pic_item_small, null);
				ivRecommend = (ImageView) childItem.findViewById(R.id.index_item_pic);
				ivRecommend.setImageResource(R.drawable.home_banner_small01);
				isSmallFull = true;
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
			}
			if (!TEMPLATE_COLL.contains(i)) {
				LinearLayout.LayoutParams childItemParams = new LinearLayout.LayoutParams(
						0, LayoutParams.MATCH_PARENT);
				childItemParams.weight = 1.0F;
				childItemParams.height = (int) (266 * Constants.displayWidth / 480);
				childItemParams.setMargins(k, k, k, k);
				childItem.setLayoutParams(childItemParams);
				childView.addView(childItem);
			} else {
				LinearLayout.LayoutParams childItemParams = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				childItemParams.setMargins(k, k, k, k);
				childItemParams.height = (int) ((isSmallFull?72:266)* Constants.displayWidth / 480);
				childItem.setLayoutParams(childItemParams);
				addView(childItem, childItemParams);
			}
			childItem.setTag(i);
			childItem.setOnClickListener(this);
			i++;
			
			if (((i >= 10) || (i % ROW_COUNT != 1)) && ((i <= 10) || (i % ROW_COUNT != 0))){
				
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
		
		invalidate();*/
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		Toast.makeText(getContext(), "功能完善中,尽请期待.", Toast.LENGTH_SHORT).show();
	}

}
