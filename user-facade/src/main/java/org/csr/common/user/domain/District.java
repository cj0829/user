package org.csr.common.user.domain;

// Generated Oct 29, 2013 10:37:52 AM by Hibernate Tools 3.2.0.b9

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.csr.core.Comment;
import org.csr.core.persistence.domain.SimpleDomain;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * 区域
 */
@Entity
@Table(name = "u_District")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch="区域",en="pmt_common_District")
public class District extends SimpleDomain<Long>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -62272773617940517L;
	private Long id;
	private String name;
	protected String remark;
	
	public District() {
	}

	public District(Long id) {
		this.id = id;
	}

	public District(Long id, String name, String remark) {
		this.id = id;
		this.name = name;
		this.remark = remark;
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

	@Column(name = "name", length = 128)
	@Comment(ch="区域名称")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "remark",length=255)
	@Comment(ch="备注")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
