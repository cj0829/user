/*
 * Copyright (c) 2006, Pointdew Inc. All rights reserved.
 * 
 * http://www.pointdew.com
 */
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
import javax.validation.constraints.NotNull;

import org.csr.common.storage.domain.Datastream;
import org.csr.core.AutoSetProperty;
import org.csr.core.Comment;
import org.csr.core.persistence.domain.SimpleDomain;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

/**
 * ClassName:TopicComments.java <br/>
 * System Name：    在线学习系统  <br/>
 * Date:     2016-11-21下午4:55:00 <br/>
 * @author   Administrator <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Entity
@Table(name = "pc_MessageBoard")
@Comment(ch = "留言板", en = "pmt_cms_topicComments")
public class MessageBoard extends SimpleDomain<Long> {

	private static final long serialVersionUID = 6020860394629878311L;
	private Long id;
	/**留言对象（对退款的留言，对维修的留言）*/
	private Long objectId;
	/**留言类型（退款，维修，一切只需要简单）*/
	private Integer objectType;
	/**留言内容*/
	private String content;
	
	@AutoSetProperty(message="图片一",required=false)
	private Datastream voucherImg1;
	@AutoSetProperty(message="图片二",required=false)
	private Datastream voucherImg2;
	@AutoSetProperty(message="图片三",required=false)
	private Datastream voucherImg3;
	@AutoSetProperty(message="图片四",required=false)
	private Datastream voucherImg4;
	@AutoSetProperty(message="图片五",required=false)
	private Datastream voucherImg5;
	
	@Id
	@GeneratedValue(generator = "globalGenerator")  
	@GenericGenerator(name = "globalGenerator", strategy = "org.csr.core.persistence.generator5.GlobalGenerator")  
	@Override
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "parentId")
	@Comment(ch="留言对象",en="parentId",vtype="required:true")
	@NotNull
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	
	@Column(name = "objectType")
	@Comment(ch="留言类型",en="objectType",vtype="required:true")
	@NotNull
	public Integer getObjectType() {
		return objectType;
	}
	
	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}
	
	@Column(name = "content", length = 256)
	@Comment(ch="留言内容",en="content",vtype="validType:['length[0,256]']")
	@Length(max=254)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@ManyToOne(cascade={},fetch=FetchType.LAZY)
	@JoinColumns({@JoinColumn(name="voucherImg1",referencedColumnName="id",nullable=true,insertable=true,updatable=true)})
	@Comment(ch="图片一",en="voucherImg1")
	public Datastream getVoucherImg1() {
		return voucherImg1;
	}
	public void setVoucherImg1(Datastream voucherImg1) {
		this.voucherImg1 = voucherImg1;
	}
	@ManyToOne(cascade={},fetch=FetchType.LAZY)
	@JoinColumns({@JoinColumn(name="voucherImg2",referencedColumnName="id",nullable=true,insertable=true,updatable=true)})
	@Comment(ch="图片二",en="voucherImg2")
	public Datastream getVoucherImg2() {
		return voucherImg2;
	}
	public void setVoucherImg2(Datastream voucherImg2) {
		this.voucherImg2 = voucherImg2;
	}
	@ManyToOne(cascade={},fetch=FetchType.LAZY)
	@JoinColumns({@JoinColumn(name="voucherImg3",referencedColumnName="id",nullable=true,insertable=true,updatable=true)})
	@Comment(ch="图片三",en="voucherImg3")
	public Datastream getVoucherImg3() {
		return voucherImg3;
	}
	public void setVoucherImg3(Datastream voucherImg3) {
		this.voucherImg3 = voucherImg3;
	}
	@ManyToOne(cascade={},fetch=FetchType.LAZY)
	@JoinColumns({@JoinColumn(name="voucherImg4",referencedColumnName="id",nullable=true,insertable=true,updatable=true)})
	@Comment(ch="图片四",en="voucherImg4")
	public Datastream getVoucherImg4() {
		return voucherImg4;
	}
	public void setVoucherImg4(Datastream voucherImg4) {
		this.voucherImg4 = voucherImg4;
	}
	@ManyToOne(cascade={},fetch=FetchType.LAZY)
	@JoinColumns({@JoinColumn(name="voucherImg5",referencedColumnName="id",nullable=true,insertable=true,updatable=true)})
	@Comment(ch="图片五",en="voucherImg5")
	public Datastream getVoucherImg5() {
		return voucherImg5;
	}
	public void setVoucherImg5(Datastream voucherImg5) {
		this.voucherImg5 = voucherImg5;
	}
}
