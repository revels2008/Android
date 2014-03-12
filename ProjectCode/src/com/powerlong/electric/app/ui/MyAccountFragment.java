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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;

import org.apache.http.params.HttpAbstractParamBean;
import org.json.JSONException;
import org.json.JSONObject;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.AccountListAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseFragment;
import com.powerlong.electric.app.utils.Base64Encoder;
import com.powerlong.electric.app.utils.Base64Utility;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.ToastUtil;
import com.powerlong.electric.app.widget.AccountListView;
import com.powerlong.electric.app.widget.BadgeView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
public class MyAccountFragment extends BaseFragment implements OnClickListener, OnItemClickListener {
	Boolean bLogin = false;
	private Button btnLogin = null;
	private Button btnRegister = null;
	
	private AccountListView accountListView = null;
	private RelativeLayout  rlLoginLayout = null;
	private LinearLayout llLayout = null;
	private TextView tvNotify = null;
	private TextView tvMessage = null;
	
	private RelativeLayout rlNotice;
	private RelativeLayout rlMessage;
	private ImageView  ivUserPhoto = null;
	
	public static View itemHeader;
	private TextView tvUserName = null;
	
	private BadgeView  msgBadge = null;
	private BadgeView  notifyBadge = null;
	private LinearLayout llPay;
	private LinearLayout llSend;
	private LinearLayout llReceive;
	private LinearLayout llComplete;
	private TextView tvComplete,tvPay,tvReceive,tvSend;
	
	private FinalBitmap fb;
	private SharedPreferences mSharedPref;
	private byte[] data;
	
	public MyAccountFragment() {
		super();
	}

	public MyAccountFragment(Application application, Activity activity,
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
		mView = inflater.inflate(R.layout.my_account_fragment_layout_new, container,
				false);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseFragment#initViews()
	 */
	@Override
	protected void initViews() {
		btnLogin = (Button)findViewById(R.id.btn_log);
		btnRegister = (Button)findViewById(R.id.btn_reg);
		accountListView = (AccountListView)findViewById(R.id.lv);
		itemHeader = LayoutInflater.from(getActivity()).inflate(
				R.layout.user_info_header, null);
		llLayout = (LinearLayout) itemHeader.findViewById(R.id.profile_card);
		tvNotify = (TextView)itemHeader.findViewById(R.id.notify);
		tvMessage = (TextView)itemHeader.findViewById(R.id.msg);
		rlNotice = (RelativeLayout) itemHeader.findViewById(R.id.notification);
		rlMessage = (RelativeLayout) itemHeader.findViewById(R.id.message);
		ivUserPhoto = (ImageView)itemHeader.findViewById(R.id.userPhoto);
		tvUserName =(TextView)itemHeader.findViewById(R.id.userName);
		msgBadge = (BadgeView)itemHeader.findViewById(R.id.msgBadge);
		msgBadge.setText(Constants.unReadMessageNum+"");

			
		notifyBadge = (BadgeView)itemHeader.findViewById(R.id.notifyBadge);
		rlLoginLayout = (RelativeLayout)findViewById(R.id.invalid_account);
		rlLoginLayout.setVisibility(View.VISIBLE);
		llPay = (LinearLayout) itemHeader.findViewById(R.id.ll_wait_to_pay);
		llPay.setOnClickListener(this);
		llSend = (LinearLayout) itemHeader.findViewById(R.id.ll_wait_to_send);
		llSend.setOnClickListener(this);
		llReceive = (LinearLayout) itemHeader.findViewById(R.id.ll_wait_to_receive);
		llReceive.setOnClickListener(this);
		llComplete = (LinearLayout) itemHeader.findViewById(R.id.ll_complete);
		llComplete.setOnClickListener(this);
		tvPay = (TextView)findViewById(R.id.tv_pay);
		tvSend = (TextView)findViewById(R.id.tv_send);
		tvReceive=(TextView)findViewById(R.id.tv_receive);
		tvComplete=(TextView)findViewById(R.id.tv_complete);
	}

	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseFragment#initEvents()
	 */
	@Override
	protected void initEvents() {
		btnLogin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
		rlMessage.setOnClickListener(this);
		rlNotice.setOnClickListener(this);
		ivUserPhoto.setOnClickListener(this);
		accountListView.setOnItemClickListener(this);
	}

	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseFragment#init()
	 */
	@Override
	protected void init() {
		String[] itemTitle = null;
		mSharedPref = mContext.getSharedPreferences("account_info", Context.MODE_PRIVATE);
		if(Constants.isNeedGroupon){
			itemTitle = getActivity().getResources().getStringArray(R.array.account_listitem_title);
		}else{
			itemTitle = getActivity().getResources().getStringArray(R.array.account_listitem_title_no_groupon);
		}
		List<String> itemTitleList = new ArrayList<String>();
		for(String title:itemTitle){
			itemTitleList.add(title);
		}
		
		AccountListAdapter adapter = new AccountListAdapter(getActivity(), itemTitleList);
		accountListView.addHeaderView(itemHeader);
		accountListView.setMinimumHeight(120);
		accountListView.setAdapter(adapter);
		accountListView.setVisibility(View.GONE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		tvPay = (TextView)findViewById(R.id.tv_pay);
		tvSend = (TextView)findViewById(R.id.tv_send);
		tvReceive=(TextView)findViewById(R.id.tv_receive);
		tvComplete=(TextView)findViewById(R.id.tv_complete);
		tvPay.setText("待付款");
		tvSend.setText("待发货");
		tvReceive.setText("待收货");
		tvComplete.setText("已完成");
		updateView(checkUserData());
	}

	
	/**
	 * 更新界面
	 * @param checkUserData 
	 * @exception 
	 * @since  1.0.0
	*/
	private void updateView(boolean isLogin) {
		LogUtil.d("MyAccountFragment", "isLogin="+isLogin);
		rlLoginLayout.setVisibility(isLogin?View.GONE:View.VISIBLE);
		accountListView.setVisibility(isLogin?View.VISIBLE:View.GONE);
		if(isLogin){
			tvUserName.setText(DataCache.UserDataCache.get(0).getNickname());
			fb = FinalBitmap.create(getActivity());
			fb.configDiskCachePath(Constants.IMG_CACHE_PATH);
		    fb.configLoadfailImage(R.drawable.pic_56);
		    if(mSharedPref.getString(Constants.userId+"", null) != null){
		    	byte[] base64Bytes = Base64Utility.decode(mSharedPref.getString(Constants.userId+"", null));
		    	ivUserPhoto.setImageBitmap(BitmapFactory.decodeByteArray(base64Bytes, 0, base64Bytes.length));
		    }else{
			    fb.display(ivUserPhoto, Constants.userIcon);
			    if(!Constants.userIcon.endsWith("null")){
			    	getNetImage(Constants.userIcon);
			    }
		    }
			getMsgNum();
			getPayNum();
		}
	}

	Handler mHandlerSaveIcon = new Handler(){
		public void handleMessage(Message msg) {
			Editor mEditor = mSharedPref.edit();
		    mEditor.putString(Constants.userId+"", new String(Base64Utility.encode(data)));
		    mEditor.commit();
		};
	};
	
	private void getNetImage(final String url){
		new Thread(){  
            @Override  
            public void run(){  
                //你要执行的方法  
                try {  
                    data = getBytes(new URL(url).openStream());    
                }  
                catch (Exception e) {  
                    e.printStackTrace();  
                }  
                mHandlerSaveIcon.sendEmptyMessage(0);
            }  
        }.start();
	}
	
	public static byte[] getBytes(InputStream is) throws IOException {  
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024]; // 用数据装  
        int len = -1;  
        while ((len = is.read(buffer)) != -1) {  
            outstream.write(buffer, 0, len);  
        }  
        outstream.close();  
        // 关闭流一定要记得。  
        return outstream.toByteArray();  
    }  
	
	/**
	 * getPayNum(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void getPayNum() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
			obj.put("mall", Constants.mallId);
			HttpUtil.queryPayNumMsgJson(getActivity(),
					mServerConnectionHandlerPay, obj.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * getMsgNum(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void getMsgNum() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("mall", Constants.mallId);
			obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
			HttpUtil.queryUnReadMsgJson(getActivity(),
					mServerConnectionHandler, obj.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("MyMessageActivity", "msg.what = " + msg.what);
			LogUtil.d("MyMessageActivity", "msg.arg1 = " + msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				updateView();
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String) msg.obj;
				showCustomToast(fail);
				break;
			}
		}
	};
	
	
	private ServerConnectionHandler mServerConnectionHandlerPay = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("MyMessageActivity", "msg.what = " + msg.what);
			LogUtil.d("MyMessageActivity", "msg.arg1 = " + msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				tvPay.setText(Constants.noPayCount!=0?tvPay.getText().toString()+"("+Constants.noPayCount+")":tvPay.getText().toString());
				tvSend.setText(Constants.waitSendCount!=0?tvSend.getText().toString()+"("+Constants.waitSendCount+")":tvSend.getText().toString());
				tvReceive.setText(Constants.waitRecvCount!=0?tvReceive.getText().toString()+"("+Constants.waitRecvCount+")":tvReceive.getText().toString());
				tvComplete.setText(Constants.finishCount!=0?tvComplete.getText().toString()+"("+Constants.finishCount+")":tvComplete.getText().toString());
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String) msg.obj;
				showCustomToast(fail);
				break;
			}
		}
	};
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		super.onPause();
	}

	/**
	 * updateView(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	protected void updateView() {
		msgBadge.setText(Constants.unReadMessageNum+"");
		if(Constants.unReadMessageNum > 0) {
			msgBadge.setVisibility(View.VISIBLE);
		} else {
			msgBadge.setVisibility(View.GONE);
		}
		notifyBadge.setText(Constants.unReadNotifyNum+"");
		if(Constants.unReadNotifyNum > 0) {
			notifyBadge.setVisibility(View.VISIBLE);
		} else {
			notifyBadge.setVisibility(View.GONE);
		}
	}

	/**
	 * initControls(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void initControls(View view) {
		btnLogin = (Button)view.findViewById(R.id.btn_log);
		btnLogin.setOnClickListener(this);
		
		btnRegister = (Button)view.findViewById(R.id.btn_reg);
		btnRegister.setOnClickListener(this);
		
		accountListView = (AccountListView)view.findViewById(R.id.lv);
		itemHeader = LayoutInflater.from(getActivity()).inflate(
				R.layout.user_info_header, null);
		llLayout = (LinearLayout) itemHeader.findViewById(R.id.profile_card);
		tvNotify = (TextView)itemHeader.findViewById(R.id.notify);
		tvMessage = (TextView)itemHeader.findViewById(R.id.msg);
		rlNotice = (RelativeLayout) itemHeader.findViewById(R.id.notification);
		rlNotice.setOnClickListener(this);
		rlMessage = (RelativeLayout) itemHeader.findViewById(R.id.message);
		rlMessage.setOnClickListener(this);
		
		ivUserPhoto = (ImageView)itemHeader.findViewById(R.id.userPhoto);
		ivUserPhoto.setOnClickListener(this);
		tvUserName =(TextView)itemHeader.findViewById(R.id.userName);
		
		msgBadge = (BadgeView)itemHeader.findViewById(R.id.msgBadge);
		msgBadge.setText(Constants.unReadMessageNum+"");
		notifyBadge = (BadgeView)itemHeader.findViewById(R.id.notifyBadge);
		
		rlLoginLayout = (RelativeLayout)view.findViewById(R.id.invalid_account);
		rlLoginLayout.setVisibility(View.VISIBLE);
		
		String[] itemTitle = getActivity().getResources().getStringArray(R.array.account_listitem_title);
		List<String> itemTitleList = new ArrayList<String>();
		for(String title:itemTitle){
			itemTitleList.add(title);
		}
		
		AccountListAdapter adapter = new AccountListAdapter(getActivity(), itemTitleList);
		
		accountListView.addHeaderView(itemHeader);
		accountListView.setMinimumHeight(120);
		accountListView.setAdapter(adapter);
		accountListView.setOnItemClickListener(this);
		accountListView.setVisibility(View.GONE);
	}

	private List<String> getData() {
		List<String> data = new ArrayList<String>();

		for (int i = 0; i < 10; i++) {

			data.add(i + "");
			data.add("Name:TiejianSha");
			data.add("Email:tntshaka@gmail.com");
		}

		return data;
	}
	/**
	 * checkUserData(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private boolean checkUserData() {
		boolean ret = DataUtil.isUserDataExisted(getActivity());
		LogUtil.d("MyAccountFragment", "ret="+ret);
		return ret;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_log:
			IntentUtil.start_activity(getActivity(), LoginActivity.class);
			break;
		case R.id.btn_reg:
			IntentUtil.start_activity(getActivity(), RegisterActivity.class);
			break;
		case R.id.notification:
			IntentUtil.start_activity(getActivity(), MyNotificationActivity.class);
			break;
		case R.id.message:
			IntentUtil.start_activity(getActivity(), MyMessageActivity.class);
			//msgBadge.setText("0");
			msgBadge.setVisibility(View.GONE);
			break;
		case R.id.userPhoto:
			//IntentUtil.start_activity(getActivity(), LoginActivity.class);
			fb.display(ivUserPhoto, Constants.userIcon);
			if(!Constants.userIcon.endsWith("null")){
				getNetImage(Constants.userIcon);
			}
			break;
		case R.id.ll_wait_to_pay:
			Intent intent = new Intent(getActivity(), MyOrderActivityNew.class);
			intent.putExtra("stat", 0);
			intent.putExtra("from", 1);
			startActivity(intent);
			break;
		case R.id.ll_wait_to_send:
			Intent intent1 = new Intent(getActivity(), MyOrderActivityNew.class);
			intent1.putExtra("stat", 1);
			intent1.putExtra("from", 1);
			startActivity(intent1);
			break;
		case R.id.ll_wait_to_receive:
			Intent intent2 = new Intent(getActivity(), MyOrderActivityNew.class);
			intent2.putExtra("stat", 2);
			intent2.putExtra("from", 1);
			startActivity(intent2);
			break;
		case R.id.ll_complete:
			Intent intent3 = new Intent(getActivity(), MyOrderActivityNew.class);
			intent3.putExtra("stat", 3);
			intent3.putExtra("from", 1);
			startActivity(intent3);
			break;
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
		LogUtil.d("Profile", "position = "+position);
		if(Constants.isNeedGroupon){
			switch(position){

			case 1:
				IntentUtil.start_activity(getActivity(), MyGrouponCouponActivity.class);
				break;
			case 2:
				IntentUtil.start_activity(getActivity(), CashCouponActivity.class);
				break;
			case 3:
				IntentUtil.start_activity(getActivity(), MyFavourActivity.class);
				break;
			case 4:
				IntentUtil.start_activity(getActivity(), SettingsActivity.class);
				break;
			}
		}else{
			switch(position){

			case 1:
				IntentUtil.start_activity(getActivity(), CashCouponActivity.class);
				break;
			case 2:
				IntentUtil.start_activity(getActivity(), MyFavourActivity.class);
				break;
			case 3:
				IntentUtil.start_activity(getActivity(), SettingsActivity.class);
				break;
			}
		}
	}
}
