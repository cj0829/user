package org.csr.common.user.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.UserSafeResourceCollectionDao;
import org.csr.common.user.domain.SafeResourceCollection;
import org.csr.common.user.domain.User;
import org.csr.common.user.domain.UserSafeResourceCollection;
import org.csr.common.user.service.UserSafeResourceCollectionService;
import org.csr.core.exception.Exceptions;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.param.AndInParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:UserSafeResourceCollection.java <br/>
 * Date:     Fri Dec 04 09:21:42 CST 2015
 * @author   liurui-PC <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *  service实现
 */
@Service("userSafeResourceCollectionService")
public class UserSafeResourceCollectionServiceImpl extends SimpleBasisService<UserSafeResourceCollection, Long> implements UserSafeResourceCollectionService {
    @Resource
    private UserSafeResourceCollectionDao userSafeResourceCollectionDao;
    
    @Override
	public BaseDao<UserSafeResourceCollection, Long> getDao() {
		return userSafeResourceCollectionDao;
	}
	
	@Override
	public void save(UserSafeResourceCollection userSafeResourceCollection){
		//如果需要判断这里更改
		
		userSafeResourceCollectionDao.save(userSafeResourceCollection);
	}

	@Override
	public void update(UserSafeResourceCollection userSafeResourceCollection){
	
		//如果需要判断这里更改
		UserSafeResourceCollection olduserSafeResourceCollection = userSafeResourceCollectionDao.findById(userSafeResourceCollection.getId());
		
		//大家需要检查一下，有些在domain对象中没有Commnt标签，是否需要在这里存在！
		olduserSafeResourceCollection.setId(userSafeResourceCollection.getId());
		olduserSafeResourceCollection.setUser(userSafeResourceCollection.getUser());
		olduserSafeResourceCollection.setSafeResourceCollection(userSafeResourceCollection.getSafeResourceCollection());

		userSafeResourceCollectionDao.update(olduserSafeResourceCollection);
	}
	
	@Override
	public boolean checkNameIsExist(Long id, String name) {
		UserSafeResourceCollection userSafeResourceCollection = userSafeResourceCollectionDao.existParam(new AndEqParam("name",name));
		if (ObjUtil.isNotEmpty(id)) {
		    if (ObjUtil.isEmpty(userSafeResourceCollection) || userSafeResourceCollection.getId().equals(id)) {
			    return false;
			}
		}
		if (ObjUtil.isEmpty(userSafeResourceCollection)) {
		    return false;
		}
		return true;
	}

	@Override
	public void batchSave(Long[] safeResourceCollectionIds, Long[] userIds) {
		if(ObjUtil.isEmpty(safeResourceCollectionIds)){
			Exceptions.service("", "没有选择资源！");
		}
		if(ObjUtil.isEmpty(userIds)){
			Exceptions.service("", "没有要授权的用户！");
		}
		for(int i=0;i<userIds.length;i++){
			for(int j=0;j<safeResourceCollectionIds.length;j++){
				UserSafeResourceCollection ss=userSafeResourceCollectionDao.existParam(new AndEqParam("user.id",userIds[i]),
						new AndEqParam("safeResourceCollection.id", safeResourceCollectionIds[j]));
				if(ObjUtil.isEmpty(ss)){
					UserSafeResourceCollection usrc=new UserSafeResourceCollection();
					usrc.setSafeResourceCollection(new SafeResourceCollection(safeResourceCollectionIds[j]));
					User user=new User();
					user.setId(userIds[i]);
					usrc.setUser(user);
					userSafeResourceCollectionDao.save(usrc);
				}
			}
		}
	}

	@Override
	public void deleteAuthorized(Long safeResourceCollectionId, Long userId) {
		if(ObjUtil.isEmpty(safeResourceCollectionId)){
			Exceptions.service("", "没有选择资源！");
		}
		if(ObjUtil.isEmpty(userId)){
			Exceptions.service("", "没有要授权的用户！");
		}
		userSafeResourceCollectionDao.deleteAuthorized(safeResourceCollectionId,userId);
	}

	@Override
	public List<UserSafeResourceCollection> findByUserId(Long userId) {
		return userSafeResourceCollectionDao.findByParam(new AndEqParam("user.id",userId));
	}

	@Override
	public int deleteSystemByUserId(Long userId,Long safeResourceCollectionId) {
		if(ObjUtil.isEmpty(safeResourceCollectionId)||ObjUtil.isEmpty(userId)){
			return 0;
		}
		return userSafeResourceCollectionDao.deleteSystemByUserId(userId,safeResourceCollectionId);
	}

	@Override
	public int deleteByUserId(Long userId) {
		if(ObjUtil.isEmpty(userId)){
			return 0;
		}
		return userSafeResourceCollectionDao.deleteByParams(new AndEqParam("user.id",userId));
	}
	
	@Override
	public UserSafeResourceCollection findByUserIdAndCollectionId(Long adminUserId, Long collectionId) {
		return userSafeResourceCollectionDao.existParam(new AndEqParam("user.id",adminUserId),new AndEqParam("safeResourceCollection.id", collectionId));
	}

	@Override
	public boolean existByCollectionId(Long collectionId) {
		if(ObjUtil.isEmpty(collectionId)){
			return true;
		}
		return userSafeResourceCollectionDao.existByCollectionId(collectionId);
	}

	@Override
	public void saveUserRoles(Long userId, List<Long> safeResourceCollectionIds) {
		if(ObjUtil.isEmpty(safeResourceCollectionIds)){
			Exceptions.service("", "没有选择资源！");
		}
		if(ObjUtil.isEmpty(userId)){
			Exceptions.service("", "没有要授权的用户！");
		}
		
		for (Long safeResourceCollectionId : safeResourceCollectionIds) {
			boolean bool = ObjUtil.isEmpty(findByUserIdAndCollectionId(userId, safeResourceCollectionId));
			if (bool) {
				UserSafeResourceCollection usrc=new UserSafeResourceCollection();
				usrc.setSafeResourceCollection(new SafeResourceCollection(safeResourceCollectionId));
				User user=new User();
				user.setId(userId);
				usrc.setUser(user);
				userSafeResourceCollectionDao.save(usrc);
			}
		}		
	}

	@Override
	public int deleteUserRoles(Long userId,
			List<Long> safeResourceCollectionIds) {
		return userSafeResourceCollectionDao.deleteByParams(new AndInParam("user.id",userId),new AndInParam("safeResourceCollection.id",safeResourceCollectionIds));
		
	}

}
