/**
 * Project Name:exam
 * File Name:ExamStrategy.java
 * Package Name:org.csr.exam.domain
 * Date:2015年6月23日上午11:03:24
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.domain;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.csr.core.Comment;
import org.csr.core.constant.YesorNo;
import org.csr.core.persistence.domain.SimpleDomain;

/**
 * ClassName: ExamStrategy.java <br/>
 * System Name： elearning系统 <br/>
 * Date: 2015年6月23日上午11:03:24 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 功能描述： <br/>
 *        考试策略
 */
@MappedSuperclass
public abstract class FlowStrategy extends SimpleDomain<Long> {
	/**
	 * serialVersionUID:(用一句话描述这个变量表示什么).
	 * 
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 461585922808887682L;

	/** 新建 是否需要审批 */
	private Byte isNewApproval = YesorNo.NO;
	/** 修改 是否需要审批 */
	private Byte isEditApproval = YesorNo.NO;
	/** 删除 是否需要审批 */
	private Byte isDeleteApproval = YesorNo.NO;
	/** 变更库  是否需要审批 */
	private Byte isChangeCategoryApproval = YesorNo.NO;

	private Long orgId;
	/** 审批流程模板模板 */
	private TaskTemp taskTemp;

	@Column(name = "isNewApproval", nullable = false)
	@Comment(ch = "添加是否需要", en = "isNewApproval")
	public Byte getIsNewApproval() {
		return isNewApproval;
	}

	public void setIsNewApproval(Byte isNewApproval) {
		this.isNewApproval = isNewApproval;
	}

	@Column(name = "isEditApproval", nullable = false)
	@Comment(ch = "编辑是否需要", en = "isEditApproval")
	public Byte getIsEditApproval() {
		return isEditApproval;
	}

	public void setIsEditApproval(Byte isEditApproval) {
		this.isEditApproval = isEditApproval;
	}

	@Column(name = "isDeleteApproval", nullable = false)
	@Comment(ch = "删除是否需要", en = "isDeleteApproval")
	public Byte getIsDeleteApproval() {
		return isDeleteApproval;
	}

	public void setIsDeleteApproval(Byte isDeleteApproval) {
		this.isDeleteApproval = isDeleteApproval;
	}

	@Column(name = "isChangeCategoryApproval", nullable = false)
	@Comment(ch = "删除是否需要", en = "isChangeCategoryApproval")
	public Byte getIsChangeCategoryApproval() {
		return isChangeCategoryApproval;
	}

	public void setIsChangeCategoryApproval(Byte isChangeCategoryApproval) {
		this.isChangeCategoryApproval = isChangeCategoryApproval;
	}
	@Column(name = "orgId", nullable = false)
	@Comment(ch = "orgId", en = "orgId")
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "taskTempId", referencedColumnName = "id", nullable = true, insertable = true, updatable = true) })
	@Comment(ch = "审批模板", en = "taskTempId")
	public TaskTemp getTaskTemp() {
		return taskTemp;
	}

	public void setTaskTemp(TaskTemp taskTemp) {
		this.taskTemp = taskTemp;
	}

}
