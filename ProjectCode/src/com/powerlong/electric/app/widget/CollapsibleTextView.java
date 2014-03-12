/**
 * 宝龙电商
 * com.powerlong.electric.app.widget
 * CollapsibleTextView.java
 * 
 * 2013-9-6-下午04:44:46
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.widget;

import com.powerlong.electric.app.R;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;
/**
 * 
 * CollapsibleTextView 可以伸缩的TextView
 * 
 * @author: Liang Wang
 * 2013-9-6-下午04:44:46
 * 
 * @version 1.0.0
 * 
 */
public class CollapsibleTextView extends LinearLayout implements
        OnClickListener {

    /** default text show max lines */
    private static final int DEFAULT_MAX_LINE_COUNT = 2;

    private static final int COLLAPSIBLE_STATE_NONE = 0;
    private static final int COLLAPSIBLE_STATE_SHRINKUP = 1;
    private static final int COLLAPSIBLE_STATE_SPREAD = 2;

    private TextView desc;
    //private TextView descOp;

    private String shrinkup;
    private String spread;
    private int mState;
    private boolean flag;

    public CollapsibleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.collapsible_textview, this);
        view.setPadding(0, -1, 0, 0);
        desc = (TextView) view.findViewById(R.id.desc_tv);
    }

    public CollapsibleTextView(Context context) {
        this(context, null);
    }

    public final void setDesc(CharSequence charSequence, BufferType bufferType) {
        desc.setText(charSequence, bufferType);
        mState = COLLAPSIBLE_STATE_SPREAD;
        requestLayout();
    }

    @Override
    public void onClick(View v) {
        flag = false;
        if(desc.getEllipsize() == TruncateAt.END){
        	desc.setEllipsize(null);
        }
        requestLayout();
    }
    
    public void updateView(View v) {
        flag = false;
        if(desc.getEllipsize() == TruncateAt.END){
        	desc.setEllipsize(null);
        }
        requestLayout();
    }
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!flag) {
            flag = true;
            if (desc.getLineCount() <= DEFAULT_MAX_LINE_COUNT) {
                mState = COLLAPSIBLE_STATE_NONE;
                //descOp.setVisibility(View.GONE);
                desc.setMaxLines(DEFAULT_MAX_LINE_COUNT + 1);
                desc.setEllipsize(TruncateAt.END);
            } else {
            	post(new InnerRunnable());
            }
        }
    }

    class InnerRunnable implements Runnable {
        @Override
        public void run() {
            if (mState == COLLAPSIBLE_STATE_SPREAD) {
                desc.setMaxLines(DEFAULT_MAX_LINE_COUNT);
                //descOp.setVisibility(View.VISIBLE);
                desc.setEllipsize(TruncateAt.END);
                //descOp.setText(spread);
                mState = COLLAPSIBLE_STATE_SHRINKUP;
            } else if (mState == COLLAPSIBLE_STATE_SHRINKUP) {
                desc.setMaxLines(Integer.MAX_VALUE);
                //descOp.setVisibility(View.VISIBLE);
                //descOp.setText(shrinkup);
                mState = COLLAPSIBLE_STATE_SPREAD;
            }
        }
    }
}
