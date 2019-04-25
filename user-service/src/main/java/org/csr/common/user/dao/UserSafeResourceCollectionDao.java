package org.csr.common.user.dao;


import org.csr.common.user.domain.UserSafeResourceCollection;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName:UserSafeResourceCollection.java <br/>
 * Date: Fri Dec 04 09:21:42 CST 2015 <br/>
 * 
 * @author liurui-PC <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  dao接口
 */
public interface UserSafeResourceCollectionDao extends BaseDao<UserSafeResourceCollection,Long>{

	/**
	 * 
	 * deleteAuthorized: 取消授权 <br/>
	 * @author liurui
	 * @param safeResourceCollectionId
	 * @param userId
	 * @since JDK 1.7
	 */
	public void deleteAuthorized(Long safeResourceCollectionId, Long userId);

	/**
	 * 
	 * deleteSystemByUserId: 删除用户的系统资源库 <br/>
	 * @author liurui
	 * @param userId
	 * @param safeResourceCollectionId
	 * @since JDK 1.7
	 */
	public int deleteSystemByUserId(Long userId,Long safeResourceCollectionId);

	/**
	 * 
	 * existByCollectionId: 资源库是否授权给了用户 <br/>
	 * @author liurui
	 * @param collectionId
	 * @return
	 * @since JDK 1.7
	 */
	public boolean existByCollectionId(Long collectionId);


}
