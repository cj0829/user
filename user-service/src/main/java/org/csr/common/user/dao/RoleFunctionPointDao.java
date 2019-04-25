package org.csr.common.user.dao;

import java.util.List;

import org.csr.common.user.domain.RoleFunctionPoint;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName:RoleFunctionPointDao.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-28上午10:08:43 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface RoleFunctionPointDao extends BaseDao<RoleFunctionPoint,Long> {
	
	/**
	 * findByRoleFunctionPointId: 查询功能点是否存在 <br/>
	 * @author caijin
	 * @param roleId
	 * @param functionPointId
	 * @return
	 * @since JDK 1.7
	 */
	public RoleFunctionPoint findByRoleFunctionPointId(Long roleId,Long functionPointId);
	/**
	 * findByRoleId: 根据角色ID取角色关联功能点 <br/>
	 * @author caijin
	 * @param roleId 角色ID
	 * @return
	 * @since JDK 1.7
	 */
    public List<RoleFunctionPoint> findByRoleId(Long roleId,Byte functionPointType);
   
	/**
	 * deleteByRoleId: 根据角色id删除角色和功能点关系<br/>
	 * @author caijin
	 * @param roleId
	 * @since JDK 1.7
	 */
	public int deleteByRoleId(Long roleId);
	
	/**
	 * deleteByRoleId: 删除角色中指定的功能点 <br/>
	 * @author caijin
	 * @param roleId
	 * @param functionPointIds
	 * @since JDK 1.7
	 */
	public int deleteByRoleId(Long roleId, Long[] functionPointIds);
	/**
	 * deleteByFunctionPointIds: 删除相关的所有的角色下的全部功能<br/>
	 * @author caijin
	 * @param functionPointId
	 * @return
	 * @since JDK 1.7
	 */
	public int deleteByFunctionPointIds(Long[] functionPointId);
	
	public int updateMustAll(Long adminRoleId);

	
}
