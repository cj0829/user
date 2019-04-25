/**
 * Project Name:exam
 * File Name:CategoryOrgSeachService.java
 * Package Name:org.csr.common.user.SafeResource
 * Date:2015-12-17下午3:43:55
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.safeResource;

import java.util.List;

/**
 * ClassName:CategoryOrgSeachService.java <br/>
 * System Name：    签到系统 <br/>
 * Date:     2015-12-17下午3:43:55 <br/>
 * @author   liurui <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 * 
 * 根据 选择资源，得到当前资源分类。<br/>
 * 最终还是查询分类。然后得到分类在具体查询 资源。<br/>
 * 资源：<br/>
 * 用户，试题，试卷 等。只要自己想定义<br/>
 */
public interface CategoryResourceSearchService {
	
	/**
	 * 具体查询的是哪种资源类型
	 * @return
	 */
	public Integer getSafeResourceType();
	/**
	 * 通过资源查询分类（分类类型有很多）。去除重复的分类。<br/>
	 * 因为一个分类可能会有多个资源。<br/>
	 * 用户的分类：是机构<br>
	 * 试题、试卷的分类：是库<br>
	 * @author liurui
	 * @param resourceIds
	 * @return
	 * @since JDK 1.7
	 */
	@SuppressWarnings("rawtypes")
	public List<Category> findCategoryByCollectionIds(List<Long> collectionIds);
	
	/**
	 * 
	 * findResourceExistByCategory: 通过资源与类别关系，返回资源id <br/>
	 * @author liurui
	 * @param collectionIds
	 * @param categoryIds
	 * @return
	 * @since JDK 1.7
	 */
	public List<Long> findResourceExistByCategory(List<Long> collectionIds,List<Long> categoryIds);
}

