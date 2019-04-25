/**
 * Project Name:user-facade
 * File Name:UserReceiveAddress.java
 * Package Name:org.csr.common.user.domain
 * Date:2016-12-16上午11:04:30
 * Copyright (c) 2016, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.csr.core.Comment;
import org.csr.core.persistence.domain.SimpleDomain;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * ClassName:UserReceiveAddress.java <br/>
 * System Name：    在线学习系统 <br/>
 * Date:     2016-12-16上午11:04:30 <br/>
 * @author   Administrator <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "pu_UserReceiveAddress")
@Comment(ch = "用户收货地址", en = "pu_UserReceiveAddress")
public class UserReceiveAddress extends SimpleDomain<Long>{
	/**
	 * (用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;
	/**用户收货地址id*/
	private Long id;
	/**用户*/
	private Long userId;
	
	/**收货人姓名*/
	private String consignee;
	/**获取收件人完整地址包括省市区*/
	private String completeAddress;
	
	/**邮政编码*/
	private String zipcode;
	/**电话*/
	private String phoneTel;
	/**是否设为默认地址 0.否 1.是*/
	private Integer isDefault;
	/**省*/
	private Long provinceCode;
	/**市*/
	private Long cityCode;
	/**区*/
	private Long districtCode;
	/**街道地址*/
	private String address;
	
	@Id
	@GeneratedValue(generator = "globalGenerator")  
	@GenericGenerator(name = "globalGenerator", strategy = "org.csr.core.persistence.generator5.GlobalGenerator")
	@Column(name = "id", unique = true, nullable = false)
	@Comment(ch = "编码", en = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "userId")
	@Comment(ch = "用户id", en = "userId")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Column(name = "name", length = 64,nullable = false)
	@Comment(ch="收货人姓名",en="consignee",vtype="required:true,validType:['length[1,64]']",len=64,search=true)
	@Length(min=1,max=64)
	@NotBlank
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	@Column(name = "completeAddress", length = 256)
	@Comment(ch = "收货人完整地址", en = "completeAddress", len = 256)
	@Length(min = 1, max = 256)
	public String getCompleteAddress() {
		return completeAddress;
	}
	public void setCompleteAddress(String completeAddress) {
		this.completeAddress = completeAddress;
	}
	@Column(name="address",length = 256,nullable = true)
	@Comment(ch="街道地址 ", en="address",vtype="required:true")
	@Length(min=1,max=256)
	@NotBlank
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name="zipcode",length = 6,nullable = true)
	@Comment(ch="邮政编码 ", en="zipcode",vtype="required:true")
	@Length(min=6,max=6)
	@NotBlank
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	@Column(name = "phoneTel",nullable = false)
	@Comment(ch="联系方式",en="phoneTel",vtype="required:true")
	@NotBlank
	public String getPhoneTel() {
		return phoneTel;
	}
	public void setPhoneTel(String phoneTel) {
		this.phoneTel = phoneTel;
	}
	@Column(name="isDefault",length = 1)
	@Comment(ch="是否为默认地址", en="isDefault",vtype="required:true")
	@NotNull
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	@Column(name="provinceCode",length = 5,nullable = true)
	@Comment(ch="省编码 ", en="provinceCode",vtype="required:true")
	@Length(min=1,max=5)
	@NotEmpty
	public Long getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(Long provinceCode) {
		this.provinceCode = provinceCode;
	}
	@Column(name="cityCode",length = 5,nullable = true)
	@Comment(ch="市编码 ", en="cityCode",vtype="required:true")
	@Length(min=1,max=5)
	@NotEmpty
	public Long getCityCode() {
		return cityCode;
	}
	public void setCityCode(Long cityCode) {
		this.cityCode = cityCode;
	}
	@Column(name="districtCode",length = 5,nullable = true)
	@Comment(ch="区编码 ", en="districtCode",vtype="required:true")
	@Length(min=1,max=5)
	@NotEmpty
	public Long getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(Long districtCode) {
		this.districtCode = districtCode;
	}
	public UserReceiveAddress() {
		super();
	}
	public UserReceiveAddress(Long id, Long userId, String consignee,
			String completeAddress, String zipcode, String phoneTel,
			Integer isDefault, Long provinceCode, Long cityCode,
			Long districtCode, String address) {
		super();
		this.id = id;
		this.userId = userId;
		this.consignee = consignee;
		this.completeAddress = completeAddress;
		this.zipcode = zipcode;
		this.phoneTel = phoneTel;
		this.isDefault = isDefault;
		this.provinceCode = provinceCode;
		this.cityCode = cityCode;
		this.districtCode = districtCode;
		this.address = address;
	}
	
}

