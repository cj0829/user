/**
 * Project Name:exam
 * File Name:TaskNodeBean.java
 * Package Name:org.csr.common.flow.result
 * Date:2015年6月29日上午10:19:17
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.entity;

import org.csr.core.web.bean.VOBase;

/**
 * ClassName: TaskNodeBean.java <br/>
 * System Name： elearning系统 <br/>
 * Date: 2015年6月29日上午10:19:17 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 功能描述： <br/>
 */
public class TaskNodeStrategyBean extends VOBase<Long>{

	/**
	 * serialVersionUID:(用一句话描述这个变量表示什么).
	 * 
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;

	/** 节点id */
	private Long taskNodeId;
	
	private Long userTaskInstanceId = -1l;
	/*** 策略 */
	/** 当前节点，是否能够关闭流程(删除就是关闭) */
	protected Byte isClose = 0;
	/** 当前节点，是否能够编辑(移动) */
	protected Byte isEdit = 0;
	/** 当前节点，是否按照顺序审批 */
	protected Byte isOrderApproval = 0;
	/** 是否要求全部审批 */
	protected Byte isAllApproval;

	protected String taskNodeName;

	public Long getTaskNodeId() {
		return taskNodeId;
	}

	public void setTaskNodeId(Long taskNodeId) {
		this.taskNodeId = taskNodeId;
	}

	
	public Long getUserTaskInstanceId() {
		return userTaskInstanceId;
	}

	public void setUserTaskInstanceId(Long userTaskInstanceId) {
		this.userTaskInstanceId = userTaskInstanceId;
	}

	public Byte getIsClose() {
		return isClose;
	}

	public void setIsClose(Byte isClose) {
		this.isClose = isClose;
	}

	public Byte getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(Byte isEdit) {
		this.isEdit = isEdit;
	}

	public Byte getIsOrderApproval() {
		return isOrderApproval;
	}

	public void setIsOrderApproval(Byte isOrderApproval) {
		this.isOrderApproval = isOrderApproval;
	}

	public Byte getIsAllApproval() {
		return isAllApproval;
	}

	public void setIsAllApproval(Byte isAllApproval) {
		this.isAllApproval = isAllApproval;
	}

	public String getTaskNodeName() {
		return taskNodeName;
	}

	public void setTaskNodeName(String taskNodeName) {
		this.taskNodeName = taskNodeName;
	}

}
