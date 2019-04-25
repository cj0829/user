package org.csr.common.user.facade.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.csr.common.flow.constant.FormOperType;
import org.csr.common.flow.domain.UserTaskInstance;
import org.csr.common.flow.service.TaskInstanceService;
import org.csr.common.flow.support.BusinessStorage;
import org.csr.common.storage.entity.DatastreamBean;
import org.csr.common.user.constant.ImportApprovalStatus;
import org.csr.common.user.constant.UserRoleType;
import org.csr.common.user.dao.UserDao;
import org.csr.common.user.domain.Agencies;
import org.csr.common.user.domain.SafeResourceCollection;
import org.csr.common.user.domain.User;
import org.csr.common.user.domain.UserImportFile;
import org.csr.common.user.domain.UserRegisterStrategy;
import org.csr.common.user.domain.UserSafeResourceCollection;
import org.csr.common.user.entity.UserBean;
import org.csr.common.user.facade.UserFacade;
import org.csr.common.user.facade.UserInitData;
import org.csr.common.user.service.AgenciesService;
import org.csr.common.user.service.MenuService;
import org.csr.common.user.service.OrganizationService;
import org.csr.common.user.service.SafeResourceCollectionService;
import org.csr.common.user.service.UserApprovalService;
import org.csr.common.user.service.UserImportFileService;
import org.csr.common.user.service.UserRegisterStrategyService;
import org.csr.common.user.service.UserSafeResourceCollectionService;
import org.csr.common.user.service.UserService;
import org.csr.core.SecurityResource;
import org.csr.core.UserSession;
import org.csr.core.constant.YesorNo;
import org.csr.core.exception.Exceptions;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.business.domain.UserStatus;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.security.resource.MenuNodeBean;
import org.csr.core.userdetails.SecurityUser;
import org.csr.core.userdetails.UserSessionBasics;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.PropertiesUtil;

public class UserFacadeImpl extends SimpleBasisFacade<UserBean, User, Long> implements UserFacade {
	@Resource
	protected UserService userService;
	@Resource
	protected UserDao userDao;
	@Resource
	protected MenuService menuService;
	@Resource
	protected AgenciesService agenciesService;
	@Resource
	protected OrganizationService organizationService;
	@Resource
	protected UserRegisterStrategyService userRegisterStrategyService;	
	@Resource
	protected TaskInstanceService taskInstanceService;	
	@Resource
	protected UserApprovalService userApprovalService;
	@Resource
	protected UserSafeResourceCollectionService userSafeResourceCollectionService;
	@Resource
	protected SafeResourceCollectionService safeResourceCollectionService;
	@Resource
	protected UserImportFileService userImportFileService;
	

	/**消息类型【用于登入统计当前用户指定消息类型的数量*/
	public final static List<UserInitData> userInitDatas=new ArrayList<>();
	
	
	public void setUserinitdatas(List<UserInitData> userInitDatas) {
		System.out.println("setUserinitdatas");
		UserFacadeImpl.userInitDatas.clear();
		UserFacadeImpl.userInitDatas.addAll(userInitDatas);
	}


	@Override
	public  UserBean wrapBean(User doMain) {
		UserBean userBean = UserBean.wrapBean(doMain);
		if(null == userBean){
			return null;
		}
		return userBean;
	}


	@Override
	public PagedInfo<UserBean> findUnJoinUserByRoleId(Page page, Long roleId,String loginName, String name, List<Long> agenciesIds) {
		PagedInfo<User> userList = userService.findUnJoinUserByRoleId(page, roleId, loginName, name, agenciesIds);
		return PersistableUtil.toPagedInfoBeans(userList, new SetBean<User>() {
			@Override
			public UserBean setValue(User doMain) {
				return wrapBean(doMain);
			}
		});
	}
	@Override
	public SecurityUser findByLoginName(String loginName) {
		User user = userService.findByLoginName(loginName);
		UserBean userBean = wrapBean(user);
		if(null == userBean){
			return null;
		}
		userBean.setPassword(user.getPassword());
		return userBean;
	}

	@Override
	public UserSession setUserSession(SecurityUser suser) {
		UserBean user = (UserBean) suser;
		UserSessionBasics us = new UserSessionBasics(user.getLoginName());
		us.setUserId(user.getId());
		us.setUserName(user.getName());
		us.setSkinName(user.getSkinName());
		MenuNodeBean menu = menuService.getCacheById(user.getDufHome());
		if (ObjUtil.isNotEmpty(menu)) {
			SecurityResource fp = menu.getSecurityResource();
			if(ObjUtil.isNotEmpty(fp)){
				us.setHomeUrl(fp.getForwardUrl());
			}
		}
		//2016-05-10，目前机构没有使用到别名，其实，可以暂时不用查询别名
//		if (ObjUtil.isNotEmpty(user.getRoot())) {
//			Organization organization = organizationService.findById(user.getRoot());
//			// 设置
//			if (ObjUtil.isNotEmpty(organization)) {
//				us.setAliases(organization.getAliases());
//			}
//		}
		
//		if (ObjUtil.isNotBlank(user.getMenuStyle())) {
//			us.setMenuStyle(user.getMenuStyle());
//		} else {
//			us.setMenuStyle("left");
//		}
		// 系统设置
		us.setAgenciesId(user.getAgenciesId());
		us.setAgenciesName(user.getAgenciesName());
		us.setPrimaryOrgId(user.getPrimaryOrgId());
		us.setDufHome(user.getDufHome());
		us.setLanguagesId(user.getLanguagesId());
//		//TODO:暂时屏蔽  设置系统消息个数
//		Long messageNum = globalMessageService.findCountUserMessageNum(messageTypes,user.getId(),YesorNo.NO);
//		us.setMessageNum(ObjUtil.toInteger(messageNum));
//		//TODO 登录的时候删除缓存中的消息
//		String[] keys = new String[messageTypes.size()];
//		for(int i=0;i<messageTypes.size();i++){
//			String key=messageTypes.get(i)+"_"+user.getId();
//			keys[i]=key;
//		}
//		CacheApi cacheApi = CacheFactory.createApi(PropertiesUtil.getConfigureValue("cache.type"));
//		cacheApi.del(keys);
		us.setAvatarUrl(user.getAvatarUrl());
		us.setHeadUrl(user.getHeadUrl());
		us.setPoints1(user.getPoints1());
		us.setSaysome(user.getSaysome());
		return us;
	}

	@Override
	public PagedInfo<UserBean> findJoinUserByRoleId(Page page, Long roleId,String loginName, String name, List<Long> agenciesIds) {
		PagedInfo<User> userList = userService.findJoinUserByRoleId(page, roleId, loginName, name, agenciesIds);
		return PersistableUtil.toPagedInfoBeans(userList, new SetBean<User>() {
			@Override
			public UserBean setValue(User doMain) {
				return wrapBean(doMain);
			}
		});
	}
	
	@Override
	public PagedInfo<UserBean> findUnJoinUserByCollectionId(Page page,Long safeResourceCollectionId, String loginName, String name,List<Long> agenciesIds) {
		PagedInfo<User> userList = userService.findUnJoinUserByCollectionId(page, safeResourceCollectionId, loginName, name, agenciesIds);
		return PersistableUtil.toPagedInfoBeans(userList, new SetBean<User>() {
			@Override
			public UserBean setValue(User doMain) {
				return wrapBean(doMain);
			}
		});
	}

	@Override
	public PagedInfo<UserBean> findJoinUserByCollectionId(Page page,Long safeResourceCollectionId, String loginName, String name,List<Long> agenciesIds) {
		PagedInfo<User> userList = userService.findJoinUserByCollectionId(page, safeResourceCollectionId, loginName, name, agenciesIds);
		return PersistableUtil.toPagedInfoBeans(userList, new SetBean<User>() {
			@Override
			public UserBean setValue(User doMain) {
				return wrapBean(doMain);
			}
		});
	}
	

	@Override
	public void updateSetResetPassword(Long id, String uuid) {
		userService.updateSetResetPassword(id, uuid);
	}

	@Override
	public void updatePassword(Long id, String oldPassword, String newPassword) {
		userService.updatePassword(id, oldPassword, newPassword);
	}

	@Override
	public UserBean findByResetPassword(String uuid) {
		return wrapBean(userService.findByResetPassword(uuid));
	}

	@Override
	public void updateSetNewPassword(Long id, String newPassword) {
		userService.updateSetNewPassword(id,newPassword);
	}

	@Override
	public void updateUserTask(User user) {
		
		checkParameter(user, "请选择用户");
		checkParameter(user.getId(), "请选择用户");
		checkParameter(user.getLoginName(), "登录名不能为空");
		checkParameter(user.getName(), "姓名不能为空");
		
		final User olduser=userDao.findById(user.getId());
		if(ObjUtil.isEmpty(olduser)){
			Exceptions.service("", "您要修改的用户不存在流程中");
		}
	
		
		UserTaskInstance userTask=olduser.getUserTaskInstance();
		
		if(checkUpdateLoginName(olduser.getId(), user.getLoginName())){
			Exceptions.service("", "您要修改的用户不存在流程中");
		}
		
		olduser.setName(user.getName());
		olduser.setLoginName(user.getLoginName());
		olduser.setGender(user.getGender());
		olduser.setManagerUser(user.getManagerUser());
		olduser.setEmail(user.getEmail());
		userDao.update(olduser);
		
		//并提交流程
		if(ObjUtil.isNotEmpty(userTask)){
			taskInstanceService.updateTaskPass(userTask.getId(),user, "修改用户",new BusinessStorage() {
				@Override
				public boolean save(UserTaskInstance userTaskInstance) {
					
					if(FormOperType.IMPORT.equals(userTaskInstance.getFormOperType())){
						olduser.setUserStatus(UserStatus.NORMAL);
						olduser.setUserTaskInstance(null);
						userService.updateSimple(olduser);
						UserImportFile userImportFile = userImportFileService.findById(olduser.getFileId());
						
						//需要重新计算审核通过和未通过数
						Integer userPassTotal = userImportFile.getUserPassTotal();
						userImportFile.setUserPassTotal(userPassTotal+1);
					}
					return true;
				}
			});
		}
	}

	@Override
	public PagedInfo<UserBean> findByImportFileId(Page page, Long importFileId) {
		PagedInfo<User> userList = userService.findByImportFileId(page, importFileId);
		return PersistableUtil.toPagedInfoBeans(userList, new SetBean<User>() {
			@Override
			public UserBean setValue(User doMain) {
				return wrapBean(doMain);
			}
		});
	}


	@Override
	public boolean saveUser(User user, Long[] roleIds) {
		if (ObjUtil.isEmpty(user.getAgencies()) || ObjUtil.isEmpty(user.getAgencies().getId())) {
			Exceptions.service("UserNameHasExist", "用户机构不能为空");
		}
		boolean hasAuthority = agenciesService.findHasAuthorityByUserId(UserSessionContext.getUserSession().getUserId(), user.getAgencies().getId());
		if(!hasAuthority){
			Exceptions.service("", "您没有权限在当前机构下增加用户");
		}
		user.setUserRoleType(UserRoleType.ADVANCED);
		return userService.saveUser(user, roleIds);
	}

	@Override
	public void updateUser(User user, Long[] roleIds) {
		if (ObjUtil.isEmpty(user.getAgencies()) || ObjUtil.isEmpty(user.getAgencies().getId())) {
			Exceptions.service("UserNameHasExist", "用户机构不能为空");
		}
		boolean hasAuthority = agenciesService.findHasAuthorityByUserId(UserSessionContext.getUserSession().getUserId(), user.getAgencies().getId());
		if(!hasAuthority){
			Exceptions.service("", "您没有权限在当前机构下修改用户");
		}
		userService.updateUser(user, roleIds);
	}


	@Override
	public boolean checkUpdateLoginName(Long userId, String loginName) {
		return userService.checkUpdateLoginName(userId, loginName);
	}

	@Override
	public void deleteUser(Long[] userIds) {
		userService.deleteUser(userIds);
	}

	@Override
	public PagedInfo<UserBean> findListPage(Page page,String loginName, String name, Long userId, List<Byte> userRoleType,List<Long> agenciesIds) {
		PagedInfo<User> paged = userService.findListPage(page, loginName, name, userId, userRoleType, agenciesIds);
		PagedInfo<UserBean> pagedBean = PersistableUtil.toPagedInfoBeans(paged, new SetBean<User>(){
			@Override
			public UserBean setValue(User doMain) {
				UserBean bean = wrapBean(doMain);
				System.out.println(doMain.getCallName());
				bean.setCallName(doMain.getCallName());
				return bean;
			}
		});
		return pagedBean;
	}
	
	@Override
	public void updateUserPreferences(User user) {
		userService.updateUserPreferences(user);		
	}


	@Override
	public void updateMenuStyle(String menuStyle) {
		User user=userService.findById(UserSessionContext.getUserSession().getUserId());
//		user.setMenuStyle(menuStyle);
		userService.updateSimple(user);
	}


	@Override
	public BaseDao<User, Long> getDao() {
		return userDao;
	}

	@Override
	public void updatePassUser(Long id) {
		final User user=userDao.findById(id);
		checkParameter(user, "当前用户不存在");
		UserTaskInstance userTask=user.getUserTaskInstance();
		//并提交流程
		if(ObjUtil.isNotEmpty(userTask)){
			taskInstanceService.updateTaskPass(userTask.getId(),user, "同意通过",new BusinessStorage() {
				@Override
				public boolean save(UserTaskInstance userTaskInstance) {
					return userApprovalService.saveForm(userTaskInstance);
				}
//				@Override
//				public void end(UserTaskInstance userTaskInstance) {
//					user.setUserStatus(UserStatus.NORMAL);
//					user.setUserTaskInstance(null);
//					userDao.update(user);
//				}
			});
		}else{
			Exceptions.service("", "当前用户无法通过");
		}
	}

	@Override
	public void deleteRegisterUser(Long id) {
		
		User user=userService.findById(id);
		
		if (ObjUtil.isEmpty(user)) {
			Exceptions.service("", "您要删除的节点不存在");
		}
		
		// 判断当前节点是否，已经在流程中
		if (ObjUtil.isNotEmpty(user.getUserTaskInstance())) {
			userService.deleteUser(new Long[]{user.getId()});
			taskInstanceService.updateCloseTask(user.getUserTaskInstance().getId());
		}else{
			Exceptions.service("", "用户已成功注册，不能被删除");
		}
	}
	@Override
	public UserBean update(User user, DatastreamBean profile) {
		User olduser = userDao.findById(user.getId());
		olduser.setName(user.getName());
		olduser.setGender(user.getGender());
		olduser.setEmail(user.getEmail());
		olduser.setBirthday(user.getBirthday());
		userDao.update(olduser);
		return wrapBean(olduser);
	}


	@Override
	public boolean saveRegUser(User user) {
		if (ObjUtil.isEmpty(user.getAgencies()) || ObjUtil.isEmpty(user.getAgencies().getId())) {
			Exceptions.service("UserNameHasExist", "用户机构不能为空");
		}
		Agencies agencies = agenciesService.findById(user.getAgencies().getId());
		user.setUserRoleType(UserRoleType.ADVANCED);
		//设置用户状态
		UserRegisterStrategy strategy = userRegisterStrategyService.saveFindByOrgId(agencies.getOrg().getId());
		if (ObjUtil.isEmpty(strategy)) {
			Exceptions.service("", "没有找到新策略");
		}
		user.setUserStatus(UserStatus.NEW);
		if (YesorNo.YES.equals(strategy.getIsRegister())) {
			
			if (!userService.saveUser(user, null)) {
				Exceptions.service("UserNameHasExist", PropertiesUtil.getExceptionMsg("loginNameIsExist"));
			}
			user.setUserStatus(ImportApprovalStatus.PENDING);
			taskInstanceService.createTaskInstance(strategy.getTaskTemp().getId(), user,FormOperType.REGUSER,"用户注册任务：", new Long[] { user.getId() }, true);
		} else {
			user.setUserStatus(UserStatus.NORMAL);
			userService.saveUser(user, null);
		}
		return true;
	}


	@Override
	public boolean saveUser(User user, Long[] roleIds, Long[] safeResourceIds) {
		saveUser(user, roleIds);
		//设置用户默认资源
		Long collectionId=user.getAgencies().getOrg().getSafeResourceCollectionId();
		if(ObjUtil.isNotEmpty(collectionId)){
			List<SafeResourceCollection> srces=safeResourceCollectionService.findByIds(safeResourceIds);
			if(ObjUtil.isNotEmpty(srces)){
				for (SafeResourceCollection safeResourceCollection : srces) {
					UserSafeResourceCollection usrc=new UserSafeResourceCollection();
					usrc.setSafeResourceCollection(safeResourceCollection);
					usrc.setUser(user);
					userSafeResourceCollectionService.save(usrc);
				}
			}
		}else{
			userSafeResourceCollectionService.deleteByUserId(user.getId());
		}
		return true;
	}


	@Override
	public void updateUser(User user, Long[] roleIds, Long[] safeResourceIds) {
		updateUser(user, roleIds);
		Agencies agencies = agenciesService.findById(user.getAgencies().getId());
		Long collectionId=agencies.getOrg().getSafeResourceCollectionId();
		//删除
		userSafeResourceCollectionService.deleteByUserId(user.getId());
		
		if(ObjUtil.isNotEmpty(collectionId)){
			List<SafeResourceCollection> srces=safeResourceCollectionService.findByIds(safeResourceIds);
			if(ObjUtil.isNotEmpty(srces)){
				for (SafeResourceCollection safeResourceCollection : srces) {
					UserSafeResourceCollection usrc=new UserSafeResourceCollection();
					usrc.setSafeResourceCollection(safeResourceCollection);
					usrc.setUser(user);
					userSafeResourceCollectionService.save(usrc);
				}
			}
		}
	}


	@Override
	public boolean saveUser(User user) {
		if (ObjUtil.isEmpty(user.getAgencies()) || ObjUtil.isEmpty(user.getAgencies().getId())) {
			Exceptions.service("UserNameHasExist", "用户机构不能为空");
		}
		boolean hasAuthority = agenciesService.findHasAuthorityByUserId(UserSessionContext.getUserSession().getUserId(),user.getAgencies().getId());
		if(!hasAuthority){
			Exceptions.service("", "您没有权限在当前机构下增加用户");
		}
		user.setUserRoleType(UserRoleType.ADVANCED);
		return userService.saveUser(user, null);
	}

	@Override
	public void updateUser(User user) {
		if (ObjUtil.isEmpty(user.getAgencies()) || ObjUtil.isEmpty(user.getAgencies().getId())) {
			Exceptions.service("UserNameHasExist", "用户机构不能为空");
		}
		boolean hasAuthority = agenciesService.findHasAuthorityByUserId(UserSessionContext.getUserSession().getUserId(),user.getAgencies().getId());
		if(!hasAuthority){
			Exceptions.service("", "您没有权限在当前机构下修改用户");
		}
		userService.updateUser(user);
	}


	@Override
	public List<UserBean> findListByNameAgenciesIds(String loginName, String name, List<Byte> userRoleType,List<Long> agenciesIds) {
		List<User> userList = userService.findList(loginName, name, userRoleType, agenciesIds);
		List<UserBean> pagedBean = PersistableUtil.toListBeans(userList, new SetBean<User>(){
			@Override
			public UserBean setValue(User doMain) {
				return wrapBean(doMain);
			}
		});
		return pagedBean;
	}


	@Override
	public List<Long> findUserIdListByNameAgenciesIds(String loginName, String name, List<Byte> userRoleType,List<Long> agenciesIds) {
		return userService.findUserIdList(loginName, name, userRoleType, agenciesIds);
	}


	@Override
	public void updateUserTime(Long id) {
		User user = userDao.findById(id);
		if(ObjUtil.isNotEmpty(user)){
			user.setUpdateTime(System.currentTimeMillis());
			userDao.update(user);
		}
	}
	
}
