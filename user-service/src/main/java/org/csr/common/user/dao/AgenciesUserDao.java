package org.csr.common.user.dao;

import java.util.List;

import org.csr.common.user.domain.AgenciesUser;
import org.csr.common.user.domain.User;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName:AgenciesUser.java <br/>
 * Date: Wed Oct 11 19:54:07 CST 2017 <br/>
 * 
 * @author summy <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  dao接口
 */
public interface AgenciesUserDao extends BaseDao<AgenciesUser,Long>{

	
	/**
	 * 查询，用户
	 * @param page
	 * @param loginName
	 * @param name
	 * @param id
	 * @param userRoleType
	 * @param agenciesIds
	 * @return
	 */
	PagedInfo<AgenciesUser> findListPage(Page page, String loginName, String name,Long userId, List<Byte> userRoleType, List<Long> agenciesIds);
	
	List<AgenciesUser> findList(String loginName, String name, List<Byte> userRoleType,List<Long> agenciesIds);

	PagedInfo<User> findUnJoinUserByRoleId(Page page, Long roleId,String loginName, String name, List<Long> agenciesIds);

	PagedInfo<User> findJoinUserByRoleId(Page page, Long roleId,String loginName, String name, List<Long> agenciesIds);

	PagedInfo<User> findUnJoinUserByCollectionId(Page page,Long safeResourceCollectionId, String loginName, String name,List<Long> agenciesIds);

	PagedInfo<User> findJoinUserByCollectionId(Page page,Long safeResourceCollectionId, String loginName, String name,List<Long> agenciesIds);

	PagedInfo<User> findInterestedFriends(Page page, List<Long> userIds,Byte userRoleType, String name, Long agenciesId);

	List<Long> findUserIdList(String loginName, String name,List<Byte> userRoleType, List<Long> agenciesIds);

}
