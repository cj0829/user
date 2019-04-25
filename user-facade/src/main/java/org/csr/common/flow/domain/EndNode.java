/**
 * Project Name:common
 * File Name:EndNode.java
 * Package Name:org.csr.common.flow.domain
 * Date:2014-3-7下午2:27:15
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.csr.core.Comment;

/**
 * ClassName: EndNode.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014-3-7下午2:27:15 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： 结束节点<br/>
 *        公用方法描述： <br/>
 * 
 */
@Entity
public class EndNode extends TaskNode {

	/**
	 * (用一句话描述这个变量表示什么).
	 * 
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;

	/** 当前任务结束时间 */
	private Date endTime;

	public EndNode() {
		super();
		this.nodeType = "EndNode";
	}

	public EndNode(String name) {
		super(name);
		this.nodeType = "EndNode";
	}

	@Column(name = "endTime")
	@Temporal(TemporalType.TIMESTAMP)
	@Comment(ch = "结束时间", en = "endTime")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
