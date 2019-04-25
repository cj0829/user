package org.csr.common.user.dao;


import java.util.List;

import org.csr.common.user.domain.SafeResource;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName:SafeResource.java <br/>
 * Date: Wed Dec 02 11:54:01 CST 2015 <br/>
 * 
 * @author liurui-PC <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  dao接口
 */
public interface SafeResourceDao extends BaseDao<SafeResource,Long>{

	/**
	 * 
	 * collectionResource: 查询传过来的资源是否是当前资源库的资源 <br/>
	 * @author liurui
	 * @param collectionId
	 * @param resourceIds
	 * @param resourceType
	 * @param tipType
	 * @return
	 * @since JDK 1.7
	 */
	public List<Long> collectionResource(Long collectionId, List<Long> resourceIds, Integer resourceType,List<Integer> tipType);

	/**
	 * 
	 * delete: 删除资源 <br/>
	 * @author liurui
	 * @param collectionId
	 * @param tipIds
	 * @param type 
	 * @since JDK 1.7
	 */
	public void delete(Long collectionId, List<Long> tipIds, Integer type);
	/**
	 * 
	 * delete: 删除资源 <br/>
	 * @author liurui
	 * @param collectionId
	 * @param tipIds
	 * @param type 
	 * @return 
	 * @since JDK 1.7
	 */
	public int delete(Long safeResourceCollectionId, Integer type);
	/**
	 * 删除资源库下的资源
	 * @param collectionId
	 */
	public void delete(Long safeResourceCollectionId);

//	/**
//	 * 
//	 * @param safeResourceCollectionIds
//	 * @param orgids
//	 * @param resourceIds
//	 * @return
//	 */
//	public Long findCountByOrgIdTipId(List<Long> safeResourceCollectionIds,List<Long> orgids, List<Long> resourceIds);

}
