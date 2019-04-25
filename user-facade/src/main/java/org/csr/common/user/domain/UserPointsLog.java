package org.csr.common.user.domain;

// Generated Nov 25, 2013 5:43:02 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.csr.core.Comment;
import org.csr.core.persistence.domain.SimpleDomain;
import org.csr.core.persistence.message.LogMsg;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * 用户积分日志
 */
@Entity
@Table(name = "u_UserPointsLog")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch="用户积分日志",en="pmt_common_UserPointsLog")
public class UserPointsLog extends SimpleDomain<Long> implements LogMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1102990763390121382L;
	private Long id;
	private Long userId;
	private String userName;
	private String operation;
	private Integer points1;
	private Integer points2;
	private Date operationDate;
	protected String remark;
	
	public UserPointsLog() {
	}

	public UserPointsLog(long id) {
		this.id = id;
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
	
	@Column(name = "userId",nullable=false)
	@Comment(ch="用户id",en="userId")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column(name = "userName",nullable=false)
	@Comment(ch="用户名称",en="userName")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "operation", length = 512)
	@Comment(ch="操作描述")
	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Column(name = "points1")
	@Comment(ch="积分1")
	public Integer getPoints1() {
		return this.points1;
	}

	public void setPoints1(Integer points1) {
		this.points1 = points1;
	}

	@Column(name = "points2")
	@Comment(ch="积分2")
	public Integer getPoints2() {
		return this.points2;
	}

	public void setPoints2(Integer points2) {
		this.points2 = points2;
	}

	@Column(name = "operationDate", length = 19)
	@Comment(ch="操作时间")
	public Date getOperationDate() {
		return this.operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	@Column(name = "remark",length=255)
	@Comment(ch="备注")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
