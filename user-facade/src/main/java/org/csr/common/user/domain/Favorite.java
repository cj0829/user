/**
 * Project Name:exam_1.1
 * File Name:Favorite.java
 * Package Name:org.csr.common.user.domain
 * Date:2016年2月17日下午4:01:13
 * Copyright (c) 2016, csr版权所有 ,All rights reserved 
 */

package org.csr.common.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.csr.core.Comment;
import org.csr.core.persistence.domain.SimpleDomain;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * ClassName:Favorite.java <br/>
 * System Name： 签到系统 <br/>
 * Date: 2016年2月17日下午4:01:13 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 收藏表 功能描述： <br/>
 *        公用方法描述： <br/>
 * 
 */
@Entity
@Table(name = "u_Favorite")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch = "收藏表",en="pmt_common_Favorite")
public class Favorite extends SimpleDomain<Long> {

	/**
	 * (用一句话描述这个变量表示什么).
	 * 
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;
	private Long id;

	private Long userId;
	/** 分类id */
	private Long categoryItemId;

	@Id
	@GeneratedValue(generator = "globalGenerator")
	@GenericGenerator(name = "globalGenerator", strategy = "org.csr.core.persistence.generator5.GlobalGenerator")
	@Column(name = "id", unique = true, nullable = false)
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "userId")
	@Comment(ch = "用户Id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "categoryItemId")
	@Comment(ch = "分类Id")
	public Long getCategoryItemId() {
		return categoryItemId;
	}

	public void setCategoryItemId(Long categoryItemId) {
		this.categoryItemId = categoryItemId;
	}

}
