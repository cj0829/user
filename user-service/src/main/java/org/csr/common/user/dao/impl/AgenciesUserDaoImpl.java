package org.csr.common.user.dao.impl;

import java.util.List;

import org.csr.common.user.constant.UserRoleType;
import org.csr.common.user.dao.AgenciesUserDao;
import org.csr.common.user.domain.AgenciesUser;
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
 * ClassName:AgenciesUser.java <br/>
 * Date: Wed Oct 11 19:54:07 CST 2017 <br/>
 * 
 * @author summy <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 dao实现
 */
@Repository("agenciesUserDao")
public class AgenciesUserDaoImpl extends JpaDao<AgenciesUser, Long> implements AgenciesUserDao {

	@Override
	public Class<AgenciesUser> entityClass() {
		return AgenciesUser.class;
	}

	@Override
	public PagedInfo<AgenciesUser> findListPage(Page page,String loginName,String name,Long id,List<Byte> userRoleType,List<Long> agenciesIds){
		Finder finder=FinderImpl.create("select u from AgenciesUser u where (1=1 ");
		if(ObjUtil.isNotEmpty(loginName)){
			finder.append(" and u.user.loginName like :loginName","loginName","%"+loginName+"%");
		}
		if(ObjUtil.isNotBlank(name)){
			finder.append(" and u.user.name like :name","name","%"+name+"%");
		}
		
		if(ObjUtil.isNotEmpty(id)){
			finder.append(" and u.user.id like ").append("'%"+id+"%'");
		}
		
		if(ObjUtil.isNotEmpty(userRoleType)){
			finder.append(" and u.user.userRoleType in (:userRoleType)","userRoleType",userRoleType);
		}
		if(ObjUtil.isNotEmpty(agenciesIds)){
			finder.append(" and u.agenciesId in (:agenciesIds)");
			finder.setParam("agenciesIds", agenciesIds);
		}
		
		finder.append(" and u.user.userStatus=").append(UserStatus.NORMAL);
		finder.append(" and u.user.isDelete=").append(false);
		finder.append(" )");
//		;
		
		return findPage(page,finder);
	}
	
	@Override
	public PagedInfo<User> findUnJoinUserByRoleId(Page page,Long roleId,String loginName,String name,List<Long> agenciesIds){
		
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
	public List<AgenciesUser> findList(String loginName, String name,List<Byte> userRoleType, List<Long> agenciesIds) {
		Finder finder=FinderImpl.create("select u from AgenciesUser u where (1=1 ");
		if(ObjUtil.isNotEmpty(loginName)){
			finder.append(" and u.user.loginName like :loginName","loginName","%"+loginName+"%");
		}
		if(ObjUtil.isNotBlank(name)){
			finder.append(" and u.user.name like :name","name","%"+name+"%");
		}
		
		if(ObjUtil.isNotEmpty(userRoleType)){
			finder.append(" and u.user.userRoleType in (:userRoleType)","userRoleType",userRoleType);
		}
		if(ObjUtil.isNotEmpty(agenciesIds)){
			finder.append(" and u.agenciesId in (:agenciesIds)");
			finder.setParam("agenciesIds", agenciesIds);
		}
		
		finder.append(" and u.user.userStatus=").append(UserStatus.NORMAL);
		finder.append(" and u.user.isDelete=").append(false);
		finder.append(" )");
//		finder.insertEnd(" ORDER BY u.agenciesId");
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
		finder.insertEnd(" ORDER BY u.agenciesId");
		finder.setCacheable(true);
		return find(finder);
	}
}
