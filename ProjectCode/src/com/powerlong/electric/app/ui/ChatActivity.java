package com.powerlong.electric.app.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.ChatMsgViewAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.ChatMsgEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;

/**
 * 聊天Activity
 * 
 * @author way
 */
public class ChatActivity extends BaseActivity implements OnClickListener {
	private ImageButton mBtnSend;// 发送btn
	private ImageView mBtnBack;// 返回btn
	private EditText mEditTextContent;
	private TextView mFriendName;
	private PullToRefreshListView mListView;
	TextView tvTitle;
	private ChatMsgViewAdapter mAdapter;// 消息视图的Adapter
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();// 消息对象数组
	private int mCurPage = 1;
	private String shopId;
	private String chatTo="";
	private boolean isRefresh = true;
	private String shopName;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		
		Intent intent = getIntent();
		shopId = intent.getStringExtra("shopId");
		chatTo = intent.getStringExtra("sender");
		shopName = intent.getStringExtra("shopName");
		
		initView();// 初始化view
		initData();// 初始化数据
	}

	/**
	 * 初始化view
	 */
	public void initView() {
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		if(StringUtil.isEmpty(shopName)) {
			tvTitle.setText("消息内容");
		} else {
			tvTitle.setText(shopName);
		}
		
		mListView = (PullToRefreshListView) findViewById(R.id.listview);
		mBtnSend = (ImageButton) findViewById(R.id.chat_add);
		mBtnSend.setOnClickListener(this);
		mBtnBack = (ImageView) findViewById(R.id.ivBack);
		mBtnBack.setOnClickListener(this);
		mFriendName = (TextView) findViewById(R.id.tvTitle);
		mEditTextContent = (EditText) findViewById(R.id.chat_editmessage);
		mListView.setMode(Mode.BOTH);
		mListView.setOnRefreshListener(new OnRefreshListener2<ListView>(){
			
			//下拉刷新获取第一页
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				mCurPage++;
				isRefresh=false;
				initData();
			}

			//上拉加载
			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				mCurPage = 1;
				mDataArrays.clear();
				isRefresh=true;
				initData();
			}});
	}


	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("ChatActivity", "msg.what = " + msg.what);
			LogUtil.d("ChatActivity", "msg.arg1 = " + msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				updateView();
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String) msg.obj;
//				Toast.makeText(ChatActivity.this, fail, Toast.LENGTH_LONG)
//						.show();
//				showCustomToast(fail);
				break;
			}
			dismissLoadingDialog();
			mListView.onRefreshComplete();
		}
	};
	
	/**
	 * 
	 */
	/**
	 * 刷新列表
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	protected void updateView() {
//		if(DataCache.MyMsgListCache.size()==0){
//			
//		}else{
			List<ChatMsgEntity> list = DataCache.MyMsgDetailCache;
			if(isRefresh){
				Collections.reverse(list);
			}
			if (list.size() > 0) {
				for (ChatMsgEntity entity : list) {
					LogUtil.d("ChatActivity", "entity.getContent() = " + entity.getContent());
					/*if (entity.getName().equals("")) {
						entity.setName(user.getName());
					}
					if (entity.getImg() < 0) {
						entity.setImg(user.getImg());
					}*/
					if(isRefresh){
						mDataArrays.add(entity);
					}else{
						mDataArrays.add(0, entity);
					}
				}
				//Collections.reverse(mDataArrays);
			}
			mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
			mListView.setAdapter(mAdapter);
			mListView.getRefreshableView().setSelection(mAdapter.getCount() - 1);
		}
//	}
	
	/**
	 * 加载消息历史，从数据库中读出
	 */
	public void initData() {
		/*List<ChatMsgEntity> list = messageDB.getMsg(user.getId());
		if (list.size() > 0) {
			for (ChatMsgEntity entity : list) {
				if (entity.getName().equals("")) {
					entity.setName(user.getName());
				}
				if (entity.getImg() < 0) {
					entity.setImg(user.getImg());
				}
				mDataArrays.add(entity);
			}
			Collections.reverse(mDataArrays);
		}
		mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
		mListView.setSelection(mAdapter.getCount() - 1);*/
		if(!DataUtil.isUserDataExisted(getBaseContext())){
			showCustomToast("很抱歉,您尚未登录,无法与对方交谈.");
			IntentUtil.start_activity(this, LoginActivity.class);
			finish();
			return;
		}
		
		showLoadingDialog(null);
		JSONObject obj = new JSONObject();
		try {
			obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
			obj.put("page", mCurPage+"");
			obj.put("shopId", shopId);
			obj.put("mall", Constants.mallId);
			DataUtil.queryMyMessageDetail(getBaseContext(), mServerConnectionHandler, obj.toString());
		} catch (JSONException e) {
			dismissLoadingDialog();
			e.printStackTrace();
		}
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.chat_add:// 发送按钮点击事件
			send();
			mEditTextContent.setText("");
			break;
		case R.id.ivBack:// 返回按钮点击事件
			finish();// 结束,实际开发中，可以返回主界面
			break;
		}
	}

	/**
	 * 发送消息
	 */
	private void send() {
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("mall", Constants.mallId);
				obj.put("content", contString);
				obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
				obj.put("shopId", shopId);
				HttpUtil.requestEditProfile(getBaseContext(), obj.toString(), mServerConnectionHandler);
			} catch (JSONException e) {
				dismissLoadingDialog();
				e.printStackTrace();
			}
			HttpUtil.sendMessage(getBaseContext(), obj.toString(), mSendMessageHandler);
			SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss     ");     
			Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间     
			String   str   =   formatter.format(curDate);     

			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setContent(contString);
			entity.setType(0);
			entity.setCreateDate(str);
			entity.setSender(Constants.nickName);
			mDataArrays.add(entity);
			mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
			
			mListView.setAdapter(mAdapter);
			mListView.getRefreshableView().setSelection(mListView.getRefreshableView().getBottom());
			mAdapter.notifyDataSetChanged();
			
		}
	}
	
	private ServerConnectionHandler mSendMessageHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("FloorDetailActivity", "msg.what = " + msg.what);
			LogUtil.d("FloorDetailActivity", "msg.arg1 = " + msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
//				updateView();
				mEditTextContent.setText("");
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				
//				String fail = (String)msg.obj;
//				showCustomToast(fail);
				break;
			}
			dismissLoadingDialog();
		}

	};
}