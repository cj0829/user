package org.csr.common.user.domain;

// Generated Oct 29, 2013 10:37:52 AM by Hibernate Tools 3.2.0.b9

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.csr.core.Comment;
import org.csr.core.persistence.domain.RootDomain;
import org.csr.core.persistence.message.LogMsg;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

/**
 * 登录日志
 */
@Entity
@Table(name = "u_LoginLog")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch="登录日志",en="pmt_common_LoginLog")
public class LoginLog extends RootDomain<Long> implements LogMsg  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5882025018631334071L;
	private Long id;
	private Long userId;
	private Date loginTime;
	private Date logoutTime;
	private String ipAdress;
	private Byte exitType;
	private String sessionId; 
	protected String remark;
	private String loginName;
	private String userName;
	private String logintype;
	
	public LoginLog() {
	}

	public LoginLog(Long id) {
		this.id = id;
	}
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "userId")
	@Comment(ch="用户Id")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId= userId;
	}

	@Column(name = "loginTime", length = 19)
	@Comment(ch="登录时间")
	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	@Column(name = "logoutTime", length = 19)
	@Comment(ch="退出时间")
	public Date getLogoutTime() {
		return this.logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	@Column(name = "ipAdress", length = 32)
	@Comment(ch="登录IP")
	public String getIpAdress() {
		return this.ipAdress;
	}

	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}


	@Column(name = "exitType",length=1)
	@Comment(ch="1 正常关闭退出；2 session失效退出；3 被踢下线；")
	public Byte getExitType() {
		return this.exitType;
	}

	public void setExitType(Byte exitType) {
		this.exitType = exitType;
	}

	@Column(name = "sessionId", length = 20)
	@Comment(ch="登录用户sessionId")
	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
 
	@Column(name = "remark",length=128)
	@Comment(ch="备注")
	@Length(max=128)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "loginName",length=40)
	@Comment(ch="用户登录名")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "userName",length=40)
	@Comment(ch="用户姓名")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "logintype",length=16)
	@Comment(ch="登录方式",en="logintype")
	public String getLogintype() {
		return logintype;
	}

	public void setLogintype(String logintype) {
		this.logintype = logintype;
	}
	
}
