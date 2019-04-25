package org.csr.common.user.domain;

// Generated Oct 29, 2013 10:37:52 AM by Hibernate Tools 3.2.0.b9

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.csr.core.Comment;
import org.csr.core.persistence.domain.RootDomain;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * 区域省关系表
 */
@Entity
@Table(name = "u_DistrictProvince")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch="区域省关系表省的数据来自数据字典",en="pmt_common_DistrictProvince")
public class DistrictProvince extends RootDomain<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6933065436625960561L;
	private Long id;
	private District district;
	private Province province;

	public DistrictProvince() {
	}

	public DistrictProvince(Long id) {
		this.id = id;
	}

	public DistrictProvince(District district, Province province) {
		this.district = district;
		this.province = province;
	}
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
	@Comment(ch="区域ID")
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "districtId", referencedColumnName = "id", nullable = true, insertable = true, updatable = true) })
	public District getDistrict() {
	    return district;
	}

	public void setDistrict(District district) {
	    this.district = district;
	}
	
	@Comment(ch="地区ID")
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "provinceId", referencedColumnName = "id", nullable = true, insertable = true, updatable = true) })
	public Province getProvince() {
	    return province;
	}

	public void setProvince(Province province) {
	    this.province = province;
	}
}
