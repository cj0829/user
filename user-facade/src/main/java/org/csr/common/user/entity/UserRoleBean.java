/**
 * Project Name:user-facade
 * File Name:UserRoleBean.java
 * Package Name:org.csr.common.user.entity
 * Date:2016-9-1下午5:28:12
 * Copyright (c) 2016, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.entity;

import org.csr.common.user.domain.UserRole;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.bean.VOBase;

/**
 * ClassName:UserRoleBean.java <br/>
 * System Name：    在线学习系统 <br/>
 * Date:     2016-9-1下午5:28:12 <br/>
 * @author   Administrator <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
public class UserRoleBean extends VOBase<Long>{
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long userId;
	private Long roleId;
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
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	public static UserRoleBean wrapBean(UserRole doMain) {
		UserRoleBean bean = new UserRoleBean();
		bean.setId(doMain.getId());
		if(ObjUtil.isNotEmpty(doMain.getRole())){
			bean.setRoleId(doMain.getRole().getId());
		}
		if(ObjUtil.isNotEmpty(doMain.getUser())){
			bean.setUserId(doMain.getUser().getId());
		}
		return bean;
	}
}

