package org.csr.common.flow.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.csr.core.Comment;
import org.csr.core.constant.YesorNo;
import org.csr.core.persistence.domain.RootDomain;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * ClassName:TaskFlow.java <br/>
 * System Name： 用户管理系统。<br/>
 * Date: 2014-3-7下午2:07:08 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： 任务节点描述，<br/>
 *        公用方法描述： <br/>
 */
@Entity
@Table(name = "f_TaskNode")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(en="pmt_flow_TaskNode",ch = "任务节点描述表")
public class TaskNode extends RootDomain<Long> {

	private static final long serialVersionUID = 1L;

	private Long id;
	/** 任务节点 */
	private String name;
	/** 所属工作流 */
	TaskTemp taskTemp;
	/** 下一个任务 */
	private TaskNode toNode;
	/** 下一个任务 */
//	private TaskNode fromNode;
	/** 被驳回，返回的任务节点 */
	private TaskNode rejectNode;
	/** 当前任务启动时间 */
	private Date startTime;
	/** 当前任务的间隔时间 */
	private Long intervalTime;
	/** 当前任务说明 */
	private String remark;
	/***策略*/
	/**当前节点，是否能够关闭流程(删除就是关闭)*/
	private Byte isClose=YesorNo.NO;
	/**当前节点，是否能够编辑(移动)*/
	private Byte isEdit=YesorNo.NO;
	/**当前节点，是否按照顺序审批*/
	private Byte isOrderApproval=YesorNo.NO;
	/**是否要求全部审批*/
	private Byte isAllApproval=0;
	
	protected String nodeType;
	
	private Long orgId;
	
	public  TaskNode() {
		this.nodeType="TaskNode";
	}

	public TaskNode(Long id) {
		this();
		this.id = id;
	}

	public TaskNode(String name) {
		this();
		this.name = name;
	}

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

	@Column(name = "name")
	@Comment(ch = "任务节点", en = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "taskTempId")
	@Comment(ch = "任务模板ID", en = "taskTempId")
	public TaskTemp getTaskTemp() {
		return taskTemp;
	}

	public void setTaskTemp(TaskTemp taskTemp) {
		this.taskTemp = taskTemp;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "rejectId")
	@Comment(ch = "被驳回，返回的任务节点", en = "rejectId")
	public TaskNode getRejectNode() {
		return rejectNode;
	}

	public void setRejectNode(TaskNode reject) {
		this.rejectNode = reject;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "toId")
	@Comment(ch = "下一个任务", en = "toId")
	public TaskNode getToNode() {
		return toNode;
	}

	public void setToNode(TaskNode to) {
		this.toNode = to;
	}

	@Column(name = "startTime")
	@Temporal(TemporalType.TIMESTAMP)
	@Comment(ch = "当前任务启动时间", en = "startTime")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "intervalTime")
	@Comment(ch = "当前任务的间隔时间", en = "intervalTime")
	public Long getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(Long intervalTime) {
		this.intervalTime = intervalTime;
	}

	@Column(name = "remark", length = 255)
	@Comment(ch = "备注", en = "remark", len = 255)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "isClose")
	@Comment(ch = "是否能够关闭流程", en = "isClose")
	public Byte getIsClose() {
		return isClose;
	}

	public void setIsClose(Byte isClose) {
		this.isClose = isClose;
	}
	@Column(name = "isEdit")
	@Comment(ch = "是否能够编辑", en = "isEdit")
	public Byte getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(Byte isEdit) {
		this.isEdit = isEdit;
	}

	@Column(name = "isOrderApproval")
	@Comment(ch = "是否按照顺序审批", en = "isEdit")
	public Byte getIsOrderApproval() {
		return isOrderApproval;
	}

	public void setIsOrderApproval(Byte isOrderApproval) {
		this.isOrderApproval = isOrderApproval;
	}

	@Column(name = "isAllApproval")
	@Comment(ch = "是否要求全部审批", en = "isEdit")
	public Byte getIsAllApproval() {
		return isAllApproval;
	}

	public void setIsAllApproval(Byte isAllApproval) {
		this.isAllApproval = isAllApproval;
	}
	
	@Column(name = "nodeType",length=31)
	public String getNodeType(){
		return this.nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
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
