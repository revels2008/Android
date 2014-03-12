/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * HomeFragment.java
 * 
 * 2013-7-27-下午04:35:42
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import org.json.JSONException;
import org.json.JSONObject;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.BannerAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseFragment;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.ImageDownloadHandler;
import com.powerlong.electric.app.utils.JSONParser;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.widget.CircleFlowIndicator;
import com.powerlong.electric.app.widget.ElasticScrollView;
import com.powerlong.electric.app.widget.ElasticScrollView.OnRefreshListener;
import com.powerlong.electric.app.widget.GridLayout;
import com.powerlong.electric.app.widget.PlBannerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

/**
 * 
 * HomeFragment
 * 
 * @author: Liang Wang 2013-7-27-下午04:35:42
 * 
 * @version 1.0.0
 * 
 */
@SuppressLint("ValidFragment")
public class HomeFragment extends BaseFragment {
	private ElasticScrollView waterfall_scroll;
	private PlBannerLayout plBannerLayout = null;
	private GridLayout  gridLayout = null;
	
	private BannerAdapter mBannerAdapter = null;
	private CircleFlowIndicator flowIndicator = null;
	private boolean isRefresh = false;
	private ImageDownloadHandler mDownloadHandler;
	
	public HomeFragment() {
		super();
	}

	public HomeFragment(Application application, Activity activity,
			Context context) {
		super(application, activity, context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.home_fragment_layout_new, container,
				false);
		mDownloadHandler = new ImageDownloadHandler(getActivity());
//		mView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT*));
		//InitLayout(view);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseFragment#initViews()
	 */
	@Override
	protected void initViews() {
		waterfall_scroll = (ElasticScrollView) findViewById(R.id.waterfall_scroll);
		plBannerLayout = (PlBannerLayout)waterfall_scroll.findViewById(R.id.banner);
		flowIndicator = (CircleFlowIndicator) waterfall_scroll.findViewById(R.id.viewflowindic);
		plBannerLayout.setFlowIndicator(flowIndicator);
		flowIndicator.setVisibility(View.GONE);
		waterfall_scroll.setView(plBannerLayout);
		gridLayout = (GridLayout)waterfall_scroll.findViewById(R.id.banner_recommand_layout);
		mDownloadHandler.setLoadingImage(R.drawable.home_full);
		gridLayout.setmDownloadHandler(mDownloadHandler);
		mBannerAdapter = new BannerAdapter(getActivity());
		plBannerLayout.setAdapter(mBannerAdapter);
	}

	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseFragment#initEvents()
	 */
	@Override
	protected void initEvents() {
		if(mMallChangeListener.getState() == Thread.State.NEW){
			mMallChangeListener.start();
		}
		waterfall_scroll.setonRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				Thread thread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							/*DataCache.RecommendCache.clear();
				      		DataCache.BannerCache.clear();
				      		mBannerAdapter.notifyDataSetChanged();
				      		gridLayout.createRecommandView();*/
							isRefresh = true;
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Message message = handler.obtainMessage(0, "new Text");
						handler.sendMessage(message);
					}
				});
				thread.start();
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseFragment#init()
	 */
	@Override
	protected void init() {
		isRefresh = false;
		getData();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		if(plBannerLayout!=null && DataCache.BannerCache.size()!=0){
			plBannerLayout.setmSideBuffer(DataCache.BannerCache.size());
			plBannerLayout.setTimeSpan(2500);
			plBannerLayout.setSelection(0*1000);
			plBannerLayout.startAutoFlowTimer();
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(plBannerLayout!=null)
			plBannerLayout.stopAutoFlowTimer();
	}


	final Handler handler = new Handler() {
      	public void handleMessage(Message message) {
			getData();
      	}
      };
      
    Thread mMallChangeListener = new Thread(){
    	public void run() {
    		while(true){
    			if(Constants.isMallChanged){
    				isRefresh = true;
    				Message message = handler.obtainMessage(0, "new Text");
					handler.sendMessage(message);
					Constants.isMallChanged = false;
    			}
    			try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}	
    	};
    };
      
    protected void OnReceiveData(/*String str*/) {
  		waterfall_scroll.onRefreshComplete();
  	}
    
    private void updateView(boolean isRefresh){
    	waterfall_scroll.onRefreshComplete();
    	LogUtil.d("HomeFargmentLatest", "updateView isRefresh = "+isRefresh);
    	if(isRefresh){
    		LogUtil.d("HomeFargmentLatest", "updateView step0");
    		isRefresh = false;
    		//mBannerAdapter.notifyDataSetChanged();
    		gridLayout.removeAllViews();
    		gridLayout.createRecommandView();
    		plBannerLayout.setmSideBuffer(DataCache.BannerCache.size());
			plBannerLayout.setTimeSpan(2500);
			plBannerLayout.setSelection(0*1000);	//设置初始位置
			plBannerLayout.startAutoFlowTimer();  //启动自动播放
			
    	}else{
    		LogUtil.d("HomeFargmentLatest", "updateView step1");
    		flowIndicator.setVisibility(View.VISIBLE);
    		//mBannerAdapter.notifyDataSetChanged();
			//plBannerLayout.setAdapter(mBannerAdapter);
			plBannerLayout.setmSideBuffer(DataCache.BannerCache.size());
			plBannerLayout.setTimeSpan(2500);
			plBannerLayout.setSelection(0*1000);	//设置初始位置
			plBannerLayout.startAutoFlowTimer();  //启动自动播放
			LogUtil.d("HomeFargmentLatest", "updateView step2");
			gridLayout.createRecommandView();
			LogUtil.d("HomeFargmentLatest", "updateView step3");
    	}
    }
      
	/**
	 * getDataTest(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void getData() {
		showLoadingDialog(null);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				JSONObject param = new JSONObject();
				try {
					param.put("mall", Constants.mallId);
					param.put("channelCode", Constants.channelCode);
					param.put("platform", Constants.platform);
					LogUtil.d("HomeFargmentLatest", "getData + isRefresh = "+isRefresh);
					DataUtil.queryAdvertisementData(getActivity(), mServerConnectionHandler, param.toString(),isRefresh);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}
	
	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler(){

		@Override
		public void handleMessage(Message msg) {
			
			LogUtil.d("HomeFargmentLatest", "msg.what = "+msg.what);
			LogUtil.d("HomeFargmentLatest", "msg.arg1 = "+msg.arg1);
			dismissLoadingDialog();
			switch(msg.what){
			case Constants.HttpStatus.SUCCESS:
				updateView(isRefresh);
				/*new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						getActivity().runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								flowIndicator.setVisibility(View.VISIBLE);
								plBannerLayout.setAdapter(mBannerAdapter);
								plBannerLayout.setmSideBuffer(3);
								plBannerLayout.setTimeSpan(2500);
								plBannerLayout.setSelection(1*1000);	//设置初始位置
								plBannerLayout.startAutoFlowTimer();  //启动自动播放
								
								
								new Handler().postDelayed(new Runnable() {
									
									@Override
									public void run() {
										gridLayout.createRecommandView();
									}
								}, 500);
							//}
						});
						
					}
				}).start();*/
				
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				waterfall_scroll.onRefreshComplete();
				String fail = (String)msg.obj;
				showCustomToast(fail);
				break;
			}
		}
	};
}
