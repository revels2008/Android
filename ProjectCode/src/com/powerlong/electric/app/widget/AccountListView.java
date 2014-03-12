/**
 * 宝龙电商
 * com.powerlong.electric.app.widget
 * AccountListView.java
 * 
 * 2013-9-3-上午09:21:47
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.widget;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.ui.MyAccountFragment;
import com.powerlong.electric.app.utils.TouchTool;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * 
 * AccountListView
 * 
 * @author: Liang Wang
 * 2013-9-3-上午09:21:47
 * 
 * @version 1.0.0
 * 
 */
public class AccountListView extends ListView {
	private Context mContext;
	private Scroller mScroller;
	TouchTool tool;
	int left, top;
	float startX, startY, currentX, currentY;
	int bgViewH, iv1W;
	int rootW, rootH;
	View headView;
	View bgView;
	boolean scrollerType;
	static final int len = 0xc8;

	public AccountListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public AccountListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		mScroller = new Scroller(mContext);
	}

	public AccountListView(Context context) {
		super(context);

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		int action = event.getAction();
		if (!mScroller.isFinished()) {
			return super.onTouchEvent(event);
		}
		headView = MyAccountFragment.itemHeader;
		bgView = headView.findViewById(R.id.path_headimage);
		currentX = event.getX();
		currentY = event.getY();
		headView.getTop();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
//			Log.i("ListView2", "ACTION_DOWN!!!");
			left = bgView.getLeft();
			top = bgView.getBottom();
			rootW = getWidth();
			rootH = getHeight();
			bgViewH = bgView.getHeight();
			startX = currentX;
			startY = currentY;
			tool = new TouchTool(bgView.getLeft(), bgView.getBottom(),
					bgView.getLeft(), bgView.getBottom() + len);
			break;
		case MotionEvent.ACTION_MOVE:
//			Log.i("ListView2", "ACTION_MOVE!!!");
//			Log.i("ListView2", "currentX" + currentX);
//			Log.i("ListView2", "currentY" + currentY);
//			Log.i("ListView2", "headView.getTop()=" + headView.getTop());
//			Log.i("ListView2", "headView.isShown()=" + headView.isShown());

			if (headView.isShown() && headView.getTop() >= 0) {
				if (tool != null) {
					int t = tool.getScrollY(currentY - startY);
					if (t >= top && t <= headView.getBottom() + len) {
						bgView.setLayoutParams(new RelativeLayout.LayoutParams(
								bgView.getWidth(), t));
					}
				}
				scrollerType = false;
			}
			break;
		case MotionEvent.ACTION_UP:
//			Log.i("ListView2", "ACTION_UP!!!");
			scrollerType = true;
			mScroller.startScroll(bgView.getLeft(), bgView.getBottom(),
					0 - bgView.getLeft(), bgViewH - bgView.getBottom(), 200);
			invalidate();
			break;
		}

		return super.dispatchTouchEvent(event);
	}

	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			int x = mScroller.getCurrX();
			int y = mScroller.getCurrY();
			System.out.println("x=" + x);
			System.out.println("y=" + y);
			bgView.layout(0, 0, x + bgView.getWidth(), y);
			invalidate();
			// 重点
			if (!mScroller.isFinished() && scrollerType && y > 200) {// 重点判断
				bgView.setLayoutParams(new RelativeLayout.LayoutParams(bgView
						.getWidth(), y));
			}
		}
	}
}
