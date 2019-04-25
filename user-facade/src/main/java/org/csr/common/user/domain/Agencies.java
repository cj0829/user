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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.csr.common.user.safeResource.SafeData;
import org.csr.core.AutoSetProperty;
import org.csr.core.Comment;
import org.csr.core.persistence.business.domain.Organization;
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
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "c_Agencies")
@Comment(ch = "机构组织",en="c_Agencies")
public class Agencies extends SimpleDomain<Long> implements TreeNode<Long>,SafeData<Long> {
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * 
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;
	/** 根节点id*/
	public static final Long ROOT = 1L;
	
	private Long id;
	/** 父组织*/
	private Long parentId;
	/** 域*/
	@AutoSetProperty(message="域")
	Organization org;
	/**组织名称*/
	private String name;
	/**组织编码*/
	String code;
	/** 地址 1*/
	String address1;
	/**地址 2*/
	String address2;
	/**地址 3*/
	String address3;
	/** 城市*/
	String city;
	/**州/省*/
	String stateAndProvince;
	/** 国家/地区 */
	String countryAndRegion;
	/**邮政编码 */
	String postalCode;
	/**主要联系电话 */
	String mainPhone;
	/** 备用电话 */
	String alternatePhone;
	/** 传真*/
	String fax;
	/** 说明备注*/
	String remark;
	/** 节点类型*/
	Integer type;
	/** 自定义字段1 */
	private String customize1;
	/** 自定义字段2 */
	private String customize2;
	/** 自定义字段3 */
	private String customize3;
	/** 自定义字段4 */
	private String customize4;
	/** 自定义字段5 */
	private String customize5;
	
	private Integer childCount;
	@SuppressWarnings("rawtypes")
	private List<TreeNode> children;
	
	public Agencies() {
	}
	
	public Agencies(Long id){
		this.id=id;
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

	@Column(name = "parentId")
	@Comment(ch = "父机构ID")
	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	@Column(name = "name", length = 40)
	@Length(min=1,max=40)
	@Comment(ch = "组织机构名称", en = "name", vtype = "required:true", len = 64, search = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	@Column(name = "remark", length = 128)
	@Length(min=0,max=128)
	@Comment(ch = "说明备注", en = "remark", vtype = "required:true", len = 128, search = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "code", length = 40)
	@Length(min=0,max=40)
	@Comment(ch = "组织编码", en = "code", len = 40, search = true)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "address1", length = 128)
	@Comment(ch = "地址 1", en = "address1", vtype = "", len = 128)
	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@Column(name = "address2", length = 128)
	@Comment(ch = "地址 2", en = "address2", vtype = "", len = 128)
	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@Column(name = "address3", length = 128)
	@Length(min=0,max=128)
	@Comment(ch = "地址 3", en = "address3", vtype = "", len = 128)
	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	@Column(name = "city", length = 128)
	@Length(min=0,max=128)
	@Comment(ch = "城市", en = "city", vtype = "", len = 128, search = true)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "stateAndProvince", length = 128)
	@Length(min=0,max=128)
	@Comment(ch = "州/省", en = "stateAndProvince", vtype = "", len = 128)
	public String getStateAndProvince() {
		return stateAndProvince;
	}

	public void setStateAndProvince(String stateAndProvince) {
		this.stateAndProvince = stateAndProvince;
	}

	@Column(name = "countryAndRegion", length = 128)
	@Length(min=0,max=128)
	@Comment(ch = "国家/地区", en = "countryAndRegion", vtype = "", len = 128)
	public String getCountryAndRegion() {
		return countryAndRegion;
	}

	public void setCountryAndRegion(String countryAndRegion) {
		this.countryAndRegion = countryAndRegion;
	}

	@Column(name = "postalCode", length = 8)
	@Length(min=0,max=8)
	@Comment(ch = "邮政编码", en = "postalCode", vtype = "", len = 8)
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Column(name = "mainPhone", length = 14)
	@Length(min=0,max=14)
	@Comment(ch = "主要联系电话", en = "mainPhone", vtype = "", len = 14)
	public String getMainPhone() {
		return mainPhone;
	}

	public void setMainPhone(String mainPhone) {
		this.mainPhone = mainPhone;
	}

	@Column(name = "alternatePhone", length = 14)
	@Length(min=0,max=14)
	@Comment(ch = "备用电话", en = "alternatePhone", vtype = "", len = 14)
	public String getAlternatePhone() {
		return alternatePhone;
	}

	public void setAlternatePhone(String alternatePhone) {
		this.alternatePhone = alternatePhone;
	}

	@Column(name = "fax", length = 14)
	@Length(min=0,max=14)
	@Comment(ch = "传真", en = "fax", vtype = "", len = 14)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}


	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "orgId", referencedColumnName = "id", nullable = true, insertable = true, updatable = true) })
	@Comment(ch = "域", en = "orgId", vtype = "required:true,validType:'integer'")
	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}
	
	@Column(name = "type")
	@Comment(ch = "机构类型", en = "type", vtype = "")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column(name = "customize1", length = 128)
	@Comment(ch = "自定义1", en = "customize1", len = 128)
	@Length(min = 0, max = 128)
	public String getCustomize1() {
		return customize1;
	}

	public void setCustomize1(String customize1) {
		this.customize1 = customize1;
	}

	@Column(name = "customize2", length = 128)
	@Comment(ch = "自定义2", en = "customize2", len = 128)
	@Length(min = 0, max = 128)
	public String getCustomize2() {
		return customize2;
	}

	public void setCustomize2(String customize2) {
		this.customize2 = customize2;
	}

	@Column(name = "customize3", length = 128)
	@Comment(ch = "自定义3", en = "customize3", len = 128)
	@Length(min = 0, max = 128)
	public String getCustomize3() {
		return customize3;
	}

	public void setCustomize3(String customize3) {
		this.customize3 = customize3;
	}

	@Column(name = "customize4", length = 128)
	@Comment(ch = "自定义4", en = "customize4", len = 128)
	@Length(min = 0, max = 128)
	public String getCustomize4() {
		return customize4;
	}

	public void setCustomize4(String customize4) {
		this.customize4 = customize4;
	}

	@Column(name = "customize5", length = 128)
	@Comment(ch = "自定义5", en = "customize5", len = 128)
	@Length(min = 0, max = 128)
	public String getCustomize5() {
		return customize5;
	}

	public void setCustomize5(String customize5) {
		this.customize5 = customize5;
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
