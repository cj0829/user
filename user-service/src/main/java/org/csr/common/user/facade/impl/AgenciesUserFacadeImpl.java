package org.csr.common.user.facade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.csr.common.user.dao.AgenciesUserDao;
import org.csr.common.user.domain.Agencies;
import org.csr.common.user.domain.AgenciesUser;
import org.csr.common.user.domain.User;
import org.csr.common.user.entity.UserBean;
import org.csr.common.user.facade.AgenciesUserFacade;
import org.csr.common.user.service.AgenciesUserService;
import org.csr.common.user.service.UserService;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.CacheableParam;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.param.AndInParam;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.support.ToValue;
import org.springframework.stereotype.Service;

/**
 * ClassName:AgenciesUser.java <br/>
 * Date: Wed Oct 11 19:54:07 CST 2017
 * 
 * @author summy <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 Facade实现
 */

@Service("agenciesUserFacade")
public class AgenciesUserFacadeImpl extends UserFacadeImpl implements AgenciesUserFacade {

	@Resource
	protected AgenciesUserService agenciesUserService;
	@Resource
	protected UserService userService;
	@Resource
	protected AgenciesUserDao agenciesUserDao;

	@Override
	public PagedInfo<UserBean> findListPage(Page page, String loginName,
			String name, Long userId, List<Byte> userRoleType,
			List<Long> agenciesIds) {
		PagedInfo<AgenciesUser> paged = agenciesUserDao.findListPage(page,loginName, name, userId, userRoleType, agenciesIds);
		List<Long> arrayTransforList = PersistableUtil.arrayTransforList(paged,new ToValue<AgenciesUser, Long>() {
			@Override
			public Long getValue(AgenciesUser obj) {
				return obj.getAgenciesId();
			}
		});

		final Map<Long, Agencies> mapAgencies = agenciesService.findMapByIds(arrayTransforList);
		PagedInfo<UserBean> pagedBean = PersistableUtil.toPagedInfoBeans(paged,new SetBean<AgenciesUser>() {
			@Override
			public UserBean setValue(AgenciesUser doMain) {
				UserBean bean = wrapBean(doMain.getUser());
				Agencies agencies = mapAgencies.get(doMain.getAgenciesId());
				if (ObjUtil.isNotEmpty(agencies)) {
					bean.setAgenciesId(agencies.getId());
					bean.setAgenciesName(agencies.getName());
				}
				if (ObjUtil.isNotEmpty(doMain.getUser())) {
					bean.setCallName(doMain.getUser().getCallName());
				}
				return bean;
			}
		});
		return pagedBean;
	}

	@Override
	public PagedInfo<UserBean> findUnJoinUserByRoleId(Page page, Long roleId,String loginName, String name, List<Long> agenciesIds) {
		PagedInfo<User> userList = agenciesUserDao.findUnJoinUserByRoleId(page,roleId, loginName, name, agenciesIds);
		return PersistableUtil.toPagedInfoBeans(userList, new SetBean<User>() {
			@Override
			public UserBean setValue(User doMain) {
				return wrapBean(doMain);
			}
		});
	}

	@Override
	public PagedInfo<UserBean> findJoinUserByRoleId(Page page, Long roleId,String loginName, String name, List<Long> agenciesIds) {
		PagedInfo<User> userList = agenciesUserDao.findJoinUserByRoleId(page,roleId, loginName, name, agenciesIds);
		return PersistableUtil.toPagedInfoBeans(userList, new SetBean<User>() {
			@Override
			public UserBean setValue(User doMain) {
				return wrapBean(doMain);
			}
		});
	}

	@Override
	public PagedInfo<UserBean> findUnJoinUserByCollectionId(Page page,
			Long safeResourceCollectionId, String loginName, String name,
			List<Long> agenciesIds) {
		PagedInfo<User> userList = agenciesUserDao.findUnJoinUserByCollectionId(page, safeResourceCollectionId,loginName, name, agenciesIds);
		return PersistableUtil.toPagedInfoBeans(userList, new SetBean<User>() {
			@Override
			public UserBean setValue(User doMain) {
				return wrapBean(doMain);
			}
		});
	}

	@Override
	public PagedInfo<UserBean> findJoinUserByCollectionId(Page page,Long safeResourceCollectionId, String loginName, String name,
			List<Long> agenciesIds) {
		PagedInfo<User> userList = agenciesUserDao.findJoinUserByCollectionId(page, safeResourceCollectionId, loginName, name, agenciesIds);
		return PersistableUtil.toPagedInfoBeans(userList, new SetBean<User>() {
			@Override
			public UserBean setValue(User doMain) {
				return wrapBean(doMain);
			}
		});
	}

	/**
	 * 按照登录名称，或者用户名，或者用户角色查询。<br>
	 * 机构，可以根据用户自身的机构，或者用户能够查询到的机构查询下面的用户<br>
	 */
	@Override
	public List<UserBean> findListByNameAgenciesIds(String loginName,
			String name, List<Byte> userRoleType, List<Long> agenciesIds) {
		List<AgenciesUser> userList = agenciesUserDao.findList(loginName, name,userRoleType, agenciesIds);
		List<Long> arrayTransforList = PersistableUtil.arrayTransforList(userList, new ToValue<AgenciesUser, Long>() {
			@Override
			public Long getValue(AgenciesUser obj) {
				return obj.getAgenciesId();
			}
		});

		final Map<Long, Agencies> mapAgencies = agenciesService.findMapByIds(arrayTransforList);

		List<UserBean> pagedBean = PersistableUtil.toListBeans(userList,new SetBean<AgenciesUser>() {
			@Override
			public UserBean setValue(AgenciesUser doMain) {
				UserBean bean = wrapBean(doMain.getUser());
				Agencies agencies = mapAgencies.get(doMain
						.getAgenciesId());
				if (ObjUtil.isNotEmpty(agencies)) {
					bean.setAgenciesId(agencies.getId());
					bean.setAgenciesName(agencies.getName());
				}
				if (ObjUtil.isNotEmpty(doMain.getUser())) {
					bean.setCallName(doMain.getUser().getCallName());
				}
				return bean;
			}
		});
		return pagedBean;
	}

	/**
	 * 按照登录名称，或者用户名，或者用户角色查询。<br>
	 * 机构，可以根据用户自身的机构，或者用户能够查询到的机构查询下面的用户<br>
	 * 查询，
	 */
	@Override
	public List<Long> findUserIdListByNameAgenciesIds(String loginName,
			String name, List<Byte> userRoleType, List<Long> agenciesIds) {
		return agenciesUserDao.findUserIdList(loginName, name, userRoleType,
				agenciesIds);
	}

	@Override
	public UserBean checkUserAgencies(Long userId, Long agenciesId) {
		AgenciesUser checkUserAgencies = agenciesUserService.checkUserAgencies(userId, agenciesId);
		if (ObjUtil.isNotEmpty(checkUserAgencies)) {
			return UserBean.wrapBean(checkUserAgencies.getUser(),
					checkUserAgencies.getAgenciesId());
		} else {
			return null;
		}
	}

	@Override
	public UserBean saveAgenciesUser(AgenciesUser agenciesUser) {
		AgenciesUser user = agenciesUserService.save(agenciesUser);
		return UserBean.wrapBean(user.getUser(), user.getAgenciesId());
	}

	@Override
	public UserBean update(AgenciesUser agenciesUser) {
		AgenciesUser user = agenciesUserService.update(agenciesUser);
		return UserBean.wrapBean(user.getUser(), user.getAgenciesId());
	}

	@Override
	public int deleteByUserIdAgenciesId(Long userId, Long agenciesId) {
		return agenciesUserService.deleteByUserIdAgenciesId(userId, agenciesId);
	}

	@Override
	public int deleteByUserId(Long userId) {
		return agenciesUserService.deleteByUserId(userId);
	}

	@Override
	public List<Long> findAgenciesByUserId(Long userId) {
		if (ObjUtil.isEmpty(userId)) {
			return new ArrayList<Long>(0);
		}
		return agenciesUserDao.findFieldListByParam("agenciesId",new AndEqParam("user.id", userId),new CacheableParam(true));
	}

	@Override
	public List<Long> findUserByAgenciesId(Long agenciesId) {
		if (ObjUtil.isEmpty(agenciesId)) {
			return new ArrayList<Long>(0);
		}
		return agenciesUserDao.findFieldListByParam("user.id", new AndEqParam("agenciesId", agenciesId),new CacheableParam(true));
	}
	
	@Override
	public List<Long> findUserByAgenciesIds(List<Long> agenciesIds) {
		if (ObjUtil.isEmpty(agenciesIds)) {
			return new ArrayList<Long>(0);
		}
		return agenciesUserDao.findFieldListByParam("user.id", new AndInParam("agenciesId", agenciesIds),new CacheableParam(true));
	}

	@Override
	public int updateAllByAgenciesId(Long agenciesId) {
		int i = 0;
		List<User> userList = userService.findList(null, null, null, ObjUtil.toList(agenciesId));
		if(ObjUtil.isNotEmpty(userList)) {
			for (User user : userList) {
				
				Agencies agencies = user.getAgencies();
				if(ObjUtil.isNotEmpty(agencies)) {
					AgenciesUser agenciesUser = new AgenciesUser();
					agenciesUser.setAgenciesId(agencies.getId());
					agenciesUser.setCode(agencies.getCode());
					agenciesUser.setUser(user);
					//保存到关系表中
					agenciesUserService.save(agenciesUser);
					i++;
				}
			}
		}
		return i;
	}
}
