/**
 * 宝龙电商
 * com.powerlong.electric.app.cache
 * BrandDetaiViewCache.java
 * 
 * 2013年9月11日-下午8:04:44
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.cache;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants;

/**
 * 
 * BrandDetaiViewCache
 * 
 * @author: YangCheng Miao
 * 2013年9月11日-下午8:04:44
 * 
 * @version 1.0.0
 * 
 */
public class BrandDetaiViewCache {
	private View baseView;
	private ImageView GrouponFlag;
    private TextView tvName;
    private TextView tvPrice;
    private ImageView imageView;
    private ImageView ivFavour;
    private ImageView ivCart;
    private TextView tvFavour;
    private TextView tvCart;
    private RelativeLayout rlOut;

    public BrandDetaiViewCache(View baseView) {
        this.baseView = baseView;
    }

    public TextView getNameView() {
        if (tvName == null) {
        	tvName = (TextView) baseView.findViewById(R.id.tv_grid_name);
        }
        return tvName;
    }
    
    public ImageView getGrouponView() {
        if (GrouponFlag == null && Constants.isNeedGroupon) {
        	GrouponFlag = (ImageView) baseView.findViewById(R.id.groupon_flag);
        }
        return GrouponFlag;
    }

    public ImageView getImageView() {
        if (imageView == null) {
            imageView = (ImageView) baseView.findViewById(R.id.iv_grid_item);
        }
        return imageView;
    }
    
    public TextView getPriceView() {
        if (tvPrice == null) {
        	tvPrice = (TextView) baseView.findViewById(R.id.tv_grid_price);
        }
        return tvPrice;
    }
    
    public ImageView getImageFavour() {
        if (ivFavour == null) {
        	ivFavour = (ImageView) baseView.findViewById(R.id.iv_shop_detail_favour);
        }
        return ivFavour;
    }
    
    public TextView getTextFavour() {
        if (tvFavour == null) {
        	tvFavour = (TextView) baseView.findViewById(R.id.tv_shop_detail_favour);
        }
        return tvFavour;
    }
    
    public ImageView getImageCart() {
        if (ivCart == null) {
        	ivCart = (ImageView) baseView.findViewById(R.id.iv_shop_detail_cart);
        }
        return ivCart;
    }
    
    public TextView getTextCart() {
        if (tvCart == null) {
        	tvCart = (TextView) baseView.findViewById(R.id.tv_shop_detail_cart);
        }
        return tvCart;
    }
    
    public RelativeLayout getLayoutOut() {
        if (rlOut == null) {
        	rlOut = (RelativeLayout) baseView.findViewById(R.id.rl_grid_item_out);
        }
        return rlOut;
    }
}
