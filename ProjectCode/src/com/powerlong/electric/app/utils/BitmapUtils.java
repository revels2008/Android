/**
 * 宝龙电商
 * com.powerlong.electric.app.utils
 * BitmapUtils.java
 * 
 * 2013-11-12-下午02:41:13
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.utils;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;

/**
 * 
 * BitmapUtils
 * 
 * @author: He Gao
 * 2013-11-12-下午02:41:13
 * 
 * @version 1.0.0
 * 
 */
public class BitmapUtils {
	public static FinalBitmap getFinalBitmap(Context context){
		FinalBitmap fb = FinalBitmap.create(context);
		fb.configDiskCachePath(Constants.IMG_CACHE_PATH);
		fb.configLoadfailImage(R.drawable.pic_246);
		fb.configLoadingImage(R.drawable.pic_246);
		return fb;
	}
}
