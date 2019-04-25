/**
 * Project Name:user-Service
 * File Name:RoleFacadeImpl.java
 * Package Name:org.csr.common.user.facade.impl
 * Date:2016-9-1下午5:09:52
 * Copyright (c) 2016, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.facade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.csr.common.user.dao.RoleDao;
import org.csr.common.user.domain.Role;
import org.csr.common.user.entity.RoleBean;
import org.csr.common.user.facade.RoleFacade;
import org.csr.common.user.service.OrganizationService;
import org.csr.common.user.service.RoleService;
import org.csr.core.Constants;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.business.domain.Organization;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.support.ToValue;
import org.springframework.stereotype.Service;

/**
 * ClassName:RoleFacadeImpl.java <br/>
 * System Name：    在线学习系统 <br/>
 * Date:     2016-9-1下午5:09:52 <br/>
 * @author   Administrator <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
@Service("roleFacade")
public class RoleFacadeImpl extends SimpleBasisFacade<RoleBean, Role, Long> implements RoleFacade {

	@Resource
	private RoleService roleService;
	@Resource
	private RoleDao roleDao;
	@Resource
	private OrganizationService organizationService;
	
	@Override
	public PagedInfo<RoleBean> findUnByUserId(Page page, Long userId,String name) {
		PagedInfo<Role> pagedRole = roleService.findUnByUserId(page,userId,name);
		PagedInfo<RoleBean> pagedroleBean = PersistableUtil.toPagedInfoBeans(pagedRole, new SetBean<Role>(){
			public RoleBean setValue(Role doMain) {
				return wrapBean(doMain);
			}
		});
		return pagedroleBean;
	}

	@Override
	public PagedInfo<RoleBean> findByUserId(Page page, Long userId, String name) {
		PagedInfo<Role> pagedRole = roleService.findByUserId(page,userId,name);
		PagedInfo<RoleBean> pagedroleBean = PersistableUtil.toPagedInfoBeans(pagedRole, new SetBean<Role>(){
			public RoleBean setValue(Role doMain) {
				return wrapBean(doMain);
			}
		});
		return pagedroleBean;
	}
	@Override
	public List<Long> findByUserId(Long userId) {
		if(ObjUtil.isEmpty(userId)){
			return new ArrayList<Long>(0);
		}
		return roleService.findByUserId(userId);
	}
	@Override
	public void saveCustom(Role role) {
		//Role wrapDomain = wrapDomain(role);
		roleService.saveCustom(role);
	}

	@Override
	public void updateCustom(Role role) {
		//Role wrapDomain = wrapDomain(role);
		roleService.updateCustom(role);
	}

	@Override
	public void deleteRole(Long[] roleIds) {
		roleService.deleteRole(roleIds);
	}

	@Override
	public boolean checkAddRoleName(String name) {
		return roleService.checkAddRoleName(name);
	}

	@Override
	public boolean checkUpdateRoleName(Long roleId, String name) {
		return roleService.checkUpdateRoleName(roleId,name);
	}

	@Override
	public RoleBean findOrgRole(Long orgId) {
		Role role = roleService.findOrgRole(orgId);
		return wrapBean(role);
	}

	@Override
	public List<RoleBean> findRoleList() {
		List<Role> list = roleService.findRoleList();
		List<RoleBean> listBean = PersistableUtil.toListBeans(list, new SetBean<Role>(){
			public RoleBean setValue(Role doMain) {
				return wrapBean(doMain);
			}
		});
		return listBean;
	}

	@Override
	public RoleBean wrapBean(Role doMain) {
		return RoleBean.wrapBean(doMain);
	}

//	@Override
//	public Role wrapDomain(RoleBean entity) {
//		Role role = new Role(entity.getId());
//		role.setName(entity.getName());
//		role.setRoleType(entity.getRoleType());
//		role.setRemark(entity.getRemark());
//		role.setRoot(entity.getRoot());
//		return role;
//	}

	@Override
	public PagedInfo<RoleBean> findAllListPage(Page page,Long primaryOrgId) {
		PagedInfo<Role> result = null;
		if(Constants.USER_SUPER.equals(UserSessionContext.getUserSession().getLoginName())){
			result = roleDao.findAllPage(page);
		}else{
			result = roleDao.findByOrgId(page, primaryOrgId);
		}
		
		List<Long> rootIds=PersistableUtil.arrayTransforList(result.getRows(),new ToValue<Role, Long>(){
			@Override
			public Long getValue(Role obj) {
				return obj.getOrgId();
			}
    	});
    	final Map<Long,Organization> orgMap=organizationService.findMapByIds(new ArrayList<Long>(rootIds));
    	PagedInfo<RoleBean> resultBean  = PersistableUtil.toPagedInfoBeans(result, new SetBean<Role>(){
			@Override
			public RoleBean setValue(Role doMain) {
				RoleBean bean = wrapBean(doMain);
				Organization org=orgMap.get(doMain.getOrgId());
				if(ObjUtil.isNotEmpty(org)){
					bean.setOrgName(org.getName());
					bean.setOrgId(doMain.getId());
				}
				return bean;
			}
    	});
		return resultBean;
	}

	@Override
	public BaseDao<Role, Long> getDao() {
		return roleDao;
	}


}

