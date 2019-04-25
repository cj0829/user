package org.csr.common.user.service;

import java.util.List;

import org.csr.common.user.domain.Role;
import org.csr.core.Param;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.business.domain.Organization;
import org.csr.core.persistence.service.BasisService;

/**
 * ClassName:RoleService <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface RoleService extends BasisService<Role, Long>{

    /**
     * @description:保存自定义角色
     * @param: Parameter：角色对象
     * @return: boolean
     * @author:wangxiujuan
     */
    public void saveCustom(Role role);

    /**
     * saveAdmin: 保存管理员角色 <br/>
     * @author caijin
     * @param role
     * @since JDK 1.7
     */
    public void saveAdmin(Role role);
    /**
     * @description:修改自定义角色
     * @param: Parameter：角色对象
     * @return: boolean
     * @author:wangxiujuan
     */
    public void updateCustom(Role role);
    
    /**
     * updateAdmin: 修改管理员角色 <br/>
     * @author caijin
     * @param role
     * @since JDK 1.7
     */
    public void updateAdmin(Role role);
   /**
    * delete: 检查角色中是否有用户，如果有用户，则当前角色不能删除 <br/>
    * 系统角色不能删除。
    * @author caijin
    * @param id
    * @since JDK 1.7
    */
    public void delete(Long id);

    /**
     * @description:根据角色id删除多个角色 ,检验角色是否有用户使用。
     * @param: parameterIds：角色id数组
     * @return: void
     * @author:wangxiujuan
     */
    public void deleteRole(Long[] roleIds);

    /**
     * @description:用于验证添加角色时角色名唯一
     * @param: Parameter：角色对象
     * @return: boolean
     * @author:wangxiujuan
     */
    public boolean checkAddRoleName(String name);

    /**
     * @description:用于验证修改角色时角色名唯一
     * @param: Parameter：角色对象
     * @return: boolean
     * @author:wangxiujuan
     */
    public boolean checkUpdateRoleName(Long roleId,String name);
  
  
    /**
     * findRoleList:  根据组织机构获取角色列表  <br/>
     * @author caijin
     * @param root 组织机构root
     * @return
     * @since JDK 1.7
     */
	public List<Role> findRoleList(Param... param);
    
	  /**
     * findOrgRoleByRoot: 查询机构管理员角色，一个机构只有一个管理员角色 <br/>
     * @author caijin
     * @param root
     * @return
     * @since JDK 1.7
     */
   public Role findOrgRole(Long orgId);
    
    /**
     * findAdminRoleByUserId: 估计用户查询管理员角色 <br/>
     * @author caijin
     * @param userId
     * @return
     * @since JDK 1.7
     */
    public Role findAdminRoleByUserId(Long userId);

    /**
	 * findUnJoinUserByRoleId: 查询用户下没有的 角色<br/>
	 * @author wangxiujuan
	 * @param page :分页对象
	 * @param roleId：角色id
	 * @return
	 * @since JDK 1.7
	 */
	public PagedInfo<Role> findUnByUserId(Page page, Long userId,String name);

	/**
	 * findJoinUserByRoleId: 查询用户下的角色 <br/>
	 * @author caijin
	 * @param page
	 * @param roleId
	 * @return
	 * @since JDK 1.7
	 */
	public PagedInfo<Role> findByUserId(Page page,Long userId,String name);
	
	/**
	 * findJoinUserByRoleId: 查询用户下的角色 <br/>
	 * @author caijin
	 * @param page
	 * @param roleId
	 * @return
	 * @since JDK 1.7
	 */
	public List<Long> findByUserId(Long userId);
	/**
	 * updateSystemNameByOrg: 修改系统角色名称 <br/>
	 * @author caijin
	 * @param page
	 * @param roleId
	 * @return
	 * @since JDK 1.7
	 */
	public int updateSystemNameByOrg(Organization orga);
}
