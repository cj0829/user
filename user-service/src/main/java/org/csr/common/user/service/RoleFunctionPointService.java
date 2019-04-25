package org.csr.common.user.service;

import org.csr.common.user.domain.RoleFunctionPoint;
import org.csr.core.persistence.service.BasisService;

/**
 * ClassName:RoleFunctionPointService <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface RoleFunctionPointService extends BasisService<RoleFunctionPoint, Long>{
    
   /**
    * deleteByRoleId: 删除角色功能点 <br/>
    * @author caijin
    * @param roleId
    * @since JDK 1.7
    */
    public void deleteByRoleId(Long roleId);
    
    /**
     * deleteByRoleId: 删除角色中指定的功能点 <br/>
     * @author caijin
     * @param roleId 角色id
     * @param functionPointIds 功能点id数组
     * @since JDK 1.7
     */
    public void deleteByRoleId(Long roleId,Long[] functionPointIds);
    
    /**
     * 根据功能点删除全部的角色下的关联的功能
     * @author caijin
     * @param id
     * @return 
     * @since JDK 1.7
     */
    public int deleteByFunctionPointIds(Long[] functionPointIds);
    
    /**
     * save: 给指定角色，修改功能点集合为：functionPointIds集合。 <br/>
     * 具体实现方式，采用和已存在的能点比较。多删，少添加。 <br/>
     * 保存，删除都可以采用此方法。
     * @author caijin
     * @param roleId 角色id
     * @param functionPointIds 
     * @return
     * @since JDK 1.7
     */
    public void save(Long roleId,Long functionPointId);
    /**
     * save: 给指定角色，修改功能点集合为：functionPointIds集合。 <br/>
     * 具体实现方式，采用和已存在的能点比较。多删，少添加。 <br/>
     * 保存，删除都可以采用此方法。
     * @author caijin
     * @param roleId 角色id
     * @param functionPointIds 
     * @return
     * @since JDK 1.7
     */
    public void save(Long roleId,Long[] functionPointIds);
    
    /**
     * 按照功能点库方式赋权。functionPointIds为功能点库。
     * 先删除全部功能点。然后在添加功能点
     * @author caijin
     * @param roleId 角色id
     * @param functionPointIds 
     * @return
     * @since JDK 1.7
     */
    public void saveByFunctionPointType(Long roleId,Long[] functionPointIds);

	/**
	 * 根据角色id，及功能点，设置当前角色中的功能点，设置为不需要授权,机构角色
	 * @param organizationId
	 * @param funId
	 */
	public RoleFunctionPoint findByOrganizationIdFunId(Long adminRoleId, Long funId);

	/**
	 * 把当前的角色下的全部权限设置为。需要授权
	 * @param roleId
	 * @return
	 */
	public int updateMustAll(Long adminRoleId);

}
