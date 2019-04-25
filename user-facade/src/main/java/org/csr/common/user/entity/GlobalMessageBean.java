/**
 * Project Name:exam
 * File Name:ExamMessage.java
 * Package Name:org.csr.exam.domain
 * Date:2015年6月23日上午10:36:01
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.entity;
import java.util.Date;
import java.util.List;

import org.csr.common.user.domain.GlobalMessage;
import org.csr.core.constant.YesorNo;
import org.csr.core.web.bean.VOBase;

/**
 * ClassName: ExamMessage.java <br/>
 * System Name：    elearning系统 <br/>
 * Date:     2015年6月23日上午10:36:01 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 * 功能描述：  <br/>
 */
public class GlobalMessageBean extends VOBase<Long> {


	private static final long serialVersionUID = 1L;
	/**id*/
	private Long id;
	/**消息类型*/
	private Integer messageType;
	/**消息内容*/
	private String messageContent;
	/**是否已读(默认是未读)*/
	private Byte isRead=YesorNo.NO;
	/**用户ID*/
	private Long userId;

	/**消息创建时间*/
	private Date createTime;
	
	/**时间间隔*/
	private Long timeout;
	/**消息beanlist*/
	private List<GlobalMessageBean> messages;
	/**消息个数*/
	private Integer messageNum;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getMessageType() {
		return messageType;
	}
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}
	
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	
	public Byte getIsRead() {
		return isRead;
	}
	public void setIsRead(Byte isRead) {
		this.isRead = isRead;
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Long getTimeout() {
		return timeout;
	}
	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}
	public List<GlobalMessageBean> getMessages() {
		return messages;
	}
	public void setMessages(List<GlobalMessageBean> messages) {
		this.messages = messages;
	}
	public Integer getMessageNum() {
		return messageNum;
	}
	public void setMessageNum(Integer messageNum) {
		this.messageNum = messageNum;
	}
	public static GlobalMessageBean toBean(GlobalMessage doMain) {
		GlobalMessageBean bean = new GlobalMessageBean();
		bean.setId(doMain.getId());
		bean.setMessageType(doMain.getMessageType());
		bean.setMessageContent(doMain.getMessageContent());
		bean.setIsRead(doMain.getIsRead());
		bean.setUserId(doMain.getUserId());
		bean.setCreateTime(doMain.getCreateTime());
		return bean;
	}
	
	public static GlobalMessageBean toBean(Long timeout,List<GlobalMessageBean> messages,Integer messageNum) {
		GlobalMessageBean bean = new GlobalMessageBean();
		bean.setTimeout(timeout);
		bean.setMessages(messages);
		bean.setMessageNum(messageNum);
		return bean;
	}
	
}

