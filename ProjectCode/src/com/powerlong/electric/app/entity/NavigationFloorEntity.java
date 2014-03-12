/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * NavigationFloorEntity.java
 * 
 * 2013-8-19-下午02:12:45
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;
import com.tgb.lk.ahibernate.annotation.Id;
import com.tgb.lk.ahibernate.annotation.Table;

/**
 * 
 * NavigationFloorEntity
 * 
 * @author: Liang Wang 2013-8-19-下午02:12:45
 * 
 * @version 1.0.0
 * 
 */
@Table(name = "navigationfloor")
public class NavigationFloorEntity {
	@Id
	@Column(name = "id")
	private int id;// primary_id

	@Column(name ="self_id")
	private int self_id;
	
	@Column(name = "group_id")
	private int group_id;// 导航分组编号

	@Column(name = "count")
	private int count;// 分类下的店铺、商品数量

	@Column(name = "name")
	private String name;// 导航分类名称

	@Column(name = "icon")
	private String icon;// 分类标识或品牌标识

	@Column(name = "method")
	private String method;// 点击请求的方法或接口地址

	@Column(name = "is_parent")
	private int is_parent;// 是否父节点

	@Column(name = "parent_id")
	private int parent_id;// 上级导航标识，
	
	@Column (name ="method_params")
	private String methodParams;//请求参数

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
	 * @param self_id the self_id to set
	 */
	public void setSelfId(int self_id) {
		this.self_id = self_id;
	}

	/**
	 * self_id
	 *
	 * @return  the self_id
	 * @since   1.0.0
	*/
	
	public int getSelfId() {
		return self_id;
	}

	/**
	 * @param group_id the group_id to set
	 */
	public void setGroupId(int group_id) {
		this.group_id = group_id;
	}

	/**
	 * group_id
	 *
	 * @return  the group_id
	 * @since   1.0.0
	*/
	
	public int getGroupId() {
		return group_id;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * count
	 *
	 * @return  the count
	 * @since   1.0.0
	*/
	
	public int getCount() {
		return count;
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
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * icon
	 *
	 * @return  the icon
	 * @since   1.0.0
	*/
	
	public String getIcon() {
		return icon;
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
	 * @param is_parent the is_parent to set
	 */
	public void setIsParent(int is_parent) {
		this.is_parent = is_parent;
	}

	/**
	 * is_parent
	 *
	 * @return  the is_parent
	 * @since   1.0.0
	*/
	
	public int getIsParent() {
		return is_parent;
	}

	/**
	 * @param parent_id the parent_id to set
	 */
	public void setParentId(int parent_id) {
		this.parent_id = parent_id;
	}

	/**
	 * parent_id
	 *
	 * @return  the parent_id
	 * @since   1.0.0
	*/
	
	public int getParentId() {
		return parent_id;
	}

	/**
	 * @param methodParams the methodParams to set
	 */
	public void setMethodParams(String methodParams) {
		this.methodParams = methodParams;
	}

	/**
	 * methodParams
	 *
	 * @return  the methodParams
	 * @since   1.0.0
	*/
	
	public String getMethodParams() {
		return methodParams;
	}
}
