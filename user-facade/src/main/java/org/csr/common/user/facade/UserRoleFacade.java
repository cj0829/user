/**
 * Project Name:user-facade
 * File Name:UserRoleFacade.java
 * Package Name:org.csr.common.user.facade
 * Date:2016-9-1下午2:55:57
 * Copyright (c) 2016, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.facade;

import java.util.List;

import org.csr.common.user.domain.UserRole;
import org.csr.common.user.entity.UserRoleBean;
import org.csr.core.persistence.service.BasisFacade;

/**
 * ClassName:UserRoleFacade.java <br/>
 * System Name：    在线学习系统 <br/>
 * Date:     2016-9-1下午2:55:57 <br/>
 * @author   Administrator <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
public interface UserRoleFacade extends BasisFacade<UserRoleBean, Long>{

	List<UserRoleBean> findUserRoleList(Long userId);

	void saveUserRole(Long roleId, Long[] userIds);

	void deleteByUserIdRoleId(Long roleId, Long userId);

	void deleteUserRole(Long roleId, Long[] userIds);

	UserRole checkUserRoleByroleId(Long moderatorId, Long managerRoleId);

	void save(UserRole userRole);

	/**
	 * 修改用户角色
	 * @param userId
	 * @param roleIds
	 */
	void updateUserRoles(Long userId, List<Long> roleIds);

}

