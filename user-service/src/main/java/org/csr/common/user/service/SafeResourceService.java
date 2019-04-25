package org.csr.common.user.service;


import java.util.List;

import org.csr.common.user.domain.SafeResource;
import org.csr.common.user.domain.SafeResourceCollection;
import org.csr.common.user.entity.CategoryListBean;
import org.csr.core.persistence.service.BasisService;

/*
 * @(#)Sss.java 
 *       
 * ClassName:SafeResource.java <br/>
 * Date: Wed Dec 02 11:54:01 CST 2015 <br/>
 * @author liurui-PC <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  service接口
 */
public interface SafeResourceService extends BasisService<SafeResource, Long> {

	
	/**
	 * save: 保存方法<br/>
	 * @author liurui-PC
	 * @param safeResource 
	 */
	public void save(SafeResource safeResource);

	/**
	 * update: 编辑方法<br/>
	 * @author liurui-PC
	 * @param safeResource 
	 */
	public void update(SafeResource safeResource);


	public boolean checkNameIsExist(Long id, String name);

	/**
	 * 
	 * save: 保存资源 <br/>
	 * @author liurui
	 * @param tipId
	 * @param code
	 * @param type
	 * @param safe
	 * @since JDK 1.7
	 */
	public void save(Long tipId, String code, Integer type, SafeResourceCollection safe);

	/**
	 * 
	 * create: 创建资源 <br/>
	 * @author liurui
	 * @param tipId
	 * @param safeResourceCollection
	 * @param type
	 * @since JDK 1.7
	 */
	public void create(Long tipId, SafeResourceCollection safeResourceCollection, Integer type);

	/**
	 * findByCollectionIds: 查询资源库下的资源 <br/>
	 * @author liurui
	 * @param safeResourceCollectionIds
	 * @return
	 * @since JDK 1.7
	 */
	public List<SafeResource> findByCollectionIdsType(List<Long> safeResourceCollectionIds,Integer safeResourceType);

	
	/**
	 * 查询资源，是否存在
	 * @author liurui
	 * @param safeResourceCollectionIds
	 * @return
	 * @since JDK 1.7
	 */
	public Long findCountByTipId(List<Long> safeResourceCollectionIds,List<Long> resourceIds);
	/**
	 * 
	 * collectionResource: 查看传过来的资源中有多少是当前资源库的资源 <br/>
	 * @author liurui
	 * @param collectionId
	 * @param resourceIds
	 * @param resourceType
	 * @param tipType
	 * @return
	 * @since JDK 1.7
	 */
	public List<Long> collectionResource(Long collectionId, List<Long> resourceIds, Integer resourceType, List<Integer> tipType);

	/**
	 * 
	 * delete: 删除资源 <br/>
	 * @author liurui
	 * @param collectionId
	 * @param tipIds
	 * @param type
	 * @since JDK 1.7
	 */
	public void delete(Long collectionId, Long[] tipIds, Integer type);

	/**
	 * 
	 * findByCollectionId: 根据末级节点的类型查询 <br/>
	 * @param safeResourceCollectionId
	 * @param tipType
	 * @return
	 * @author liurui
	 * @date 2016-1-25
	 * @since JDK 1.7
	 */
	public List<SafeResource> findByCollectionId(Long safeResourceCollectionId,List<Integer> tipType);

	/**
	 * 删除资源库下的资源
	 * @param collectionId
	 */
	public void delete(Long collectionId);

	/**
	 * 查询出，分类
	 * @param userId
	 * @param safeResourceType
	 * @return
	 */
	CategoryListBean findCategoryListBeanByUserSafeResourceType(Long userId,Integer safeResourceType);

	/**
	 * 删除资源类型
	 * @param collectionId
	 * @param type
	 */
	void delete(Long safeResourceCollectionId, Integer type);
	
}
