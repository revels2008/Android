package com.powerlong.electric.app.adapter;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.widget.CoverFlow;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * This class is an adapter that provides base, abstract class for images
 * adapter.
 * 
 */
public abstract class AbstractCoverFlowImageAdapter extends BaseAdapter {

    /** The Constant TAG. */
    private static final String TAG = AbstractCoverFlowImageAdapter.class.getSimpleName();

    /** The width. */
    private float width = 0;

    /** The height. */
    private float height = 0;

    /** The bitmap map. */
    private final Map<Integer, SoftReference<Bitmap>> bitmapMap = new HashMap<Integer, SoftReference<Bitmap>>();
    
    public AbstractCoverFlowImageAdapter() {
        super();
    }

    /**
     * Set width for all pictures.
     * 
     * @param width
     *            picture height
     */
    public synchronized void setWidth(final float width) {
        this.width = width;
    }

    /**
     * Set height for all pictures.
     * 
     * @param height
     *            picture height
     */
    public synchronized void setHeight(final float height) {
        this.height = height;
    }

    @Override
    public final Bitmap getItem(final int position) {
        final SoftReference<Bitmap> softBitmapReference = bitmapMap.get(position);
        if (softBitmapReference != null) {
            final Bitmap bitmap = softBitmapReference.get();
            if (bitmap == null) {
                Log.v(TAG, "Empty bitmap reference at position: " + position + ":" + this);
            } else {
                Log.v(TAG, "Reusing bitmap item at position: " + position + ":" + this);
                return bitmap;
            }
        }
        Log.v(TAG, "Creating item at position: " + position + ":" + this);
        final Bitmap bitmap = createBitmap(position);
        bitmapMap.put(position, new SoftReference<Bitmap>(bitmap));
        Log.v(TAG, "Created item at position: " + position + ":" + this);
        return bitmap;
    }

    /**
     * Creates new bitmap for the position specified.
     * 
     * @param position
     *            position
     * @return Bitmap created
     */
    protected abstract Bitmap createBitmap(int position);

    /**
     * get imageview
     */
    //protected abstract ImageView getImageView(int position);
    
    /**
     * get imageviews
     */
    //protected abstract ArrayList<ImageView> getImageViews();
    
    
    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public final synchronized long getItemId(final int position) {
        return position;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    @Override
    public final synchronized View getView(final int position, View convertView, final ViewGroup parent) {
        //ImageView imageView;
        ViewHolder holder ;
        //Log.v(TAG, "getImageView(position) =  " + getImageView(position));
        //if(getImageView(position) == null){
	        if (convertView == null) {
	        	holder = new ViewHolder();
	            final Context context = parent.getContext();
	            Log.v(TAG, "Creating Image view at position: " + position + ":" + this);
	            convertView = new LinearLayout(context);
	            holder.imageView = new ImageView(context);
	            holder.imageView.setLayoutParams(new CoverFlow.LayoutParams((int) Constants.displayWidth*3/5, (int) Constants.displayHeight*3/4));
	            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
	            convertView.setTag(holder);
	        } else {
	            Log.v(TAG, "Reusing view at position: " + position + ":" + this);
	            //imageView = (ImageView) convertView;
	            holder = (ViewHolder) convertView.getTag();
	        }
	        
	        holder.imageView.setImageBitmap(getItem(position));
	        
	        BitmapDrawable d = (BitmapDrawable) holder.imageView.getDrawable();
	        d.setAntiAlias(true);
	        
	        //getImageViews().add( holder.imageView);
        //}else{
        //	return getImageView(position);
        //}
		//imageView.setLayoutParams(new CoverFlow.LayoutParams(180, 480));
        return holder.imageView;
    }
    
    private class ViewHolder{
    	ImageView imageView;
    }

}
