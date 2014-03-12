/**
 * 宝龙电商
 * com.powerlong.electric.app.utils
 * RoundCornerUtil.java
 * 
 * 2013年9月6日-下午4:34:22
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * 
 * RoundCornerUtil
 * 
 * @author: YangCheng Miao
 * 2013年9月6日-下午4:34:22
 * 
 * @version 1.0.0
 * 
 */
public class RoundCornerUtil {
	public static Drawable toRoundCorner(Drawable drawable, int pixels) { 
       /* Bitmap bitmap;
		bitmap = ImageUtil.drawableToBitmap(drawable);
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888); 
        Canvas canvas = new Canvas(output); 
 
        final int color = 0xff424242; 
        final Paint paint = new Paint(); 
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()); 
        final RectF rectF = new RectF(rect); 
        final float roundPx = pixels; 
 
        paint.setAntiAlias(true); 
        canvas.drawARGB(0, 0, 0, 0); 
        paint.setColor(color); 
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint); 
 
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN)); 
        canvas.drawBitmap(bitmap, rect, rect, paint); 
 
        return ImageUtil.bitmapToDrawable(output); */
		return drawable;
    }

}
