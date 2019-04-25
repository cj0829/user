package org.csr.common.user.service;

import java.util.List;

import org.csr.common.user.domain.User;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.service.BasisService;

/**
 * ClassName:BasisServiceImpl <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface UserService extends BasisService<User, Long>{

	/**
	 * findByOrganizationAdmin: 查询组织机构管理员 <br/>
	 * @author caijin
	 * @param organizationId
	 * @return
	 * @since JDK 1.7
	 */
	public User findByOrganizationAdmin(Long organizationId);

	/**
	 * findUnJoinUserByRoleId: 查询角色下没有的用户 <br/>
	 * @author wangxiujuan
	 * @param page :分页对象
	 * @param roleId：角色id
	 * @return
	 * @since JDK 1.7
	 */
	public PagedInfo<User> findUnJoinUserByRoleId(Page page, Long roleId,String loginName,String name,List<Long> agenciesIds);

	/**
	 * findJoinUserByRoleId: 查询角色下的用户 <br/>
	 * @author caijin
	 * @param page
	 * @param roleId
	 * @return
	 * @since JDK 1.7
	 */
	public PagedInfo<User> findJoinUserByRoleId(Page page,Long roleId,String loginName,String name,List<Long> agenciesIds);
	/**
	 * saveUser: 创建一个新的用户，并保持新用户的userInfo信息。如果userInfo为null，则创建一条新的只有id的值 <br/>
	 * 验证用户名称的唯一性<br>
	 * 如果，roleIds为null，则用户没有角色
	 * @author caijin
	 * @param user  用户对象
	 * @param userType 
	 * @param userInfo  用户信息对象
	 * @param roleIds 角色id数组
	 * @return
	 * @since JDK 1.7
	 */
	public  boolean saveUser(User user, Long[] roleIds);

	/**
	 * updateUser: 修改用户信息,包含修改用户详细信息userInfo，当userInfo为null时，只修改 user信息<br/>
	 * @author caijin
	 * @param user
	 * @param userInfo
	 * @since JDK 1.7
	 */
	public void updateUser(User user);

	/**
	 * 修改用email
	 * @author caijin
	 * @param user
	 * @param userInfo
	 * @since JDK 1.7
	 */
	public void updateUserEmail(Long userId,String email);

	
	/**
	 * updateUser: 修改用户信息,包含修改用户详细信息userInfo，当userInfo为null时，只修改 user信息<br/>
	 * 不修改登录名<br>
	 * 如果，roleIds为null，册删除，全部用户角色<br>
	 * @author caijin
	 * @param user 用户表对象
	 * @param userInfo 用户信息对象
	 * @param roleIds 角色id数组
	 * @since JDK 1.7
	 */
	public void updateUser(User user, Long[] roleIds);

	/**
	 * updateById: 修改用户状态 <br/>
	 * @author caijin
	 * @param userId 用户ID 
	 * @param userStatus 用户状态
	 * @since JDK 1.7
	 */
	public void updateById(Long userId, int userStatus);
	
	/**
	 * updatePassword: 修改用户密码 <br/>
	 * @author caijin
	 * @param userId
	 * @param password
	 * @since JDK 1.7
	 */
	public void updatePassword(Long userId, String oldPassword,String newPassword);

	/**
	 * updatePassword: 直接设置新密码<br/>
	 * 用于，忘记密码修改
	 * @author caijin
	 * @param userId
	 * @param password
	 * @since JDK 1.7
	 */
	public void updateSetNewPassword(Long userId,String newPassword);

	/**
	 * updateStyle: 修改用户 <br/>
	 * @author caijin
	 * @param user 用户对象
	 * @param us 用户登录信息对象
	 * @param request 对象
	 * @since JDK 1.7
	 */
	public void updateStyle(Long userId,String skinName);

	/**
	 * updateResetPassword: 批量重置密码 <br/>
	 * @author caijin
	 * @param userIds 用户id数组
	 * @param root:组织机构
	 * @since JDK 1.7
	 */
	public void updateResetDefaultPassword(Long[] userIds, Long root);

	/**
	 * delete: 删除用户，并且删除用户消息表（UserInfo），删除用户角色表（UserRole） <br/>
	 * @author caijin
	 * @param id
	 * @since JDK 1.7
	 */
	public void delete(Long id);

	/**
	 * deleteUser: 根据用户id删除多个用户 <br/>
	 * @author caijin
	 * @param userIds:用户id数组
	 * @since JDK 1.7
	 */
	public void deleteUser(Long[] userIds);

	/**
	 * checkUpdateLoginName: 用于验证修改用户是登录名是否唯一 <br/>
	 * @author caijin
	 * @param id
	 * @param loginName
	 * @return
	 * @since JDK 1.7
	 */
	public boolean checkUpdateLoginName(Long id, String loginName);

	/**
	 * updateSetResetPassword: 设置用户的重置密码的uuid <br/>
	 * @author caijin
	 * @param id
	 * @param uuid
	 * @since JDK 1.7
	 */
	public void updateSetResetPassword(Long id, String uuid);


	/**
	 * findByResetPassword: 根据需要设置的 resetPassword来查询用户，<br/>
	 * @author caijin
	 * @param resetPassword
	 * @return
	 * @since JDK 1.7
	 */
	public User findByResetPassword(String resetPassword);
	
	/**
	 * 需要递归查询出，用户及用户管理下的所有用户，根据管理员id<br>
	 * 及管理的级别<br>
	 * 但是无法，提供分页
	 * @author caijin
	 * @param page
	 * @param managerUserId  :管理员用户
	 * @param loginName
	 * @param name
	 * @param id
	 * @param userRoleType
	 * @return
	 * @since JDK 1.7
	 */
	public List<User> findUsersLevelList(Long managerUserId, Integer level, String loginName, String name,
			Long id, Byte userRoleType,List<Long> agenciesIds);


	/**
	 * 根据管理员，查询子用户
	 * @author caijin
	 * @param managerUserId
	 * @param loginName
	 * @param name
	 * @param id
	 * @param userRoleType
	 * @return
	 * @since JDK 1.7
	 */
	public List<User> findChildUserByManagerUserId(Long managerUserId,String loginName,String name,Long id,Byte userRoleType,List<Long> agenciesIds);

	/**
	 * findCountUserByAgenciesIds:根据机构Id查询统计引用了机构的用户数量
	 * @author huayj
	 * @param agenciesIds
	 * @return
	 * @return Long
	 * @date&time 2015-9-18 下午4:56:25
	 * @since JDK 1.7
	 */
	public Long findCountUserByAgenciesIds(Long[] agenciesIds);

	/**
	 * 
	 * findUnJoinUserByCollectionId: 查询没有当前资源授权的用户 <br/>
	 * @author liurui
	 * @param page
	 * @param safeResourceCollectionId
	 * @param loginName
	 * @param name
	 * @param agenciesIds
	 * @return
	 * @since JDK 1.7
	 */
	public PagedInfo<User> findUnJoinUserByCollectionId(Page page, Long safeResourceCollectionId,
			String loginName, String name, List<Long> agenciesIds);


	/**
	 * 
	 * findJoinUserByCollectionId: 查询被授予了资源授权的用户 <br/>
	 * @author liurui
	 * @param page
	 * @param safeResourceCollectionId
	 * @param loginName
	 * @param name
	 * @param agenciesIds
	 * @return
	 * @since JDK 1.7
	 */
	public PagedInfo<User> findJoinUserByCollectionId(Page page, Long safeResourceCollectionId,
			String loginName, String name, List<Long> agenciesIds);


	/**
	 * updateUserPreferences:
	 * @author huayj
	 * @param user
	 * @return void
	 * @date&time 2016-1-6 上午10:15:01
	 * @since JDK 1.7
	 */
	public void updateUserPreferences(User user);

	/**
	 * 修改用户，并提交流程，提供驳回后的修改，如果流程暂停，直接修改。
	 * @author caijin
	 * @param user
	 * @since JDK 1.7
	 */
	public void updateUserTask(Long userCommandId, User user, Long roleId);

	/**
	 * 根据文件id 查询出已经审批过的用户。
	 * @author caijin
	 * @param importFileId
	 * @return
	 * @since JDK 1.7
	 */
	public PagedInfo<User> findByImportFileId(Page page, Long importFileId);
	
	public PagedInfo<User> findListPage(Page page,String loginName,String name,Long id,List<Byte> userRoleType, List<Long> agenciesIds);
	
	public PagedInfo<User> findListPageByUserStatus(Page page,Byte userRoleType, List<Long> agenciesIds);
	/**
	 * @param loginName
	 * @return
	 */
	User findByLoginName(String loginName);

	/**
	 * 查询到全部的用户
	 * @param loginName
	 * @param name
	 * @param userId
	 * @param userRoleType
	 * @param agenciesIds
	 * @return
	 */
	public List<User> findList(String loginName, String name,List<Byte> userRoleType, List<Long> agenciesIds);
	


	/**
	 * 查询到全部的用户
	 * @param loginName
	 * @param name
	 * @param userId
	 * @param userRoleType
	 * @param agenciesIds
	 * @return
	 */
	public List<Long> findUserIdList(String loginName, String name,List<Byte> userRoleType, List<Long> agenciesIds);

	boolean checkLoginName(String loginName);
	
	User checkNameAndAgenciesId(String name,Long agenciesId);

	/**
	 * 根据查询用户
	 * @param callName
	 * @return
	 */
	List<User> findByCallName(String callName);

	/**
	 * 检查用户的call 是否重复,有当前有效
	 * @param decByDESede
	 * @param agenciesId
	 */
	public boolean checkCallNameAndAgenciesId(String decByDESede, Long agenciesId);
	

}
