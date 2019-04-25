package org.csr.common.user.facade;

import java.util.List;
import java.util.Map;

import org.csr.common.storage.entity.DatastreamBean;
import org.csr.common.user.domain.User;
import org.csr.common.user.entity.UserBean;
import org.csr.core.UserSession;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.security.authority.AuthenticationService;
import org.csr.core.userdetails.SecurityUser;

/**
 * @author caijin
 *
 */
public interface UserFacade extends AuthenticationService {

	public UserBean findById(Long userId);
	
	public List<UserBean> findByIds(List<Long> userIds);

	public Map<Long, UserBean> findMapByIds(List<Long> userIds);

	/**
	 * findByLoginName: 获取安全用户<br>
	 * 
	 * @author caijin
	 * @param loginName
	 * @return
	 * @since JDK 1.7
	 */
	SecurityUser findByLoginName(String loginName);
	
	public Object findNameById(Long userId, String string);
	
	/**
	 * 按照登录名称，或者用户名，或者用户角色查询。<br>
	 * 机构，可以根据用户自身的机构，或者用户能够查询到的机构查询下面的用户<br>
	 * 
	 * @param page
	 * @param loginName
	 * @param name
	 * @param userId
	 * @param userRoleType
	 * @param agenciesIds
	 * @return
	 */
	public PagedInfo<UserBean> findListPage(Page page,String loginName, String name, Long userId,List<Byte> userRoleType,List<Long> agenciesIds);
	
	/**
	 * 编辑时，检测登录名是否存在
	 * @param userId
	 * @param loginName
	 * @return
	 */
	public boolean checkUpdateLoginName(Long userId, String loginName);
	
	/**
	 * 查询
	 * @param page
	 * @param importFileId
	 * @return
	 */
	PagedInfo<UserBean> findByImportFileId(Page page, Long importFileId);



	public boolean saveUser(User user);

	public void updateUser(User user);

	public boolean saveUser(User user, Long[] roleIds);

	public void updateUser(User user, Long[] roleIds);

	public boolean saveUser(User user, Long[] roleIds, Long[] safeResourceIds);

	public void updateUser(User user, Long[] roleIds, Long[] safeResourceIds);

	/**
	 * 根据重置密码id 查询用户密码
	 * @param uuid
	 * @return
	 */
	public UserBean findByResetPassword(String uuid);
	/**
	 * 修改用户密码
	 * @param id
	 * @param newPassword
	 */
	public void updateSetNewPassword(Long id, String newPassword);
	
	
	public void updateSetResetPassword(Long id, String uuid);

	/**
	 * 修改用户密码，需要提供旧密码
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 */
	public void updatePassword(Long id, String oldPassword, String newPassword);


	/**
	 * @param user
	 */
	public void updateUserPreferences(User user);

	void updateUserTask(User user);
	
	/**
	 * 审批用户
	 * @param id
	 */
	public void updatePassUser(Long id);
	/**
	 * 填充UserSession对象值
	 * 
	 * @param suser
	 * @return
	 */
	UserSession setUserSession(SecurityUser suser);

	public void updateMenuStyle(String menuStyle);
	
	/**
	 * 修改用户时间
	 * @param id
	 */
	public void updateUserTime(Long id);

	public void deleteUser(Long[] ids);

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @param profile
	 */
	public UserBean update(User user, DatastreamBean profile);


	/**
	 * 注册用户
	 * 
	 * @param user
	 * @return
	 */
	boolean saveRegUser(User user);

	/**
	 * 删除注册用户
	 * @param id
	 */
	public void deleteRegisterUser(Long id);
	
	public PagedInfo<UserBean> findUnJoinUserByRoleId(Page page, Long roleId,String loginName, String name, List<Long> agenciesIds);

	public PagedInfo<UserBean> findJoinUserByRoleId(Page page, Long roleId,String loginName, String name, List<Long> agenciesIds);

	public PagedInfo<UserBean> findUnJoinUserByCollectionId(Page page,Long safeResourceCollectionId, String loginName, String name,List<Long> agenciesIds);

	public PagedInfo<UserBean> findJoinUserByCollectionId(Page page,Long safeResourceCollectionId, String loginName, String name,List<Long> agenciesIds);

	/**
	 * 按照登录名称，或者用户名，或者用户角色查询。<br>
	 * 机构，可以根据用户自身的机构，或者用户能够查询到的机构查询下面的用户<br>
	 */
	public List<UserBean> findListByNameAgenciesIds(String loginName, String name,List<Byte> userRoleType,List<Long> agenciesIds);
	
	/**
	 * 按照登录名称，或者用户名，或者用户角色查询。<br>
	 * 机构，可以根据用户自身的机构，或者用户能够查询到的机构查询下面的用户<br>
	 * 查询，
	 */
	public List<Long> findUserIdListByNameAgenciesIds(String loginName, String name,List<Byte> userRoleType,List<Long> agenciesIds);


}
