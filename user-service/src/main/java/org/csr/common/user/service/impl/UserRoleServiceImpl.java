package org.csr.common.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.UserRoleDao;
import org.csr.common.user.domain.Role;
import org.csr.common.user.domain.User;
import org.csr.common.user.domain.UserRole;
import org.csr.common.user.service.OrganizationService;
import org.csr.common.user.service.UserRoleService;
import org.csr.core.exception.Exceptions;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.business.domain.Organization;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.param.AndInParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:UserRoleServiceImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends SimpleBasisService<UserRole, Long>
		implements UserRoleService {
	@Resource
	private UserRoleDao userRoleDao;
	@Resource
	private OrganizationService organizationService;

	@Override
	public BaseDao<UserRole, Long> getDao() {
		return userRoleDao;
	}

	/**
	 * 保存用户角色UserRole：用户角色对象
	 * @see org.csr.common.user.service.UserRoleService#save(org.csr.common.user.domain.UserRole)
	 */
	public void save(UserRole userRole) {
		if (ObjUtil.isEmpty(checkUserRoleByroleId(userRole.getUser().getId(),userRole.getRole().getId()))) {
			userRoleDao.save(userRole);
		}
	}

	/**
	 * 删除用户角色UserRole：用户角色对象
	 * @see org.csr.common.user.service.UserRoleService#delete(org.csr.common.user.domain.UserRole)
	 */
	public void delete(UserRole userRole) {
		userRoleDao.delete(userRole);
	}
	
	/**
	 * 查询用户是否有某个角色 userId：用户id，roleId：角色id
	 * @see org.csr.common.user.service.UserRoleService#checkUserRoleByroleId(java.lang.Long, java.lang.Long)
	 */
	public UserRole checkUserRoleByroleId(Long userId, Long roleId){
		if(ObjUtil.isEmpty(userId) || ObjUtil.isEmpty(roleId)){
			return null;
		}
		return userRoleDao.existParam(new AndEqParam("user.id",userId),new AndEqParam("role.id",roleId));
	}
	/**
	 * :查询用户所有角色List userId：用户id
	 * @see org.csr.common.user.service.UserRoleService#findUserRoleList(java.lang.Long)
	 */
	public List<UserRole> findUserRoleList(Long userId) {
		User user=new User();
		user.setId(userId);
		AndEqParam userParam = new AndEqParam("user", user);
		return userRoleDao.findByParam(userParam);
	}

	/**
	 * 根据用户id删除用户角色userId：用户id
	 * @see org.csr.common.user.service.UserRoleService#deleteByUserId(java.lang.Long)
	 */
	public void deleteByUserId(Long userId) {
		userRoleDao.deleteByUserId(userId);
	}
	
	/**
	 * 根据用户id和角色Id删除用户角色userId：用户id
	 * @see org.csr.common.user.service.UserRoleService#deleteByUserIdRoleId(java.lang.Long, java.lang.Long)
	 */
	public int deleteByUserIdRoleId(Long userId,Long roleId){
		Organization org=organizationService.findbyAdminUserIdAndRoleId(userId,roleId);
		if(ObjUtil.isNotEmpty(org)){
			Exceptions.service("", "域管理员不能删除！");
		}
		//检查是否需要删除特权
		
		
		return userRoleDao.deleteByUserIdRoleId(userId, roleId);
	}

	/**
	 * @description:保存用户角色
	 * @param: roleId：角色id,userIds: 用户id数组
	 * @return: boolean
	 * @author:wangxiujuan
	 */
	public boolean saveUserRole(Long roleId, Long[] userIds) {
		UserRole userRole = null;
		if(ObjUtil.isEmpty(roleId)){
			Exceptions.service("", "没有选择角色！");
		}
		if(ObjUtil.isEmpty(userIds)){
			Exceptions.service("", "没有要授权的用户！");
		}
		
		for (Long id : userIds) {
			boolean bool = ObjUtil.isEmpty(checkUserRoleByroleId(id, roleId));
			if (bool) {
				userRole = new UserRole();
				userRole.setRole(new Role(roleId));
				User user=new User();
				user.setId(id);
				userRole.setUser(user);
				userRoleDao.save(userRole);
			}
		}
		return true;
	}

	/**
	 * @description:删除用户角色
	 * @param: roleId：角色id,userIds: 用户id数组
	 * @return: boolean
	 * @author:wangxiujuan
	 */
	public boolean deleteUserRole(Long roleId, Long[] userIds) {
		for (Long id : userIds) {
			deleteByUserIdRoleId(id, roleId);
		}
		return true;
	}
	/**
	 * @description:删除用户角色
	 * @param: roleId：角色id,userIds: 用户id数组
	 * @return: boolean
	 * @author:wangxiujuan
	 */
	@Override
	public int deleteUserRoles(Long userId,List<Long> roleIds) {
		return userRoleDao.deleteByParams(new AndInParam("user.id",userId),new AndInParam("role.id",roleIds));
	}
	
	@Override
	public Long findCountUserByRoleIds(Long[] roleIds) {
		List<Long> list = new ArrayList<Long>();
		for (int i = 0; i < roleIds.length; i++) {
			list.add(roleIds[i]);
		}
		return userRoleDao.countParam(new AndInParam("role.id",list));
	}

	@Override
	public List<Long> findByUserId(Long userId) {
		return userRoleDao.findByUserId(userId);
	}


	@Override
	public boolean saveUserRoles(Long userId, List<Long> roleIds) {
		UserRole userRole = null;
		for (Long roleId : roleIds) {
			boolean bool = ObjUtil.isEmpty(checkUserRoleByroleId(userId, roleId));
			if (bool) {
				userRole = new UserRole();
				userRole.setRole(new Role(roleId));
				User user=new User();
				user.setId(userId);
				userRole.setUser(user);
				userRoleDao.save(userRole);
			}
		}
		return false;
	}
}
