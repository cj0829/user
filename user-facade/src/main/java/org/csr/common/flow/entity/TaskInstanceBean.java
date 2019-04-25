package org.csr.common.flow.entity;

import org.csr.core.web.bean.VOBase;

/**
 * ClassName:TaskInstanceResult.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014年3月17日下午11:01:47 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 */
public class TaskInstanceBean extends VOBase<Long>{

	/**
	 * serialVersionUID:(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	/** 任务 */
	private String taskName;

	private Long taskTempId;
	/** 流程1.激活状态，2.是否暂停流程,3,流程结束，4，异常结束 */
	private Integer taskInstanceStatus;
	/** 流程1.激活状态，2.是否暂停流程,3,流程结束，4，异常结束 */
	private String taskInstanceStatusName;
	/** 任务总数 */
	private Integer total;
	/** 完成任务数 */
	private Integer completionNumber;

	private String userNames;

	private String userIds;

	public TaskInstanceBean() {
	}

	public TaskInstanceBean(Long id, String taskName, Long taskTempId,
			Integer taskInstanceStatus, Integer total,
			Integer completionNumber) {
		this.id = id;
		this.taskName = taskName;
		this.taskTempId = taskTempId;
		this.total = total;
		this.completionNumber = completionNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Long getTaskTempId() {
		return taskTempId;
	}

	public void setTaskTempId(Long taskTempId) {
		this.taskTempId = taskTempId;
	}

	public Integer getTaskInstanceStatus() {
		return taskInstanceStatus;
	}

	public void setTaskInstanceStatus(Integer taskInstanceStatus) {
		this.taskInstanceStatus = taskInstanceStatus;
	}

	public String getTaskInstanceStatusName() {
		return taskInstanceStatusName;
	}

	public void setTaskInstanceStatusName(String taskInstanceStatusName) {
		this.taskInstanceStatusName = taskInstanceStatusName;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getCompletionNumber() {
		return completionNumber;
	}

	public void setCompletionNumber(Integer completionNumber) {
		this.completionNumber = completionNumber;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
}
