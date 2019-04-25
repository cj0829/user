package org.csr.common.flow.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.csr.core.Comment;
import org.csr.core.persistence.domain.SimpleDomain;
import org.hibernate.annotations.GenericGenerator;

/**
 * ClassName:TaskFlowInstance.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014-3-7下午2:25:36 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： 工作流程实例 <br/>
 *        公用方法描述： <br/>
 */
@Entity
@Table(name = "f_TaskInstance")
@Comment(en="pmt_flow_TaskInstance",ch ="任务实例包：能够包含多个任务实例，针对多用户数据包")
public class TaskInstance extends SimpleDomain<Long> {

	private static final long serialVersionUID = 1L;

	private String name;
	/** 任务 */
	private TaskTemp taskTemp;
	/** 流程1.激活状态，2.是否暂停流程,3,流程结束，4，异常结束 */
	private Integer taskInstanceStatus;
	/** 任务总数 */
	private Integer total;
	/** 完成任务书 */
	private Integer completionNumber;
	/** 异常任务 */
	private Integer exceptionNumber;
	/** 当前任务开始时间 */
	private Date startTime;
	/** 当前任务结束时间 */
	private Date endTime;

	private Long id;
	
	@Id
	@GeneratedValue(generator = "globalGenerator")
	@GenericGenerator(name = "globalGenerator", strategy = "org.csr.core.persistence.generator5.GlobalGenerator")
	@Column(name = "id", unique = true, nullable = false)
	@Comment(ch = "ID", en = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "name", length = 255)
	@Comment(ch ="任务名称",en="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "taskTempId")
	@Comment(ch ="流程模板ID",en="taskTempId")
	public TaskTemp getTaskTemp() {
		return taskTemp;
	}

	public void setTaskTemp(TaskTemp taskTemp) {
		this.taskTemp = taskTemp;
	}

	@Column(name = "taskInstanceStatus", length = 1)
	@Comment(ch ="流程1.激活状态，2.是否暂停流程,3,流程结束，4，异常结束",en="taskInstanceStatus")
	public Integer getTaskInstanceStatus() {
		return taskInstanceStatus;
	}

	public void setTaskInstanceStatus(Integer active) {
		this.taskInstanceStatus = active;
	}

	@Column(name = "total")
	@Comment(ch ="下发流程总数",en="total")
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Column(name = "completionNumber")
	@Comment(ch ="已完成流程数",en="completionNumber")
	public Integer getCompletionNumber() {
		return completionNumber;
	}

	public void setCompletionNumber(Integer completionNumber) {
		this.completionNumber = completionNumber;
	}

	@Column(name = "exceptionNumber")
	@Comment(ch ="异常流程数",en="exceptionNumber")
	public Integer getExceptionNumber() {
		return exceptionNumber;
	}

	public void setExceptionNumber(Integer exceptionNumber) {
		this.exceptionNumber = exceptionNumber;
	}
	
	@Column(name = "startTime")
	@Temporal(TemporalType.TIMESTAMP)
	@Comment(ch ="当前任务开始时间",en="startTime")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "endTime")
	@Temporal(TemporalType.TIMESTAMP)
	@Comment(ch ="当前任务结束时间",en="endTime")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	
}
