package org.csr.common.user.facade;

import java.util.List;

import org.csr.common.user.domain.AgenciesUser;
import org.csr.common.user.entity.UserBean;


/**
 * @(#)Sss.java
 * 
 *              ClassName:AgenciesUser.java <br/>
 *              Date: Wed Oct 11 19:54:07 CST 2017 <br/>
 * @author summy <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 Facade接口
 */
public interface AgenciesUserFacade extends UserFacade {
	/**
	 * 修改，存在相同数据，不修改，需要删除 旧的。
	 * @param agenciesUser
	 * @return
	 */
	UserBean update(AgenciesUser agenciesUser);
	/**
	 * 删除机构用户，根据机构id，及用户id
	 * @param userId
	 * @param agenciesId
	 */
	int deleteByUserIdAgenciesId(Long userId, Long agenciesId);
	
	/**
	 * 删除机构用户，根据机构id，及用户id
	 * @param userId
	 * @param agenciesId
	 */
	int deleteByUserId(Long userId);
	/**
	 * @param agenciesUser
	 */
	UserBean saveAgenciesUser(AgenciesUser agenciesUser);
	/**
	 * 检测是否，存在
	 * @param userId
	 * @param agenciesId
	 * @return
	 */
	UserBean checkUserAgencies(Long userId, Long agenciesId);

	/**
	 * 根据用户id查询，所有的存在的 机构
	 * @param userId
	 * @param agenciesId
	 * @return
	 */
	List<Long> findAgenciesByUserId(Long userId);
	
	
	/**
	 * 根据用户id查询，所有的存在的 机构
	 * @param userId
	 * @param agenciesId
	 * @return
	 */
	List<Long> findUserByAgenciesId(Long agenciesId);
	
	/**
	 * 根据用户id查询，所有的存在的 机构
	 * @param userId
	 * @param agenciesId
	 * @return
	 */
	List<Long> findUserByAgenciesIds(List<Long> agenciesIds);
	
	/**
	 * 根据机构查询用户并，创建到全部机构下
	 * @param agenciesId
	 * @return
	 */
	int updateAllByAgenciesId(Long agenciesId);
}
