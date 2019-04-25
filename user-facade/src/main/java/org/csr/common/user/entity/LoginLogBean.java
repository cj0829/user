package org.csr.common.user.entity;

import java.util.Date;

import javax.persistence.Transient;

import org.csr.common.user.domain.LoginLog;
import org.csr.core.util.DateUtil;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.bean.VOBase;

/**
 * ClassName:LoginLogResult.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014-1-27 上午9:31:56 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 */
public class LoginLogBean extends VOBase<Long> implements java.io.Serializable {

	/**
	 * serialVersionUID:
	 * 
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = -5651612942605945826L;
	private Long id;
	private Long userId;
	private String userName;
	private String loginName;
	private Date loginTime;
	private Date logoutTime;
	private String ipAdress;
//	private Long organizationRoot;
	private Byte exitType;
	private String sessionId;
	private String logintype;
	public LoginLogBean() {
	}

	public LoginLogBean(Long id) {
		this.id = id;
	}

	public LoginLogBean(Long id, Long userId, String userName,
			String loginName, Date loginTime, Date logoutTime, String ipAdress,
			Byte exitType) {
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.loginName = loginName;
		this.loginTime = loginTime;
		this.logoutTime = logoutTime;
		this.ipAdress = ipAdress;
		this.exitType = exitType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public String getIpAdress() {
		return ipAdress;
	}

	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}

//	public Long getOrganizationRoot() {
//		return organizationRoot;
//	}
//
//	public void setOrganizationRoot(Long organizationRoot) {
//		this.organizationRoot = organizationRoot;
//	}

	public Byte getExitType() {
		return exitType;
	}

	public void setExitType(Byte exitType) {
		this.exitType = exitType;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Transient
	public String getLoginTimeString() {
		if (ObjUtil.isNotEmpty(loginTime)) {
			return DateUtil.parseDateTimeToSec(getLoginTime());
		} else {
			return "";
		}
	}

	@Transient
	public String getLogoutTimeString() {
		if (ObjUtil.isNotEmpty(logoutTime)) {
			return DateUtil.parseDateTimeToSec(getLogoutTime());
		} else {
			return "";
		}
	}
	
	public String getLogintype() {
		return logintype;
	}

	public void setLogintype(String logintype) {
		this.logintype = logintype;
	}

	public static LoginLogBean wrapBean(LoginLog doMain) {
		LoginLogBean bean = new LoginLogBean();
		bean.setId(doMain.getId());
		bean.setIpAdress(doMain.getIpAdress());
		bean.setSessionId(doMain.getSessionId());
		bean.setUserId(doMain.getUserId());
		bean.setUserName(doMain.getUserName());
		bean.setLoginName(doMain.getLoginName());
		bean.setLoginTime(doMain.getLoginTime());
		bean.setLogoutTime(doMain.getLogoutTime());
		bean.setExitType(doMain.getExitType());
		bean.setLogintype(doMain.getLogintype());
		return bean;
	}

}
