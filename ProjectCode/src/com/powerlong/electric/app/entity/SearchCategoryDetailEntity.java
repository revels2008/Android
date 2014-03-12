/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * SearchCategoryDetailEntity.java
 * 
 * 2013-8-27-下午05:32:30
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.utils.JSONUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.tgb.lk.ahibernate.annotation.Column;
import com.tgb.lk.ahibernate.annotation.Id;
import com.tgb.lk.ahibernate.annotation.Table;

/**
 * 
 * SearchCategoryDetailEntity   搜索模块分类详情实体
 * 
 * @author: Liang Wang
 * 2013-8-27-下午05:32:30
 * 
 * @version 1.0.0
 * 
 */
@Table (name ="categroy_detail")
public class SearchCategoryDetailEntity implements Serializable {
	public static List<SearchCategoryDetailEntity> convertJson2DetailCategory(String json){
		ArrayList<SearchCategoryDetailEntity> detailEntities = new ArrayList<SearchCategoryDetailEntity>();
		try {
			JSONArray detail = new JSONArray(json);
				for (int i = 0; i < detail.length(); i++) {
					JSONObject detailObj = detail.getJSONObject(i);
					LogUtil.d("ElectricApp",
							"get SearchCategoryDetail + content = "
									+ detailObj.toString());
					SearchCategoryDetailEntity detailEntity = new SearchCategoryDetailEntity();
					int selfIdDetail = JSONUtil
							.getInt(detailObj,
									Constants.JSONKeyName.SEARCH_CATEGORY_DETAIL_OBJ_KEY_SELFID,
									0);
					int levelDetail = JSONUtil
							.getInt(detailObj,
									Constants.JSONKeyName.SEARCH_CATEGORY_DETAIL_OBJ_KEY_LEVEL,
									0);
					int mallIdDetail = JSONUtil
							.getInt(detailObj,
									Constants.JSONKeyName.SEARCH_CATEGORY_DETAIL_OBJ_KEY_MAILLID,
									0);
					int appItemCategoryIdDetail = JSONUtil
							.getInt(detailObj,
									Constants.JSONKeyName.SEARCH_CATEGORY_DETAIL_OBJ_KEY_APPITEMCATEGORYID,
									0);
					String nameDetail = JSONUtil
							.getString(
									detailObj,
									Constants.JSONKeyName.SEARCH_CATEGORY_DETAIL_OBJ_KEY_NAME,
									null);
					long pidDetail = JSONUtil
							.getLong(
									detailObj,
									Constants.JSONKeyName.SEARCH_CATEGORY_DETAIL_OBJ_KEY_PID,
									0);
					String method = JSONUtil
							.getString(
									detailObj,
									Constants.JSONKeyName.SEARCH_CATEGORY_DETAIL_OBJ_KEY_METHOD,
									null);
					String methodParams = JSONUtil
							.getString(
									detailObj,
									Constants.JSONKeyName.SEARCH_CATEGORY_DETAIL_OBJ_KEY_DATA,
									null);
					String descriptiondDetail = JSONUtil
							.getString(
									detailObj,
									Constants.JSONKeyName.SEARCH_CATEGORY_OBJ_KEY_DESCRIPTION,
									null);
					String logoDetail = JSONUtil
							.getString(
									detailObj,
									Constants.JSONKeyName.SEARCH_CATEGORY_OBJ_KEY_LOGO,
									null);

					detailEntity.setSeflId(selfIdDetail);
					detailEntity.setPid(pidDetail);
					detailEntity.setName(nameDetail);
					detailEntity
							.setAppItemCategoryId(appItemCategoryIdDetail);
					detailEntity.setLevel(levelDetail);
					detailEntity.setMethod(method);
					detailEntity.setData(methodParams);
					detailEntity.setMallId(mallIdDetail);
					detailEntity.setDescription(descriptiondDetail);
					detailEntity.setLogo(logoDetail);
					detailEntities.add(detailEntity);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return detailEntities;
	}
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since 1.0.0
	 */
	
	private static final long serialVersionUID = -6989471126394604459L;
	@Id
	@Column (name = "id")
	private int id;//primary id
	@Column(name="appItemCategoryId")
	private long appItemCategoryId;
	@Column(name="data")
	private String data;
	@Column(name="description")
	private String description;
	@Column(name="selfId")
	private long selfId;
	@Column(name="level")
	private int level;
	@Column(name="logo")
	private String logo;
	@Column(name="mallId")
	private int mallId;
	@Column(name="method")
	private String method;
	@Column(name="name")
	private String name;
	@Column(name="pid")
	private long pid;
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * id
	 *
	 * @return  the id
	 * @since   1.0.0
	*/
	
	public int getId() {
		return id;
	}
	/**
	 * @param seflId the seflId to set
	 */
	public void setSeflId(long seflId) {
		this.selfId = seflId;
	}
	/**
	 * seflId
	 *
	 * @return  the seflId
	 * @since   1.0.0
	*/
	
	public long getSeflId() {
		return selfId;
	}
	/**
	 * @param appItemCategoryId the appItemCategoryId to set
	 */
	public void setAppItemCategoryId(long appItemCategoryId) {
		this.appItemCategoryId = appItemCategoryId;
	}
	/**
	 * appItemCategoryId
	 *
	 * @return  the appItemCategoryId
	 * @since   1.0.0
	*/
	
	public long getAppItemCategoryId() {
		return appItemCategoryId;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * data
	 *
	 * @return  the data
	 * @since   1.0.0
	*/
	
	public String getData() {
		return data;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * description
	 *
	 * @return  the description
	 * @since   1.0.0
	*/
	
	public String getDescription() {
		return description;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * level
	 *
	 * @return  the level
	 * @since   1.0.0
	*/
	
	public int getLevel() {
		return level;
	}
	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/**
	 * logo
	 *
	 * @return  the logo
	 * @since   1.0.0
	*/
	
	public String getLogo() {
		return logo;
	}
	/**
	 * @param mallId the mallId to set
	 */
	public void setMallId(int mallId) {
		this.mallId = mallId;
	}
	/**
	 * mallId
	 *
	 * @return  the mallId
	 * @since   1.0.0
	*/
	
	public int getMallId() {
		return mallId;
	}
	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * method
	 *
	 * @return  the method
	 * @since   1.0.0
	*/
	
	public String getMethod() {
		return method;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * name
	 *
	 * @return  the name
	 * @since   1.0.0
	*/
	
	public String getName() {
		return name;
	}
	/**
	 * @param pid the pid to set
	 */
	public void setPid(long pid) {
		this.pid = pid;
	}
	/**
	 * pid
	 *
	 * @return  the pid
	 * @since   1.0.0
	*/
	
	public long getPid() {
		return pid;
	}
}
