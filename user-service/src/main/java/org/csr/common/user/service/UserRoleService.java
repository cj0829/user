package org.csr.common.user.service;

import java.util.List;

import org.csr.common.user.domain.UserRole;
import org.csr.core.persistence.service.BasisService;

/**
 * ClassName:UserRoleService.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface UserRoleService extends BasisService<UserRole, Long>{
	/**
	 * save: 保存用户角色 <br/>
	 * @author caijin
	 * @param userRole：用户角色对象
	 * @since JDK 1.7
	 */
	public void save(UserRole userRole);
	/**
	 * delete: 删除用户角色 <br/>
	 * @author caijin
	 * @param userRole：用户角色对象
	 * @since JDK 1.7
	 */
	public void delete(UserRole userRole);
	
	/**
	 * checkUserRoleByroleId: 查询用户是否有某个角色<br/>
	 * @author caijin
	 * @param userId：用户id
	 * @param roleId：角色id
	 * @return
	 * @since JDK 1.7
	 */
	public UserRole checkUserRoleByroleId(Long userId, Long roleId);
	
	/**
	 * findUserRoleList: 查询用户所有角色List <br/>
	 * @author caijin
	 * @param userId：用户id
	 * @return
	 * @since JDK 1.7
	 */
	public List<UserRole> findUserRoleList(Long userId);
	/**
	 * deleteByUserId: 根据用户id删除用户角色 <br/>
	 * @author caijin
	 * @param userId：用户id
	 * @since JDK 1.7
	 */
	public void deleteByUserId(Long userId);
	
	/**
	 * deleteByUserIdRoleId: 根据用户id和角色Id删除用户角色<br/>
	 * @author caijin
	 * @param userId：用户id
	 * @param roleId :角色id
	 * @since JDK 1.7
	 */
	public int deleteByUserIdRoleId(Long userId,Long roleId);
	
	
	/**
	 * @description:保存用户角色
	 * @param: roleId：角色id,userIds: 用户id数组
	 * @return: boolean
	 * @author:wangxiujuan
	 */
	public boolean saveUserRole(Long roleId,Long[] userIds);
	
	
	
	/**
	 * @description:保存用户角色
	 * @param: roleId：角色id,userIds: 用户id数组
	 * @return: boolean
	 * @author:wangxiujuan
	 */
	public boolean saveUserRoles(Long userId,List<Long> roleIds);
	/**
	 * @description:删除用户角色
	 * @param: roleId：角色id,userIds: 用户id数组
	 * @return: boolean
	 * @author:wangxiujuan
	 */
	public boolean deleteUserRole(Long roleId,Long[] userIds);
	/**
	 * findCountUserByRoleIds:
	 * @author huayj
	 * @param roleIds
	 * @return
	 * @return Long
	 * @date&time 2015-9-18 下午5:36:30
	 * @since JDK 1.7
	 */
	public Long findCountUserByRoleIds(Long[] roleIds);
	/**
	 * 根据userId查询它的角色id
	 * @param userId
	 * @return
	 */
	public List<Long> findByUserId(Long userId);
	
	/**
	 * 删除一个用的全部角色
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	int deleteUserRoles(Long userId, List<Long> roleIds);
}
