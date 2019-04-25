/**
 * Project Name:training
 * File Name:Orginternal.java
 * Package Name:org.csr.common.domain
 * Date:2014年9月9日下午3:01:21
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.csr.common.user.safeResource.SafeData;
import org.csr.core.Comment;
import org.csr.core.persistence.domain.RootDomain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * ClassName: Orginternal.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014年9月9日下午3:01:21 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "c_AgenciesUser")
@Comment(ch = "用户机构关系表", en = "c_AgenciesUser")
public class AgenciesUser extends RootDomain<Long> implements SafeData<Long> {
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * 
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;
	/** 编号 */
	private Long id;
	/** 机构id */
	private Long agenciesId;
	/** 机构code */
	private String code;
	/** 用户 */
	private User user;

	public AgenciesUser() {
	}

	public AgenciesUser(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(generator = "globalGenerator")
	@GenericGenerator(name = "globalGenerator", strategy = "org.csr.core.persistence.generator5.GlobalGenerator")
	@Column(name = "id", unique = true, nullable = false)
	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "agenciesId")
	@Comment(ch = "机构ID")
	public Long getAgenciesId() {
		return agenciesId;
	}

	public void setAgenciesId(Long agenciesId) {
		this.agenciesId = agenciesId;
	}

	@Column(name = "code")
	@Comment(ch = "机构code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	@Comment(ch = "用户ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
