package org.csr.common.flow.entity;

import java.util.Date;

import org.csr.common.flow.domain.HistoryForm;
import org.csr.core.web.bean.VOBase;

public class HistoryFormBean extends VOBase<Long> {

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
	// /** 当前节点，是否能够关闭流程(删除就是关闭) */
	// private Integer taskNodeIsClose = YesorNo.NO;
	// /** 当前节点，是否能够编辑(移动) */
	// private Integer taskNodeIsEdit = YesorNo.NO;
	// /** 当前节点，是否按照顺序审批 */
	// private Integer taskNodeIsOrderApproval = YesorNo.NO;
	// /** 是否要求全部审批 */
	// private Integer taskNodeIsAllApproval = 0;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Form getFormObj() {
		return formObj;
	}

	public void setFormObj(Form formObj) {
		this.formObj = formObj;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Long getUserTaskInstanceId() {
		return userTaskInstanceId;
	}

	public void setUserTaskInstanceId(Long userTaskInstanceId) {
		this.userTaskInstanceId = userTaskInstanceId;
	}

	public Long getTaskTempId() {
		return taskTempId;
	}

	public void setTaskTempId(Long taskTempId) {
		this.taskTempId = taskTempId;
	}

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

	public Long getCurrentBy() {
		return currentBy;
	}

	public void setCurrentBy(Long currentBy) {
		this.currentBy = currentBy;
	}

	public String getCurrentLoginName() {
		return currentLoginName;
	}

	public void setCurrentLoginName(String currentLoginName) {
		this.currentLoginName = currentLoginName;
	}

	public String getCurrentName() {
		return currentName;
	}

	public void setCurrentName(String currentName) {
		this.currentName = currentName;
	}

	public Long getNextBy() {
		return nextBy;
	}

	public void setNextBy(Long nextBy) {
		this.nextBy = nextBy;
	}

	public Date getProcessingTime() {
		return processingTime;
	}

	public void setProcessingTime(Date processingTime) {
		this.processingTime = processingTime;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public String getTaskTempName() {
		return taskTempName;
	}

	public void setTaskTempName(String taskTempName) {
		this.taskTempName = taskTempName;
	}

	public Long getStarTtaskNodeId() {
		return starTtaskNodeId;
	}

	public void setStarTtaskNodeId(Long starTtaskNodeId) {
		this.starTtaskNodeId = starTtaskNodeId;
	}

	public String getStarTtaskNodeName() {
		return starTtaskNodeName;
	}

	public void setStarTtaskNodeName(String starTtaskNodeName) {
		this.starTtaskNodeName = starTtaskNodeName;
	}

	public Long getEndTaskNodeId() {
		return endTaskNodeId;
	}

	public void setEndTaskNodeId(Long endTaskNodeId) {
		this.endTaskNodeId = endTaskNodeId;
	}

	public String getEndTaskNodeName() {
		return endTaskNodeName;
	}

	public void setEndTaskNodeName(String endTaskNodeName) {
		this.endTaskNodeName = endTaskNodeName;
	}

	public String getTaskNodeName() {
		return taskNodeName;
	}

	public void setTaskNodeName(String taskNodeName) {
		this.taskNodeName = taskNodeName;
	}

	public Date getTaskNodeStartTime() {
		return taskNodeStartTime;
	}

	public void setTaskNodeStartTime(Date taskNodeStartTime) {
		this.taskNodeStartTime = taskNodeStartTime;
	}

	public Long getTaskNodeIntervalTime() {
		return taskNodeIntervalTime;
	}

	public void setTaskNodeIntervalTime(Long taskNodeIntervalTime) {
		this.taskNodeIntervalTime = taskNodeIntervalTime;
	}

	public String getTaskNodeRemark() {
		return taskNodeRemark;
	}

	public void setTaskNodeRemark(String taskNodeRemark) {
		this.taskNodeRemark = taskNodeRemark;
	}

	public static HistoryFormBean wrapBean(HistoryForm doMain) {
		HistoryFormBean bean = new HistoryFormBean();
//		bean.setFormObj(doMain.getFormObj());
		bean.setId(doMain.getId());
		bean.setClassName(doMain.getClassName());
		bean.setFormId(doMain.getFormId());
		bean.setProcessingTime(doMain.getProcessingTime());
		bean.setOpinion(doMain.getOpinion());
		// 当前节点id
		bean.setTaskNodeId(doMain.getTaskNodeId());
		bean.setTaskNodeName(doMain.getTaskNodeName());
		bean.setTaskNodeIntervalTime(doMain.getTaskNodeIntervalTime());
		bean.setTaskNodeRemark(doMain.getTaskNodeRemark());
		bean.setTaskNodeStartTime(doMain.getTaskNodeStartTime());
		// 当前节点的
		bean.setCurrentBy(doMain.getCurrentBy());
		bean.setCurrentLoginName(doMain.getCurrentLoginName());
		bean.setCurrentName(doMain.getCurrentName());
		bean.setUserTaskInstanceId(doMain.getUserTaskInstanceId());
		// 模板信息
		bean.setTaskTempId(doMain.getTaskTempId());
		bean.setTaskTempName(doMain.getTaskNodeName());
		bean.setStarTtaskNodeId(doMain.getStarTtaskNodeId());
		bean.setStarTtaskNodeName(doMain.getStarTtaskNodeName());
		bean.setEndTaskNodeId(doMain.getEndTaskNodeId());
		bean.setEndTaskNodeName(doMain.getEndTaskNodeName());
		return bean;
	}

}
