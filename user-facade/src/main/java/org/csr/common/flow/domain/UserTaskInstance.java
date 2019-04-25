package org.csr.common.flow.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.csr.common.flow.entity.Form;
import org.csr.common.flow.entity.UserTaskImpl;
import org.csr.common.user.domain.User;
import org.csr.core.Comment;
import org.csr.core.persistence.domain.SimpleDomain;
import org.hibernate.validator.constraints.Length;

/**
 * ClassName:TaskFlow.java <br/>
 * System Name： 用户管理系统。<br/>
 * Date: 2014-3-7下午2:07:08 <br/>
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *        功能描述： 任务节点描述，<br/>
 *        公用方法描述： <br/>
 * 用来记录，用户审批过程，如果审批完成，将会删除用户任务记录。 <br/>
 * 审批的全过程，将记录在历史表。这样，将不需要过程表。当前的表就是过程表。 <br/>
 * 在审批的时候查询当前表中是否有记录。就说明是否有审批在
 */
@Entity
@Table(name = "f_UserTaskInstance")
@Comment(en="pmt_flow_UserTaskInstance",ch = "上报用户任务实例")
public class UserTaskInstance extends SimpleDomain<Long> implements UserTaskImpl {

	/**
	 * serialVersionUID:(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	/** 流程的起始人*/
	private User user;
	/** 当前流程的 当前状态 */
	private TaskNode current;
	/** 当前流程的 状态 是否异常 */
	private Boolean userTaskInstanceStatus;
	/** 当前审批的流程id */
	private Long taskTempId;

	private TaskInstance taskInstance;
	/** 表单内容数据 */
	private Long formId;
	/** 表单内容数据 */
	private Form formObj;
	/** 表单数据clas名称数据 */
	private String className;
	/** 当前节点的处理意见 */
	private String opinion;
	/** 当前节点的操作方式 */
	private Integer formOperType;
	
	public UserTaskInstance() {

	}

	public UserTaskInstance(User user, TaskNode current,
			TaskInstance taskInstance) {
		this.user = user;
		this.current = current;
		this.taskInstance = taskInstance;
	}

	public UserTaskInstance(Long id) {
		this.id=id;
	}

	/**
	 * 简单描述该方法的实现功能（可选）.
	 * @see org.csr.common.flow.domain.UserTaskInstance#getId()
	 */
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@Comment(ch = "ID", en = "id")
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * 简单描述该方法的实现功能（可选）.
	 * @see org.csr.common.flow.domain.UserTaskInstance#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 简单描述该方法的实现功能（可选）.
	 * @see org.csr.common.flow.domain.UserTaskInstance#getUser()
	 */
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "userId", referencedColumnName = "id", nullable = true, insertable = true, updatable = true) })
	@Comment(ch = "用户审批链ID", en = "userId")
	public User getUser() {
		return user;
	}

	/**
	 * 简单描述该方法的实现功能（可选）.
	 * @see org.csr.common.flow.domain.UserTaskInstance#setUser(org.csr.core.domain.User)
	 */
	public void setUser(User userApprovalChain) {
		this.user = userApprovalChain;
	}

	/**
	 * 简单描述该方法的实现功能（可选）.
	 * @see org.csr.common.flow.domain.UserTaskInstance#getUserTaskInstanceStatus()
	 */
	@Column(name = "userTaskInstanceStatus")
	@Comment(ch = "用户流程状态，true 正常，false 异常", en = "userTaskInstanceStatus")
	public Boolean getUserTaskInstanceStatus() {
		return userTaskInstanceStatus;
	}

	/**
	 * 简单描述该方法的实现功能（可选）.
	 * @see org.csr.common.flow.domain.UserTaskInstance#setUserTaskInstanceStatus(java.lang.Boolean)
	 */
	public void setUserTaskInstanceStatus(Boolean userTaskInstanceStatus) {
		this.userTaskInstanceStatus = userTaskInstanceStatus;
	}

	/**
	 * 简单描述该方法的实现功能（可选）.
	 * @see org.csr.common.flow.domain.UserTaskInstance#getCurrent()
	 */
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "currentTaskId", referencedColumnName = "id", nullable = true, insertable = true, updatable = true) })
	@Comment(ch = "当前任务节点", en = "currentTaskId")
	public TaskNode getCurrent() {
		return current;
	}

	/**
	 * 简单描述该方法的实现功能（可选）.
	 * @see org.csr.common.flow.domain.UserTaskInstance#setCurrent(org.csr.common.flow.domain.TaskNode)
	 */
	public void setCurrent(TaskNode current) {
		this.current = current;
	}

	/**
	 * 简单描述该方法的实现功能（可选）.
	 * @see org.csr.common.flow.domain.UserTaskInstance#getTaskTempId()
	 */
	@Column(name = "taskTempId")
	@Comment(ch = "审批流程模板id", en = "taskTempId")
	public Long getTaskTempId() {
		return taskTempId;
	}

	/**
	 * 简单描述该方法的实现功能（可选）.
	 * @see org.csr.common.flow.domain.UserTaskInstance#setTaskTempId(java.lang.Long)
	 */
	public void setTaskTempId(Long taskTempId) {
		this.taskTempId = taskTempId;
	}

	/**
	 * 简单描述该方法的实现功能（可选）.
	 * @see org.csr.common.flow.domain.UserTaskInstance#getTaskInstance()
	 */
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "taskInstanceId", referencedColumnName = "id", nullable = true, insertable = true, updatable = true) })
	@Comment(ch = "任务实例ID", en = "taskInstance")
	public TaskInstance getTaskInstance() {
		return taskInstance;
	}

	/**
	 * 简单描述该方法的实现功能（可选）.
	 * @see org.csr.common.flow.domain.UserTaskInstance#setTaskInstance(org.csr.common.flow.domain.TaskInstance)
	 */
	public void setTaskInstance(TaskInstance taskInstanceId) {
		this.taskInstance = taskInstanceId;
	}
	
	/**
	 * 简单描述该方法的实现功能（可选）.
	 * @see org.csr.common.flow.domain.UserTaskInstance#getFormId()
	 */
	@Override
	@Column(name = "formId")
	@Comment(ch = "formId", en = "formId")
	public Long getFormId() {
		return formId;
	}

	/**
	 * 简单描述该方法的实现功能（可选）.
	 * @see org.csr.common.flow.domain.UserTaskInstance#setFormId(java.lang.Long)
	 */
	@Override
	public void setFormId(Long formId) {
		this.formId = formId;
	}

	/**
	 * 简单描述该方法的实现功能（可选）.
	 * @see org.csr.common.flow.domain.UserTaskInstance#getFormObj()
	 */
	@Override
	@Column(name = "formObj", length = 64 * 1024)
	@Comment(ch = "对象二进制内容", en = "formObj")
	public Form getFormObj() {
		return formObj;
	}

	/**
	 * 简单描述该方法的实现功能（可选）.
	 * @see org.csr.common.flow.domain.UserTaskInstance#setFormObj(org.csr.common.flow.support.Form)
	 */
	@Override
	public void setFormObj(Form formObj) {
		this.formObj = formObj;
	}

	/**
	 * 简单描述该方法的实现功能（可选）.
	 * @see org.csr.common.flow.domain.UserTaskInstance#getClassName()
	 */
	@Override
	@Column(name = "className",length=128)
	@Comment(ch = "对象class名称", en = "className")
	@Length(max=128)
	public String getClassName() {
		return className;
	}

	/**
	 * 简单描述该方法的实现功能（可选）.
	 * @see org.csr.common.flow.domain.UserTaskInstance#setClassName(java.lang.String)
	 */
	@Override
	public void setClassName(String className) {
		this.className = className;
	}
	
	/**
	 * 简单描述该方法的实现功能（可选）.
	 * @see org.csr.common.flow.domain.UserTaskInstance#getOpinion()
	 */
	@Override
	@Column(name = "opinion",length=64)
	@Comment(ch = "当前节点的审批意见", en = "opinion")
	@Length(max=64)
	public String getOpinion() {
		return opinion;
	}
	@Column(name = "formOperType")
	@Comment(ch = "审批节点命令方式", en = "formOperType")
	public Integer getFormOperType() {
		return formOperType;
	}

	public void setFormOperType(Integer formOperType) {
		this.formOperType = formOperType;
	}

	/**
	 * 简单描述该方法的实现功能（可选）.
	 * @see org.csr.common.flow.domain.UserTaskInstance#setOpinion(java.lang.String)
	 */
	@Override
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
}
