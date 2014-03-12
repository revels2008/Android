/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * BrandActivity.java
 * 
 * 2013年8月12日-下午3:01:15
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.GridViewAdapter;
import com.powerlong.electric.app.adapter.ImageAndTextListAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.BrandEntity;
import com.powerlong.electric.app.entity.BrandListEntity;
import com.powerlong.electric.app.entity.ImageAndText;
import com.powerlong.electric.app.entity.NavigationActivityEntity;
import com.powerlong.electric.app.entity.NavigationBaseEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.ToastUtil;

/**
 * 
 * BrandActivity
 * 
 * @author: YangCheng Miao 2013年8月12日-下午3:01:15
 * 
 * @version 1.0.0
 * 
 */

public class BrandActivity extends BaseActivity implements OnClickListener{
	private GridView gridView;
	private TextView txTitle;
	private ImageView ivReturn;
	private ImageView ivFilter;

	Handler mChildHandler, mMainHandler;
	//private ProgressBar mpProgressBar = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.brand);
		
		findView();
		txTitle.setText("品牌");
		getData();
	}

	/**
	 * findView(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void findView() {
		//mpProgressBar = (ProgressBar) findViewById(R.id.progressbar);
		gridView = (GridView) findViewById(R.id.mygridview);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		txTitle = (TextView) findViewById(R.id.txTitle);
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(this);
		ivFilter = (ImageView) findViewById(R.id.ivFilter);
		ivFilter.setOnClickListener(this);
		ivFilter.setVisibility(View.GONE);
		
	}

	private void getData() {
		showLoadingDialog(null);
		DataUtil.getNavBrandData(
				getBaseContext(), mServerConnectionHandler);
	}
	
	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler(){

		@Override
		public void handleMessage(Message msg) {
			
			LogUtil.d("BrandActivity", "msg.what = "+msg.what);
			LogUtil.d("BrandActivity", "msg.arg1 = "+msg.arg1);
			switch(msg.what){
			case Constants.HttpStatus.SUCCESS:
				updateView(Integer.toString(msg.arg1));
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
				break;
			}
			dismissLoadingDialog();
		}
		
	};

	/**
	 * updateView(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @param navId 
	 * @exception 
	 * @since  1.0.0
	*/
	protected void updateView(String navId) {
		final ArrayList<BrandListEntity> entities = DataCache.BrandListCache;
		BrandListEntity entity;
		List<ImageAndText> list = new ArrayList<ImageAndText>();
		
		if (entities != null) {
			for (int i=0; i<entities.size(); i++) {
				entity = entities.get(i);
				list.add(new ImageAndText(entity.getIcon(), entity.getIcon()));
			}
			LogUtil.d("BrandActivity", "list szie = "+list.size());
			gridView.setAdapter(new ImageAndTextListAdapter(this, list,gridView));
			
			gridView.setOnItemClickListener(new OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
//					IntentUtil.start_activity(BrandActivity.this, BrandDetailActivity.class);
					long shopId;
					BrandListEntity entity = entities.get(arg2);
					shopId = entity.getId();
					
					Intent intent = new Intent(BrandActivity.this, ShopDetailActivityNew.class);
					intent.putExtra("shopId", shopId);
					intent.putExtra("from", "BrandActivity");
					intent.putExtra("orderType", "0");
					startActivity(intent);
				}
			});
		}
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.ivReturn:
			finish();
			break;
		}
		
	}

}

/**
 * loader = new AsnycImageLoader(); gridView = (GridView)
 * findViewById(R.id.mygridview);
 * 
 * // gridViewAdapter = new GridViewAdapter(this, R.layout.brand_grid_item, //
 * mDrawale); // gridView = (GridView) findViewById(R.id.mygridview); new
 * ChildThread().start();
 * 
 * if (mChildHandler != null) { Message childMsg =
 * mChildHandler.obtainMessage(); childMsg.what = 1;
 * mChildHandler.sendMessage(childMsg); } mMainHandler = new Handler() {
 * 
 * @Override public void handleMessage(Message msg) { Drawable d = (Drawable)
 *           msg.obj;
 * 
 *           // Log.i(TAG, "Got an incoming message from the child thread - " //
 *           + (String) msg.obj); // 接收子线程的消息 // info.setText((String) msg.obj);
 *           //if (message.equals("OK")){ gridViewAdapter = new
 *           GridViewAdapter(BrandActivity.this, R.layout.brand_grid_item,d);
 * 
 *           // Log.e("<<<<<<<<<<<<<<<<<<<<<<<", "OK");
 * 
 *           //} } };
 * 
 *           }
 * 
 *           public void loadImage(String url, int id) { final ImageView
 *           imageView = (ImageView) findViewById(id); Drawable cacheImage =
 *           loader.loadDrawable(url, new AsnycImageLoader.ImageCallback() {
 * @Override public void imageLoaded(Drawable drawable) {
 *           imageView.setImageDrawable(drawable); } }); if (cacheImage != null)
 *           { imageView.setImageDrawable(cacheImage); } }
 * 
 *           private ArrayList<String> getUrl() {
 *           ArrayList<NavigationBaseEntity> entity = DataUtil
 *           .getNavBrandData(getBaseContext()); for (int i = 0; i <
 *           entity.size(); i++) { mDatas.add(entity.get(i).getIcon()); } return
 *           mDatas; }
 * 
 *           protected Drawable loadImageFromUrl(final String imageUrl) {
 * 
 *           try { // InputStream is = new URL(imageUrl).openStream(); return
 *           Drawable .createFromStream(new URL(imageUrl).openStream(), ""); }
 *           catch (Exception e) { e.printStackTrace(); throw new
 *           RuntimeException(); }
 * 
 *           }
 * 
 *           private ArrayList<Drawable> getDrawable(ArrayList<String> list) {
 *           ArrayList<Drawable> mList = new ArrayList<Drawable>(); for (int i =
 *           0; i < list.size(); i++) {
 *           mList.add(loadImageFromUrl(list.get(i))); }
 * 
 *           return mList; }
 * 
 *           class ChildThread extends Thread {
 * 
 *           private static final String CHILD_TAG = "ChildThread";
 * 
 *           public void run() {
 * 
 *           // this.setName("ChildThread"); //Looper.prepare(); //
 *           初始化消息循环队列，需要在Handler创建之前 //mChildHandler = new Handler() {
 * 
 *           //@Override //public void handleMessage(Message msg) { mDrawale =
 *           getDrawable(getUrl());// 在子线程中可以做一些耗时的工作
 *           //Log.e("<<<<<<<<<<<<<<<<<<", "load"); //Message toMain =
 *           mMainHandler.obtainMessage(); //toMain.obj = "OK";
 *           //mMainHandler.sendMessage(toMain); mMainHandler.obtainMessage(1,
 *           mDrawale).sendToTarget(); // Log.i(CHILD_TAG, //
 *           "Send a message to the main thread - " + // (String)toMain.obj);
 *           //} //}; Log.i(CHILD_TAG, "Child handler is bound to - " +
 *           mChildHandler.getLooper().getThread().getName()); // 启动子线程消息循环队列
 *           //Looper.loop();
 * 
 *           }
 * 
 *           }
 * 
 *           public void onDestroy() { super.onDestroy();
 *           mChildHandler.getLooper().quit();
 * 
 *           } }
 */
