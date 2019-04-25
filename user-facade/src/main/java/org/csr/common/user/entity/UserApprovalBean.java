/**
 * 
 */
package org.csr.common.user.entity;
import java.util.Date;

import org.csr.common.flow.entity.FormBean;
import org.csr.common.flow.entity.UserTaskInstanceBean;
import org.csr.common.user.domain.User;
import org.csr.core.util.ObjUtil;

/**
 *ClassName:UserCommand.java<br/>
 *Date:2015-9-17 上午11:11:20
 *@author huayj
 *@version 1.0
 *@since JDK 1.7
 *功能描述:
 */
public class UserApprovalBean extends FormBean {

	private static final long serialVersionUID = 2367371079302114863L;
	
	private String name;
	private String loginName;
	
	private Long organizationId;
	/**机构组织id*/
	private Long agenciesId;
	/**机构组织Name*/
	private String agenciesName;
	/**电子邮件*/
	private String email;
	/**用户类型*/
	private Byte userRoleType;
	/**管理人*/
	private Long managerUserId;
	
	private String managerUserName;
	
	private Long primaryOrgId;
	
	private Long orgId;
	private String organizationName;
	/**用户导入文件Id*/
	private Long fileId;
	/**用户状态*/
	private Integer userStatus;
	/**创建时间*/
	private Date createTime;
	
	private Byte gender;
	
	public Long getId() {
		return super.id;
	}
	public void setId(Long id) {
		super.id = id;
	}
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public Long getAgenciesId() {
		return agenciesId;
	}
	public void setAgenciesId(Long agenciesId) {
		this.agenciesId = agenciesId;
	}
	public String getAgenciesName() {
		return agenciesName;
	}
	public void setAgenciesName(String agenciesName) {
		this.agenciesName = agenciesName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Byte getUserRoleType() {
		return userRoleType;
	}
	public void setUserRoleType(Byte userRoleType) {
		this.userRoleType = userRoleType;
	}
	public Long getManagerUserId() {
		return managerUserId;
	}
	public void setManagerUserId(Long managerUserId) {
		this.managerUserId = managerUserId;
	}
	public String getManagerUserName() {
		return managerUserName;
	}
	public void setManagerUserName(String managerUserName) {
		this.managerUserName = managerUserName;
	}
	public Long getPrimaryOrgId() {
		return primaryOrgId;
	}
	public void setPrimaryOrgId(Long primaryOrgId) {
		this.primaryOrgId = primaryOrgId;
	}
	
	public Byte getGender() {
		return gender;
	}
	public void setGender(Byte gender) {
		this.gender = gender;
	}
	public static UserApprovalBean toBean(User doMain) {
		UserApprovalBean bean = new UserApprovalBean();
		bean.setId(doMain.getId());
		bean.setFileId(doMain.getFileId());
		bean.setUserStatus(doMain.getUserStatus());
		bean.setUserTaskInstance(UserTaskInstanceBean.wrapBean(doMain.getUserTaskInstance()));
		bean.setCreateTime(doMain.getCreateTime());
		bean.setId(doMain.getId());
		bean.setLoginName(doMain.getLoginName());
		bean.setName(doMain.getName());
		bean.setOrganizationId(doMain.getPrimaryOrgId());
		bean.setOrganizationName(doMain.getOrganizationName());
		bean.setUserRoleType(doMain.getUserRoleType());
		if(ObjUtil.isNotEmpty(doMain.getAgencies())){
			bean.setAgenciesId(doMain.getAgencies().getId());
			bean.setAgenciesName(doMain.getAgencies().getName());
		}
		bean.setGender(doMain.getGender());
		User managerUser = doMain.getManagerUser();
		if(ObjUtil.isNotEmpty(managerUser)){
			bean.setManagerUserId(managerUser.getId());
			bean.setManagerUserName(managerUser.getLoginName());
		}
		bean.setUserStatus(doMain.getUserStatus());
		
		return bean;
	}
}
