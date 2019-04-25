package org.csr.common.user.domain;

// Generated Oct 29, 2013 10:37:52 AM by Hibernate Tools 3.2.0.b9

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.csr.core.Comment;
import org.csr.core.persistence.domain.SimpleDomain;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * 积分规则
 */
@Entity
@Table(name = "u_PointsRules")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch="积分规则",en="pmt_common_PointsRules")
public class PointsRules extends SimpleDomain<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5209835753016151383L;
	private Long id;
	private String operation;
	private FunctionPoint functionPoint;
	private Integer points1;
	private String pointsMark1;
	private Integer points2;
	private String pointsMark2;
	private Integer countDay;
	private String remark;
	
	public PointsRules() {
	}

	public PointsRules(Long id) {
		this.id = id;
	}

	public PointsRules(Long id, String operation, FunctionPoint functionPoint,
			Integer points1, String pointsMark1, Integer points2,
			String pointsMark2, Integer countDay,
			String remark,String functionPointName) {
		this.id = id;
		this.operation = operation;
		this.functionPoint = functionPoint;
		this.points1 = points1;
		this.pointsMark1 = pointsMark1;
		this.points2 = points2;
		this.pointsMark2 = pointsMark2;
		this.countDay = countDay;
		this.remark = remark;
	}
	@Id
	@GeneratedValue(generator = "globalGenerator")  
	@GenericGenerator(name = "globalGenerator", strategy = "org.csr.core.persistence.generator5.GlobalGenerator")
	@Column(name = "id", unique = true, nullable = false)
	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "operation", length = 512)
	@Comment(ch="操作描述",en="operation")
	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Comment(ch="功能点ID",en="functionPoint")
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "functionPointId", referencedColumnName = "id", nullable = true, insertable = true, updatable = true) })
	public FunctionPoint getFunctionPoint() {
		return this.functionPoint;
	}

	public void setFunctionPoint(FunctionPoint functionPoint) {
		this.functionPoint = functionPoint;
	}

	@Column(name = "points1")
	@Comment(ch="当前操作积分1",en="points1")
	public Integer getPoints1() {
		return this.points1;
	}

	public void setPoints1(Integer points1) {
		this.points1 = points1;
	}

	@Column(name = "pointsMark1", length = 512)
	@Comment(ch="描述1",en="pointsMark1")
	public String getPointsMark1() {
		return this.pointsMark1;
	}

	public void setPointsMark1(String pointsMark1) {
		this.pointsMark1 = pointsMark1;
	}

	@Column(name = "points2")
	@Comment(ch="当前操作积分2",en="points2")
	public Integer getPoints2() {
		return this.points2;
	}

	public void setPoints2(Integer points2) {
		this.points2 = points2;
	}

	@Column(name = "pointsMark2", length = 512)
	@Comment(ch="描述2",en="pointsMark2")
	public String getPointsMark2() {
		return this.pointsMark2;
	}

	public void setPointsMark2(String pointsMark2) {
		this.pointsMark2 = pointsMark2;
	}

	@Column(name = "countDay",length=2)
	@Comment(ch="加分频率：0不限次数，每天加积分次数",en="countDay")
	public Integer getCountDay() {
	    return countDay;
	}

	public void setCountDay(Integer countDay) {
	    this.countDay = countDay;
	}

	@Column(name = "remark",length=255)
	@Comment(ch="备注",en="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
