/**
 * 宝龙电商
 * com.powerlong.electric.app.widget
 * ElasticScrollView.java
 * 
 * 2013-8-22-下午05:33:27
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.widget;

import java.util.Date;

import com.baidu.location.i.b;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.ui.HomeActivityNew;
import com.powerlong.electric.app.utils.LogUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * 
 * ElasticScrollView
 * 
 * @author: Liang Wang
 * 2013-8-22-下午05:33:27
 * 
 * @version 1.0.0
 * 
 */
public class ElasticScrollView extends ScrollView {
	private static final String TAG = "ElasticScrollView";
	private final static int RELEASE_To_REFRESH = 0;
	private final static int PULL_To_REFRESH = 1;
	public final static int REFRESHING = 2;
	public final static int DONE = 3;
	private final static int LOADING = 4;
	// 实际的padding的距离与界面上偏移距离的比例
	private final static int RATIO = 1;
	
	private int headContentWidth;
	private int headContentHeight;

	private LinearLayout innerLayout;
	private LinearLayout headView;
	private ImageView arrowImageView;
	private ProgressBar progressBar;
	private TextView tipsTextview;
	private TextView lastUpdatedTextView;
	private OnRefreshListener refreshListener;
	private boolean isRefreshable;
	private int state;
	private boolean isBack;
	private View naviView;

	public View getNaviView() {
		return naviView;
	}

	public void setNaviView(View naviView) {
		this.naviView = naviView;
	}

	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;

	private boolean canReturn;
	private boolean isRecored;
	private int startY;
	private int startX;
	private PlBannerLayout banner = null;
	boolean onHorizontal = false;
	
	private int currentState = -1;
	
	public int getCurrentState() {
		return currentState;
	}
	
	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}

	public boolean isRefreshable() {
		return isRefreshable;
	}

	/**
	 * @param isRefreshable the isRefreshable to set
	 */
	public void setRefreshable(boolean isRefreshable) {
		this.isRefreshable = isRefreshable;
	}

	public ElasticScrollView(Context context) {
		super(context);
		init(context);
	}

	public ElasticScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		//LayoutInflater inflater = LayoutInflater.from(context);
		//innerLayout = new LinearLayout(context);
		//innerLayout.setLayoutParams(new LinearLayout.LayoutParams(
		//		LinearLayout.LayoutParams.FILL_PARENT,
		//		LinearLayout.LayoutParams.WRAP_CONTENT));
		//innerLayout.setOrientation(LinearLayout.VERTICAL);
		
		//headView = (LinearLayout) inflater.inflate(R.layout.mylistview_head,
		//		null);
		

		//innerLayout.addView(headView);
		//addView(innerLayout);
		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);

		state = DONE;
		setRefreshable(false);
		canReturn = false;
	}
	
	
	public void setView(PlBannerLayout banner) {
		this.banner  = banner;
//		HomeActivityNew.mSlidingMenu.addIgnoredView(banner);
	}
	
	/* (non-Javadoc)
	 * @see android.view.View#onFinishInflate()
	 */
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		innerLayout = (LinearLayout)findViewById(R.id.elastic_root_lay);
		
		headView = (LinearLayout) findViewById(R.id.mylistview_head_lay);
		
		arrowImageView = (ImageView) headView
				.findViewById(R.id.head_arrowImageView);
		progressBar = (ProgressBar) headView
				.findViewById(R.id.head_progressBar);
		tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
		lastUpdatedTextView = (TextView) headView
				.findViewById(R.id.head_lastUpdatedTextView);
		measureView(headView);

		headContentHeight = headView.getMeasuredHeight();
		headContentWidth = headView.getMeasuredWidth();
		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();

		Log.i("size", "width:" + headContentWidth + " height:"
				+ headContentHeight);
		super.onFinishInflate();
	}
	
	// 对触屏事件进行重定向
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			onHorizontal = false;
			startX = (int)ev.getX();
			startY = (int)ev.getY();
			break;
		case MotionEvent.ACTION_UP:
			onHorizontal = false;
			break;
		default:
			break;
		}

		if (PlBannerLayout.onTouch) {
			float dx = Math.abs(ev.getX() - startX);
			//float dy = Math.abs(ev.getY() - startY);
			Log.i("MyViewFlow", "dx:=" + dx);
			if (dx > 20.0 /*|| dy <5.0*/) {
				onHorizontal = true;
			}
			if (onHorizontal) {
				Log.i(TAG, "viewFlow处理");
				return true;
			} else {
				Log.i(TAG, "listview处理");
				return super.onInterceptTouchEvent(ev);
			}
		} else {
			return super.onInterceptTouchEvent(ev);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		LogUtil.i(TAG, "viewFlow是否被点击：" + PlBannerLayout.onTouch);
		
		if(PlBannerLayout.onTouch)
		{
			return banner.onTouchEvent(event);
		}
		
		if (isRefreshable()) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (getScrollY() == 0 && !isRecored) {
					isRecored = true;
					startY = (int) event.getY();
					Log.i(TAG, "在down时候记录当前位置‘");
				}
				startX= (int)event.getX();
				break;
			case MotionEvent.ACTION_UP:
				if (state != REFRESHING && state != LOADING) {
					if (state == DONE) {
						// 什么都不做
					}
					if (state == PULL_To_REFRESH) {
						state = DONE;
						changeHeaderViewByState();
						Log.i(TAG, "由下拉刷新状态，到done状态");
					}
					if (state == RELEASE_To_REFRESH) {
						state = REFRESHING;
						changeHeaderViewByState();
						onRefresh();
						Log.i(TAG, "由松开刷新状态，到done状态");
					}
				}
				isRecored = false;
				isBack = false;

				break;
			case MotionEvent.ACTION_MOVE:
				if (PlBannerLayout.onTouch) {
					float dx = Math.abs(event.getX() - startX);
					Log.i("MyViewFlow", "dx:=" + dx);
					if (dx > 20.0) {
						return true;
					}
				}
				int tempY = (int) event.getY();
				if (!isRecored && getScrollY() == 0) {
					Log.i(TAG, "在move时候记录下位置");
					isRecored = true;
					startY = tempY;
				}

				if (state != REFRESHING && isRecored && state != LOADING) {
					// 可以松手去刷新了
					if (state == RELEASE_To_REFRESH) {
						canReturn = true;

						if (((tempY - startY) / RATIO < headContentHeight)
								&& (tempY - startY) > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();
							Log.i(TAG, "由松开刷新状态转变到下拉刷新状态");
						}
						// 一下子推到顶了
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();
							Log.i(TAG, "由松开刷新状态转变到done状态");
						} else {
							// 不用进行特别的操作，只用更新paddingTop的值就行了
						}
					}
					// 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
					if (state == PULL_To_REFRESH) {
						canReturn = true;

						// 下拉到可以进入RELEASE_TO_REFRESH的状态
						if ((tempY - startY) / RATIO >= headContentHeight) {
							state = RELEASE_To_REFRESH;
							isBack = true;
							changeHeaderViewByState();
							Log.i(TAG, "由done或者下拉刷新状态转变到松开刷新");
						}
						// 上推到顶了
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();
							Log.i(TAG, "由DOne或者下拉刷新状态转变到done状态");
						}
					}

					// done状态下
					if (state == DONE) {
						if (tempY - startY > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();
						}
					}

					// 更新headView的size
					if (state == PULL_To_REFRESH) {
						headView.setPadding(0, -1 * headContentHeight
								+ (tempY - startY) / RATIO, 0, 0);

					}

					// 更新headView的paddingTop
					if (state == RELEASE_To_REFRESH) {
						headView.setPadding(0, (tempY - startY) / RATIO
								- headContentHeight, 0, 0);
					}
					if (canReturn) {
						canReturn = false;
						return true;
					}
				}
				break;
			}
		} else {
			return getNaviView().onTouchEvent(event);
		}
		return super.onTouchEvent(event);
	}

	// 当状态改变时候，调用该方法，以更新界面
	private void changeHeaderViewByState() {
		switch (state) {
		case RELEASE_To_REFRESH:
			setCurrentState(RELEASE_To_REFRESH);
			arrowImageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			arrowImageView.clearAnimation();
			arrowImageView.startAnimation(animation);

			tipsTextview.setText("松开刷新");

			Log.i(TAG, "当前状态，松开刷新");
			break;
		case PULL_To_REFRESH:
			setCurrentState(PULL_To_REFRESH);
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.VISIBLE);
			// 是由RELEASE_To_REFRESH状态转变来的
			if (isBack) {
				isBack = false;
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(reverseAnimation);

				tipsTextview.setText("下拉刷新");
			} else {
				tipsTextview.setText("下拉刷新");
			}
			Log.i(TAG, "当前状态，下拉刷新");
			break;

		case REFRESHING:
			setCurrentState(REFRESHING);
			headView.setPadding(0, 0, 0, 0);

			progressBar.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.GONE);
			tipsTextview.setText("正在刷新...");
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			Log.i(TAG, "当前状态,正在刷新...");
			break;
		case DONE:
			setCurrentState(DONE);
			headView.setPadding(0, -1 * headContentHeight, 0, 0);

			progressBar.setVisibility(View.GONE);
			arrowImageView.clearAnimation();
			arrowImageView.setImageResource(R.drawable.goicon);
			tipsTextview.setText("下拉刷新");
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			Log.i(TAG, "当前状态，done");
			break;
		}
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	public void setonRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
		setRefreshable(true);
	}

	public interface OnRefreshListener {
		public void onRefresh();
	}

	public void onRefreshComplete() {
		state = DONE;
		lastUpdatedTextView.setText("最近更新:" + new Date().toLocaleString());
		changeHeaderViewByState();
		invalidate();
		scrollTo(0, 0);
	}

	private void onRefresh() {
		if (refreshListener != null) {
			refreshListener.onRefresh();
		}
	}

	public void addChild(View child) {
		innerLayout.addView(child);
	}

	public void addChild(View child, int position) {
		innerLayout.addView(child, position);
	}
}
