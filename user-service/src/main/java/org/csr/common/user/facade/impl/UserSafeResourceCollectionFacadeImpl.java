package org.csr.common.user.facade.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.UserSafeResourceCollectionDao;
import org.csr.common.user.domain.UserSafeResourceCollection;
import org.csr.common.user.entity.UserSafeResourceCollectionBean;
import org.csr.common.user.facade.UserInitData;
import org.csr.common.user.facade.UserSafeResourceCollectionFacade;
import org.csr.common.user.service.UserSafeResourceCollectionService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.support.CompareValue;
import org.csr.core.util.support.ToValue;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service("userSafeResourceCollectionFacade")
@Lazy(false)
public class UserSafeResourceCollectionFacadeImpl
		extends SimpleBasisFacade<UserSafeResourceCollectionBean, UserSafeResourceCollection, Long>
		implements UserSafeResourceCollectionFacade,UserInitData  {

	@Resource
	private UserSafeResourceCollectionService userSafeResourceCollectionService;
	@Resource
	private UserSafeResourceCollectionDao userSafeResourceCollectionDao;

	
	@Override
	public void save(UserSafeResourceCollection userSafeResourceCollection) {
		// UserSafeResourceCollection wrapDomain =
		// wrapDomain(userSafeResourceCollection);
		userSafeResourceCollectionService.save(userSafeResourceCollection);
	}

	@Override
	public void batchSave(Long[] safeResourceCollectionIds, Long[] userIds) {
		userSafeResourceCollectionService.batchSave(safeResourceCollectionIds,
				userIds);

	}

	@Override
	public void deleteAuthorized(Long safeResourceCollectionId, Long userId) {
		userSafeResourceCollectionService.deleteAuthorized(
				safeResourceCollectionId, userId);

	}

	@Override
	public void update(UserSafeResourceCollection userSafeResourceCollection) {
		// UserSafeResourceCollection wrapDomain =
		// wrapDomain(userSafeResourceCollection);
		userSafeResourceCollectionService.update(userSafeResourceCollection);

	}

	@Override
	public int deleteSimple(Long[] ids) {
		return userSafeResourceCollectionService.deleteSimple(ids);
	}

	@Override
	public boolean checkNameIsExist(Long id, String name) {
		return userSafeResourceCollectionService.checkNameIsExist(id, name);
	}

	@Override
	public UserSafeResourceCollectionBean wrapBean(
			UserSafeResourceCollection doMain) {
		return UserSafeResourceCollectionBean.wrapBean(doMain);
	}

	@Override
	public BaseDao<UserSafeResourceCollection, Long> getDao() {
		return userSafeResourceCollectionDao;
	}

	@Override
	public List<UserSafeResourceCollectionBean> findByUserId(Long userId) {
		if (ObjUtil.isEmpty(userId)) {
			return new ArrayList<UserSafeResourceCollectionBean>(0);
		}
		List<UserSafeResourceCollection> userList = userSafeResourceCollectionService
				.findByUserId(userId);
		return PersistableUtil.toListBeans(userList,
				new SetBean<UserSafeResourceCollection>() {

					@Override
					public UserSafeResourceCollectionBean setValue(
							UserSafeResourceCollection doMain) {
						return UserSafeResourceCollectionBean.wrapBean(doMain);
					}

				});
	}

	@Override
	public void delInitData(Long userId) {
		if(ObjUtil.isEmpty(userId)){
			return ;
		}
		userSafeResourceCollectionDao.deleteByParams(new AndEqParam("user.id",userId));
	}

	@Override
	public void updateUserSafeResourceCollection(final Long userId, List<Long> safeResourceCollectionIds) {
		checkParameter(userId, "请选择要修改的用户");
		List<UserSafeResourceCollection> byIds = userSafeResourceCollectionDao.findByParam(new AndEqParam("user.id", userId));
		
		List<Long> oldIds = PersistableUtil.arrayTransforList(byIds, new ToValue<UserSafeResourceCollection, Long>() {
			@Override
			public Long getValue(UserSafeResourceCollection obj) {
				if(ObjUtil.isNotEmpty(obj.getSafeResourceCollection())){
					return obj.getSafeResourceCollection().getId();
				}
				return null;
			}
		});
		
		ObjUtil.compareId(oldIds, safeResourceCollectionIds, new CompareValue<Long>() {
			@Override
			public void add(List<Long> safeResourceCollectionIds) {
				userSafeResourceCollectionService.saveUserRoles(userId,safeResourceCollectionIds);
			}
			@Override
			public void update(List<Long> safeResourceCollectionIds) {
				System.out.println("update");
			}
			@Override
			public void delete(List<Long> safeResourceCollectionIds) {
				userSafeResourceCollectionService.deleteUserRoles(userId, safeResourceCollectionIds);
			}
		});
	}

}
