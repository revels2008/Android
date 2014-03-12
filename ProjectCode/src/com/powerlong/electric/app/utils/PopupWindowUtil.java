package com.powerlong.electric.app.utils;

import java.util.List;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.ui.SearchFragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
/**
 * 
 * PopupWindowUtil： pop window工具类
 * 
 * @author: Liang Wang 2013-8-7-下午04:27:21
 * 
 * @version 1.0.0
 * 
 */
public class PopupWindowUtil<T> implements OnClickListener {
	PopupWindow popupWindow;

	public PopupWindowUtil() {
	}

	int width = 0;

	public void showActionWindow(View parent, Context context, List<T> data) {
		// final RingtoneclipModel currentData = model;
		// final int res_id = currentData.getId();
		int[] location = new int[2];
		int popWidth = context.getResources().getDimensionPixelOffset(
				R.dimen.popupWindow_width);
		parent.getLocationOnScreen(location);
		View view = getView(context, data);

		popupWindow = new PopupWindow(view, popWidth, LayoutParams.WRAP_CONTENT);// new
																					// PopupWindow(view,
																					// popWidth,
																					// LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		// 显示的位置为:屏幕的最右端
		popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, location[0],
				location[1] -60*data.size());
		popupWindow.update();

	}
	
	private static int mItemHeight = 0;

	private View getView(Context context, List<T> data) {
		LinearLayout layout = new LinearLayout(context);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layout.setLayoutParams(params);
		layout.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				60);
		itemParams.gravity= Gravity.CENTER;
		layout.setBackgroundResource(R.drawable.tab_selected);
		for (int i = 0; i < data.size(); i++) {
			if (i != data.size() - 1) {
				String name = "";
				if (data.get(i) instanceof String) {
					name = ((List<String>) data).get(i);
				}
				Button btn = getButton(context, name, i);
				ImageView img = getImageView(context);
				layout.addView(btn,itemParams);
				layout.addView(img);
				LogUtil.d("popwindow","btn h = "+btn.getHeight());
			} else {
				String name = "";
				if (data.get(i) instanceof String) {
					name = ((List<String>) data).get(i);
				}
				Button btn = getButton(context, name, i);
				layout.addView(btn,itemParams);
			}
		}
		return layout;
	}

	private Button getButton(Context context, String text, int i) {
		Button btn = new Button(context);
		btn.setText(text);
		btn.setTextColor(context.getResources().getColor(R.color.white));
		btn.setTag(i);
		btn.setPadding(20, 15, 20, 15);
		btn.setBackgroundColor(Color.TRANSPARENT);
		btn.setOnClickListener(this);
		return btn;
	}

	private static ImageView getImageView(Context context) {
		ImageView img = new ImageView(context);
		img.setBackgroundResource(R.drawable.dis_popup_side);
		img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		return img;
	}

	@Override
	public void onClick(View v) {
		popupWindow.dismiss();
	}
}
