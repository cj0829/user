/**
 * Project Name:exam
 * File Name:ExamMessage.java
 * Package Name:org.csr.exam.domain
 * Date:2015年6月23日上午10:36:01
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.csr.core.Comment;
import org.csr.core.Persistable;
import org.csr.core.constant.YesorNo;
import org.csr.core.persistence.domain.SimpleDomain;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * ClassName: ExamMessage.java <br/>
 * System Name：    elearning系统 <br/>
 * Date:     2015年6月23日上午10:36:01 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 * 功能描述：  <br/>
 */
@Entity
@Table(name = "u_GlobalMessage")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch = "消息",en="pmt_common_GlobalMessage")
public class GlobalMessage extends SimpleDomain<Long> {

	private static final long serialVersionUID = 1L;
	/**id*/
	private Long id;
	/**消息类型*/
	private Integer messageType;
	/**消息对象*/
	@SuppressWarnings("rawtypes")
	private Persistable object;
	/**操作*/
	private Integer oper;
	/**消息内容*/
	private String messageContent;
	/**是否已读(默认是未读)*/
	private Byte isRead=YesorNo.NO;
	/**用户ID*/
	private Long userId;
	
	@Id
	@GeneratedValue(generator = "globalGenerator")  
	@GenericGenerator(name = "globalGenerator", strategy = "org.csr.core.persistence.generator5.GlobalGenerator")  
	@Column(name = "id", unique = true, nullable = false)
	@Comment(ch = "ID", en = "id", search = true)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "messageType")
	@Comment(ch="消息类型",en = "messageType")
	public Integer getMessageType() {
		return messageType;
	}
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}
	
	@SuppressWarnings("rawtypes")
	@Column(name = "object", length = 64 * 1024)
	@Comment(ch = "对象二进制内容", en = "object")
	public Persistable getObject() {
		return object;
	}
	@SuppressWarnings("rawtypes")
	public void setObject(Persistable object) {
		this.object = object;
	}
	
	@Column(name = "oper")
	@Comment(ch="操作",en = "oper")
	public Integer getOper() {
		return oper;
	}
	public void setOper(Integer oper) {
		this.oper = oper;
	}
	
	@Column(name = "messageContent")
	@Comment(ch="消息内容",en = "messageContent")
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	
	@Column(name = "isRead")
	@Comment(ch="是否已读",en = "isRead")
	public Byte getIsRead() {
		return isRead;
	}
	public void setIsRead(Byte isRead) {
		this.isRead = isRead;
	}
	
	@Column(name = "userId")
	@Comment(ch="用户ID",en = "userId")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}

