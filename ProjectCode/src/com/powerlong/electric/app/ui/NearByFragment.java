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
import com.powerlong.electric.app.adapter.ShopListAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.FloorDetailEntity;
import com.powerlong.electric.app.entity.NavigationFloorDetailsEntity;
import com.powerlong.electric.app.entity.NearbyShopEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.listener.PlWebViewLoadingListener;
import com.powerlong.electric.app.ui.base.BaseFragment;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.PopupWindowUtil;
import com.powerlong.electric.app.utils.ToastUtil;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

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
public class NearByFragment extends BaseFragment implements OnClickListener,
		PlWebViewLoadingListener {
	private ImageView imgMore;
	private Button btn;
	private Button btnMap;
	private PlWebView mPlWebview = null;
	private ListView lvNearby;
	private ArrayAdapter<FloorDetailEntity> adapter;
	
	public NearByFragment() {
		super();
	}

	public NearByFragment(Application application, Activity activity,
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
		mView = inflater.inflate(R.layout.nearby_fragment_layout, container,
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
		imgMore = (ImageView) findViewById(R.id.imageview_above_more);

		btn = (Button) findViewById(R.id.btnScan);
		btn.setOnClickListener(this);

		mPlWebview = (PlWebView) findViewById(R.id.webview);
		lvNearby = (ListView) findViewById(R.id.lv_nearby);
		if(mActivity!=null)
			btnMap = (Button) mActivity.findViewById(R.id.btn_map);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.powerlong.electric.app.ui.base.BaseFragment#initEvents()
	 */
	@Override
	protected void initEvents() {
		imgMore.setOnClickListener(this);
		mPlWebview.setWebLoadingListener(this);
		btnMap.setOnClickListener(this);
		btnMap.setVisibility(View.VISIBLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.powerlong.electric.app.ui.base.BaseFragment#init()
	 */
	@Override
	protected void init() {
		showLoadingDialog("正在加载,请稍后...");
//		mPlWebview
//				.loadUrl("http://192.168.171.112:8080/locate_service/location/search.htm?data={%22mall%22:%221%22,%22mac%22:%221%22}");
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
//		FloorDetailAdapterOrg adapter = new FloorDetailAdapterOrg(list,
//				FloorDetailActivity.this);
		adapter = new ShopListAdapter(getActivity(), list, lvNearby);
		adapter.notifyDataSetChanged();
		lvNearby.setAdapter(adapter);
		lvNearby.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				long shopId = entities.get(arg2).getId();
				Intent intent = new Intent(mContext, ShopDetailActivityNew.class);
				intent.putExtra("shopId", shopId);
				Log.e("<<<<<<<<<<<", shopId+"");
				startActivity(intent);
			}
		});
	}

}
