package org.csr.common.user.dao.impl;


import org.csr.common.user.dao.UserSafeResourceCollectionDao;
import org.csr.common.user.domain.UserSafeResourceCollection;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Repository;


/**
 * ClassName:UserSafeResourceCollection.java <br/>
 * Date: Fri Dec 04 09:21:42 CST 2015 <br/>
 * 
 * @author liurui-PC <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  dao实现
 */
@Repository("userSafeResourceCollectionDao")
public class UserSafeResourceCollectionDaoImpl extends JpaDao<UserSafeResourceCollection,Long> implements UserSafeResourceCollectionDao{

	@Override
	public Class<UserSafeResourceCollection> entityClass(){
		return UserSafeResourceCollection.class;
	}

	@Override
	public void deleteAuthorized(Long safeResourceCollectionId, Long userId) {
		Finder finder=FinderImpl.create("delete from UserSafeResourceCollection usrc where usrc.safeResourceCollection.id=:safeResourceCollectionId " +
				"and usrc.user.id=:userId");
		finder.setParam("safeResourceCollectionId",safeResourceCollectionId);
		finder.setParam("userId",userId);
		batchHandle(finder);
	}

	@Override
	public int deleteSystemByUserId(Long userId,Long safeResourceCollectionId) {
		Finder finder=FinderImpl.create("delete from UserSafeResourceCollection usrc where usrc.safeResourceCollection.id=:safeResourceCollectionId and usrc.user.id=:userId");
		finder.setParam("safeResourceCollectionId",safeResourceCollectionId);
		finder.setParam("userId",userId);
		return batchHandle(finder);
	}

	@Override
	public boolean existByCollectionId(Long collectionId) {
		if(ObjUtil.isEmpty(collectionId)){
			return true;
		}
		Finder finder=FinderImpl.create("select distinct usrc.safeResourceCollection from UserSafeResourceCollection usrc where usrc.safeResourceCollection.id=:safeResourceCollectionId");
		finder.setParam("safeResourceCollectionId",collectionId);
		if(ObjUtil.isNotEmpty(find(finder))){
			return true;
		}
		return false;
	}

}
