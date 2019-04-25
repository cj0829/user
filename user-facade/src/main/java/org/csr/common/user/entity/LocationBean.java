/**
 * Project Name:training
 * File Name:Orginternal.java
 * Package Name:org.csr.common.domain
 * Date:2014年9月9日下午3:01:21
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.user.entity;

import java.util.List;

import org.csr.common.user.domain.Location;
import org.csr.core.tree.TreeNode;
import org.csr.core.web.bean.VOBase;

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
public class LocationBean extends VOBase<Long> implements TreeNode<Long> {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * 
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;
	/** 根节点id */
	public static final Long ROOT = 1L;

	private Long id;
	/** 父组织 */
	private Long parentId;
	/** 组织名称 */
	private String name;
	/** 组织编码 */
	String code;
	/** 机构路径 */
	String path;
	/** 说明备注 */
	String remark;

	private Integer childCount;
	private List<TreeNode> children;

	public LocationBean() {
	}

	public LocationBean(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public List<TreeNode> getChildren() {
		return this.children;
	}

	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public static LocationBean wrapBean(Location doMain) {
		if (null == doMain) {
			return null;
		}
		LocationBean bean = new LocationBean();
		bean.setId(doMain.getId());
		bean.setParentId(doMain.getParentId());
		bean.setName(doMain.getName());
		bean.setRemark(doMain.getRemark());
		bean.setCode(doMain.getCode());
		bean.setPath(doMain.getPath());
		return bean;
	}
}
