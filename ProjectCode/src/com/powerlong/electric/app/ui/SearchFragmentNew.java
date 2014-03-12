/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * SearchFragmentNew.java
 * 
 * 2013-11-5-上午09:17:33
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.dao.SearchHistoryDao;
import com.powerlong.electric.app.domain.DomainSearchCategory;
import com.powerlong.electric.app.domain.DomainSearchHistory;
import com.powerlong.electric.app.ui.adapter.AdapterSearchCategory;
import com.powerlong.electric.app.ui.adapter.AdapterSearchHistory;
import com.powerlong.electric.app.ui.base.BaseFragment;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.ToastUtil;

/**
 * 
 * SearchFragmentNew
 * 
 * @author: hegao
 * 2013-11-5-上午09:17:33
 * 
 * @version 1.0.0
 * 
 */
public class SearchFragmentNew extends BaseFragment {
	private Context context;
	
	@ViewInject(id=R.id.list_category) private PullToRefreshListView listView_category;
	@ViewInject(id=R.id.list_search) private PullToRefreshListView listView_search;
	@ViewInject(id=R.id.ll_search_header) private LinearLayout ll_search_header;
	@ViewInject(id=R.id.search_header_one) private View search_header_one;
	@ViewInject(id=R.id.search_header_two) private View search_header_two;
	@ViewInject(id=R.id.tv_search_word_one) private TextView tv_search_word_one;
	@ViewInject(id=R.id.tv_search_word_two) private TextView tv_search_word_two;
//	@ViewInject(id=R.id.llclear) private LinearLayout ll_llclear; //清除搜索记录
//	@ViewInject(id=R.id.clear) private Button btn_clear;
//	清楚搜索记录，用footView来完成。
	@ViewInject(id=R.id.search_edit) private EditText et_search;
	@ViewInject(id=R.id.search_cancel) private Button btn_cancel; //取消搜索。
	@ViewInject(id=R.id.close) private ImageView img_clear; //清除搜索内容。
	private TextView tv_title;
	private ImageView img_back;
//	private FragmentTransaction ft;
	private FragmentManager fragmentManager;
	
	private AdapterSearchCategory<DomainSearchCategory> adapterSearchCategory;
	private AdapterSearchHistory<DomainSearchHistory> adapterSearchHistory;
	private Activity mActivity;

	private LinearLayout llClear;

	private LinearLayout llSearch;
	private View footView;
	
	private SearchHistoryDao searchHistoryDao;
	
	private LayoutInflater layoutInflater;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
		mActivity = activity;
//		FinalActivity.initInjectedView(activity);
		fragmentManager = getFragmentManager();
//		ft = getFragmentManager().beginTransaction();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		layoutInflater = inflater;
		View viewRoot = inflater.inflate(R.layout.search_fragment_layout_new, null);
		FinalActivity.initInjectedView(this, viewRoot);
		return viewRoot;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews();
		initEvents();
		init();
	}
	public void initViews() {
		et_search = (EditText) mActivity.findViewById(R.id.search_edit);
		btn_cancel = (Button) mActivity.findViewById(R.id.search_cancel);
		img_clear = (ImageView) mActivity.findViewById(R.id.close);
		tv_title = (TextView)mActivity.findViewById(R.id.above_textview_title);
		img_back = (ImageView)mActivity.findViewById(R.id.back);
		llClear = (LinearLayout) mActivity.findViewById(R.id.llclear);
		llSearch = (LinearLayout)mActivity.findViewById(R.id.llsearch);
		footView = layoutInflater.inflate(R.layout.adapter_search_footview, null);
		listView_search.getRefreshableView().addFooterView(footView);
		footView.setVisibility(View.GONE);
		listView_category.setVisibility(View.VISIBLE);
		listView_search.setVisibility(View.GONE);
	}

	public void initEvents() {
		footView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				searchHistoryDao.clear();
				adapterSearchHistory.setList(searchHistoryDao.getAll());
				footView.setVisibility(View.GONE);
			}
		});
		search_header_one.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				DomainSearchHistory searchHistory = new DomainSearchHistory();
				searchHistory.setType(Constants.FilterType.GOODS);
				searchHistory.setSearchWord(et_search.getText().toString());
				searchHistoryDao.insert(searchHistory);
				IntentUtil.startActivity(mActivity, SearchBaseActivityNew.class, searchHistory);
			}
		});
		search_header_two.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				DomainSearchHistory searchHistory = new DomainSearchHistory();
				searchHistory.setType(Constants.FilterType.SHOPS);
				searchHistory.setSearchWord(et_search.getText().toString());
				searchHistoryDao.insert(searchHistory);
				IntentUtil.startActivity(mActivity, SearchBaseActivityNew.class, searchHistory);
			}
		});
		img_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tv_title.setVisibility(View.GONE);
				img_back.setVisibility(View.GONE);
				llClear.setVisibility(View.VISIBLE);
				llSearch.setVisibility(View.VISIBLE);
				fragmentManager.popBackStack();
			}
		});
		et_search.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					btn_cancel.setVisibility(View.VISIBLE);
					img_clear.setVisibility(View.VISIBLE);
					listView_category.setVisibility(View.GONE);
					listView_search.setVisibility(View.VISIBLE);
					adapterSearchHistory.setList(searchHistoryDao.getAll());
					if(adapterSearchHistory.getList().size()<1){
						footView.setVisibility(View.GONE);
					}else{
						footView.setVisibility(View.VISIBLE);
					}
				}else{
					et_search.setText("");
					btn_cancel.setVisibility(View.GONE);
					img_clear.setVisibility(View.GONE);
					listView_category.setVisibility(View.VISIBLE);
					listView_search.setVisibility(View.GONE);
				}
			}
		});
		et_search.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View view, int i, KeyEvent keyevent) {
				if(keyevent.getAction() == KeyEvent.ACTION_UP && keyevent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
					DomainSearchHistory searchHistory = new DomainSearchHistory();
					searchHistory.setType(Constants.FilterType.GOODS);
					searchHistory.setSearchWord(et_search.getText().toString());
					searchHistoryDao.insert(searchHistory);
					IntentUtil.startActivity(mActivity, SearchBaseActivityNew.class, searchHistory);
					return true;
				}
				return false;
			}
		});
		et_search.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable editable) {
				String keyword = et_search.getText().toString();
				if(keyword!=null && !"".equals(keyword)){
					ll_search_header.setVisibility(View.VISIBLE);
					tv_search_word_one.setText("搜索  \""+keyword+"\" ");
					tv_search_word_two.setText("搜索  \""+keyword+"\" ");
//					执行搜索。
					try {
						JSONObject jsonObj = new JSONObject();
						jsonObj.put("mall", Constants.mallId);
						jsonObj.put("classification", "keyword");
						jsonObj.put("keyword", keyword);
						AjaxParams ajaxParams = new AjaxParams();
						ajaxParams.put("data", jsonObj.toString());
						FinalHttp finalHttp = new FinalHttp();
						finalHttp.get(Constants.ServerUrl.SEARCH_GET_SEARCH_LIKE, ajaxParams, new AjaxCallBack<String>() {
							@Override
							public void onFailure(Throwable t, int errorNo,
									String strMsg) {
								if(t!=null && strMsg !=null){
//									Toast.makeText(mApplication, strMsg, Toast.LENGTH_SHORT).show();
									ToastUtil.showExceptionTips(getActivity().getBaseContext(), strMsg);
								}
							}
							@Override
							public void onSuccess(String t) {
								try {
									JSONObject jsObj = new JSONObject(t);
									if(0!=jsObj.getInt("code")){
										Toast.makeText(context, jsObj.getString("msg"), Toast.LENGTH_SHORT).show();
									}else{
										String keyList = jsObj.getJSONObject("data").getString("keyList");
										List<DomainSearchHistory> list = DomainSearchHistory.convertJsonToSearchHistory(keyList);
										adapterSearchHistory.setList(list);
									}
								} catch (Exception e) {
//									Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
									ToastUtil.showExceptionTips(getActivity().getBaseContext(), e.getLocalizedMessage());
								}
							}
						});
					} catch (JSONException e) {
						
					}
				}else{
//					显示历史记录
					ll_search_header.setVisibility(View.GONE);
					adapterSearchHistory.setList(searchHistoryDao.getAll());
					if(adapterSearchHistory.getList().size()<1){
						footView.setVisibility(View.GONE);
					}else{
						footView.setVisibility(View.VISIBLE);
					}
				}
			}
			@Override
			public void beforeTextChanged(CharSequence charsequence, int i,
					int j, int k) {
				
			}
			@Override
			public void onTextChanged(CharSequence charsequence, int i, int j,
					int k) {
				
			}});
		img_clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				et_search.setText("");
			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setVisibility(View.GONE);
				et_search.clearFocus();
			}
		});
		listView_category.setMode(Mode.PULL_DOWN_TO_REFRESH);
		listView_category.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				切换适配器？不如开启新的fragment。
				DomainSearchCategory domainSearchCategory = adapterSearchCategory.getDomain(arg2-1);
				String sub_category = domainSearchCategory.getLowerCategoryList();
//				有子类别
				if(sub_category!=null && !"".equals(sub_category)){
					tv_title.setText(domainSearchCategory.getName());
					tv_title.setVisibility(View.VISIBLE);
					img_back.setVisibility(View.VISIBLE);
					llClear.setVisibility(View.GONE);
					llSearch.setVisibility(View.GONE);
//					开启新的fragment显示 子类别。
					FragmentTransaction ft = fragmentManager.beginTransaction();
					Bundle bundle = new Bundle();
					bundle.putString("data", sub_category);
					Fragment searchFragmentSub = new SearchFragmentSubCategory();
					searchFragmentSub.setArguments(bundle);
					ft.replace(R.id.realtabcontent, searchFragmentSub);
					ft.addToBackStack(null);
					ft.commit();
				}
			}
		});
		listView_category.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(mActivity, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				getData();
			}
		});
		listView_search.setMode(Mode.DISABLED);
		listView_search.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				DomainSearchHistory searchHistory = adapterSearchHistory.getDomain(arg2-1);
				if(searchHistory.getType()==0){
					searchHistory.setType(Constants.FilterType.GOODS);
				}
				searchHistoryDao.insert(searchHistory);//数据库中记录该次搜索
//				跳转到相应的界面。
				IntentUtil.startActivity(mActivity, SearchBaseActivityNew.class, searchHistory);
			}
		});
		
	}

	public void init() {
		searchHistoryDao = new SearchHistoryDao(context);
		adapterSearchCategory = new AdapterSearchCategory<DomainSearchCategory>(context);
		listView_category.setAdapter(adapterSearchCategory);
		adapterSearchHistory = new AdapterSearchHistory<DomainSearchHistory>(context);
		listView_search.setAdapter(adapterSearchHistory);
		getData();
	}
	
	private void getData(){
		showLoadingDialog(null);
		FinalHttp finalHttp = new FinalHttp();
		JSONObject jsObj = new JSONObject();
		try {
			jsObj.put("mall", Constants.mallId);
		} catch (Exception e1) {
			throw new RuntimeException(e1.getLocalizedMessage(), e1);
		}
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("data", jsObj.toString());
		finalHttp.get(Constants.ServerUrl.SEARCH_GET_CATEGORIS, ajaxParams, new AjaxCallBack<String>() {
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				if(t!=null && strMsg !=null){
					Toast.makeText(context, strMsg, Toast.LENGTH_SHORT).show();
					listView_category.onRefreshComplete();
					dismissLoadingDialog();
				}
			}
			@Override
			public void onSuccess(String json_category) {
				try {
					JSONObject jsObj = new JSONObject(json_category);
					if(0!=jsObj.getInt("code")){
						Toast.makeText(context, jsObj.getString("msg"), Toast.LENGTH_SHORT).show();
					}else{
						String str = jsObj.getJSONObject("data").getString("navigationList");
						List<DomainSearchCategory> list = DomainSearchCategory.convertJsonToSearCategory(context, str);
						adapterSearchCategory.clearData();
						adapterSearchCategory.addData(list);
						footView.setVisibility(View.GONE);
					}
				} catch (Exception e) {
					Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
				}finally{
					listView_category.onRefreshComplete();
					dismissLoadingDialog();
				}
			}
		});
	}
}
