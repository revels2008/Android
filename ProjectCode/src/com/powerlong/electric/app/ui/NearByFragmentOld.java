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

import java.util.ArrayList;
import java.util.List;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.ImageAdapter;
import com.powerlong.electric.app.adapter.ReflectingImageAdapter;
import com.powerlong.electric.app.adapter.ResourceImageAdapter;
import com.powerlong.electric.app.adapter.ShopListAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.FloorDetailEntity;
import com.powerlong.electric.app.entity.NavigationFloorDetailsEntity;
import com.powerlong.electric.app.entity.NearbyShopEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.listener.PlWebViewLoadingListener;
import com.powerlong.electric.app.ui.base.BaseFragment;
import com.powerlong.electric.app.utils.AsyncImageLoader;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.GalleryFlow;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.PopupWindowUtil;
import com.powerlong.electric.app.utils.ToastUtil;
import com.powerlong.electric.app.widget.CoverFlow;
import com.powerlong.electric.app.widget.PlWebView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

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
public class NearByFragmentOld extends BaseFragment implements OnClickListener,
		PlWebViewLoadingListener, OnGestureListener {
	private ImageView imgMore;
	private Button btnMap;
	private GestureDetector detector;
	private List<String> dataArray = null;
	private LayoutInflater mInflater=null;
	private com.powerlong.electric.app.utils.AsyncImageLoader mAsyncImageLoader;
	private ViewFlipper flipper;
	private  View convertView=null;
	private int currentPage=0;
	private LinearLayout ll;
	private int screenHeight;
	int index;
	
	public NearByFragmentOld() {
		super();
	}

	public NearByFragmentOld(Application application, Activity activity,
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
		mView = inflater.inflate(R.layout.item_pic_viewflipper, container,
				false);    
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.powerlong.electric.app.ui.base.BaseFragment#initViews()
	 */
	@Override
	protected void initViews() {
		if(mActivity!=null)
			btnMap = (Button) mActivity.findViewById(R.id.btn_map);
		detector = new GestureDetector(this);
		flipper = (ViewFlipper) this.findViewById(R.id.viewFlipper); 
//		flipper.addView(getImageView(R.drawable.test01));
//		flipper.addView(getImageView(R.drawable.test02));
//		flipper.addView(getImageView(R.drawable.test03));
//		flipper.addView(getImageView(R.drawable.test04));
		
		WindowManager wm = getActivity().getWindowManager();
		screenHeight = wm.getDefaultDisplay().getHeight();
	}
	private View getImageView(int id){
	     ImageView imgView = new ImageView(getActivity());
	     imgView.setImageResource(id);
	     return imgView;
	    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.powerlong.electric.app.ui.base.BaseFragment#initEvents()
	 */
	@Override
	protected void initEvents() {
		if(btnMap!=null){
			btnMap.setOnClickListener(this);
			btnMap.setVisibility(View.VISIBLE);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.powerlong.electric.app.ui.base.BaseFragment#init()
	 */
	@Override
	protected void init() {
//		showLoadingDialog("正在加载,请稍后...");
//		mPlWebview
//				.loadUrl("http://192.168.171.112:8080/locate_service/location/search.htm?data={%22mall%22:%221%22,%22mac%22:%221%22}");
//		getData();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		final CoverFlow reflectingCoverFlow = (CoverFlow) findViewById(this.getResources().getIdentifier(
                "coverflowReflect", "id", "com.powerlong.electric.app"));
//		setupCoverFlow(reflectingCoverFlow, false);
	}

	private PopupWindowUtil mPopView = null;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			Message msg = new Message();
			String result = data.getStringExtra("qrcode");
			dismissLoadingDialog();
			showCustomToast(result);
		}
		if (resultCode == Activity.RESULT_CANCELED) {
			dismissLoadingDialog();
			showCustomToast("no result");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.imageview_above_more:
			List<String> data = new ArrayList<String>();
			data.add("宝贝");
			data.add("商铺");
			mPopView = new PopupWindowUtil();
			mPopView.showActionWindow(view, getActivity(), data);
			break;
		case R.id.btnScan:
			Intent intent = new Intent("android.intent.action.CAPTURE");
			startActivityForResult(intent, 0);
			break;
		case R.id.btn_map:
			IntentUtil.start_activity(getActivity(), NearbyMapActivity.class);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.powerlong.electric.app.listener.PlWebViewLoadingListener#onLoadingSucced
	 * ()
	 */
	@Override
	public void onLoadingSucced() {
		dismissLoadingDialog();
		showCustomToast("加载成功");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.powerlong.electric.app.listener.PlWebViewLoadingListener#onLoadingFailed
	 * ()
	 */
	@Override
	public void onLoadingFailed() {
		dismissLoadingDialog();
		showCustomToast("加载失败");
	}
	
	private void getData() {
//		setProgressBarVisibility(true);
		int mall = Constants.mallId;
//		DataUtil.queryNearbyListData(getActivity().getBaseContext(), mServerConnectionHandler, mall, "1");
	}

	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("FloorDetailActivity", "msg.what = " + msg.what);
			LogUtil.d("FloorDetailActivity", "msg.arg1 = " + msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				updateView();
				
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
	 * updateView(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @param navId
	 * @exception
	 * @since 1.0.0
	 */
	protected void updateView() {
		final ArrayList<NearbyShopEntity> entities = DataCache.NearbyShopCache;
		NearbyShopEntity entity;
		List<FloorDetailEntity> list = new ArrayList<FloorDetailEntity>();

		for (int i = 0; i < entities.size(); i++) {
			entity = entities.get(i);
			list.add(new FloorDetailEntity(entity.getPicturePath(), entity.getShopName(), entity.getBrief(), 
					Float.parseFloat(entity.getEvaluation()+""), entity.getClassification(), entity.getFavourNum()+""));
		}
		LogUtil.d("BrandActivity", "list szie = " + list.size());
	}
	
	/**
     * Setup cover flow.
     * 
     * @param mCoverFlow
     *            the m cover flow
     * @param reflect
     *            the reflect
     */
    private void setupCoverFlow(final CoverFlow mCoverFlow, final boolean reflect) {
        BaseAdapter coverImageAdapter;
        if (reflect) {
            coverImageAdapter = new ReflectingImageAdapter(new ResourceImageAdapter(mActivity.getBaseContext()));
        } else {
            coverImageAdapter = new ResourceImageAdapter(mActivity.getBaseContext());
        }
        mCoverFlow.setAdapter(coverImageAdapter);
        mCoverFlow.setSelection(0, true);
        setupListeners(mCoverFlow);
    }

    /**
     * Sets the up listeners.
     * 
     * @param mCoverFlow
     *            the new up listeners
     */
    private void setupListeners(final CoverFlow mCoverFlow) {
        mCoverFlow.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView< ? > parent, final View view, final int position, final long id) {
            }

        });
        mCoverFlow.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView< ? > parent, final View view, final int position, final long id) {
              
            }

            @Override
            public void onNothingSelected(final AdapterView< ? > parent) {
                
            }
        });
    }

	/* (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onDown(android.view.MotionEvent)
	 */
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//     return detector.onTouchEvent(event);
//    }
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onFling(android.view.MotionEvent, android.view.MotionEvent, float, float)
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if(e1.getX() - e2.getX() > 120){//向右滑动
			   flipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in));
			   flipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_out));
			   flipper.showNext();
			  }else if(e2.getX() - e1.getX() > 120){//向左滑动
			   flipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_in));
			   flipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_out));
			   flipper.showPrevious();
			  }
		return false;
	}

	/* (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onLongPress(android.view.MotionEvent)
	 */
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onScroll(android.view.MotionEvent, android.view.MotionEvent, float, float)
	 */
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onShowPress(android.view.MotionEvent)
	 */
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onSingleTapUp(android.view.MotionEvent)
	 */
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onDown(android.view.MotionEvent)
	 */
	 
}
