/**
 * Project Name:user-facade
 * File Name:SafeResourceFacade.java
 * Package Name:org.csr.common.user.facade
 * Date:2016-9-1下午3:19:25
 * Copyright (c) 2016, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.facade;

import java.util.List;

import org.csr.common.user.entity.CategoryListBean;
import org.csr.common.user.entity.SafeResourceBean;
import org.csr.core.persistence.service.BasisFacade;

/**
 * ClassName:SafeResourceFacade.java <br/>
 * System Name：    在线学习系统 <br/>
 * Date:     2016-9-1下午3:19:25 <br/>
 * @author   Administrator <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
public interface SafeResourceFacade extends BasisFacade<SafeResourceBean, Long>{

	List<SafeResourceBean> findByCollectionId(Long safeResourceCollectionId,List<Integer> tipType);

	List<Long> collectionResource(Long collectionId,List<Long> resourceIds,Integer resourceType, List<Integer> tipType);

	/**
	 * @param userId
	 * @param agencies
	 * @return
	 */
	CategoryListBean findCategoryListBeanByUserSafeResourceType(Long userId,Integer safeResourceType);

	List<Long> findCategoryIdsBeanByUserSafeResourceType(Long userId,Integer safeResourceType);
	
}

