/**
 * Project Name:user-facade
 * File Name:roleFacade.java
 * Package Name:org.csr.common.user.facade
 * Date:2016-9-1下午2:51:01
 * Copyright (c) 2016, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.facade;

import java.util.List;

import org.csr.common.user.domain.Role;
import org.csr.common.user.entity.RoleBean;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.service.BasisFacade;

/**
 * ClassName:roleFacade.java <br/>
 * System Name：    在线学习系统 <br/>
 * Date:     2016-9-1下午2:51:01 <br/>
 * @author   Administrator <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
public interface RoleFacade extends BasisFacade<RoleBean, Long>{

	PagedInfo<RoleBean> findUnByUserId(Page page, Long userId, String name);

	PagedInfo<RoleBean> findByUserId(Page page, Long userId, String name);
	
	List<Long> findByUserId(Long userId);

	void saveCustom(Role role);

	void updateCustom(Role role);

	void deleteRole(Long[] roleIds);

	boolean checkAddRoleName(String name);

	boolean checkUpdateRoleName(Long roleId, String name);

	RoleBean findOrgRole(Long root);

	List<RoleBean> findRoleList();

	PagedInfo<RoleBean> findAllListPage(Page page, Long primaryOrgId);

}

