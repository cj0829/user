package org.csr.common.user.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.csr.core.AutoSetProperty;
import org.csr.core.Comment;
import org.csr.core.Persistable;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="u_UserSafeResourceCollection")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(en="pmt_common_UserSafeResourceCollection")
public class UserSafeResourceCollection implements Persistable<Long>{
	private static final long serialVersionUID = 1L;

	/**id*/
	private Long id;
	/**用户的id*/
	@AutoSetProperty(message="用户的id")
	private User user;
	/**资源的id*/
	@AutoSetProperty(message="资源的id")
	private SafeResourceCollection safeResourceCollection;
	
	@Override
	@Id
	@GeneratedValue(generator = "globalGenerator")  
	@GenericGenerator(name = "globalGenerator", strategy = "org.csr.core.persistence.generator5.GlobalGenerator")  
	@Column(name = "id", unique = true, nullable = false)
	@Comment(ch = "ID", en = "id", search = true)
	public Long getId() {
		return this.id;
	}
	@Override
	public void setId(Long id) {
		this.id=id;
	}
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name="userId", referencedColumnName="id",nullable=true,insertable=true,updatable=true) })
    @Comment(ch="用户",en="userId")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name="safeResourceCollectionId", referencedColumnName="id",nullable=true,insertable=true,updatable=true) })
    @Comment(ch="安全资源集",en="safeResourceCollectionId")
	public SafeResourceCollection getSafeResourceCollection() {
		return safeResourceCollection;
	}
	public void setSafeResourceCollection(SafeResourceCollection safeResourceCollection) {
		this.safeResourceCollection = safeResourceCollection;
	}
}
