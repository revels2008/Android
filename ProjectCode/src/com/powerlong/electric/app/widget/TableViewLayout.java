/**
 * 宝龙电商
 * com.powerlong.electric.app.widget
 * GridLayout.java
 * 
 * 2013-8-24-上午10:23:45
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.ItemBargainAdapter;
import com.powerlong.electric.app.adapter.SimpleListDialogAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.dialog.ItemsEditDialog;
import com.powerlong.electric.app.dialog.ItemsEditDialog.onItemsClickListener;
import com.powerlong.electric.app.dialog.SimpleListDialog;
import com.powerlong.electric.app.dialog.SimpleListDialog.onSimpleListItemClickListener;
import com.powerlong.electric.app.entity.BarginListEntity;
import com.powerlong.electric.app.entity.CartShopListEntity;
import com.powerlong.electric.app.entity.ItemBargainListEntity;
import com.powerlong.electric.app.entity.ItemListEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.HomeActivityNew;
import com.powerlong.electric.app.utils.AsyncImageLoaderUtil;
import com.powerlong.electric.app.utils.AsyncImageLoaderUtil.ILoadImageCallback;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.DoubleUtil;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.ListViewUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.utils.ToastUtil;

/**
 * 
 * GridLayout:首页广告位视图
 * 
 * @author: Liang Wang 2013-8-24-上午10:23:45
 * 
 * @version 1.0.0
 * 
 */
public class TableViewLayout extends LinearLayout {
	private static final int CELL_MARGIN_DP = 4;
	private LayoutInflater mInflater;
	private Context mContext = null;
	public static double totalPrice = 0;
	private double[] shopPrice;
	private long mShopId = 0;
	private int mOpeIndex = 0;//保存长按的是店铺的第几个商品index
	private int[] shopItems;
	private boolean isAllSelected;
	private int shopIndex;

	private SimpleListDialog mSimpleListDialog = null;
	private ItemsEditDialog mItemsEditDialog = null;
	private String[] mOperation;
	private int mBuyNum = 1;
	private long editShopId, editItemId, editCartId;
	private int editNum, editType, editShopIndex, editItemIndex;
	private int delShopIndex=0; //保存长按的是的是第几家店铺index
	private String oldBuyNum = "0";
	private ImageView mImgDelete;
	private ImageView mImgFavour;
	private String currentTag;
	private int shopTag;
	private ArrayList<String> editTags = new ArrayList<String>();
	/**
	 * 记录批量操作时待操作的商品数量
	 */
	public int waitExecuteNum = 0;
	/**
	 * 记录编辑的相关信息key ： shopId ， value : 商品索引  # 店铺索引
	 */
	private HashMap<Integer, ArrayList<String>> editMap = new HashMap<Integer, ArrayList<String>>();
	
	public String getCurrentTag() {
		return currentTag;
	}

	public void setCurrentTag(String currentTag) {
		this.currentTag = currentTag;
	}

	public int getShopTag() {
		return shopTag;
	}

	public void setShopTag(int shopTag) {
		this.shopTag = shopTag;
	}

	public ImageView getmImgDelete() {
		return mImgDelete;
	}

	public void setmImgDelete(ImageView mImgDelete) {
		this.mImgDelete = mImgDelete;
	}

	public ImageView getmImgFavour() {
		return mImgFavour;
	}

	public void setmImgFavour(ImageView mImgFavour) {
		this.mImgFavour = mImgFavour;
	}

	private LinearLayout mBtnSelectAll;
	
	public LinearLayout getmBtnSelectAll() {
		return mBtnSelectAll;
	}

	public void setmBtnSelectAll(LinearLayout mBtnSelectAll) {
		this.mBtnSelectAll = mBtnSelectAll;
	}

	public TableViewLayout(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public TableViewLayout(Context context, AttributeSet attr) {
		super(context, attr);
		mContext = context;
		init();
	}

	protected void init() {
		mInflater = LayoutInflater.from(getContext());
		createRootView();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onFinishInflate()
	 */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
	}

	/**
	 * 确定整个TableViewLayout为填满父控件，垂直方向
	 */
	private void createRootView() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		setLayoutParams(params);
		setOrientation(VERTICAL);
	}

	CartShopListEntity shopEntity = null;
	ArrayList<ItemListEntity> itemEntitites = null;

	private class HeaderViewHolder {
		CheckBox headCheck;
	}

	public class ItemViewHolder {
		CheckBox itemCheck;
		ImageView itemImage;
		ImageView grouponFlag;
		TextView itemName;
		TextView itemInfo;
		TextView listPrice;
		TextView plPrice;
		TextView buyCount;
		ListView itemList;
		Button mBtnEditNum;
	}

	public void clearAllMap() {
		editMap.clear();
	}
	
	private ItemViewHolder currentViewHolder;
	
	public ItemViewHolder getCurrentViewHolder() {
		return currentViewHolder;
	}

	public void setCurrentViewHolder(ItemViewHolder currentViewHolder) {
		this.currentViewHolder = currentViewHolder;
	}

	private class FooterViewHolder {
		TextView tvBargain;
		TextView tvPrice;
		TextView tvNum;
	}

	HashMap<Integer, HeaderViewHolder> hashHeaderViewHolder = new HashMap<Integer, HeaderViewHolder>();
	HashMap<Integer, ArrayList<ItemViewHolder>> hashItemViewHolder = new HashMap<Integer, ArrayList<ItemViewHolder>>();
	HashMap<Integer, FooterViewHolder> hashFooterViewHolder = new HashMap<Integer, FooterViewHolder>();

	@SuppressWarnings("unchecked")
	public void createTableView() {
		this.removeAllViews();
		waitExecuteNum = 0;
		Context context = getContext();
		LinearLayout shopView = new LinearLayout(context);
		LinearLayout.LayoutParams shopViewParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		shopView.setOrientation(VERTICAL);
		shopView.setBackgroundResource(R.drawable.list_bgwhite_nor);
		shopView.setLayoutParams(shopViewParams);

		float f = Constants.screen_density;
		int k = (int) (CELL_MARGIN_DP * f);

		Log.i("CartFinal", "创建列表时 DataCache.CartShopListCache.size() = " + DataCache.CartShopListCache.size());
		int VIEW_COUNT = DataCache.CartShopListCache.size();
		int curIndex = 0;
		shopItems = new int[VIEW_COUNT];

		while (curIndex < VIEW_COUNT) {
			shopEntity = DataCache.CartShopListCache.get(curIndex);
			final int shopId = shopEntity.getShopId();
			LogUtil.d(VIEW_LOG_TAG, "shopId=" + shopId);
			itemEntitites = DataCache.CartItemListCache.get(shopId);

			RelativeLayout headerView = new RelativeLayout(context);

			/**
			 * 店铺头View
			 */
			final HeaderViewHolder hViewHolder = new HeaderViewHolder();
			headerView = (RelativeLayout) mInflater.inflate(R.layout.tableview_header_layout, null);
			hViewHolder.headCheck = (CheckBox) headerView.findViewById(R.id.checkBoxHeader);
			hViewHolder.headCheck.setText(shopEntity.getShopName()); //店铺名称
			hViewHolder.headCheck.setTag(curIndex); //购物车中第几个店铺
			hViewHolder.headCheck.setChecked(shopEntity.isChecked());
			hViewHolder.headCheck.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					boolean isChecked = hViewHolder.headCheck.isChecked();
					for (ItemListEntity entity : itemEntitites) {
						entity.setChecked(isChecked);
					}
					updateItemViewHolderChecked(view.getTag(), isChecked);
					invalidate();
					updateFooterView(view.getTag());
					if (mHandler != null) {
						mHandler.obtainMessage(0).sendToTarget();
						Message msg = mHandler.obtainMessage();
						Bundle bundle = new Bundle();
						bundle.putDouble("totalPrice", totalPrice);
						bundle.putBoolean("isAllSelected", isAllSelected);
						msg.setData(bundle);
						msg.sendToTarget();

					}
					setCheckBox();
					if (isAllSelected()) {
						mHandler.obtainMessage(2).sendToTarget();
					} else if (isAllNotSelected()) {
						mHandler.obtainMessage(4).sendToTarget();
					} else {
						mHandler.obtainMessage(3).sendToTarget();
					}
				}
			});

			/**
			 * 店铺头map中记录
			 */
			hashHeaderViewHolder.put(curIndex, hViewHolder);

			RelativeLayout.LayoutParams headerViewParams = new RelativeLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			headerViewParams.height = (int) (60 * Constants.displayWidth / 480);
			headerViewParams.setMargins(k, k, k, k);
			headerView.setLayoutParams(headerViewParams);
			headerView.setTag("header" + curIndex); //店铺头设置tag
			shopView.addView(headerView);

			/**
			 * 画分割线
			 */
			ImageView seprator = new ImageView(context);
			seprator.setImageResource(R.drawable.line_full_cart_new);
			shopView.addView(seprator);

			/**
			 * 为每个店铺加载其下的商品信息
			 */
			int childViewCount = itemEntitites.size();
			LogUtil.d("TableViewLayout", "childViewCount = " + childViewCount);
			ArrayList<ItemViewHolder> itemViewHolders = new ArrayList<TableViewLayout.ItemViewHolder>();
			int itemIndex = 0;
			for (final ItemListEntity itemEntity : itemEntitites) {
				Log.i("CartFinal", "商品的名称: " + itemEntity.getItemName() + " , 商品的规格: " + itemEntity.getProp());
				LogUtil.d("TableViewLayout", "itemEntity = " + itemEntity);
				LogUtil.d("TableViewLayout", "itemEntity.checked = " + itemEntity.isChecked());
				final RelativeLayout itemView =  (RelativeLayout) mInflater.inflate(R.layout.tableview_item_layout, null);
				/**
				 * 为单个的商品设置tag，格式：<商品所属店铺Id>~<商品索引>~<商品所属店铺索引>
				 */
				itemView.setTag(shopId + "~" + itemIndex + "~" + curIndex);
				final ItemViewHolder itemViewHolder = new ItemViewHolder();

				final int shopTag = curIndex;
				shopItems[curIndex] = itemIndex;
				
				/**
				 * 单击商品条目跳转至商品详细页面
				 */
				itemView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						final String tag = (String) v.getTag();
						String str[] = tag.split("~");
						final int shopId = StringUtil.toInt(str[0]);
						final int viewIndex = StringUtil.toInt(str[1]);
//						final int shopTagEdit = StringUtil.toInt(str[1]);
						itemEntitites = DataCache.CartItemListCache.get(shopId);
						ItemListEntity entity = itemEntitites.get(viewIndex);
						final long itemId = entity.getItemId();
						final String itemName = entity.getItemName();
						String type = entity.getIsGroupon();

						if (mHandler != null) {
							Message msg2 = mHandler.obtainMessage(6);
							Bundle bundle2 = new Bundle();
							bundle2.putLong("itemId", itemId);
							bundle2.putInt("shopId", shopId);
							bundle2.putString("itemName", itemName);
							bundle2.putString("type", type);
							msg2.setData(bundle2);
							msg2.sendToTarget();
						}
					}
				});

				/**
				 * 长按商品条目触发商品编辑事件
				 */
				itemView.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(View view) {
						final String tag = (String) view.getTag();
						String str[] = tag.split("~");
						final int shopId = StringUtil.toInt(str[0]);
						final int viewIndex = StringUtil.toInt(str[1]);
						shopIndex = StringUtil.toInt(str[2]);
						itemEntitites = DataCache.CartItemListCache.get(shopId);
						ItemListEntity entity = itemEntitites.get(viewIndex);
						
						final long itemId = entity.getItemId();
						final int type = StringUtil.toInt(entity.getIsGroupon());
						final long cartId = DataCache.cartId;
						final String itemName = entity.getItemName();
						
						editShopId = shopId;
						editItemId = itemId;
						editType = type;
						editCartId = cartId;
						editShopIndex = shopIndex;
						editItemIndex = viewIndex;
						LogUtil.d("TableViewLayout","itemEntity longclick at = " + viewIndex);

						mOperation = getResources().getStringArray(R.array.cart_operation);
						mSimpleListDialog = new SimpleListDialog(mContext);
						mSimpleListDialog.setTitle("主题功能");
						mSimpleListDialog.setTitleLineVisibility(View.GONE);
						mSimpleListDialog.setAdapter(new SimpleListDialogAdapter(mContext, mOperation));
						mSimpleListDialog.setOnSimpleListItemClickListener(new onSimpleListItemClickListener() {
									@Override
									public void onItemClick(int position) {
										/**
										 * 加入收藏夹
										 */
										if (position == 0) {
											if(type == 1) {
												ToastUtil.showExceptionTips(mContext, "团购商品不能加入收藏夹");
											} else {
												JSONObject obj = new JSONObject();
												try {
													obj.put("mall", Constants.mallId);
													obj.put("TGC",DataCache.UserDataCache.get(0).getTGC());
													obj.put("id", itemId);
													obj.put("itemName", itemName);
													obj.put("fromType", 0);
													obj.put("cartId", DataCache.cartId);
													delShopIndex = shopIndex; //加入收藏夹商品所在店铺索引
													mShopId = shopId; //加入收藏夹商品所在店铺Id
													mOpeIndex = viewIndex; //购物车范围内商品所在店铺中的索引
													DataUtil.sendUserFavourOperation(mFavourHandler,obj.toString(), 0);
												} catch (JSONException e) {
													e.printStackTrace();
												}
												updateFooterView(shopTag);
												setCheckBox();
												if (mHandler != null) {
													mHandler.obtainMessage(0).sendToTarget();
													Message msg = mHandler.obtainMessage();
													Bundle bundle = new Bundle();
													bundle.putDouble("totalPrice", totalPrice);
													msg.setData(bundle);
													msg.sendToTarget();
												}
												if (isAllSelected()) {
													mHandler.obtainMessage(2).sendToTarget();
												} else if (isAllNotSelected()) {
													mHandler.obtainMessage(4).sendToTarget();
												} else {
													mHandler.obtainMessage(3).sendToTarget();
												}
											}
											
										}
										/**
										 * 编辑商品
										 */
										else if (position == 1) {

											int buyNum = itemEntity.getBuyNum();
											mBuyNum = buyNum;
										
											String strBuyNum = "" + buyNum;
											oldBuyNum = strBuyNum;
											double unitPrice = 0;
											String rebatePrice = itemEntity
													.getplPrice()+"";
											String listPrice = itemEntity
													.getListPrice()+"";
											LogUtil.d("TableViewLayout",
													" listPrice=  " + listPrice);
											LogUtil.d("TableViewLayout",
													" rebatePrice=  "
															+ rebatePrice);
											if (!StringUtil
													.isEmpty(rebatePrice)) {
												unitPrice = StringUtil
														.toDouble(rebatePrice);
											} else {
												unitPrice = StringUtil
														.toDouble(listPrice);
											}
											final double uPrice = unitPrice;
											LogUtil.d("TableViewLayout",
													" uPrice=  " + unitPrice);
											LogUtil.d("TableViewLayout",
													" buyNum=  " + buyNum);

											mItemsEditDialog = new ItemsEditDialog(
													mContext);
											mItemsEditDialog
													.setTitle(R.string.cart_edit_count_title);
											mItemsEditDialog
													.setSubTitle(DoubleUtil.subPrice(DoubleUtil.round(unitPrice
															* buyNum, 3)) + "");
											mItemsEditDialog
													.setEditText(strBuyNum);
											mItemsEditDialog
													.setButton(
															"取消",
															new DialogInterface.OnClickListener() {

																@Override
																public void onClick(
																		DialogInterface dialog,
																		int which) {
																	mItemsEditDialog
																			.cancel();
																}
															},
															"确认",
															new DialogInterface.OnClickListener() {

																@Override
																public void onClick(
																		DialogInterface dialog,
																		int which) {
																	String text = mItemsEditDialog
																			.getText();
																	if (text == null) {
//																		mItemsEditDialog
//																				.requestFocus();

																	} else {
																		mItemsEditDialog.dismiss();
																		itemEntity.setBuyNum(mBuyNum);
																		editNum = mBuyNum;
//																		requestChangeItemNum();
//																		updateFooterView(shopTag);
//																		setCheckBox();
																		if (mHandler != null) {
																			mHandler.obtainMessage(0).sendToTarget();
																			Message msg = mHandler.obtainMessage();
																			Bundle bundle = new Bundle();
																			bundle.putDouble("totalPrice", totalPrice);
																			msg.setData(bundle);
																			msg.sendToTarget();

																		}
																		if (isAllSelected()) {
																			mHandler.obtainMessage(2).sendToTarget();
																		} else if (isAllNotSelected()) {
																			mHandler.obtainMessage(4).sendToTarget();
																		} else {
																			mHandler.obtainMessage(3).sendToTarget();
																		}
																	}
																}
															});

											mItemsEditDialog
													.setOnItemsClickListener(new onItemsClickListener() {

														@Override
														public void onIncreaseClicked(
																int num) {
															mBuyNum = num;
															mItemsEditDialog
																	.setSubTitle(DoubleUtil.round(uPrice
																			* num, 3) + "");
															
														}

														@Override
														public void onDecreaseClicked(
																int num) {
															mBuyNum = num;
															
															mItemsEditDialog
																	.setSubTitle(DoubleUtil.round(uPrice
																			* num, 3) + "");
															
														}
													});
											mItemsEditDialog.show();
										} 
										/**
										 * 删除商品
										 */
										else if (position == 2) {
											JSONObject obj = new JSONObject();
											try {
												obj.put("TGC",DataCache.UserDataCache.get(0).getTGC());
												obj.put("mall",Constants.mallId);
												obj.put("itemId", itemId);
												obj.put("shopId", shopId);
												obj.put("type", type + "");
												obj.put("cartId", cartId);
												delShopIndex = shopIndex;
												mShopId = shopId;
												mOpeIndex = viewIndex;
												HttpUtil.requestAddOrDeleteItemTocart(mContext,obj.toString(),
														mFavourHandler, 1);
											} catch (JSONException e) {
												e.printStackTrace();
											}
											updateFooterView(shopTag);
											setCheckBox();
											if (mHandler != null) {
												mHandler.obtainMessage(0).sendToTarget();
												Message msg = mHandler.obtainMessage();
												Bundle bundle = new Bundle();
												bundle.putDouble("totalPrice", totalPrice);
												msg.setData(bundle);
												msg.sendToTarget();

											}
											if (isAllSelected()) {
												mHandler.obtainMessage(2).sendToTarget();
											} else if (isAllNotSelected()) {
												mHandler.obtainMessage(4).sendToTarget();
											} else {
												mHandler.obtainMessage(3).sendToTarget();
											}
										}

									}
								});
						mSimpleListDialog.setCanceledOnTouchOutside(true);
						mSimpleListDialog.show();

						return true;
					}

				});

				itemViewHolder.itemCheck = (CheckBox) itemView.findViewById(R.id.ck_commodity);
				itemViewHolder.itemCheck.setChecked(itemEntity.isChecked());
				itemViewHolder.itemCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								if (itemViewHolder.itemCheck.isChecked() == true) {
									itemViewHolder.itemCheck.setChecked(true);
									itemEntity.setChecked(true);
								} else {
									itemViewHolder.itemCheck.setChecked(false);
									itemEntity.setChecked(false);
								}

								String tag = (String) itemView.getTag();
								updateFooterView(shopTag);
								String editStr = tag+"#"+shopTag;
								
								String[] strings = tag.split("~");
								int shopId = Integer.parseInt(strings[0]);
								String goodIndexStr = strings[1];
								String shopIndexStr = strings[2];
								
								if (isChecked) {
									waitExecuteNum ++;
									if (!editMap.containsKey(shopId)) {
										ArrayList<String> mArrayList = new ArrayList<String>();
										mArrayList.add(goodIndexStr + "#" + shopIndexStr);
										editMap.put(shopId, mArrayList);
									} else {
										ArrayList<String> formerArrayList = editMap.get(shopId);
										formerArrayList.add(goodIndexStr + "#" + shopIndexStr);
										editMap.put(shopId, formerArrayList);
									}
									Log.i("ShoppingCartNew", "editMap key " + shopId + "添加了 " + goodIndexStr + "#" + shopIndexStr);
								} else {
									waitExecuteNum --;
									ArrayList<String> formerArrayList = editMap.get(shopId);
									for (int i = 0; i < formerArrayList.size(); i ++) {
										if (formerArrayList.get(i).equals(goodIndexStr + "#" + shopIndexStr)) {
											formerArrayList.remove(i);
											Log.i("ShoppingCartNew", "editMap key " + shopId + "删除了 " + goodIndexStr + "#" + shopIndexStr);
											break;
										}
									}
										editMap.put(shopId, formerArrayList);
								}
								Log.i("CartFinal", "waitExecuteNum = " + waitExecuteNum);
								setCheckBox();
								if (mHandler != null) {
									mHandler.obtainMessage(0).sendToTarget();
									Message msg = mHandler.obtainMessage();
									Bundle bundle = new Bundle();
									bundle.putDouble("totalPrice", totalPrice);
									msg.setData(bundle);
									msg.sendToTarget();

								}
								if (isAllSelected()) {
									mHandler.obtainMessage(2).sendToTarget();
								} else if (isAllNotSelected()) {
									mHandler.obtainMessage(4).sendToTarget();
								} else {
									mHandler.obtainMessage(3).sendToTarget();
								}
							}
						});
				itemViewHolder.itemImage = (ImageView) itemView.findViewById(R.id.im_commodity);
				itemViewHolder.grouponFlag = (ImageView) itemView.findViewById(R.id.groupon_Flag_shop);
				LinearLayout.LayoutParams itemImageParams = (LinearLayout.LayoutParams) itemViewHolder.itemImage.getLayoutParams();
				itemImageParams.width = (int) (96 * Constants.displayWidth / 480);
				itemImageParams.height = (int) (96 * Constants.displayWidth / 480);
				Log.i("CartFinal", "itemImageParams.width = " + itemImageParams.width + " , "
						+ "itemImageParams.height = " + itemImageParams.height);
				itemViewHolder.itemImage.setLayoutParams(itemImageParams);

				String imageUrl = itemEntity.getImage();
				AsyncImageLoaderUtil imageLoader = new AsyncImageLoaderUtil();
				Drawable cachedImage = imageLoader.loadDrawable(imageUrl,
						itemViewHolder.itemImage, new ILoadImageCallback() {
							@Override
							public void onObtainDrawable(Drawable drawable,
									ImageView imageView) {
								imageView.setImageDrawable(drawable);
							}

						});
				if (cachedImage == null) {
					itemViewHolder.itemImage
							.setImageResource(R.drawable.pic_56);
				} else {
					itemViewHolder.itemImage.setImageDrawable(cachedImage);
				}
				if (itemViewHolder.grouponFlag != null && Constants.isNeedGroupon) {
					int type = Integer.parseInt(itemEntity.getIsGroupon());
					LogUtil.d("tableviewlayout", "type = " + type);
					if (type == 1) {
						itemViewHolder.grouponFlag.setVisibility(View.VISIBLE);
					} else {
						itemViewHolder.grouponFlag.setVisibility(View.GONE);
					}
				}
				itemViewHolder.itemName = (TextView) itemView
						.findViewById(R.id.tv_commodity_name);
				itemViewHolder.itemName.setText(itemEntity.getItemName());
				itemViewHolder.itemInfo = (TextView) itemView
						.findViewById(R.id.tv_commodity_introduction);
				itemViewHolder.itemInfo.setText("");
				itemViewHolder.itemInfo.setVisibility(View.GONE);
				itemViewHolder.plPrice = (TextView) itemView
						.findViewById(R.id.tv_commodity_price_now);
				itemViewHolder.plPrice.setText(Constants.moneyTag
						+ DoubleUtil.subPrice(Double.parseDouble(itemEntity.getplPrice())));
				itemViewHolder.listPrice = (TextView) itemView
						.findViewById(R.id.tv_commodity_price_before);
				itemViewHolder.listPrice.setText(Constants.moneyTag
						+ DoubleUtil.subPrice(Double.parseDouble(itemEntity.getListPrice())));
				itemViewHolder.listPrice.getPaint().setFlags(
						Paint.STRIKE_THRU_TEXT_FLAG);

				itemViewHolder.buyCount = (TextView) itemView
						.findViewById(R.id.tv_commodity_amount);
				itemViewHolder.buyCount.setText(Constants.numPrfix
						+ itemEntity.getBuyNum());
				itemViewHolder.mBtnEditNum = (Button) itemView.findViewById(R.id.btn_edit_num);
				itemViewHolder.mBtnEditNum.setText("" + itemEntity.getBuyNum());
				itemViewHolder.mBtnEditNum.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						final String tag = (String) itemView.getTag();
						String str[] = tag.split("~");
						final int shopId = StringUtil.toInt(str[0]);
						final int viewIndex = StringUtil.toInt(str[1]);
						shopIndex = StringUtil.toInt(str[2]);
						itemEntitites = DataCache.CartItemListCache.get(shopId);
						ItemListEntity entity = itemEntitites.get(viewIndex);
						
						final long itemId = entity.getItemId();
						final int type = StringUtil.toInt(entity.getIsGroupon());
						final long cartId = DataCache.cartId;
						final String itemName = entity.getItemName();
						final String storeNum = String.valueOf(entity.getStoreNum());
						
						editShopId = shopId;
						editItemId = itemId;
						editType = type;
						editCartId = cartId;
						editShopIndex = shopIndex;
						editItemIndex = viewIndex;
						int buyNum = itemEntity.getBuyNum();
						mBuyNum = buyNum;
						String strBuyNum = "" + buyNum;
						oldBuyNum = strBuyNum;
						double unitPrice = 0;
						String rebatePrice = itemEntity.getplPrice()+"";
						String listPrice = itemEntity.getListPrice()+"";
						LogUtil.d("TableViewLayout"," listPrice=  " + listPrice);
						LogUtil.d("TableViewLayout"," rebatePrice=  "+ rebatePrice);
						if (!StringUtil.isEmpty(rebatePrice)) {
							unitPrice = StringUtil.toDouble(rebatePrice);
						} else {
							unitPrice = StringUtil
									.toDouble(listPrice);
						}
						final double uPrice = unitPrice;
						LogUtil.d("TableViewLayout"," uPrice=  " + unitPrice);
						LogUtil.d("TableViewLayout"," buyNum=  " + buyNum);

						mItemsEditDialog = new ItemsEditDialog(mContext);
						mItemsEditDialog.setTitle(R.string.cart_edit_count_title);
						mItemsEditDialog.setSubTitle(DoubleUtil.subPrice(DoubleUtil.round(unitPrice
										* buyNum, 3)) + "");
						mItemsEditDialog.setStoreNum(storeNum);
						mItemsEditDialog.setEditText(strBuyNum);
						mItemsEditDialog.setButton("取消",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog,
													int which) {
												mItemsEditDialog.cancel();
											}
										},"确认",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog,
													int which) {
												String text = mItemsEditDialog.getText();
												if (text == null) {
//													mItemsEditDialog.requestFocus();
												} else {
													mItemsEditDialog.dismiss();
													itemEntity.setBuyNum(mBuyNum);
													editNum = mBuyNum;
													requestChangeItemNum();
													setShopTag(shopTag);
//													updateItemNum();
//													updateFooterView(shopTag);
//													setCheckBox();
													if (mHandler != null) {
														mHandler.obtainMessage(0).sendToTarget();
														Message msg = mHandler.obtainMessage();
														Bundle bundle = new Bundle();
														bundle.putDouble("totalPrice", totalPrice);
														msg.setData(bundle);
														msg.sendToTarget();

													}
													if (isAllSelected()) {
														mHandler.obtainMessage(2).sendToTarget();
													} else if (isAllNotSelected()) {
														mHandler.obtainMessage(4).sendToTarget();
													} else {
														mHandler.obtainMessage(3).sendToTarget();
													}
												}
											}
										});

						mItemsEditDialog
								.setOnItemsClickListener(new onItemsClickListener() {

									@Override
									public void onIncreaseClicked(
											int num) {
										mBuyNum = num;
										mItemsEditDialog
												.setSubTitle(DoubleUtil.round(uPrice
														* num, 3) + "");
										
									}

									@Override
									public void onDecreaseClicked(
											int num) {
										mBuyNum = num;
										
										mItemsEditDialog
												.setSubTitle(DoubleUtil.round(uPrice
														* num, 3) + "");
										
									}
								});
						mItemsEditDialog.show();
					}
				});
//				itemViewHolder.mBtnEditNum.setVisibility(View.GONE);
//				itemViewHolder.mTxtEdit = (Button) itemView.findViewById(R.id.txt_edit);
//				itemViewHolder.mTxtEdit.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						
//						ItemViewHolder currentHolder = getCurrentViewHolder();
//						if (currentHolder != null && currentHolder != itemViewHolder) {
//							refreshEditFactor();
//						}
//						
//						//Toast.makeText(mContext, "edit item", Toast.LENGTH_SHORT).show();
//						System.out.println("edit will be executed");
//						if (itemViewHolder.mTxtEdit.getText().equals("编辑")) {
//							itemViewHolder.mTxtEdit.setText("完成");
//							Log.i("ShoppingCart", "shopTag = " + shopTag);
//							setShopTag(shopTag);
//							Log.i("ShoppingCart", "tag = " + itemView.getTag());
//							setCurrentTag( (String) itemView.getTag());
//							setCurrentViewHolder(itemViewHolder);
//						} else {
//							itemViewHolder.mTxtEdit.setText("编辑");
//							setCurrentViewHolder(null);
//						}
//							
////						itemViewHolder.mBtnEditNum.setVisibility((itemViewHolder.mBtnEditNum.getVisibility() == 
////								View.GONE) ? View.VISIBLE : View.GONE);
//							
//						if (getmImgDelete() != null) {
//							ImageView mDelete = getmImgDelete();
//							mDelete.setVisibility((mDelete.getVisibility() == View.VISIBLE) ? 
//									View.GONE : View.VISIBLE);
//						}
//						if (getmImgFavour() != null) {
//							ImageView mFavour = getmImgFavour();
//							mFavour.setVisibility((mFavour.getVisibility() == View.VISIBLE) ? 
//									View.GONE : View.VISIBLE);
//						}
//						if (getmBtnSelectAll() != null) {
//							LinearLayout mSelectAll = getmBtnSelectAll();
//							mSelectAll.setVisibility((mSelectAll.getVisibility() == View.VISIBLE) ? 
//									View.GONE : View.VISIBLE);
//						}
//					}
//				});
				int id = new Long(itemEntity.getItemId()).intValue();
				ArrayList<ItemBargainListEntity> bargainEntities = DataCache.itemBargainListCache.get(id);
				if(null != bargainEntities && bargainEntities.size() > 0) {
/*					itemViewHolder.itemBargain = (TextView) itemView
							.findViewById(R.id.tv_item_bargain);
					itemViewHolder.itemBargain.setText(bargainEntities.get(0).getName());*/
					itemViewHolder.itemList = (ListView) itemView
							.findViewById(R.id.item_list);
					List<String> data = new ArrayList<String>();
					for (int i=0; i<bargainEntities.size(); i++) {
						ItemBargainListEntity entity = bargainEntities.get(i);
						data.add(entity.getName());
					}
					ItemBargainAdapter adapter = new ItemBargainAdapter(getContext(), data, itemViewHolder.itemList);
					
					itemViewHolder.itemList.setAdapter(adapter);
					itemViewHolder.itemList.setEnabled(false);
					ListViewUtil.setListViewHeightBasedOnChildren(itemViewHolder.itemList);
					
				}
				
				itemViewHolders.add(itemViewHolder);
				
				RelativeLayout.LayoutParams itemViewParams = new RelativeLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//				itemViewParams.height = (int) (133 * Constants.displayWidth / 480);
				itemViewParams.setMargins(k, k, k, k);
				itemView.setLayoutParams(itemViewParams);
				shopView.addView(itemView);

				ImageView seprator1 = new ImageView(context);
				seprator1.setImageResource(R.drawable.line_full_cart_new);
				shopView.addView(seprator1);

				itemIndex++;
			}

			hashItemViewHolder.put(curIndex, itemViewHolders);

			LinearLayout footerView = new LinearLayout(context);
			footerView = (LinearLayout) mInflater.inflate(
					R.layout.tableview_footer_layout, null);
			final FooterViewHolder fViewHolder = new FooterViewHolder();
			fViewHolder.tvBargain = (TextView) footerView
					.findViewById(R.id.bargainList);
			String bargainTxt = "";
			ArrayList<BarginListEntity> barginList = DataCache.CartBagainListCache
					.get(shopId);
			int index = 0;
			for (BarginListEntity entity : barginList) {
				if (index < barginList.size() - 1)
					bargainTxt += entity.getBagainName() + ",";
				else {
					bargainTxt += entity.getBagainName();
				}
				index++;
			}
			fViewHolder.tvBargain.setText(bargainTxt);

			fViewHolder.tvPrice = (TextView) footerView
					.findViewById(R.id.priceTxt);
			fViewHolder.tvPrice.setText("￥0.00");

			fViewHolder.tvNum = (TextView) footerView.findViewById(R.id.numTxt);
			fViewHolder.tvNum.setText("x" + 0);

			RelativeLayout.LayoutParams footerViewParams = new RelativeLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			footerViewParams.height = (int) (80 * Constants.displayWidth / 480 + 8);
			footerViewParams.setMargins(k, k, k, k);
			footerView.setLayoutParams(footerViewParams);
			footerView.setTag("footer" + curIndex);
			shopView.addView(footerView);

			LinearLayout.LayoutParams childItemLayParams = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			addView(shopView, childItemLayParams);
			childItemLayParams.setMargins(k, k, k, k);

			LinearLayout localLinearLayout3 = new LinearLayout(context);
			shopView = localLinearLayout3;
			shopView.setOrientation(VERTICAL);
			shopView.setBackgroundResource(R.drawable.list_bgwhite_nor);

			hashFooterViewHolder.put(curIndex, fViewHolder);
			curIndex++;
		}
		addExtraView();
		invalidate();
	}

	public void addExtraView() {
		View extraView = View.inflate(mContext, R.layout.extra_view, null);
		addView(extraView);
	}
	
	/**
	 * deleteCartItem(编辑时删除所选条目)
	 * (这里描述这个方法适用条件 – 可选) 
	 */
	public void deleteCartItem() {
		
		Log.i("CartFinal", "等待处理的数量为  " + waitExecuteNum);
		
		for (Entry<Integer, ArrayList<String>> entry : editMap.entrySet()) {
			int editShopId = entry.getKey();
			int editShopIndex = -1;
			ArrayList<String> valueList = entry.getValue();
			for (String value : valueList) {
				Log.i("ShoppingCartNew", "删除map中 key editShopId = " + editShopId + " value = " + value);
				String[] valueArray = value.split("#");
				int editGoodIndex = Integer.parseInt(valueArray[0]);
				editShopIndex = Integer.parseInt(valueArray[1]);
				ItemListEntity entity = null;
				ItemViewHolder mHolder = null;
				if (DataCache.CartItemListCache.containsKey(editShopId) && editGoodIndex < DataCache.CartItemListCache.get(editShopId).size()) {
					entity = DataCache.CartItemListCache.get(editShopId).get(editGoodIndex);
				}
				
				if (hashItemViewHolder.containsKey(editShopIndex) && editGoodIndex < hashItemViewHolder.get(editShopIndex).size()) {
					mHolder = hashItemViewHolder.get(editShopIndex).get(editGoodIndex);
				}
				if (entity != null && mHolder != null) {
					final long itemId = entity.getItemId();
					final int type = StringUtil.toInt(entity.getIsGroupon());
					final long cartId = DataCache.cartId;
					JSONObject obj = new JSONObject();
					try {
						obj.put("TGC",DataCache.UserDataCache.get(0).getTGC());
						obj.put("mall",Constants.mallId);
						obj.put("itemId", itemId);
						obj.put("shopId", editShopId);
						obj.put("type", type + "");
						obj.put("cartId", cartId);
						Log.i("ShoppingCart", "即将删除第 " + editShopIndex + "个商铺中的第 " + editGoodIndex + "个商品");
						if (waitExecuteNum > 0) {
							HttpUtil.requestAddOrDeleteItemTocart(mContext,obj.toString(),
									new ServerConnectionHandler(editShopId, editGoodIndex, editShopIndex, entity,mHolder) {
										@Override
										public void handleMessage(Message msg) {
											switch (msg.what) {
											case Constants.HttpStatus.SUCCESS:
//												synchronized (DataCache.CartItemListCache) {
												waitExecuteNum--;
												Log.i("CartFinal", "处理完一个后 当前的waitExecuteNum = " + waitExecuteNum);
													ArrayList<ItemListEntity> itemList = DataCache.CartItemListCache.get(Integer.parseInt(getShopId() + ""));
													if (DataCache.CartItemListCache.size() > 0) {
														DataCache.CartItemListCache.get(Integer.parseInt(getShopId() + "")).remove(getEntity());
														hashItemViewHolder.get(getShopIndex()).remove(getmHolder());
														if (itemList.size() == 0) {
															hashItemViewHolder.remove(getShopIndex());
															hashFooterViewHolder.remove(getShopIndex());
															hashHeaderViewHolder.remove(getShopIndex());
														}
//													}
														if (waitExecuteNum == 0) {
															mHandler.obtainMessage(4).sendToTarget();
															if (mHandler != null) {
																mHandler.obtainMessage(1).sendToTarget();
															}
															setCheckBox();
															
															if (DataCache.UserDataCache.size() > 0) {
																JSONObject obj = new JSONObject();
																try {
																	obj.put("mall", Constants.mallId);
																	obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
																	DataUtil.queryMyMessageListData(mContext,
																			HomeActivityNew.mServerConnectionHandlerNew2, obj.toString());
																} catch (JSONException e) {
																	e.printStackTrace();
																}
																HttpUtil.queryUnReadMsgJson(mContext,
																		HomeActivityNew.mServerConnectionHandlerNew2, obj.toString());
																HttpUtil.getCartItemCount(mContext,
																		HomeActivityNew.mServerConnectionHandlerNewCart);
																HttpUtil.getCartTotalCount(mContext, mHandler);
															}
														}
															
												}
												break;
											case Constants.HttpStatus.NORMAL_EXCEPTION:
											case Constants.HttpStatus.CONNECTION_EXCEPTION:
												String fail = (String) msg.obj;
												break;
											}
										}
									}, 1);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				updateFooterView(editShopIndex);
			}
		}
	}
	 
	/**
	 * favourCartItem(编辑时把所选条目加入收藏夹)
	 * (这里描述这个方法适用条件 – 可选) 
	 */
	public void favorCartItem() {
		for (Entry<Integer, ArrayList<String>> entry : editMap.entrySet()) {
			int editShopId = entry.getKey();
			int editShopIndex = -1;
			ArrayList<String> valueList = entry.getValue();
			for (String value : valueList) {
				Log.i("ShoppingCartNew", "收藏map中 key editShopId = " + editShopId + " value = " + value);
				String[] valueArray = value.split("#");
				int editGoodIndex = Integer.parseInt(valueArray[0]);
				editShopIndex = Integer.parseInt(valueArray[1]);
				ItemListEntity entity = null;
				ItemViewHolder mHolder = null;
				if (DataCache.CartItemListCache.containsKey(editShopId) && editGoodIndex < DataCache.CartItemListCache.get(editShopId).size()) {
					entity = DataCache.CartItemListCache.get(editShopId).get(editGoodIndex);
				}
				
				if (hashItemViewHolder.containsKey(editShopIndex) && editGoodIndex < hashItemViewHolder.get(editShopIndex).size()) {
					mHolder = hashItemViewHolder.get(editShopIndex).get(editGoodIndex);
				}
				
				if (entity != null && mHolder != null) {
					final long itemId = entity.getItemId();
					final String itemName = entity.getItemName();
					final int type = StringUtil.toInt(entity.getIsGroupon());
					if(type == 1) {
						ToastUtil.showExceptionTips(mContext, "团购商品不能加入收藏夹");
					} else {
						JSONObject obj = new JSONObject();
						try {
							obj.put("mall",Constants.mallId);
							obj.put("TGC",DataCache.UserDataCache.get(0).getTGC());
							obj.put("id", itemId);
							obj.put("itemName", itemName);
							obj.put("fromType", 0);
							obj.put("cartId", DataCache.cartId);
							
							Log.i("ShoppingCart", "即将收藏第 " + editShopIndex + "个商铺中的第 " + editGoodIndex + "个商品");
								DataUtil.sendUserFavourOperation(
										new ServerConnectionHandler(editShopId, editGoodIndex, editShopIndex, entity,mHolder) {
											@Override
											public void handleMessage(Message msg) {
												switch (msg.what) {
												case Constants.HttpStatus.SUCCESS:
													waitExecuteNum --;
													Log.i("CartFinal", "处理完一个后 当前的waitExecuteNum = " + waitExecuteNum);
														ArrayList<ItemListEntity> itemList = DataCache.CartItemListCache.get(Integer.parseInt(getShopId() + ""));
														if (DataCache.CartItemListCache.size() > 0) {
															DataCache.CartItemListCache.get(Integer.parseInt(getShopId() + "")).remove(getEntity());
															hashItemViewHolder.get(getShopIndex()).remove(getmHolder());
															if (itemList.size() == 0) {
																hashItemViewHolder.remove(getShopIndex());
																hashFooterViewHolder.remove(getShopIndex());
																hashHeaderViewHolder.remove(getShopIndex());
															}
															
															if (waitExecuteNum == 0) {
																mHandler.obtainMessage(4).sendToTarget();
																if (mHandler != null) {
																	mHandler.obtainMessage(1).sendToTarget();
																}
																setCheckBox();
																
																if (DataCache.UserDataCache.size() > 0) {
																	JSONObject obj = new JSONObject();
																	try {
																		obj.put("mall", Constants.mallId);
																		obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
																		DataUtil.queryMyMessageListData(mContext,
																				HomeActivityNew.mServerConnectionHandlerNew2, obj.toString());
																	} catch (JSONException e) {
																		e.printStackTrace();
																	}
																	HttpUtil.queryUnReadMsgJson(mContext,
																			HomeActivityNew.mServerConnectionHandlerNew2, obj.toString());
																	HttpUtil.getCartItemCount(mContext,
																			HomeActivityNew.mServerConnectionHandlerNewCart);
																	HttpUtil.getCartTotalCount(mContext, mHandler);
																}
															}
														}
													break;
												case Constants.HttpStatus.NORMAL_EXCEPTION:
												case Constants.HttpStatus.CONNECTION_EXCEPTION:
													String fail = (String) msg.obj;
													break;
												}
											}
										}, obj.toString(),0);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				updateFooterView(editShopIndex);
			}
		}
	}
	
	private void requestChangeItemNum() {
		mHandler.obtainMessage(8).sendToTarget();
		HttpUtil.requestChangeItemNum(mUpdateNumHandler, getUpdateNumParam(), mContext);
	}
	
	private String getUpdateNumParam() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("mall", Constants.mallId);
			obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());	
			obj.put("shopId", editShopId);
			obj.put("itemId", editItemId);
			obj.put("itemNum", editNum);
			obj.put("type", editType);
			obj.put("cartId", editCartId);
			return obj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}					
	}
	
	private ServerConnectionHandler mUpdateNumHandler = new ServerConnectionHandler() {
		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("ItemDetailActivity", "msg.what = " + msg.what);
			LogUtil.d("ItemDetailActivity", "msg.arg1 = " + msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				shopEntity = DataCache.CartShopListCache.get(editShopIndex);
				int shopId = shopEntity.getShopId();
				itemEntitites = DataCache.CartItemListCache.get(shopId);
				ItemListEntity itemEntity = itemEntitites.get(editItemIndex);
				itemEntity.setBuyNum(editNum);
				updateItemNum();
				updateFooterView(getShopTag());
				setCheckBox();
				
				if (mHandler != null) {
					mHandler.obtainMessage(0).sendToTarget();
					Message msg1 = mHandler.obtainMessage();
					Bundle bundle = new Bundle();
					bundle.putDouble("totalPrice", totalPrice);
					msg1.setData(bundle);
					msg1.sendToTarget();
				}
				mHandler.obtainMessage(9).sendToTarget();
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				updateItemCount();
				updateFooterView(getShopTag());
				String fail = (String) msg.obj;
				Toast.makeText(mContext, fail, Toast.LENGTH_SHORT).show();
				mHandler.obtainMessage(9).sendToTarget();
				break;
			}
			// mpProgressBar.setVisibility(View.GONE);
		}
	};
	
	protected void updateFooterView(Object key) {
		int VIEW_COUNT = DataCache.CartShopListCache.size();
		int curIndex = (Integer) key;
		shopPrice = new double[VIEW_COUNT];
		double shopPriceBefore = 0;
		Log.v("hashFooterViewHolder", key + "");

		shopEntity = DataCache.CartShopListCache.get((Integer) key);
		int shopId = shopEntity.getShopId();
		LogUtil.d(VIEW_LOG_TAG, "shopId=" + shopId);
		itemEntitites = DataCache.CartItemListCache.get(shopId);

		int itemIndex = 0;
		double shopTotalPrice = 0;
		int shopTotalNum = 0;

		for (int j = 0; j < itemEntitites.size(); j++) {
			ItemListEntity itemEntity = itemEntitites.get(j);
			if (itemEntity.isChecked()) {
				Log.i("CartFinal", "itemEntity.getBuyNum() = " + itemEntity.getBuyNum());
				shopTotalNum += itemEntity.getBuyNum();
				shopTotalPrice += (itemEntity.getBuyNum() * StringUtil
						.toDouble(itemEntity.getplPrice()));
				shopTotalPrice = DoubleUtil.round(shopTotalPrice, 3);
			}
		}
		shopPriceBefore = shopPrice[curIndex];
		shopPrice[curIndex] = shopTotalPrice;
		final FooterViewHolder fViewHolder = hashFooterViewHolder
				.get((Integer) key);
		if (fViewHolder != null) {
			fViewHolder.tvPrice.setText("￥"+DoubleUtil.subPrice(shopTotalPrice));
			fViewHolder.tvNum.setText("x" + shopTotalNum);
			invalidate();
		}
		getTotalPrice();
	}

	/**
	 * 计算总计金额
	 */
	private void getTotalPrice() {
		totalPrice = 0;
		for (int i = 0; i < DataCache.CartShopListCache.size(); i++) {
			CartShopListEntity shopEntity = DataCache.CartShopListCache.get(i);
			int shopId = shopEntity.getShopId();

			itemEntitites = DataCache.CartItemListCache.get(shopId);
			for (int j = 0; j < itemEntitites.size(); j++) {
				ItemListEntity itemEntity = itemEntitites.get(j);
				LogUtil.d("TableViewLayout", "itemEntity = " + itemEntity);
				LogUtil.d("TableViewLayout", "itemEntity.checked = " + itemEntity.isChecked());

				if (itemEntity.isChecked()) {
					totalPrice += (itemEntity.getBuyNum() * StringUtil
							.toDouble(itemEntity.getplPrice()));
					totalPrice = DoubleUtil.round(totalPrice, 3);
				}

			}
		}
	}

	/**
	 * 判断是否全选
	 */
	private boolean isAllSelected() {
		int shopCount = DataCache.CartShopListCache.size();

		for (int i = 0; i < shopCount; i++) {
			ArrayList<ItemViewHolder> ivList = hashItemViewHolder.get(i);
			if (ivList != null && ivList.size() > 0) {
				for (int j = 0; j < ivList.size(); j++) {
					if (!ivList.get(j).itemCheck.isChecked()) {
						return false;
					}
				}
			}
			
		}
		return true;
	}

	/**
	 * 判断是否全不选
	 */
	public boolean isAllNotSelected() {
		int shopCount = DataCache.CartShopListCache.size();

		for (int i = 0; i < shopCount; i++) {
			ArrayList<ItemViewHolder> ivList = hashItemViewHolder.get(i);
			if (ivList != null && ivList.size() > 0) {
				for (int j = 0; j < ivList.size(); j++) {
					if (ivList.get(j).itemCheck.isChecked()) {
						return false;
					}
				}
			}
			
		}
		return true;
	}
	
	/**
	 * 改变相关CheckBox的状态
	 * 计算店铺headview的checkbox是否要选中（单个店铺的商品是否全选中）
	 */
	private void setCheckBox() {
		int index = 0;
		int shopCount = DataCache.CartShopListCache.size();

		for (int i = 0; i < shopCount; i++) {
			int shopId = DataCache.CartShopListCache.get(i).getShopId();
			itemEntitites = DataCache.CartItemListCache.get(shopId);
			index = 0;
			ArrayList<ItemViewHolder> ivList = hashItemViewHolder.get(i);
			if (ivList == null || ivList.size() <= 0)
				return;
			for (int j = 0; j < ivList.size(); j++) {
				ItemListEntity entity = itemEntitites.get(j);
				if (ivList.get(j).itemCheck.isChecked()) {
					entity.setChecked(true);
					index++;
				} else {
					entity.setChecked(false);
				}
				if (index == ivList.size()) {
					hashHeaderViewHolder.get(i).headCheck.setChecked(true);
				} else /* if (index == 0) */{
					hashHeaderViewHolder.get(i).headCheck.setChecked(false);
				}
			}
		}
	}

	private void updateItemNum() {
		shopEntity = DataCache.CartShopListCache.get(editShopIndex);
		int shopId = shopEntity.getShopId();

		itemEntitites = DataCache.CartItemListCache.get(shopId);
		ArrayList<ItemViewHolder> viewHolders = hashItemViewHolder.get(editShopIndex);
		ItemListEntity itemEntity = itemEntitites.get(editItemIndex);
		ItemViewHolder viewHolder = viewHolders.get(editItemIndex);
		viewHolder.buyCount.setText(" X" + itemEntity.getBuyNum() + "");
		viewHolder.mBtnEditNum.setText("" + itemEntity.getBuyNum());
	}
	
	private void updateItemCount(){
		shopEntity = DataCache.CartShopListCache.get(editShopIndex);
		int shopId = shopEntity.getShopId();
		itemEntitites = DataCache.CartItemListCache.get(shopId);
		ItemListEntity itemEntity = itemEntitites.get(editItemIndex);
		FooterViewHolder viewHolder = hashFooterViewHolder.get(editShopIndex);
		
		itemEntity.setBuyNum(Integer.parseInt(oldBuyNum));
		viewHolder.tvNum.setText("x" + oldBuyNum);
		viewHolder.tvPrice.setText("￥"+itemEntity.getplPrice()+"");
	}

	// private int getTotalPrice() {
	// int totalPrice = 0;
	// for (int i=0; i<shopPrice.length; i++) {
	// totalPrice += shopPrice[i];
	// }
	// return totalPrice;
	// }
	/**
	 * 刷新headerView的编辑状态 void
	 * 
	 * @param viewIndex
	 * @exception
	 * @since 1.0.0
	 */
	/*
	 * protected void updateHeadViewHolder(int viewIndex) { HeaderViewHolder
	 * headView = hashHeaderViewHolder.get(viewIndex);
	 * headView.headEdit.setImageResource
	 * (isEdit?R.drawable.confirm:R.drawable.edit);
	 * updateItemViewHolderEdited(viewIndex,isEdit); }
	 */

	/**
	 * 刷新itemView的编辑状态
	 * 
	 * @param viewIndex
	 *            view index
	 * @param isEdit
	 *            true：编辑,false:正常
	 * @exception
	 * @since 1.0.0
	 */
	private void updateItemViewHolderEdited(int viewIndex, boolean isEdit) {
		ArrayList<ItemViewHolder> viewHolders = hashItemViewHolder
				.get(viewIndex);
		for (ItemViewHolder holder : viewHolders) {
			// holder.llEidt.setVisibility(isEdit?View.VISIBLE:View.GONE);
			// holder.llItemInfo.setVisibility(!isEdit?View.VISIBLE:View.GONE);
		}
		// invalidate();
	}

	/**
	 * 刷新itemView的选中状态
	 * 
	 * @param object
	 *            view index
	 * @param isChecked
	 *            true：选中,false:未选中
	 * @exception
	 * @since 1.0.0
	 */
	protected void updateItemViewHolderChecked(Object key, boolean isChecked) {
		ArrayList<ItemViewHolder> viewHolders = hashItemViewHolder.get(key);
		for (ItemViewHolder holder : viewHolders) {
			holder.itemCheck.setChecked(isChecked);
		}
		invalidate();
	}

	/**
	 * 更新界面 是否全选购物车内商铺
	 * 
	 * @param bSelectAll
	 * @exception
	 * @since 1.0.0
	 */
	public void updateViewToSelectAll(boolean bSelectAll) {
		int shopIndex = 0;
		boolean isAll = isAllSelected();
		LogUtil.e("tablelayout", "isAll = "+isAll);
		for (CartShopListEntity entity : DataCache.CartShopListCache) {
			entity.setChecked(bSelectAll);
			HeaderViewHolder hViewHolder = hashHeaderViewHolder.get(shopIndex);
			hViewHolder.headCheck.setChecked(bSelectAll);
			int shopId = entity.getShopId();
			ArrayList<ItemListEntity> itemEntity = DataCache.CartItemListCache
					.get(shopId);
			for (ItemListEntity itemE : itemEntity) {
				LogUtil.e("tablelayout", "step 1 isAll = "+isAll);
				if (isAll) {
					itemE.setChecked(false);
				} else {
					itemE.setChecked(true);
				}

			}
			updateItemViewHolderChecked(shopIndex, bSelectAll);
			shopIndex++;
		}
		invalidate();
	}
	
	public void refreshCart() {
		hashHeaderViewHolder.clear();
		hashItemViewHolder.clear();
		hashFooterViewHolder.clear();
	}

	private Handler mHandler = null;

	/**
	 * setHandler(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param mFooterViewHandler
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public void setHandler(Handler mFooterViewHandler) {
		mHandler = mFooterViewHandler;
	}

	private ServerConnectionHandler mFavourHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("ItemDetailActivity", "msg.what = " + msg.what);
			LogUtil.d("ItemDetailActivity", "msg.arg1 = " + msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				// updateView();
				// ToastUtil.showExceptionTips(getBaseContext(), "收藏成功");

				LogUtil.d("TableViewLayout", "shopId = " + mShopId);
				LogUtil.d("TableViewLayout", "mOpeIndex = " + mOpeIndex);
				ArrayList<ItemListEntity> itemList = DataCache.CartItemListCache.get(Integer.parseInt(mShopId + ""));
				if (DataCache.CartItemListCache.size() > 0) {
					DataCache.CartItemListCache.get(Integer.parseInt(mShopId + "")).remove(mOpeIndex);
					Log.v("TableViewLayout shopId", mShopId+"");
					hashItemViewHolder.get(delShopIndex).remove(mOpeIndex);
					updateFooterView(delShopIndex);
					if (itemList.size() == 0) {
						shopEntity = DataCache.CartShopListCache.remove(shopIndex);
						hashItemViewHolder.remove(delShopIndex);
						hashFooterViewHolder.remove(delShopIndex);
						hashHeaderViewHolder.remove(delShopIndex);
					}

					if (mHandler != null) {
						mHandler.obtainMessage(1).sendToTarget();
					}
					
					setCheckBox();
					if (mHandler != null) {
						mHandler.obtainMessage(0).sendToTarget();
						Message msg2 = mHandler.obtainMessage();
						Bundle bundle = new Bundle();
						bundle.putDouble("totalPrice", totalPrice);
						msg2.setData(bundle);
						msg2.sendToTarget();

					}
					if (isAllSelected()) {
						mHandler.obtainMessage(2).sendToTarget();
					} else if (isAllNotSelected()) {
						mHandler.obtainMessage(4).sendToTarget();
					} else {
						mHandler.obtainMessage(3).sendToTarget();
					}
				}
				
				if (DataCache.UserDataCache.size() > 0) {
					JSONObject obj = new JSONObject();
					try {
						obj.put("mall", Constants.mallId);
						obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
						DataUtil.queryMyMessageListData(mContext,
								HomeActivityNew.mServerConnectionHandlerNew2, obj.toString());
					} catch (JSONException e) {
						e.printStackTrace();
					}
					HttpUtil.queryUnReadMsgJson(mContext,
							HomeActivityNew.mServerConnectionHandlerNew2, obj.toString());
					HttpUtil.getCartItemCount(mContext,
							HomeActivityNew.mServerConnectionHandlerNewCart);
					HttpUtil.getCartTotalCount(mContext, mHandler);
				}
				
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String) msg.obj;
				break;
			}
			// mpProgressBar.setVisibility(View.GONE);
		}

	};
}
