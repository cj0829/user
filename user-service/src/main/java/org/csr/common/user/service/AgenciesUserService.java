package org.csr.common.user.service;


import java.util.List;

import org.csr.common.user.domain.AgenciesUser;
import org.csr.common.user.domain.User;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.service.BasisService;

/**
 * @(#)Sss.java 
 *       
 * ClassName:AgenciesUser.java <br/>
 * Date: Wed Oct 11 19:54:07 CST 2017 <br/>
 * @author summy <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  service接口
 */
public interface AgenciesUserService extends BasisService<AgenciesUser, Long> {
	
	PagedInfo<AgenciesUser> findListPage(Page page, String loginName, String name,Long userId, List<Byte> userRoleType, List<Long> agenciesIds);

	PagedInfo<User> findUnJoinUserByRoleId(Page page, Long roleId,String loginName, String name, List<Long> agenciesIds);

	PagedInfo<User> findJoinUserByRoleId(Page page, Long roleId,String loginName, String name, List<Long> agenciesIds);

	PagedInfo<User> findUnJoinUserByCollectionId(Page page,Long safeResourceCollectionId, String loginName, String name,List<Long> agenciesIds);

	PagedInfo<User> findJoinUserByCollectionId(Page page,Long safeResourceCollectionId, String loginName, String name,List<Long> agenciesIds);

	PagedInfo<User> findInterestedFriends(Page page, List<Long> userIds,Byte userRoleType, String name, Long agenciesId);

	List<Long> findUserIdList(String loginName, String name,List<Byte> userRoleType, List<Long> agenciesIds);

	List<AgenciesUser> findList(String loginName, String name, List<Byte> userRoleType,List<Long> agenciesIds);
	/**
	 * 保存，如果从存在相同数据。不保存
	 * @param agenciesUser
	 * @return
	 */
	AgenciesUser save(AgenciesUser agenciesUser);


	/**
	 * 修改，存在相同数据，不修改，需要删除 旧的。
	 * @param agenciesUser
	 * @return
	 */
	AgenciesUser update(AgenciesUser agenciesUser);


	/**
	 * 删除机构用户，根据机构id，及用户id
	 * @param userId
	 * @param agenciesId
	 */
	int deleteByUserIdAgenciesId(Long userId, Long agenciesId);


	/**
	 * 检测是否，存在
	 * @param userId
	 * @param agenciesId
	 * @return
	 */
	AgenciesUser checkUserAgencies(Long userId, Long agenciesId);

	/**
	 * 用户 的 所有机构id
	 * @param userId
	 */
	int deleteByUserId(Long userId);
}
