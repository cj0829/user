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
 * 用户区域关系表
 */
@Entity
@Table(name = "u_UserDistrict")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch="用户区域关系表",en="pmt_common_UserDistrict")
public class UserDistrict extends RootDomain<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1233266824155686473L;
	private Long id;
	private Long userId;
	private District district;

	public UserDistrict() {
	}

	public UserDistrict(long id) {
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
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "userId")
	@Comment(ch="用户id",en = "userId")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "districtId", referencedColumnName = "id", nullable = true, insertable = true, updatable = true) })
	@Comment(ch="区域ID")
	public District getDistrict() {
		return this.district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

}
