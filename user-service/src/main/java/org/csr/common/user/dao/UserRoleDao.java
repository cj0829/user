package org.csr.common.user.dao;

import java.util.List;

import org.csr.common.user.domain.UserRole;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName:UserRoleDao.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-28上午10:08:31 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface UserRoleDao extends BaseDao<UserRole, Long> {

	/**
	 * deleteByUserId: 根据用户id删除用户角色<br/>
	 * @author caijin
	 * @param userId：用户id
	 * @since JDK 1.7
	 */
	public void deleteByUserId(Long userId);
	
	/**
	 * deleteByUserIdRoleId: 根据用户id和角色Id删除用户角色 <br/>
	 * @author caijin
	 * @param userId：用户id
	 * @param roleId：角色id
	 * @return: UserRole
	 * @since JDK 1.7
	 */
	public int deleteByUserIdRoleId(Long userId,Long roleId);

	/**
	 * findUserRoleById: 根据用户Id和角色Id查询用户角色<br/>
	 * @author:wangxiujuan
	 * @param userId：用户id
	 * @param roleId：角色id
	 * @return
	 * @since JDK 1.7
	 */
	public UserRole findUserRoleById(Long userId, Long roleId);

	/**
	 * checkUserRoleByRoleId: 检验角色是否有用户使用 <br/>
	 * @author caijin
	 * @param roleId：角色id
	 * @return
	 * @since JDK 1.7
	 */
	public boolean checkUserRoleByRoleId(Long roleId);

	/**
	 * 根据userId查询它的角色id
	 * @param userId
	 * @return
	 */
	public List<Long> findByUserId(Long userId);

}
