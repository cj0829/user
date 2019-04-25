package org.csr.common.user.dao;

import java.util.List;

import org.csr.common.user.domain.User;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName:BasisServiceImpl <br/>
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
public interface UserDao extends BaseDao<User, Long> {

	/**
	 * 根据登陆名取用户明细
	 * 
	 * @author caijin
	 * @param loginName
	 * @return
	 * @since JDK 1.7
	 */
	public User findByLoginName(String loginName);

	/**
	 * findListPage: 查询用户集合。 <br/>
	 * 如果组织机构root为null则查询全部机构的用户 <br/>
	 * 用户名为模糊查询，如果用户名为null 或者 用户名为空字符串 ，则查询全部 用户类型为为null，则查询全部用户类型的用户
	 * 
	 * @author caijin
	 * @param page
	 * @param root  按域，只查询当前域
	 * @param loginName 登录名
	 * @param name  姓名
	 * @param id 变化
	 * @param userRoleType 用户角色类型
	 * @param agenciesIds 结构id
	 * @return
	 * @since JDK 1.7
	 */
	public PagedInfo<User> findListPage(Page page, String loginName, String name, Long id, List<Byte> userRoleType,
			List<Long> agenciesIds);

	/**
	 * 查询当前下的全部被管理用户
	 * 
	 * @author huayj
	 * @param managerUserId
	 * @return
	 * @since JDK 1.7
	 */
	public List<User> findChildUserByManagerUserId(Long managerUserId, String loginName, String name, Long id,
			Byte userRoleType, List<Long> agenciesIds);

	/**
	 * 查询子用户，用户对象只包含，id，和是否有下级用户
	 * 
	 * @author caijin
	 * @param managerUserId
	 * @param loginName
	 * @param name
	 * @param id
	 * @param userRoleType
	 * @param agenciesIds
	 * @return
	 * @since JDK 1.7
	 */
	List<User> findChildIdUserByManagerUserId(Long managerUserId, String loginName, String name, Long id,
			Byte userRoleType, List<Long> agenciesIds);

	/**
	 * findList: 查询用户集合。 <br/>
	 * 如果组织机构root为null则查询全部机构的用户 <br/>
	 * 用户名为模糊查询，如果用户名为null 或者 用户名为空字符串 ，则查询全部 用户类型为为null，则查询全部用户类型的用户
	 * 
	 * @author caijin
	 * @param root
	 * @param name
	 * @param userType
	 * @return
	 * @since JDK 1.7
	 */
	public List<User> findList(Long orgId, String loginName, Byte userRoleType);

	/**
	 * findUnJoinUserByRoleId: 查询角色下没有的用户 <br/>
	 * 
	 * @author wangxiujuan
	 * @param page:分页对象
	 * @param roleId：角色id
	 * @return
	 * @since JDK 1.7
	 */
	public PagedInfo<User> findUnJoinUserByRoleId(Page page, Long roleId, String loginName, String name,
			List<Long> agenciesIds);

	/**
	 * findJoinUserByRoleId: 查询角色下的用户 <br/>
	 * 
	 * @author caijin
	 * @param page
	 * @param roleId
	 * @return
	 * @since JDK 1.7
	 */
	public PagedInfo<User> findJoinUserByRoleId(Page page, Long roleId, String loginName, String name,
			List<Long> agenciesIds);

	/**
	 * @param organizationId
	 * @return
	 */
	public User findByOrganizationAdmin(Long organizationId);

	/**
	 * updateBatchPassword: 批量修改用户密码 <br/>
	 * 
	 * @author caijin
	 * @param userIds ：用户id
	 * @param password  :password:密码
	 * @since JDK 1.7
	 */
	public void updateBatchPassword(Long[] userIds, String password);

	/**
	 * updateById: 修改用户状态 <br/>
	 * 
	 * @author caijin
	 * @param userId
	 *            ：用户id
	 * @param userStatus
	 *            ::用户状态
	 * @since JDK 1.7
	 */
	public void updateById(Long userId, int userStatus);

	/**
	 * 
	 * findUnJoinUserByCollectionId: 查询没有当前资源授权的用户 <br/>
	 * 
	 * @author liurui
	 * @param page
	 * @param safeResourceCollectionId
	 * @param loginName
	 * @param name
	 * @param agenciesIds
	 * @return
	 * @since JDK 1.7
	 */
	public PagedInfo<User> findUnJoinUserByCollectionId(Page page, Long safeResourceCollectionId, String loginName,
			String name, List<Long> agenciesIds);

	/**
	 * 
	 * findJoinUserByCollectionId: 查询被授予了当前资源授权的用户 <br/>
	 * 
	 * @author liurui
	 * @param page
	 * @param safeResourceCollectionId
	 * @param loginName
	 * @param name
	 * @param agenciesIds
	 * @return
	 * @since JDK 1.7
	 */
	public PagedInfo<User> findJoinUserByCollectionId(Page page, Long safeResourceCollectionId, String loginName,
			String name, List<Long> agenciesIds);

	/**
	 * 统计用户数量
	 * 
	 * @return
	 */
	public Long countAll();

	/* 用户状态为注册 */
	public PagedInfo<User> findListPageByUserStatus(Page page, Byte userRoleType, List<Long> agenciesIds);

	/**
	 * 感兴趣的人
	 * 
	 * @param page
	 * @param userIds
	 * @param userRoleType
	 * @param name
	 * @param agenciesId
	 * @return
	 */
	public PagedInfo<User> findInterestedFriends(Page page, List<Long> userIds, Byte userRoleType, String name,
			Long agenciesId);

	/**
	 * @param loginName
	 * @param name
	 * @param userRoleType
	 * @param agenciesIds
	 * @return
	 */
	public List<User> findList(String loginName, String name, List<Byte> userRoleType, List<Long> agenciesIds);

	/**
	 * @param loginName
	 * @param name
	 * @param userRoleType
	 * @param agenciesIds
	 * @return
	 */
	public List<Long> findUserIdList(String loginName, String name, List<Byte> userRoleType, List<Long> agenciesIds);
}
