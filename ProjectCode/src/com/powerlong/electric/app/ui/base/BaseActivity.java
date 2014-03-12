/**
 * 宝龙电商
 * com.powerlong.electric.app.ui.base
 * BaseActivity.java
 * 
 * 2013-7-24-上午10:11:34
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui.base;

import java.lang.reflect.Field;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.dialog.FlippingLoadingDialog;
import com.powerlong.electric.app.widget.HandyTextView;
import com.powerlong.electric.app.utils.LogUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * 
 * BaseActivity
 * 
 * @author: Liang Wang 2013-7-24-上午10:11:34
 * 
 * @version 1.0.0
 * 
 */
public class BaseActivity extends Activity {

	private static final String TAG = "BaseActivity";

	protected AlertDialog mAlertDialog;
	protected AsyncTask mRunningTask;
	protected FlippingLoadingDialog mLoadingDialog;

	/******************************** 【Activity LifeCycle For Debug】 *******************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LogUtil.d(TAG, this.getClass().getSimpleName()
				+ " onCreate() invoked!!");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mLoadingDialog = new FlippingLoadingDialog(this, "请求提交中");
		// MobclickAgent.onError(this);
	}

	@Override
	protected void onStart() {
		LogUtil.d(TAG, this.getClass().getSimpleName() + " onStart() invoked!!");
		super.onStart();
	}

	@Override
	protected void onRestart() {
		LogUtil.d(TAG, this.getClass().getSimpleName()
				+ " onRestart() invoked!!");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		LogUtil.d(TAG, this.getClass().getSimpleName()
				+ " onResume() invoked!!");
		super.onResume();
		// MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		LogUtil.d(TAG, this.getClass().getSimpleName() + " onPause() invoked!!");
		super.onPause();
		try {
			// MobclickAgent.onPause(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onStop() {
		LogUtil.d(TAG, this.getClass().getSimpleName() + " onStop() invoked!!");
		super.onStop();
	}

	@Override
	public void onDestroy() {
		LogUtil.d(TAG, this.getClass().getSimpleName()
				+ " onDestroy() invoked!!");
		super.onDestroy();

		if (mRunningTask != null && mRunningTask.isCancelled() == false) {
			mRunningTask.cancel(false);
			mRunningTask = null;
		}
		if (mAlertDialog != null) {
			mAlertDialog.dismiss();
			mAlertDialog = null;
		}
	}

	protected void showLoadingDialog(String text) {
		if (text != null) {
			mLoadingDialog.setText(text);
		}
		mLoadingDialog.show();
	}

	protected void dismissLoadingDialog() {
		if (mLoadingDialog.isShowing()) {
			mLoadingDialog.dismiss();
		}
	}

	public void recommandToYourFriend(String url, String shareTitle) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, shareTitle + "   " + url);

		Intent itn = Intent.createChooser(intent, "分享");
		startActivity(itn);
	}

	/******************************** 【Activity LifeCycle For Debug】 *******************************************/

	/** 短暂显示Toast提示(来自res) **/
	protected void showShortToast(int resId) {
		Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
	}

	/** 短暂显示Toast提示(来自String) **/
	protected void showShortToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	/** 长时间显示Toast提示(来自res) **/
	protected void showLongToast(int resId) {
		Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
	}

	/** 长时间显示Toast提示(来自String) **/
	protected void showLongToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	/** 显示自定义Toast提示(来自res) **/
	protected void showCustomToast(int resId) {
		View toastRoot = LayoutInflater.from(BaseActivity.this).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text))
				.setText(getString(resId));
		Toast toast = new Toast(BaseActivity.this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}

	/** 显示自定义Toast提示(来自String) **/
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(BaseActivity.this).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(BaseActivity.this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}

	protected boolean hasExtra(String pExtraKey) {
		if (getIntent() != null) {
			return getIntent().hasExtra(pExtraKey);
		}
		return false;
	}

	protected void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
	}

	protected void openActivity(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	protected void openActivity(String pAction) {
		openActivity(pAction, null);
	}

	protected void openActivity(String pAction, Bundle pBundle) {
		Intent intent = new Intent(pAction);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	/**
	 * 通过反射来设置对话框是否要关闭，在表单校验时很管用， 因为在用户填写出错时点确定时默认Dialog会消失， 所以达不到校验的效果
	 * 而mShowing字段就是用来控制是否要消失的，而它在Dialog中是私有变量， 所有只有通过反射去解决此问题
	 * 
	 * @param pDialog
	 * @param pIsClose
	 */
	public void setAlertDialogIsClose(DialogInterface pDialog, Boolean pIsClose) {
		try {
			Field field = pDialog.getClass().getSuperclass()
					.getDeclaredField("mShowing");
			field.setAccessible(true);
			field.set(pDialog, pIsClose);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected AlertDialog showAlertDialog(String TitleID, String Message) {
		mAlertDialog = new AlertDialog.Builder(this).setTitle(TitleID)
				.setMessage(Message).show();
		return mAlertDialog;
	}

	protected AlertDialog showAlertDialog(int pTitelResID, String pMessage,
			DialogInterface.OnClickListener pOkClickListener) {
		String title = getResources().getString(pTitelResID);
		return showAlertDialog(title, pMessage, pOkClickListener, null, null);
	}

	protected AlertDialog showAlertDialog(String pTitle, String pMessage,
			DialogInterface.OnClickListener pOkClickListener,
			DialogInterface.OnClickListener pCancelClickListener,
			DialogInterface.OnDismissListener pDismissListener) {
		mAlertDialog = new AlertDialog.Builder(this)
				.setTitle(pTitle)
				.setMessage(pMessage)
				.setPositiveButton(android.R.string.ok, pOkClickListener)
				.setNegativeButton(android.R.string.cancel,
						pCancelClickListener).show();
		if (pDismissListener != null) {
			mAlertDialog.setOnDismissListener(pDismissListener);
		}
		return mAlertDialog;
	}

	protected AlertDialog showAlertDialog(String pTitle, String pMessage,
			String pPositiveButtonLabel, String pNegativeButtonLabel,
			DialogInterface.OnClickListener pOkClickListener,
			DialogInterface.OnClickListener pCancelClickListener,
			DialogInterface.OnDismissListener pDismissListener) {
		mAlertDialog = new AlertDialog.Builder(this).setTitle(pTitle)
				.setMessage(pMessage)
				.setPositiveButton(pPositiveButtonLabel, pOkClickListener)
				.setNegativeButton(pNegativeButtonLabel, pCancelClickListener)
				.show();
		if (pDismissListener != null) {
			mAlertDialog.setOnDismissListener(pDismissListener);
		}
		return mAlertDialog;
	}

	protected ProgressDialog showProgressDialog(int pTitelResID,
			String pMessage,
			DialogInterface.OnCancelListener pCancelClickListener) {
		String title = getResources().getString(pTitelResID);
		return showProgressDialog(title, pMessage, pCancelClickListener);
	}

	protected ProgressDialog showProgressDialog(String pTitle, String pMessage,
			DialogInterface.OnCancelListener pCancelClickListener) {
		mAlertDialog = ProgressDialog.show(this, pTitle, pMessage, true, true);
		mAlertDialog.setOnCancelListener(pCancelClickListener);
		return (ProgressDialog) mAlertDialog;
	}

	protected void hideKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	protected void handleOutmemoryError() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(BaseActivity.this, "内存空间不足！", Toast.LENGTH_SHORT)
						.show();
				// finish();
			}
		});
	}

	private int network_err_count = 0;

	protected void handleNetworkError() {
		network_err_count++;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (network_err_count < 3) {
					Toast.makeText(BaseActivity.this, "网速好像不怎么给力啊！",
							Toast.LENGTH_SHORT).show();
				} else if (network_err_count < 5) {
					Toast.makeText(BaseActivity.this, "网速真的不给力！",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(BaseActivity.this, "唉，今天的网络怎么这么差劲！",
							Toast.LENGTH_SHORT).show();
				}
				// finish();
			}
		});
	}

	protected void handleMalformError() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(BaseActivity.this, "数据格式错误！", Toast.LENGTH_SHORT)
						.show();
				// finish();
			}
		});
	}

	protected void handleFatalError() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(BaseActivity.this, "发生了一点意外，程序终止！",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}

	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	public void defaultFinish() {
		super.finish();
	}

}