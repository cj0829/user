/**
 * Project Name:exam
 * File Name:ExamStrategy.java
 * Package Name:org.csr.exam.domain
 * Date:2015年6月23日上午11:03:24
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.csr.common.user.domain.User;
import org.csr.core.AutoSetProperty;
import org.csr.core.Comment;
import org.csr.core.persistence.domain.SimpleDomain;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * ClassName: ExamStrategy.java <br/>
 * System Name： elearning系统 <br/>
 * Date: 2015年6月23日上午11:03:24 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 功能描述： <br/>
 * 节点审批用户关系，
 */
@Entity
@Table(name = "f_taskNodeApprovalChain")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(en="pmt_flow_TaskNodeApprovalChain",ch = "节点审批用户关系")
public  class TaskNodeApprovalChain extends SimpleDomain<Long> {
	
	/** 自身 */
	public static final Integer Self = 1;
	/** 管理人 */
	public static final Integer Manager = 2;
	/** 2级管理人 */
	public static final Integer Manager2 = 3;
	/** 3级管理人 */
	public static final Integer Manager3 = 4;
	
	private static final long serialVersionUID = 461585922808887682L;
	/** 审批人编号 */
	private Long id;
	/** 所属流程节点 */
	private TaskNode taskNode;
	/** 批准人 */
	@AutoSetProperty(message = "批准人", required = false)
	private User approver;
	/** 类型(用户，关系) */
	private Integer type;
	/** 批准关系-字典数据 */
	private Integer approverReleation;
	
	private Long orgId;
	@Id
	@GeneratedValue(generator = "globalGenerator")
	@GenericGenerator(name = "globalGenerator", strategy = "org.csr.core.persistence.generator5.GlobalGenerator")
	@Column(name = "id", unique = true, nullable = false)
	@Comment(ch = "答案ID", en = "id", search = true)
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "taskNodeId", referencedColumnName = "id", nullable = true, insertable = true, updatable = true) })
	@Comment(ch = "任务节点", en = "taskNodeId")
	public TaskNode getTaskNode() {
		return taskNode;
	}

	public void setTaskNode(TaskNode taskNode) {
		this.taskNode = taskNode;
	}

	
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "approverId", referencedColumnName = "id", nullable = true, insertable = true, updatable = true) })
	@Comment(ch = "审批人", en = "approver")
	public User getApprover() {
		return approver;
	}

	public void setApprover(User approver) {
		this.approver = approver;
	}
	
	@Column(name = "type", nullable = false)
	@Comment(ch = "类型(用户，关系)", en = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column(name = "approverReleationId")
	@Comment(ch = "批准人关系", en = "approverReleationId")
	public Integer getApproverReleation() {
		return approverReleation;
	}

	public void setApproverReleation(Integer approverReleation) {
		this.approverReleation = approverReleation;
	}


	@Column(name = "orgId", nullable = false)
	@Comment(ch = "orgId", en = "orgId")
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
}
