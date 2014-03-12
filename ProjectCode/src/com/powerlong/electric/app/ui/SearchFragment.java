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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.SearchListAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.config.Constants.FilterType;
import com.powerlong.electric.app.config.Constants.ListType;
import com.powerlong.electric.app.entity.SearchCategoryDetailEntity;
import com.powerlong.electric.app.entity.SearchCategoryEntity;
import com.powerlong.electric.app.entity.SearchEntity;
import com.powerlong.electric.app.entity.SearchHistoryEntity;
import com.powerlong.electric.app.entity.SearchResultEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseFragment;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;

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
public class SearchFragment extends BaseFragment implements OnEditorActionListener,
		OnFocusChangeListener, OnClickListener, TextWatcher, OnItemClickListener {

	InputMethodManager imm = null;
	private EditText evSearch = null;
	private Button btnCancel = null;
	private ImageView ivClose = null;
	private PullToRefreshListView mSearchList = null;
	private LinearLayout llClear = null;
	@SuppressWarnings("rawtypes")
	private SearchListAdapter mAdapter = null;
	
	private LinearLayout llSearch = null;
	private ImageView	ivReturn = null;
	private TextView 	tvTitle = null;
	
	private int iCurListType = ListType.NORMAL;
	private String cur_strdetaillist = "";
	
	public SearchFragment() {
		super();
	}

	public SearchFragment(Application application, Activity activity,
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
		mView  = inflater.inflate(R.layout.search_fragment_layout,
				container, false);
		if(mActivity!=null)
			imm = (InputMethodManager) mActivity.getSystemService(
					Context.INPUT_METHOD_SERVICE);

		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseFragment#initViews()
	 */
	@Override
	protected void initViews() {
		evSearch = (EditText) mActivity.findViewById(R.id.search_edit);
		btnCancel = (Button) mActivity.findViewById(R.id.search_cancel);
		ivClose = (ImageView) mActivity.findViewById(R.id.close);
		mSearchList = (PullToRefreshListView) findViewById(R.id.searchlist);
		mSearchList.setMode(Mode.PULL_DOWN_TO_REFRESH);
		llClear = (LinearLayout) findViewById(R.id.llclear);
		llSearch = (LinearLayout)mActivity.findViewById(R.id.llsearch);
		ivReturn = (ImageView)mActivity.findViewById(R.id.back);
		tvTitle = (TextView)mActivity.findViewById(R.id.above_textview_title);
	}

	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseFragment#initEvents()
	 */
	@Override
	protected void initEvents() {
		evSearch.setOnFocusChangeListener(this);
		evSearch.setOnEditorActionListener(this);
		evSearch.addTextChangedListener(this);
		btnCancel.setOnClickListener(this);
		ivClose.setOnClickListener(this);
		llClear.setOnClickListener(this);
		ivReturn.setOnClickListener(this);
		mSearchList.setOnItemClickListener(this);
		mSearchList.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(SearchFragment.this.getActivity().getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				getData();
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseFragment#init()
	 */
	@Override
	protected void init() {
		Resources res = getResources();
		String[] title = res.getStringArray(R.array.search_fragment_listitem_tile);
		String[] summary = res
				.getStringArray(R.array.search_fragment_listitem_summary);
		int[] icon = new int[] { R.drawable.search_listdress, R.drawable.search_listbag,
				R.drawable.search_listfood, R.drawable.search_listfruit, R.drawable.search_listmeat,
				R.drawable.search_listseafood, R.drawable.search_listfigure };
		// mSearchCategoriesCache = new ArrayList<SearchEntity>();
		// mSearchHistroyEntity = new ArrayList<SearchHistoryEntity>();
		// mSearchResultEntity = new ArrayList<SearchResultEntity>();
		mAdapter = new SearchListAdapter<SearchEntity>(mActivity);
		mSearchList.setAdapter(mAdapter);
		//getData();
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
		DataUtil.getSearchCategoriesData(getActivity(), mInitHandler, Constants.mallId);
	}

	private ServerConnectionHandler mInitHandler = new ServerConnectionHandler(){
		@Override
		public void handleMessage(Message msg) {
			LogUtil.d("SearchFragment", "msg.what = " + msg.what);
			dismissLoadingDialog();
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				mAdapter.clear();
				mAdapter.setListType(ListType.NORMAL);
				iCurListType = ListType.NORMAL;
				mAdapter.appendToList(DataCache.SearchCategoriesCache, false);
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
				break;
			}
			mSearchList.onRefreshComplete();
		}
	};
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(DataCache.SearchCategoriesCache.size()<=0){
			DataCache.SearchCategoriesCache.clear();
//			DataCache.SearchCategoryDetailCache.clear();
			getData();
		}
		updateUiView(mAdapter.getListType());
	}

//	private ArrayList<SearchCategoryDetailEntity>  detailEntity = new ArrayList<SearchCategoryDetailEntity>();
	/**
	 * updateUiView(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param listType 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void updateUiView(int listType) {
		//mAdapter.setListType(listType);
		if(listType == ListType.NORMAL){
			mAdapter.appendToList(DataCache.SearchCategoriesCache, true);
			//mSearchList.setAdapter(mAdapter);
			
		}else if(listType == ListType.MATCHED){
			mAdapter.appendToList(DataCache.SearchResultEntity, true);
			//mSearchList.setAdapter(mAdapter);
		}else if(listType == ListType.SEARCHING){
			mAdapter.appendToList(DataCache.SearchHistroyEntity, true);
			//mSearchList.setAdapter(mAdapter);
		}else if(listType == ListType.SUMMARY){
			mAdapter.appendToList(SearchCategoryDetailEntity.convertJson2DetailCategory(cur_strdetaillist), true);
//			mAdapter.appendToList(detailEntity, true);
			//mSearchList.setAdapter(mAdapter);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		super.onPause();
		evSearch.setText("");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.TextView.OnEditorActionListener#onEditorAction(android
	 * .widget.TextView, int, android.view.KeyEvent)
	 */
	@Override
	public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_SEARCH
				|| actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
			imm.toggleSoftInput(InputMethodManager.RESULT_UNCHANGED_SHOWN,
					InputMethodManager.HIDE_NOT_ALWAYS);
			evSearch.clearFocus();
		} else {

		}
		SearchHistoryEntity enitity = new SearchHistoryEntity(FilterType.GOODS,
				evSearch.getText().toString().trim());
		DataCache.SearchHistroyEntity.add(enitity);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.view.View.OnFocusChangeListener#onFocusChange(android.view.View,
	 * boolean)
	 */
	@Override
	public void onFocusChange(View view, boolean flag) {
		if (flag) {
			/*
			 * Animation anim =
			 * AnimationUtils.loadAnimation(mActivity.getBaseContext
			 * (),R.anim.push_left_out); //btnCancel.setAnimation(anim);
			 * //btnCancel.startAnimation(anim); evSearch.setAnimation(anim);
			 * evSearch.startAnimation(anim);
			 */
			btnCancel.setVisibility(View.VISIBLE);
			mAdapter.clear();
			mAdapter.setListType(ListType.SEARCHING);
			iCurListType = ListType.SEARCHING;
			mAdapter.appendToList(DataCache.SearchHistroyEntity, true);
			if (DataCache.SearchHistroyEntity.size() > 0)
				llClear.setVisibility(View.VISIBLE);
		} else {
			btnCancel.setVisibility(View.GONE);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		LogUtil.d(SearchFragment.class.getName(),
				"onClick.. + listtype = " + mAdapter.getListType());
		LogUtil.d(SearchFragment.class.getName(),
				"onClick.. + id = " + view.getId());
		switch (view.getId()) {
		case R.id.search_cancel:
			if (imm.isActive()) {
				imm.toggleSoftInput(InputMethodManager.RESULT_UNCHANGED_SHOWN,
						InputMethodManager.HIDE_NOT_ALWAYS);
				evSearch.clearFocus();
			}

			if (mAdapter.getListType() != ListType.NORMAL) {
				mAdapter.clear();
				mAdapter.setListType(ListType.NORMAL);
				iCurListType = ListType.NORMAL;
				mAdapter.appendToList(DataCache.SearchCategoriesCache, true);
				llClear.setVisibility(View.GONE);
			}
			break;
		case R.id.close:
			evSearch.setText("");
			if(mAdapter.getListType() == 0){
				return;
			}
			
			if (mAdapter.getListType() == ListType.MATCHED) {
				mAdapter.clear();
				mAdapter.setListType(ListType.SEARCHING);
				iCurListType = ListType.SEARCHING;
				mAdapter.appendToList(DataCache.SearchHistroyEntity, true);
				if (DataCache.SearchHistroyEntity.size() > 0)
					llClear.setVisibility(View.VISIBLE);
			}
			ivClose.setVisibility(View.GONE);
			break;
		case R.id.action:
			LogUtil.d(SearchFragment.class.getName(), "action clicked");
			break;
		case R.id.llclear:
			mAdapter.clear();
			DataCache.SearchHistroyEntity.clear();
			mAdapter.setListType(ListType.SEARCHING);
			iCurListType = ListType.SEARCHING;
			mAdapter.appendToList(DataCache.SearchHistroyEntity, true);
			llClear.setVisibility(View.GONE);
			break;
		case R.id.back:
			if (mAdapter.getListType() != ListType.NORMAL) {
				mAdapter.clear();
				mAdapter.setListType(ListType.NORMAL);
				iCurListType = ListType.SEARCHING;
				mAdapter.appendToList(DataCache.SearchCategoriesCache, true);
				ivReturn.setVisibility(View.GONE);
				llSearch.setVisibility(View.VISIBLE);
				mSearchList.setPullToRefreshEnabled(true);
			}
			break;
		}
	}
	
	String[] testString = new String[] { "女人", "男人", "女包", "男包", "女装", "男装" };

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
	 */
	@Override
	public void afterTextChanged(Editable editable) {
		LogUtil.d(SearchFragment.class.getName(),
				"afterTextChanged.. + editable = " + editable);
		if (TextUtils.isEmpty(editable)) {
			ivClose.setVisibility(View.GONE);
		} else {
			ivClose.setVisibility(View.VISIBLE);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.TextWatcher#beforeTextChanged(java.lang.CharSequence,
	 * int, int, int)
	 */
	@Override
	public void beforeTextChanged(CharSequence charsequence, int i, int j, int k) {
		LogUtil.d(SearchFragment.class.getName(),
				"beforeTextChanged.. + charsequence = " + charsequence);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.TextWatcher#onTextChanged(java.lang.CharSequence, int,
	 * int, int)
	 */
	@Override
	public void onTextChanged(CharSequence charsequence, int i, int j, int k) {
		LogUtil.d(SearchFragment.class.getName(),
				"onTextChanged.. + charsequence = " + charsequence);
		if (TextUtils.isEmpty(charsequence)) {
			ivClose.setVisibility(View.GONE);
		} else {
			ivClose.setVisibility(View.VISIBLE);
		}
		
		if(mAdapter.getListType() == ListType.NORMAL)
			return;
		
		if(!StringUtil.isEmpty(charsequence.toString())){
			getMatchedResult(charsequence);
		}else{
			mAdapter.clear();
		}
	}

	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler(){
		@Override
		public void handleMessage(Message msg) {
			LogUtil.d("SearchFragment", "msg.what = " + msg.what);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				mAdapter.clear();
				mAdapter.setListType(ListType.MATCHED);
				iCurListType = ListType.MATCHED;
				mAdapter.appendToList(DataCache.SearchResultEntity, true);
				llClear.setVisibility(View.GONE);
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
				break;
			}

		}
	};
	/**
	 * getMatchedResult(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param charsequence
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private void getMatchedResult(CharSequence charsequence) {
		DataCache.SearchResultEntity.clear();
		String filter = charsequence.toString().trim();
		SearchResultEntity entity = new SearchResultEntity(filter,Constants.FilterType.GOODS);
		DataCache.SearchResultEntity.add(entity);
		entity = new SearchResultEntity(filter,Constants.FilterType.SHOPS);
		DataCache.SearchResultEntity.add(entity);
		
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("mall", Constants.mallId);
			jsonObj.put("classification", "keyword");
			jsonObj.put("keyword", filter);
			DataUtil.getMatchedObjByParams(jsonObj.toString(),Constants.FilterType.ALL, mServerConnectionHandler);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*for (String test : testString) {
			if (test.contains(filter)) {
				entity = new SearchResultEntity(filter);
				DataCache.SearchResultEntity.add(entity);
			}
		}*/
	}

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		int listType = mAdapter.getListType();
		if(listType == ListType.NORMAL){
			llClear.setVisibility(View.GONE);
			SearchCategoryEntity entity = (SearchCategoryEntity)mAdapter.getList().get(pos-1);
//			long pid = entity.getSelfId();
			tvTitle.setText(entity.getName());
			tvTitle.setVisibility(View.VISIBLE);
//			ArrayList<SearchCategoryDetailEntity> entityDetail = DataCache.SearchCategoryDetailCache.get(pid);
			String strdetaillist = entity.getLowerCategoryList();
			if(strdetaillist!=null && !"".equals(strdetaillist)){
//				detailEntity.clear();
////				detailEntity.addAll(DataCache.SearchCategoryDetailCache.get(pid));
				cur_strdetaillist = strdetaillist;
				llSearch.setVisibility(View.GONE);
				ivReturn.setVisibility(View.VISIBLE);
				mAdapter.clear();
				mAdapter.setListType(ListType.SUMMARY);
				iCurListType = ListType.SUMMARY;
				mAdapter.appendToList(SearchCategoryDetailEntity.convertJson2DetailCategory(strdetaillist), true);
				mSearchList.setPullToRefreshEnabled(false);
			}else{
				DataCache.SearchResultEntity.clear();
				String filter = entity.getName();
				LogUtil.d("SearchFragment", "filter = "+filter);
				IntentUtil.startActivity(mActivity, SearchBaseActivityNew.class,entity);
			}
		}else if(listType == ListType.MATCHED){
			SearchResultEntity entity = (SearchResultEntity) mSearchList.getRefreshableView().getAdapter().getItem(pos-1);
//			LogUtil.d("SearchFragment", "type = "+entity.getResultType());
			IntentUtil.startActivity(mActivity, SearchBaseActivityNew.class,entity);
		}else if(listType == ListType.SEARCHING){
			
		}else if(listType == ListType.SUMMARY){
			SearchCategoryDetailEntity entity = (SearchCategoryDetailEntity)mAdapter.getList().get(pos-1);
//			long pid = entity.getSeflId();
//			LogUtil.d("ElectricApp",
//					"ListType.SUMMARY + pid = "
//							+ pid);
//			ArrayList<SearchCategoryDetailEntity> entityDetail = DataCache.SearchCategoryDetailCache.get(pid);
//			LogUtil.d("ElectricApp",
//					"ListType.SUMMARY + entityDetail = "
//							+ entityDetail);
//			if(entityDetail!=null&&entityDetail.size()>0){
//				detailEntity.clear();
//				detailEntity.addAll(entityDetail);
//				tvTitle.setText(entity.getName());
//				tvTitle.setVisibility(View.VISIBLE);
				//DataCache.SearchDetailsEntity.clear();
				//DataCache.SearchDetailsEntity.addAll(enity.getDetails());
//				llSearch.setVisibility(View.GONE);
//				ivReturn.setVisibility(View.VISIBLE);
//				mAdapter.clear();
//				mAdapter.setListType(ListType.SUMMARY);
//				iCurListType = ListType.SUMMARY;
//				mAdapter.appendToList(detailEntity, true);
//			}else{
				DataCache.SearchResultEntity.clear();
				String filter = entity.getName();
				LogUtil.d("SearchFragment", "filter = "+filter);
				IntentUtil.startActivity(mActivity, SearchBaseActivityNew.class,entity);
//			}
		}
	}
}
