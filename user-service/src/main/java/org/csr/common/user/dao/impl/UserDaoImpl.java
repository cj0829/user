package org.csr.common.user.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.csr.common.user.constant.UserRoleType;
import org.csr.common.user.dao.UserDao;
import org.csr.common.user.domain.User;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.business.domain.UserStatus;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Repository;

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
@Repository("userDao")
public class UserDaoImpl extends JpaDao<User,Long> implements UserDao{

	@Override
	public Class<User> entityClass(){
		return User.class;
	}

	
	@Override
	public PagedInfo<User> findListPage(Page page,String loginName,String name,Long id,List<Byte> userRoleType,List<Long> agenciesIds){
		Finder finder=FinderImpl.create("select u from User u where (1=1 ");
		if(ObjUtil.isNotEmpty(loginName)){
			finder.append(" and u.loginName like :loginName","loginName","%"+loginName+"%");
		}
		if(ObjUtil.isNotBlank(name)){
			finder.append(" and u.name like :name","name","%"+name+"%");
		}
		if(ObjUtil.isNotEmpty(id)){
			finder.append(" and u.id like ").append("'%"+id+"%'");
		}
		
		if(ObjUtil.isNotEmpty(userRoleType)){
			finder.append(" and u.userRoleType in (:userRoleType)","userRoleType",userRoleType);
		}
		if(ObjUtil.isNotEmpty(agenciesIds)){
			finder.append(" and u.agencies.id in (:agenciesIds)");
			finder.setParam("agenciesIds", agenciesIds);
		}
		
		finder.append(" and u.userStatus=").append(UserStatus.NORMAL);
		finder.append(" and u.isDelete=").append(false);
		finder.append(" )");
		finder.setCacheable(true);
		return findPage(page,finder);
	}
	@Override
	public PagedInfo<User> findListPageByUserStatus(Page page,Byte userRoleType,List<Long> agenciesIds){
		Finder finder=FinderImpl.create("select u from User u where 1=1");
		if(ObjUtil.isNotEmpty(agenciesIds)){
			finder.append(" and u.agencies.id in (:agenciesIds)");
			finder.setParam("agenciesIds", agenciesIds);
		}
		finder.append(" and u.isDelete=").append(false);
//		if(ObjUtil.isNotEmpty(userStatus)){
//			finder.append(" and u.userStatus = :userStatus","userStatus",userStatus);
//		}
		finder.setCacheable(true);
		return findPage(page,finder);
	}
	
	
	@Override
	public List<User> findChildUserByManagerUserId(Long managerUserId,String loginName,String name,Long id,Byte userRoleType,List<Long> agenciesIds) {
		Finder finder = FinderImpl.create("select u,(select count(cu.id) from User cu where cu.managerUser.id=u.id)");
		finder.append(" from User u where u.managerUser.id=:managerUserId");
		finder.setParam("managerUserId", managerUserId);
		
		if(ObjUtil.isNotBlank(loginName)){
			finder.append(" and u.loginName like :loginName","loginName","%"+loginName+"%");
		}
		//============
		if(ObjUtil.isNotBlank(name)){
			finder.append(" and u.name like :name","name","%"+name+"%");
		}
		if(ObjUtil.isNotEmpty(id)){
			finder.append(" and u.id like ").append("'%"+id+"%'");
		}
		//============
		if(ObjUtil.isNotEmpty(userRoleType)){
			finder.append(" and u.userRoleType= :userRoleType","userRoleType",userRoleType);
		}
		finder.append(" and u.userStatus=").append(UserStatus.NORMAL);
		finder.append(" and u.isDelete=").append(false);
		finder.setCacheable(true);
		List<Object[]> list = find(finder);
		List<User> userList = new ArrayList<User>(0);
		if (ObjUtil.isNotEmpty(list)) {
			for (Object[] objects : list) {
				User user = (User) objects[0];
				userList.add(user);
				Long childCount = (Long) objects[1];
				if (ObjUtil.isNotEmpty(childCount)) {
					user.setChildCount(childCount.intValue());
				} else {
					user.setChildCount(0);
				}
			}
		}
		return userList;
	}
	
	
	@Override
	public List<User> findChildIdUserByManagerUserId(Long managerUserId,String loginName,String name,Long id,Byte userRoleType,List<Long> agenciesIds) {
		Finder finder = FinderImpl.create("select u.id,(select count(cu.id) from User cu where cu.managerUser.id=u.id)");
		finder.append(" from User u where u.managerUser.id=:managerUserId");
		finder.setParam("managerUserId", managerUserId);
		
		if(ObjUtil.isNotBlank(loginName)){
			finder.append(" and u.loginName like :loginName","loginName","%"+loginName+"%");
		}
		//============
		if(ObjUtil.isNotBlank(name)){
			finder.append(" and u.name like :name","name","%"+name+"%");
		}
		if(ObjUtil.isNotEmpty(id)){
			finder.append(" and u.id like ").append("'%"+id+"%'");
		}
		//============
		if(ObjUtil.isNotEmpty(userRoleType)){
			finder.append(" and u.userRoleType= :userRoleType","userRoleType",userRoleType);
		}
		finder.append(" and u.userStatus=").append(UserStatus.NORMAL);
		finder.append(" and u.isDelete=").append(false);
		finder.setCacheable(true);
		List<Object[]> list = find(finder);
		List<User> userList = new ArrayList<User>(0);
		if (ObjUtil.isNotEmpty(list)) {
			for (Object[] objects : list) {
				User user = new User(ObjUtil.toLong( objects[0]));
				userList.add(user);
				Long childCount = (Long) objects[1];
				if (ObjUtil.isNotEmpty(childCount)) {
					user.setChildCount(childCount.intValue());
				} else {
					user.setChildCount(0);
				}
			}
		}
		return userList;
	}
	

	@Override
	public List<User> findList(Long orgId,String loginName,Byte userRoleType){
		Finder finder=FinderImpl.create("select u from User u where 1=1");
		if(ObjUtil.isNotEmpty(loginName)){
			finder.append(" and u.loginName like :loginName","loginName","%"+loginName+"%");
		}
		if(ObjUtil.isNotEmpty(userRoleType)){
			finder.append(" and u.userRoleType= :userRoleType","userRoleType",userRoleType);
		}
		finder.append(" and u.userStatus=").append(UserStatus.NORMAL);
		finder.append(" and u.isDelete=").append(false);
		if(ObjUtil.longNotNull(orgId)){
			finder.append(" and u.primaryOrgId = :primaryOrgId","primaryOrgId",orgId);
		}
		finder.setCacheable(true);
		return find(finder);
	}

	/**
	 * findByLoginName: 根据登陆名取用户明细 <br/>
	 * 
	 * @author caijin
	 * @param loginName
	 * @return
	 * @since JDK 1.7
	 */
	public User findByLoginName(String loginName){
		Finder finder=FinderImpl.create("select u from User u where u.loginName=:loginName");
		finder.setParam("loginName", loginName);
		finder.append(" and u.userStatus=").append(UserStatus.NORMAL);
		finder.append(" and u.isDelete=").append(false);
		List<User> userList=this.find(finder);
		if(userList.size()>0){
			return userList.get(0);
		}
		return null;
	}

	/**
	 * 批量修改用户密码 userId：用户id,password:密码
	 * 
	 * @see org.csr.common.user.dao.UserDao#updateBatchPassword(java.lang.String,
	 *      java.lang.String)
	 */
	public void updateBatchPassword(Long[] userIds,String password){
		if(ObjUtil.isEmpty(userIds)){
			return;
		}
		Finder finder=FinderImpl.create("update User u set u.password=:password");
		finder.append(" where u.id in (:userIds)","userIds",Arrays.asList(userIds));
		finder.setParam("password",password);
		this.batchHandle(finder);
	}

	/**
	 * 修改用户状态userId：用户id,userStatus:用户状态
	 * 
	 * @see org.csr.common.user.dao.UserDao#updateById(java.lang.Long, int)
	 */
	public void updateById(Long userId,int userStatus){
		Finder finder=FinderImpl.create("update User u set u.userStatus=:userStatus");
		finder.append(" where u.userId=:userId");
		finder.setParam("userStatus",userStatus);
		finder.setParam("userId",userId);
		this.batchHandle(finder);
	}

	@Override
	public User findByOrganizationAdmin(Long organizationId) {
		Finder finder=FinderImpl.create("select u from User u where u.id = (select o.adminUserId from Organization o where o.id=:organizationId)");
		finder.setParam("organizationId", organizationId);
		finder.setCacheable(true);
		List<User> userList=this.find(finder);
		if(userList.size()>0){
			return userList.get(0);
		}
		return null;
	}

	public PagedInfo<User> findUnJoinUserByRoleId(Page page,Long roleId,String loginName,String name,List<Long> agenciesIds){
//		Finder user=FinderImpl.create("select u from User u where u.userRoleType=").append(UserRoleType.GENERAL);
//		if(ObjUtil.isNotBlank(loginName)){
//			user.append(" and u.loginName like '%").append(loginName).append("%'");
////			user.append(" and u.loginName like '%:loginName%'","loginName",loginName);
//		}
//		if(ObjUtil.isNotBlank(name)){
//			user.append(" and u.name like '%").append(name).append("%'");
//		}
//		Long usercount=count(user);
//		if(ObjUtil.longIsNull(usercount)){
//			return createPagedInfo(0l, page, new ArrayList<User>());
//		}
//		Finder userRole=FinderImpl.create("select ur.id from UserRole ur where ur.role.id=:roleId");
//		userRole.setParam("roleId", roleId);
//		if(ObjUtil.isNotBlank(loginName)){
//			userRole.append(" and ur.user.loginName like '%").append(loginName).append("%'");
//		}
//		if(ObjUtil.isNotBlank(name)){
//			userRole.append(" and ur.user.name like '%").append(name).append("%'");
//		}
//		Long userRolecount=count(userRole);
//		if(ObjUtil.longNotNull(usercount)){
//			usercount=usercount-userRolecount;
//		}
//		
		Finder finder=FinderImpl.create("select u from User u ");
		finder.append("where  not exists (select ur.user.id from UserRole ur where ur.user.id = u.id ");
		finder.append(" and ur.role.id=").append(roleId).append(")");
//		finder.append(" and u.userRoleType=").append(UserRoleType.GENERAL);
		finder.append(" and u.userStatus=").append(UserStatus.NORMAL);
		finder.append(" and u.isDelete=").append(false);
		if(ObjUtil.isNotEmpty(agenciesIds)){
			finder.append(" and  u.agencies.id in (:agenciesIds)");
			finder.setParam("agenciesIds", agenciesIds);
		}
		//由查询域引起的
		if(ObjUtil.isNotBlank(loginName)){
			finder.append(" and u.loginName like '%").append(loginName).append("%'");
		}
		if(ObjUtil.isNotBlank(name)){
			finder.append(" and u.name like '%").append(name).append("%'");
		}
		finder.setCacheable(true);
		PagedInfo<User>  userInfo=findPage(page, finder);
		return userInfo;
 	}
	@Override
	public PagedInfo<User> findJoinUserByRoleId(Page page, Long roleId,String loginName,String name,List<Long> agenciesIds) {
		Finder finder=FinderImpl.create("select ur.user from UserRole ur where ");
		finder.append(" ur.role.id = '").append(roleId).append("'");
		if(ObjUtil.isNotBlank(loginName)){
			finder.append(" and ur.user.loginName like '%").append(loginName).append("%'");
		}
		if(ObjUtil.isNotBlank(name)){
			finder.append(" and ur.user.name like '%").append(name).append("%'");
		}
		if(ObjUtil.isNotEmpty(agenciesIds)){
			finder.append(" and  ur.user.agencies.id in (:agenciesIds)");
			finder.setParam("agenciesIds", agenciesIds);
		}
		finder.append(" and ur.user.userStatus=").append(UserStatus.NORMAL);
		finder.append(" and ur.user.isDelete=").append(false);
		finder.setCacheable(true);
		return findPage(page, finder);
	}

	@Override
	public PagedInfo<User> findUnJoinUserByCollectionId(Page page, Long safeResourceCollectionId,
			String loginName, String name, List<Long> agenciesIds) {
		Finder finder=FinderImpl.create("select u from User u ");
		finder.append("where  not exists (select usrc.user.id from UserSafeResourceCollection usrc where usrc.user.id = u.id ");
		finder.append(" and usrc.safeResourceCollection.id=").append(safeResourceCollectionId).append(")");
		finder.append(" and u.userRoleType=").append(UserRoleType.GENERAL);
		finder.append(" and u.userStatus=").append(UserStatus.NORMAL);
		finder.append(" and u.isDelete=").append(false);
		finder.setCacheable(true);
		if(ObjUtil.isNotEmpty(agenciesIds)){
			finder.append(" and  u.agencies.id in (:agenciesIds)");
			finder.setParam("agenciesIds", agenciesIds);
		}
		//由查询域引起的
		if(ObjUtil.isNotBlank(loginName)){
			finder.append(" and u.loginName like '%").append(loginName).append("%'");
		}
		if(ObjUtil.isNotBlank(name)){
			finder.append(" and u.name like '%").append(name).append("%'");
		}
	    PagedInfo<User>  userInfo=findPage(page, finder);
		return userInfo;
	}

	@Override
	public PagedInfo<User> findJoinUserByCollectionId(Page page, Long safeResourceCollectionId,
			String loginName, String name, List<Long> agenciesIds) {
		Finder finder=FinderImpl.create("select usrc.user from UserSafeResourceCollection usrc where ");
		finder.append(" usrc.safeResourceCollection.id = '").append(safeResourceCollectionId).append("'");
		if(ObjUtil.isNotBlank(loginName)){
			finder.append(" and usrc.user.loginName like '%").append(loginName).append("%'");
		}
		if(ObjUtil.isNotBlank(name)){
			finder.append(" and usrc.user.name like '%").append(name).append("%'");
		}
		if(ObjUtil.isNotEmpty(agenciesIds)){
			finder.append(" and  usrc.user.agencies.id in (:agenciesIds)");
			finder.setParam("agenciesIds", agenciesIds);
		}
		finder.append(" and usrc.user.userStatus=").append(UserStatus.NORMAL);
		finder.append(" and usrc.user.isDelete=").append(false);
		finder.setCacheable(true);
		return findPage(page, finder);
	}

	@Override
	public Long countAll() {
		Finder finder=FinderImpl.create("select count(u.id) from User u");
		finder.setCacheable(true);
		return countOriginalHql(finder);
	}

	@Override
	public PagedInfo<User> findInterestedFriends(Page page, List<Long> userIds,
			Byte userRoleType, String name, Long agenciesId) {
		Finder finder=FinderImpl.create("select u from User u where 1=1");
		if(ObjUtil.isNotEmpty(userIds)){
			finder.append(" and u.id not in(:userIds)", "userIds", userIds);
		}
		if(ObjUtil.isNotEmpty(userRoleType)){
			finder.append(" and u.userRoleType=:userRoleType", "userRoleType", userRoleType);
		}
		if(ObjUtil.isNotEmpty(name)){
			finder.append(" and u.name like:name", "name", "%"+name+"%");
		}
		if(ObjUtil.isNotEmpty(agenciesId)){
			finder.append(" and u.agencies.id=:agenciesId", "agenciesId", agenciesId);
		}
		return findPage(page, finder);
	}


	@Override
	public List<User> findList(String loginName, String name, List<Byte> userRoleType, List<Long> agenciesIds) {
		Finder finder=FinderImpl.create("select u from User u where (1=1 ");
		if(ObjUtil.isNotBlank(loginName)){
			finder.append(" and u.loginName like :loginName","loginName","%"+loginName+"%");
		}
		if(ObjUtil.isNotBlank(name)){
			finder.append(" and u.name like :name","name","%"+name+"%");
		}
		if(ObjUtil.isNotEmpty(userRoleType)){
			finder.append(" and u.userRoleType in (:userRoleType)","userRoleType",userRoleType);
		}
		if(ObjUtil.isNotEmpty(agenciesIds)){
			finder.append(" and u.agencies.id in (:agenciesIds)");
			finder.setParam("agenciesIds", agenciesIds);
		}
		
		finder.append(" and u.userStatus=").append(UserStatus.NORMAL);
		finder.append(" and u.isDelete=").append(false);
		finder.append(" )");
		finder.setCacheable(true);
		return find(finder);
	}


	@Override
	public List<Long> findUserIdList(String loginName, String name,List<Byte> userRoleType, List<Long> agenciesIds) {
		Finder finder=FinderImpl.create("select u.id from User u where (1=1 ");
		if(ObjUtil.isNotEmpty(loginName)){
			finder.append(" and u.loginName like :loginName","loginName","%"+loginName+"%");
		}
		if(ObjUtil.isNotBlank(name)){
			finder.append(" and u.name like :name","name","%"+name+"%");
		}
		
		if(ObjUtil.isNotEmpty(userRoleType)){
			finder.append(" and u.userRoleType in (:userRoleType)","userRoleType",userRoleType);
		}
		if(ObjUtil.isNotEmpty(agenciesIds)){
			finder.append(" and u.agencies.id in (:agenciesIds)");
			finder.setParam("agenciesIds", agenciesIds);
		}
		
		finder.append(" and u.userStatus=").append(UserStatus.NORMAL);
		finder.append(" and u.isDelete=").append(false);
		finder.append(" )");
		finder.setCacheable(true);
		return find(finder);
	}

	
}
