package org.csr.common.user.service;

import java.util.List;

import org.csr.common.user.domain.UserSpecialFunctionPoint;
import org.csr.common.user.entity.UserSpecialFunctionPointNode;
import org.csr.core.persistence.service.BasisService;

/**
 * ClassName:UserSpecialFunctionPointService <br/>
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
public interface UserSpecialFunctionPointService extends
		BasisService<UserSpecialFunctionPoint, Long> {

	/**
	 * findByUserId: 获取用户功能点 <br/>
	 * 
	 * @author caijin
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	public List<UserSpecialFunctionPoint> findByUserId(Long userId);

	/**
	 * 查询已启用的特权
	 * 
	 * @param userId
	 * @param root
	 * @return
	 */
	public List<UserSpecialFunctionPoint> findExistEnableUserSpecialFunctionPoint(
			Long userId, Long root);

	/**
	 * 查询已禁用的特权
	 * 
	 * @param userId
	 * @param root
	 * @return
	 */
	public List<UserSpecialFunctionPoint> findExistDisableUserSpecialFunctionPoint(
			Long userId, Long root);

	/**
	 * 需要比较当前的权限，与赋予的权限是否存在差异，如果存在差异，作出相对应的操作（增加，或删除） 如果当前权限在角色中已经存在，则需要做删除<br>
	 * 如果单
	 * 
	 * @author caijin
	 * @param userId
	 *            用户id
	 * @param functionPointIds
	 *            功能点id数组
	 * @param flag
	 * @return
	 * @since JDK 1.7
	 */
	public void saveUserSpecialFunctionPoint(Long userId,
			Long[] functionPointIds);

	/**
	 * updateUserSpecialFunctionPoint: 增加或者删除用户特权 <br/>
	 * userId不能为空<br/>
	 * 根据类型，增加对应的类型权限<br/>
	 * 
	 * @author caijin
	 * @param userId
	 *            用户id
	 * @param functionPointIds
	 *            功能点id数组
	 * @param flag
	 * @return
	 * @since JDK 1.7
	 */
	public void updateUserSpecialFunctionPoint(Long userId,
			Long[] functionPointIds);

	/**
	 * saveUserSpecialFunctionPoint: 增加用户权限， <br/>
	 * userId不能为空<br/>
	 * 根据类型，增加对应的类型权限<br/>
	 * 
	 * @author caijin
	 * @param userId
	 *            用户id
	 * @param functionPointIds
	 *            功能点id数组
	 * @param flag
	 * @return
	 * @since JDK 1.7
	 */
	public void addUserSpecialFunctionPoint(Long userId, Long[] functionPointIds);

	/**
	 * deleteUserSpecialFunctionPoint: 删除用户权限，根据权限类型。<br/>
	 * userId不能为空<br/>
	 * 根据类型，删除对应的类型权限<br/>
	 * 
	 * @author caijin
	 * @param userId
	 * @param isAddPrivilege
	 * @since JDK 1.7
	 */
	public void deleteUserSpecialFunctionPoint(Long userId,
			Long[] functionPointIds);

	/**
	 * 删除用户已经启用的特权
	 * 
	 * @param userId
	 * @param functionPointIds
	 * @return
	 */
	public int delExistEnableUserSpecialFunctionPoint(List<Long> ids);

	/**
	 * 删除用户已经禁用的特权
	 * 
	 * @param userId
	 * @param asList
	 * @return
	 */
	public int delExistDisableUserSpecialFunctionPoint(List<Long> ids);

	public List<UserSpecialFunctionPointNode> wrapCompleteStructure(
			List<UserSpecialFunctionPoint> findExistEnableUserSpecialFunctionPoint,
			boolean b);

}
