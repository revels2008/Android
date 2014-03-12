/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * ActvityActivity.java
 * 
 * 2013年8月21日-下午5:43:25
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.utils.AsyncImageLoaderUtil;
import com.powerlong.electric.app.adapter.ImageAndTextListAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.cache.ViewCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.ActivityItemEntity;
import com.powerlong.electric.app.entity.ImageAndText;
import com.powerlong.electric.app.entity.NavigationActivityEntity;
import com.powerlong.electric.app.entity.NavigationBaseEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.ui.model.ViewItem;
import com.powerlong.electric.app.utils.AsyncImageLoaderUtil.ILoadImageCallback;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.widget.PlTableView;
import com.powerlong.electric.app.widget.PlTableView.ClickListener;

/**
 * 
 * ActvityActivity
 * 
 * @author: YangCheng Miao 2013年8月21日-下午5:43:25
 * 
 * @version 1.0.0
 * 
 */
public class LatestActivity extends BaseActivity implements OnClickListener {
	PlTableView tableView;
	List<PlTableView> list = new ArrayList<PlTableView>();
	TextView tvCategory, tvActivityAdvertising, tvTime, tvAddress, tvSponor;
	ImageView ivAdvertising;
	ImageView ivBack;
	ImageView ivFilter;
	//private ProgressBar mpProgressBar = null;
	ArrayList<ActivityItemEntity> entity;
//	final Handler handler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			((ImageView) LatestActivity.this.findViewById(msg.arg1))
//					.setImageDrawable((Drawable) msg.obj);
//		}
//	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity);
		findView();
		getData();
	}

	/**
	 * getData(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void getData() {
		showLoadingDialog(null);
		DataUtil.getNavActvityData(getBaseContext(), mServerConnectionHandler, 1);
	}

	/**
	 * findView(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void findView() {
		ivBack = (ImageView) findViewById(R.id.ivReturn);
		ivBack.setOnClickListener(this);
		ivFilter = (ImageView) findViewById(R.id.ivFilter);
		ivFilter.setOnClickListener(this);
		//mpProgressBar = (ProgressBar) findViewById(R.id.progressbar);

	}

	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			LogUtil.d("LatestActicity", "msg.what = " + msg.what);
			LogUtil.d("LatestActicity", "msg.arg1 = " + msg.arg1);
			dismissLoadingDialog();
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				String navId = Integer.toString(msg.arg1);
				LogUtil.d("HomeActivity", "navId = " + navId);

				updateNavItemView(navId);
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
				break;
			}
			//mpProgressBar.setVisibility(View.GONE);
		}

	};

	protected void updateNavItemView(String navId) {
		entity = DataCache.NavActivityListCache;
		tableView = new PlTableView(getBaseContext(), null);
		tableView.setClickable(false);
		tableView.setFocusable(false);
		LayoutInflater mInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout child[] = new RelativeLayout[entity.size()];
		ScrollView sv = (ScrollView) findViewById(R.id.sv_activity);
		LinearLayout ll = new LinearLayout(sv.getContext());
		ll.setOrientation(LinearLayout.VERTICAL);
//		CustomClickListener listener[];
		for (int i = 0; i < entity.size(); i++) {
			tableView = new PlTableView(getBaseContext(), null);
//			listener = new CustomClickListener[entity.size()];
			final int index = i;

			child[i] = (RelativeLayout) mInflater.inflate(
					R.layout.activity_item, null);
			
			ivAdvertising = (ImageView) child[i]
					.findViewById(R.id.iv_activity_item);
			tvCategory = (TextView) child[i]
					.findViewById(R.id.tv_activity_category);
			tvActivityAdvertising = (TextView) child[i]
					.findViewById(R.id.tv_activity_advertising);
			tvTime = (TextView) child[i].findViewById(R.id.tv_activity_time);
			tvAddress = (TextView) child[i]
					.findViewById(R.id.tv_activity_address);
			tvSponor = (TextView) child[i]
					.findViewById(R.id.tv_activity_sponsor);

			String imageUrl = entity.get(i).getImage();
			AsyncImageLoaderUtil imageLoader = new AsyncImageLoaderUtil();
			
			Drawable cachedImage = imageLoader.loadDrawable(imageUrl,ivAdvertising, new ILoadImageCallback() {

				@Override
				public void onObtainDrawable(Drawable drawable,ImageView imageView) {
					imageView.setImageDrawable(drawable);
				}
				
			});
			if (cachedImage == null) {
				ivAdvertising.setImageResource(R.drawable.pic_56);
//				Log.e("Adapter", "null");
			} else {
				ivAdvertising.setImageDrawable(cachedImage);
			}

			ivAdvertising.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					long activityId;
					activityId = entity.get(index).getId();
					Intent intent = new Intent(LatestActivity.this, ActivityDetailActivity.class);
					intent.putExtra("activityId", activityId);
					startActivity(intent);
				}
			});
			// loadImage(entity.get(i).getThumbnail(), R.id.iv_activity_item);
			tvCategory.setText(entity.get(i).getClassification());
			tvCategory.setVisibility(View.GONE);
			tvActivityAdvertising.setText(entity.get(i).getName());
			tvTime.setText(entity.get(i).getDuration());
			tvAddress.setText(entity.get(i).getAddress());
			tvAddress.setVisibility(View.VISIBLE);
			tvSponor.setText(entity.get(i).getIsPlazaActivity()==0?"商家":"城市广场");
			tvSponor.setVisibility(View.VISIBLE);

			ViewItem viteItem = new ViewItem(child[i]);
			tableView.addViewItem(viteItem);

			ll.addView(tableView);
			tableView.commit();
		}
		sv.addView(ll);
	}

/*	private class CustomClickListener implements ClickListener {

		@Override
		public void onClick(int index) {
//			IntentUtil.start_activity(LatestActivity.this, ActivityDetailActivity.class);
			long activityId;
			activityId = entity.get(index).getId();
			Intent intent = new Intent(LatestActivity.this, ActivityDetailActivity.class);
			intent.putExtra("activityId", activityId);
			startActivity(intent);
		}
	}*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivReturn:
			finish();
			break;
		case R.id.ivFilter:
			break;
		}

	}

	// protected Drawable loadImageFromUrl(String imageUrl) {
	// Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl, new
	// ImageCallback() {
	// public void imageLoaded(Drawable imageDrawable, String imageUrl) {
	// ImageView imageViewByTag = (ImageView)
	// listView.findViewWithTag(imageUrl);
	// if (imageViewByTag != null) {
	// imageViewByTag.setImageDrawable(imageDrawable);
	// }
	// }
	// });
	// if (cachedImage == null) {
	// imageView.setImageResource(R.drawable.icon);
	// Log.e("Adapter", "null");
	// }else{
	// imageView.setImageDrawable(cachedImage);
	// }
	// }

//	private void loadImage(final String url, final ImageView iv) {
//		Thread thread = new Thread() {
//			@Override
//			public void run() {
//				Drawable drawable = null;
//				try {
//					drawable = Drawable.createFromStream(
//							new URL(url).openStream(), "image.png");
//				} catch (IOException e) {
//				}
//
//				Message message = handler.obtainMessage();
//				message.arg1 = 0;
//				message.obj = drawable;
//				handler.sendMessage(message);
//			}
//		};
//		thread.start();
//		thread = null;
//	}
}
