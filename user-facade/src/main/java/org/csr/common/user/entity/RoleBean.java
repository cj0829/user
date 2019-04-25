package org.csr.common.user.entity;

import org.csr.common.user.domain.Role;
import org.csr.core.web.bean.VOBase;

/**
 * 角色
 */
public class RoleBean extends VOBase<Long> {

	/**
	 * 当前的全局的功能点
	 */
	public static final Long global = 1l;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7806297233086099628L;
	private Long id;
	private String name;
	private Byte roleType;
	private Long orgId;
	private String orgName;
	protected String remark;
	/**是否选择当前节点*/
	private boolean checked = false;
	

	public RoleBean() {
	}

	public RoleBean(Long id) {
		this.id = id;
	}

	public RoleBean(Long id, String name, Byte roleType, String remark,Long orgId) {
		this.id = id;
		this.name = name;
		this.roleType = roleType;
		this.remark = remark;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getRoleType() {
		return this.roleType;
	}

	public void setRoleType(Byte roleType) {
		this.roleType = roleType;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public static RoleBean wrapBean(Role doMain) {
		RoleBean roleBean = new RoleBean(doMain.getId());
		roleBean.setName(doMain.getName());
		roleBean.setRoleType(doMain.getRoleType());
		roleBean.setRemark(doMain.getRemark());
		roleBean.setOrgId(doMain.getOrgId());
		return roleBean;
	}
}
