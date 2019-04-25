package org.csr.common.user.domain;

// Generated Oct 29, 2013 10:37:52 AM by Hibernate Tools 3.2.0.b9

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.csr.core.Comment;
import org.csr.core.persistence.domain.RootDomain;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * 用户角色关系表
 */
@Entity
@Table(name = "u_UserRole")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch="用户角色关系表",en="pmt_common_UserRole")
public class UserRole extends RootDomain<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 174568917250422664L;
	private Long id;
	private User user;
	private Role role;

	public UserRole() {
	}

	public UserRole(Long id) {
		this.id = id;
	}

	public UserRole(Long id, User user, Role role) {
		this.id = id;
		this.user = user;
		this.role = role;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")  
    @Comment(ch="用户ID")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user= user;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="roleId")  
	@Comment(ch="角色ID")
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
