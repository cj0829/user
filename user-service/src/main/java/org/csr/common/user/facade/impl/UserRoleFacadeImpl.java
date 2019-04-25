/**
 * Project Name:user-Service
 * File Name:UserRoleFacadeImpl.java
 * Package Name:org.csr.common.user.facade.impl
 * Date:2016-9-1下午5:20:22
 * Copyright (c) 2016, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.facade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.UserRoleDao;
import org.csr.common.user.domain.UserRole;
import org.csr.common.user.entity.UserRoleBean;
import org.csr.common.user.facade.UserInitData;
import org.csr.common.user.facade.UserRoleFacade;
import org.csr.common.user.service.UserRoleService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.support.CompareValue;
import org.csr.core.util.support.ToValue;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * ClassName:UserRoleFacadeImpl.java <br/>
 * System Name：    在线学习系统 <br/>
 * Date:     2016-9-1下午5:20:22 <br/>
 * @author   Administrator <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
@Service("userRoleFacade")
@Lazy(false)
public class UserRoleFacadeImpl extends SimpleBasisFacade<UserRoleBean, UserRole, Long> implements UserRoleFacade,UserInitData{

	@Resource
	private UserRoleService userRoleService;
	@Resource
	private UserRoleDao userRoleDao;
	
	@Override
	public List<UserRoleBean> findUserRoleList(Long userId) {
		List<UserRole> list = userRoleService.findUserRoleList(userId);
		List<UserRoleBean> listBean = PersistableUtil.toListBeans(list, new SetBean<UserRole>(){
			public UserRoleBean setValue(UserRole doMain) {
				return wrapBean(doMain);
			}
		});
		return listBean;
	}

	@Override
	public void saveUserRole(Long roleId, Long[] userIds) {
		userRoleService.saveUserRole(roleId,userIds);
	}

	@Override
	public void deleteByUserIdRoleId(Long roleId, Long userId) {
		userRoleService.deleteByUserIdRoleId(roleId,userId);
	}

	@Override
	public void deleteUserRole(Long roleId, Long[] userIds) {
		userRoleService.deleteUserRole(roleId, userIds);
	}

	@Override
	public UserRoleBean wrapBean(UserRole doMain) {
		return UserRoleBean.wrapBean(doMain);
	}

	@Override
	public UserRole checkUserRoleByroleId(Long moderatorId, Long managerRoleId) {
		// TODO Auto-generated method stub
		return userRoleService.checkUserRoleByroleId(moderatorId, managerRoleId);
	}

	@Override
	public void save(UserRole userRole) {
		userRoleService.save(userRole);
		
	}

	@Override
	public BaseDao<UserRole, Long> getDao() {
		return userRoleDao;
	}

	@Override
	public void updateUserRoles(final Long userId, List<Long> roleIds) {
		checkParameter(userId, "请选择要修改的用户");
		List<UserRole> byIds = userRoleDao.findByParam(new AndEqParam("user.id", userId));
		
		List<Long> oldIds = PersistableUtil.arrayTransforList(byIds, new ToValue<UserRole, Long>() {
			@Override
			public Long getValue(UserRole obj) {
				if(ObjUtil.isNotEmpty(obj.getRole())){
					return obj.getRole().getId();
				}
				return null;
			}
		});
		
		ObjUtil.compareId(oldIds, roleIds, new CompareValue<Long>() {
			@Override
			public void add(List<Long> roleIds) {
				userRoleService.saveUserRoles(userId,roleIds);
			}
			@Override
			public void update(List<Long> roleIds) {
				System.out.println("update");
			}
			@Override
			public void delete(List<Long> roleIds) {
				userRoleService.deleteUserRoles(userId, roleIds);
			}
		});
	}

	@Override
	public void delInitData(Long userId) {
		userRoleDao.deleteByUserId(userId);		
	}


//	@Override
//	public UserRole wrapDomain(UserRoleBean entity) {
//		UserRole doMain = new UserRole(entity.getId());
//		doMain.setRole(new Role(entity.getRoleId()));
//		doMain.setUser(new User(entity.getUserId()));
//		return doMain;
//	}

}

