/**
 * Project Name:exam
 * File Name:FlowStrategy.java
 * Package Name:com.pmt.exam.domain
 * Date:2015年6月23日上午10:37:12
 * Copyright (c) 2015, 博海云领版权所有 ,All rights reserved 
 */

package org.csr.common.user.entity;

import org.csr.common.user.domain.UserImportStrategy;
import org.csr.common.user.domain.UserRegisterStrategy;
import org.csr.core.constant.YesorNo;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.bean.VOBase;

/**
 * ClassName: FlowStrategy.java <br/>
 * System Name： elearning系统 <br/>
 * Date: 2015年6月23日上午10:37:12 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 功能描述： <br/>
 *        库策略，
 */
public class UserStrategyBean extends VOBase<Long> {

	/**
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = -2940739518232668347L;
	/** 试题库策略编号 */
	private Long importId;
	
	private Long registerId;
	/**机构名称*/
	private String agenciesString;
	/** 导入用户是否需要审批 */
	private Byte isImport = YesorNo.NO;
	/** 注册用户是否需要审批 */
	private Byte isRegister = YesorNo.NO;
	private Long orgId;

	private String orgName;
	/** 审批流程模板模板 */
	/** 导入-审批流程模板模板 */
	private Long importTaskTempId;
	private String importTaskTempName;

	/** 共享-审批流程模板模板 */
	private Long registerTaskTempId;
	private String registerTaskTempName;
	

	public Long getImportId() {
		return importId;
	}

	public void setImportId(Long importId) {
		this.importId = importId;
	}

	public Long getRegisterId() {
		return registerId;
	}

	public void setRegisterId(Long registerId) {
		this.registerId = registerId;
	}

	public String getAgenciesString() {
		if(ObjUtil.isBlank(agenciesString)){
			return "";
		}else{
			return agenciesString;
		}
	}

	public void setAgenciesString(String agenciesString) {
		this.agenciesString = agenciesString;
	}

	public Byte getIsImport() {
		return isImport;
	}

	public void setIsImport(Byte isImport) {
		this.isImport = isImport;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Byte getIsRegister() {
		return isRegister;
	}

	public void setIsRegister(Byte isRegister) {
		this.isRegister = isRegister;
	}

	public Long getImportTaskTempId() {
		return importTaskTempId;
	}

	public void setImportTaskTempId(Long importTaskTempId) {
		this.importTaskTempId = importTaskTempId;
	}

	public String getImportTaskTempName() {
		return importTaskTempName;
	}

	public void setImportTaskTempName(String importTaskTempName) {
		this.importTaskTempName = importTaskTempName;
	}

	public Long getRegisterTaskTempId() {
		return registerTaskTempId;
	}

	public void setRegisterTaskTempId(Long registerTaskTempId) {
		this.registerTaskTempId = registerTaskTempId;
	}

	public String getRegisterTaskTempName() {
		return registerTaskTempName;
	}

	public void setRegisterTaskTempName(String registerTaskTempName) {
		this.registerTaskTempName = registerTaskTempName;
	}

	public static UserStrategyBean wrapBean(UserRegisterStrategy doMain) {
		UserStrategyBean bean = new UserStrategyBean();
		bean.setStatus(SUCCESS);
		bean.setRegisterId(doMain.getId());
		bean.setIsRegister(doMain.getIsRegister());
		if (ObjUtil.isNotEmpty(doMain.getTaskTemp())) {
			bean.setRegisterTaskTempId(doMain.getTaskTemp().getId());
			bean.setRegisterTaskTempName(doMain.getTaskTemp().getName());
		}
		return bean;
	}

	public static UserStrategyBean wrapBean(UserImportStrategy doMain) {
		UserStrategyBean bean = new UserStrategyBean();
		bean.setStatus(SUCCESS);
		bean.setImportId(doMain.getId());
		bean.setIsRegister(doMain.getIsImport());
		if (ObjUtil.isNotEmpty(doMain.getTaskTemp())) {
			bean.setImportTaskTempId(doMain.getTaskTemp().getId());
			bean.setImportTaskTempName(doMain.getTaskTemp().getName());
		}
		return bean;
	}

	

	public static UserStrategyBean wrapBean(OrganizationNode org,UserImportStrategy importStrategy,UserRegisterStrategy registerStrategy) {
		UserStrategyBean bean = new UserStrategyBean();

		if (ObjUtil.isNotEmpty(org)) {
			bean.setOrgId(org.getId());
			bean.setOrgName(org.getName());
		}
		bean.setIsImport(importStrategy.getIsImport());
		bean.setImportId(importStrategy.getId());
		if (ObjUtil.isNotEmpty(importStrategy.getTaskTemp())) {
			bean.setImportTaskTempId(importStrategy.getTaskTemp().getId());
			bean.setImportTaskTempName(importStrategy.getTaskTemp().getName());
		}
		
		bean.setIsRegister(registerStrategy.getIsRegister());
		bean.setRegisterId(registerStrategy.getId());
		if (ObjUtil.isNotEmpty(registerStrategy.getTaskTemp())) {
			bean.setRegisterTaskTempId(registerStrategy.getTaskTemp().getId());
			bean.setRegisterTaskTempName(registerStrategy.getTaskTemp().getName());
		}
		return bean;
	}
}
