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

import org.csr.common.user.constant.AuthenticationMode;
import org.csr.core.Comment;
import org.csr.core.persistence.domain.RootDomain;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * 角色功能点关系表
 */
@Entity
@Table(name = "u_RoleFunctionPoint")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch="角色功能点关系表",en="pmt_common_RoleFunctionPoint")
public class RoleFunctionPoint extends RootDomain<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4807588590836325102L;
	private Long id;
	private Role role;
	private FunctionPoint functionPoint;
	/**
	 * 授权方式:1、不允许授权，2、默认已授权，3、需要授权
	 */
	private Byte authenticationMode=AuthenticationMode.MUST;
	
	public RoleFunctionPoint() {
	}

	public RoleFunctionPoint(Long id) {
		this.id = id;
	}

	public RoleFunctionPoint(Role role, FunctionPoint functionPoint) {
		this.role = role;
		this.functionPoint = functionPoint;
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
	@Comment(ch="角色ID")
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "roleId", referencedColumnName = "id", nullable = true, insertable = true, updatable = true) })
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Comment(ch="功能点ID")
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "functionPointId", referencedColumnName = "id", nullable = true, insertable = true, updatable = true) })
	public FunctionPoint getFunctionPoint() {
		return this.functionPoint;
	}

	public void setFunctionPoint(FunctionPoint functionPoint) {
		this.functionPoint = functionPoint;
	}
	
	@Column(name = "authenticationMode",length=1) 
	@Comment(ch="授权方式:1、不允许授权，2、默认已授权，3、需要授权")
	public Byte getAuthenticationMode() {
		return authenticationMode;
	}

	public void setAuthenticationMode(Byte authenticationMode) {
		this.authenticationMode = authenticationMode;
	}
}
