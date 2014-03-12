package com.powerlong.electric.app.db;

import android.content.Context;

import com.powerlong.electric.app.dao.NavigationBaseDao;
import com.powerlong.electric.app.entity.BannerEntity;
import com.powerlong.electric.app.entity.NavigationActivityEntity;
import com.powerlong.electric.app.entity.NavigationBaseEntity;
import com.powerlong.electric.app.entity.NavigationBrandEntity;
import com.powerlong.electric.app.entity.NavigationEntertainmentEntity;
import com.powerlong.electric.app.entity.NavigationFloorEntity;
import com.powerlong.electric.app.entity.NavigationFoodsEntity;
import com.powerlong.electric.app.entity.NavigationGrouponEntity;
import com.powerlong.electric.app.entity.NavigationShoppingEntity;
import com.powerlong.electric.app.entity.RecommendEntity;
import com.powerlong.electric.app.entity.SearchCategoryDetailEntity;
import com.powerlong.electric.app.entity.SearchCategoryEntity;
import com.tgb.lk.ahibernate.util.PlElectricAppDBHelper;

public class DBHelper extends PlElectricAppDBHelper {
	// 数据库名称
	public static final String dataBaseName = "powerlong.db";
	// 版本
	private static final int version = 4;
	// 要初始化的表
	public static final Class<?>[] clazz = { SearchCategoryEntity.class,
			SearchCategoryDetailEntity.class, NavigationBaseEntity.class,
			BannerEntity.class,RecommendEntity.class
			/*NavigationFloorEntity.class, NavigationBrandEntity.class,
			NavigationActivityEntity.class,
			NavigationEntertainmentEntity.class, NavigationFoodsEntity.class,
			NavigationGrouponEntity.class, NavigationShoppingEntity.class */};

	public DBHelper(Context context) {
		super(context, dataBaseName, null, version, clazz);
	}
}
