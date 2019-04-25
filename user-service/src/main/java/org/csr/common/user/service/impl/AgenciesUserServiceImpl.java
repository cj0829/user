package org.csr.common.user.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.AgenciesUserDao;
import org.csr.common.user.domain.AgenciesUser;
import org.csr.common.user.domain.User;
import org.csr.common.user.service.AgenciesUserService;
import org.csr.core.exception.Exceptions;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:AgenciesUser.java <br/>
 * Date:     Wed Oct 11 19:54:07 CST 2017
 * @author   summy <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *  service实现
 */
@Service("agenciesUserService")
public class AgenciesUserServiceImpl extends SimpleBasisService<AgenciesUser, Long> implements AgenciesUserService {
    @Resource
    private AgenciesUserDao agenciesUserDao;
    
    @Override
	public BaseDao<AgenciesUser, Long> getDao() {
		return agenciesUserDao;
	}

	@Override
	public AgenciesUser save(AgenciesUser agenciesUser) {
		if(ObjUtil.isEmpty(agenciesUser)){
			return agenciesUser;
		}
		if(ObjUtil.isEmpty(agenciesUser.getUser()) || ObjUtil.isEmpty(agenciesUser.getUser().getId())){
			Exceptions.service("", "必须选择用户");
		}
		
		if(ObjUtil.isEmpty(agenciesUser.getAgenciesId())){
			Exceptions.service("", "必须选择机构");
		}
		
		AgenciesUser user = checkUserAgencies(agenciesUser.getUser().getId(), agenciesUser.getAgenciesId());
		if(ObjUtil.isNotEmpty(user)){
			return user;
		}
		agenciesUserDao.save(agenciesUser);
		return agenciesUser;
	}

	@Override
	public AgenciesUser update(AgenciesUser agenciesUser) {
		if(ObjUtil.isEmpty(agenciesUser)){
			return agenciesUser;
		}
		if(ObjUtil.isEmpty(agenciesUser.getUser()) || ObjUtil.isEmpty(agenciesUser.getUser().getId())){
			Exceptions.service("", "必须选择用户");
		}
		
		if(ObjUtil.isEmpty(agenciesUser.getAgenciesId())){
			Exceptions.service("", "必须选择机构");
		}
		AgenciesUser user = checkUserAgencies(agenciesUser.getUser().getId(), agenciesUser.getAgenciesId());
		
		if(ObjUtil.isNotEmpty(user)){
			return user;
		}
		agenciesUserDao.update(agenciesUser);
		return agenciesUser;
	}

	@Override
	public int deleteByUserIdAgenciesId(Long userId, Long agenciesId) {
		if(ObjUtil.isEmpty(userId) || ObjUtil.isEmpty(agenciesId)){
			return 0;
		}
		return agenciesUserDao.deleteByParams(new AndEqParam("agenciesId", agenciesId),new AndEqParam("user.id", userId));
	}
	
	@Override
	
	public	AgenciesUser checkUserAgencies(Long userId,Long agenciesId){
		return agenciesUserDao.existParam(new AndEqParam("agenciesId", agenciesId),new AndEqParam("user.id", userId));
		
	}
	
	
	@Override
	public int deleteByUserId(Long userId) {
		if(ObjUtil.isEmpty(userId)){
			return 0;
		}
		return agenciesUserDao.deleteByParams(new AndEqParam("user.id", userId));
	}

	@Override
	public PagedInfo<AgenciesUser> findListPage(Page page, String loginName,String name, Long userId, List<Byte> userRoleType,List<Long> agenciesIds) {
		return agenciesUserDao.findListPage(page, loginName, name, userId, userRoleType, agenciesIds);
	}

	@Override
	public PagedInfo<User> findUnJoinUserByRoleId(Page page, Long roleId,
			String loginName, String name, List<Long> agenciesIds) {
		return agenciesUserDao.findUnJoinUserByRoleId(page, roleId, loginName, name, agenciesIds);
	}

	@Override
	public PagedInfo<User> findJoinUserByRoleId(Page page, Long roleId,
			String loginName, String name, List<Long> agenciesIds) {
		return agenciesUserDao.findJoinUserByRoleId(page, roleId, loginName, name, agenciesIds);
	}

	@Override
	public PagedInfo<User> findUnJoinUserByCollectionId(Page page,
			Long safeResourceCollectionId, String loginName, String name,
			List<Long> agenciesIds) {
		return agenciesUserDao.findUnJoinUserByCollectionId(page, safeResourceCollectionId, loginName, name, agenciesIds);
	}

	@Override
	public PagedInfo<User> findJoinUserByCollectionId(Page page,Long safeResourceCollectionId, String loginName, String name,
			List<Long> agenciesIds) {
		return agenciesUserDao.findJoinUserByCollectionId(page, safeResourceCollectionId, loginName, name, agenciesIds);
	}

	@Override
	public PagedInfo<User> findInterestedFriends(Page page, List<Long> userIds,
			Byte userRoleType, String name, Long agenciesId) {
		return agenciesUserDao.findInterestedFriends(page, userIds, userRoleType, name, agenciesId);
	}

	@Override
	public List<Long> findUserIdList(String loginName, String name,
			List<Byte> userRoleType, List<Long> agenciesIds) {
		return agenciesUserDao.findUserIdList(loginName, name, userRoleType, agenciesIds);
	}

	@Override
	public List<AgenciesUser> findList(String loginName, String name,List<Byte> userRoleType, List<Long> agenciesIds) {
		return agenciesUserDao.findList(loginName, name, userRoleType, agenciesIds);
	}
	
}
