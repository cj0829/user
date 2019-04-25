/**
 * Project Name:exam
 * File Name:CategoryListBean.java
 * Package Name:org.csr.common.user.result
 * Date:2015-12-18上午9:55:21
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
 */

package org.csr.common.user.entity;

import java.util.ArrayList;
import java.util.List;

import org.csr.core.web.bean.VOBase;

/**
 * ClassName:CategoryListBean.java <br/>
 * System Name： 签到系统 <br/>
 * Date: 2015-12-18上午9:55:21 <br/>
 * 
 * @author lishengmei <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 *   拥有的全部资源。
 */
public class CategoryListBean extends VOBase<Long> {

	/**
	 * (用一句话描述这个变量表示什么).
	 * 
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;
	/**拥有的资源*/
	private final List<Long> collectionIds;
	/**拥有的分类*/
	private List<Long> categoryIds = new ArrayList<Long>();
	/**拥有的域*/
	private List<Long> orgIds = new ArrayList<Long>();

	public CategoryListBean(List<Long> collectionIds) {
		this.collectionIds = collectionIds;
	}

	public List<Long> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<Long> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public List<Long> getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(List<Long> orgIds) {
		this.orgIds = orgIds;
	}

	public List<Long> getCollectionIds() {
		return collectionIds;
	}

}
