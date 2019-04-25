package org.csr.common.user.domain;

// Generated Oct 29, 2013 10:37:52 AM by Hibernate Tools 3.2.0.b9

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.csr.core.Comment;
import org.csr.core.persistence.domain.SimpleDomain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

/**
 * 角色
 */
@Entity
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "u_Role")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch="角色",en="pmt_common_Role")
public class Role extends SimpleDomain<Long> {

	/**
	 * 当前的全局的功能点
	 */
	public static final Long global = 1l;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7806297233086099628L;
	private Long id;
	private String name;
	private Byte roleType;
	private Long orgId;
	protected String remark;
	
	public Role() {
	}

	public Role(Long id) {
		this.id = id;
	}

	public Role(Long id, String name, Byte roleType, String remark,
			Long orgId) {
		this.id = id;
		this.name = name;
		this.roleType = roleType;
		this.remark = remark;
		this.orgId = orgId;
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
	@Column(name = "name", length = 40)
	@Length(min=1,max=40)
	@Comment(ch="安全角色名称",en="name",len=40)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "roleType",length=1)
	@Comment(ch="角色类型：1.系统管理员；2.自定义角色")
	public Byte getRoleType() {
		return this.roleType;
	}

	public void setRoleType(Byte roleType) {
		this.roleType = roleType;
	}
	
	@Column(name = "orgId", nullable = false)
	@Comment(ch="域id")
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Column(name = "remark",length=255)
	@Length(min=0,max=255)
	@Comment(ch="备注",len=255)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
