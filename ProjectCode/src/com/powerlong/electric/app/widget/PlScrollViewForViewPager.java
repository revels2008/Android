/**
 * 宝龙电商
 * com.powerlong.electric.app.widget
 * PlScrollViewForViewPager.java
 * 
 * 2013-12-23-下午6:21:48
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * 
 * PlScrollViewForViewPager
 * 
 * @author: Liang Wang
 * 2013-12-23-下午6:21:48
 * 
 * @version 1.0.0
 * 
 */
public class PlScrollViewForViewPager extends ScrollView {
	private boolean canScroll;
	 
    private GestureDetector mGestureDetector;
    View.OnTouchListener mGestureListener;
 
    public PlScrollViewForViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(new YScrollDetector());
        canScroll = true;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_UP)
            canScroll = true;
        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
    }
 
    class YScrollDetector extends SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(canScroll)
                if (Math.abs(distanceY) >= Math.abs(distanceX))
                    canScroll = true;
                else
                    canScroll = false;
            return canScroll;
        }
    }
}
