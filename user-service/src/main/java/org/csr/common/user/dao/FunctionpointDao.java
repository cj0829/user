package org.csr.common.user.dao;

import java.util.List;

import org.csr.common.user.domain.FunctionPoint;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName:BasisServiceImpl <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：功能点  <br/>
 */
public interface FunctionpointDao extends BaseDao<FunctionPoint, Long> {

	/**
	 * findByRoleId: 查询角色下的功能点，只需要查询角色关联表RoleFunctionPoint与功能点表 FunctionPoint就行<br/>
	 * 返回功能点集合
	 * @author caijin
	 * @param roleId
	 * @return
	 * @since JDK 1.7
	 */
	public List<FunctionPoint> findByRoleId(Long roleId);

	/**
	 * findIdsByRoleId: 查询角色下的功能点，只需要查询角色关联表RoleFunctionPoint<br/>
	 * 返回功能点Id
	 * @author caijin
	 * @param roleId
	 * @return
	 * @since JDK 1.7
	 */
	public List<Long> findIdsByRoleId(Long roleId);
	
	/**
	 * findAllModule: 查询模块 <br/>
	 * @author caijin
	 * @return
	 * @since JDK 1.7
	 */
	public List<FunctionPoint> findAllModule();
	
	/**
	 * findAllFunction: 查询功能<br/>
	 * @author caijin
	 * @return
	 * @since JDK 1.7
	 */
	public List<FunctionPoint> findAllFunction();
	/**
	 * findAllCanBeAuthorized:  查询全部能授权的功能点,如果structure=true，只查询库
	 * @author caijin
	 * @param structure true：包含库结构
	 * @return
	 * @since JDK 1.7
	 */
	public List<FunctionPoint> findAllCanBeAuthorized(boolean structure);
	
	/**
	 * findIdAllCanBeAuthorized:
	 * @author huayj
	 * @param structure
	 * @return
	 * @return List<FunctionPoint>
	 * @date&time 2015-9-18 上午10:05:54
	 * @since JDK 1.7
	 */
	List<Long> findIdAllCanBeAuthorized(boolean structure);
	/**
	 * findRoleFunctionPointByUserId: 根据用户id查询，用户所有的角色中的全部功能点<br>
	 * 如果，用户存在多个角色，相关的角色中所有的权功能点，全部查询出来。没有跟权限关联<br>
	 * 不包含权限的增减
	 * @author caijin
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	public List<FunctionPoint> findRoleFunctionPointByUserId(Long userId);
	
	/**
	 * findUnByUserId: 根据用户id查询 ，用户没有授权的功能点。<br/>
	 * 查询范围，在damin角色中查询<br/>
	 * @author caijin
	 * @param userId
	 * @param orgId 
	 * @return
	 * @since JDK 1.7
	 */
	public List<FunctionPoint> findUnAuthorizeByUserId(Long userId, Long orgId);
	/**
	 * findSpecialByUserId: 根据用户，及权限状态查询。当前状态的下的功能点。<br/>
	 * 关联，用户权限表来查询。<br/>
	 * 如果，是增加权限的话，需要判断增加的权限是否在admin Role 里的功能点。如果是不存在的话。剔除<br>
	 * 返回值：1. 赋予的特殊权限集合。2.取消的特殊权限集合
	 * @author caijin
	 * @param userId
	 * @param isAddPrivilege  增加权限就是给用户赋予该功能的权限；否则就是把该功能权限取消
	 * @return
	 * @since JDK 1.7
	 */
	List<FunctionPoint> findSpecialByUserId(Long userId,Byte isAddPrivilege);
	
	/**
	 * @description:查找子类的id
	 * @param:
	 * @return: List
	 */
	List<Long> findChildrenIds(Long id);

	/**
	 * @description:根据父类id删除子类数据
	 * @param:
	 * @return: void
	 */
	public void deleteChildren(Long id);

	/**
	 * updateStatus: 修改Functionpoint状态，启用，或禁止状态 <br/>
	 * @author caijin
	 * @param id
	 * @param Status
	 * @since JDK 1.7
	 */
	public int updateStatus(Long id,int Status);
	/**
	 * 根据用户查询已经授权过的权限，返回功能点<br/>
	 * 关联，用户权限表来查询，目前提供用户权限使用 <br/>
	 * 查询关联表状态[isAddPrivilege]为：1.增加权限就是给用户赋予该功能的权限
	 * @see org.csr.common.user.dao.FunctionpointDao#findSpecialByUserId(java.lang.Long)
	 */
	List<Long> findIdSpecialByUserId(Long userId, Byte isAddPrivilege);

	/**
	 *  根据用户id查询，用户查询已经授权的全部功能点<br/>
	 *  如果，用户存在多个角色，相关的角色中所有的权功能点，全部查询出来。没有跟权限关联,
	 * @see org.csr.common.user.dao.FunctionpointDao#findRoleFunctionPointByUserId(java.lang.Long)
	 */
	List<Long> findIdRoleFunctionPointByUserId(Long userId);

	/**
	 * 查询是否存在指定的用户权限
	 * @param userId
	 * @param fnId
	 * @return
	 */
	public boolean validateFunctionPoint(Long userId, Long fnId);

	List<Long> findIdAuthorizeByUserId(Long userId);

	/**
	 * 根据管理设置的角色，查询不需要授权的功能点
	 * @param adminRoleId
	 * @return
	 */
	public List<FunctionPoint> findDefaultByOrganizationId(Long adminRoleId);



}
