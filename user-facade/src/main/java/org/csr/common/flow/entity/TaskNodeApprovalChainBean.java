/**
 * Project Name:exam
 * File Name:ExamStrategy.java
 * Package Name:org.csr.exam.domain
 * Date:2015年6月23日上午11:03:24
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.entity;

import org.csr.common.flow.domain.TaskNodeApprovalChain;
import org.csr.common.user.entity.UserBean;
import org.csr.core.constant.YesorNo;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.bean.VOBase;

/**
 * ClassName: ExamStrategy.java <br/>
 * System Name： elearning系统 <br/>
 * Date: 2015年6月23日上午11:03:24 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 功能描述： <br/>
 *        节点审批用户关系，
 */
public class TaskNodeApprovalChainBean extends VOBase<Long> {

	/**
	 * serialVersionUID:(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;
	/** 审批人编号 */
	private Long id;
	/** 所属流程节点 */
	private Long taskNodeId;
	private String taskNodeName;
//	/** 批准人 */
	private UserBean approver;
	/** 类型(用户，关系) */
	private Integer type;
	/** 批准关系-字典数据 */
	private Integer approverReleation;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTaskNodeId() {
		return taskNodeId;
	}

	public void setTaskNodeId(Long taskNodeId) {
		this.taskNodeId = taskNodeId;
	}

	public String getTaskNodeName() {
		return taskNodeName;
	}

	public void setTaskNodeName(String taskNodeName) {
		this.taskNodeName = taskNodeName;
	}

	public UserBean getApprover() {
		return approver;
	}

	public void setApprover(UserBean approver) {
		this.approver = approver;
	}

	public Byte getIsNew(){
		return YesorNo.NO;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

//	public String getTypeName() {
//		if (ObjUtil.isNotEmpty(type)) {
//			return DictionaryUtil.getDictName("examApproverType", type.toString());
//		}
//		return "";
//	}

	public Integer getApproverReleation() {
		return approverReleation;
	}

	public void setApproverReleation(Integer approverReleation) {
		this.approverReleation = approverReleation;
	}

//	public String getApproverReleationName() {
//		if (ObjUtil.isNotEmpty(approverReleation)) {
//			return DictionaryUtil.getDictName("approvalChainType", approverReleation.toString());
//		}
//		return "";
//	}

	public static TaskNodeApprovalChainBean wrapBean(TaskNodeApprovalChain doMain) {
		TaskNodeApprovalChainBean bean = new TaskNodeApprovalChainBean();
		bean.setId(doMain.getId());
		bean.setApproverReleation(doMain.getApproverReleation());
		bean.setType(doMain.getType());
		bean.setStatus(SUCCESS);
		if (ObjUtil.isNotEmpty(doMain.getTaskNode())) {
			bean.setTaskNodeId(doMain.getTaskNode().getId());
			bean.setTaskNodeName(doMain.getTaskNode().getName());
		}
		if (ObjUtil.isNotEmpty(doMain.getApprover())) {
			bean.setApprover(UserBean.wrapBean(doMain.getApprover()));
		}
		return bean;
	}
}
