/**
 * Project Name:exam
 * File Name:TaskNodeBean.java
 * Package Name:org.csr.common.flow.result
 * Date:2015年6月29日上午10:19:17
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.entity;

import java.util.Date;

import org.csr.common.flow.domain.TaskNode;
import org.csr.core.util.ObjUtil;
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
public class TaskNodeBean extends VOBase<Long>{

	/**
	 * serialVersionUID:(用一句话描述这个变量表示什么).
	 * 
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	/** 任务节点 */
	private String name;
	/** 审批流程模板模板 */
	private Long taskTempId;
	private String taskTempName;
	/** 下一个任务 */
	private Long toNodeId;
	private String toNodeName;
	/** 被驳回，返回的任务节点 */
	private Long rejectNodeId;
	private String rejectNodeName;
	/** 当前任务启动时间 */
	private Date startTime;
	/** 当前任务的间隔时间 */
	private Long intervalTime;
	/** 当前任务说明 */
	private String remark;
	/** 审批链id */
	private String chainId;
	/** 审批链name */
	private String chainName;
	/** 节点类型 */
	private String nodeType;
	/***策略*/
	/**当前节点，是否能够关闭流程(删除就是关闭)*/
	private Byte isClose;
	/**当前节点，是否能够编辑(移动)*/
	private Byte isEdit;
	/**当前节点，是否按照顺序审批*/
	private Byte isOrderApproval;
	/**是否要求全部审批*/
	private Byte isAllApproval;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getTaskTempId() {
		return taskTempId;
	}

	public void setTaskTempId(Long taskTempId) {
		this.taskTempId = taskTempId;
	}

	public String getTaskTempName() {
		return taskTempName;
	}

	public void setTaskTempName(String taskTempName) {
		this.taskTempName = taskTempName;
	}

	public Long getToNodeId() {
		return toNodeId;
	}

	public void setToNodeId(Long toNodeId) {
		this.toNodeId = toNodeId;
	}

	public String getToNodeName() {
		return toNodeName;
	}

	public void setToNodeName(String toNodeName) {
		this.toNodeName = toNodeName;
	}

	public Long getRejectNodeId() {
		return rejectNodeId;
	}

	public void setRejectNodeId(Long rejectNodeId) {
		this.rejectNodeId = rejectNodeId;
	}

	public String getRejectNodeName() {
		return rejectNodeName;
	}

	public void setRejectNodeName(String rejectNodeName) {
		this.rejectNodeName = rejectNodeName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Long getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(Long intervalTime) {
		this.intervalTime = intervalTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getChainId() {
		return chainId;
	}

	public void setChainId(String chainId) {
		this.chainId = chainId;
	}

	public String getChainName() {
		return chainName;
	}

	public void setChainName(String chainName) {
		this.chainName = chainName;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
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

	public static TaskNodeBean wrapBean(TaskNode doMain) {
		TaskNodeBean bean = new TaskNodeBean();
		bean.setId(doMain.getId());
		bean.setNodeType(doMain.getNodeType());
		
		bean.setName(doMain.getName());
		bean.setIntervalTime(doMain.getIntervalTime());
		if (ObjUtil.isNotEmpty(doMain.getRejectNode())) {
			bean.setRejectNodeId(doMain.getRejectNode().getId());
			bean.setRejectNodeName(doMain.getRejectNode().getName());
		}
		if (ObjUtil.isNotEmpty(doMain.getToNode())) {
			bean.setToNodeId(doMain.getToNode().getId());
			bean.setToNodeName(doMain.getToNode().getName());
		}
		if (ObjUtil.isNotEmpty(doMain.getTaskTemp())) {
			bean.setTaskTempId(doMain.getTaskTemp().getId());
			bean.setTaskTempName(doMain.getTaskTemp().getName());
		}
		bean.setRemark(doMain.getRemark());
		bean.setStartTime(doMain.getStartTime());
		//节点策略
		bean.setIsClose(doMain.getIsClose());
		bean.setIsEdit(doMain.getIsEdit());
		bean.setIsOrderApproval(doMain.getIsOrderApproval());
		bean.setIsAllApproval(doMain.getIsAllApproval());
		return bean;
	}
}
