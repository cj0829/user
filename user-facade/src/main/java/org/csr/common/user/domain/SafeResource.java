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

/**
 * ClassName:SafeResource.java <br/>
 * System Name：    签到系统  <br/>
 * Date:     2015-11-20上午9:51:15 <br/>
 * @author   liurui <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 安全资源：  <br/>
 * 任何要访问的数据 都可以定义为安装资源。<br/>
 * 并且需要抽象出安全资源接口。<br/>
 * 
 * 
 */
@Entity
@Table(name = "u_SafeResource")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(en="pmt_common_SafeResource")
public class SafeResource implements Persistable<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**id*/
	private Long id;
	/**编码*/
	private String code;
	/**类型:SafeResourceType*/
	private Integer type;
	/**安全资源集*/
	@AutoSetProperty(message="安全资源集",required=false)
	private SafeResourceCollection safeResourceCollection;
	/**叶子节点*/
	private Long tipId;
	/**叶子类型 (域，分类，资源)*/
	private Integer tipType;
	
	public SafeResource() {}
	public SafeResource(Long id) {
		this.id = id;
	}

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
	@Column(name="code")
	@Comment(ch="编码",en="code")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(name="type")
	@Comment(ch="类型",en="type")
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	@Column(name="tipId")
	@Comment(ch="梢",en="tipId")
	public Long getTipId() {
		return tipId;
	}
	public void setTipId(Long tipId) {
		this.tipId = tipId;
	}
	@Column(name="tipType")
	@Comment(ch="梢的类型",en="tipType")
	public Integer getTipType() {
		return tipType;
	}
	public void setTipType(Integer tipType) {
		this.tipType = tipType;
	}

}
