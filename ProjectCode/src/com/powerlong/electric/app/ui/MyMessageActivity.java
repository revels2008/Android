/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * NotificationActivity.java
 * 
 * 2013年9月7日-上午10:54:31
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.MyMessageAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.ChatMsgListEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.ToastUtil;

/**
 * 
 * MyMessageActivity
 * 
 * @author: YangCheng Miao 2013年9月7日-上午10:54:31
 * 
 * @version 1.0.0
 * 
 */
public class MyMessageActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener {
	ImageView ivReturn;
	ImageView ivEdit;
	TextView tvTitle;

	ListView mListView = null;
	MyMessageAdapter mAdapter = null;
	private int mCurPage = 1;

	TextView  tvTitleTips = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_message);
		
		findView();
		getData();
	}
	
	

	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseActivity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
	
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
			dismissLoadingDialog();
		}
	};

	/**
	 * 从服务器拉取数据
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void getData() {
		if(!DataUtil.isUserDataExisted(getBaseContext())){
			showCustomToast("很抱歉,您尚未登录,无法与对方交谈.");
			 IntentUtil.start_activity(this, LoginActivity.class);
			finish();
			return;
		}
		
		showLoadingDialog(null);
		JSONObject obj = new JSONObject();
		try {
			obj.put("mall", Constants.mallId);
			obj.put("page", mCurPage + "");
			obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
			DataUtil.queryMyMessageListData(getBaseContext(),
					mServerConnectionHandler, obj.toString());
		} catch (JSONException e) {
			dismissLoadingDialog();
			e.printStackTrace();
		}
	}

	/**
	 * 刷新列表
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	protected void updateView() {
		if(DataCache.MyMsgListCache.size()==0){
			tvTitleTips.setVisibility(View.VISIBLE);
		}else{
			tvTitleTips.setVisibility(View.GONE);
			mAdapter.notifyDataSetChanged();
		}
	}

	private void findView() {
		ivReturn = (ImageView) findViewById(R.id.ivBack);
		ivReturn.setOnClickListener(this);
		ivEdit = (ImageView) findViewById(R.id.ivEdit);
		ivEdit.setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitle.setText("消息");
		
		tvTitleTips = (TextView)findViewById(R.id.nomatchedtips);
		tvTitleTips.setText(String.format(Html.fromHtml(getResources().getString(R.string.str_blank_list)).toString(),"消息"));
		tvTitleTips.setVisibility(View.GONE);
		
		mListView = (ListView) findViewById(R.id.lvMessage);
		mAdapter = new MyMessageAdapter(this);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivBack:
			finish();
			break;

		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		ChatMsgListEntity entity = DataCache.MyMsgListCache.get(arg2);
		if(entity!=null){
			String shopId = entity.getShopId()+"";
			BasicNameValuePair name = new BasicNameValuePair("shopName", entity.getSender());
			BasicNameValuePair id = new BasicNameValuePair("shopId", shopId);
			IntentUtil.start_activity(MyMessageActivity.this, ChatActivity.class,id,name);
		}
	}

}
