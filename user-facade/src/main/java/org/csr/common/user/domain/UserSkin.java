package org.csr.common.user.domain;

// Generated Oct 29, 2013 10:37:52 AM by Hibernate Tools 3.2.0.b9

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.csr.core.Comment;
import org.csr.core.persistence.domain.RootDomain;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * UserSkin generated by hbm2java
 */
@Entity
@Table(name = "u_UserSkin")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(en="pmt_common_UserSkin")
public class UserSkin extends RootDomain<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6046260066397442112L;
	private Long id;
	private Long userId;

	public UserSkin() {
	}

	public UserSkin(Long id) {
		setId(id);
	}

	public UserSkin(Long id, Long userId) {
		setId(id);
		this.userId = userId;
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
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}