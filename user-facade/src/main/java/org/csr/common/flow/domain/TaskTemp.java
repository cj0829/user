package org.csr.common.flow.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.csr.core.Comment;
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
 *        功能描述： 发布一个工作流程<br/>
 *        公用方法描述： <br/>
 */
@Entity
@Table(name = "f_TaskTemp")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(en="pmt_flow_TaskTemp",ch = "工作流程模板")
public class TaskTemp extends RootDomain<Long> {

	private static final long serialVersionUID = 1L;
	/** 工作流程模板编号 */
	private Long id;
	/** 工作流程模板名称 */
	private String name;
	/** 开始流程 */
	private StartNode start;
	/** 结束流程 */
	private EndNode end;
	/** 是否有效 */
	private boolean valid;

	private Long orgId;
	
	public TaskTemp() {
		this(null);
	}

	public TaskTemp(Long id) {
		this.id = id;
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
	@Comment(ch = "模板名称", en = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "startId")
	@Comment(ch = "开始流程节点", en = "startId")
	public StartNode getStart() {
		return start;
	}

	public void setStart(StartNode start) {
		this.start = start;
	}

	@OneToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "endId")
	@Comment(ch = "结束流程节点", en = "endId")
	public EndNode getEnd() {
		return end;
	}

	public void setEnd(EndNode end) {
		this.end = end;
	}

	@Column(name = "valid")
	@Comment(ch = "是否有效", en = "valid")
	public boolean getValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
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
