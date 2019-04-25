package org.csr.common.user.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.csr.common.flow.domain.UserTaskInstance;
import org.csr.common.flow.service.TaskInstanceService;
import org.csr.common.flow.support.BusinessStorage;
import org.csr.common.user.constant.UserRoleType;
import org.csr.common.user.dao.UserDao;
import org.csr.common.user.dao.UserMoreDao;
import org.csr.common.user.domain.Agencies;
import org.csr.common.user.domain.AgenciesUser;
import org.csr.common.user.domain.Role;
import org.csr.common.user.domain.User;
import org.csr.common.user.domain.UserRole;
import org.csr.common.user.facade.UserInitData;
import org.csr.common.user.service.AgenciesService;
import org.csr.common.user.service.AgenciesUserService;
import org.csr.common.user.service.OrganizationService;
import org.csr.common.user.service.RoleService;
import org.csr.common.user.service.SafeResourceCollectionService;
import org.csr.common.user.service.UserApprovalService;
import org.csr.common.user.service.UserRoleService;
import org.csr.common.user.service.UserSafeResourceCollectionService;
import org.csr.common.user.service.UserService;
import org.csr.core.Param;
import org.csr.core.exception.Exceptions;
import org.csr.core.license.Arg;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.business.domain.Organization;
import org.csr.core.persistence.business.domain.UserStatus;
import org.csr.core.persistence.business.service.OrganizationParameterService;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.param.AndInParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.userdetails.UserSessionBasics;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.DateUtil;
import org.csr.core.util.Md5PwdEncoder;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.PropertiesUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:UserServiceImpl.java <br/>
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
@Service("userService")
public class UserServiceImpl extends SimpleBasisService<User, Long> implements UserService {
	@Resource
	private UserDao userDao;
	@Resource
	private UserMoreDao userMoreDao;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private AgenciesUserService agenciesUserService;
	@Resource
	private AgenciesService agenciesService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private OrganizationParameterService organizationParameterService;
	@Resource
	private SafeResourceCollectionService safeResourceCollectionService;
	@Resource
	private UserSafeResourceCollectionService userSafeResourceCollectionService;
	@Resource
	protected TaskInstanceService taskInstanceService;
	@Resource
	protected UserApprovalService userApprovalService;
	@Resource
	private RoleService roleService;
	

	@Override
	public BaseDao<User, Long> getDao() {
		return userDao;
	}

	/**
	 * 获取安全用户<br>
	 * @see org.csr.core.security.authority.AuthenticationService#findByLoginName(java.lang.String)
	 */
	@Override
	public User findByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}

	/**
	 * 获取安全用户<br>
	 * @see org.csr.core.security.authority.AuthenticationService#findByLoginName(java.lang.String)
	 */
	@Override
	public List<User> findByCallName(String callName) {
		if(ObjUtil.isBlank(callName)) {
			return new ArrayList<User>(0);
		}else {
			
			return userDao.findByParam(new AndEqParam("callName", callName));
		}
		
		
	}
	
	@Override
	public PagedInfo<User> findListPage(Page page,String loginName,String name,Long id,List<Byte> userRoleType, List<Long> agenciesIds) {
		PagedInfo<User> result = userDao.findListPage(page, loginName, name, id, userRoleType, agenciesIds);
		return result;
	}
	@Override
	public PagedInfo<User> findListPageByUserStatus(Page page,Byte userRoleType, List<Long> agenciesIds) {
		PagedInfo<User> result = userDao.findListPageByUserStatus(page, userRoleType, agenciesIds);
		return result;
	}


	@Override
	public PagedInfo<User> findUnJoinUserByRoleId(Page page, Long roleId,
			String loginName, String name,List<Long> agenciesIds) {
		return userDao.findUnJoinUserByRoleId(page, roleId, loginName, name,agenciesIds);
	}

	@Override
	public PagedInfo<User> findJoinUserByRoleId(Page page, Long roleId,
			String loginName, String name,List<Long> agenciesIds) {
		return userDao.findJoinUserByRoleId(page, roleId, loginName, name,agenciesIds);
	}

	/**
	 * 创建一个新的用户，并保持新用户的userInfo信息。如果userInfo为null，则创建一条新的只有id的值
	 * 
	 * @see org.csr.core.user.service.UserService#saveUser(org.csr.common.user.domain.User,
	 *      org.csr.common.user.domain.UserInfo, java.lang.Long[])
	 */
	@Override
	public synchronized boolean saveUser(User user, Long[] roleIds) {
 		if(Arg.USERNUM<=userDao.countAll()){
			Exceptions.service("", "用户数已达系统上限！");
		}
		if(ObjUtil.isBlank(user.getLoginName())){
			Exceptions.service("", "用户登录名不能为空！");
		}
		if(user.getLoginName().trim().length()<6){
			Exceptions.service("", "用户登录名长度不能小于6");
		}
		if (checkLoginName(user.getLoginName())) {
			Exceptions.service("UserNameHasExist", "用户名称已存在");
		}
		if (ObjUtil.isEmpty(user.getUserStatus())) {
			Exceptions.service("UserStatus", "用户状态不能为空");
		}
		if (ObjUtil.isEmpty(user.getAgencies()) || ObjUtil.isEmpty(user.getAgencies().getId())) {
			Exceptions.service("UserNameHasExist", "用户机构不能为空");
		}
		Agencies agencies = agenciesService.findById(user.getAgencies().getId());
		if(ObjUtil.isEmpty(agencies)){
			Exceptions.service("UserAgenciesHasExist", "所选择机构不存在");
		}
		user.setPrimaryOrgId(agencies.getOrg().getId());
		// 暂时不需要，root
		// user.setRoot(organizationService.findRoot(user.getRoot()).getId());
		if (ObjUtil.isNotBlank(user.getPassword())) {
			user.setPassword(Md5PwdEncoder.encodePassword(user.getPassword()));
		} else {
			user.setPassword(Md5PwdEncoder.encodePassword(organizationParameterService.findParameterValue(user.getPrimaryOrgId(),"default_password")));
		}
		userDao.save(user);
		
		if (roleIds != null && roleIds.length > 0) {
			UserRole userRole;
			for (Long roleId : roleIds) {
				userRole = new UserRole();
				userRole.setRole(new Role(roleId));
				userRole.setUser(user);
				userRoleService.save(userRole);
			}
		}
		AgenciesUser agenciesUser = new AgenciesUser();
		agenciesUser.setAgenciesId(agencies.getId());
		agenciesUser.setCode(agencies.getCode());
		agenciesUser.setUser(user);
		//保存到关系表中
		agenciesUserService.save(agenciesUser);
		return true;
	}

	/**
	 * 修改用户信息,包含修改用户详细信息userInfo，当userInfo为null时，只修改 user信息<br/>
	 * 
	 * @see org.csr.core.user.service.UserService#updateUser(org.csr.common.user.domain.User,
	 *      org.csr.common.user.domain.UserInfo)
	 */
	@Override
	public void updateUser(User user) {
		
		// 保存用户
		User userOld = findById(user.getId());
		
		// if (ObjUtil.isNotBlank(user.getPassword())) {
		// userOld.setPassword(Md5PwdEncoder.encodePassword(user.getPassword()));
		// }
		// 不能修改用户的机构
		// userOld.setOrganizationId(user.getOrganizationId());
		// userOld.setRoot(organizationService.findRoot(user.getOrganizationId()).getId());
		// 也不能修改登录名
		// olduser.setLoginName(user.getLoginName());
		User olduser = userDao.findById(user.getId());
		// 修改用户的管理员
		if(ObjUtil.isNotBlank(user.getLoginName())){
			if (checkUpdateLoginName(olduser.getId(),user.getLoginName())) {
				Exceptions.service("UserNameHasExist", "用户名称已存在");
			}
			olduser.setLoginName(user.getLoginName());
		}
		olduser.setManagerUser(user.getManagerUser());
		olduser.setName(user.getName());
		olduser.setCallName(user.getCallName());
		olduser.setGender(user.getGender());
		olduser.setEmail(user.getEmail());
		olduser.setMobile(user.getMobile());
		
		//如果没有选择机构，只修改用户其他的信息
		if(ObjUtil.isNotEmpty(user.getAgencies())) {
			//删除 原有的机构
			agenciesUserService.deleteByUserIdAgenciesId(olduser.getId(),olduser.getAgencies().getId());
			Agencies ag = agenciesService.findById(user.getAgencies().getId());
			//TODO 2016.7.28修改以下两行代码
			if(ObjUtil.isEmpty(ag)){
				Exceptions.service("","您的注册的机构不存在！");
			}
			olduser.setAgencies(user.getAgencies());
			olduser.setPrimaryOrgId(ag.getOrg().getId());
			
			AgenciesUser agenciesUser = new AgenciesUser();
			agenciesUser.setAgenciesId(ag.getId());
			agenciesUser.setCode(ag.getCode());
			agenciesUser.setUser(user);
			//保存到关系表中
			agenciesUserService.save(agenciesUser);
		}
		olduser.setManagerUser(user.getManagerUser());
		// 保存用户详细
		userDao.update(userOld);
	}

	/**
	 * 修改用户信息
	 * 
	 * @see org.csr.core.user.service.UserService#updateUser(org.csr.common.user.domain.User,
	 *      org.csr.common.user.domain.UserInfo, java.lang.Long[])
	 */
	@Override
	public  void updateUser(User user, Long[] roleIds) {
		updateUser(user);
		// 保存用户角色
		List<UserRole> userRoleList = userRoleService.findUserRoleList(user.getId());
		boolean flag;
		// 校验是否有删除的角色
		for (UserRole ur : userRoleList) {
			if (ObjUtil.isNotEmpty(roleIds)) {
				flag = true;
				for (Long roleId : roleIds) {
					if (ur.getRole().getId().equals(roleId)) {
						flag = false;
						break;
					}
				}
				if (flag) {
					userRoleService.deleteSimple(ur.getId());
				}
			}else{
				userRoleService.deleteSimple(ur.getId());
			}
		}
		UserRole userRole;
		if (roleIds != null) {
			for (Long roleId : roleIds) {
				userRole = new UserRole();
				userRole.setRole(new Role(roleId));
				userRole.setUser(user);
				userRoleService.save(userRole);
			}
		}
	}

	/**
	 * 修改用户状态
	 * @see org.csr.core.user.service.UserService#updateById(java.lang.Long, int)
	 */
	@Override
	public void updateById(Long userId, int userStatus) {
		userDao.updateById(userId, userStatus);
	}

	/**
	 * 修改用户密码简单描述该方法的实现功能（可选）.
	 * 
	 * @see org.csr.core.user.service.UserService#updatePassword(java.lang.Long,
	 *      java.lang.String)
	 */
	@Override
	public void updatePassword(Long userId, String oldPassword,
			String newPassword) {
		User user = findById(userId);
		if (ObjUtil.isEmpty(user)) {
			Exceptions.service("", "要修改的用户不存在");
		}
		if (ObjUtil.isEmpty(oldPassword)) {
			Exceptions.service("", "必须提供旧密码");
		}

		if (!user.getPassword().equalsIgnoreCase(
				Md5PwdEncoder.encodePassword(oldPassword))) {
			Exceptions.service("oldPasswordError",PropertiesUtil.getExceptionMsg("oldPasswordError"));
		}

		// TODO ：需要用正则方式判断密码的字符串的规则，
		if (ObjUtil.isBlank(newPassword) || newPassword.length() < 6) {
			Exceptions.service("", "新密码长度必须超过6字符");
		}
		user.setPassword(Md5PwdEncoder.encodePassword(newPassword));
		user.setResetPassword(null);
		userDao.update(user);
	}

	@Override
	public void updateSetNewPassword(Long userId, String newPassword) {
		User user = findById(userId);
		if (ObjUtil.isEmpty(user)) {
			Exceptions.service("", "要修改的用户不存在");
		}
		// TODO ：需要用正则方式判断密码的字符串的规则，
		if (ObjUtil.isBlank(newPassword) || newPassword.length() < 6) {
			Exceptions.service("", "新密码长度必须超过6字符");
		}
		user.setPassword(Md5PwdEncoder.encodePassword(newPassword));
		user.setResetPassword(null);
		userDao.update(user);

	}

	@Override
	public void updateSetResetPassword(Long id, String uuid) {
		User user = findById(id);
		if (ObjUtil.isEmpty(user)) {
			Exceptions.service("", "要设置的用户不存在");
		}
		// TODO ：需要用正则方式判断密码的字符串的规则，
		if (ObjUtil.isNotBlank(uuid)) {
			user.setResetPassword(uuid);
			userDao.update(user);
		}
	}

	/**
	 * 修改用户
	 * 
	 * @see org.csr.core.user.service.UserService#updateStyle(org.csr.common.user.domain.User,
	 *      org.csr.core.security.userdetails.UserSession,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void updateStyle(Long userId, String skinName) {
		User user = userDao.findById(userId);
		if (ObjUtil.isEmpty(user)) {
			Exceptions.service("oldPasswordError",PropertiesUtil.getExceptionMsg("oldPasswordError"));
		}
		user.setSkinName(skinName);
		userDao.update(user);
	}

	@Override
	public void updateUserEmail(Long userId, String email) {
		User user = userDao.findById(userId);
		if (ObjUtil.isEmpty(user)) {
			Exceptions.service("", "没有找到要更改用户");
		}
		user.setEmail(email);
		userDao.update(user);

	}

	/**
	 * 重置密码
	 * 
	 * @see org.csr.core.user.service.UserService#updateResetPassword(java.lang.Long[],
	 *      java.lang.Long)
	 */
	public void updateResetDefaultPassword(Long[] userIds, Long root) {
		userDao.updateBatchPassword(userIds,
				Md5PwdEncoder.encodePassword(organizationParameterService.findParameterValue(root, "defaultPassword")));
	}

	/**
	 * 查询组织机构管理员
	 * 
	 * @see org.csr.core.user.service.UserService#findByOrganizationAdmin(java.lang.Long)
	 */
	@Override
	public User findByOrganizationAdmin(Long organizationId) {
		return userDao.findByOrganizationAdmin(organizationId);
	}

	/**
	 * 删除用户，并且删除用户消息表（UserInfo），删除用户角色表（UserRole） <br/>
	 * 
	 * @see org.csr.core.user.service.UserService#delete(java.lang.Long)
	 */
	public void delete(Long id) {
		
		User user = userDao.findById(id);
		
		if (ObjUtil.isEmpty(user)) {
			Exceptions.service("", "要删除用户不存在");
		}
		if (UserRoleType.SUPER.equals(user.getUserRoleType())) {
			Exceptions.service("", "当前用户为超级用户");
		}
		List<Organization> orgs = organizationService.findAdminUserByUserId(user.getId());
		if (ObjUtil.isNotEmpty(orgs)) {
			Exceptions.service("", "当前用户为域管理员");
		}
		Long manager = userDao.countParam(new AndEqParam("managerUser.id", user.getId()),new AndEqParam("isDelete", false));
		if(manager>0){
			Exceptions.service("", "用户["+user.getLoginName()+"]已经是管理员，不能删除");
		}
		
		if(ObjUtil.isNotEmpty(org.csr.common.user.facade.impl.UserFacadeImpl.userInitDatas)){
			for (UserInitData userInitData : org.csr.common.user.facade.impl.UserFacadeImpl.userInitDatas) {
				userInitData.delInitData(id);
			}
		}
		//删除详情
		userMoreDao.deleteById(id);
		
		userDao.deleteById(id);
		
		Long agenciesId = null;
		if(ObjUtil.isNotEmpty(user.getAgencies())){
			agenciesId=user.getAgencies().getId();
		}
		
		//删除到关系表中
		if(ObjUtil.isNotEmpty(agenciesId) && ObjUtil.isNotEmpty(user.getId())){
			agenciesUserService.deleteByUserId(user.getId());
		}
	}

	/**
	 * 根据用户id删除多个用户
	 * @see org.csr.core.user.service.UserService#deleteUser(java.lang.Long[])
	 */
	public void deleteUser(Long[] userIds) {
		for (Long id : userIds) {
			delete(id);
		}
	}

	/**
	 * 根据用户id删除多个用户
	 * 
	 * @see org.csr.core.user.service.UserService#deleteUser(java.lang.Long[])
	 */
	public void deleteUpUser(Long[] userIds) {
		List<User> users=userDao.findByIds(userIds);
		if(ObjUtil.isEmpty(users)){
			Exceptions.service("", "您要删除的用户不存在");
		}
		for (User user : users) {
			Long manager = userDao.countParam(new AndEqParam("managerUser.id", user.getId()),new AndEqParam("isDelete", false));
			if(manager>0){
				Exceptions.service("", "用户["+user.getLoginName()+"]已经是管理员，不能删除");
			}
			user.setIsDelete(true);
			userDao.update(user);
		}
	}

	/**
	 * 用于验证添加用户名是否唯一
	 * 
	 * @see org.csr.core.user.service.UserService#checkLoginName(java.lang.String)
	 */
	@Override
	public boolean checkLoginName(String loginName) {
		User user = existUserLoginName(loginName);
		if (ObjUtil.isNotEmpty(user)) {
			return true;
		}
		return false;
	}

	/**
	 * 用于验证添加用户名是否唯一
	 * 
	 * @see org.csr.core.user.service.UserService#checkUpdateLoginName(java.lang.Long,
	 *      java.lang.String)
	 */
	public boolean checkUpdateLoginName(Long id, String loginName) {
		User user = existUserLoginName(loginName);
		if(ObjUtil.isNotEmpty(id)){
			if (ObjUtil.isEmpty(user) || user.getId().equals(id)) {
				return false;
			}
		}
		if (ObjUtil.isEmpty(user)) {
			return false;
		}
		return true;
	}

	/**
	 * 查询是否存在name
	 * 
	 * @param name
	 * @return
	 */
	private User existUserLoginName(String loginName) {
		Param param = new AndEqParam("loginName", loginName);
		return userDao.existParam(param);
	}

	@Override
	public User findByResetPassword(String resetPassword) {
		Param param = new AndEqParam("resetPassword", resetPassword);
		User user = userDao.existParam(param);
		if (ObjUtil.isNotEmpty(user)) {
			return user;
		}
		return null;
	}


	@Override
	public List<User> findUsersLevelList(Long managerUserId,
			Integer level, String loginName, String name, Long id,
			Byte userRoleType, List<Long> agenciesIds) {
		if (ObjUtil.isEmpty(level)) {
			level = 0;
		}
		return findLevelChild(managerUserId, level, 1, loginName, name, id,userRoleType,agenciesIds);
	}
	
	
	
	public List<User> findChildUserByManagerUserId(Long managerUserId,String loginName,String name,Long id,Byte userRoleType,List<Long> agenciesIds){
		return userDao.findChildUserByManagerUserId(managerUserId, loginName, name, id, userRoleType,agenciesIds);
	}

	
	
	public Long findCountUserByAgenciesIds(Long[] agenciesIds){
		if(ObjUtil.isEmpty(agenciesIds)){
			return 0l;
		}
		return userDao.countParam(new AndEqParam("userStatus",UserStatus.NORMAL),new AndEqParam("isDelete", false),new AndInParam("agencies.id",Arrays.asList(agenciesIds)));
	}
	

	@Override
	public PagedInfo<User> findUnJoinUserByCollectionId(Page page, Long safeResourceCollectionId,
			String loginName, String name, List<Long> agenciesIds) {
		return userDao.findUnJoinUserByCollectionId(page,safeResourceCollectionId,loginName,name,agenciesIds);
	}

	@Override
	public PagedInfo<User> findJoinUserByCollectionId(Page page, Long safeResourceCollectionId,
			String loginName, String name, List<Long> agenciesIds) {
		return userDao.findJoinUserByCollectionId(page,safeResourceCollectionId,loginName,name,agenciesIds);
	}
	@Override
	public void updateUserPreferences(User user) {
//		if(ObjUtil.longIsNull(user.getDufHome())){
//			Exceptions.service("", "您必须选择首选项");
//		}
		if(ObjUtil.isBlank(user.getSkinName())){
			Exceptions.service("", "您必须选择皮肤");
		}
		User oldUser = userDao.findById(user.getId());
		oldUser.setDufHome(user.getDufHome());
		oldUser.setSkinName(user.getSkinName());
		userDao.update(oldUser);
		//设置用户
		if(UserSessionContext.getUserSession().getUserId().equals(oldUser.getId())){
			UserSessionBasics usersession=(UserSessionBasics) UserSessionContext.getUserSession();
			usersession.setSkinName(user.getSkinName());
		}
	}
	/**
	 * 修改用户信息
	 * 
	 * @see org.csr.core.user.service.UserService#updateUser(org.csr.common.user.domain.User,
	 *      org.csr.common.user.domain.UserInfo, java.lang.Long[])
	 */
	@Override
	public  void updateUserTask(Long userId,User user,Long roleId) {
		User command=userDao.findById(userId);
		if(ObjUtil.isEmpty(command)){
			Exceptions.service("", "您要修改的用户不存在流程中");
		}
		if(ObjUtil.isEmpty(roleId)){
			Exceptions.service("", "您必须选择角色");
		}
		Role role = roleService.findById(roleId);
		if(ObjUtil.isEmpty(role)){
			Exceptions.service("", "您必须选择角色不存在");
		}
		if(ObjUtil.isBlank(user.getLoginName())){
			Exceptions.service("", "登录名不能为空");
		}
		if(ObjUtil.isBlank(user.getName())){
			Exceptions.service("", "姓名不能为空");
		}
		
		UserTaskInstance userTask=command.getUserTaskInstance();
		// 保存用户
		if(checkUpdateLoginName(command.getId(), user.getLoginName())){
			Exceptions.service("", "您要修改的用户不存在流程中");
		}
		
		command.setName(user.getName());
		command.setLoginName(user.getLoginName());
		command.setGender(user.getGender());
		command.setManagerUser(user.getManagerUser());
		command.setEmail(user.getEmail());
		userDao.update(command);
		// 保存用户角色
		userRoleService.deleteByUserId(command.getId());
		UserRole userRole;
		userRole = new UserRole();
		userRole.setRole(role);
		userRole.setUser(command);
		userRoleService.save(userRole);
		
		//并提交流程
		if(ObjUtil.isNotEmpty(userTask)){
			taskInstanceService.updateTaskPass(userTask.getId(),command, "修改用户",new BusinessStorage() {
				@Override
				public boolean save(UserTaskInstance userTaskInstance) {
					return userApprovalService.saveForm(userTaskInstance);
				}
			});
		}
	}
	
	private List<User> findLevelChild(Long managerUserId, Integer toLevel,
			Integer level, String loginName, String name, Long id,
			Byte userRoleType,List<Long> agenciesIds) {
		List<User> userList = new ArrayList<User>();
		if (toLevel > 0 && toLevel < level) {
			return userList;
		}
		List<User> childList = userDao.findChildUserByManagerUserId(managerUserId, loginName, name, id, userRoleType,agenciesIds);
		for (int i = 0; i < childList.size(); i++) {
			User user = childList.get(i);
			user.setLevel(level);
			userList.add(user);
			if (user.getChildCount() > 0) {
				userList.addAll(findLevelChild(user.getId(), toLevel,level + 1, loginName, name, id, userRoleType,agenciesIds));
			}
		}
		return userList;
	}
	
	

	@Override
	public PagedInfo<User> findByImportFileId(Page page,Long importFileId) {
		if(ObjUtil.isEmpty(importFileId)){
			return PersistableUtil.createPagedInfo(0, page, new ArrayList<User>(0));
		}
		page.toParam().add(new AndEqParam("fileId", importFileId));
		page.toParam().add(new AndEqParam("userStatus", UserStatus.NORMAL));
		return userDao.findAllPage(page);
	}

	@Override
	public List<User> findList(String loginName, String name,List<Byte> userRoleType, List<Long> agenciesIds) {
		return userDao.findList(loginName, name, userRoleType,agenciesIds);
	}

	@Override
	public List<Long> findUserIdList(String loginName, String name, List<Byte> userRoleType, List<Long> agenciesIds) {
		return userDao.findUserIdList(loginName, name, userRoleType,agenciesIds);
	}

	@Override
	public User checkNameAndAgenciesId(String name,Long agenciesId) {
		return userDao.existParam(new AndEqParam("name",name),new AndEqParam("isDelete", false),new AndEqParam("agencies.id",agenciesId));
	}

	@Override
	public boolean checkCallNameAndAgenciesId(String callName, Long agenciesId) {
		User existParam = userDao.existParam(new AndEqParam("name", callName), new AndEqParam("isDelete", false),new AndEqParam("agencies.id", agenciesId));
		if (ObjUtil.isNotEmpty(existParam)) {
			Date parseDate = DateUtil.parseDate(new Date(), "yyyy-MM-dd 00:00:00");
			Date createTime = existParam.getCreateTime();
			if(ObjUtil.isNotEmpty(createTime)) {
				return parseDate.before(createTime);
			}else {
				return false;
			}
		} else {
			return false;
		}
	}


	public static void main(String[] args) {
		Date parseDate = DateUtil.parseDate(new Date(), "yyyy-MM-dd 00:00:00");
		System.out.println(parseDate.before(new Date()));;
	}
}
