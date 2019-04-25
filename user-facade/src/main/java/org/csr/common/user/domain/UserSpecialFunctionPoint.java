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
 * 用户权限
 */
@Entity
@Table(name = "u_UserSpecialFunctionPoint")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch="用户权限",en="pmt_common_UserSpecialFunctionPoint")
public class UserSpecialFunctionPoint extends RootDomain<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8263429926949463256L;
	private Long id;
	private User user;
	private FunctionPoint functionPoint;
	
	/**
	 * 1 是；2 否
     * 增加权限就是给用户赋予该功能的权限；否则就是把该功能权限取消
	 */
	private Byte isAddPrivilege;

	public UserSpecialFunctionPoint() {
	}

	public UserSpecialFunctionPoint(Long id) {
		this.id = id;
	}

	public UserSpecialFunctionPoint(User user, FunctionPoint functionPoint,
			Byte isAddPrivilege) {
		this.user = user;
		this.functionPoint = functionPoint;
		this.isAddPrivilege = isAddPrivilege;
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

	@Comment(ch="用户ID")
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "userId", referencedColumnName = "id", nullable = true, insertable = true, updatable = true) })
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
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

	@Column(name = "isAddPrivilege",length=1)
	@Comment(ch="1 是；2 否   增加权限就是给用户赋予该功能的权限；否则就是把该功能权限取消；")
	public Byte getIsAddPrivilege() {
		return this.isAddPrivilege;
	}

	public void setIsAddPrivilege(Byte isAddPrivilege) {
		this.isAddPrivilege = isAddPrivilege;
	}

}
