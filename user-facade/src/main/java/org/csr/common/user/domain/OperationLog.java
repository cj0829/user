package org.csr.common.user.domain;

// Generated Oct 29, 2013 10:37:52 AM by Hibernate Tools 3.2.0.b9

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.csr.core.Comment;
import org.csr.core.persistence.domain.RootDomain;
import org.csr.core.persistence.message.LogMsg;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * 操作日志
 */
@Entity
@Table(name = "u_OperationLog")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch="操作日志",en="pmt_common_OperationLog")
public class OperationLog extends RootDomain<Long> implements LogMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8390973355357789612L;
	private Long id;
	private Long loginLogId;
	private String operation;
//	private String oldContent;
//	private String newContent;
	private Long userId;
	private Date operationTime;
	private Byte operationLogType;
	private String functionPointCode;
	private String loginName;
	private String userName;
	private String functionPointCodeName;

	public OperationLog() {
	}

	public OperationLog(Long id) {
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
	@Column(name = "loginLogId")
	@Comment(ch="登录日志ID")
	public Long getLoginLogId() {
		return loginLogId;
	}

	public void setLoginLogId(Long loginLogId) {
		this.loginLogId = loginLogId;
	}

	@Column(name = "operation", length = 128)
	@Comment(ch="操作描述")
	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Column(name = "operationTime", length = 19)
	@Comment(ch="操作时间")
	public Date getOperationTime() {
		return this.operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	@Column(name = "operationLogType", length = 1)
	@Comment(ch="1 增；2 删；3 改；4 查")
	public Byte getOperationLogType() {
		return this.operationLogType;
	}

	public void setOperationLogType(Byte operationLogType) {
		this.operationLogType = operationLogType;
	}

	@Column(name = "functionPointCode", length = 16)
	@Comment(ch="操作功能点编码")
	public String getFunctionPointCode() {
		return this.functionPointCode;
	}

	public void setFunctionPointCode(String functionPointCode) {
		this.functionPointCode = functionPointCode;
	}
//
//	@Column(name = "oldContent", length = 4000)
//	@Comment(ch="修改前的内容")
//	public String getOldContent() {
//		return oldContent;
//	}
//
//	public void setOldContent(String oldContent) {
//		this.oldContent = oldContent;
//	}
//
//	@Column(name = "newContent", length = 4000)
//	@Comment(ch="修改后的内容")
//	public String getNewContent() {
//		return newContent;
//	}
//
//	public void setNewContent(String newContent) {
//		this.newContent = newContent;
//	}

	@Column(name = "userId")
	@Comment(ch="操作者ID")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "loginName",length=64)
	@Comment(ch="用户名",en="loginName")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "userName",length=64)
	@Comment(ch="姓名",en="userName")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "functionPointCodeName",length=64)
	@Comment(ch="功能点编码名称",en="functionPointCodeName")
	public String getFunctionPointCodeName() {
		return functionPointCodeName;
	}

	public void setFunctionPointCodeName(String functionPointCodeName) {
		this.functionPointCodeName = functionPointCodeName;
	}
	
}
