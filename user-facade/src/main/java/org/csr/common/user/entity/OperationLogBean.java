package org.csr.common.user.entity;

import java.util.Date;

import javax.persistence.Transient;

import org.csr.common.user.domain.OperationLog;
import org.csr.core.util.DateUtil;
import org.csr.core.web.bean.VOBase;

/**
 * ClassName:OperationLogResult.java <br/>
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
public class OperationLogBean extends VOBase<Long> {

	/**
	 * serialVersionUID:
	 * 
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 5485621335724009821L;
	private Long id;
	private String operation;
	private String oldContent;
	private String newContent;
	private Long userId;
	private String userName;
	private Date operationTime;
	private Byte operationLogType;
	private String functionPointCode;
	private String loginName;
	private String functionPointCodeName;

	public OperationLogBean() {
	}

	public OperationLogBean(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
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

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public Byte getOperationLogType() {
		return operationLogType;
	}

	public void setOperationLogType(Byte operationLogType) {
		this.operationLogType = operationLogType;
	}

	public String getFunctionPointCode() {
		return functionPointCode;
	}

	public void setFunctionPointCode(String functionPointCode) {
		this.functionPointCode = functionPointCode;
	}

	@Transient
	public String getOperationTimeString() {
		return DateUtil.parseDateTimeToSec(getOperationTime());
	}


	public String getOldContent() {
		return oldContent;
	}

	public void setOldContent(String oldContent) {
		this.oldContent = oldContent;
	}

	public String getNewContent() {
		return newContent;
	}

	public void setNewContent(String newContent) {
		this.newContent = newContent;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getFunctionPointCodeName() {
		return functionPointCodeName;
	}

	public void setFunctionPointCodeName(String functionPointCodeName) {
		this.functionPointCodeName = functionPointCodeName;
	}
	public static OperationLogBean wrapBean(OperationLog doMain) {
		OperationLogBean log = new OperationLogBean();
		log.setId(doMain.getId());
		log.setOperation(doMain.getOperation());
		log.setUserId(doMain.getUserId());
		log.setUserName(doMain.getUserName());
		log.setOperationTime(doMain.getOperationTime());
		log.setOperationLogType(doMain.getOperationLogType());
		log.setFunctionPointCode(doMain.getFunctionPointCode());
		log.setLoginName(doMain.getLoginName());
		log.setFunctionPointCodeName(doMain.getFunctionPointCodeName());
		return log;
	}
}
