package com.powerlong.electric.app.dialog;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.ui.base.BaseDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class ItemsEditDialog extends BaseDialog{
	private ImageView ibDecrease = null;
	private ImageView ibIncrese = null;
	private onItemsClickListener mOnItemsClickListener;
	private EditText mEtEnter;
	private TextView mTxtStoreNum;
	private Context mContext = null;
	
	public ItemsEditDialog(Context context) {
		super(context);
		mContext = context;
		setDialogContentView(R.layout.include_dialog_items_edit);
		ibDecrease = (ImageView)findViewById(R.id.decrease);
		ibDecrease.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int curNum = Integer.valueOf(getText());
				if(curNum>1){
					curNum--;
					String strBuyNum = ""+curNum;
					mEtEnter.setText(""+curNum);
					mEtEnter.setSelection(strBuyNum.length());
				}
				if(mOnItemsClickListener!=null)
					mOnItemsClickListener.onDecreaseClicked(curNum);
			}
		});
		
		ibIncrese = (ImageView)findViewById(R.id.increase);
		ibIncrese.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int curNum = Integer.valueOf(getText());
				//if(curNum>=0){
					curNum++;
					String strBuyNum = ""+curNum;
					mEtEnter.setText(""+curNum);
					mEtEnter.setSelection(strBuyNum.length());
				//}
				if(mOnItemsClickListener!=null)
					mOnItemsClickListener.onIncreaseClicked(curNum);
			}
		});
		mEtEnter = (EditText)findViewById(R.id.edit_num);
		mEtEnter.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String storeString = mTxtStoreNum.getText().toString();
				if (storeString != null && storeString.trim().length() > 0) {
					int num = Integer.parseInt(s.toString());
					int storeNum = Integer.parseInt(mTxtStoreNum.getText().toString());
					if (num == storeNum) {
						ibIncrese.setEnabled(false);
					} else {
						ibIncrese.setEnabled(true);
					}
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mTxtStoreNum = (TextView) findViewById(R.id.txt_storeNum);
	}

	@Override
	public void setTitle(CharSequence text) {
		super.setTitle(text);
	}
	
	@Override
	public void setTitle(int id) {
		String title = mContext.getResources().getString(id);
		super.setTitle(title);
	}
	
	@Override
	public void setSubTitle(CharSequence text) {
		super.setSubTitle(text);
	}

	public void setButton(CharSequence text,
			DialogInterface.OnClickListener listener) {
		super.setButton1(text, listener);
	}

	public void setButton(CharSequence text1,
			DialogInterface.OnClickListener listener1, CharSequence text2,
			DialogInterface.OnClickListener listener2) {
		super.setButton1(text1, listener1);
		super.setButton2(text2, listener2);
	}
	
	public String getText() {
		if (isNull(mEtEnter)) {
			return null;
		}
		return mEtEnter.getText().toString().trim();
	}

	private boolean isNull(EditText editText) {
		String text = editText.getText().toString().trim();
		if (text != null && text.length() > 0) {
			return false;
		}
		return true;
	}

	public void requestFocus() {
		mEtEnter.requestFocus();
	}
	
	public void setOnItemsClickListener(
			onItemsClickListener listener) {
		mOnItemsClickListener = listener;
	}

	public interface onItemsClickListener {
		public void onDecreaseClicked(int num);
		public void onIncreaseClicked(int num);
	}

	/**
	 * setEditText(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param strBuyNum 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	public void setEditText(String strBuyNum) {
		if(mEtEnter!=null){
			mEtEnter.setText(strBuyNum);
			mEtEnter.setSelection(strBuyNum.length());
			int num = Integer.parseInt(strBuyNum);
			int storeNum = Integer.parseInt(mTxtStoreNum.getText().toString());
			if (num == storeNum) {
				ibIncrese.setEnabled(false);
			} else {
				ibIncrese.setEnabled(true);
			}
		}
	}
	
	/**
	 * 设置所编辑商品的库存数量
	 */
	public void setStoreNum(String storeNum) {
		if (mTxtStoreNum != null) 
			mTxtStoreNum.setText(storeNum);
	}
}
