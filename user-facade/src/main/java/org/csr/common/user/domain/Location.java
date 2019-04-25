/**
 * Project Name:training
 * File Name:Orginternal.java
 * Package Name:org.csr.common.domain
 * Date:2014年9月9日下午3:01:21
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.user.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.csr.core.Comment;
import org.csr.core.persistence.domain.SimpleDomain;
import org.csr.core.tree.TreeNode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

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
@Table(name = "cm_location")
@Comment(ch = "位置", en = "u_location")
public class Location extends SimpleDomain<Long> implements TreeNode<Long> {
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * 
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;
	/** 内部地址 */
	public static final Long ROOT = 1L;

	private Long id;
	/** 父组织 */
	private Long parentId;
	/** 组织名称 */
	private String name;
	/** 组织编码 */
	String code;
	/** 类型 */
	Integer type;
	/** 机构路径 */
	String path;
	/** 说明备注 */
	String remark;

	private Integer childCount;
	@SuppressWarnings("rawtypes")
	private List<TreeNode> children;

	public Location() {
	}

	public Location(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(generator = "globalGenerator")
	@GenericGenerator(name = "globalGenerator", strategy = "org.csr.core.persistence.generator5.GlobalGenerator")
	@Column(name = "id", unique = true, nullable = false)
	@Comment(ch = "位置id", en = "id")
	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "parentId")
	@Comment(ch = "父机构ID", en = "parentId")
	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Column(name = "name", length = 40)
	@Length(min = 1, max = 40)
	@Comment(ch = "组织机构名称", en = "name", vtype = "required:true", len = 64, search = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "type")
	@Comment(ch = "机构类型", en = "type", vtype = "")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column(name = "remark", length = 128)
	@Length(min = 0, max = 128)
	@Comment(ch = "说明备注", en = "remark", vtype = "required:true", len = 128, search = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "code", length = 40)
	@Length(min = 0, max = 40)
	@Comment(ch = "组织编码", en = "code", len = 40, search = true)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "path", length = 40)
	@Length(min = 0, max = 40)
	@Comment(ch = "路径", en = "path", len = 40, search = true)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@SuppressWarnings("rawtypes")
	@Transient
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	@SuppressWarnings("rawtypes")
	@Transient
	public List<TreeNode> getChildren() {
		return this.children;
	}

	@Transient
	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

}
