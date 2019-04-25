package org.csr.common.user.service;


import java.util.List;

import org.csr.common.user.domain.UserSafeResourceCollection;
import org.csr.core.persistence.service.BasisService;

/*
 * @(#)Sss.java 
 *       
 * ClassName:UserSafeResourceCollection.java <br/>
 * Date: Fri Dec 04 09:21:42 CST 2015 <br/>
 * @author liurui-PC <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  service接口
 */
public interface UserSafeResourceCollectionService extends BasisService<UserSafeResourceCollection, Long>{

	
	/**
	 * save: 保存方法<br/>
	 * @author liurui-PC
	 * @param userSafeResourceCollection 
	 */
	public void save(UserSafeResourceCollection userSafeResourceCollection);

	/**
	 * update: 编辑方法<br/>
	 * @author liurui-PC
	 * @param userSafeResourceCollection 
	 */
	public void update(UserSafeResourceCollection userSafeResourceCollection);


	public boolean checkNameIsExist(Long id, String name);

	/**
	 * 
	 * batchSave: 批量保存 <br/>
	 * @author liurui
	 * @param safeResourceCollectionIds
	 * @param userIds
	 * @since JDK 1.7
	 */
	public void batchSave(Long[] safeResourceCollectionIds, Long[] userIds);

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
	 * findByUserId: 查询用户的资源库 <br/>
	 * @author liurui
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	public List<UserSafeResourceCollection> findByUserId(Long userId);

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
	 * deleteSystemByUserId: 删除用户的系统资源库 <br/>
	 * @author liurui
	 * @param userId
	 * @param safeResourceCollectionId
	 * @since JDK 1.7
	 */
	public int deleteByUserId(Long userId);
	/**
	 * 
	 * findByUserIdAndCollectionId: 根据用户和库查询 <br/>
	 * @author liurui
	 * @param adminUserId
	 * @param collectionId
	 * @return
	 * @since JDK 1.7
	 */
	public UserSafeResourceCollection findByUserIdAndCollectionId(Long adminUserId, Long collectionId);

	/**
	 * 
	 * existByCollectionId: 资源是否sho <br/>
	 * @author liurui
	 * @param id
	 * @since JDK 1.7
	 */
	public boolean existByCollectionId(Long id);

	public void saveUserRoles(Long userId, List<Long> safeResourceCollectionIds);

	public int deleteUserRoles(Long userId,List<Long> safeResourceCollectionIds);

}
