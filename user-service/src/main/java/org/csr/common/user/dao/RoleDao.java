package org.csr.common.user.dao;

import java.util.List;

import org.csr.common.user.domain.Role;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName:RoleDao <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface RoleDao extends BaseDao<Role,Long> {
	
	/**
	 * findAdminRoleByUserId:根据用户id查询管理员角色 <br/>
	 * @author caijin
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	public Role findAdminRoleByUserId(Long userId);

	/**
	 * findJoinUserByRoleId: 查询用户下的角色 <br/>
	 * @author caijin
	 * @param page
	 * @param userId
	 * @param name
	 * @return
	 * @since JDK 1.7
	 */
	public PagedInfo<Role> findByUserId(Page page, Long userId,String name);

	/**
	 * findJoinUserByRoleId: 查询用户下的角色 <br/>
	 * @author caijin
	 * @param page
	 * @param userId
	 * @param name
	 * @return
	 * @since JDK 1.7
	 */
	public List<Long> findByUserId(Long userId);
	/**
	 * findUnJoinUserByRoleId: 查询用户下没有的 角色 <br/>
	 * @author caijin
	 * @param page
	 * @param userId
	 * @param name
	 * @return
	 * @since JDK 1.7
	 */
	public PagedInfo<Role> findUnByUserId(Page page, Long userId,String name);

	public PagedInfo<Role> findByOrgId(Page page, Long primaryOrgId);

	
  
}
