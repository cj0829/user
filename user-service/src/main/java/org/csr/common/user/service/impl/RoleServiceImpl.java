package org.csr.common.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.constant.RoleType;
import org.csr.common.user.dao.RoleDao;
import org.csr.common.user.dao.RoleFunctionPointDao;
import org.csr.common.user.dao.UserRoleDao;
import org.csr.common.user.domain.Role;
import org.csr.common.user.service.OrganizationService;
import org.csr.common.user.service.RoleService;
import org.csr.common.user.service.UserRoleService;
import org.csr.core.Param;
import org.csr.core.exception.Exceptions;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.business.domain.Organization;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.param.AndInParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.PropertiesUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:RoleServiceImpl.java <br/>
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
@Service("roleService")
public class RoleServiceImpl extends SimpleBasisService<Role,Long> implements RoleService{
	@Resource
	private RoleDao roleDao;
	@Resource
	private RoleFunctionPointDao roleFunctionPointDao;
	@Resource
	private UserRoleDao userRoleDao;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private OrganizationService organizationService;

	@Override
	public BaseDao<Role,Long> getDao(){
		return roleDao;
	}

	/**
	 * 保存自定义[Custom]用户类型的用户
	 * 
	 * @see org.csr.common.user.service.RoleService#saveCustom(org.csr.common.user.domain.Role)
	 */
	public void saveCustom(Role role){
		if(ObjUtil.isEmpty(role)){
			Exceptions.service("FunctionPointCodeHasExist","");
		}

		 if(!ObjUtil.longNotNull(role.getOrgId())){
			 Exceptions.service("organizationRootIsNull","organizationRootIsNull");
		 }

		role.setRoleType(RoleType.CUSTOM);
		if(checkAddRoleName(role.getName())){
			Exceptions.service("FunctionPointCodeHasExist",PropertiesUtil.getExceptionMsg("roleNameIsExist"));
		}
		roleDao.save(role);
	}

	/**
	 * 保存 系统管理员[system]用户类型的用户
	 * 
	 * @see org.csr.common.user.service.RoleService#saveAdmin(org.csr.common.user.domain.Role)
	 */
	public void saveAdmin(Role role){
		if(ObjUtil.isEmpty(role)){
			Exceptions.service("FunctionPointCodeHasExist","");
		}

		if(!ObjUtil.longNotNull(role.getOrgId())){
			Exceptions.service("organizationRootIsNull","organizationRootIsNull");
		}

		role.setRoleType(RoleType.SYSTEM);
		if(checkAddRoleName(role.getName())){
			role.setName(role.getName()+1);
//			Exceptions.service("FunctionPointCodeHasExist","");
		}
		roleDao.save(role);
	}

	/**
	 * 修改 自定义[Custom]用户类型的用户
	 * 
	 * @see org.csr.common.user.service.RoleService#update(org.csr.common.user.domain.Role)
	 */
	public void updateCustom(Role role){

		if(ObjUtil.isEmpty(role)||ObjUtil.isEmpty(role.getId())){
			Exceptions.service("FunctionPointCodeHasExist","");
		}

		// if(!ObjUtil.longNotNull(role.getRoot())){
		// Exceptions.service("organizationRootIsNull","organizationRootIsNull");
		// }

		if(checkUpdateRoleName(role.getId(),role.getName())){
			Exceptions.service("FunctionPointCodeHasExist","");
		}
		Role role2=roleDao.findById(role.getId());
		if(RoleType.SYSTEM.equals(role2.getRoleType())){
			Exceptions.service("", "不能够修改系统管理员的信息");
		}
		//如果没有值，不修改当前的root
		if(ObjUtil.isNotEmpty(role.getOrgId())){
			role2.setOrgId(role.getOrgId());
		}
		role2.setName(role.getName());
		role2.setRoleType(role.getRoleType());
		role2.setRemark(role.getRemark());
		roleDao.update(role2);
	}

	/**
	 * 修改 系统管理员[System]用户类型的用户
	 * 
	 * @see org.csr.common.user.service.RoleService#updateAdmin(org.csr.common.user.domain.Role)
	 */
	public void updateAdmin(Role role){

		if(ObjUtil.isEmpty(role)||ObjUtil.isEmpty(role.getId())){
			Exceptions.service("FunctionPointCodeHasExist","");
		}

		if(!ObjUtil.longNotNull(role.getOrgId())){
			Exceptions.service("organizationRootIsNull","organizationRootIsNull");
		}

		if(checkUpdateRoleName(role.getId(),role.getName())){
			Exceptions.service("FunctionPointCodeHasExist","");
		}
		Role role2=roleDao.findById(role.getId());
		//如果没有值，不修改当前的root
		if(ObjUtil.isNotEmpty(role.getOrgId())){
			role2.setOrgId(role.getOrgId());
		}
		role2.setName(role.getName());
		role2.setRoleType(RoleType.SYSTEM);
		role2.setRemark(role.getRemark());
		roleDao.update(role2);
	}

	/**
	 * 根据id取删除角色,检验角色是否有用户使用,系统角色不能删除
	 * 简单描述该方法的实现功能（可选）.
	 * @see org.csr.common.user.service.RoleService#delete(java.lang.Long)
	 */
	public void delete(Long id){
		if(ObjUtil.isEmpty(id)){
			Exceptions.service("noSelectData",PropertiesUtil.getExceptionMsg("noSelectData"));
		}
		Role role = roleDao.findById(id);
		if(ObjUtil.isEmpty(role)){
			Exceptions.service("noSelectData","角色不存在");
		}
		if(RoleType.SYSTEM.equals(role.getRoleType())){
			Exceptions.service("noSelectData","系统角色不能删除");
		}
		if(userRoleDao.checkUserRoleByRoleId(id)){
			roleDao.deleteById(id);
			roleFunctionPointDao.deleteByRoleId(id);
		}else{
			Exceptions.service("roleIsUsed","");
		}
	}

	/**
	 * @description:根据角色id删除多个角色
	 * @param: parameterIds：角色id数组
	 * @return: void
	 * @author:huayj
	 */
	public void deleteRole(Long[] roleIds){
		if(ObjUtil.isEmpty(roleIds)){
			Exceptions.service("noSelectData",PropertiesUtil.getExceptionMsg("noSelectData"));
		}
		Long Count = organizationService.findCountUserByRoleIds(roleIds);
		Long count = userRoleService.findCountUserByRoleIds(roleIds);
		
		if(count==0 && Count==0){
			roleFunctionPointDao.deleteByParams(new AndInParam("role.id",ObjUtil.asList(roleIds)));
			roleDao.deleteByIds(roleIds);
		}else {
			Exceptions.service("100010", "存在被引用的角色，无法删除");
		}
	}

	/**
	 * @description:用于验证添加角色名是否唯一
	 * @param: Parameter:角色对象
	 * @return: boolean
	 * @author:wangxiujuan
	 */
	public boolean checkAddRoleName(String name){
		if(ObjUtil.isEmpty(checkRoleName(name))){
			return false;
		}
		return true;
	}

	/**
	 * checkAddRoleName:检查当前功能点 名称是否存在 <br/>
	 * 
	 * @author caijin
	 * @param functionPointCode
	 * @return
	 * @since JDK 1.7
	 */
	private Role checkRoleName(String name){
		AndEqParam and=new AndEqParam("name",name);
		return roleDao.existParam(and);
	}

	/**
	 * @description:用于验证修改角色名是否唯一
	 * @param: Parameter:角色对象
	 * @return: boolean
	 * @author:wangxiujuan
	 */
	public boolean checkUpdateRoleName(Long roleId,String name){
		Role oldRole=checkRoleName(name);
		if(ObjUtil.isEmpty(oldRole)||oldRole.getId().equals(roleId)){
			return false;
		}
		return true;
	}


	/**
	 * 根据组织机构获取角色列表如果root id 为null 查询全部角色root：组织机构id
	 * 
	 * @see org.csr.common.user.service.RoleService#findRoleListByRoot(java.lang.Long)
	 */
	public List<Role> findRoleList(Param... param){
		return roleDao.findByParam(param);
	}


	/**
	 * @description:根据rootId查询角色
	 * @return: admin 角色
	 * @author:caijin
	 */
	@Override
	public Role findOrgRole(Long orgId){
		Organization org = organizationService.findById(orgId);
		if(ObjUtil.isEmpty(org)){
			Exceptions.service("noSelectData","域不存在");
		}
		if(ObjUtil.isEmpty(org.getAdminRoleId())){
			Exceptions.service("noSelectData","域的管理角色不存在");
		}
		Role role=roleDao.findById(org.getAdminRoleId());
		if(ObjUtil.isEmpty(org)){
			Exceptions.service("noSelectData","管理角色不存在");
		}
		return role;
	}

	/**
	 * 估计用户查询管理员角色
	 * 
	 * @see org.csr.common.user.service.RoleService#findAdminRoleByUserId(java.lang.Long)
	 */
	@Override
	public Role findAdminRoleByUserId(Long userId){
		return roleDao.findAdminRoleByUserId(userId);
	}

	@Override
	public PagedInfo<Role> findUnByUserId(Page page, Long userId,String name) {
		return roleDao.findUnByUserId(page,userId,name);
	}

	@Override
	public PagedInfo<Role> findByUserId(Page page, Long userId,String name) {
		return roleDao.findByUserId(page,userId,name);
	}
	@Override
	public List<Long> findByUserId(Long userId) {
		return roleDao.findByUserId(userId);
	}

	@Override
	public int updateSystemNameByOrg(Organization organization) {
		int i=0;
		if(ObjUtil.isNotEmpty(organization)){
			List<Role>  roleList =  roleDao.findByParam(new AndEqParam("orgId", organization.getId()),new AndEqParam("roleType", RoleType.SYSTEM));
			if(ObjUtil.isNotEmpty(roleList)){
				for (Role role : roleList) {
					role.setName(organization.getName()+ "管理员");
					roleDao.update(role);
					i++;
				}
			}
		}
		return i;
	}
}
