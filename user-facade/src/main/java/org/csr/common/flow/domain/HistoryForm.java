package org.csr.common.flow.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.csr.common.flow.entity.Form;
import org.csr.core.Comment;
import org.csr.core.constant.YesorNo;
import org.csr.core.persistence.domain.RootDomain;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "f_HistoryForm")
@Comment(en="pmt_flow_HistoryForm",ch = "历史表单记录")
public class HistoryForm extends RootDomain<Long> {

	private static final long serialVersionUID = 3427938285646037656L;

	private Long id;
	/** 表单内容数据 */
	private Form formObj;
	/** 表单数据clas名称数据 */
	private String className;
	/** 表单内容数据 真实，formId */
	private Long formId;
	/** 表单编号 */
	private Long userTaskInstanceId;
	// 审批模板
	/** 当前审批的流程id */
	private Long taskTempId;
	/** 工作流程模板名称 */
	private String taskTempName;
	/** 开始流程 */
	private Long starTtaskNodeId;
	private String starTtaskNodeName;
	/** 结束流程 */
	private Long endTaskNodeId;
	private String endTaskNodeName;
	// 审批模板

	// /流程数据记录
	/** 任务节点编号 */
	private Long taskNodeId;
	/** 任务节点编号 */
	private String taskNodeName;
	/** 任务节点类型 */
	private String nodeType;
	
	/** 当前任务启动时间 */
	private Date taskNodeStartTime;
	/** 当前任务的间隔时间 */
	private Long taskNodeIntervalTime;
	/** 当前任务说明 */
	private String taskNodeRemark;
	/** 当前节点，是否能够关闭流程(删除就是关闭) */
	private Byte taskNodeIsClose = YesorNo.NO;
	/** 当前节点，是否能够编辑(移动) */
	private Byte taskNodeIsEdit = YesorNo.NO;
	/** 当前节点，是否按照顺序审批 */
	private Byte taskNodeIsOrderApproval = YesorNo.NO;
	/** 是否要求全部审批 */
	private Byte taskNodeIsAllApproval = 0;
	// /流程数据记录

	// /数据处理记录
	/** 流程当前处理者 */
	protected Long currentBy;
	protected String currentLoginName;
	protected String currentName;
	
	/** 下一流程处理者 */
	protected Long nextBy;
	/** 流程处理时间 */
	protected Date processingTime;
	/** 流程处理评价,意见 */
	protected String opinion;

	// /数据处理记录

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

	@Column(name = "formObj", length = 64 * 1024)
	@Comment(ch = "对象二进制内容", en = "formObj")
	public Form getFormObj() {
		return formObj;
	}

	public void setFormObj(Form formObj) {
		this.formObj = formObj;
	}

	@Column(name = "className")
	@Comment(ch = "对象class名称", en = "className")
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Comment(ch = "用户流程实体id", en = "用户流程实体id")
	@Column(name = "userTaskInstanceId")
	public Long getUserTaskInstanceId() {
		return userTaskInstanceId;
	}

	public void setUserTaskInstanceId(Long userTaskInstanceId) {
		this.userTaskInstanceId = userTaskInstanceId;
	}

	@Column(name = "taskTempId")
	@Comment(ch = "任务节点编号", en = "taskTempId")
	public Long getTaskTempId() {
		return taskTempId;
	}

	public void setTaskTempId(Long taskTempId) {
		this.taskTempId = taskTempId;
	}

	@Column(name = "taskNodeId")
	@Comment(ch = "任务节点编号", en = "taskNodeId")
	public Long getTaskNodeId() {
		return taskNodeId;
	}

	public void setTaskNodeId(Long taskNodeId) {
		this.taskNodeId = taskNodeId;
	}
	

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	@Comment(ch = "当前处理用户id", en = "currentUserId")
	@Column(name = "currentUserId")
	public Long getCurrentBy() {
		return currentBy;
	}

	public void setCurrentBy(Long currentBy) {
		this.currentBy = currentBy;
	}

	@Comment(ch = "当前处理用户登录名", en = "currentLoginName")
	@Column(name = "currentLoginName")
	public String getCurrentLoginName() {
		return currentLoginName;
	}

	public void setCurrentLoginName(String currentLoginName) {
		this.currentLoginName = currentLoginName;
	}

	@Comment(ch = "当前处理用姓名", en = "currentName")
	@Column(name = "currentName")
	public String getCurrentName() {
		return currentName;
	}

	public void setCurrentName(String currentName) {
		this.currentName = currentName;
	}

	@Comment(ch = "下一个处理用户id", en = "nextUserId")
	@Column(name = "nextUserId")
	public Long getNextBy() {
		return nextBy;
	}

	public void setNextBy(Long nextBy) {
		this.nextBy = nextBy;
	}

	@Column(name = "processingTime")
	@Temporal(TemporalType.TIMESTAMP)
	@Comment(ch = "当前流程处理时间", en = "processingTime")
	public Date getProcessingTime() {
		return processingTime;
	}

	public void setProcessingTime(Date processingTime) {
		this.processingTime = processingTime;
	}

	@Column(name = "opinion", length = 255)
	@Comment(ch = "当前流程处理意见", en = "opinion")
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	@Column(name = "formId")
	@Comment(ch = "处理的formId", en = "formId")
	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}
	@Column(name = "taskTempName")
	@Comment(ch = "流程模板名称", en = "taskTempName")
	public String getTaskTempName() {
		return taskTempName;
	}

	public void setTaskTempName(String taskTempName) {
		this.taskTempName = taskTempName;
	}
	
	@Column(name = "starTtaskNodeId")
	@Comment(ch = "启动流程id", en = "starTtaskNodeId")
	public Long getStarTtaskNodeId() {
		return starTtaskNodeId;
	}

	public void setStarTtaskNodeId(Long starTtaskNodeId) {
		this.starTtaskNodeId = starTtaskNodeId;
	}

	@Column(name = "starTtaskNodeName")
	@Comment(ch = "启动流程名称", en = "starTtaskNodeName")
	public String getStarTtaskNodeName() {
		return starTtaskNodeName;
	}

	public void setStarTtaskNodeName(String starTtaskNodeName) {
		this.starTtaskNodeName = starTtaskNodeName;
	}

	@Column(name = "endTaskNodeId")
	@Comment(ch = "结束流程id", en = "endTaskNodeId")
	public Long getEndTaskNodeId() {
		return endTaskNodeId;
	}

	public void setEndTaskNodeId(Long endTaskNodeId) {
		this.endTaskNodeId = endTaskNodeId;
	}

	@Column(name = "endTaskNodeName")
	@Comment(ch = "结束流程名称", en = "endTaskNodeName")
	public String getEndTaskNodeName() {
		return endTaskNodeName;
	}

	public void setEndTaskNodeName(String endTaskNodeName) {
		this.endTaskNodeName = endTaskNodeName;
	}

	@Column(name = "taskNodeName")
	@Comment(ch = "当前处理名称", en = "taskNodeName")
	public String getTaskNodeName() {
		return taskNodeName;
	}

	public void setTaskNodeName(String taskNodeName) {
		this.taskNodeName = taskNodeName;
	}

	@Column(name = "taskNodeStartTime")
	@Temporal(TemporalType.TIMESTAMP)
	@Comment(ch = "当前流程启动时间", en = "taskNodeStartTime")
	public Date getTaskNodeStartTime() {
		return taskNodeStartTime;
	}

	public void setTaskNodeStartTime(Date taskNodeStartTime) {
		this.taskNodeStartTime = taskNodeStartTime;
	}

	@Column(name = "taskNodeIntervalTime")
	@Comment(ch = "当前流程执行时间间隔", en = "taskNodeIntervalTime")
	public Long getTaskNodeIntervalTime() {
		return taskNodeIntervalTime;
	}

	public void setTaskNodeIntervalTime(Long taskNodeIntervalTime) {
		this.taskNodeIntervalTime = taskNodeIntervalTime;
	}

	@Column(name = "taskNodeRemark")
	@Comment(ch = "当前流程说明", en = "taskNodeRemark")
	public String getTaskNodeRemark() {
		return taskNodeRemark;
	}

	public void setTaskNodeRemark(String taskNodeRemark) {
		this.taskNodeRemark = taskNodeRemark;
	}

	@Column(name = "taskNodeIsClose")
	@Comment(ch = "当前流程是否关闭", en = "taskNodeIsClose")
	public Byte getTaskNodeIsClose() {
		return taskNodeIsClose;
	}

	public void setTaskNodeIsClose(Byte taskNodeIsClose) {
		this.taskNodeIsClose = taskNodeIsClose;
	}

	@Column(name = "taskNodeIsEdit")
	@Comment(ch = "当前流程是否能够编辑", en = "taskNodeIsEdit")
	public Byte getTaskNodeIsEdit() {
		return taskNodeIsEdit;
	}

	public void setTaskNodeIsEdit(Byte taskNodeIsEdit) {
		this.taskNodeIsEdit = taskNodeIsEdit;
	}

	@Column(name = "taskNodeIsOrderApproval")
	@Comment(ch = "当前流程是否能够排序审批", en = "taskNodeIsOrderApproval")
	public Byte getTaskNodeIsOrderApproval() {
		return taskNodeIsOrderApproval;
	}

	public void setTaskNodeIsOrderApproval(Byte taskNodeIsOrderApproval) {
		this.taskNodeIsOrderApproval = taskNodeIsOrderApproval;
	}

	@Column(name = "taskNodeIsAllApproval")
	@Comment(ch = "当前流程是否能够排序审批人数", en = "taskNodeIsAllApproval")
	public Byte getTaskNodeIsAllApproval() {
		return taskNodeIsAllApproval;
	}

	public void setTaskNodeIsAllApproval(Byte taskNodeIsAllApproval) {
		this.taskNodeIsAllApproval = taskNodeIsAllApproval;
	}

}
